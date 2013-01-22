package lv.janis.skuja.fd.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/*
 * Example 1: Create an animated sprite from texture atlas
 *
 * Create the sprite (in create() method):
 *     atlas = new TextureAtlas(Gdx.files.internal("data/sprites.txt"), Gdx.files.internal("data/"));
 *     mySprite = new AnimatedSprite(atlas, "mySprite");
 *
 *     mySprite.setAnimationRate(60);
 *     mySprite.loop(true);
 *     mySprite.play();
 *
 * Render the sprite (in render() method):
 *     mySprite.update(Gdx.graphics.getDeltaTime());
 *     mySprite.draw(batch);
 *
 * Example 2: Create an animated sprite from file and play reversed and after that pingpong-loop :)
 *
 * Create the sprite (in create() method):
 *     mySprite = new AnimatedSprite(Gdx.files.internal("data/walkanim.png"), 64, 64);
 *
 *     mySprite.setAnimationRate(8);
 *      mySprite.loop(true, AnimatedSprite_LoopType.PINGPONG);
 *      mySprite.play(AnimatedSprite_PlayType.REVERSED);
 *
 * Render the sprite (in render() method):
 *     mySprite.update(Gdx.graphics.getDeltaTime());
 *     mySprite.draw(batch);
 *
 *
 * Example 3: Find out if animation is still running
 * mySprite.update(Gdx.graphics.getDeltaTime());
 * if(mySprite.isPlaying()){
 *      //draw your sprite
 *      mySprite.draw(batch);
 * }else{
 *      //do something like remove your object from the stage
 *      //or omit and do no sprite is drawn
 * }
 *
 */
public class AnimatedSprite extends Sprite {
	protected List<TextureRegion> frameRegions;
	protected int currentFrame;

	protected float frameRate;
	protected float totalDeltaTime;
	protected boolean isPlaying;
	protected boolean isLooping;
	protected boolean isReversed;

	protected AnimatedSprite_PlayType playType;
	protected AnimatedSprite_LoopType loopType;

	public enum AnimatedSprite_PlayType {
		NORMAL, REVERSED, RANDOM
	}

	public enum AnimatedSprite_LoopType {
		NORMAL, PINGPONG
	}

	public final boolean looping() {
		return isLooping;
	}

	public final boolean isPlaying() {
		return isPlaying;
	}

	/*
	 * Initialize
	 */
	private void Init() {
		frameRate = 1.0f;
		isPlaying = false;
		isLooping = true;
		isReversed = false;

		currentFrame = 0;

		frameRegions = new ArrayList<TextureRegion>();
	}

	// Initialize our textureregion (too bad the super constructor must be the
	// first call in overloaded constructors in java,
	// because it's the same code as in the super constructor. Maybe badlogic
	// can create a protected init in the sprite class ?)
	private void CreateSprite(TextureRegion region) {
		setRegion(region);
		setColor(1, 1, 1, 1);
		setSize(Math.abs(region.getRegionWidth()), Math.abs(region.getRegionHeight()));
		setOrigin(getWidth() / 2, getHeight() / 2);

		// Set our playhead at the correct position
		stop();
	}

	/*
	 * Create an animated sprite, based on texture-atlas content.
	 * 
	 * @param textureAtlas Reference to the TextureAtlas instance
	 * 
	 * @param regionName Name of region to be used
	 */
	public AnimatedSprite(final TextureAtlas textureAtlas, final String regionName) {
		Init();

		Array<AtlasRegion> atlasFrameRegions = textureAtlas.findRegions(regionName);
		for (AtlasRegion rg : atlasFrameRegions)
			frameRegions.add((TextureRegion) rg);

		// Initialize our first frame
		CreateSprite(frameRegions.get(0));
	}

	/*
	 * Create an animated sprite, based on a filehandle
	 * 
	 * @param file File to load
	 * 
	 * @param tileWidth Width of each frame tile
	 * 
	 * @param tileHeight Height of each frame tile
	 */
	public AnimatedSprite(final FileHandle file, final int tileWidth, final int tileHeight) {
		Texture texture = new Texture(file);
		CreateAnimatedSpriteFromTexture(texture, tileWidth, tileHeight);
	}

	/*
	 * Create an animated sprite, based on an existing texture instance
	 * 
	 * @param texture Reference to the texture instance
	 * 
	 * @param tileWidth Width of each frame tile
	 * 
	 * @param tileHeight Height of each frame tile
	 */
	public AnimatedSprite(final Texture texture, final int tileWidth, final int tileHeight) {
		CreateAnimatedSpriteFromTexture(texture, tileWidth, tileHeight);
	}

	/*
	 * Wrapper for our texture based constructors.
	 * 
	 * @param texture Reference to the texture instance
	 * 
	 * @param tileWidth Width of each frame tile
	 * 
	 * @param tileHeight Height of each frame tile
	 */
	private void CreateAnimatedSpriteFromTexture(final Texture texture, final int tileWidth, final int tileHeight) {
		Init();

		// Split the texture into tiles (from upper left corner to bottom right
		// corner)
		TextureRegion[][] tiles = TextureRegion.split(texture, tileWidth, tileHeight);

		// Now unwrap the two dimensional array into a single array with all the
		// tiles in the correct animation order.
		for (int row = 0; row < tiles.length; row++) {
			for (int col = 0; col < tiles[row].length; col++) {
				frameRegions.add(tiles[row][col]);
			}
		}

		// Initialize our first frame
		CreateSprite(frameRegions.get(0));
	}

	/*
	 * Set at what rate the animated sprite should be playing.
	 * 
	 * @param fps Framerate in frames per second
	 */
	public void setAnimationRate(final int fps) {
		frameRate = 1.0f / (float) fps;
	}

	/*
	 * Loop the animation
	 * 
	 * @param isLooping If true: the animation plays infinite
	 */
	public void loop(boolean isLooping) {
		loop(isLooping, AnimatedSprite_LoopType.NORMAL);
	}

	/*
	 * Loop the animation (use special looping abilities)
	 * 
	 * @param isLooping If true: the animation plays infinite
	 * 
	 * @param loopType NORMAL: last frame ? start at frame 0. PINGPONG: last
	 * frame ? reverse animation.
	 */
	public void loop(boolean isLooping, AnimatedSprite_LoopType loopType) {
		this.loopType = loopType;
		this.isLooping = isLooping;
	}

	/*
	 * Stop (and therefore rewind and play)
	 */
	public void restart() {
		stop();
		play();
	}

	/*
	 * Start or resume animation
	 */
	public void play() {
		play(AnimatedSprite_PlayType.NORMAL);
	}

	/*
	 * Start or resume animation (use special playing abilities)
	 * 
	 * @param playType NORMAL: start at first frame. REVERSED: start at last
	 * frame. RANDOM: pick a random frame.
	 */
	public void play(AnimatedSprite_PlayType playType) {
		this.playType = playType;
		isReversed = this.playType == AnimatedSprite_PlayType.REVERSED;
		isPlaying = true;
		totalDeltaTime = 0;
	}

	/*
	 * Pauses animation
	 */
	public void pause() {
		isPlaying = false;
		totalDeltaTime = 0;
	}

	/*
	 * Stops and rewinds animation.
	 */
	public void stop() {
		pause();
		if (isLooping) {
			currentFrame = getFirstFrameNumber();
		} else {
			currentFrame -= 1;
		}
	}

	/*
	 * Gets the index of the last frame.
	 * 
	 * @return Returns 0 or last frame index.
	 */
	private int getFirstFrameNumber() {
		return (playType == AnimatedSprite_PlayType.REVERSED ? frameRegions.size() - 1 : 0);
	}

	/*
	 * Updates and increments the animation playhead to the next frame
	 * 
	 * @param deltaTime Frame duration in seconds.
	 */
	public void update(final float deltaTime) {
		if (isPlaying) {
			totalDeltaTime += deltaTime;
			if (totalDeltaTime > frameRate) {
				totalDeltaTime = 0;

				if (this.playType == AnimatedSprite_PlayType.RANDOM) {
					currentFrame = (int) Math.round(Math.random() * (frameRegions.size() - 1));
				} else {
					currentFrame += (isReversed ? -1 : 1);

					// Back to first
					if (currentFrame >= frameRegions.size() || currentFrame < 0) {
						// If not looping, stop
						if (!isLooping) {
							stop();
						} else {
							switch (loopType) {
							case PINGPONG:
								isReversed = !isReversed;
								if (isReversed) {
									currentFrame = frameRegions.size() - 2;
									if (currentFrame < 0)
										currentFrame = 0;
								} else
									currentFrame = 0;

								break;
							case NORMAL:
							default:
								currentFrame = getFirstFrameNumber();
								break;
							}

						}
					}
				}

				TextureRegion region = frameRegions.get(currentFrame);
				// set the region
				setRegion(region);

				// Give an AtlasRegion some special attention (boundaries may be
				// trimmed)
				if (region instanceof AtlasRegion) {
					// Set the region and make sure the sprite is set at the
					// original cell size
					AtlasRegion atlasRegion = (AtlasRegion) region;
					if (atlasRegion.rotate)
						rotate90(true);
					setOrigin(atlasRegion.originalWidth / 2, atlasRegion.originalHeight / 2);
					super.setBounds(this.getX(), this.getY(), Math.abs(atlasRegion.getRegionWidth()),
							Math.abs(atlasRegion.getRegionHeight()));
				}

				setColor(1, 1, 1, 1);
			}
		}
	}
}

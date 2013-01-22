package lv.janis.skuja.fd.model;

import lv.janis.skuja.fd.manager.SoundManager.FdSound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

/**
 * @author Janis Skuja
 */
public class Minion {
	private MinionType minionType;
	private AnimatedSprite walkingSprite;
	private AnimatedSprite attackingSprite;
	private AnimatedSprite dieingSprite;
	private MinionState minionState;
	private TextureAtlas textureAtlas;
	private float x;
	private float y;
	private Rectangle rectangle;
	private float lastAttackTime;
	private float health;
	private float attack;
	private FdSound sound;

	public Minion(MinionType minionType) {
		this.minionType = minionType;
		this.y = MathUtils.random(5, 60);
		this.minionState = MinionState.WALKING;

		switch (minionType) {
		case DARK_KNIGHT:

			this.sound = FdSound.KNIGHT_ATTACK;

			this.attack = 9.5f;

			this.x = Gdx.graphics.getWidth() - 200;

			this.textureAtlas = new TextureAtlas("sprites/darkpack.pack");

			attackingSprite = new AnimatedSprite(textureAtlas, "dk_attack");

			dieingSprite = new AnimatedSprite(textureAtlas, "dk_dieing");

			walkingSprite = new AnimatedSprite(textureAtlas, "dk_walking");

			break;
		case LIGHT_KNIGHT:

			this.sound = FdSound.KNIGHT_ATTACK;

			this.attack = 10;

			this.x = 100;

			this.textureAtlas = new TextureAtlas("sprites/lightpack.pack");

			attackingSprite = new AnimatedSprite(textureAtlas, "k_attack");

			dieingSprite = new AnimatedSprite(textureAtlas, "k_dieing");

			walkingSprite = new AnimatedSprite(textureAtlas, "k_walk");

			break;
		default:
			break;
		}

		this.health = 100;

		walkingSprite.setX(this.x);
		walkingSprite.setY(this.y);
		walkingSprite.setAnimationRate(8);
		walkingSprite.loop(true);
		walkingSprite.play();

		dieingSprite.setY(this.y);
		dieingSprite.setX(this.x);
		dieingSprite.setAnimationRate(6);
		dieingSprite.loop(false);
		dieingSprite.play();

		attackingSprite.setY(this.y);
		attackingSprite.setX(this.x);
		attackingSprite.setAnimationRate(8);
		attackingSprite.loop(true);
		attackingSprite.play();

		this.rectangle = new Rectangle(this.x, this.y, 50, 50);
	}

	public FdSound getSound() {
		return sound;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public float getAttack() {
		return attack;
	}

	public float getLastAttackTime() {
		return lastAttackTime;
	}

	public void setLastAttackTime(float lastAttackTime) {
		this.lastAttackTime = lastAttackTime;
	}

	public TextureAtlas getTextureAtlas() {
		return textureAtlas;
	}

	public void setTextureAtlas(TextureAtlas textureAtlas) {
		this.textureAtlas = textureAtlas;
	}

	public MinionType getMinionType() {
		return minionType;
	}

	public MinionState getMinionState() {
		return minionState;
	}

	public void setMinionState(MinionState minionState) {
		this.minionState = minionState;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public Float getY() {
		return new Float(y);
	}

	public void setY(float y) {
		this.y = y;
	}

	public AnimatedSprite getAnimatedSprite() {
		switch (minionState) {
		case ATACKING:
			return attackingSprite;
		case WALKING:
			return walkingSprite;
		case DIEING:
			return dieingSprite;
		default:
			break;
		}
		return null;
	}

	public void update(float deltaTime) {

		if (minionState != MinionState.ATACKING && minionState != MinionState.DIEING) {
			switch (minionType) {
			case DARK_KNIGHT:
				this.x -= deltaTime * 20;
				break;
			case LIGHT_KNIGHT:
				this.x += deltaTime * 20;
				break;
			default:
				break;
			}
		}

		getAnimatedSprite().setX(this.x);

		this.rectangle.setX(this.x);

		getAnimatedSprite().update(deltaTime);
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}
}

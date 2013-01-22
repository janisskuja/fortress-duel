package lv.janis.skuja.fd.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

/**
 * @author Janis Skuja
 */
public class Fortress {

	private FortressType fortressType;
	private Sprite fortressSprite;
	private Sprite fortressDead;
	private FortressState fortressState;
	private float health;
	private Rectangle rectangle;
	private TextureAtlas textureAtlas;

	public Fortress(FortressType fortressType) {
		this.fortressType = fortressType;
		this.health = 500;
		this.textureAtlas = new TextureAtlas("sprites/fortressPack.pack");
		this.fortressState = FortressState.ALIVE;
		switch (fortressType) {
		case LIGHT_FORTRESS:
			fortressSprite = textureAtlas.createSprite("light_fort");
			fortressDead = textureAtlas.createSprite("light_fort_dead");
			fortressSprite.setX(0);
			fortressDead.setX(0);
			this.rectangle = new Rectangle(0, 0, 100, 480);
			break;
		case DARK_FORTRESS:
			this.rectangle = new Rectangle(680, 0, 100, 480);
			fortressSprite = textureAtlas.createSprite("dark_fort");
			fortressDead = textureAtlas.createSprite("dark_fort_dead");
			fortressSprite.setX(Gdx.graphics.getWidth() - fortressSprite.getWidth());
			fortressDead.setX(fortressSprite.getX());
			break;
		default:
			break;
		}

		fortressSprite.setY(5);
		fortressDead.setY(5);

	}

	public FortressType getFortressType() {
		return fortressType;
	}

	public void setFortressType(FortressType fortressType) {
		this.fortressType = fortressType;
	}

	public Sprite getFortressSprite() {
		if (fortressState == FortressState.ALIVE)
			return fortressSprite;
		else
			return fortressDead;
	}

	public FortressState getFortressState() {
		return fortressState;
	}

	public void setFortressState(FortressState fortressState) {
		this.fortressState = fortressState;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public TextureAtlas getTextureAtlas() {
		return textureAtlas;
	}

	public void setTextureAtlas(TextureAtlas textureAtlas) {
		this.textureAtlas = textureAtlas;
	}

}

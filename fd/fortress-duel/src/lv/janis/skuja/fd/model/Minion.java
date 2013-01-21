package lv.janis.skuja.fd.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * @author Janis Skuja
 */
public class Minion {
	private MinionType type;
	private float lastAttackTime;
	private int health;
	private Rectangle rectangle;
	private Animation attackAnimation;
	private Animation walkAnimation;
	private Drawable drawableDead;
	private Drawable drawableAlive;
	private Image image;
	private boolean dead;
	private boolean attacking;

	public Minion(MinionType type, float lastAttackTime, Drawable textureAlive, Drawable textureDead) {
		super();
		this.type = type;
		this.drawableAlive = textureAlive;
		this.drawableDead = textureDead;
		this.image = new Image(textureAlive);
		this.health = type.getHealth();
		image.scale((float) -0.5);
		if (type.isEnemy()) {
			image.setPosition(Gdx.graphics.getWidth() - image.getWidth() - 10, MathUtils.random(10, 70));
		} else {
			image.setPosition(image.getWidth() + 10, MathUtils.random(10, 90));
		}
		rectangle = new Rectangle(image.getX(), image.getY(), image.getWidth() / 2, image.getHeight() / 2);
		this.lastAttackTime = lastAttackTime;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		if (dead)
			this.image = new Image(drawableDead);
		this.dead = dead;
	}

	public boolean isAttacking() {
		return attacking;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public MinionType getType() {
		return type;
	}

	public void setType(MinionType type) {
		this.type = type;
	}

	public float getLastAttackTime() {
		return lastAttackTime;
	}

	public void setLastAttackTime(float lastAttackTime) {
		this.lastAttackTime = lastAttackTime;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public Animation getAttackAnimation() {
		return attackAnimation;
	}

	public void setAttackAnimation(Animation attackAnimation) {
		this.attackAnimation = attackAnimation;
	}

	public Animation getWalkAnimation() {
		return walkAnimation;
	}

	public void setWalkAnimation(Animation walkAnimation) {
		this.walkAnimation = walkAnimation;
	}

	public Drawable getDrawableDead() {
		return drawableDead;
	}

	public void setDrawableDead(Drawable drawableDead) {
		this.drawableDead = drawableDead;
	}

	public Drawable getDrawableAlive() {
		return drawableAlive;
	}

	public void setDrawableAlive(Drawable drawableAlive) {
		this.drawableAlive = drawableAlive;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

}

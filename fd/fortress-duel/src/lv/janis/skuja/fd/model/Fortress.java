package lv.janis.skuja.fd.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/**
 * @author Janis Skuja
 */
public class Fortress {
	private FortressSide fortressSide;
	private int health;
	private Drawable drawable;
	private Image image;
	private Rectangle rectangle;

	public Fortress(FortressSide fortressSide, int health, Drawable drawable) {
		super();
		this.fortressSide = fortressSide;
		this.health = health;
		this.rectangle = new Rectangle((fortressSide == FortressSide.GOOD ? 0 : Gdx.graphics.getWidth()
				- drawable.getMinWidth()), 70, drawable.getMinWidth(), drawable.getMinHeight());
		this.image = new Image(drawable);
		image.setPosition(rectangle.getX(), rectangle.getY());
	}

	public Drawable getDrawable() {
		return drawable;
	}

	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public FortressSide getFortressSide() {
		return fortressSide;
	}

	public void setFortressSide(FortressSide fortressSide) {
		this.fortressSide = fortressSide;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

}

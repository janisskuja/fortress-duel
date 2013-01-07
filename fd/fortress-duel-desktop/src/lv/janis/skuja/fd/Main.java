package lv.janis.skuja.fd;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = FortressDuelGame.TITLE;
		cfg.useGL20 = FortressDuelGame.USEGL20;
		cfg.width = FortressDuelGame.WIDTH;
		cfg.height = FortressDuelGame.HEIGHT;
		
		new LwjglApplication(new FortressDuelGame(), cfg);
	}
}

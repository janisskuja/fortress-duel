package lv.janis.skuja.fd;

import java.util.Locale;
import java.util.ResourceBundle;

import lv.janis.skuja.fd.resources.ResourceBundleResourceBuilder;
import lv.janis.skuja.fd.screen.SplashScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * @author Janis Skuja
 * 
 */
public class FortressDuelGame extends Game {
	public static String VERSION = " 0.0.1 Alpha";
	public static String TITLE = "Fortress Duel" + VERSION;
	public static boolean USEGL20 = false;
	public static int WIDTH = 800;
	public static int HEIGHT = 480;
	// resource bundles
	private ResourceBundleResourceBuilder builder;
	private ResourceBundle language;
	// preferences
	private FortressDuelPreferences preferences;
	// fonts used throughout game
	private BitmapFont fontArial30;
	// log FPS
	private FPSLogger fpsLog;
	// commonly used textures
	private TextureAtlas buttonTextureAtlas;
	// commonly used skin
	private Skin skin;

	public FortressDuelGame() {
		super();
		preferences = new FortressDuelPreferences();
		builder = new ResourceBundleResourceBuilder();
		fpsLog = new FPSLogger();
	}

	@Override
	public void create() {
		// set default language from preferences
		Locale.setDefault(preferences.getLanguage());

		builder.bundle(FortressDuelPreferences.LOCALE_ENGLISH,
				Gdx.files.internal("resource/languageEn.properties"));
		builder.bundle(FortressDuelPreferences.LOCALE_LATVIAN,
				Gdx.files.internal("resource/languageLv.properties"));
		builder.bundle(FortressDuelPreferences.LOCALE_RUSSIAN,
				Gdx.files.internal("resource/languageRu.properties"));
		builder.root(Gdx.files.internal("resource/languageEn.properties"));

		// build game language (get the right resource .properties file)
		this.language = builder.build();

		// create fonts
		fontArial30 = new BitmapFont(Gdx.files.internal("font/arial.fnt"),
				false);
		// create textures and skin
		buttonTextureAtlas = new TextureAtlas("texture/button/button.pack");
		skin = new Skin();
		skin.addRegions(buttonTextureAtlas);
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
		fontArial30.dispose();
		buttonTextureAtlas.dispose();
		skin.dispose();
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
		fpsLog.log();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	public ResourceBundleResourceBuilder getBuilder() {
		return builder;
	}

	public FortressDuelPreferences getPreferences() {
		return preferences;
	}

	public ResourceBundle getLanguage() {
		return language;
	}

	public void setLanguage(ResourceBundle language) {
		this.language = language;
	}

	public BitmapFont getFontArial30() {
		return fontArial30;
	}

	public void setFontArial30(BitmapFont fontArial30) {
		this.fontArial30 = fontArial30;
	}

	public Skin getSkin() {
		return skin;
	}

	public void setSkin(Skin skin) {
		this.skin = skin;
	}

}

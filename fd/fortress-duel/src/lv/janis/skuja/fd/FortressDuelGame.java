package lv.janis.skuja.fd;

import java.util.Locale;

import lv.janis.skuja.fd.resources.ResourceBundleResourceBuilder;
import lv.janis.skuja.fd.screen.SplashScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class FortressDuelGame extends Game {
	public static String VERSION = " 0.0.1 Alpha";
	public static String TITLE = "Fortress Duel" + VERSION;
	public static boolean USEGL20 = false;
	public static int WIDTH = 800;
	public static int HEIGHT = 480;
	// resource bundles
	private ResourceBundleResourceBuilder builder;
	// preferences
	FortressDuelPreferences preferences;

	public FortressDuelGame() {
		super();
		preferences = new FortressDuelPreferences();
		builder = new ResourceBundleResourceBuilder();
	}

	@Override
	public void create() {
		Locale.setDefault(preferences.getLanguage());

		builder.bundle(FortressDuelPreferences.LOCALE_ENGLISH,
				Gdx.files.internal("resource/languageEn.properties"));
		builder.bundle(FortressDuelPreferences.LOCALE_LATVIAN,
				Gdx.files.internal("resource/languageLv.properties"));
		builder.bundle(FortressDuelPreferences.LOCALE_RUSSIAN,
				Gdx.files.internal("resource/languageRu.properties"));
		builder.root(Gdx.files.internal("resource/languageEn.properties"));

		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
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
}

package lv.janis.skuja.fd;

import java.util.Locale;
import java.util.ResourceBundle;

import lv.janis.skuja.fd.manager.MusicManager;
import lv.janis.skuja.fd.manager.PreferencesManager;
import lv.janis.skuja.fd.manager.SoundManager;
import lv.janis.skuja.fd.manager.VibrationManager;
import lv.janis.skuja.fd.resources.ResourceBundleResourceBuilder;
import lv.janis.skuja.fd.screen.SplashScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
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
	private PreferencesManager preferences;
	// managers
	private VibrationManager vibrationManager;
	private MusicManager musicManager;
	private SoundManager soundManager;
	// log FPS
	private FPSLogger fpsLog;
	// commonly used skin
	private Skin skin;

	@Override
	public void create() {

		Gdx.input.setCatchBackKey(true);

		preferences = new PreferencesManager();
		builder = new ResourceBundleResourceBuilder();
		fpsLog = new FPSLogger();
		// initialize managers
		vibrationManager = new VibrationManager(preferences.isVibrationEnabled());
		musicManager = new MusicManager(preferences.isMusicEnabled());
		soundManager = new SoundManager(preferences.isSoundEffectsEnabled());
		// set default language from preferences
		Locale.setDefault(preferences.getLanguage());

		builder.bundle(PreferencesManager.LOCALE_ENGLISH, Gdx.files.internal("resource/languageEn.properties"));
		builder.bundle(PreferencesManager.LOCALE_LATVIAN, Gdx.files.internal("resource/languageLv.properties"));
		builder.bundle(PreferencesManager.LOCALE_RUSSIAN, Gdx.files.internal("resource/languageRu.properties"));
		builder.root(Gdx.files.internal("resource/languageEn.properties"));

		// build game language (get the right resource .properties file)
		this.language = builder.build();

		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
		musicManager.dispose();
		soundManager.dispose();
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

	public PreferencesManager getPreferences() {
		return preferences;
	}

	public ResourceBundle getLanguage() {
		return language;
	}

	public void setLanguage(ResourceBundle language) {
		this.language = language;
	}

	public Skin getSkin() {
		return skin;
	}

	public void setSkin(Skin skin) {
		this.skin = skin;
	}

	public VibrationManager getVibrationManager() {
		return vibrationManager;
	}

	public void setVibrationManager(VibrationManager vibrationManager) {
		this.vibrationManager = vibrationManager;
	}

	public MusicManager getMusicManager() {
		return musicManager;
	}

	public void setMusicManager(MusicManager musicManager) {
		this.musicManager = musicManager;
	}

	public SoundManager getSoundManager() {
		return soundManager;
	}

	public void setSoundManager(SoundManager soundManager) {
		this.soundManager = soundManager;
	}

}

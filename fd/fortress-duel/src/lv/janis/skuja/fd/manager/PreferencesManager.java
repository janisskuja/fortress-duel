package lv.janis.skuja.fd.manager;

import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * @author Janis Skuja
 * 
 */
public class PreferencesManager {
	// not neccasserily preference constants
	public static final int VIBRATION_TIME = 50;
	// constants
	private static final String PREF_MUSIC_ENABLED = "music.enabled";
	private static final String PREF_SOUND_ENABLED = "sound.enabled";
	private static final String PREF_VIBRATION_ENABLED = "vibration.enabled";
	private static final String PREFS_LANGUAGE = "language";
	private static final String PREFS_NAME = "fortressduel";
	// language constraints
	public static Locale LOCALE_RUSSIAN = new Locale("RUSSIAN");
	public static Locale LOCALE_LATVIAN = new Locale("LATVIAN");
	public static Locale LOCALE_ENGLISH = Locale.ENGLISH;
	private Preferences preferences;

	public PreferencesManager() {
		this.preferences = Gdx.app.getPreferences(PREFS_NAME);
	}

	public Preferences getPreferences() {
		return preferences;
	}

	public boolean isSoundEffectsEnabled() {
		return preferences.getBoolean(PREF_SOUND_ENABLED, true);
	}

	public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
		preferences.putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
		preferences.flush();
	}

	public boolean isVibrationEnabled() {
		return preferences.getBoolean(PREF_VIBRATION_ENABLED, true);
	}

	public void setVibrationEnabled(boolean vibrationEnabled) {
		preferences.putBoolean(PREF_VIBRATION_ENABLED, vibrationEnabled);
		preferences.flush();
	}

	public void setLanguage(Locale language) {
		preferences.putString(PREFS_LANGUAGE, language.toString());
		preferences.flush();
	}

	public Locale getLanguage() {
		String stringLang = preferences.getString(PREFS_LANGUAGE, LOCALE_ENGLISH.toString());
		if (stringLang.equals(LOCALE_ENGLISH.toString())) {
			return LOCALE_ENGLISH;
		} else if (stringLang.equals(LOCALE_RUSSIAN.toString())) {
			return LOCALE_RUSSIAN;
		} else if (stringLang.equals(LOCALE_LATVIAN.toString())) {
			return LOCALE_LATVIAN;
		}
		return LOCALE_ENGLISH;
	}

	public boolean isMusicEnabled() {
		return preferences.getBoolean(PREF_MUSIC_ENABLED, true);
	}

	public void setMusicEnabled(boolean musicEnabled) {
		preferences.putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
		preferences.flush();
	}
}

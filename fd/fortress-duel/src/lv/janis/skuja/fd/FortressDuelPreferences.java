package lv.janis.skuja.fd;

import java.util.Locale;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * @author Janis Skuja
 * 
 */
public class FortressDuelPreferences {
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

	public FortressDuelPreferences() {

	}

	protected Preferences getPrefs() {
		return Gdx.app.getPreferences(PREFS_NAME);
	}

	public boolean isSoundEffectsEnabled() {
		return getPrefs().getBoolean(PREF_SOUND_ENABLED, true);
	}

	public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
		getPrefs().putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
		getPrefs().flush();
	}

	public boolean isVibrationEnabled() {
		return getPrefs().getBoolean(PREF_VIBRATION_ENABLED, true);
	}

	public void setVibrationEnabled(boolean vibrationEnabled) {
		getPrefs().putBoolean(PREF_VIBRATION_ENABLED, vibrationEnabled);
	}

	public void setLanguage(Locale language) {
		getPrefs().putString(PREFS_LANGUAGE, language.toString());
		getPrefs().flush();
	}

	public Locale getLanguage() {
		String stringLang = getPrefs().getString(PREFS_LANGUAGE,
				LOCALE_ENGLISH.toString());
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
		return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
	}

	public void setMusicEnabled(boolean musicEnabled) {
		getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
		getPrefs().flush();
	}
}

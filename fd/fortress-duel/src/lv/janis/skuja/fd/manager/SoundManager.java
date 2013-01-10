package lv.janis.skuja.fd.manager;

import lv.janis.skuja.fd.manager.SoundManager.FdSound;
import lv.janis.skuja.fd.utils.LRUCache;
import lv.janis.skuja.fd.utils.LRUCache.CacheEntryRemovedListener;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Disposable;

/**
 * A service that manages the sound effects.
 */
/**
 * @author Janis Skuja
 */
public class SoundManager implements CacheEntryRemovedListener<FdSound, Sound>, Disposable {
	/**
	 * The available sound files.
	 */
	public enum FdSound {
		CLICK("sound/button.wav");

		private final String fileName;

		private FdSound(String fileName) {
			this.fileName = fileName;
		}

		public String getFileName() {
			return fileName;
		}
	}

	/**
	 * The volume to be set on the sound.
	 */
	private float volume = 1f;

	/**
	 * Whether the sound is enabled.
	 */
	private boolean enabled = true;

	/**
	 * The sound cache.
	 */
	private final LRUCache<FdSound, Sound> soundCache;

	/**
	 * Creates the sound manager.
	 */
	public SoundManager(boolean enabled) {
		soundCache = new LRUCache<SoundManager.FdSound, Sound>(10);
		soundCache.setEntryRemovedListener(this);
		this.enabled = enabled;
	}

	/**
	 * Plays the specified sound.
	 */
	public void play(FdSound sound) {
		// check if the sound is enabled
		if (!enabled)
			return;

		// try and get the sound from the cache
		Sound soundToPlay = soundCache.get(sound);
		if (soundToPlay == null) {
			FileHandle soundFile = Gdx.files.internal(sound.getFileName());
			soundToPlay = Gdx.audio.newSound(soundFile);
			soundCache.add(sound, soundToPlay);
		}

		// play the sound
		soundToPlay.play(volume);
	}

	/**
	 * Sets the sound volume which must be inside the range [0,1].
	 */
	public void setVolume(float volume) {

		// check and set the new volume
		if (volume < 0 || volume > 1f) {
			throw new IllegalArgumentException("The volume must be inside the range: [0,1]");
		}
		this.volume = volume;
	}

	/**
	 * Enables or disabled the sound.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	// EntryRemovedListener implementation

	@Override
	public void notifyEntryRemoved(FdSound key, Sound value) {
		value.dispose();
	}

	/**
	 * Disposes the sound manager.
	 */
	public void dispose() {
		for (Sound sound : soundCache.retrieveAll()) {
			sound.stop();
			sound.dispose();
		}
	}
}

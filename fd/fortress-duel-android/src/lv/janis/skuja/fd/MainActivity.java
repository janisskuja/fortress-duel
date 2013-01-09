package lv.janis.skuja.fd;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

/**
 * @author Janis Skuja
 * 
 */
public class MainActivity extends AndroidApplication {
	private WakeLock wakeLock;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		cfg.useGL20 = FortressDuelGame.USEGL20;
		// for saving battery life
		cfg.useCompass = false;
		cfg.useAccelerometer = false;
		// to use wakelock this needs to be true
		cfg.useWakelock = true;
		initialize(new FortressDuelGame(), cfg);

		// keep the screen bright
		PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK,
				"FD wakelock");
	}

	@Override
	protected void onPause() {
		// when game is paused, need to release wakelock
		wakeLock.release();
		super.onPause();
	}

	@Override
	protected void onResume() {
		// when game is resumed need to acquire wakelock
		wakeLock.acquire();
		super.onResume();
	}
}
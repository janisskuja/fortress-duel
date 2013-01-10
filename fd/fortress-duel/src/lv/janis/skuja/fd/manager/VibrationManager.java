package lv.janis.skuja.fd.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Peripheral;

public class VibrationManager {
	private boolean enabled;
	private boolean available;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public VibrationManager(boolean enabled) {
		super();
		this.enabled = enabled;
		this.available = Gdx.input.isPeripheralAvailable(Peripheral.Vibrator);
	}

	public void vibrate(int miliseconds) {
		if (enabled && available)
			Gdx.input.vibrate(miliseconds);
	}
}

package lv.janis.skuja.fd.screen;

import java.util.Locale;

import lv.janis.skuja.fd.FortressDuelGame;
import lv.janis.skuja.fd.manager.MusicManager.FdMusic;
import lv.janis.skuja.fd.manager.PreferencesManager;
import lv.janis.skuja.fd.manager.SoundManager.FdSound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class OptionsScreen implements Screen {

	private FortressDuelGame game;

	private Stage stage;

	private SpriteBatch batch;

	private TextButton btnCredits;
	private TextButton btnLangLv;
	private TextButton btnLangEn;
	private TextButton btnLangRu;

	private Table table;

	private CheckBox cbSound;
	private CheckBox cbMusic;
	private CheckBox cbVibration;

	public OptionsScreen(FortressDuelGame game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.act(delta);

		batch.begin();
		stage.draw();
		batch.end();

		// Listen for the back key and navigate to previous screen
		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			this.game.setScreen(new MenuScreen(game));
		}
	}

	@Override
	public void resize(int width, int height) {
		createStage(width, height);

	}

	private void createStage(int width, int height) {
		if (stage == null)
			stage = new Stage(width, height, true);
		stage.clear();

		Gdx.input.setInputProcessor(stage);

		createButtons();

		createCheckBoxes();

		createTable();

		stage.addActor(table);
	}

	private void createTable() {

		table = new Table(game.getSkin());
		table.defaults().width(150).height(50).pad(10);
		table.setFillParent(true);
		table.add(game.getLanguage().getString("menu.options")).colspan(4).center().height(100);
		table.row();
		table.add(game.getLanguage().getString("options.sound")).right();
		table.add(cbSound).colspan(3).left();
		table.row();
		table.add(game.getLanguage().getString("options.music")).right();
		table.add(cbMusic).colspan(3).left();
		table.row();
		table.add(game.getLanguage().getString("options.vibration")).right();
		table.add(cbVibration).colspan(3).left();
		table.row();
		table.add(game.getLanguage().getString("options.language")).right();
		table.add(btnLangEn).center();
		table.add(btnLangLv).center();
		table.add(btnLangRu).center();
		table.row();
		table.add(btnCredits).colspan(4).center();
	}

	private void createCheckBoxes() {
		cbMusic = new CheckBox(" ", game.getSkin());
		cbMusic.setChecked(game.getPreferences().isMusicEnabled());
		cbSound = new CheckBox(" ", game.getSkin());
		cbSound.setChecked(game.getPreferences().isSoundEffectsEnabled());
		cbVibration = new CheckBox(" ", game.getSkin());
		cbVibration.setChecked(game.getPreferences().isVibrationEnabled());

		cbMusic.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.getVibrationManager().vibrate(PreferencesManager.VIBRATION_TIME);
				game.getSoundManager().play(FdSound.CLICK);
				boolean enabled = cbMusic.isChecked();
				game.getPreferences().setMusicEnabled(enabled);
				game.getMusicManager().setEnabled(enabled);
				game.getMusicManager().play(FdMusic.MENU);
			}
		});
		cbSound.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.getVibrationManager().vibrate(PreferencesManager.VIBRATION_TIME);
				boolean enabled = cbSound.isChecked();
				game.getPreferences().setSoundEffectsEnabled(enabled);
				game.getSoundManager().setEnabled(enabled);
				game.getSoundManager().play(FdSound.CLICK);
			}
		});
		cbVibration.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				boolean enabled = cbVibration.isChecked();
				game.getSoundManager().play(FdSound.CLICK);
				game.getPreferences().setVibrationEnabled(enabled);
				game.getVibrationManager().setEnabled(enabled);
				game.getVibrationManager().vibrate(PreferencesManager.VIBRATION_TIME);
			}
		});
	}

	private void createButtons() {

		btnLangEn = new TextButton(game.getLanguage().getString("options.language.en"), game.getSkin());
		btnLangLv = new TextButton(game.getLanguage().getString("options.language.lv"), game.getSkin());
		btnLangRu = new TextButton(game.getLanguage().getString("options.language.ru"), game.getSkin());
		btnCredits = new TextButton(game.getLanguage().getString("options.credits"), game.getSkin());
		// add listeners to buttons
		btnLangEn.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.getVibrationManager().vibrate(PreferencesManager.VIBRATION_TIME);
				if (!PreferencesManager.LOCALE_ENGLISH.equals(game.getPreferences().getLanguage())) {
					game.getSoundManager().play(FdSound.CLICK);
					game.getPreferences().setLanguage(PreferencesManager.LOCALE_ENGLISH);
					Locale.setDefault(game.getPreferences().getLanguage());
					game.setLanguage(game.getBuilder().build());
					createStage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				}
			}
		});
		btnLangLv.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.getSoundManager().play(FdSound.CLICK);
				game.getVibrationManager().vibrate(PreferencesManager.VIBRATION_TIME);
				if (!PreferencesManager.LOCALE_LATVIAN.equals(game.getPreferences().getLanguage())) {
					game.getPreferences().setLanguage(PreferencesManager.LOCALE_LATVIAN);
					Locale.setDefault(game.getPreferences().getLanguage());
					game.setLanguage(game.getBuilder().build());
					createStage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				}
			}
		});
		btnLangRu.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.getSoundManager().play(FdSound.CLICK);
				game.getVibrationManager().vibrate(PreferencesManager.VIBRATION_TIME);
				if (!PreferencesManager.LOCALE_RUSSIAN.equals(game.getPreferences().getLanguage())) {
					game.getPreferences().setLanguage(PreferencesManager.LOCALE_RUSSIAN);
					Locale.setDefault(game.getPreferences().getLanguage());
					game.setLanguage(game.getBuilder().build());
					createStage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				}
			}
		});
		btnCredits.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.getSoundManager().play(FdSound.CLICK);
				game.getVibrationManager().vibrate(PreferencesManager.VIBRATION_TIME);
				new Dialog(game.getLanguage().getString("options.credits"), game.getSkin()) {
					protected void result(Object object) {
						game.getVibrationManager().vibrate(PreferencesManager.VIBRATION_TIME);
						game.getSoundManager().play(FdSound.CLICK);
					}
				}.text(game.getLanguage().getString("options.credits"))
						.button(game.getLanguage().getString("game.ok"), true).show(stage).setFillParent(true);
			}
		});
	}

	@Override
	public void show() {
		batch = new SpriteBatch();
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		stage.dispose();
		batch.dispose();
	}

}

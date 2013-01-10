package lv.janis.skuja.fd.screen;

import lv.janis.skuja.fd.FortressDuelGame;
import lv.janis.skuja.fd.manager.PreferencesManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * @author Janis Skuja
 * 
 */
public class MenuScreen implements Screen {

	private boolean dialogShown = false;

	private FortressDuelGame game;

	private Stage stage;

	private SpriteBatch batch;

	private TextButton btnStart;
	private TextButton btnOptions;
	private TextButton btnHelp;
	private TextButton btnHighScores;

	private Table table;

	public MenuScreen(FortressDuelGame game) {
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

		// Listen for the back key
		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			if (!dialogShown) {
				dialogShown = true;
				new Dialog(game.getLanguage().getString("game.title.exit"), game.getSkin()) {
					protected void result(Object object) {
						game.getVibrationManager().vibrate(PreferencesManager.VIBRATION_TIME);
						if (object.equals(true)) {
							Gdx.app.exit();
						} else {
							dialogShown = false;
						}
					}
				}.text(game.getLanguage().getString("game.exit"))
						.button(game.getLanguage().getString("game.yes"), true)
						.button(game.getLanguage().getString("game.no"), false).show(stage);
			}
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

		createTable();

		// Add actors (buttons, labels, table etc. to stage)
		stage.addActor(table);
	}

	private void createTable() {
		// Create and add actors to table
		table = new Table(game.getSkin());
		table.center();
		table.defaults().height(50).width(300).pad(10);
		table.setFillParent(true);
		table.add(game.getLanguage().getString("game.title"));
		table.row();
		table.add(btnStart);
		table.row();
		table.add(btnHighScores);
		table.row();
		table.add(btnOptions);
		table.row();
		table.add(btnHelp);
	}

	private void createButtons() {

		// Create buttons and place them on screen
		// Start game button
		btnStart = new TextButton(game.getLanguage().getString("menu.play"), game.getSkin());
		// Options button
		btnOptions = new TextButton(game.getLanguage().getString("menu.options"), game.getSkin());
		// High-scores button
		btnHighScores = new TextButton(game.getLanguage().getString("menu.high.scores"), game.getSkin());
		// Help button
		btnHelp = new TextButton(game.getLanguage().getString("menu.help"), game.getSkin());

		// add listeners to buttons
		// Start game button listener
		btnStart.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.getVibrationManager().vibrate(PreferencesManager.VIBRATION_TIME);
				game.setScreen(new GameScreen(game));
			}
		});
		// Options button listener
		btnOptions.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.getVibrationManager().vibrate(PreferencesManager.VIBRATION_TIME);
				game.setScreen(new OptionsScreen(game));
			}
		});
		// High-scores button listener
		btnHighScores.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.getVibrationManager().vibrate(PreferencesManager.VIBRATION_TIME);
				game.setScreen(new HighScoresSreen(game));
			}
		});
		// Help button listener
		btnHelp.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.getVibrationManager().vibrate(PreferencesManager.VIBRATION_TIME);
				game.setScreen(new HelpScreen(game));
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
		batch.dispose();
		stage.dispose();
	}

}

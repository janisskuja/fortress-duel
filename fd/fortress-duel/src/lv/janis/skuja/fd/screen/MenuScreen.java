package lv.janis.skuja.fd.screen;

import lv.janis.skuja.fd.FortressDuelGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

/**
 * @author Janis Skuja
 * 
 */
public class MenuScreen implements Screen {

	private static final float BTN_HEIGHT = 50;

	private static final float BTN_WIDTH = 300;

	private static final float RIGHT_PADDING = 50;

	private FortressDuelGame game;

	private Stage stage;

	private SpriteBatch batch;

	private TextButton btnStart;
	private TextButton btnOptions;
	private TextButton btnHelp;
	private TextButton btnHighScores;

	private Label lblTitle;

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
	}

	@Override
	public void resize(int width, int height) {
		if (stage == null)
			stage = new Stage(width, height, true);
		stage.clear();

		Gdx.input.setInputProcessor(stage);

		TextButtonStyle btnStyle = new TextButtonStyle();
		btnStyle.up = game.getSkin().getDrawable("buttonnormal");
		btnStyle.down = game.getSkin().getDrawable("buttonpressed");
		btnStyle.font = game.getFontArial30();
		btnStyle.fontColor = new Color(1, 0, 0, 1);

		// create buttons and place them on screen
		// TODO put all buttons in array and do placement logic in a loop
		// Start game button
		btnStart = new TextButton(game.getLanguage().getString("menu.play"),
				btnStyle);
		btnStart.setWidth(BTN_WIDTH);
		btnStart.setHeight(BTN_HEIGHT);
		btnStart.setX(Gdx.graphics.getWidth() - BTN_WIDTH - RIGHT_PADDING);
		btnStart.setY(Gdx.graphics.getHeight() - (BTN_HEIGHT * 2)
				- (BTN_HEIGHT * 0) - 20 - btnStart.getHeight());

		// Options button
		btnOptions = new TextButton(game.getLanguage()
				.getString("menu.options"), btnStyle);
		btnOptions.setWidth(BTN_WIDTH);
		btnOptions.setHeight(BTN_HEIGHT);
		btnOptions.setX(Gdx.graphics.getWidth() - BTN_WIDTH - RIGHT_PADDING);
		btnOptions.setY(Gdx.graphics.getHeight() - (BTN_HEIGHT * 2)
				- (BTN_HEIGHT * 1) - 20 - btnOptions.getHeight());

		// High-scores button
		btnHighScores = new TextButton(game.getLanguage().getString(
				"menu.high.scores"), btnStyle);
		btnHighScores.setWidth(BTN_WIDTH);
		btnHighScores.setHeight(BTN_HEIGHT);
		btnHighScores.setX(Gdx.graphics.getWidth() - BTN_WIDTH - RIGHT_PADDING);
		btnHighScores.setY(Gdx.graphics.getHeight() - (BTN_HEIGHT * 2)
				- (BTN_HEIGHT * 2) - 20 - btnOptions.getHeight());

		// Help button
		btnHelp = new TextButton(game.getLanguage().getString("menu.help"),
				btnStyle);
		btnHelp.setWidth(BTN_WIDTH);
		btnHelp.setHeight(BTN_HEIGHT);
		btnHelp.setX(Gdx.graphics.getWidth() - BTN_WIDTH - RIGHT_PADDING);
		btnHelp.setY(Gdx.graphics.getHeight() - (BTN_HEIGHT * 2)
				- (BTN_HEIGHT * 3) - 20 - btnHelp.getHeight());

		// add listeners to buttons
		// TODO: add listeners in loop
		// Start game button listener
		btnStart.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new GameScreen(game));
			}
		});

		// Options button listener
		btnOptions.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new OptionsScreen(game));
			}
		});

		// High-scores button listener
		btnHighScores.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new HighScoresSreen(game));
			}
		});

		// Help button listener
		btnHelp.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new HelpScreen(game));
			}
		});

		// add actors (buttons, labels etc. to stage)
		stage.addActor(btnStart);
		stage.addActor(btnOptions);
		stage.addActor(btnHighScores);
		stage.addActor(btnHelp);
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

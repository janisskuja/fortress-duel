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
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * @author Janis Skuja
 * 
 */
public class SplashScreen implements Screen {
	private FortressDuelGame game;
	private SpriteBatch batch;
	private Label lblClickScreen;
	private Stage stage;

	public SplashScreen(FortressDuelGame game) {
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

		LabelStyle labelStyle = new LabelStyle(game.getFontArial30(), Color.RED);

		lblClickScreen = new Label(game.getLanguage().getString(
				"splash.click.screen"), labelStyle);
		lblClickScreen.setX(0);
		lblClickScreen.setY(Gdx.graphics.getHeight() / 2
				- game.getFontArial30().getXHeight());
		lblClickScreen.setWidth(width);
		lblClickScreen.setAlignment(Align.center);

		stage.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				game.setScreen(new MenuScreen(game));
			}
		});

		stage.addActor(lblClickScreen);

	}

	@Override
	public void show() {
		batch = new SpriteBatch();
	}

	@Override
	public void hide() {

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
	}

}

package lv.janis.skuja.fd.screen;

import java.util.ResourceBundle;

import lv.janis.skuja.fd.FortressDuelGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class SplashScreen implements Screen {
	private FortressDuelGame game;
	private SpriteBatch batch;
	private BitmapFont white;
	private Label lblClickScreen;
	private Stage stage;
	private ResourceBundle language;

	public SplashScreen(FortressDuelGame game) {
		this.game = game;
		this.language = game.getBuilder().build();
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

		LabelStyle ls = new LabelStyle(white, Color.RED);

		lblClickScreen = new Label(language.getString("splash.click.screen"),
				ls);
		lblClickScreen.setX(0);
		lblClickScreen.setY(Gdx.graphics.getHeight() / 4 - white.getXHeight()
				* 4);
		lblClickScreen.setWidth(width);
		lblClickScreen.setAlignment(Align.center);

		stage.addActor(lblClickScreen);

	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		white = new BitmapFont(Gdx.files.internal("font/arial.fnt"), false);
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

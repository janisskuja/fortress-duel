package lv.janis.skuja.fd.screen;

import lv.janis.skuja.fd.FortressDuelGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * @author Janis Skuja
 */
public class HelpScreen implements Screen {

	private FortressDuelGame game;
	private Stage stage;
	private SpriteBatch batch;

	private Table table;

	public HelpScreen(FortressDuelGame game) {
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

		createTable();

		stage.addActor(table);
	}

	private void createTable() {

		table = new Table(game.getSkin());
		table.defaults().width(150).height(50).pad(10);
		table.setFillParent(true);
		table.add(game.getLanguage().getString("menu.help")).center().height(100);
		table.row();
		table.add(game.getLanguage().getString("help.text")).right();
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

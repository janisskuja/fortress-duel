package lv.janis.skuja.fd.screen;

import java.util.Locale;

import lv.janis.skuja.fd.FortressDuelGame;
import lv.janis.skuja.fd.FortressDuelPreferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class OptionsScreen implements Screen {

	private FortressDuelGame game;

	private Stage stage;

	private SpriteBatch batch;

	private TextButton btnCredits;
	private TextButton btnLangLv;
	private TextButton btnLangEn;
	private TextButton btnLangRu;

	private Label lblSound;
	private Label lblMusic;
	private Label lblVibration;
	private Label lblLanguage;

	private Label lblTitle;

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
	}

	@Override
	public void resize(int width, int height) {
		if (stage == null)
			stage = new Stage(width, height, true);
		stage.clear();

		Gdx.input.setInputProcessor(stage);

		// Labels
		LabelStyle labelStyle = new LabelStyle(game.getFontArial30(),
				Color.WHITE);

		lblSound = new Label(game.getLanguage().getString("options.sound"),
				labelStyle);
		lblMusic = new Label(game.getLanguage().getString("options.music"),
				labelStyle);
		lblVibration = new Label(game.getLanguage().getString(
				"options.vibration"), labelStyle);
		lblLanguage = new Label(game.getLanguage()
				.getString("options.language"), labelStyle);

		lblTitle = new Label(game.getLanguage().getString("menu.options"),
				labelStyle);

		// Buttons
		TextButtonStyle btnStyle = new TextButtonStyle();
		btnStyle.up = game.getSkin().getDrawable("buttonnormal");
		btnStyle.down = game.getSkin().getDrawable("buttonpressed");
		btnStyle.font = game.getFontArial30();
		btnStyle.fontColor = new Color(0, 0, 0, 1);

		TextButtonStyle btnStyleSelected = new TextButtonStyle();
		btnStyleSelected.up = game.getSkin().getDrawable("buttonnormal");
		btnStyleSelected.down = game.getSkin().getDrawable("buttonpressed");
		btnStyleSelected.font = game.getFontArial30();
		btnStyleSelected.fontColor = new Color(1, 0, 0, 1);

		btnLangEn = new TextButton(game.getLanguage().getString(
				"options.language.en"), (Locale.getDefault().equals(
				FortressDuelPreferences.LOCALE_ENGLISH) ? btnStyleSelected
				: btnStyle));
		btnLangLv = new TextButton(game.getLanguage().getString(
				"options.language.lv"), (Locale.getDefault().equals(
				FortressDuelPreferences.LOCALE_LATVIAN) ? btnStyleSelected
				: btnStyle));
		btnLangRu = new TextButton(game.getLanguage().getString(
				"options.language.ru"), (Locale.getDefault().equals(
				FortressDuelPreferences.LOCALE_RUSSIAN) ? btnStyleSelected
				: btnStyle));

		// Checkboxes
		CheckBoxStyle checkBoxStyle = new CheckBoxStyle();

		checkBoxStyle.font = game.getFontArial30();
		checkBoxStyle.fontColor = new Color(1, 0, 0, 1);
		checkBoxStyle.up = game.getSkin().getDrawable("buttonpressed");
		checkBoxStyle.checkboxOn = game.getSkin().getDrawable("buttonnormal");

		cbMusic = new CheckBox(" ", checkBoxStyle);
		cbMusic.setChecked(game.getPreferences().isMusicEnabled());
		cbSound = new CheckBox(" ", checkBoxStyle);
		cbSound.setChecked(game.getPreferences().isSoundEffectsEnabled());
		cbVibration = new CheckBox(" ", checkBoxStyle);
		cbVibration.setChecked(game.getPreferences().isVibrationEnabled());

		// create the table layout
		table = new Table();
		table.defaults().width(150);
		table.setFillParent(true);
		table.add(lblTitle).colspan(4).center().height(100);
		table.row();
		table.add(lblSound).right();
		table.add(cbSound).colspan(3).center();
		table.row();
		table.add(lblMusic).right();
		table.add(cbMusic).colspan(3).center();
		table.row();
		table.add(lblVibration).right();
		table.add(cbVibration).colspan(3).center();
		table.row();
		table.add(lblLanguage).right();
		table.add(btnLangEn).center();
		table.add(btnLangLv).center();
		table.add(btnLangRu).center();

		stage.addActor(table);

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

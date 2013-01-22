package lv.janis.skuja.fd.screen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lv.janis.skuja.fd.FortressDuelGame;
import lv.janis.skuja.fd.manager.MusicManager.FdMusic;
import lv.janis.skuja.fd.manager.PreferencesManager;
import lv.janis.skuja.fd.manager.SoundManager.FdSound;
import lv.janis.skuja.fd.model.Fortress;
import lv.janis.skuja.fd.model.FortressState;
import lv.janis.skuja.fd.model.FortressType;
import lv.janis.skuja.fd.model.Minion;
import lv.janis.skuja.fd.model.MinionState;
import lv.janis.skuja.fd.model.MinionType;
import lv.janis.skuja.fd.utils.MinionComparator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * @author Janis Skuja
 */
public class GameScreen implements Screen {

	private static final int NANO_SECOND = 1000000000;
	private FortressDuelGame game;
	private SpriteBatch batch;
	private Array<Minion> lightMinions;
	private Array<Minion> darkMinions;
	private Fortress lightFortress;
	private Fortress darkFortress;
	private float lastDarkSpawned;
	private boolean gameLost;
	private boolean gameWon;
	private boolean gamePaused;
	private Sprite background;

	private BitmapFont font;
	private boolean dialogShown;

	private Stage stage;

	private long gold;

	private Label lblGold;
	private Label labelHpLight;
	private Label labelHpDark;

	private ImageButton btnKnight;
	private ImageButton btnArcher;

	private Skin gameSkin;

	private Table table;

	public GameScreen(FortressDuelGame game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.3f, 0, 0.3f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		Gdx.gl.glActiveTexture(GL10.GL_TEXTURE0);

		processInput();

		if (gameLost == false && gameWon == false && gamePaused == false) {
			if (TimeUtils.nanoTime() - lastDarkSpawned > (NANO_SECOND / 0.2)) {
				spawnDarkMinion();
				gold += 30;

			}
			lblGold.setText("$" + gold);
			collisionDetection();

			updateMinions();
		} else if (gamePaused == false) {
			if (gameLost) {
				gamePaused = true;
				showLostDialog();
			} else if (gameWon) {
				gamePaused = true;
				showWonDialog();
			}
		}
		labelHpDark.setText("HP: " + darkFortress.getHealth());
		labelHpLight.setText("HP: " + lightFortress.getHealth());

		stage.act();

		batch.begin();
		drawBackGround();
		drawFortresses();
		drawMinions();
		drawUI();
		batch.end();

	}

	private void showWonDialog() {
		new Dialog(game.getLanguage().getString("game.title.game.won"), game.getSkin()) {
			protected void result(Object object) {
				game.getVibrationManager().vibrate(PreferencesManager.VIBRATION_TIME);
				game.getSoundManager().play(FdSound.CLICK);
				game.setScreen(new MenuScreen(game));
			}
		}.text(game.getLanguage().getString("game.won")).button(game.getLanguage().getString("game.ok"), true)
				.show(stage);
	}

	private void showLostDialog() {
		new Dialog(game.getLanguage().getString("game.title.game.lost"), game.getSkin()) {
			protected void result(Object object) {
				game.getVibrationManager().vibrate(PreferencesManager.VIBRATION_TIME);
				game.getSoundManager().play(FdSound.CLICK);
				game.setScreen(new MenuScreen(game));

			}
		}.text(game.getLanguage().getString("game.lost")).button(game.getLanguage().getString("game.ok"), true)
				.show(stage);
	}

	private void drawUI() {
		// TODO: this is a workaround for scene to work with sprites
		font.draw(batch, "" + gold, 10, 900);
		stage.draw();
	}

	private void processInput() {
		// Listen for the back key
		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			gamePaused = true;
			if (!dialogShown) {
				dialogShown = true;
				new Dialog(game.getLanguage().getString("game.title.exit"), game.getSkin()) {
					protected void result(Object object) {
						game.getVibrationManager().vibrate(PreferencesManager.VIBRATION_TIME);
						game.getSoundManager().play(FdSound.CLICK);
						if (object.equals(true)) {
							game.setScreen(new MenuScreen(game));
						} else {
							dialogShown = false;
							gamePaused = false;
						}
					}
				}.text(game.getLanguage().getString("game.exit"))
						.button(game.getLanguage().getString("game.yes"), true)
						.button(game.getLanguage().getString("game.no"), false).show(stage);
			}
		}
	}

	private void drawBackGround() {
		background.draw(batch);
	}

	private void spawnDarkMinion() {
		darkMinions.add(new Minion(MinionType.DARK_KNIGHT));
		lastDarkSpawned = TimeUtils.nanoTime();
	}

	private void collisionDetection() {
		for (Minion lightMinion : lightMinions) {
			for (Minion darkMinion : darkMinions) {

				// if minions overlap

				if (darkMinion.getRectangle().overlaps(lightMinion.getRectangle())) {

					if (lightMinion.getMinionState() != MinionState.DIEING
							&& darkMinion.getMinionState() != MinionState.DIEING) {
						darkMinion.setMinionState(MinionState.ATACKING);
						lightMinion.setMinionState(MinionState.ATACKING);
						if (TimeUtils.nanoTime() - lightMinion.getLastAttackTime() > NANO_SECOND / 1.4f
								&& lightMinion.getMinionState() != MinionState.DIEING) {
							game.getSoundManager().play(lightMinion.getSound());
							darkMinion.setHealth(darkMinion.getHealth() - lightMinion.getAttack());
							darkMinion.getAnimatedSprite().setColor(Color.RED);
							if (darkMinion.getHealth() <= 0) {
								gold += 100;
								darkMinion.setMinionState(MinionState.DIEING);
							}
							lightMinion.setLastAttackTime(TimeUtils.nanoTime());
						}
						if (TimeUtils.nanoTime() - darkMinion.getLastAttackTime() > NANO_SECOND / 1.4f
								&& darkMinion.getMinionState() != MinionState.DIEING) {
							game.getSoundManager().play(darkMinion.getSound());
							lightMinion.setHealth(lightMinion.getHealth() - darkMinion.getAttack());
							lightMinion.getAnimatedSprite().setColor(Color.RED);
							if (lightMinion.getHealth() <= 0) {
								lightMinion.setMinionState(MinionState.DIEING);
							}
							darkMinion.setLastAttackTime(TimeUtils.nanoTime());
						}
					} else {
						if (lightMinion.getMinionState() != MinionState.DIEING
								&& lightMinion.getMinionState() != MinionState.WALKING) {
							lightMinion.setMinionState(MinionState.WALKING);
						}
						if (darkMinion.getMinionState() != MinionState.DIEING
								&& darkMinion.getMinionState() != MinionState.WALKING) {
							darkMinion.setMinionState(MinionState.WALKING);
						}
					}
				} else {
					// if overlaps with fortress
					if (darkMinion.getRectangle().overlaps(lightFortress.getRectangle())
							&& darkMinion.getMinionState() != MinionState.DIEING) {
						darkMinion.setMinionState(MinionState.ATACKING);
						if (TimeUtils.nanoTime() - darkMinion.getLastAttackTime() > NANO_SECOND / 1.4f
								&& darkMinion.getMinionState() != MinionState.DIEING) {
							game.getSoundManager().play(darkMinion.getSound());
							if (lightFortress.getHealth() > 0) {
								lightFortress.setHealth(lightFortress.getHealth() - darkMinion.getAttack());
								lightFortress.getFortressSprite().setColor(Color.RED);
							} else {
								lightFortress.setFortressState(FortressState.DEAD);
								this.gameLost = true;
							}
							darkMinion.setLastAttackTime(TimeUtils.nanoTime());
						}
					}

					if (lightMinion.getRectangle().overlaps(darkFortress.getRectangle())
							&& lightMinion.getMinionState() != MinionState.DIEING) {
						lightMinion.setMinionState(MinionState.ATACKING);
						if (TimeUtils.nanoTime() - lightMinion.getLastAttackTime() > NANO_SECOND / 1.4f
								&& lightMinion.getMinionState() != MinionState.DIEING) {
							game.getSoundManager().play(lightMinion.getSound());
							if (darkFortress.getHealth() > 0) {
								darkFortress.setHealth(darkFortress.getHealth() - lightMinion.getAttack());
								darkFortress.getFortressSprite().setColor(Color.RED);
							} else {
								darkFortress.setFortressState(FortressState.DEAD);
								this.gameWon = true;
							}
							lightMinion.setLastAttackTime(TimeUtils.nanoTime());
						}
					}
				}
			}

		}

	}

	private void updateMinions() {
		for (Minion darkMinion : darkMinions) {
			darkMinion.update(Gdx.graphics.getDeltaTime());
		}
		for (Minion lightMinion : lightMinions) {
			lightMinion.update(Gdx.graphics.getDeltaTime());
		}
	}

	private void drawMinions() {
		List<Minion> allMinions = getAllSortedMinions();
		for (Minion minion : allMinions) {
			minion.getAnimatedSprite().draw(batch);
		}
	}

	private List<Minion> getAllSortedMinions() {
		List<Minion> result = new ArrayList<Minion>();
		for (Minion minion : lightMinions) {
			result.add(minion);
		}
		for (Minion minion : darkMinions) {
			result.add(minion);
		}
		Collections.sort(result, new MinionComparator());
		return result;
	}

	@Override
	public void resize(int width, int height) {
		if (stage == null)
			stage = new Stage(width, height, true);
		stage.clear();
		Gdx.input.setInputProcessor(stage);

		createButtons();
		createLabels();
		createTable();

		stage.addActor(labelHpDark);
		stage.addActor(labelHpLight);
		stage.addActor(table);

	}

	private void createLabels() {
		labelHpDark = new Label("", game.getSkin());
		labelHpLight = new Label("", game.getSkin());
		lblGold = new Label("$" + gold, game.getSkin());
		lblGold.setColor(Color.RED);
		labelHpDark.setColor(Color.RED);
		labelHpLight.setColor(Color.RED);
		labelHpDark.setPosition(600, 400);
		labelHpLight.setPosition(50, 400);
	}

	private void createTable() {

		table = new Table(game.getSkin());
		table.defaults().width(50).height(50).pad(20);
		table.setFillParent(true);

		table.add(lblGold);
		table.add(btnKnight);
		table.add(btnArcher);

		table.top().left();
	}

	private void createButtons() {

		btnKnight = new ImageButton(gameSkin.getDrawable("k_walk"));
		btnArcher = new ImageButton(gameSkin.getDrawable("a_walk"));

		btnKnight.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.getVibrationManager().vibrate(PreferencesManager.VIBRATION_TIME);

				game.getSoundManager().play(FdSound.CLICK);

				spawnKnight();
			}

		});
		btnArcher.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.getVibrationManager().vibrate(PreferencesManager.VIBRATION_TIME);

				game.getSoundManager().play(FdSound.CLICK);

				spawnArcher();

			}
		});

	}

	protected void spawnArcher() {

	}

	protected void spawnKnight() {
		if (gold >= 90) {
			gold -= 90;
			lightMinions.add(new Minion(MinionType.LIGHT_KNIGHT));
		}
	}

	@Override
	public void show() {

		game.getMusicManager().play(FdMusic.LEVEL);

		font = new BitmapFont(Gdx.files.internal("data/default.fnt"), Gdx.files.internal("data/default_0.png"), false);

		TextureAtlas atlas = new TextureAtlas("sprites/lightpack.pack");
		gameSkin = new Skin();
		gameSkin.addRegions(atlas);

		background = new Sprite(new Texture("texture/bg.png"));
		batch = new SpriteBatch();
		lightMinions = new Array<Minion>();
		darkMinions = new Array<Minion>();

		lightFortress = new Fortress(FortressType.LIGHT_FORTRESS);
		darkFortress = new Fortress(FortressType.DARK_FORTRESS);

	}

	private void drawFortresses() {
		lightFortress.getFortressSprite().draw(batch);
		darkFortress.getFortressSprite().draw(batch);
	}

	@Override
	public void hide() {
		dispose();

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		batch.dispose();
	}

}

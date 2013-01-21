package lv.janis.skuja.fd.screen;

import lv.janis.skuja.fd.FortressDuelGame;
import lv.janis.skuja.fd.manager.MusicManager.FdMusic;
import lv.janis.skuja.fd.manager.PreferencesManager;
import lv.janis.skuja.fd.manager.SoundManager.FdSound;
import lv.janis.skuja.fd.model.Fortress;
import lv.janis.skuja.fd.model.FortressSide;
import lv.janis.skuja.fd.model.Minion;
import lv.janis.skuja.fd.model.MinionType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * @author Janis Skuja
 */
public class GameScreen implements Screen {

	private static final float MOVEMENT_SPEED = 100 * Gdx.graphics.getDeltaTime();
	private static final long SECOND = 1000000000;
	private FortressDuelGame game;
	private long gold;
	private Label lblGold;
	private ImageButton btnKnight;
	private ImageButton btnArcher;
	private ImageButton btnWizard;
	private TextButton btnRight;
	private TextButton btnLeft;

	private boolean dialogShown = false;

	private Skin gameSkin;
	private Stage stage;
	private SpriteBatch batch;

	private long lastIncomeTime;
	private long lastEnemySpawn;

	private OrthographicCamera camera;
	private Table table;

	private Fortress fortressGood;
	private Fortress fortressBad;

	private Texture background;
	private Texture background2;
	Image image;

	private Array<Minion> allies;
	private Array<Minion> enemies;

	public GameScreen(FortressDuelGame game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor((float) 0.3, 0, (float) 0.3, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		Gdx.gl.glActiveTexture(GL10.GL_TEXTURE0);
		stage.act(delta);
		batch.begin();

		stage.draw();
		batch.end();

		if (TimeUtils.nanoTime() - lastIncomeTime >= SECOND) {
			gold += 10;
			lblGold.setText("$" + gold);
			lastIncomeTime = TimeUtils.nanoTime();
		}

		if (TimeUtils.nanoTime() - lastEnemySpawn >= 3 * SECOND) {
			lastEnemySpawn = TimeUtils.nanoTime();
			int random = MathUtils.random(1, 3);
			switch (random) {
			case 1:
				spawnKnight(false);
				break;
			case 2:
				spawnArcher(false);
				break;
			case 3:
				spawnWizard(false);
				break;
			default:
				break;
			}
		}

		for (Minion ally : allies) {
			for (Minion enemy : enemies) {
				if (!ally.isAttacking()) {
					ally.getImage().setX(ally.getImage().getX() + MOVEMENT_SPEED);
					ally.getRectangle().setX(ally.getImage().getX() + MOVEMENT_SPEED);
				}
				if (!enemy.isAttacking()) {
					enemy.getImage().setX(enemy.getImage().getX() - MOVEMENT_SPEED);
					enemy.getRectangle().setX(enemy.getImage().getX() - MOVEMENT_SPEED);
				}
				if (ally.getRectangle().overlaps(enemy.getRectangle()) && !ally.isDead() && !enemy.isDead()) {
					ally.setAttacking(true);
					enemy.setAttacking(true);
					enemy.setHealth(enemy.getHealth() - ally.getType().getAttack());
					if (enemy.getHealth() <= 0) {
						enemy.setDead(true);
						ally.setAttacking(false);
					}

					ally.setHealth(ally.getHealth() - enemy.getType().getAttack());
					if (ally.getHealth() <= 0) {
						ally.setDead(true);
						enemy.setAttacking(false);
					}

				}
			}
		}

		for (Minion ally : allies) {
			if (!ally.isAttacking()) {
				float X = ally.getImage().getX();
				ally.getImage().setX(X + MOVEMENT_SPEED);
				ally.getRectangle().setX(X + MOVEMENT_SPEED);
			}
		}

		for (Minion enemy : enemies) {
			if (!enemy.isAttacking()) {
				float X = enemy.getImage().getX();
				enemy.getImage().setX(X - MOVEMENT_SPEED);
				enemy.getRectangle().setX(X - MOVEMENT_SPEED);
			}
		}

		for (Minion ally : allies) {
			for (Minion enemy : enemies) {

				if (ally.getRectangle().overlaps(enemy.getRectangle()) && !ally.isDead() && !enemy.isDead()) {
					ally.setAttacking(true);
					enemy.setAttacking(true);
					enemy.setHealth(enemy.getHealth() - ally.getType().getAttack());
					if (enemy.getHealth() <= 0) {
						enemy.setDead(true);
						ally.setAttacking(false);
					}

					ally.setHealth(ally.getHealth() - enemy.getType().getAttack());
					if (ally.getHealth() <= 0) {
						ally.setDead(true);
						enemy.setAttacking(false);
					}

				}
			}
		}

		// Listen for the back key
		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.ESCAPE)) {
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

		createStageAndCamera(width, height);
	}

	private void createStageAndCamera(int width, int height) {
		if (camera == null)
			camera = new OrthographicCamera();
		camera.setToOrtho(false, width, height);
		if (stage == null)
			stage = new Stage(width, height, true);
		stage.clear();
		Gdx.input.setInputProcessor(stage);

		stage.setCamera(camera);
		stage.getCamera().update();
		stage.setViewport(width, height, true);

		createButtons();
		createLabels();
		createTable();

		stage.addActor(new Image(background));
		stage.addActor(new Image(background2));
		stage.addActor(fortressGood.getImage());
		stage.addActor(fortressBad.getImage());
		stage.addActor(table);
	}

	private void createLabels() {
		lblGold = new Label("$" + gold, game.getSkin());

		// lblGold.setPosition(Gdx.graphics.getWidth() - lblGold.getWidth() -
		// 300,
		// Gdx.graphics.getHeight() - lblGold.getHeight());
	}

	private void createTable() {

		table = new Table(game.getSkin());
		table.defaults().width(50).height(50).pad(20);
		table.setFillParent(true);
		// table.add(btnLeft);
		table.add(lblGold);
		table.add(btnKnight);
		table.add(btnArcher);
		table.add(btnWizard);
		// table.add(btnRight);
		// table.setPosition(stage.getCamera().position.x,
		// stage.getCamera().position.y - Gdx.graphics.getHeight() / 2);
		table.top().left();
	}

	private void createButtons() {

		btnKnight = new ImageButton(gameSkin.getDrawable("knight_good"));
		btnArcher = new ImageButton(gameSkin.getDrawable("archer_good"));
		btnWizard = new ImageButton(gameSkin.getDrawable("wizard_good"));

		btnRight = new TextButton("-->", game.getSkin());
		btnLeft = new TextButton("<--", game.getSkin());

		btnKnight.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.getVibrationManager().vibrate(PreferencesManager.VIBRATION_TIME);

				game.getSoundManager().play(FdSound.CLICK);

				spawnKnight(true);
			}

		});
		btnArcher.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.getVibrationManager().vibrate(PreferencesManager.VIBRATION_TIME);

				game.getSoundManager().play(FdSound.CLICK);

				spawnArcher(true);

			}
		});
		btnWizard.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				game.getVibrationManager().vibrate(PreferencesManager.VIBRATION_TIME);

				game.getSoundManager().play(FdSound.CLICK);

				spawnWizard(true);
			}
		});

		btnRight.addListener(new InputListener() {

			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				moveCameraRight();
				return true;
			}

		});

		btnLeft.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				moveCameraLeft();
				return true;
			}
		});

	}

	protected void spawnWizard(boolean isAlly) {
		Minion minion;
		if (isAlly) {
			minion = new Minion(MinionType.ALLY_KNIGHT, TimeUtils.nanoTime(), gameSkin.getDrawable("wizard_good"),
					gameSkin.getDrawable("arrow"));
			allies.add(minion);

		} else {
			minion = new Minion(MinionType.ENEMY_KNIGHT, TimeUtils.nanoTime(), gameSkin.getDrawable("wizard_bad"),
					gameSkin.getDrawable("arrow"));
			enemies.add(minion);
		}
		stage.addActor(minion.getImage());
	}

	protected void spawnArcher(boolean isAlly) {
		Minion minion;
		if (isAlly) {
			minion = new Minion(MinionType.ALLY_KNIGHT, TimeUtils.nanoTime(), gameSkin.getDrawable("archer_good"),
					gameSkin.getDrawable("arrow"));
			allies.add(minion);

		} else {
			minion = new Minion(MinionType.ENEMY_KNIGHT, TimeUtils.nanoTime(), gameSkin.getDrawable("archer_bad"),
					gameSkin.getDrawable("arrow"));
			enemies.add(minion);
		}
		stage.addActor(minion.getImage());
	}

	protected void spawnKnight(boolean isAlly) {
		Minion minion;
		if (isAlly) {
			minion = new Minion(MinionType.ALLY_KNIGHT, TimeUtils.nanoTime(), gameSkin.getDrawable("knight_good"),
					gameSkin.getDrawable("arrow"));
			allies.add(minion);

		} else {
			minion = new Minion(MinionType.ENEMY_KNIGHT, TimeUtils.nanoTime(), gameSkin.getDrawable("knight_bad"),
					gameSkin.getDrawable("arrow"));
			enemies.add(minion);
		}
		stage.addActor(minion.getImage());
	}

	protected void moveCameraLeft() {
		stage.getCamera().translate(-20, 0, 0);
		table.translate(-20, 0);
		stage.getCamera().update();
	}

	private void moveCameraRight() {
		stage.getCamera().translate(20, 0, 0);
		table.translate(20, 0);
		stage.getCamera().update();
		Vector2 vector = new Vector2(stage.getCamera().position.x, stage.getCamera().position.y);
		stage.stageToScreenCoordinates(vector);
	}

	@Override
	public void show() {
		allies = new Array<Minion>();
		enemies = new Array<Minion>();

		background = new Texture("texture/bg.png");
		background2 = new Texture("texture/bg2.png");
		TextureAtlas atlas = new TextureAtlas("texture/patagonists.pack");
		gameSkin = new Skin();
		gameSkin.addRegions(atlas);

		fortressGood = new Fortress(FortressSide.GOOD, 1000, gameSkin.getDrawable("fortressGood"));
		fortressBad = new Fortress(FortressSide.BAD, 1000, gameSkin.getDrawable("fortressBad"));

		batch = new SpriteBatch();
		game.getMusicManager().play(FdMusic.LEVEL);
		gold = 500;
		lastIncomeTime = TimeUtils.nanoTime();

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
		stage.dispose();
		batch.dispose();
	}

}

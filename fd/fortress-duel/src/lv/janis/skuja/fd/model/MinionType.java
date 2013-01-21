package lv.janis.skuja.fd.model;

/**
 * @author Janis Skuja
 */
public enum MinionType {
	ALLY_KNIGHT(false, 100, 50, 5, 3), ALLY_WIZARD(false, 300, 30, 10, 3), ALLY_ARCHED(false, 200, 40, 4, 2), ENEMY_KNIGHT(
			true, 0, 50, 4, 3), ENEMY_WIZARD(true, 0, 30, 9, 3), ENEMY_ARCHER(true, 0, 30, 3, 2);

	private final boolean enemy;
	private final int price;
	private final int health;
	private final int attack;
	private final int speed;

	private MinionType(boolean enemy, int price, int health, int attack, int speed) {
		this.enemy = enemy;
		this.price = price;
		this.health = health;
		this.attack = attack;
		this.speed = speed;
	}

	public boolean isEnemy() {
		return enemy;
	}

	public int getPrice() {
		return price;
	}

	public int getHealth() {
		return health;
	}

	public int getAttack() {
		return attack;
	}

	public int getSpeed() {
		return speed;
	}

}

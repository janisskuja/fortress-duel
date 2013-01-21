package lv.janis.skuja.fd.model;

public enum FortressSide {
	GOOD(1, "goodFortress"), BAD(2, "badFortress");

	private final int id;
	private final String name;

	private FortressSide(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}

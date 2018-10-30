package ar.edu.itba.pod.hazelcaster.abstractions;

public enum MovementType {

	TAKE_OFF(0, "Despegue"),
	LANDING(1, "Aterrizaje");

	private final int id;
	private final String value;

	private MovementType(final int id, final String value) {
		this.id = id;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public int getID() {
		return id;
	}

	public static MovementType fromID(final int id) {
		switch (id) {
			case 0 : return TAKE_OFF;
			case 1 : return LANDING;
			default: {
				throw new IllegalArgumentException(
					"El identificador es desconocido (id = " + id + ").");
			}
		}
	}

	public static MovementType fromString(final String value) {
		switch (value) {
			case "Despegue" : return TAKE_OFF;
			case "Aterrizaje" : return LANDING;
			default: {
				throw new IllegalArgumentException(
					"La cadena es desconocida (value = " + value + ").");
			}
		}
	}
}

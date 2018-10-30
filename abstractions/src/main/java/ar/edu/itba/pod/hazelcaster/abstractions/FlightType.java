package ar.edu.itba.pod.hazelcaster.abstractions;

public enum FlightType {

	CABOTAGE(0, "Cabotaje"),
	INTERNATIONAL(1, "Internacional"),
	NOT_APPLIABLE(2, "N/A");

	private final int id;
	private final String value;

	private FlightType(final int id, final String value) {
		this.id = id;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public int getID() {
		return id;
	}

	public static FlightType fromID(final int id) {
		switch (id) {
			case 0 : return CABOTAGE;
			case 1 : return INTERNATIONAL;
			case 2 : return NOT_APPLIABLE;
			default: {
				throw new IllegalArgumentException(
					"El identificador es desconocido (id = " + id + ").");
			}
		}
	}

	public static FlightType fromString(final String value) {
		switch (value) {
			case "Cabotaje" : return CABOTAGE;
			case "Internacional" : return INTERNATIONAL;
			case "N/A" : return NOT_APPLIABLE;
			default: {
				throw new IllegalArgumentException(
					"La cadena es desconocida (value = " + value + ").");
			}
		}
	}
}

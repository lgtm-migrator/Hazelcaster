package ar.edu.itba.pod.hazelcaster.backend.field;

public enum MovementField {

	/*
	* NOTA: el orden importa, ya que este Enum está pensado para ser accedido a
	*	través del método Enum#ordinal().
	*/

	FECHA,
	HORA_MOVIMIENTO_HHMM,
	CLASE_DE_VUELO,
	CLASIFICACION_VUELO,
	TIPO_DE_MOVIMIENTO,
	ORIGEN_OACI,
	DESTINO_OACI,
	AEROLINEA_NOMBRE,
	AERONAVE,
	AERONAVE_APC;
}

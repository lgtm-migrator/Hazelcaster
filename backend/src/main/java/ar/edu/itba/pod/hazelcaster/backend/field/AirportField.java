package ar.edu.itba.pod.hazelcaster.backend.field;

public enum AirportField {

	/*
	* NOTA: el orden importa, ya que este Enum está pensado para ser accedido a
	*	través del método Enum#ordinal().
	*/

	LOCAL,
	OACI,
	IATA,
	TIPO,
	DENOMINACION,
	COORDENADAS,
	LATITUD,
	LONGITUD,
	ELEV,
	UOM_ELEV,
	REF,
	DISTANCIA_REF,
	DIRECCION_REF,
	CONDICION,
	CONTROL,
	REGION,
	FIR,
	USO,
	TRAFICO,
	SNA,
	CONCESIONADO,
	PROVINCIA,
	INHAB;
}

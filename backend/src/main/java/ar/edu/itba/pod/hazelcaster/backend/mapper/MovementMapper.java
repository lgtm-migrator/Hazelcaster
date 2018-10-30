package ar.edu.itba.pod.hazelcaster.backend.mapper;

import ar.edu.itba.pod.hazelcaster.abstractions.FlightType;
import ar.edu.itba.pod.hazelcaster.abstractions.Movement;
import ar.edu.itba.pod.hazelcaster.abstractions.MovementType;
import ar.edu.itba.pod.hazelcaster.backend.field.MovementField;
import ar.edu.itba.pod.hazelcaster.interfaces.CSVSerializer;
import ar.edu.itba.pod.hazelcaster.interfaces.MapperService;

public class MovementMapper implements MapperService.Mapper<String, Movement> {

	/**
	* <p>Se espera un registro que provenga de un archivo con la estructura
	* completa, es decir, que puede contener campos irrelevantes.</p>
	*/
	@Override
	public Movement map(final String register) {
		final String [] fields = register.split(CSVSerializer.SEPARATOR);
		return new Movement.Builder()
				.classification(FlightType.fromString(fields[MovementField.CLASIFICACION_VUELO.ordinal()]))
				.type(MovementType.fromString(fields[MovementField.TIPO_DE_MOVIMIENTO.ordinal()]))
				.origin(fields[MovementField.ORIGEN_OACI.ordinal()])
				.destination(fields[MovementField.DESTINO_OACI.ordinal()])
				.build();
	}

	@Override
	public String unmap(final Movement object) {
		return new StringBuilder(80)
				.append(object.getClassification().getValue()).append(CSVSerializer.SEPARATOR)
				.append(object.getType().getValue()).append(CSVSerializer.SEPARATOR)
				.append(object.getOrigin()).append(CSVSerializer.SEPARATOR)
				.append(object.getDestination()).append("\n")
				.toString();
	}

	@Override
	public String metadata() {
		return "Clasificación Vuelo;Tipo de Movimiento;Origen OACI;Destino OACI\n";
	}
}

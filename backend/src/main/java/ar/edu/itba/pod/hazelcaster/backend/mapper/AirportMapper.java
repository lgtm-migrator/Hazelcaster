package ar.edu.itba.pod.hazelcaster.backend.mapper;

import ar.edu.itba.pod.hazelcaster.abstractions.Airport;
import ar.edu.itba.pod.hazelcaster.backend.field.AirportField;
import ar.edu.itba.pod.hazelcaster.interfaces.CSVSerializer;
import ar.edu.itba.pod.hazelcaster.interfaces.MapperService;

public class AirportMapper implements MapperService.Mapper<String, Airport> {

	/**
	* <p>Se espera un registro que provenga de un archivo con la estructura
	* completa, es decir, que puede contener campos irrelevantes.</p>
	*/
	@Override
	public Airport map(final String register) {
		final String [] fields = register.split(CSVSerializer.SEPARATOR);
		return new Airport.Builder()
				.oaci(fields[AirportField.OACI.ordinal()])
				.iata(fields[AirportField.IATA.ordinal()])
				.denomination(fields[AirportField.DENOMINACION.ordinal()])
				.province(fields[AirportField.PROVINCIA.ordinal()])
				.build();
	}

	@Override
	public String unmap(final Airport object) {
		return new StringBuilder(80)
				.append(object.getOACI()).append(CSVSerializer.SEPARATOR)
				.append(object.getIATA()).append(CSVSerializer.SEPARATOR)
				.append(object.getDenomination()).append(CSVSerializer.SEPARATOR)
				.append(object.getProvince()).append("\n")
				.toString();
	}

	@Override
	public String metadata() {
		return "oaci;iata;denominacion;provincia\n";
	}
}

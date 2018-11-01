package ar.edu.itba.pod.hazelcaster.backend.mapper;

import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.MovesBetweenAirportsOutput;
import ar.edu.itba.pod.hazelcaster.interfaces.CSVSerializer;
import ar.edu.itba.pod.hazelcaster.interfaces.MapperService;

public class MovesBetweenAirportsOutputMapper implements MapperService.Mapper<String, MovesBetweenAirportsOutput> {

	@Override
	public MovesBetweenAirportsOutput map(String register) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String unmap(MovesBetweenAirportsOutput object) {
		return new StringBuilder(80)
				.append(object.getOaciA()).append(CSVSerializer.SEPARATOR)
				.append(object.getOaciB()).append(CSVSerializer.SEPARATOR)
				.append(object.getCountAB().toString()).append(CSVSerializer.SEPARATOR)
				.append(object.getCountBA().toString()).append("\n")
				.toString();
	}

	@Override
	public String metadata() {
		return "Origen;Destino;Origen->Destino;Destino->Origen\n";
	}
	
}

package ar.edu.itba.pod.hazelcaster.backend.mapper;

import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.SameMovesPairOutput;
import ar.edu.itba.pod.hazelcaster.interfaces.CSVSerializer;
import ar.edu.itba.pod.hazelcaster.interfaces.MapperService;

public class SameMovesPairOutputMapper implements MapperService.Mapper<String, SameMovesPairOutput> {

	@Override
	public SameMovesPairOutput map(String register) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String unmap(SameMovesPairOutput object) {
		return new StringBuilder(80)
				.append(object.getCount()).append(CSVSerializer.SEPARATOR)
				.append(object.getOaciA()).append(CSVSerializer.SEPARATOR)
				.append(object.getOaciB()).append("\n")
				.toString();
	}

	@Override
	public String metadata() {
		return "Grupo;Aeropuerto A;Aeropuerto B\n";
	}

}

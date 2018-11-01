package ar.edu.itba.pod.hazelcaster.backend.mapper;

import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.MovesBetweenProvincesOutput;
import ar.edu.itba.pod.hazelcaster.interfaces.CSVSerializer;
import ar.edu.itba.pod.hazelcaster.interfaces.MapperService;

public class MovesBetweenProvincesOutputMapper implements MapperService.Mapper<String, MovesBetweenProvincesOutput> {

	@Override
	public MovesBetweenProvincesOutput map(String register) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String unmap(MovesBetweenProvincesOutput object) {
		return new StringBuilder(80)
				.append(object.getProvA()).append(CSVSerializer.SEPARATOR)
				.append(object.getProvB()).append(CSVSerializer.SEPARATOR)
				.append(object.getCount().toString()).append("\n")
				.toString();
	}

	@Override
	public String metadata() {
		return "Provincia A;Provincia B;Movimientos\n";
	}

}

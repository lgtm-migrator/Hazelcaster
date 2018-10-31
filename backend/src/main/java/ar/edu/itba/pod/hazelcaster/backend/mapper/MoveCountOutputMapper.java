package ar.edu.itba.pod.hazelcaster.backend.mapper;

import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.MoveCountOutput;
import ar.edu.itba.pod.hazelcaster.interfaces.CSVSerializer;
import ar.edu.itba.pod.hazelcaster.interfaces.MapperService;

public class MoveCountOutputMapper implements MapperService.Mapper<String, MoveCountOutput> {

	@Override
	public MoveCountOutput map(String register) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String unmap(MoveCountOutput object) {
		return new StringBuilder(80)
				.append(object.getOaci()).append(CSVSerializer.SEPARATOR)
				.append(object.getDenomination()).append(CSVSerializer.SEPARATOR)
				.append(object.getCount().toString()).append("\n")
				.toString();
	}

	@Override
	public String metadata() {
		return "OACI;Denominación;Movimientos\n";
	}

}

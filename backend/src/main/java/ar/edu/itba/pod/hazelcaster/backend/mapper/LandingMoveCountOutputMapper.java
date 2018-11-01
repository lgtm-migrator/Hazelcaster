package ar.edu.itba.pod.hazelcaster.backend.mapper;

import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.LandingMoveCountOutput;
import ar.edu.itba.pod.hazelcaster.interfaces.CSVSerializer;
import ar.edu.itba.pod.hazelcaster.interfaces.MapperService;

public class LandingMoveCountOutputMapper implements MapperService.Mapper<String, LandingMoveCountOutput> {

	@Override
	public LandingMoveCountOutput map(String register) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String unmap(LandingMoveCountOutput object) {
		return new StringBuilder(80)
				.append(object.getOaci()).append(CSVSerializer.SEPARATOR)
				.append(object.getCount().toString()).append("\n")
				.toString();
	}

	@Override
	public String metadata() {
		return "OACI;Aterrizajes\n";
	}

}

package ar.edu.itba.pod.hazelcaster.backend.mapper;

import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.InternationalPercentageOutput;
import ar.edu.itba.pod.hazelcaster.interfaces.CSVSerializer;
import ar.edu.itba.pod.hazelcaster.interfaces.MapperService;

public class InternationalPercentageOutputMapper implements MapperService.Mapper<String, InternationalPercentageOutput> {

	@Override
	public InternationalPercentageOutput map(String register) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String unmap(InternationalPercentageOutput object) {
		return new StringBuilder(80)
				.append(object.getIata()).append(CSVSerializer.SEPARATOR)
				.append(object.getPercentage().toString()).append("%\n")
				.toString();
	}

	@Override
	public String metadata() {
		return "IATA;Porcentaje\n";
	}
	
	

}

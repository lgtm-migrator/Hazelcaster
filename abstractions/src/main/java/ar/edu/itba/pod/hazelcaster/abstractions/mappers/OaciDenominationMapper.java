package ar.edu.itba.pod.hazelcaster.abstractions.mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import ar.edu.itba.pod.hazelcaster.abstractions.Airport;

public class OaciDenominationMapper implements Mapper<String, Airport, String, String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2263531137132814497L;

	@Override
	public void map(String key, Airport value, Context<String, String> context) {
		
		if (value.getOACI() != null && !value.getOACI().equals("")) {
			context.emit(value.getOACI(), value.getDenomination());
		}
		
	}

}

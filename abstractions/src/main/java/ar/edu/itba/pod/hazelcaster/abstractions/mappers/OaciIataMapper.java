package ar.edu.itba.pod.hazelcaster.abstractions.mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import ar.edu.itba.pod.hazelcaster.abstractions.Airport;

public class OaciIataMapper implements Mapper<String, Airport, String, String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8291848628833952825L;

	@Override
	public void map(String key, Airport value, Context<String, String> context) {
		
		if (value.getOACI() != null && !value.getOACI().equals("")
				&& value.getIATA() != null && !value.getIATA().equals("")) {
			context.emit(value.getOACI(), value.getIATA());
		}
	}

}

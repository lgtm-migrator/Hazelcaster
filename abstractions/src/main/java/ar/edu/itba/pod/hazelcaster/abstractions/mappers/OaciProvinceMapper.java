package ar.edu.itba.pod.hazelcaster.abstractions.mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import ar.edu.itba.pod.hazelcaster.abstractions.Airport;

public class OaciProvinceMapper implements Mapper<String, Airport, String, String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9120299223040794389L;

	@Override
	public void map(String key, Airport value, Context<String, String> context) {
		
		if (value.getOACI() != null && !value.getOACI().equals("")
				&& value.getProvince() != null && !value.getProvince().equals("")) {
			context.emit(value.getOACI(), value.getProvince());
		}
	}

}

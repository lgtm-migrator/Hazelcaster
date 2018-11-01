package ar.edu.itba.pod.hazelcaster.abstractions.mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import ar.edu.itba.pod.hazelcaster.abstractions.Movement;

public class MovesBetweenAirportsMapper implements Mapper<String, Movement, String, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4894489529750647360L;

	@Override
	public void map(String key, Movement value, Context<String, Long> context) {
		
		context.emit(value.getOrigin() + "_" + value.getDestination(), 1L);
	}

}

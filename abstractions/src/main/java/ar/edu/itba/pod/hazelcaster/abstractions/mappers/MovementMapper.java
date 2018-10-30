package ar.edu.itba.pod.hazelcaster.abstractions.mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import ar.edu.itba.pod.hazelcaster.abstractions.Movement;

public class MovementMapper implements Mapper<String, Movement, Movement, Integer> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6711539623627202448L;

	@Override
	public void map(String key, Movement value, Context<Movement, Integer> context) {
		context.emit(value, 1);
	}
}

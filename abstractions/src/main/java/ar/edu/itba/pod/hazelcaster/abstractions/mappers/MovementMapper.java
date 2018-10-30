package ar.edu.itba.pod.hazelcaster.abstractions.mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import ar.edu.itba.pod.hazelcaster.abstractions.Query1ResultType;

public class MovementMapper implements Mapper<String, Query1ResultType, Query1ResultType, Integer> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6711539623627202448L;

	@Override
	public void map(String key, Query1ResultType value, Context<Query1ResultType, Integer> context) {
		context.emit(value, 1);
	}
}

package ar.edu.itba.pod.hazelcaster.abstractions.mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import ar.edu.itba.pod.hazelcaster.abstractions.LongPair;
import ar.edu.itba.pod.hazelcaster.abstractions.Movement;
import utils.StringPairKeyManager;

public class MovesBetweenAirportsMapper implements Mapper<String, Movement, String, LongPair> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4894489529750647360L;

	@Override
	public void map(String key, Movement value, Context<String, LongPair> context) {
		
		if (value.getDestination().equals(value.getOrigin())) {
			return;
		}
		
		context.emit(
				StringPairKeyManager.getKey(value.getOrigin(), value.getDestination()), 
				new LongPair(1L, 0L));
		context.emit(
				StringPairKeyManager.getKey(value.getDestination(), value.getOrigin()), 
				new LongPair(0L, 1L));
	}

}

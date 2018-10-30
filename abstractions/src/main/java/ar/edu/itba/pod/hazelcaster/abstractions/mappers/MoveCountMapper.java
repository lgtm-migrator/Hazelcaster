package ar.edu.itba.pod.hazelcaster.abstractions.mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import ar.edu.itba.pod.hazelcaster.abstractions.Movement;
import ar.edu.itba.pod.hazelcaster.abstractions.MovementType;

public class MoveCountMapper implements Mapper<String, Movement, String, Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6711539623627202448L;

	@Override
	public void map(String key, Movement value, Context<String, Long> context) {
		
		if (value.getType().equals(MovementType.TAKE_OFF)) {
			context.emit(value.getOrigin(), 1L);
		}
		else {
			context.emit(value.getDestination(), 1L);
		}
	}
}

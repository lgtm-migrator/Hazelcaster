package ar.edu.itba.pod.hazelcaster.abstractions.mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import ar.edu.itba.pod.hazelcaster.abstractions.Movement;
import ar.edu.itba.pod.hazelcaster.abstractions.MovementType;

public class LandingMoveCountMapper implements Mapper<String, Movement, String, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3785036595435224008L;
	
	private final String oaci;
	
	public LandingMoveCountMapper(final String oaci) {
		this.oaci = oaci;
	}

	@Override
	public void map(String key, Movement value, Context<String, Long> context) {
		
		if (value.getType().equals(MovementType.LANDING) 
				&& value.getDestination().equals(oaci)) {
			context.emit(value.getOrigin(), 1L);
		}
	}

}

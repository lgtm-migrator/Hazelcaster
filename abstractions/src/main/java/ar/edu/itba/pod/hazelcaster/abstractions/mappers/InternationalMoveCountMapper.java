package ar.edu.itba.pod.hazelcaster.abstractions.mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import ar.edu.itba.pod.hazelcaster.abstractions.FlightType;
import ar.edu.itba.pod.hazelcaster.abstractions.LongPair;
import ar.edu.itba.pod.hazelcaster.abstractions.Movement;

public class InternationalMoveCountMapper implements Mapper<String, Movement, String, LongPair> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7067067642698503543L;

	@Override
	public void map(String key, Movement value, Context<String, LongPair> context) {
		
		if (value.getDestination().equals(value.getOrigin())) {
			return;
		}
		
		long internationalValue = 0L;
		if (value.getClassification().equals(FlightType.INTERNATIONAL)) {
			internationalValue = 1L;
		}
		
		context.emit(value.getOrigin(), new LongPair(1L,internationalValue));
		context.emit(value.getDestination(), new LongPair(1L, internationalValue));
	}

}

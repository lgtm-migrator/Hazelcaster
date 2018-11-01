package ar.edu.itba.pod.hazelcaster.abstractions.mappers;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.MoveCountOutput;

public class ThousandMovesMapper implements Mapper<String, MoveCountOutput, Long, String> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8042113535216100355L;

	@Override
	public void map(String key, MoveCountOutput value, Context<Long, String> context) {

		if (value.getCount() >= 1000L) {
			context.emit((value.getCount()/1000)*1000, value.getOaci());
		}
	}

}

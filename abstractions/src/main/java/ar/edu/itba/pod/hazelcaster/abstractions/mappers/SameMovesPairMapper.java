package ar.edu.itba.pod.hazelcaster.abstractions.mappers;

import java.util.List;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.SameMovesPairOutput;

public class SameMovesPairMapper implements Mapper<Long, List<String>, Long, SameMovesPairOutput> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2844411929743761562L;

	@Override
	public void map(Long key, List<String> value, Context<Long, SameMovesPairOutput> context) {
		
		int size = value.size();
		if (size >= 2) {
			for (int i = 0; i < size; i++) {
				for (int j = i + 1; j < size; j++) {
					int cmp = value.get(i).compareTo(value.get(j));
					if (cmp < 0) {
						context.emit(key, new SameMovesPairOutput(key, value.get(i), value.get(j)));
					}
					else {
						context.emit(key, new SameMovesPairOutput(key, value.get(j), value.get(i)));
					}
				}
			}
		}
	}
	
}

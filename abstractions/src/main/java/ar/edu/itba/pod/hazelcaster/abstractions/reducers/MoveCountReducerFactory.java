package ar.edu.itba.pod.hazelcaster.abstractions.reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.MoveCountOutput;

public class MoveCountReducerFactory implements ReducerFactory<String, Long, MoveCountOutput> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3431945081279013355L;

	@Override
	public Reducer<Long, MoveCountOutput> newReducer( String key ) {
		return new MovementReducer(key);
	}
	 
	private class MovementReducer extends Reducer<Long, MoveCountOutput> {
		
		private volatile long sum;
		private final String key;
		
		public MovementReducer(final String key) {
			this.key = key;
		}
		
		@Override
		public void beginReduce () {
			sum = 0;
		}
	 
		@Override
		public void reduce(Long value) {
			sum += value;
		}
	 
		@Override
		public MoveCountOutput finalizeReduce() {
			return new MoveCountOutput(key, null, sum);
		}

	}
}

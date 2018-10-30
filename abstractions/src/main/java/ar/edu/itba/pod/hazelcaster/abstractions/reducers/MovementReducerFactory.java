package ar.edu.itba.pod.hazelcaster.abstractions.reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import ar.edu.itba.pod.hazelcaster.abstractions.Movement;

public class MovementReducerFactory implements ReducerFactory<Movement, Integer, Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3431945081279013355L;

	@Override
	public Reducer<Integer, Integer> newReducer( Movement key ) {
		return new MovementReducer();
	}
	 
	private class MovementReducer extends Reducer<Integer, Integer> {
		private volatile int sum;
		
		@Override
		public void beginReduce () {
			sum = 0;
		}
	 
		@Override
		public void reduce(Integer value) {
			sum += value;
		}
	 
		@Override
		public Integer finalizeReduce() {
			return sum;
		}

	}
}

package ar.edu.itba.pod.hazelcaster.abstractions.reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

public class MovesBetweenProvincesReducerFactory implements ReducerFactory<String, Long, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7444615584452949132L;

	@Override
	public Reducer<Long, Long> newReducer(String key) {
		return new MovesBetweenProvincesReducer();
	}
	
	private class MovesBetweenProvincesReducer extends Reducer<Long, Long> {
		
		private volatile long sum;
		
		@Override
		public void beginReduce() {
			sum = 0;
		}
		
		@Override
		public void reduce(Long value) {
			sum += value;
		}
		
		@Override
		public Long finalizeReduce() {
			return sum;
		}
		
	}
	
}

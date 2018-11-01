package ar.edu.itba.pod.hazelcaster.abstractions.reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import ar.edu.itba.pod.hazelcaster.abstractions.LongPair;

public class LongPairMapReducerFactory implements ReducerFactory<String, LongPair, LongPair> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -315492252371909911L;

	@Override
	public Reducer<LongPair, LongPair> newReducer(String key) {

		return new LongPairReducer();
	}
	
	private class LongPairReducer extends Reducer<LongPair, LongPair> {
		
		private volatile long sumA;
		private volatile long sumB;
		
		@Override
		public void beginReduce() {
			sumA = 0;
			sumB = 0;
		}
		
		@Override
		public void reduce(LongPair value) {
			sumA += value.getA();
			sumB += value.getB();
		}
		
		@Override
		public LongPair finalizeReduce() {
			return new LongPair(sumA, sumB);
		}
		
	}

}

package ar.edu.itba.pod.hazelcaster.abstractions.combiners;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

import ar.edu.itba.pod.hazelcaster.abstractions.LongPair;

public class LongPairCombinerFactory 
		implements CombinerFactory<String, LongPair, LongPair> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1331126948134463598L;

	@Override
	public Combiner<LongPair, LongPair> newCombiner(String key) {
		return new LongPairCombiner();
	}
	
	private class LongPairCombiner 
			extends Combiner<LongPair, LongPair> {

		private long sumA = 0;
		private long sumB = 0;
		
		@Override
		public void combine(LongPair value) {
			sumA += value.getA();
			sumB += value.getB();
		}

		@Override
		public LongPair finalizeChunk() {
			return new LongPair(sumA, sumB);
		}
		
		@Override
		public void reset() {
			sumA = 0;
			sumB = 0;
		}
		
	}

}

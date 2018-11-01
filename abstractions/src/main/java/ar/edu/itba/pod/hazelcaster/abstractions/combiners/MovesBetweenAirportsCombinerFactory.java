package ar.edu.itba.pod.hazelcaster.abstractions.combiners;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

import ar.edu.itba.pod.hazelcaster.abstractions.LongPair;

public class MovesBetweenAirportsCombinerFactory 
		implements CombinerFactory<String, LongPair, LongPair> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1331126948134463598L;

	@Override
	public Combiner<LongPair, LongPair> newCombiner(String key) {
		return new MovesBetweenAirportsCombiner();
	}
	
	private class MovesBetweenAirportsCombiner 
			extends Combiner<LongPair, LongPair> {

		private long sumAB = 0;
		private long sumBA = 0;
		
		@Override
		public void combine(LongPair value) {
			sumAB += value.getA();
			sumBA += value.getB();
		}

		@Override
		public LongPair finalizeChunk() {
			return new LongPair(sumAB, sumBA);
		}
		
		@Override
		public void reset() {
			sumAB = 0;
			sumBA = 0;
		}
		
	}

}

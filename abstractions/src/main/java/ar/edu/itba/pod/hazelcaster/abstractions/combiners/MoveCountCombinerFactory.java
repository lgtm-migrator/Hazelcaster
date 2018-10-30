package ar.edu.itba.pod.hazelcaster.abstractions.combiners;

import com.hazelcast.mapreduce.Combiner;
import com.hazelcast.mapreduce.CombinerFactory;

public class MoveCountCombinerFactory implements CombinerFactory<String, Long, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3928420343225703945L;

	@Override
	public Combiner<Long, Long> newCombiner(String key) {
		return new MoveCountCombiner();
	}
	
	private class MoveCountCombiner extends Combiner<Long, Long> {

		private long sum = 0;
		
		@Override
		public void combine(Long value) {
			sum++;
		}

		@Override
		public Long finalizeChunk() {
			return sum;
		}
		
		@Override
		public void reset() {
			sum = 0;
		}
		
	}

}

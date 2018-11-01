package ar.edu.itba.pod.hazelcaster.abstractions.reducers;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import ar.edu.itba.pod.hazelcaster.abstractions.LongPair;
import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.MovesBetweenAirportsOutput;

public class MovesBetweenAirportsReducerFactory implements ReducerFactory<String, LongPair, MovesBetweenAirportsOutput> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -315492252371909911L;

	@Override
	public Reducer<LongPair, MovesBetweenAirportsOutput> newReducer(String key) {

		return new MovesBetweenAirportsReducer(key);
	}
	
	private class MovesBetweenAirportsReducer extends Reducer<LongPair, MovesBetweenAirportsOutput> {
		
		private volatile long sumA;
		private volatile long sumB;
		private final String key;
		
		public MovesBetweenAirportsReducer(final String key) {
			this.key = key;
		}
		
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
		public MovesBetweenAirportsOutput finalizeReduce() {
			String[] oacis = key.split(",");
			String oaciA = oacis[0];
			String oaciB = oacis[1];
			return new MovesBetweenAirportsOutput(oaciA, oaciB, sumA, sumB);
		}
		
	}

}

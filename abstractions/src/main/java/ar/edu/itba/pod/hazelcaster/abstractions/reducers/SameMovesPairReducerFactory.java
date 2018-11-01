package ar.edu.itba.pod.hazelcaster.abstractions.reducers;

import java.util.ArrayList;
import java.util.List;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;


public class SameMovesPairReducerFactory implements ReducerFactory<Long, String, List<String>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1955506959304550611L;

	@Override
	public Reducer<String, List<String>> newReducer(Long key) {
		return new SameMovesReducer();
	}
	
	private class SameMovesReducer extends Reducer<String, List<String>> {

		private List<String> list;
		
		@Override
		public void beginReduce() {
			list = new ArrayList<>();
		}
		
		@Override
		public void reduce(String value) {
			list.add(value);
		}

		@Override
		public List<String> finalizeReduce() {
			return list;
		}
		
	}

}

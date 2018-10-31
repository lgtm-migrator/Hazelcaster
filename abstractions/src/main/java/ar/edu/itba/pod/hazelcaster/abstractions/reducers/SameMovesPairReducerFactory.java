package ar.edu.itba.pod.hazelcaster.abstractions.reducers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hazelcast.mapreduce.Reducer;
import com.hazelcast.mapreduce.ReducerFactory;

import ar.edu.itba.pod.hazelcaster.abstractions.SameMovesList;

public class SameMovesPairReducerFactory implements ReducerFactory<Long, String, SameMovesList> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1955506959304550611L;

	@Override
	public Reducer<String, SameMovesList> newReducer(Long key) {
		return new SameMovesReducer(key);
	}
	
	private class SameMovesReducer extends Reducer<String, SameMovesList> {

		private List<String> list;
		private final Long key;
		
		public SameMovesReducer(final Long key) {
			this.key = key;
		}
		
		@Override
		public void beginReduce() {
			list = Collections.synchronizedList(new ArrayList<>());
		}
		
		@Override
		public void reduce(String value) {
			list.add(value);
		}

		@Override
		public SameMovesList finalizeReduce() {
			Collections.sort(list);
			return new SameMovesList(key, list);
		}
		
	}

}

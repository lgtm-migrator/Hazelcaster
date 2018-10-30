package ar.edu.itba.pod.hazelcaster.backend;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IList;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;

import ar.edu.itba.pod.hazelcaster.abstractions.Movement;
import ar.edu.itba.pod.hazelcaster.abstractions.Query1ResultType;
import ar.edu.itba.pod.hazelcaster.abstractions.mappers.MovementMapper;
import ar.edu.itba.pod.hazelcaster.abstractions.reducers.MovementReducerFactory;
import ar.edu.itba.pod.hazelcaster.interfaces.QueryService;

	/**
	* <p>Implementación concreta de las consultas, basada en una arquitectura
	* <i>map-reduce</i>.</p>
	*/

@Service
public class MapReduceBasedQueryService implements QueryService {
	
	@Autowired
	HazelcastInstance hazelcastInstance;

	@Override
	public Map<Query1ResultType, Integer> getAirportsMovements(List<Movement> movements) 
					throws InterruptedException, ExecutionException {
		JobTracker jobTracker = hazelcastInstance.getJobTracker( "default" );
		//TODO: create list config in hazelcastInstance configuration
		IList<Query1ResultType> movementsList = hazelcastInstance.getList("query1");
		movementsList.clear();
		for(Movement m : movements) {
			movementsList.add(new Query1ResultType(m.getOrigin()));
			movementsList.add(new Query1ResultType(m.getDestination()));
		}
		final KeyValueSource<String, Query1ResultType> source = KeyValueSource.fromList(movementsList);
		Job<String, Query1ResultType> job1 = jobTracker.newJob(source);
		ICompletableFuture<Map<Query1ResultType, Integer>> future = job1
				.mapper(new MovementMapper())
        		.reducer(new MovementReducerFactory())
        		.submit();
		Map<Query1ResultType, Integer> result = future.get();
		return result;
	}

	@Override
	public Map<Query1ResultType, Integer> getAirportsPairsWithSameMovements(List<Movement> movements) 
			throws InterruptedException, ExecutionException {
		return null;
	}

	@Override
	public void getMovementsBetweenAirports() {
		// TODO Auto-generated method stub
	}

	@Override
	public void getAirportsWithMostLandings(final String oaci, final int airports) {
		// TODO Auto-generated method stub
	}

	@Override
	public void getAirportsWithMostInternationalLandings(final int airports) {
		// TODO Auto-generated method stub
	}

	@Override
	public void getProvincesPairsWithMovements(final int minMovements) {
		// TODO Auto-generated method stub	
	}
}

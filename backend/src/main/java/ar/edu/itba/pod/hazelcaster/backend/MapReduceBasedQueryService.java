package ar.edu.itba.pod.hazelcaster.backend;

import java.util.ArrayList;
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
	public void getAirportsMovements() throws InterruptedException, ExecutionException {
		JobTracker jobTracker = hazelcastInstance.getJobTracker( "default" );
		//TODO: read from file
		List<Movement> movements = new ArrayList<>();
		//TODO: create list config in hazelcastInstance configuration
		IList<Movement> movementsList = hazelcastInstance.getList("query1");
		movementsList.clear();
		movementsList.addAll(movements);
		final KeyValueSource<String, Movement> source = KeyValueSource.fromList(movementsList);
		Job<String, Movement> job1 = jobTracker.newJob(source);
		ICompletableFuture<Map<Movement, Integer>> future = job1
				.mapper(new MovementMapper())
        		.reducer(new MovementReducerFactory())
        		.submit();
		//TODO: Check
		//future.andThen(buildCallback());
		Map<Movement, Integer> result = future.get();
		//TODO: Analyze result
	}

	@Override
	public void getAirportsPairsWithSameMovements() throws InterruptedException, ExecutionException {
		
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

package ar.edu.itba.pod.hazelcaster.backend;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IList;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;

import ar.edu.itba.pod.hazelcaster.abstractions.Airport;
import ar.edu.itba.pod.hazelcaster.abstractions.Movement;
import ar.edu.itba.pod.hazelcaster.abstractions.combiners.MoveCountCombinerFactory;
import ar.edu.itba.pod.hazelcaster.abstractions.mappers.MoveCountMapper;
import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.MoveCountOutput;
import ar.edu.itba.pod.hazelcaster.abstractions.reducers.MoveCountReducerFactory;
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
	public List<MoveCountOutput> getAirportsMovements(final List<Movement> movements, final List<Airport> airports) 
			throws InterruptedException, ExecutionException {
		
		JobTracker jobTracker = hazelcastInstance.getJobTracker( "default" );
		//TODO: read from file
		//TODO: create list config in hazelcastInstance configuration
		IList<Movement> movementsList = hazelcastInstance.getList("query1");

		movementsList.clear();
		movementsList.addAll(movements);
				
		final KeyValueSource<String, Movement> source = KeyValueSource.fromList(movementsList);
		Job<String, Movement> job1 = jobTracker.newJob(source);
		ICompletableFuture<Map<String, MoveCountOutput>> future = job1
				.mapper(new MoveCountMapper())
				.combiner(new MoveCountCombinerFactory())
        		.reducer(new MoveCountReducerFactory())
        		.submit();
		
		// TODO: Check
		// future.andThen(buildCallback());
				
		Map<String, String> oaciDenominationMap = airports.stream()
				.filter(airport -> !airport.getOACI().equals(""))
				.collect(Collectors.toMap(Airport::getOACI, Airport::getDenomination));
		
		return future.get().values().stream()
				.map(element -> {
					element.setDenomination(oaciDenominationMap.get(element.getOaci()));
					return element;
				})
				.sorted()
				.collect(Collectors.toList());
	}

	@Override
	public void getAirportsPairsWithSameMovements() throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub	
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

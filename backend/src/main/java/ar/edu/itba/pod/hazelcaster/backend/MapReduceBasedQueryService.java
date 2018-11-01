package ar.edu.itba.pod.hazelcaster.backend;

import ar.edu.itba.pod.hazelcaster.abstractions.collators.*;
import ar.edu.itba.pod.hazelcaster.abstractions.combiners.*;
import ar.edu.itba.pod.hazelcaster.abstractions.mappers.*;
import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.*;
import ar.edu.itba.pod.hazelcaster.abstractions.reducers.*;
import ar.edu.itba.pod.hazelcaster.abstractions.Airport;
import ar.edu.itba.pod.hazelcaster.abstractions.Movement;
import ar.edu.itba.pod.hazelcaster.interfaces.QueryService;
import ar.edu.itba.pod.hazelcaster.interfaces.properties.HazelcasterProperties;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICompletableFuture;
import com.hazelcast.core.IList;
import com.hazelcast.core.IMap;
import com.hazelcast.mapreduce.Job;
import com.hazelcast.mapreduce.JobTracker;
import com.hazelcast.mapreduce.KeyValueSource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

	/**
	* <p>Implementación concreta de las consultas, basada en una arquitectura
	* <i>map-reduce</i>.</p>
	*/

@Service
public class MapReduceBasedQueryService implements QueryService {
	
	@Autowired
	HazelcastInstance hazelcastInstance;
	
	@Autowired
	HazelcasterProperties properties;

	@Override
	public List<MoveCountOutput> getAirportsMovements() throws InterruptedException, ExecutionException {
		
		JobTracker jobTracker = hazelcastInstance.getJobTracker(properties.getClusterName() + "-jobtracker");
		
		IList<Airport> airportsIList = hazelcastInstance.getList(properties.getClusterName() + "-airports");
		IList<Movement> movementsIList = hazelcastInstance.getList(properties.getClusterName() + "-movements");
		
		final KeyValueSource<String, Airport> airportSource = KeyValueSource.fromList(airportsIList);
		Job<String, Airport> airportJob = jobTracker.newJob(airportSource);
		
		// Map from OACI to Denomination (Airport).
		ICompletableFuture<Map<String, String>> oaciDenominationMapFuture = airportJob
				.mapper(new OaciDenominationMapper())
				.submit(new OaciDenominationCollator());
		
		Map<String, String> oaciDenominationMap = oaciDenominationMapFuture.get();
		
		final KeyValueSource<String, Movement> movementSource = KeyValueSource.fromList(movementsIList);
		Job<String, Movement> movementJob = jobTracker.newJob(movementSource);
		
		ICompletableFuture<List<MoveCountOutput>> future = movementJob
				.mapper(new MoveCountMapper())
				.combiner(new MoveCountCombinerFactory())
        		.reducer(new MoveCountReducerFactory())
        		.submit(new MoveCountCollator(oaciDenominationMap));
		
		return future.get();
	}

	@Override
	public List<SameMovesPairOutput> getAirportsPairsWithSameMovements() throws InterruptedException, ExecutionException {
		
		JobTracker jobTracker = hazelcastInstance.getJobTracker(properties.getClusterName() + "-jobtracker");
				
		List<MoveCountOutput> moveCounts = getAirportsMovements();
		IList<MoveCountOutput> moveCountsIList = hazelcastInstance.getList(properties.getClusterName() + "-movecounts");
		
		moveCountsIList.clear();
		moveCountsIList.addAll(moveCounts);
		
		// Obtain map from thousands to list of oaci. 2000 -> A,B,C,D
		final KeyValueSource<String, MoveCountOutput> countSource = KeyValueSource.fromList(moveCountsIList);
		Job<String, MoveCountOutput> sameMovesJob = jobTracker.newJob(countSource);
		
		ICompletableFuture<Map<Long, List<String>>> sameMovesFuture = sameMovesJob
				.mapper(new ThousandMovesMapper())
				.reducer(new SameMovesPairReducerFactory())
				.submit();
		
		Map<Long, List<String>> sameMovesMap = sameMovesFuture.get();
		
		IMap<Long, List<String>> sameMovesIMap = hazelcastInstance.getMap(properties.getClusterName() + "-map");
		
		sameMovesIMap.clear();
		sameMovesIMap.putAll(sameMovesMap);
		
		final KeyValueSource<Long, List<String>> pairsSource = KeyValueSource.fromMap(sameMovesIMap);
		Job<Long, List<String>> pairsJob = jobTracker.newJob(pairsSource);
		
		ICompletableFuture<List<SameMovesPairOutput>> pairsFuture = pairsJob
				.mapper(new SameMovesPairMapper())
				.submit(new SameMovesPairCollator());
		
		return pairsFuture.get();
	}

	@Override
	public List<MovesBetweenAirportsOutput> getMovementsBetweenAirports() throws InterruptedException, ExecutionException {
		
		JobTracker jobTracker = hazelcastInstance.getJobTracker(properties.getClusterName() + "-jobtracker");
		
		IList<Movement> movementsIList = hazelcastInstance.getList(properties.getClusterName() + "-movements");
		
		final KeyValueSource<String, Movement> movementSource = KeyValueSource.fromList(movementsIList);
		Job<String, Movement> movementJob = jobTracker.newJob(movementSource);
		
		ICompletableFuture<List<MovesBetweenAirportsOutput>> movesBetweenAirportsFuture = movementJob
				.mapper(new MovesBetweenAirportsMapper())
				.combiner(new MovesBetweenAirportsCombinerFactory())
				.reducer(new MovesBetweenAirportsReducerFactory())
				.submit(new MovesBetweenAirportsCollator());
		
		return movesBetweenAirportsFuture.get();
	}

	@Override
	public List<LandingMoveCountOutput> getAirportsWithMostLandings(final String oaci, final int airports) 
			throws InterruptedException, ExecutionException {
		
		JobTracker jobTracker = hazelcastInstance.getJobTracker(properties.getClusterName() + "-jobtracker");
		
		IList<Movement> movementsIList = hazelcastInstance.getList(properties.getClusterName() + "-movements");
		
		final KeyValueSource<String, Movement> movementSource = KeyValueSource.fromList(movementsIList);
		Job<String, Movement> movementJob = jobTracker.newJob(movementSource);
		
		ICompletableFuture<List<LandingMoveCountOutput>> landingMovesFuture = movementJob
				.mapper(new LandingMoveCountMapper(oaci))
				.combiner(new MoveCountCombinerFactory())
				.reducer(new LandingMoveCountReducerFactory())
				.submit(new LandingMoveCountCollator(airports));
		
		return landingMovesFuture.get();
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

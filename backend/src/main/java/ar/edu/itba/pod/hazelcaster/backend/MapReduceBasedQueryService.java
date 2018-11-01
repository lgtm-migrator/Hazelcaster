package ar.edu.itba.pod.hazelcaster.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
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
import ar.edu.itba.pod.hazelcaster.abstractions.SameMovesList;
import ar.edu.itba.pod.hazelcaster.abstractions.collators.MoveCountCollator;
import ar.edu.itba.pod.hazelcaster.abstractions.collators.OaciDenominationCollator;
import ar.edu.itba.pod.hazelcaster.abstractions.combiners.MoveCountCombinerFactory;
import ar.edu.itba.pod.hazelcaster.abstractions.mappers.MoveCountMapper;
import ar.edu.itba.pod.hazelcaster.abstractions.mappers.OaciDenominationMapper;
import ar.edu.itba.pod.hazelcaster.abstractions.mappers.SameMovesPairMapper;
import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.MoveCountOutput;
import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.SameMovesPairOutput;
import ar.edu.itba.pod.hazelcaster.abstractions.reducers.MoveCountReducerFactory;
import ar.edu.itba.pod.hazelcaster.abstractions.reducers.SameMovesPairReducerFactory;
import ar.edu.itba.pod.hazelcaster.interfaces.QueryService;
import ar.edu.itba.pod.hazelcaster.interfaces.properties.HazelcasterProperties;

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
	public List<MoveCountOutput> getAirportsMovements() 
			throws InterruptedException, ExecutionException {
		
		JobTracker jobTracker = hazelcastInstance.getJobTracker(properties.getClusterName() + "-jobtracker");
		
		IList<Airport> airportsList = hazelcastInstance.getList(properties.getClusterName() + "-airports");
		IList<Movement> movementsList = hazelcastInstance.getList(properties.getClusterName() + "-movements");
		
		final KeyValueSource<String, Airport> airportSource = KeyValueSource.fromList(airportsList);
		Job<String, Airport> airportJob = jobTracker.newJob(airportSource);
		
		// Map from OACI to Denomination (Airport).
		ICompletableFuture<Map<String, String>> oaciDenominationMapFuture = airportJob
				.mapper(new OaciDenominationMapper())
				.submit(new OaciDenominationCollator());
		
		Map<String, String> oaciDenominationMap = oaciDenominationMapFuture.get();
		
		final KeyValueSource<String, Movement> movementSource = KeyValueSource.fromList(movementsList);
		Job<String, Movement> movementJob = jobTracker.newJob(movementSource);
		
		ICompletableFuture<List<MoveCountOutput>> future = movementJob
				.mapper(new MoveCountMapper())
				.combiner(new MoveCountCombinerFactory())
        		.reducer(new MoveCountReducerFactory())
        		.submit(new MoveCountCollator(oaciDenominationMap));
		
		return future.get();
	}

	@Override
	public List<SameMovesPairOutput> getAirportsPairsWithSameMovements(List<Movement> movements,
			List<Airport> airports) throws InterruptedException, ExecutionException {
		
		List<MoveCountOutput> moveCounts = getAirportsMovements();
		
		moveCounts = moveCounts.parallelStream()
				.filter(element -> !(element.getCount() < 1000L))
				.map(element -> {
					element.setCount((element.getCount()/1000)*1000);
					return element;
				})
				.collect(Collectors.toList());
		
		JobTracker jobTracker = hazelcastInstance.getJobTracker("default");
		IList<MoveCountOutput> moveCountIList = hazelcastInstance.getList("query2");
		
		moveCountIList.clear();
		moveCountIList.addAll(moveCounts);
		
		final KeyValueSource<String, MoveCountOutput> source = KeyValueSource.fromList(moveCountIList);
		Job<String, MoveCountOutput> job = jobTracker.newJob(source);
		
		ICompletableFuture<Map<Long, SameMovesList>> future = job
				.mapper(new SameMovesPairMapper())
				.reducer(new SameMovesPairReducerFactory())
				.submit();
		
		Map<Long, SameMovesList> resultMap = future.get();
		
		List<SameMovesList> processedList = resultMap.values().parallelStream()
				.filter(list -> !(list.getOaciList().size() < 2))
				.sorted()
				.collect(Collectors.toList());
		
		List<SameMovesPairOutput> output = new ArrayList<>();
		
		for (SameMovesList list : processedList) {
			Long count = list.getCount();
			List<String> oaciList = list.getOaciList();
			for (int i = 0; i < oaciList.size(); i++) {
				for (int j = i + 1; j < oaciList.size(); j++) {
					output.add(new SameMovesPairOutput(count, oaciList.get(i), oaciList.get(j)));
				}
			}
		}
		
		return output;
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

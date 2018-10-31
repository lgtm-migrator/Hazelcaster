package ar.edu.itba.pod.hazelcaster.backend;

import java.util.ArrayList;
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
import ar.edu.itba.pod.hazelcaster.abstractions.SameMovesList;
import ar.edu.itba.pod.hazelcaster.abstractions.combiners.MoveCountCombinerFactory;
import ar.edu.itba.pod.hazelcaster.abstractions.mappers.MoveCountMapper;
import ar.edu.itba.pod.hazelcaster.abstractions.mappers.SameMovesPairMapper;
import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.MoveCountOutput;
import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.SameMovesPairOutput;
import ar.edu.itba.pod.hazelcaster.abstractions.reducers.MoveCountReducerFactory;
import ar.edu.itba.pod.hazelcaster.abstractions.reducers.SameMovesPairReducerFactory;
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
		
		JobTracker jobTracker = hazelcastInstance.getJobTracker("default");
		IList<Movement> movementsList = hazelcastInstance.getList("query1");

		movementsList.clear();
		movementsList.addAll(movements);
				
		final KeyValueSource<String, Movement> source = KeyValueSource.fromList(movementsList);
		Job<String, Movement> job = jobTracker.newJob(source);
		
		ICompletableFuture<Map<String, MoveCountOutput>> future = job
				.mapper(new MoveCountMapper())
				.combiner(new MoveCountCombinerFactory())
        		.reducer(new MoveCountReducerFactory())
        		.submit();
						
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
	public List<SameMovesPairOutput> getAirportsPairsWithSameMovements(List<Movement> movements,
			List<Airport> airports) throws InterruptedException, ExecutionException {
		
		List<MoveCountOutput> moveCounts = getAirportsMovements(movements, airports);
		
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

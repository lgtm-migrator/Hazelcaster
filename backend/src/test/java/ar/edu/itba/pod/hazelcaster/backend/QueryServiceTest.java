package ar.edu.itba.pod.hazelcaster.backend;

import static org.junit.jupiter.api.Assertions.*;
import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.*;
import ar.edu.itba.pod.hazelcaster.abstractions.Airport;
import ar.edu.itba.pod.hazelcaster.abstractions.Movement;
import ar.edu.itba.pod.hazelcaster.backend.config.HazelcasterTestConfiguration;
import ar.edu.itba.pod.hazelcaster.backend.support.Generator;
import ar.edu.itba.pod.hazelcaster.interfaces.QueryService;
import ar.edu.itba.pod.hazelcaster.interfaces.properties.HazelcasterProperties;
import com.google.common.collect.Comparators;
import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javax.inject.Inject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(HazelcasterTestConfiguration.class)
class QueryServiceTest {

	private static final String OACI = "ZZZZ-test";
	private static final String IATA = "777-test";
	private static final int MAX_AIRPORTS = 1000;
	private static final int MAX_MOVEMENTS = 100000;
	private static final int MIN_MOVEMENTS = 10;

	@Inject protected Generator generator;
	@Inject protected QueryService service;
	@Inject protected HazelcastInstance hazelcast;
	@Inject protected HazelcasterProperties properties;

	protected Map<String, String> iataToOaci;

	@BeforeEach
	protected void cleanAllDistributedStructures() {
		final List<Airport> airports = generator.generateAirports(MAX_AIRPORTS - 1);
		final List<Movement> movements = generator.generateMovements(airports, MAX_MOVEMENTS);
		airports.add(generator.generateAirportWith(OACI, IATA));
		if (iataToOaci != null) iataToOaci.clear();
		iataToOaci = generator.generateIATAToOACIMapping(airports);
		hazelcast.getDistributedObjects()
			.stream()
			.forEach(DistributedObject::destroy);
		hazelcast.getList(properties.getClusterName() + "-airports").addAll(airports);
		hazelcast.getList(properties.getClusterName() + "-movements").addAll(movements);
	}

	@Test
	protected void query1ResultIsWellSorted()
			throws InterruptedException, ExecutionException {
		final List<MoveCountOutput> result = service.getAirportsMovements();
		assertTrue(Comparators.isInOrder(result, Comparator
				.comparing(MoveCountOutput::getCount).reversed()
				.thenComparing(MoveCountOutput::getOaci)),
				"Query 1 has bad ordering. Should be descending by movements and ascending by OACI.");
	}

	@Test
	protected void query2ResultIsWellSorted()
			throws InterruptedException, ExecutionException {
		final List<SameMovesPairOutput> result = service.getAirportsPairsWithSameMovements();
		assertTrue(Comparators.isInOrder(result, Comparator
				.comparing(SameMovesPairOutput::getCount).reversed()
				.thenComparing(SameMovesPairOutput::getOaciA)
				.thenComparing(SameMovesPairOutput::getOaciB)),
				"Query 2 has bad ordering. Should be descending by group, " +
				"and then ascending by origin OACI and then by destination OACI.");
	}

	@Test
	protected void query3ResultIsWellSorted()
			throws InterruptedException, ExecutionException {
		final List<MovesBetweenAirportsOutput> result = service.getMovementsBetweenAirports();
		assertTrue(Comparators.isInOrder(result, Comparator
				.comparing(MovesBetweenAirportsOutput::getOaciA)
				.thenComparing(MovesBetweenAirportsOutput::getOaciB)),
				"Query 3 has bad ordering. Should be ascending by origin OACI and then by destination OACI.");
	}

	@Test
	protected void query4ResultIsWellSorted()
			throws InterruptedException, ExecutionException {
		final List<LandingMoveCountOutput> result = service.getAirportsWithMostLandings(OACI, MAX_AIRPORTS);
		assertTrue(Comparators.isInOrder(result, Comparator
				.comparing(LandingMoveCountOutput::getCount).reversed()
				.thenComparing(LandingMoveCountOutput::getOaci)),
				"Query 4 has bad ordering. Should be descending by landings and ascendent by OACI.");
	}

	@Test
	protected void query5ResultIsWellSorted()
			throws InterruptedException, ExecutionException {
		final List<InternationalPercentageOutput> result = service.getAirportsWithMostInternationalLandings(MAX_AIRPORTS);
		assertTrue(Comparators.isInOrder(result, Comparator
				.comparing(InternationalPercentageOutput::getPercentage).reversed()
				.thenComparing(InternationalPercentageOutput::getIata)),
				// Según el enunciado, debería ser:
				//.thenComparing((x, y) -> iataToOaci.get(x.getIata()).compareTo(iataToOaci.get(y.getIata())))),
				//"Query 5 has bad ordering. Should be descending by percentage and ascendent by OACI.");
				"Query 5 has bad ordering. Should be descending by percentage and ascendent by IATA.");
	}

	@Test
	protected void query6ResultIsWellSorted()
			throws InterruptedException, ExecutionException {
		final List<MovesBetweenProvincesOutput> result = service.getProvincesPairsWithMovements(MIN_MOVEMENTS);
		assertTrue(Comparators.isInOrder(result, Comparator
				.comparing(MovesBetweenProvincesOutput::getCount).reversed()
				.thenComparing((x, y) -> (x.getProvA() + x.getProvB()).compareTo(y.getProvA() + y.getProvB()))),
				"Query 6 has bad ordering. Should be descending by movements and ascendent by 'Province A || Province B'.");
	}

	@Test
	protected void query4ResultShouldBeBounded()
			throws InterruptedException, ExecutionException {
		final List<LandingMoveCountOutput> result = service.getAirportsWithMostLandings(OACI, MAX_AIRPORTS);
		assertTrue(result.size() <= MAX_AIRPORTS,
				"Query 4 is not well bounded. The size should not exceed " + MAX_AIRPORTS + " airports.");
	}

	@Test
	protected void query5ResultShouldBeBounded()
			throws InterruptedException, ExecutionException {
		final List<InternationalPercentageOutput> result = service.getAirportsWithMostInternationalLandings(MAX_AIRPORTS);
		assertTrue(result.size() <= MAX_AIRPORTS,
				"Query 5 is not well bounded. The size should not exceed " + MAX_AIRPORTS + " airports.");
	}

	@AfterAll
	protected static void shutdownCluster() {
		Hazelcast.shutdownAll();
	}
}

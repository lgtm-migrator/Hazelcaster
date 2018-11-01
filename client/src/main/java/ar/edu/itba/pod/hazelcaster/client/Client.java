package ar.edu.itba.pod.hazelcaster.client;

import ar.edu.itba.pod.hazelcaster.abstractions.Airport;
import ar.edu.itba.pod.hazelcaster.abstractions.Movement;
import ar.edu.itba.pod.hazelcaster.client.config.ClientConfiguration;
import ar.edu.itba.pod.hazelcaster.client.config.ClientProperties;
import ar.edu.itba.pod.hazelcaster.interfaces.CSVSerializer;
import ar.edu.itba.pod.hazelcaster.interfaces.QueryService;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class Client {

	private static final Logger logger = LoggerFactory.getLogger(Client.class);
	private static final Logger timeLogger = LoggerFactory.getLogger("time-logger");

	protected final HazelcastInstance hazelcast;
	protected final ClientProperties properties;
	protected final CSVSerializer csv;
	protected final QueryService qService;

	@Inject
	public Client(
			@Nullable final HazelcastInstance hazelcast,
			final ClientProperties properties,
			final CSVSerializer csv,
			final QueryService qService) {
		this.hazelcast = hazelcast;
		this.properties = properties;
		this.csv = csv;
		this.qService = qService;
	}

	public static void main(final String ... arguments) {
		try {
			logger.info("(2018) Hazelcaster Client v1.0.");
			SpringApplication.run(ClientConfiguration.class, arguments)
				.getBean(Client.class)
				.run();
		}
		catch (final Exception exception) {
			logger.error("Excepción inesperada: '{}'.", exception.getClass().getName());
			logger.error("Mensaje: {}.", exception.getMessage());
			exception.printStackTrace();
		}
		logger.info("Hazelcaster client exiting...");
	}

	public void run()
			throws IOException, InterruptedException, ExecutionException {
		if (hazelcast == null) {
			logger.error("No se pudo desplegar la instancia de Hazelcast.");
			return;
		}
		timeLogger.info("Inicio de lectura de archivos CSV de entrada.");
		final List<Airport> airports = csv.read(Airport.class, properties.getAirportsFilename());
		final List<Movement> movements = csv.read(Movement.class, properties.getMovementsFilename());
		timeLogger.info("Fin de lectura de archivos CSV de entrada. Limpiando cluster...");

		final IList<Airport> airportsIList = hazelcast.getList(properties.getClusterName() + "-airports");
		final IList<Movement> movementsIList = hazelcast.getList(properties.getClusterName() + "-movements");

		// Clean cluster, and load data:
		airportsIList.clear();
		movementsIList.clear();
		airportsIList.addAll(airports);
		movementsIList.addAll(movements);

		List<?> result = null;
		timeLogger.info("Cluster limpio. Inicio de la consulta {} bajo map-reduce.", properties.getQueryID());
		
		switch (properties.getQueryID()) {
			case 1:
				result = qService.getAirportsMovements();
				break;
			case 2:
				result = qService.getAirportsPairsWithSameMovements();
				break;
			case 3:
				result = qService.getMovementsBetweenAirports();
				break;
			case 4:
				result = qService.getAirportsWithMostLandings(properties.getOACI(),properties.getN());
				break;
			case 5:
				result = qService.getAirportsWithMostInternationalLandings(properties.getN());
				break;
			case 6:
				//qService.getProvincesPairsWithMovements(minMovements);
				break;
		}
		
		csv.write(result, properties.getResultFilename());
		timeLogger.info("Fin de la consulta {} bajo map-reduce. Descargada en CSV.", properties.getQueryID());
		hazelcast.shutdown();
	}
}

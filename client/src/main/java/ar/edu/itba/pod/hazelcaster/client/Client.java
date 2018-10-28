package ar.edu.itba.pod.hazelcaster.client;

import ar.edu.itba.pod.hazelcaster.client.config.ClientConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

public class Client {

	private static final Logger logger
		= LoggerFactory.getLogger(Client.class);

	public static void main(final String ... arguments) {
		try {
			logger.info("(2018) Hazelcaster Client v1.0.");
			SpringApplication.run(ClientConfiguration.class, arguments);
		}
		catch (final Exception exception) {
			logger.error("Excepción inesperada: '{}'.", exception.getClass().getName());
		}
	}
}

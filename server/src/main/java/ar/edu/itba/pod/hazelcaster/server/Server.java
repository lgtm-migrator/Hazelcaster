package ar.edu.itba.pod.hazelcaster.server;

import ar.edu.itba.pod.hazelcaster.server.config.ServerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;

public class Server {

	private static final Logger logger
		= LoggerFactory.getLogger(Server.class);

	public static void main(final String ... arguments) {
		try {
			logger.info("(2018) Hazelcaster Server v1.0.");
			SpringApplication.run(ServerConfiguration.class, arguments);
		}
		catch (final Exception exception) {
			logger.error("Excepción inesperada: '{}'.", exception.getClass().getName());
		}
	}
}

package ar.edu.itba.pod.hazelcaster.server;

import ar.edu.itba.pod.hazelcaster.server.config.ServerConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Server {

	private static final Logger logger
		= LoggerFactory.getLogger(Server.class);

	@SuppressWarnings("resource")
	public static void main(final String ... arguments) {
		logger.info("(2018) Hazelcaster Server v1.0.");
		new AnnotationConfigApplicationContext(ServerConfiguration.class);
	}
}

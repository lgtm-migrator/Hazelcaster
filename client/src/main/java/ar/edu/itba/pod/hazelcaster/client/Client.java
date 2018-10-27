package ar.edu.itba.pod.hazelcaster.client;

import ar.edu.itba.pod.hazelcaster.client.config.ClientConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class Client {

	private static final Logger logger
		= LoggerFactory.getLogger(Client.class);

	public static void main(final String ... arguments) {
		logger.info("(2018) Hazelcaster Client v1.0.");
		final AbstractApplicationContext context
			= new AnnotationConfigApplicationContext(ClientConfiguration.class);
		context.close();
	}
}

package ar.edu.itba.pod.hazelcaster.server.config;

import ar.edu.itba.pod.hazelcaster.interfaces.properties.HazelcasterProperties;
import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import java.io.IOException;
import java.util.Properties;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(ServerProperties.class)
@SpringBootApplication
public class ServerConfiguration {

	private static final Logger logger
		= LoggerFactory.getLogger(ServerConfiguration.class);

	@Inject
	protected HazelcasterProperties hazelcasterProperties;

	@Bean
	public Properties properties() {
		final Properties properties = new Properties();
		properties.setProperty("hazelcaster.cluster.name", hazelcasterProperties.getClusterName());
		properties.setProperty("hazelcaster.cluster.key", hazelcasterProperties.getClusterKey());
		System.setProperty("hazelcast.logging.type", "slf4j");
		return properties;
	}

	@Bean
	public Config hazelcastConfig(final Properties properties)
			throws IOException {
		final String filename = hazelcasterProperties.getXMLConfigFilename();
		final Config config = new ClasspathXmlConfig(filename, properties);
		config.getNetworkConfig()
			.getInterfaces()
			.setInterfaces(hazelcasterProperties.getInterfaces());
		return config;
	}

	@Bean
	public HazelcastInstance hazelcastInstance(final Config config) {
		try {
			return Hazelcast.newHazelcastInstance(config);
		}
		catch (final Exception exception) {
			logger.error("No se pudo iniciar un nuevo nodo en el cluster de Hazelcast: '{}'.",
					exception.getMessage());
			return null;
		}
	}
}

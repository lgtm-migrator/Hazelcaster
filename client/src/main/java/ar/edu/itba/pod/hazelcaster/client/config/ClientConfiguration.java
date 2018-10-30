package ar.edu.itba.pod.hazelcaster.client.config;

import ar.edu.itba.pod.hazelcaster.interfaces.properties.HazelcasterProperties;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.XmlClientConfigBuilder;
import com.hazelcast.core.HazelcastInstance;
import java.io.IOException;
import java.util.Properties;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;

@EnableConfigurationProperties(ClientProperties.class)
@SpringBootApplication(scanBasePackages = {
	"ar.edu.itba.pod.hazelcaster.backend",
	"ar.edu.itba.pod.hazelcaster.client"
})
public class ClientConfiguration {

	private static final Logger logger
		= LoggerFactory.getLogger(ClientConfiguration.class);

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
	public ClientConfig hazelcastConfig(final Properties properties)
			throws IOException {
		final String filename = hazelcasterProperties.getXMLConfigFilename();
		final XmlClientConfigBuilder builder = new XmlClientConfigBuilder(filename);
		builder.setProperties(properties);
		final ClientConfig config = builder.build();
		config.getNetworkConfig().setAddresses(hazelcasterProperties.getAddresses());
		return config;
	}

	@Bean @Nullable
	public HazelcastInstance hazelcastInstance(final ClientConfig config) {
		try {
			return HazelcastClient.newHazelcastClient(config);
		}
		catch (final Exception exception) {
			logger.error("No se pudo iniciar la instancia cliente de Hazelcast: '{}'.",
					exception.getMessage());
			return null;
		}
	}
}

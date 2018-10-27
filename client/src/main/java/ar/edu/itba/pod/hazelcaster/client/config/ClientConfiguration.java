package ar.edu.itba.pod.hazelcaster.client.config;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.XmlClientConfigBuilder;
import com.hazelcast.core.HazelcastInstance;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("ar.edu.itba.pod.hazelcaster.backend")
@PropertySource("classpath:/application.properties")
public class ClientConfiguration {

	@Bean
	public Properties properties(
			@Value("${hazelcaster.cluster.name}") final String name,
			@Value("${hazelcaster.cluster.key}") final String key) {
		final Properties properties = new Properties();
		properties.setProperty("hazelcaster.cluster.name", name);
		properties.setProperty("hazelcaster.cluster.key", key);
		properties.setProperty("hazelcast.logging.type", "slf4j");
		return properties;
	}

	@Bean
	public ClientConfig hazelcastConfig(
			@Value("${hazelcaster.xml.config}") final String xmlConfig,
			@Value("${hazelcaster.interfaces}") final String interfaces,
			@Value("${addresses}") final String addresses,
			final Properties properties)
					throws IOException {
		final List<String> nodes = Stream.of(addresses.split(","))
				.collect(Collectors.toList());
		final XmlClientConfigBuilder builder = new XmlClientConfigBuilder(xmlConfig);
		builder.setProperties(properties);
		final ClientConfig config = builder.build();
		config.getNetworkConfig().setAddresses(nodes);
		return config;
	}

	@Bean
	public HazelcastInstance hazelcastInstance(final ClientConfig config) {
		return HazelcastClient.newHazelcastClient(config);
	}
}

package ar.edu.itba.pod.hazelcaster.server.config;

import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/application.properties")
public class ServerConfiguration {

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
	public Config hazelcastConfig(
			@Value("${hazelcaster.xml.config}") final String xmlConfig,
			@Value("${hazelcaster.interfaces}") final String interfaces,
			final Properties properties)
					throws IOException {
		final List<String> networks = Stream.of(interfaces.split(",|\\s"))
				.filter(n -> !n.isEmpty())
				.collect(Collectors.toList());
		final Config config = new ClasspathXmlConfig(xmlConfig, properties);
		config.getNetworkConfig().getInterfaces().setInterfaces(networks);
		return config;
	}

	@Bean
	public HazelcastInstance hazelcastInstance(final Config config) {
		return Hazelcast.newHazelcastInstance(config);
	}
}

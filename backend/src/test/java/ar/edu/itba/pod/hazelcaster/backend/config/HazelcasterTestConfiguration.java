package ar.edu.itba.pod.hazelcaster.backend.config;

import ar.edu.itba.pod.hazelcaster.interfaces.properties.HazelcasterProperties;
import com.hazelcast.config.ClasspathXmlConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("ar.edu.itba.pod.hazelcaster.backend")
@SpringBootConfiguration
public class HazelcasterTestConfiguration {

	@Bean
	public HazelcasterProperties hazelcasterProperties() {
		return new HazelcasterProperties() {

			@Override
			public String getXMLConfigFilename() {
				return "hazelcast-test.xml";
			}

			@Override
			public int getSyncReplicas() {
				return 0;
			}

			@Override
			public List<String> getInterfaces() {
				return getAddresses();
			}

			@Override
			public String getCoordinator() {
				return getAddresses().get(0);
			}

			@Override
			public String getClusterName() {
				return "hazelcaster-test-cluster";
			}

			@Override
			public String getClusterKey() {
				return "hazelcaster-test-password";
			}

			@Override
			public List<String> getAddresses() {
				return Arrays.asList("127.0.0.1");
			}
		};
	}

	@Bean
	public Properties properties(final HazelcasterProperties hazelcasterProperties) {
		final Properties properties = new Properties();
		properties.setProperty("hazelcaster.cluster.name", hazelcasterProperties.getClusterName());
		properties.setProperty("hazelcaster.cluster.key", hazelcasterProperties.getClusterKey());
		properties.setProperty("hazelcaster.sync.replicas", Integer.toString(hazelcasterProperties.getSyncReplicas()));
		System.setProperty("hazelcast.logging.type", "slf4j");
		return properties;
	}

	@Bean
	public Config hazelcastConfig(
			final HazelcasterProperties hazelcasterProperties, final Properties properties)
					throws IOException {
		final String filename = hazelcasterProperties.getXMLConfigFilename();
		final Config config = new ClasspathXmlConfig(filename, properties);
		config.getNetworkConfig()
			.getInterfaces()
			.setInterfaces(hazelcasterProperties.getInterfaces());
		config.getNetworkConfig()
			.getJoin()
			.getTcpIpConfig()
			.addMember(hazelcasterProperties.getCoordinator());
		return config;
	}

	@Bean
	public HazelcastInstance hazelcastInstance(final Config config) {
		return Hazelcast.newHazelcastInstance(config);
	}
}

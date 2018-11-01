package ar.edu.itba.pod.hazelcaster.server.config;

import ar.edu.itba.pod.hazelcaster.interfaces.properties.HazelcasterProperties;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties("hazelcaster")
public class ServerProperties implements HazelcasterProperties {

	@NotBlank @Pattern(regexp = ".*\\.xml")
	protected String xmlConfig;

	@Pattern(regexp = "\\d{5}-\\d{5}-\\d{5}")
	protected String clusterName;

	@NotBlank @Size(min = 32)
	protected String clusterKey;

	@NotBlank
	protected String interfaces;

	@PositiveOrZero
	protected int syncReplicas;

	@Override
	public String getXMLConfigFilename() {
		return xmlConfig;
	}

	@Override
	public String getClusterName() {
		return clusterName;
	}

	@Override
	public String getClusterKey() {
		return clusterKey;
	}

	@Override
	public List<String> getInterfaces() {
		return Stream.of(interfaces.split(",|;|\\s"))
				.filter(i -> !i.isEmpty())
				.collect(Collectors.toList());
	}

	@Override
	public List<String> getAddresses() {
		return Collections.emptyList();
	}

	@Override
	public int getSyncReplicas() {
		return syncReplicas;
	}

	public void setXmlConfig(final String xmlConfig) {
		this.xmlConfig = xmlConfig;
	}

	public void setClusterName(final String clusterName) {
		this.clusterName = clusterName;
	}

	public void setClusterKey(final String clusterKey) {
		this.clusterKey = clusterKey;
	}

	public void setInterfaces(final String interfaces) {
		this.interfaces = interfaces;
	}

	public void setSyncReplicas(final int syncReplicas) {
		this.syncReplicas = syncReplicas;
	}
}

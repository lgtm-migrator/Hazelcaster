package ar.edu.itba.pod.hazelcaster.client.config;

import ar.edu.itba.pod.hazelcaster.interfaces.properties.HazelcasterProperties;
import ar.edu.itba.pod.hazelcaster.interfaces.properties.OutputProperties;
import ar.edu.itba.pod.hazelcaster.interfaces.properties.QueryProperties;
import ar.edu.itba.pod.hazelcaster.interfaces.properties.SourceProperties;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties("hazelcaster")
public class ClientProperties
	implements HazelcasterProperties, QueryProperties,
		SourceProperties, OutputProperties {

	@NotBlank @Pattern(regexp = ".*\\.xml")
	protected String xmlConfig;

	@Pattern(regexp = "\\d{5}-\\d{5}-\\d{5}")
	protected String clusterName;

	@NotBlank @Size(min = 32)
	protected String clusterKey;

	@NotBlank
	protected String addresses;

	@Min(1) @Max(6)
	protected int query;

	@PositiveOrZero
	protected int n;

	@PositiveOrZero
	protected int min;

	@Pattern(regexp = "^$|[a-zA-Z0-9]{4}")
	protected String oaci;

	@NotBlank
	protected String outPath;

	@NotBlank
	protected String timeOutPath;

	@NotBlank
	protected String airportsInPath;

	@NotBlank
	protected String movementsInPath;

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
		return Collections.emptyList();
	}

	@Override
	public List<String> getAddresses() {
		return Stream.of(addresses.split(",|;|\\s"))
				.filter(a -> !a.isEmpty())
				.collect(Collectors.toList());
	}

	@Override
	public int getQueryID() {
		return query;
	}

	@Override
	public int getN() {
		return n;
	}

	@Override
	public int getMin() {
		return min;
	}

	@Override
	public String getOACI() {
		return oaci;
	}

	@Override
	public String getResultFilename() {
		return outPath;
	}

	@Override
	public String getTimeFilename() {
		return timeOutPath;
	}

	@Override
	public String getAirportsFilename() {
		return airportsInPath;
	}

	@Override
	public String getMovementsFilename() {
		return movementsInPath;
	}

	@Override
	public int getSyncReplicas() {
		return -1;
	}

	@Override
	public String getCoordinator() {
		return "127.0.0.1";
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

	public void setAddresses(final String addresses) {
		this.addresses = addresses;
	}

	public void setQuery(final int query) {
		this.query = query;
	}

	public void setN(final int n) {
		this.n = n;
	}

	public void setMin(final int min) {
		this.min = min;
	}

	public void setOACI(final String oaci) {
		this.oaci = oaci;
	}

	public void setOutPath(final String outPath) {
		this.outPath = outPath;
	}

	public void setTimeOutPath(final String timeOutPath) {
		this.timeOutPath = timeOutPath;
	}

	public void setAirportsInPath(final String airportsInPath) {
		this.airportsInPath = airportsInPath;
	}

	public void setMovementsInPath(final String movementsInPath) {
		this.movementsInPath = movementsInPath;
	}
}

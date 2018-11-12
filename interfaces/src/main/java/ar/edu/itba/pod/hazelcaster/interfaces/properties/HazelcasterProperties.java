package ar.edu.itba.pod.hazelcaster.interfaces.properties;

import java.util.List;

public interface HazelcasterProperties {

	public String getXMLConfigFilename();
	public String getClusterName();
	public String getClusterKey();
	public List<String> getInterfaces();
	public List<String> getAddresses();
	public int getSyncReplicas();
	public String getCoordinator();
}

package ar.edu.itba.pod.hazelcaster.interfaces;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import ar.edu.itba.pod.hazelcaster.abstractions.Movement;
import ar.edu.itba.pod.hazelcaster.abstractions.Query1ResultType;

/**
	* <p>Las 6 queries requeridas por la cátedra.</p>
	*/

public interface QueryService {

	// NOTA: Se deben corregir los tipos de retorno.

	public Map<Query1ResultType, Integer> getAirportsMovements(List<Movement> movements) 
			throws InterruptedException, ExecutionException;
	public Map<Query1ResultType, Integer> getAirportsPairsWithSameMovements(List<Movement> movements) 
			throws InterruptedException, ExecutionException;
	public void getMovementsBetweenAirports();

	public void getAirportsWithMostLandings(final String oaci, final int airports);
	public void getAirportsWithMostInternationalLandings(final int airports);
	public void getProvincesPairsWithMovements(final int minMovements);
}

package ar.edu.itba.pod.hazelcaster.interfaces;

import java.util.concurrent.ExecutionException;

/**
	* <p>Las 6 queries requeridas por la cátedra.</p>
	*/

public interface QueryService {

	// NOTA: Se deben corregir los tipos de retorno.

	public void getAirportsMovements() throws InterruptedException, ExecutionException;
	public void getAirportsPairsWithSameMovements() throws InterruptedException, ExecutionException;
	public void getMovementsBetweenAirports();

	public void getAirportsWithMostLandings(final String oaci, final int airports);
	public void getAirportsWithMostInternationalLandings(final int airports);
	public void getProvincesPairsWithMovements(final int minMovements);
}

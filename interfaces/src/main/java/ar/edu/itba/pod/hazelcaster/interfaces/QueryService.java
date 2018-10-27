package ar.edu.itba.pod.hazelcaster.interfaces;

	/**
	* <p>Las 6 queries requeridas por la cátedra.</p>
	*/

public interface QueryService {

	// NOTA: Se deben corregir los tipos de retorno.

	public void getAirportsMovements();
	public void getAirportsPairsWithSameMovements();
	public void getMovementsBetweenAirports();

	public void getAirportsWithMostLandings(final int airports);
	public void getAirportsWithMostInternationalLandings(final int airports);
	public void getProvincesPairsWithMovements(final int minMovements);
}

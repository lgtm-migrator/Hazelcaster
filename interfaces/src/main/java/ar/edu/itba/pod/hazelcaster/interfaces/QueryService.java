package ar.edu.itba.pod.hazelcaster.interfaces;

import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

	/**
	* <p>Las 6 queries requeridas por la cátedra.</p>
	*/

public interface QueryService {

	public List<MoveCountOutput> getAirportsMovements() 
			throws InterruptedException, ExecutionException;
	
	public List<SameMovesPairOutput> getAirportsPairsWithSameMovements() 
			throws InterruptedException, ExecutionException;
	
	public List<MovesBetweenAirportsOutput> getMovementsBetweenAirports() 
			throws InterruptedException, ExecutionException;

	public List<LandingMoveCountOutput> getAirportsWithMostLandings(final String oaci, final int airports) 
			throws InterruptedException, ExecutionException;
	
	public void getAirportsWithMostInternationalLandings(final int airports);
	public void getProvincesPairsWithMovements(final int minMovements);
}

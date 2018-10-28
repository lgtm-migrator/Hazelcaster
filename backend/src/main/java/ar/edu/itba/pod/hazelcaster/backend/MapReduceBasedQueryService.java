package ar.edu.itba.pod.hazelcaster.backend;

import ar.edu.itba.pod.hazelcaster.interfaces.QueryService;
import org.springframework.stereotype.Service;

	/**
	* <p>Implementación concreta de las consultas, basada en una arquitectura
	* <i>map-reduce</i>.</p>
	*/

@Service
public class MapReduceBasedQueryService implements QueryService {

	@Override
	public void getAirportsMovements() {
		// TODO Auto-generated method stub
	}

	@Override
	public void getAirportsPairsWithSameMovements() {
		// TODO Auto-generated method stub
	}

	@Override
	public void getMovementsBetweenAirports() {
		// TODO Auto-generated method stub
	}

	@Override
	public void getAirportsWithMostLandings(final String oaci, final int airports) {
		// TODO Auto-generated method stub
	}

	@Override
	public void getAirportsWithMostInternationalLandings(final int airports) {
		// TODO Auto-generated method stub
	}

	@Override
	public void getProvincesPairsWithMovements(final int minMovements) {
		// TODO Auto-generated method stub	
	}
}

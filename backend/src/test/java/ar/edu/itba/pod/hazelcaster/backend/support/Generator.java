package ar.edu.itba.pod.hazelcaster.backend.support;

import ar.edu.itba.pod.hazelcaster.abstractions.Airport;
import ar.edu.itba.pod.hazelcaster.abstractions.FlightType;
import ar.edu.itba.pod.hazelcaster.abstractions.Movement;
import ar.edu.itba.pod.hazelcaster.abstractions.MovementType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.text.CharacterPredicates;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.stereotype.Service;

@Service
public class Generator {

	protected static final FlightType [] flightTypes = FlightType.values();
	protected static final MovementType [] movementTypes = MovementType.values();
	protected static final String [] provinces = {
		"C.A.B.A.", "Buenos Aires", "Catamarca", "Chaco", "Chubut", "Córdoba",
		"Corrientes", "Entre Ríos", "Formosa", "Jujuy", "La Pampa", "La Rioja",
		"Mendoza", "Misiones", "Neuquén", "Río Negro", "Salta", "San Juan",
		"San Luis", "Santa Cruz", "Santa Fe", "Santiago del Estero",
		"Tierra del Fuego", "Tucumán"
	};

	protected final RandomStringGenerator stringGenerator;
	protected final Random numberGenerator;

	public Generator() {
		this.stringGenerator = new RandomStringGenerator.Builder()
				.withinRange('0', 'z')
				.filteredBy(CharacterPredicates.ASCII_ALPHA_NUMERALS)
				.build();
		this.numberGenerator = new Random();
	}

	public List<Airport> generateAirports(final int size) {
		final List<Airport> airports = new ArrayList<>();
		final Set<String> oacis = new HashSet<>();
		final Set<String> iatas = new HashSet<>();
		while (airports.size() < size) {
			final String oaci = stringGenerator.generate(0, 4).toUpperCase();
			final String iata = stringGenerator.generate(0, 3).toUpperCase();
			if (!oacis.contains(oaci) && !iatas.contains(iata)) {
				oacis.add(oaci);
				iatas.add(iata);
				airports.add(generateAirportWith(oaci, iata));
			}
		}
		return airports;
	}

	public Airport generateAirportWith(final String oaci, final String iata) {
		return new Airport.Builder()
				.oaci(oaci)
				.iata(iata)
				.denomination(stringGenerator.generate(20, 40))
				.province(provinces[numberGenerator.nextInt(provinces.length)])
				.build();
	}

	public List<Movement> generateMovements(final List<Airport> airports, final int size) {
		final List<Movement> movements = new ArrayList<>();
		final List<String> oacis = airports.stream()
				.map(Airport::getOACI)
				.filter(oaci -> !oaci.isEmpty())
				.collect(Collectors.toList());
		final int oacisSize = oacis.size();
		for (int k = 0; k < oacisSize; ++k) {
			oacis.add(stringGenerator.generate(4).toUpperCase());
		}
		for (int k = 0; k < size; ++k) {
			movements.add(new Movement.Builder()
				.classification(flightTypes[numberGenerator.nextInt(flightTypes.length)])
				.type(movementTypes[numberGenerator.nextInt(movementTypes.length)])
				.origin(oacis.get(numberGenerator.nextInt(oacis.size())))
				.destination(oacis.get(numberGenerator.nextInt(oacis.size())))
				.build());
		}
		return movements;
	}

	public Map<String, String> generateIATAToOACIMapping(final List<Airport> airports) {
		return airports.stream()
				.filter(airport -> !airport.getOACI().isEmpty())
				.filter(airport -> !airport.getIATA().isEmpty())
				.collect(Collectors.toMap(Airport::getIATA, Airport::getOACI));
	}
}

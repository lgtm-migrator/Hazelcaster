package ar.edu.itba.pod.hazelcaster.abstractions.collators;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.TreeSet;

import com.hazelcast.mapreduce.Collator;

import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.MovesBetweenAirportsOutput;

public class MovesBetweenAirportsCollator implements Collator<Map.Entry<String, MovesBetweenAirportsOutput>, List<MovesBetweenAirportsOutput>> {

	@Override
	public List<MovesBetweenAirportsOutput> collate(Iterable<Entry<String, MovesBetweenAirportsOutput>> values) {
		
		TreeSet<MovesBetweenAirportsOutput> result = new TreeSet<>();
		values.forEach(entry -> {
			result.add(entry.getValue());
		});
		
		return result.stream().collect(Collectors.toList());
	}

}

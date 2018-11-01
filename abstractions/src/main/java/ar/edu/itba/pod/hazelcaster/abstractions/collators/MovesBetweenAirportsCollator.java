package ar.edu.itba.pod.hazelcaster.abstractions.collators;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.TreeSet;

import com.hazelcast.mapreduce.Collator;

import ar.edu.itba.pod.hazelcaster.abstractions.LongPair;
import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.MovesBetweenAirportsOutput;
import utils.OaciPairKeyManager;

public class MovesBetweenAirportsCollator implements Collator<Map.Entry<String, LongPair>, List<MovesBetweenAirportsOutput>> {

	@Override
	public List<MovesBetweenAirportsOutput> collate(Iterable<Entry<String, LongPair>> values) {
		
		TreeSet<MovesBetweenAirportsOutput> result = new TreeSet<>();
		values.forEach(entry -> {
			String[] oacis = OaciPairKeyManager.getOaciPair(entry.getKey());
			result.add(new MovesBetweenAirportsOutput(
					oacis[0], 
					oacis[1], 
					entry.getValue().getA(), 
					entry.getValue().getB()));
		});
		
		return result.stream().collect(Collectors.toList());
	}

}

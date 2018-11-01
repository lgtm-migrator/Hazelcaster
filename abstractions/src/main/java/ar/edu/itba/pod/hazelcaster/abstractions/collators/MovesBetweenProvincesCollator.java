package ar.edu.itba.pod.hazelcaster.abstractions.collators;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.TreeSet;

import com.hazelcast.mapreduce.Collator;

import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.MovesBetweenProvincesOutput;
import utils.StringPairKeyManager;

public class MovesBetweenProvincesCollator implements Collator<Map.Entry<String, Long>, List<MovesBetweenProvincesOutput>> {

	private final int min;
	
	public MovesBetweenProvincesCollator(final int min) {
		this.min = min;
	}
	
	@Override
	public List<MovesBetweenProvincesOutput> collate(Iterable<Entry<String, Long>> values) {
		
		TreeSet<MovesBetweenProvincesOutput> result = new TreeSet<>();
		
		values.forEach(entry -> {	
			if (entry.getValue() >= min) {
				String[] provinces = StringPairKeyManager.getStringPair(entry.getKey());
				
				result.add(new MovesBetweenProvincesOutput(
						provinces[0],
						provinces[1],
						entry.getValue()));
			}	
		});
		
		return result.stream().collect(Collectors.toList());
	}

}

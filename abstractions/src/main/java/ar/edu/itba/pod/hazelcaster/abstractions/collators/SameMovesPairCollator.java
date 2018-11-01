package ar.edu.itba.pod.hazelcaster.abstractions.collators;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;
import java.util.stream.Collectors;

import com.hazelcast.mapreduce.Collator;

import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.SameMovesPairOutput;

public class SameMovesPairCollator implements Collator<Map.Entry<Long, List<SameMovesPairOutput>>, List<SameMovesPairOutput>> {

	@Override
	public List<SameMovesPairOutput> collate(Iterable<Entry<Long, List<SameMovesPairOutput>>> values) {
		
		TreeSet<SameMovesPairOutput> result = new TreeSet<>();
		values.forEach(entry -> {
			for (SameMovesPairOutput output : entry.getValue()) {
				result.add(output);
			}
		});
		
		return result.stream().collect(Collectors.toList());
	}

	

}

package ar.edu.itba.pod.hazelcaster.abstractions.collators;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.TreeSet;

import com.hazelcast.mapreduce.Collator;

import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.LandingMoveCountOutput;

public class LandingMoveCountCollator implements Collator<Map.Entry<String,Long>,List<LandingMoveCountOutput>> {

	private final int limit;
	
	public LandingMoveCountCollator(final int limit) {
		this.limit = limit;
	}
	
	@Override
	public List<LandingMoveCountOutput> collate(Iterable<Entry<String, Long>> values) {

		TreeSet<LandingMoveCountOutput> result = new TreeSet<>();
		values.forEach(entry -> {
			result.add(new LandingMoveCountOutput(entry.getKey(), entry.getValue()));
		});
		
		return result.parallelStream().sorted().collect(Collectors.toList()).subList(0, Math.min(limit, result.size()));
	}

}

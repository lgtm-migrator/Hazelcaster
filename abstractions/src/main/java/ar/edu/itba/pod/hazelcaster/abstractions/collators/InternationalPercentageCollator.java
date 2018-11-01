package ar.edu.itba.pod.hazelcaster.abstractions.collators;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.TreeSet;

import com.hazelcast.mapreduce.Collator;

import ar.edu.itba.pod.hazelcaster.abstractions.LongPair;
import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.InternationalPercentageOutput;

public class InternationalPercentageCollator implements Collator<Map.Entry<String, LongPair>, List<InternationalPercentageOutput>> {

	protected final Map<String, String> oaciIataMap;
	protected final int airports;
	
	public InternationalPercentageCollator(final Map<String, String> oaciIataMap,
			final int airports) {
		this.oaciIataMap = oaciIataMap;
		this.airports = airports;
	}
	
	@Override
	public List<InternationalPercentageOutput> collate(Iterable<Entry<String, LongPair>> values) {
		
		TreeSet<InternationalPercentageOutput> result = new TreeSet<>();
		values.forEach(entry -> {
			
			String iata = oaciIataMap.get(entry.getKey());
			
			if (iata != null) {
				double internationals = (double) entry.getValue().getB();
				double total = (double) entry.getValue().getA();
												
				if (total != 0) {
					double percentage = (internationals/total)*100;
					result.add(new InternationalPercentageOutput(iata, (int)percentage));
				}
			}
		});
		
		return result.parallelStream().sorted().collect(Collectors.toList()).subList(0, Math.min(airports, result.size()));	
	}

}

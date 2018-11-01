package ar.edu.itba.pod.hazelcaster.abstractions.collators;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.TreeSet;

import com.hazelcast.mapreduce.Collator;

import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.MoveCountOutput;

public class MoveCountCollator implements Collator<Map.Entry<String,MoveCountOutput>,List<MoveCountOutput>> {

	protected final Map<String, String> oaciDenominationMap;
	
	public MoveCountCollator(final Map<String, String> oaciDenominationMap) {
		this.oaciDenominationMap = oaciDenominationMap;
	}
	
	@Override
	public List<MoveCountOutput> collate(Iterable<Entry<String, MoveCountOutput>> values) {
		
		TreeSet<MoveCountOutput> result = new TreeSet<>();
		values.forEach(entry -> {
			
			MoveCountOutput entryValue = entry.getValue();
			String denomination = oaciDenominationMap.get(entry.getKey());
			
			if (denomination == null) {
				denomination = "";
			}
			
			entryValue.setDenomination(denomination);
			result.add(entryValue);
		});
		
		return result.parallelStream().collect(Collectors.toList());
	}

}

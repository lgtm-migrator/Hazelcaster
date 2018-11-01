package ar.edu.itba.pod.hazelcaster.abstractions.collators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hazelcast.mapreduce.Collator;

public class OaciDenominationCollator implements Collator<Map.Entry<String,List<String>>, Map<String,String>> {

	@Override
	public Map<String, String> collate(Iterable<Map.Entry<String, List<String>>> values) {
		
		Map<String, String> result = new HashMap<>();
		values.forEach(entry -> {
			result.put(entry.getKey(), entry.getValue().get(0));
		});
		
		return result;
	}

}

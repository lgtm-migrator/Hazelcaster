package ar.edu.itba.pod.hazelcaster.abstractions.mappers;

import java.util.Map;

import com.hazelcast.mapreduce.Context;
import com.hazelcast.mapreduce.Mapper;

import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.MovesBetweenAirportsOutput;
import utils.StringPairKeyManager;

public class MovesBetweenProvincesMapper implements Mapper<String, MovesBetweenAirportsOutput, String, Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5577808889026186363L;

	private final Map<String, String> oaciProvinceMap;
	
	public MovesBetweenProvincesMapper(final Map<String, String> oaciProvinceMap) {
		this.oaciProvinceMap = oaciProvinceMap;
	}
	
	@Override
	public void map(String key, MovesBetweenAirportsOutput value, Context<String, Long> context) {
		
		String provA = oaciProvinceMap.get(value.getOaciA());
		String provB = oaciProvinceMap.get(value.getOaciB());
		
		if (provA == null || provB == null || provA.equals(provB)) {
			return;
		}
		
		context.emit(
				StringPairKeyManager.getKey(provA, provB), 
				value.getCountAB() + value.getCountBA());
	}

}

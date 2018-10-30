package ar.edu.itba.pod.hazelcaster.backend.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import ar.edu.itba.pod.hazelcaster.abstractions.Airport;
import ar.edu.itba.pod.hazelcaster.abstractions.Query1ResultType;
import ar.edu.itba.pod.hazelcaster.abstractions.Query2ResultType;

@Service
public class ResultTransformerService {

	@SuppressWarnings("rawtypes")
	public SortedSet<Query1ResultType> Query1Transform(
			Map<Query1ResultType, Integer> inputMap, 
			List<Airport> airportsList) {
		
		SortedSet<Query1ResultType> result = new TreeSet<>();
		Iterator it = inputMap.entrySet().iterator();
	    while (it.hasNext()) {
	    	Map.Entry pair = (Map.Entry)it.next();
	    	String oaci = ((Query1ResultType)pair.getKey()).getOaci();
	    	String desc = "";
	    	//TODO: change for better algorithm like binary search
	    	for(Airport a : airportsList) {
	    		if(a.getOACI().equals(oaci)) {
	    			desc = a.getDenomination();
	    			break;
	    		}
	    	}
	    	Integer amount = ((Integer)pair.getValue());
	    	result.add(new Query1ResultType(oaci, desc, amount));
	    }
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public SortedSet<Query2ResultType> Query2Transform(
			Map<Query1ResultType, Integer> inputMap, 
			List<Airport> airportsList) {
		
		SortedSet<Query2ResultType> result = new TreeSet<>();
		Iterator it = inputMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry next = (Entry) it.next();
	        it.remove();
	        for (Map.Entry e : inputMap.entrySet()) {
	            e.equals(next);
	            Query1ResultType q1 = (Query1ResultType) next.getKey();
	            Query1ResultType q2 = (Query1ResultType) e.getKey();
	            Integer a1 = (Integer) next.getValue();
	            Integer a2 = (Integer) e.getValue();
	            int a1Mod = a1%1000;
	            if( (a1Mod) == (a2%1000) && (a1Mod > 0)) {
	            	if(q1.getOaci().compareTo(q2.getOaci()) < 0) {
	            		result.add(new Query2ResultType(a1Mod, q1.getOaci(), q2.getOaci()));
	            	}
	            	else result.add(new Query2ResultType(a1Mod, q2.getOaci(), q1.getOaci()));
	            }
	        }
	    }
		return result;
	}
}

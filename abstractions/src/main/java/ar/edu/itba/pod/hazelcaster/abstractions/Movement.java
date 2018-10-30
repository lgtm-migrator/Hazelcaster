package ar.edu.itba.pod.hazelcaster.abstractions;

import java.io.IOException;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

public class Movement implements DataSerializable{

	String OACI;
	String description;
	
	public Movement() {
	}
	
	public Movement(String OACI, String description) {
		this.OACI = OACI;
		this.description = description;
	}
	
	@Override
	public void writeData(ObjectDataOutput out) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readData(ObjectDataInput in) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public String getOACI() {
		return this.OACI;
	}
	
	public void setOACI(String OACI) {
		this.OACI = OACI;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
	    if (o == this) return true;
	    if (!(o instanceof Movement))return false;
	    Movement m = (Movement)o;
		return this.OACI.equals(m.getOACI());
	}
}

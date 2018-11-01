package ar.edu.itba.pod.hazelcaster.abstractions.outputObjects;

import java.io.IOException;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

public class LandingMoveCountOutput implements DataSerializable, Comparable<LandingMoveCountOutput> {
	
	protected String oaci;
	protected Long count;
	
	public LandingMoveCountOutput() {}
	
	public LandingMoveCountOutput(String oaci, Long count) {
		this.oaci = oaci;
		this.count = count;
	}

	@Override
	public void writeData(ObjectDataOutput out) throws IOException {
		out.writeUTF(oaci);
		out.writeLong(count);
	}

	@Override
	public void readData(ObjectDataInput in) throws IOException {
		oaci = in.readUTF();
		count = in.readLong();
	}
	
	public String getOaci() {
		return oaci;
	}

	public void setOaci(String oaci) {
		this.oaci = oaci;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	@Override
	public int compareTo(LandingMoveCountOutput other) {
		if (this.count > other.getCount()) {
			return -1;
		}
		if (this.count < other.getCount()) {
			return 1;
		}
		return this.oaci.compareTo(other.getOaci());
	}

}

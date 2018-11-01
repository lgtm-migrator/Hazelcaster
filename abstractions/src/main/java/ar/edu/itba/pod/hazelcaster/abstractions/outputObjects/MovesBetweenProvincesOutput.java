package ar.edu.itba.pod.hazelcaster.abstractions.outputObjects;

import java.io.IOException;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

public class MovesBetweenProvincesOutput 
		implements DataSerializable, Comparable<MovesBetweenProvincesOutput> {
	
	protected String provA;
	protected String provB;
	protected Long count;
	
	public MovesBetweenProvincesOutput() {}
	
	public MovesBetweenProvincesOutput(String provA, String provB, Long count) {
		this.provA = provA;
		this.provB = provB;
		this.count = count;
	}

	@Override
	public void writeData(ObjectDataOutput out) throws IOException {
		out.writeUTF(provA);
		out.writeUTF(provB);
		out.writeLong(count);
	}

	@Override
	public void readData(ObjectDataInput in) throws IOException {
		provA = in.readUTF();
		provB = in.readUTF();
		count = in.readLong();
	}
	
	public String getProvA() {
		return provA;
	}

	public void setProvA(String provA) {
		this.provA = provA;
	}

	public String getProvB() {
		return provB;
	}

	public void setProvB(String provB) {
		this.provB = provB;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	@Override
	public int compareTo(MovesBetweenProvincesOutput other) {
		if (this.count > other.getCount()) {
			return -1;
		}
		if (this.count < other.getCount()) {
			return 1; 
		}
		return (this.provA + this.provB).compareTo(other.getProvA() + other.getProvB());
	}

	@Override
	public String toString() {
		return "MovesBetweenProvincesOutput [provA=" + provA + ", provB=" + provB + ", count=" + count + "]";
	}

}

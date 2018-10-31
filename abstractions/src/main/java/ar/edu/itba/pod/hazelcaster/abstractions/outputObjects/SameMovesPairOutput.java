package ar.edu.itba.pod.hazelcaster.abstractions.outputObjects;

import java.io.IOException;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

public class SameMovesPairOutput implements DataSerializable, Comparable<SameMovesPairOutput> {

	protected Long count;
	protected String oaciA;
	protected String oaciB;
	
	public SameMovesPairOutput() {}
	
	public SameMovesPairOutput(Long count, String oaciA, String oaciB) {
		this.count = count;
		this.oaciA = oaciA;
		this.oaciB = oaciB;
	}
	
	@Override
	public void writeData(ObjectDataOutput out) throws IOException {
		out.writeLong(count);
		out.writeUTF(oaciA);
		out.writeUTF(oaciB);
	}

	@Override
	public void readData(ObjectDataInput in) throws IOException {
		count = in.readLong();
		oaciA = in.readUTF();
		oaciB = in.readUTF();
	}
	
	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getOaciA() {
		return oaciA;
	}

	public void setOaciA(String oaciA) {
		this.oaciA = oaciA;
	}

	public String getOaciB() {
		return oaciB;
	}

	public void setOaciB(String oaciB) {
		this.oaciB = oaciB;
	}

	@Override
	public int compareTo(SameMovesPairOutput other) {
		if (this.count > other.getCount()) {
			return -1;
		}
		if (this.count < other.getCount()) {
			return 1;
		}
		return (this.oaciA + this.oaciB).compareTo(other.getOaciA() + other.getOaciB());
	}

	@Override
	public String toString() {
		return "SameMovesPairOutput [count=" + count + ", oaciA=" + oaciA + ", oaciB=" + oaciB + "]";
	}

}

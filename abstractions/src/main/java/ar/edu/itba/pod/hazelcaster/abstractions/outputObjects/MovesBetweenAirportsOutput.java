package ar.edu.itba.pod.hazelcaster.abstractions.outputObjects;

import java.io.IOException;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

public class MovesBetweenAirportsOutput 
		implements DataSerializable, Comparable<MovesBetweenAirportsOutput> {

	protected String oaciA;
	protected String oaciB;
	protected Long countAB;
	protected Long countBA;
	
	public MovesBetweenAirportsOutput() {}
	
	public MovesBetweenAirportsOutput(String oaciA, String oaciB, 
			Long countAB, Long countBA) {
		this.oaciA = oaciA;
		this.oaciB = oaciB;
		this.countAB = countAB;
		this.countBA = countBA;
	}

	@Override
	public void writeData(ObjectDataOutput out) throws IOException {
		out.writeUTF(oaciA);
		out.writeUTF(oaciB);
		out.writeLong(countAB);
		out.writeLong(countBA);
	}

	@Override
	public void readData(ObjectDataInput in) throws IOException {
		oaciA = in.readUTF();
		oaciB = in.readUTF();
		countAB = in.readLong();
		countBA = in.readLong();
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

	public Long getCountAB() {
		return countAB;
	}

	public void setCountAB(Long countAB) {
		this.countAB = countAB;
	}

	public Long getCountBA() {
		return countBA;
	}

	public void setCountBA(Long countBA) {
		this.countBA = countBA;
	}

	@Override
	public int compareTo(MovesBetweenAirportsOutput other) {
		int firstCompare = this.oaciA.compareTo(other.getOaciA());
		if (firstCompare != 0) {
			return firstCompare;
		}
		return this.oaciB.compareTo(other.getOaciB());
	}

	@Override
	public String toString() {
		return "MovesBetweenAirportsOutput [oaciA=" + oaciA + ", oaciB=" + oaciB + ", countAB=" + countAB + ", countBA="
				+ countBA + "]";
	}

}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((oaciA == null) ? 0 : oaciA.hashCode());
		result = prime * result + ((oaciB == null) ? 0 : oaciB.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovesBetweenAirportsOutput other = (MovesBetweenAirportsOutput) obj;
		if (countAB == null) {
			if (other.countAB != null)
				return false;
		} else if (!countAB.equals(other.countAB))
			return false;
		if (countBA == null) {
			if (other.countBA != null)
				return false;
		} else if (!countBA.equals(other.countBA))
			return false;
		if (oaciA == null) {
			if (other.oaciA != null)
				return false;
		} else if (!oaciA.equals(other.oaciA))
			return false;
		if (oaciB == null) {
			if (other.oaciB != null)
				return false;
		} else if (!oaciB.equals(other.oaciB))
			return false;
		return true;
	}

}

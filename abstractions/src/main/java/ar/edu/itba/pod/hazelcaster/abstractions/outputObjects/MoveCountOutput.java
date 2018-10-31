package ar.edu.itba.pod.hazelcaster.abstractions.outputObjects;

import java.io.IOException;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

public class MoveCountOutput implements DataSerializable, Comparable<MoveCountOutput> {
	
	protected String oaci;
	protected String denomination;
	protected Long count;
	
	public MoveCountOutput() {}
	
	public MoveCountOutput(String oaci, String denomination, Long count) {
		this.oaci = oaci;
		this.denomination = denomination;
		this.count = count;
	}

	@Override
	public void writeData(ObjectDataOutput out) throws IOException {
		out.writeUTF(oaci);
		out.writeUTF(denomination);
		out.writeUTF(count.toString());
	}

	@Override
	public void readData(ObjectDataInput in) throws IOException {
		oaci = in.readUTF();
		denomination = in.readUTF();
		count = Long.valueOf(in.readUTF());
	}

	public String getOaci() {
		return oaci;
	}

	public void setOaci(String oaci) {
		this.oaci = oaci;
	}

	public String getDenomination() {
		return denomination;
	}

	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "oaci=" + oaci + ";denomination=" + denomination + ";count=" + count;
	}

	@Override
	public int compareTo(MoveCountOutput other) {
		if (this.count > other.getCount()) {
			return -1;
		}
		if (this.count < other.getCount()) {
			return 1;
		}
		return this.oaci.compareTo(other.getOaci());
	}

}

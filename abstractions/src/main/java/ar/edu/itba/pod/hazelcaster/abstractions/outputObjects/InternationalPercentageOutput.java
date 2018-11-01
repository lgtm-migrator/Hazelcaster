package ar.edu.itba.pod.hazelcaster.abstractions.outputObjects;

import java.io.IOException;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

public class InternationalPercentageOutput implements DataSerializable, Comparable<InternationalPercentageOutput> {

	protected String iata;
	protected Integer percentage;
	
	public InternationalPercentageOutput() {}
	
	public InternationalPercentageOutput(String iata, Integer percentage) {
		this.iata = iata;
		this.percentage = percentage;
	}

	@Override
	public void writeData(ObjectDataOutput out) throws IOException {
		out.writeUTF(iata);
		out.writeInt(percentage);
	}

	@Override
	public void readData(ObjectDataInput in) throws IOException {
		iata = in.readUTF();
		percentage = in.readInt();
	}
	
	public String getIata() {
		return iata;
	}

	public void setIata(String iata) {
		this.iata = iata;
	}

	public Integer getPercentage() {
		return percentage;
	}

	public void setPercentage(Integer percentage) {
		this.percentage = percentage;
	}

	@Override
	public int compareTo(InternationalPercentageOutput other) {
		if (this.percentage > other.getPercentage()) {
			return -1;
		}
		if (this.percentage < other.getPercentage()) {
			return 1;
		}
		return this.iata.compareTo(other.getIata());
	}

	@Override
	public String toString() {
		return "InternationalPercentageOutput [iata=" + iata + ", percentage=" + percentage + "]";
	}
	
}

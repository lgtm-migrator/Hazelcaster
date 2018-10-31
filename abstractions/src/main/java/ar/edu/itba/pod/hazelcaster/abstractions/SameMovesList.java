package ar.edu.itba.pod.hazelcaster.abstractions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

public class SameMovesList implements DataSerializable, Comparable<SameMovesList> {
	
	protected Long count;
	protected List<String> oaciList;
	
	public SameMovesList() {}
	
	public SameMovesList(Long count, List<String> oaciList) {
		this.count = count;
		this.oaciList = oaciList;
	}

	@Override
	public void writeData(ObjectDataOutput out) throws IOException {
		out.writeLong(count);
		out.writeInt(oaciList.size());
		for (String oaci : oaciList) {
			out.writeUTF(oaci);
		}
	}

	@Override
	public void readData(ObjectDataInput in) throws IOException {
		count = in.readLong();
		int size = in.readInt();
		oaciList = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			oaciList.add(in.readUTF());
		}
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<String> getOaciList() {
		return oaciList;
	}

	public void setOaciList(List<String> oaciList) {
		this.oaciList = oaciList;
	}
	
	

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder()
				.append("Count: ").append(count)
				.append("OaciList:");
		for (String oaci : oaciList) {
			sb.append(" " + oaci);
		}
		return sb.toString();
	}

	@Override
	public int compareTo(SameMovesList other) {
		if (this.count > other.getCount()) {
			return -1;
		}
		if (this.count < other.getCount()) {
			return 1;
		}
		return 0;
	}

}

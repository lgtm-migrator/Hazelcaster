package ar.edu.itba.pod.hazelcaster.abstractions;

import java.io.IOException;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

public class LongPair implements DataSerializable {
	
	protected Long a;
	protected Long b;
	
	public LongPair() {/* For Hazelcast */}
	
	public LongPair(Long a, Long b) {
		this.a = a;
		this.b = b;
	}

	public Long getA() {
		return a;
	}

	public void setA(Long a) {
		this.a = a;
	}

	public Long getB() {
		return b;
	}

	public void setB(Long b) {
		this.b = b;
	}

	@Override
	public void writeData(ObjectDataOutput out) throws IOException {
		out.writeLong(a);
		out.writeLong(b);
	}

	@Override
	public void readData(ObjectDataInput in) throws IOException {
		a = in.readLong();
		b = in.readLong();
	}

}

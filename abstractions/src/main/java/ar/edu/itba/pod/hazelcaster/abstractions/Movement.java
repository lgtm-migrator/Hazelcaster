package ar.edu.itba.pod.hazelcaster.abstractions;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;
import java.io.IOException;

public class Movement implements DataSerializable {

	protected FlightType classification;
	protected MovementType type;
	protected String origin;
	protected String destination;

	public Movement() {/* For Hazelcast */}

	public Movement(final Builder builder) {
		this.classification = builder.classification;
		this.type = builder.type;
		this.origin = builder.origin;
		this.destination = builder.destination;
	}

	@Override
	public void writeData(final ObjectDataOutput out)
			throws IOException {
		out.writeByte(classification.getID());
		out.writeByte(type.getID());
		out.writeUTF(origin);
		out.writeUTF(destination);
	}

	@Override
	public void readData(final ObjectDataInput in)
			throws IOException {
		classification = FlightType.fromID(in.readByte());
		type = MovementType.fromID(in.readByte());
		origin = in.readUTF();
		destination = in.readUTF();
	}

	public FlightType getClassification() {
		return classification;
	}

	public MovementType getType() {
		return type;
	}

	public String getOrigin() {
		return origin;
	}

	public String getDestination() {
		return destination;
	}

	public static class Builder {

		protected FlightType classification;
		protected MovementType type;
		protected String origin;
		protected String destination;

		public Movement build() {
			return new Movement(this);
		}

		public Builder classification(final FlightType classification) {
			this.classification = classification;
			return this;
		}

		public Builder type(final MovementType type) {
			this.type = type;
			return this;
		}

		public Builder origin(final String origin) {
			this.origin = origin;
			return this;
		}

		public Builder destination(final String destination) {
			this.destination = destination;
			return this;
		}
	}
}

package ar.edu.itba.pod.hazelcaster.abstractions;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;
import java.io.IOException;

public class Airport implements DataSerializable {

	protected String oaci;
	protected String iata;
	protected String denomination;
	protected String province;

	public Airport() {/* For Hazelcast */}

	public Airport(final Builder builder) {
		this.oaci = builder.oaci;
		this.iata = builder.iata;
		this.denomination = builder.denomination;
		this.province = builder.province;
	}

	@Override
	public void writeData(final ObjectDataOutput out)
			throws IOException {
		out.writeUTF(oaci);
		out.writeUTF(iata);
		out.writeUTF(denomination);
		out.writeUTF(province);
	}

	@Override
	public void readData(final ObjectDataInput in)
			throws IOException {
		oaci = in.readUTF();
		iata = in.readUTF();
		denomination = in.readUTF();
		province = in.readUTF();
	}

	public String getOACI() {
		return oaci;
	}

	public String getIATA() {
		return iata;
	}

	public String getDenomination() {
		return denomination;
	}

	public String getProvince() {
		return province;
	}

	public static class Builder {

		protected String oaci;
		protected String iata;
		protected String denomination;
		protected String province;

		public Airport build() {
			return new Airport(this);
		}

		public Builder oaci(final String oaci) {
			this.oaci = oaci;
			return this;
		}

		public Builder iata(final String iata) {
			this.iata = iata;
			return this;
		}

		public Builder denomination(final String denomination) {
			this.denomination = denomination;
			return this;
		}

		public Builder province(final String province) {
			this.province = province;
			return this;
		}
	}
}

package ar.edu.itba.pod.hazelcaster.abstractions;

public class Query1ResultType implements Comparable<Query1ResultType>{

	String oaci;
	String description;
	Integer amount;
	
	public Query1ResultType(String oaci) {
		this.oaci = oaci;
	}
	
	public Query1ResultType(String oaci, String description, Integer amount) {
		this.oaci = oaci;
		this.description = description;
		this.amount = amount;
	}
	
 	public String getOaci() {
		return this.oaci;
	}
	
	public void setOaci(String oaci) {
		this.oaci = oaci;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Integer getAmount() {
		return this.amount;
	}
	
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
	    if (o == this) return true;
	    if (!(o instanceof Movement))return false;
	    Query1ResultType m = (Query1ResultType)o;
		return this.oaci.equals(m.getOaci());
	}

	@Override
	public int compareTo(Query1ResultType o) {
		if(this.amount > o.getAmount()) {
			return -1;
		}
		else if(this.amount < o.getAmount()) {
			return 1;
		}
		else return this.getOaci().compareTo(o.getOaci());
	}
}

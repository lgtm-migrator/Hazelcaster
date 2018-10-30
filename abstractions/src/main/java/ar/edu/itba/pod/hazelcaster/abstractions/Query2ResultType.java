package ar.edu.itba.pod.hazelcaster.abstractions;

public class Query2ResultType implements Comparable<Query2ResultType>{

	Integer amount;
	String oaciA;
	String oaciB;
	
	public Query2ResultType(Integer amount, String oaciA, String oaciB) {
		this.amount = amount;
		this.oaciA = oaciA;
		this.oaciB = oaciB;
	}
	
	public Integer getAmount() {
		return this.amount;
	}
	
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	public String getOaciA() {
		return this.oaciA;
	}
	
	public void setOaciA(String oaciA) {
		this.oaciA = oaciA;
	}
	
	public String getOaciB() {
		return this.oaciB;
	}
	
	public void setOaciB(String oaciB) {
		this.oaciB = oaciB;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
	    if (o == this) return true;
	    if (!(o instanceof Movement))return false;
	    Query2ResultType m = (Query2ResultType)o;
		return this.oaciA.equals(m.getOaciA()) 
				&& this.oaciB.equals(m.getOaciB());
	}

	@Override
	public int compareTo(Query2ResultType o) {
		if(this.amount > o.getAmount()) {
			return -1;
		}
		else if(this.amount < o.getAmount()) {
			return 1;
		}
		else if(this.getOaciA().compareTo(o.getOaciA()) < 0) {
			return -1;
		}
		else if(this.getOaciA().compareTo(o.getOaciA()) > 0) {
			return 1;
		}
		else return this.getOaciB().compareTo(o.getOaciB());
	}
}

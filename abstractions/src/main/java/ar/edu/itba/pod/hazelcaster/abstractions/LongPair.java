package ar.edu.itba.pod.hazelcaster.abstractions;

public class LongPair {
	
	private Long a;
	private Long b;
	
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

}

package ar.edu.itba.pod.hazelcaster.abstractions.exception;

public class UnmappedTypeException extends Exception {

	private static final long serialVersionUID = 1L;

	public UnmappedTypeException(final Class<?> type) {
		super(String.format("No se puede mapear hacia un objeto de tipo '%s'.",
				type.getCanonicalName()));
	}
}

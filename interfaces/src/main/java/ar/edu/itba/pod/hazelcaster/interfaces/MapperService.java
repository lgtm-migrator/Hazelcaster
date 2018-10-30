package ar.edu.itba.pod.hazelcaster.interfaces;

import ar.edu.itba.pod.hazelcaster.abstractions.exception.UnmappedTypeException;
import java.util.List;

	/**
	* <p>Interface que define un servicio bajo el cual se pueden registrar
	* diferentes serializadores/deserializadores para un formato
	* específico. Debido a que cada mapeo posee información completa sobre el
	* tipo, puede aplicar operaciones adicionales, como filtrado de datos,
	* cohersión de tipos, etc.</p>
	*
	* @param <R>
	*	Un objeto para un tipo soportado, se puede traducir a otro objeto de
	*	tipo <i>R</i> (serializar), y viceversa (deserializar). En todos los
	*	casos, la deserialización implica obtener el tipo original.
	*/

public interface MapperService<R> {

	public <O> MapperService<R> addMapper(final Class<O> type, final Mapper<R, O> mapper);

	public <O> List<O> toObjects(final List<R> registers, final Class<O> type)
			throws UnmappedTypeException;

	public <O> List<R> toRegisters(final List<O> objects)
			throws UnmappedTypeException;

	public <O> R getMetadata(final Class<O> type)
			throws UnmappedTypeException;

	/**
	* <p>Define un mapeador específico, donde la serialización implica una
	* conversión <i>O -> R</i> y la deserialización implica <i>R -> O</i>.</p>
	*
	* @param <R>
	*	El tipo a deserializar (mapear).
	* @param <O>
	*	El tipo a serializar.
	*/
	public interface Mapper<R, O> {

		/** Deserialización (<i>R -> O</i>). */
		public O map(final R register);

		/** Serialización (<i>O -> R</i>). */
		public R unmap(final O object);

		/** Metadata para la serialización del tipo <i>O</i>. */
		public R metadata();
	}
}

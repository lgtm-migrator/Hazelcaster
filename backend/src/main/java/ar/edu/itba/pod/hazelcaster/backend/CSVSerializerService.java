package ar.edu.itba.pod.hazelcaster.backend;

import static java.util.stream.Collectors.toList;
import ar.edu.itba.pod.hazelcaster.backend.mapper.*;
import ar.edu.itba.pod.hazelcaster.abstractions.Airport;
import ar.edu.itba.pod.hazelcaster.abstractions.Movement;
import ar.edu.itba.pod.hazelcaster.abstractions.exception.UnmappedTypeException;
import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.MoveCountOutput;
import ar.edu.itba.pod.hazelcaster.abstractions.outputObjects.SameMovesPairOutput;
import ar.edu.itba.pod.hazelcaster.interfaces.CSVSerializer;
import ar.edu.itba.pod.hazelcaster.interfaces.MapperService;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

	/**
	* <p>Servicio que permite serializar y deserializar objetos desde y hacia
	* archivos en formato <i>CSV</i>.</p>
	*/

@Service
public class CSVSerializerService implements CSVSerializer {

	private static final Logger logger
		= LoggerFactory.getLogger(CSVSerializerService.class);

	protected final Map<Class<?>, Mapper<String, ?>> mappers;

	public CSVSerializerService() {
		this.mappers = new HashMap<>();
		addMapper(Airport.class, new AirportMapper());
		addMapper(Movement.class, new MovementMapper());
		addMapper(MoveCountOutput.class, new MoveCountOutputMapper());
		addMapper(SameMovesPairOutput.class, new SameMovesPairOutputMapper());
		// Agregar más mappers...
	}

	@Override
	public <O> MapperService<String> addMapper(
			final Class<O> type, final Mapper<String, O> mapper) {
		mappers.put(type, mapper);
		return this;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <O> List<O> toObjects(final List<String> registers, final Class<O> type)
			throws UnmappedTypeException {
		final Mapper<String, O> mapper = (Mapper<String, O>) mappers.get(type);
		if (mapper != null) {
			return registers.stream()
					.map(mapper::map)
					.collect(toList());
		}
		else throw new UnmappedTypeException(type);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <O> List<String> toRegisters(final List<O> objects)
			throws UnmappedTypeException {
		if (objects.isEmpty()) return Collections.emptyList();
		else {
			final Class<?> type = objects.get(0).getClass();
			final Mapper<String, O> mapper = (Mapper<String, O>) mappers.get(type);
			if (mapper != null) {
				return objects.stream()
						.map(mapper::unmap)
						.collect(toList());
			}
			else throw new UnmappedTypeException(type);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <O> String getMetadata(final Class<O> type)
			throws UnmappedTypeException {
		final Mapper<String, O> mapper = (Mapper<String, O>) mappers.get(type);
		if (mapper != null) {
			return mapper.metadata();
		}
		else throw new UnmappedTypeException(type);
	}

	@Override
	public <O> List<O> read(final Class<O> type, final String filename)
			throws IOException {
		logger.info("Leyendo el archivo: '{}'.", filename);
		try {
			// Se excluye la primera línea...
			final List<String> lines = Files.readAllLines(Paths.get(filename), CHARSET);
			return toObjects(lines.subList(1, lines.size()), type);
		}
		catch (final MalformedInputException exception) {
			logger.error("El archivo no se encuentra en {}: '{}'.",
					CHARSET.name(), exception.getMessage());
		}
		catch (final IOException exception) {
			logger.error("Excepción al leer el archivo CSV: '{}'.", exception.getMessage());
		}
		catch (final UnmappedTypeException exception) {
			logger.error("Excepción de mapeo al deserializar. '{}'", exception.getMessage());
		}
		logger.error("No se pudo deserializar el archivo '{}'.", filename);
		return Collections.emptyList();
	}

	@Override
	public <O> CSVSerializer write(final List<O> objects, final String filename)
			throws IOException {
		logger.info("Escribiendo el archivo: '{}'.", filename);
		try (final PrintWriter output = new PrintWriter(new File(filename), CHARSET.name())) {
			if (!objects.isEmpty()) {
				final Class<?> type = objects.get(0).getClass();
				output.print(getMetadata(type));
				toRegisters(objects)
					.stream()
					.forEachOrdered(output::print);
			}
		}
		catch (final IOException exception) {
			logger.error("Excepción al serializar en CSV: '{}'.", exception.getMessage());
			logger.error("No se pudo serializar la lista de objetos en el archivo '{}'.", filename);
		}
		catch (final UnmappedTypeException exception) {
			logger.error("Excepción de mapeo al serializar. '{}'", exception.getMessage());
		}
		return this;
	}
}

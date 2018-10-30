package ar.edu.itba.pod.hazelcaster.interfaces;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public interface CSVSerializer extends MapperService<String> {

	public static final Charset CHARSET = Charset.forName("UTF-8");
	public static final String SEPARATOR = ";";

	public <O> List<O> read(final Class<O> type, final String filename)
			throws IOException;

	public <O> CSVSerializer write(final List<O> objects, final String filename)
			throws IOException;
}

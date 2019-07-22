package org.techlyric.procon;

import java.io.IOException;
import java.io.PrintStream;
import java.util.function.Consumer;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

public class CSVConsumer implements AutoCloseable {
	final JsonFactory jfactory = new JsonFactory();
	int lineNumber = -1;
	String[] header = null;
	boolean withHeader = true;
	JsonGenerator jGenerator = null;

	public CSVConsumer() {

	}

	void jsonprint(int lineNo, String[] headerRow, String[] row) throws IOException {
		if (jGenerator == null) {
			return;
		}
		jGenerator.writeStartObject();
		for (int num = 0; num < headerRow.length; num++) {
			jGenerator.writeStringField(headerRow[num], row[num]);
		}
		jGenerator.writeEndObject();
		jGenerator.writeRaw("\n");
		jGenerator.flush();
	}

	public Consumer<String[]> rowData = row -> {
		lineNumber++;
		if (withHeader && lineNumber == 0) {
			header = row;
			return;
		}
		try {
			jsonprint(lineNumber, header, row);
		} catch (IOException e) {
			e.printStackTrace();
		}
	};

	public JsonGenerator getjsonGenerator() {
		return this.jGenerator;
	}

	public void setjsonGenerator(PrintStream stream) {
		try {
			this.jGenerator = jfactory.createGenerator(stream, JsonEncoding.UTF8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		try {
			if (this.jGenerator == null) {
				return;
			}
			this.jGenerator.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

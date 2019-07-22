package org.techlyric.procon;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import org.simpleflatmapper.csv.CsvParser;

public class Main {

	public static void main(String[] args) throws IOException {
		CSVConsumer csvConsumer = new CSVConsumer();
		csvConsumer.setjsonGenerator(System.out);
		try (InputStream is = Main.class.getResourceAsStream("/text.txt")) {
			is.mark(2);
			InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8);

			// Stream
			try (Stream<String[]> stream = CsvParser.stream(reader)) {
				stream.forEach(csvConsumer.rowData);
			}
		}
		csvConsumer.close();
	}
}

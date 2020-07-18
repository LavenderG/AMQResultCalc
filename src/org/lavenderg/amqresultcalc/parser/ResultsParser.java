package org.lavenderg.amqresultcalc.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lavenderg.amqresultcalc.exceptions.InvalidRecordLengthAMQParserException;
import org.lavenderg.amqresultcalc.exceptions.InvalidValueAMQParserException;
import org.lavenderg.amqresultcalc.logic.result.Result;

/**
 * Clase que procesa resultados y los transforma en datos procesables por la parte lógica de la aplicación.
 * @author lavenderg
 *
 */
public class ResultsParser {
	
	// Número de elementos de los registros del CSV
	private static final int RESULT_RECORD_LENGTH = 2;

	/**
	 * Procesa un archivo con resultados y devuelve una lista de resultados.
	 * @param outFile El {@link File} que contiene los resultados.
	 * @return Un {@link List} de {@link Result}.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<Result> parseResults(File outFile) throws FileNotFoundException, IOException {
		List<Result> results = new ArrayList<Result>();
		try (BufferedReader reader = new BufferedReader(new FileReader(outFile))) {
			while (reader.ready()) {
				String line = reader.readLine();
				String[] lineSplit = line.split(";");
				if (lineSplit.length != RESULT_RECORD_LENGTH) {
					throw new InvalidRecordLengthAMQParserException(String.format(
							"Invalid record: '%s'. Records must have the format: name;points", 
							line));
				}
				
				String name = lineSplit[0];
				Integer points;
				try {
					points = Integer.parseInt(lineSplit[1]);
				} catch (NumberFormatException e) {
					throw new InvalidValueAMQParserException(String.format("A number was expected in 'points', encountered: '%s'",
							lineSplit[1]));
				}
				
				results.add(new Result(name, points));
			}
		}
		
		return results;
	}
}

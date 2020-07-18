package org.lavenderg.amqresultcalc.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.lavenderg.amqresultcalc.logic.result.Result;

/**
 * Clase que escribe los resultados totales a un archivo de salida.
 * @author lavenderg
 */
public class ResultWriter {
	
	/**
	 * Escribe una lista de resultados como CSV a un fichero.
	 * @param results La {@link List} de {@link Result} a escribir.
	 * @param outFile El archivo de salida, como {@link File}.
	 * @throws IOException
	 */
	public void logResults(List<Result> results, File outFile) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
			for (Result result : results) {
				writer.write(result.toString());
				writer.newLine();
			}
		}
	}

}

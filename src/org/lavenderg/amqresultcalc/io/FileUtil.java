package org.lavenderg.amqresultcalc.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Clase con diversos métodos de manipulación de archivos
 * @author lavenderg
 */
public class FileUtil {

	/**
	 * Lea un archivo de texto de forma completa.
	 * @param textFile El archivo de texto a leer, como {@link File}.
	 * @return El texto completo del archivo, como {@link String}.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String readTextFile(File textFile) throws FileNotFoundException, IOException {
		StringBuilder builder = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(textFile))) {
			while (reader.ready()) {
				builder.append(reader.readLine());
				builder.append(System.lineSeparator());
			}
		}
		
		return builder.toString();
	}
}

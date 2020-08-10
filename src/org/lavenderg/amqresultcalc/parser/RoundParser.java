package org.lavenderg.amqresultcalc.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lavenderg.amqresultcalc.exceptions.InvalidBeginTagAMQParserException;
import org.lavenderg.amqresultcalc.exceptions.InvalidRecordLengthAMQParserException;
import org.lavenderg.amqresultcalc.exceptions.InvalidValueAMQParserException;
import org.lavenderg.amqresultcalc.logic.round.BattleRoyaleRound;
import org.lavenderg.amqresultcalc.logic.round.BattleRoyaleRoundOutcome;
import org.lavenderg.amqresultcalc.logic.round.Round;
import org.lavenderg.amqresultcalc.logic.round.StandardRound;
import org.lavenderg.amqresultcalc.logic.round.StandardRoundOutcome;

/**
 * Clase que procesa el archivo de texto .amq y lo transforma al modelo lógico de la aplicación.
 * @author lavenderg
 */
public class RoundParser {
	
	// Etiquetas parser
	private static final String ROUND_STD_BEGIN = "BEGINSTDROUND";
	private static final String ROUND_STD_END   = "ENDSTDROUND";
	private static final String ROUND_BATTLEROYALE_BEGIN = "BEGINBATTLEROYALE";
	private static final String ROUND_BATTLEROYALE_END   = "ENDBATTLEROYALE";
	
	// CSV separador
	private static final String FILE_RECORD_SEPARATOR = ";";
	
	// Número de elementos de los registros del CSV
	private static final int ROUND_BEGIN_RECORD_LENGTH = 3;
	private static final int ROUND_STD_RECORD_LENGTH = 3;
	private static final int ROUND_BATTLEROYALE_RECORD_LENGTH = 5;
	
	// Texto de errores
	private static final String PARSER_TEXT_ERROR_REVIVED_NAN = "Se esperaba un número en 'resucitado', se encontró: '%s'";
	private static final String PARSER_TEXT_ERROR_LP_NAN = "Se esperaba un número en 'puntos de vida', se encontró: '%s'";
	private static final String PARSER_TEXT_ERROR_ANSWERED_SONGS_NAN = "Se esperaba un número en 'número de respuestas', se encontró: '%s'";
	private static final String PARSER_TEXT_ERROR_POSITION_NAN = "Se esperaba un número en 'posición', se encontró: '%s'";
	
	/**
	 * Procesa una o más rondas y sus resultados desde un {@link File} y los convierte en {@link Round}.
	 * @param resultFile El archivo usado en el procesamiento.
	 * @return Una {@link List} de {@link Round} procesadas.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<Round> parseRounds(File resultFile) throws FileNotFoundException, IOException {
		List<Round> rounds = new ArrayList<Round>();
		try (BufferedReader reader = new BufferedReader(new FileReader(resultFile))) {

			String line;
			while ((line = reader.readLine()) != null) {
				String[] splitLine = line.split(FILE_RECORD_SEPARATOR);
				if (splitLine.length != ROUND_BEGIN_RECORD_LENGTH) {
					throw new InvalidBeginTagAMQParserException(
							String.format("Registro inicial no válido: '%s'. El registro inicial debe tener el formato: Palabra clave inicial;Nombre de ronda;URL imagen", 
									line, ROUND_BEGIN_RECORD_LENGTH));
				}
				
				// Primer elemento del registro es el tipo; el segundo es el nombre de ronda; el tercero es la URL de la captura
				// TODO: hacer URL opcional
				String roundName = splitLine[1];
				String roundType = splitLine[0];
				String roundUrl = splitLine[2];
				// Check round type
				switch (roundType) {
				case ROUND_STD_BEGIN:
					rounds.add(parseStandardRound(reader, roundName, roundUrl));
					break;
				case ROUND_BATTLEROYALE_BEGIN:
					rounds.add(parseBattleRoyaleRound(reader, roundName, roundUrl));
					break;
				default:
					throw new InvalidBeginTagAMQParserException(
							String.format("Palabra clave inicial no válida: '%s'", roundType));
				}
			}
		}
		
		return rounds;
	}

	private Round parseStandardRound(BufferedReader reader, String roundName, String roundUrl) throws IOException {
		List<StandardRoundOutcome> outcomes = new ArrayList<StandardRoundOutcome>();
		String line;
		while (!(line = reader.readLine()).equals(ROUND_STD_END)) {
			outcomes.add(parseStandardRoundOutcome(line));
		}
		
		return new StandardRound(roundName, roundUrl, outcomes);
	}

	private StandardRoundOutcome parseStandardRoundOutcome(String line) {
		String[] outcome = line.split(FILE_RECORD_SEPARATOR);
		if (outcome.length != ROUND_STD_RECORD_LENGTH) {
			throw new InvalidRecordLengthAMQParserException(String.format("Registro no válido: '%s'. Los registros deben tener el formato: nombre:posición;número de respuestas",
					line));
		}
		
		Integer position = null;
		try {
			position = Integer.parseInt(outcome[1]);
		} catch (NumberFormatException e) {
			throw new InvalidValueAMQParserException(String.format(PARSER_TEXT_ERROR_POSITION_NAN,
					outcome[1]));
		}
		
		Integer answeredSongs = null;
		try {
			answeredSongs = Integer.parseInt(outcome[2]);
		} catch (NumberFormatException e) {
			throw new InvalidValueAMQParserException(String.format(PARSER_TEXT_ERROR_ANSWERED_SONGS_NAN,
					outcome[2]));
		}
		
		return new StandardRoundOutcome(outcome[0], position, answeredSongs);
	}
	
	private Round parseBattleRoyaleRound(BufferedReader reader, String roundName, String roundUrl) throws IOException {
		List<BattleRoyaleRoundOutcome> outcomes = new ArrayList<BattleRoyaleRoundOutcome>();
		String line;
		while (!(line = reader.readLine()).equals(ROUND_BATTLEROYALE_END)) {
			outcomes.add(parseBattleRoyaleRoundOutcome(line));
		}
		
		return new BattleRoyaleRound(roundName, roundUrl, outcomes);
	}

	private BattleRoyaleRoundOutcome parseBattleRoyaleRoundOutcome(String line) {
		String[] outcome = line.split(FILE_RECORD_SEPARATOR);
		if (outcome.length != ROUND_BATTLEROYALE_RECORD_LENGTH) {
			throw new InvalidRecordLengthAMQParserException(String.format("Registro no válido: '%s'. Los registros deben tener el formato: nombre;posicion;aciertos;puntosdevida;resucitado",
					line));
		}
		
		Integer position = null;
		try {
			position = Integer.parseInt(outcome[1]);
		} catch (NumberFormatException e) {
			throw new InvalidValueAMQParserException(String.format(PARSER_TEXT_ERROR_POSITION_NAN,
					outcome[1]));
		}
		
		Integer answeredSongs = null;
		try {
			answeredSongs = Integer.parseInt(outcome[2]);
		} catch (NumberFormatException e) {
			throw new InvalidValueAMQParserException(String.format(PARSER_TEXT_ERROR_ANSWERED_SONGS_NAN,
					outcome[2]));
		}
		
		Integer lifePoints = null;
		try {
			lifePoints = Integer.parseInt(outcome[3]);
		} catch (NumberFormatException e) {
			throw new InvalidValueAMQParserException(String.format(PARSER_TEXT_ERROR_LP_NAN,
					outcome[3]));
		}
		
		Boolean revived = null;
		try {
			revived = Integer.parseInt(outcome[4]) == 1 ? true : false;
		} catch (NumberFormatException e) {
			throw new InvalidValueAMQParserException(String.format(PARSER_TEXT_ERROR_REVIVED_NAN,
					outcome[4]));
		}
		
		return new BattleRoyaleRoundOutcome(outcome[0], position, answeredSongs, lifePoints, revived);
	}

}

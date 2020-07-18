package org.lavenderg.amqresultcalc.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.lavenderg.amqresultcalc.logic.result.Result;
import org.lavenderg.amqresultcalc.logic.result.ResultUtil;
import org.lavenderg.amqresultcalc.logic.round.Round;

/**
 * Clase que crea BBCode asociado con los cálculos de las rondas.
 * @author lavenderg
 */
public class BBCodeWriter {
	
	// Texto y etiquetas
	private static final String TEXT_PLAYERS_AND_DATE = "Resultados del %s con los siguientes jugadores presentes: %s.";
	private static final String TEXT_RESULTS_WERE = "Los resultados fueron los siguientes:";
	private static final String TEXT_FOLLOWING_POINTS = "Con estos resultados los jugadores obtuvieron los siguientes puntos...";
	private static final String TEXT_AGGREGATE_HEADER = "RESULTADOS TRAS LA RONDA";
	private static final String DIV_CENTER_OPENING = "[div align=\"center\"]";
	private static final String DIV_HEADER = "[div align=\"center\"][font size=\"4\"][font color=\"1979e6\"][b]";
	private static final String DIV_CLOSE = "[/div]";
	private static final String DIV_HEADER_CLOSE = "[/b][/font][/font][/div]";
	private static final String BOLD_OPENING = "[b]";
	private static final String BOLD_CLOSE = "[/b]";
	private static final String INIT_TABLE = "[table][tbody]";
	private static final String END_TABLE = "[/tbody][/table]";
	private static final String INIT_TABLE_ROW = "[tr]";
	private static final String END_TABLE_ROW = "[/tr]";
	private static final String INIT_TABLE_DATA = "[td style=\"border:1px solid rgb(0, 0, 0);padding:3px;\"]";
	private static final String END_TABLE_DATA  = "[/td]";
	private static final String TABLE_HEADER_PLAYER = "Jugador";
	private static final String TABLE_HEADER_POINTS = "Puntuación";
	private static final String SPOILER_BEGIN_TAG = "[spoiler=Capturas]";
	private static final String SPOILER_END_TAG = "[/spoiler]";
	private static final String IMG_TAG_FORMATTED = "[img style=\"%s\" src=\"%s\" alt=\" \"]";
	private static final String IMG_ATTRIB_WIDTH = "max-width:100%;";
	
	/**
	 * Escribe BBCode asociado con las rondas y resultados al archivo dado.
	 * @param rounds Lista de rondas que serán procesadas en BBCode, como {@link List} de {@link Round}.
	 * @param previousResults Resultados previos para realizar la adición de puntos, como {@link List} de {@link Result}.
	 * @param outFile Archivo de salida, como {@link File}.
	 * @throws IOException
	 */
	public void logRounds(List<Round> rounds, List<Result> previousResults, File outFile) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFile))) {
			logNumberOfPlayers(rounds, writer);
			logRoundsWithPoints(rounds, writer);
			logAggregateResultsHeader(rounds, writer);
			logResultsTable(ResultUtil.calculateResultsTable(rounds, previousResults), writer);
		}
		
		
	}

	private void logNumberOfPlayers(List<Round> rounds, BufferedWriter writer) throws IOException {
		Set<String> players = new HashSet<String>();
		for (Round round : rounds) {
			for (Result result : round.calculateRoundResults()) {
				players.add(result.getPlayerName());
			}
		}
		
		StringBuilder playersString = new StringBuilder();
		boolean firstSeparation = true;
		for (String player : players) {
			if (firstSeparation) {
				firstSeparation = false;
			} else {
				playersString.append(", ");
			}
			playersString.append(player);
		}
		
		// TODO: añadir opción de escribir fecha
		writer.write(String.format(TEXT_PLAYERS_AND_DATE, "<FECHA>", playersString.toString()));
		writer.newLine();
		writer.newLine();
		writer.write(TEXT_RESULTS_WERE);
		writer.newLine();
		writer.newLine();
		
		// Capturas
		writer.write(SPOILER_BEGIN_TAG);
		writer.newLine();
		for (Round round : rounds) {
			writer.write(round.roundName());
			writer.newLine();
			writer.write(String.format(IMG_TAG_FORMATTED, IMG_ATTRIB_WIDTH, round.roundURL()));
			writer.newLine();
			writer.newLine();
		}
		writer.write(SPOILER_END_TAG);
		
		writer.newLine();
		writer.newLine();
		writer.write(TEXT_FOLLOWING_POINTS);
		writer.newLine();
		writer.newLine();
	}
	
	private void logRoundsWithPoints(List<Round> rounds, BufferedWriter writer) throws IOException {
		writer.write(DIV_CENTER_OPENING);
		writer.newLine();
		
		for (Round round : rounds) {
			writer.write(BOLD_OPENING);
			writer.write(round.roundName());
			writer.write(BOLD_CLOSE);
			writer.newLine();
			writer.newLine();
			for (Result result : round.calculateRoundResults()) {
				writer.write(result.toString());
				writer.newLine();
			}
			writer.newLine();
			writer.newLine();
		}
		
		writer.write(DIV_CLOSE);
		writer.newLine();
		writer.newLine();
		writer.newLine();
	}
	
	private void logAggregateResultsHeader(List<Round> rounds, BufferedWriter writer) throws IOException {
		writer.write(DIV_HEADER);
		writer.newLine();
		writer.write(TEXT_AGGREGATE_HEADER);
		writer.newLine();
		writer.write(DIV_HEADER_CLOSE);
		writer.newLine();
		writer.newLine();
	}
	
	private void logResultsTable(List<Result> calculatedResultsTable, BufferedWriter writer) throws IOException {
		writer.write(DIV_CENTER_OPENING);
		writer.newLine();
		
		writer.write(INIT_TABLE);
		writer.newLine();
		writer.write(INIT_TABLE_ROW);
		writer.newLine();
		writer.write(INIT_TABLE_DATA);
		writer.write(TABLE_HEADER_PLAYER);
		writer.newLine();
		writer.write(END_TABLE_DATA);
		writer.newLine();
		writer.write(INIT_TABLE_DATA);
		writer.write(TABLE_HEADER_POINTS);
		writer.newLine();
		writer.write(END_TABLE_DATA);
		writer.newLine();
		writer.write(END_TABLE_ROW);
		writer.newLine();
		
		ResultUtil.orderByPoints(calculatedResultsTable);
		int positionCounter = 1;
		for (Result result : calculatedResultsTable) {
			writer.write(INIT_TABLE_ROW);
			writer.newLine();
			writer.write(INIT_TABLE_DATA);
			writer.write(positionCounter + ". " + result.getPlayerName());
			writer.newLine();
			writer.write(END_TABLE_DATA);
			writer.newLine();
			writer.write(INIT_TABLE_DATA);
			writer.write(result.getPlayerPoints().toString());
			writer.newLine();
			writer.write(END_TABLE_DATA);
			writer.newLine();
			writer.write(END_TABLE_ROW);
			writer.newLine();
			positionCounter++;
		}
		
		writer.write(END_TABLE);
		writer.newLine();
		writer.write(DIV_CLOSE);
		writer.newLine();
	}
}

package org.lavenderg.amqresultcalc.logic.round;

import java.util.ArrayList;
import java.util.List;

import org.lavenderg.amqresultcalc.logic.result.Result;
import org.lavenderg.amqresultcalc.logic.result.StandardResult;

/**
 * Clase que representa a una ronda estándar.
 * @author lavenderg
 */
public class StandardRound implements Round {
	
	/**
	 * Puntos asociados a cada posición. Índice 0 = primera posición. 
	 */
	private static final Integer[] pointsFromPosition = {10, 7, 5, 4, 3, 2, 1};
	
	private final List<StandardRoundOutcome> roundOutcomes;
	private final String roundName;
	
	/**
	 * Crea una ronda estándar con un nombre y una lista de resultados.
	 * @param roundName El nombre de la ronda, como {@link String}.
	 * @param roundOutcomes Una {@link List} de {@link StandardRoundOutcome}.
	 */
	public StandardRound(String roundName, List<StandardRoundOutcome> roundOutcomes) {
		this.roundOutcomes = roundOutcomes;
		this.roundName = roundName;
	}
	
	private static Integer getPointsFromPosition(Integer position) {
		if (position - 1 < pointsFromPosition.length) {
			return pointsFromPosition[position - 1];
		}
		return 0;
	}

	@Override
	public List<Result> calculateRoundResults() {
		List<Result> results = new ArrayList<Result>();
		for (StandardRoundOutcome roundOutcome : roundOutcomes) {
			Integer answeredSongs = roundOutcome.getAnsweredSongs();
			Integer positionPoints = StandardRound.getPointsFromPosition(roundOutcome.getPlayerPosition());
			Integer playerPoints = answeredSongs + positionPoints;
			results.add(new StandardResult(roundOutcome.getPlayerName(), playerPoints, answeredSongs, positionPoints));
		}
		
		return results;
	}

	@Override
	public String roundName() {
		return roundName;
	}

}

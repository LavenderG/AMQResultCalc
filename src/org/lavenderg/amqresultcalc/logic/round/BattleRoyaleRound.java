package org.lavenderg.amqresultcalc.logic.round;

import java.util.ArrayList;
import java.util.List;

import org.lavenderg.amqresultcalc.logic.result.BattleRoyaleResult;
import org.lavenderg.amqresultcalc.logic.result.Result;

/**
 * Clase que representa a una ronda de battle royale.
 * @author lavenderg
 */
public class BattleRoyaleRound implements Round {

	/**
	 * Puntos asociados a cada posición. Índice 0 = primera posición. 
	 */
	private static final Integer[] pointsFromPosition = {10, 7, 5, 4, 3, 2, 1};
	
	private final List<BattleRoyaleRoundOutcome> roundOutcomes;
	private final String roundName;
	private final String urlName;
	
	/**
	 * Crea una ronda de battle royale con un nombre y una lista de resultados.
	 * @param roundName El nombre de la ronda, como {@link String}.
	 * @param roundOutcomes Una {@link List} de {@link BattleRoyaleRoundOutcome}.
	 */
	public BattleRoyaleRound(String roundName, String urlName, List<BattleRoyaleRoundOutcome> roundOutcomes) {
		this.roundOutcomes = roundOutcomes;
		this.roundName = roundName;
		this.urlName = urlName;
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
		
		// Calcular número de jugadores vivos
		Integer numberOfAlivePlayers = 0;
		for (BattleRoyaleRoundOutcome outcome: roundOutcomes) {
			if (outcome.getLifePoints() > 0) {
				numberOfAlivePlayers++;
			}
		}
		
		// Calcular puntos
		for (BattleRoyaleRoundOutcome outcome: roundOutcomes) {
			String playerName = outcome.getPlayerName();
			Integer playerPosition = outcome.getPlayerPosition();
			Integer answeredSongs = outcome.getAnsweredSongs();
			Integer lifePoints = outcome.getLifePoints();
			Boolean revived = outcome.getRevived();
			
			Integer positionPoints = BattleRoyaleRound.getPointsFromPosition(playerPosition);
			Integer songPoints;
			if (numberOfAlivePlayers == 1 && lifePoints > 0) {
				songPoints = answeredSongs * 3;
			} else if (lifePoints > 0) {
				songPoints = answeredSongs * 2;
			} else {
				songPoints = answeredSongs;
			}
			
			Integer survivalPoints;
			Integer revivedPoints;
			if (revived && lifePoints > 0 && playerPosition == 1) {
				revivedPoints = 9;
			} else if (revived && lifePoints > 0 && playerPosition != 1) {
				revivedPoints = 0;
				lifePoints *= 2;
			} else {
				revivedPoints = 0;
			}
			survivalPoints = lifePoints * 3;
			
			Integer totalPoints = positionPoints + songPoints + survivalPoints + revivedPoints;
			results.add(new BattleRoyaleResult(playerName, totalPoints, positionPoints, songPoints, survivalPoints, revivedPoints));
		}
		return results;
	}

	@Override
	public String roundName() {
		return roundName;
	}

	@Override
	public String roundURL() {
		return urlName;
	}

}

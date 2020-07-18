package org.lavenderg.amqresultcalc.logic.result;

/**
 * Clase que representa al resultado de de una partida battle royale.
 * @author lavenderg
 */
public class BattleRoyaleResult extends Result {

	private final Integer positionPoints;
	private final Integer songPoints;
	private final Integer survivalPoints;
	private final Integer revivedPoints;

	/**
	 * Crea un resultado a partir del nombre del jugador, su puntuación y sus puntuaciones parciales.
	 * @param playerName El nombre del jugador, como {@link String}.
	 * @param playerPoints Los puntos totales del jugador, como {@link Integer}.
	 * @param songPoints Los puntos parciales del jugador relativos al número de músicas acertadas, como {@link Integer}.
	 * @param positionPoints Los puntos parciales del jugador relativos a la posición en la que quedó, como {@link Integer}.
	 * @param survivalPoints Los puntos parciales del jugador relativos al número de vidas, como {@link Integer}.
	 * @param revivedPoints Los puntos parciales del jugador relativos a si revivió o no, como {@link Integer}.
	 */
	public BattleRoyaleResult(String playerName, Integer playerPoints, Integer positionPoints, Integer songPoints,
			Integer survivalPoints, Integer revivedPoints) {
		super(playerName, playerPoints);
		this.positionPoints = positionPoints;
		this.songPoints = songPoints;
		this.survivalPoints = survivalPoints;
		this.revivedPoints = revivedPoints;
	}

	public Integer getPositionPoints() {
		return positionPoints;
	}

	public Integer getSongPoints() {
		return songPoints;
	}

	public Integer getSurvivalPoints() {
		return survivalPoints;
	}

	public Integer getRevivedPoints() {
		return revivedPoints;
	}

	@Override
	public String toString() {
		return String.format("%-10s (%02d + %02d + %02d + %02d = %02d puntos)", playerName,
				positionPoints, songPoints, survivalPoints, revivedPoints, playerPoints);
	}
	
	

}

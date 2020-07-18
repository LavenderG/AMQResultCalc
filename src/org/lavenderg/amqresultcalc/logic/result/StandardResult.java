package org.lavenderg.amqresultcalc.logic.result;

/**
 * Clase que representa al resultado de de una partida estándar.
 * @author lavenderg
 */
public class StandardResult extends Result {
	
	private final Integer answeredSongs;
	private final Integer positionPoints;

	/**
	 * Crea un resultado a partir del nombre del jugador, su puntuación y sus puntuaciones parciales.
	 * @param playerName El nombre del jugador, como {@link String}.
	 * @param playerPoints Los puntos totales del jugador, como {@link Integer}.
	 * @param answeredSongs Los puntos parciales del jugador relativos al número de músicas acertadas, como {@link Integer}.
	 * @param positionPoints Los puntos parciales del jugador relativos a la posición en la que quedó, como {@link Integer}.
	 */
	public StandardResult(String playerName, Integer playerPoints, Integer answeredSongs, Integer positionPoints) {
		super(playerName, playerPoints);
		this.answeredSongs = answeredSongs;
		this.positionPoints = positionPoints;
	}

	public Integer getAnsweredSongs() {
		return answeredSongs;
	}

	public Integer getPositionPoints() {
		return positionPoints;
	}

	@Override
	public String toString() {
		return String.format("%-12s (%02d + %02d = %02d puntos)", 
				playerName, positionPoints, answeredSongs, playerPoints);
	}

}

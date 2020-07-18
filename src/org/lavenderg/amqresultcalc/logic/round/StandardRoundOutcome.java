package org.lavenderg.amqresultcalc.logic.round;

/**
 * Clase que representa los resultados de una ronda estándar.
 * @author lavenderg
 */
public class StandardRoundOutcome {
	
	private final String playerName;
	private final Integer playerPosition;
	private final Integer answeredSongs;
	
	/**
	 * Crea un objeto {@link StandardRoundOutcome} que representa los resultados de una ronda estándar.
	 * @param playerName El nombre del jugador, como {@link String}.
	 * @param playerPosition La posición del jugador, como {@link Integer}.
	 * @param answeredSongs El número de canciones respondidas correctamente, como {@link Integer}.
	 */
	public StandardRoundOutcome(String playerName, Integer playerPosition, Integer answeredSongs) {
		this.playerName = playerName;
		this.playerPosition = playerPosition;
		this.answeredSongs = answeredSongs;
	}

	public String getPlayerName() {
		return playerName;
	}

	public Integer getPlayerPosition() {
		return playerPosition;
	}

	public Integer getAnsweredSongs() {
		return answeredSongs;
	}
	
	

}

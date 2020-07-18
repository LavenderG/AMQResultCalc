package org.lavenderg.amqresultcalc.logic.result;

/**
 * Clase que representa el resultado de una ronda relativo a un jugador.
 * @author lavenderg
 */
public class Result {

	protected final String playerName;
	protected final Integer playerPoints;
	
	/**
	 * Crea un resultado a partir del nombre del jugador y su puntuación.
	 * @param playerName El nombre del jugador, como {@link String}.
	 * @param playerPoints La puntuación del jugador, como {@link Integer}.
	 */
	public Result(String playerName, Integer playerPoints) {
		super();
		this.playerName = playerName;
		this.playerPoints = playerPoints;
	}

	public String getPlayerName() {
		return playerName;
	}

	public Integer getPlayerPoints() {
		return playerPoints;
	}
	
	@Override
	public String toString() {
		return String.format("%s;%d", playerName, playerPoints);
	}

}

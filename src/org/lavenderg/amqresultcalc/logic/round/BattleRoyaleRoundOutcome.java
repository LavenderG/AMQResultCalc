package org.lavenderg.amqresultcalc.logic.round;

/**
 * Clase que representa los resultados de una ronda de battle royale.
 * @author lavenderg
 */
public class BattleRoyaleRoundOutcome {
	
	private final String playerName;
	private final Integer playerPosition;
	private final Integer answeredSongs;
	private final Integer lifePoints;
	private final Boolean revived;
	
	/**
	 * Crea un objeto {@link BattleRoyaleRoundOutcome} que representa los resultados de una ronda battle royale.
	 * @param playerName El nombre del jugador, como {@link String}.
	 * @param playerPosition La posición del jugador, como {@link Integer}.
	 * @param answeredSongs El número de canciones respondidas correctamente, como {@link Integer}.
	 * @param lifePoints El número de puntos de vida del jugador, como {@link Integer}.
	 * @param revived Si el jugador ha resucitado o no, como {@link Boolean}.
	 */
	public BattleRoyaleRoundOutcome(String playerName, Integer playerPosition, Integer answeredSongs,
			Integer lifePoints, Boolean revived) {
		this.playerName = playerName;
		this.playerPosition = playerPosition;
		this.answeredSongs = answeredSongs;
		this.lifePoints = lifePoints;
		this.revived = revived;
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

	public Integer getLifePoints() {
		return lifePoints;
	}

	public Boolean getRevived() {
		return revived;
	}
	
	

}

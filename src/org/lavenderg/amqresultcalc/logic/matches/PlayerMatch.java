package org.lavenderg.amqresultcalc.logic.matches;

public class PlayerMatch {

	private final String player1;
	private final String player2;

	public PlayerMatch(String player1, String player2) {
		this.player1 = player1;
		this.player2 = player2;
	}

	public String getPlayer1() {
		return player1;
	}

	public String getPlayer2() {
		return player2;
	}

	@Override
	public String toString() {
		if (player1 == null && player2 == null) {
			return "";
		} else if (player1 == null) {
			return player2;
		} else if (player2 == null) {
			return player1;
		} else {
			return player1 + " " + player2;
		}
	}

}

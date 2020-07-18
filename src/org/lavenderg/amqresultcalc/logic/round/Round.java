package org.lavenderg.amqresultcalc.logic.round;

import java.util.List;

import org.lavenderg.amqresultcalc.logic.result.Result;

/**
 * Interfaz que representa a una ronda de juego.
 * @author lavenderg
 */
public interface Round {
	
	/**
	 * Devuelve el nombre de la ronda.
	 * @return {@link String}, nombre de la ronda.
	 */
	String roundName();
	
	/**
	 * Calcula y devuelve los resultados de la ronda.
	 * @return Los resultados {@link Result} de la ronda, en una {@link List}.
	 */
	List<Result> calculateRoundResults();

}

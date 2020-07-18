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
	 * Devuelve la URL de la captura de la ronda.
	 * @return {@link String}, URL de la captura de la ronda.
	 */
	String roundURL();
	
	/**
	 * Calcula y devuelve los resultados de la ronda.
	 * @return Los resultados {@link Result} de la ronda, en una {@link List}.
	 */
	List<Result> calculateRoundResults();

}

package org.lavenderg.amqresultcalc.logic.result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lavenderg.amqresultcalc.logic.round.Round;

/**
 * Clase con operaciones relacionadas con {@link Result}.
 * @author lavenderg
 */
public class ResultUtil {
	
	/**
	 * Suma dos listas de resultados.
	 * @param op1 La primera lista de resultados.
	 * @param op2 La segunda lista de resultados.
	 * @return Una {@link List} con el resultado de la suma.
	 */
	public static List<Result> add(List<Result> op1, List<Result> op2) {
		List<Result> result = new ArrayList<Result>();
		
		// Guarda op1
		Map<String, Integer> resultMap = new HashMap<String, Integer>();
		for (Result result1 : op1) {
			resultMap.put(result1.getPlayerName(), result1.getPlayerPoints());
		}
		
		// Añade op2
		for (Result result2: op2) {
			if (resultMap.containsKey(result2.getPlayerName())) {
				Integer addPoints = resultMap.get(result2.getPlayerName()) + result2.getPlayerPoints();
				resultMap.put(result2.getPlayerName(), addPoints);
			} else {
				resultMap.put(result2.getPlayerName(), result2.getPlayerPoints());
			}
		}
		
		resultMap.forEach((name, points) -> {result.add(new Result(name, points));});
		return result;
	}
	
	/**
	 * Calcula la tabla de resultados a partir de una tabla de resultados previa y una lista de rondas.
	 * @param rounds La {@link List} de {@link Round} a usar en el cálculo.
	 * @param previousResults La {@link List} de {@link Result} previa.
	 * @return La {@link List} de {@link Result} calculada.
	 */
	public static List<Result> calculateResultsTable(List<Round> rounds, List<Result> previousResults) {
		List<Result> adder = previousResults;
		for (Round round : rounds) {
			adder = ResultUtil.add(round.calculateRoundResults(), adder);
		}
		
		return adder;
	}
	
	/**
	 * Ordena una lista de resultados.
	 * @param results La lista de resultados a ordenador.
	 * @return Referencia a la {@link List}, ya ordenada.
	 */
	public static List<Result> orderByPoints(List<Result> results) {
		results.sort((r1, r2) -> {return r2.playerPoints - r1.playerPoints;});
		return results;
	}

}

package org.lavenderg.amqresultcalc.logic.matches;

import java.util.ArrayList;
import java.util.List;

import org.lavenderg.amqresultcalc.logic.result.Result;

public class MatchCalculator {

	public List<PlayerMatch> calculateMatches(List<Result> results) {
		List<PlayerMatch> matches = new ArrayList<PlayerMatch>();
		Result[] resultsArray = new Result[results.size()];
		results.toArray(resultsArray);
		int playerIndex;
		if (results.size() % 2 == 0) {
			playerIndex = 0;
		} else {
			playerIndex = 1;
			matches.add(new PlayerMatch(results.get(0).getPlayerName(), null));
		}
		int matchIndex1 = playerIndex;
		int matchIndex2 = results.size() - 1;
		while (matchIndex1 < matchIndex2) {
			matches.add(new PlayerMatch(results.get(matchIndex1).getPlayerName(), results.get(matchIndex2).getPlayerName()));
			matchIndex1++;
			matchIndex2--;
		}
		return matches;
	}
}

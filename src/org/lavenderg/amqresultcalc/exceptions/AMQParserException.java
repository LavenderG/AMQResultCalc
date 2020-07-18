package org.lavenderg.amqresultcalc.exceptions;

import org.lavenderg.amqresultcalc.parser.RoundParser;

/**
 * Excepción base de {@link RoundParser}.
 * @author lavenderg
 */
public class AMQParserException extends RuntimeException {

	public AMQParserException(String format) {
		super(format);
	}

	private static final long serialVersionUID = 3268551417205155430L;

}

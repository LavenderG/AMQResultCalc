package org.lavenderg.amqresultcalc.exceptions;

import org.lavenderg.amqresultcalc.parser.RoundParser;

/**
 * Excepción que indica un valor no válido en {@link RoundParser}.
 * @author lavenderg
 */
public class InvalidValueAMQParserException extends AMQParserException {

	public InvalidValueAMQParserException(String format) {
		super(format);
	}

	
	private static final long serialVersionUID = 7021093062245833786L;

}

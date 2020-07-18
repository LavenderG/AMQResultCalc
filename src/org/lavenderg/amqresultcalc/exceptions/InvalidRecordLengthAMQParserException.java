package org.lavenderg.amqresultcalc.exceptions;

import org.lavenderg.amqresultcalc.parser.RoundParser;

/**
 * Excepción que indica un número no válido de elementos del registro en {@link RoundParser}.
 * @author lavenderg
 */
public class InvalidRecordLengthAMQParserException extends AMQParserException {

	public InvalidRecordLengthAMQParserException(String format) {
		super(format);
	}

	private static final long serialVersionUID = 4822777354935135494L;

}

package org.lavenderg.amqresultcalc.exceptions;

import org.lavenderg.amqresultcalc.parser.RoundParser;

/**
 * Excepción que indica un error en la etiqueta inicial del {@link RoundParser}.
 * @author lavenderg
 */
public class InvalidBeginTagAMQParserException extends AMQParserException {

	public InvalidBeginTagAMQParserException(String format) {
		super(format);
	}

	private static final long serialVersionUID = -7383687626803677172L;}

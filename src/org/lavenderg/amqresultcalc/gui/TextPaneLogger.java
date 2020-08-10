package org.lavenderg.amqresultcalc.gui;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * Clase que edita y modifica texto de un {@link JTextPane} de forma dinámica.
 * @author lavenderg
 */
class TextPaneLogger {
	
	private StyledDocument document;
	
	public TextPaneLogger(JTextPane textPane) {
		this.document = textPane.getStyledDocument();
	}
	
	public void logInfo(String text) throws BadLocationException {
		SimpleAttributeSet infoAttr = new SimpleAttributeSet();
		logText(text, infoAttr);
	}
	
	public void logSuccess(String text) throws BadLocationException {
		SimpleAttributeSet successAttr = new SimpleAttributeSet();
		StyleConstants.setForeground(successAttr, Color.GREEN);
		logText(text, successAttr);
	}
	
	public void logError(String text) throws BadLocationException {
		SimpleAttributeSet errorAttr = new SimpleAttributeSet();
		StyleConstants.setForeground(errorAttr, Color.RED);
		logText(text, errorAttr);
	}
	
	private void logText(String text, SimpleAttributeSet attr) throws BadLocationException {
		document.insertString(document.getLength(), text, attr);
		document.insertString(document.getLength(), System.lineSeparator(), attr);
	}

}

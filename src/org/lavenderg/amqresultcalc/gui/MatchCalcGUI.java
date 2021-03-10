package org.lavenderg.amqresultcalc.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

public class MatchCalcGUI extends JFrame {

	private static final long serialVersionUID = 3977750860095436305L;
	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public MatchCalcGUI() {
		setType(Type.UTILITY);
		setTitle("Calculadora de parejas");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}

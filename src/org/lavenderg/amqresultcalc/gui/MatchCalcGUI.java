package org.lavenderg.amqresultcalc.gui;

import java.awt.BorderLayout;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import org.lavenderg.amqresultcalc.logic.matches.PlayerMatch;
import org.lavenderg.amqresultcalc.gui.model.PlayerResultsTableModel;
import org.lavenderg.amqresultcalc.logic.matches.MatchCalculator;
import org.lavenderg.amqresultcalc.logic.result.Result;

import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JSpinner;
import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

public class MatchCalcGUI extends JFrame {

	private static final long serialVersionUID = 3977750860095436305L;
	private JPanel contentPane;
	private JSplitPane splitPaneMatchCalc;
	private JSplitPane mainTablePanel;
	private JScrollPane tableScrollPane;
	private JScrollPane matchesResultScrollPane;
	private JTextPane textPaneMatchesResult;
	private JTable playerPositionsTable;
	private JButton btnAddResultado;
	private JScrollPane controlParejasScrollPane;


	/**
	 * Create the frame.
	 * @param resultadosCargados Los resultados cargados previamente en la aplicación.
	 */

	private List<Result> resultadosCargados;
	private JPanel controlParejasPane;
	private JLabel lblNombre;
	private JTextField textFieldNombre;
	private JLabel lblPuntuacion;
	private JPanel addResultadoPane;
	private JButton btnCalcularParejas;
	private JPanel calcularParejasPane;
	private JSpinner spinnerPuntuacion;
	private JButton btnEliminarFila;
	private JMenuBar menuBar;
	private JMenu mnArchivo;
	private JMenuItem mntmCerrar;

	public MatchCalcGUI(List<Result> resultadosCargados) {
		setType(Type.UTILITY);
		setTitle("Calculadora de parejas");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		this.resultadosCargados = resultadosCargados;
		setJMenuBar(getMenuBar_1());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getSplitPaneMatchCalc(), BorderLayout.CENTER);
	}

	private JSplitPane getSplitPaneMatchCalc() {
		if (splitPaneMatchCalc == null) {
			splitPaneMatchCalc = new JSplitPane();
			splitPaneMatchCalc.setLeftComponent(getMainTablePanel());
			splitPaneMatchCalc.setRightComponent(getMatchesResultScrollPane());
		}
		return splitPaneMatchCalc;
	}
	private JSplitPane getMainTablePanel() {
		if (mainTablePanel == null) {
			mainTablePanel = new JSplitPane();
			mainTablePanel.setOrientation(JSplitPane.VERTICAL_SPLIT);

			mainTablePanel.setLeftComponent(getTableScrollPane());
			mainTablePanel.setRightComponent(getControlParejasScrollPane());
		}
		return mainTablePanel;
	}
	private JScrollPane getTableScrollPane() {
		if (tableScrollPane == null) {
			tableScrollPane = new JScrollPane();
			tableScrollPane.setViewportView(getPlayerPositionsTable());
		}
		return tableScrollPane;
	}
	private JScrollPane getMatchesResultScrollPane() {
		if (matchesResultScrollPane == null) {
			matchesResultScrollPane = new JScrollPane();
			matchesResultScrollPane.setViewportView(getTextPaneMatchesResult());
		}
		return matchesResultScrollPane;
	}
	private JTextPane getTextPaneMatchesResult() {
		if (textPaneMatchesResult == null) {
			textPaneMatchesResult = new JTextPane();
			textPaneMatchesResult.setEditable(false);
		}
		return textPaneMatchesResult;
	}
	private JTable getPlayerPositionsTable() {
		if (playerPositionsTable == null) {
			playerPositionsTable = new JTable(new PlayerResultsTableModel(resultadosCargados));
		}
		return playerPositionsTable;
	}
	private JButton getBtnAddResultado() {
		if (btnAddResultado == null) {
			btnAddResultado = new JButton("Añadir resultado");
			btnAddResultado.setToolTipText("Añade el jugador y puntuación introducidos a la tabla de resultados.");
			btnAddResultado.addActionListener(new BtnAddParejaActionListener());
			btnAddResultado.setMnemonic('d');
		}
		return btnAddResultado;
	}
	private class BtnAddParejaActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			PlayerResultsTableModel model = (PlayerResultsTableModel) getPlayerPositionsTable().getModel();
			if (getTextFieldNombre().getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(MatchCalcGUI.this, "El nombre del jugador no puede estar vacío.", "Error al añadir resultado", JOptionPane.ERROR_MESSAGE);
			} else {
				var nombre = getTextFieldNombre().getText().trim();
				var puntuacion = (Integer) spinnerPuntuacion.getValue();
				model.insertResult(new Result(nombre, puntuacion));
			}
		}
	}
	private class BtnEliminarFilaActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (playerPositionsTable.getSelectedRow() == -1) {
				JOptionPane.showMessageDialog(MatchCalcGUI.this, "No hay ninguna fila seleccionada.", "Error al eliminar fila", JOptionPane.ERROR_MESSAGE);
			} else {
				((PlayerResultsTableModel) playerPositionsTable.getModel()).removeRow(playerPositionsTable.getSelectedRow());
			}
		}
	}
	private class BtnCalcularParejasActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			PlayerResultsTableModel model = (PlayerResultsTableModel) playerPositionsTable.getModel();
			if (model.getRowCount() == 0) {
				JOptionPane.showMessageDialog(MatchCalcGUI.this, "No se ha introducido ningún resultado en la tabla.", "Error al calcular las parejas", JOptionPane.ERROR_MESSAGE);
			} else {
				List<PlayerMatch> calculatedMatches = new MatchCalculator().calculateMatches(model.getResults());
				textPaneMatchesResult.setText(calculatedMatches.parallelStream().map(m -> m.toString()).collect(Collectors.joining(" // " + System.lineSeparator())));
			}
		}
	}
	private class MntmCerrarActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			MatchCalcGUI.this.dispose();
		}
	}

	private JScrollPane getControlParejasScrollPane() {
		if (controlParejasScrollPane == null) {
			controlParejasScrollPane = new JScrollPane();
			controlParejasScrollPane.setViewportView(getControlParejasPane());
		}
		return controlParejasScrollPane;
	}

	private JPanel getControlParejasPane() {
		if (controlParejasPane == null) {
			controlParejasPane = new JPanel();
			controlParejasPane.setAutoscrolls(true);
			controlParejasPane.setAlignmentY(0.0f);
			controlParejasPane.setLayout(new BoxLayout(controlParejasPane, BoxLayout.Y_AXIS));
			controlParejasPane.add(getAddResultadoPane());
			controlParejasPane.add(getCalcularParejasPane());
		}
		return controlParejasPane;
	}
	private JLabel getLblNombre() {
		if (lblNombre == null) {
			lblNombre = new JLabel("Nombre:");
			lblNombre.setLabelFor(getTextFieldNombre());
		}
		return lblNombre;
	}
	private JTextField getTextFieldNombre() {
		if (textFieldNombre == null) {
			textFieldNombre = new JTextField();
			textFieldNombre.setToolTipText("El nombre del jugador.");
			textFieldNombre.setColumns(10);
		}
		return textFieldNombre;
	}
	private JLabel getLblPuntuacion() {
		if (lblPuntuacion == null) {
			lblPuntuacion = new JLabel("Puntuación:");
			lblPuntuacion.setLabelFor(lblPuntuacion);
		}
		return lblPuntuacion;
	}
	private JPanel getAddResultadoPane() {
		if (addResultadoPane == null) {
			addResultadoPane = new JPanel();
			addResultadoPane.add(getLblNombre());
			addResultadoPane.add(getTextFieldNombre());
			addResultadoPane.add(getLblPuntuacion());
			addResultadoPane.add(getSpinnerPuntuacion());
			addResultadoPane.add(getBtnAddResultado());
		}
		return addResultadoPane;
	}
	private JButton getBtnCalcularParejas() {
		if (btnCalcularParejas == null) {
			btnCalcularParejas = new JButton("Calcular parejas");
			btnCalcularParejas.setToolTipText("Calcula las parejas según los datos de la tabla.");
			btnCalcularParejas.setMnemonic('C');
			btnCalcularParejas.addActionListener(new BtnCalcularParejasActionListener());
			btnCalcularParejas.setAlignmentX(0.5f);
		}
		return btnCalcularParejas;
	}
	private JPanel getCalcularParejasPane() {
		if (calcularParejasPane == null) {
			calcularParejasPane = new JPanel();
			calcularParejasPane.add(getBtnEliminarFila());
			calcularParejasPane.add(getBtnCalcularParejas());
		}
		return calcularParejasPane;
	}
	private JSpinner getSpinnerPuntuacion() {
		if (spinnerPuntuacion == null) {
			SpinnerNumberModel model = new SpinnerNumberModel(0, 0, null, 1);
			spinnerPuntuacion = new JSpinner(model);
			spinnerPuntuacion.setToolTipText("La puntuación que ha obtenido el jugador.");
			spinnerPuntuacion.setPreferredSize(new Dimension(70, 20));
		}
		return spinnerPuntuacion;
	}
	private JButton getBtnEliminarFila() {
		if (btnEliminarFila == null) {
			btnEliminarFila = new JButton("Eliminar fila");
			btnEliminarFila.setMnemonic('E');
			btnEliminarFila.setToolTipText("Elimina la fila de la tabla seleccionada actualmente.");
			btnEliminarFila.addActionListener(new BtnEliminarFilaActionListener());
		}
		return btnEliminarFila;
	}
	private JMenuBar getMenuBar_1() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMnArchivo());
		}
		return menuBar;
	}
	private JMenu getMnArchivo() {
		if (mnArchivo == null) {
			mnArchivo = new JMenu("Archivo");
			mnArchivo.setMnemonic('A');
			mnArchivo.add(getMntmCerrar());
		}
		return mnArchivo;
	}
	private JMenuItem getMntmCerrar() {
		if (mntmCerrar == null) {
			mntmCerrar = new JMenuItem("Cerrar");
			mntmCerrar.addActionListener(new MntmCerrarActionListener());
			mntmCerrar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK));
			mntmCerrar.setMnemonic('C');
		}
		return mntmCerrar;
	}
}

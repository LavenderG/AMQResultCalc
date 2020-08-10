package org.lavenderg.amqresultcalc.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;

import org.lavenderg.amqresultcalc.exceptions.AMQParserException;
import org.lavenderg.amqresultcalc.io.BBCodeWriter;
import org.lavenderg.amqresultcalc.io.FileUtil;
import org.lavenderg.amqresultcalc.io.ResultWriter;
import org.lavenderg.amqresultcalc.logic.result.Result;
import org.lavenderg.amqresultcalc.logic.result.ResultUtil;
import org.lavenderg.amqresultcalc.logic.round.Round;
import org.lavenderg.amqresultcalc.parser.ResultsParser;
import org.lavenderg.amqresultcalc.parser.RoundParser;

import javax.swing.JTextPane;
import javax.swing.JSplitPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

/**
 * Clase que forma la interfaz gráfica de la aplicación.
 * @author lavenderg
 *
 */
public class AMQResultCalcGUI extends JFrame {

	private static final long serialVersionUID = -1283616002326547034L;
	private static final String AMQRC_VERSION = "0.4";
	
	private JPanel contentPane;
	private JSplitPane splitPaneMainLog;
	private JSplitPane splitPaneRoundsResult;
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenuItem mntmCargarRondas;
	private JMenuItem mntmCargarResultados;
	private JSeparator separator;
	private JMenuItem mntmSalir;
	private JMenu mnCalcular;
	private JMenuItem mntmGenerarResultados;
	
	final RoundParser roundParser = new RoundParser();
	final ResultsParser resultsParser = new ResultsParser();
	final ResultWriter resultWriter = new ResultWriter();
	final TextPaneLogger paneLogger;
	final BBCodeWriter bbcode = new BBCodeWriter();
	
	List<Result> resultadosCargados = null;
	List<Round> rondasCargadas = null;
	private JScrollPane scrollPaneLog;
	private JTextPane textPaneLog;
	private JScrollPane scrollPaneRounds;
	private JTextPane textPaneRounds;
	private JScrollPane scrollPaneResults;
	private JTextPane textPaneResult;
	private JMenu mnAyuda;
	private JMenuItem mntmSobreAmqresultcalc;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AMQResultCalcGUI frame = new AMQResultCalcGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AMQResultCalcGUI() {
		setTitle("AMQ Result Calc");
		setAutoRequestFocus(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setJMenuBar(getMenuBar_1());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getSplitPaneMainLog(), BorderLayout.CENTER);
		paneLogger = new TextPaneLogger(textPaneLog);
	}
	private JSplitPane getSplitPaneMainLog() {
		if (splitPaneMainLog == null) {
			splitPaneMainLog = new JSplitPane();
			splitPaneMainLog.setResizeWeight(0.8);
			splitPaneMainLog.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitPaneMainLog.setLeftComponent(getSplitPaneRoundsResult());
			splitPaneMainLog.setRightComponent(getScrollPaneLog());
		}
		return splitPaneMainLog;
	}
	private JSplitPane getSplitPaneRoundsResult() {
		if (splitPaneRoundsResult == null) {
			splitPaneRoundsResult = new JSplitPane();
			splitPaneRoundsResult.setResizeWeight(0.6);
			splitPaneRoundsResult.setLeftComponent(getScrollPane_1());
			splitPaneRoundsResult.setRightComponent(getScrollPane_2());
		}
		return splitPaneRoundsResult;
	}
	private JMenuBar getMenuBar_1() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMenuFile());
			menuBar.add(getMnCalcular());
			menuBar.add(getMnAyuda());
		}
		return menuBar;
	}
	private JMenu getMenuFile() {
		if (menuFile == null) {
			menuFile = new JMenu("Archivo");
			menuFile.setMnemonic('A');
			menuFile.add(getMntmCargarRondas());
			menuFile.add(getMntmCargarResultados());
			menuFile.add(getSeparator());
			menuFile.add(getMntmSalir());
		}
		return menuFile;
	}
	private JMenuItem getMntmCargarRondas() {
		if (mntmCargarRondas == null) {
			mntmCargarRondas = new JMenuItem("Cargar rondas...");
			mntmCargarRondas.setMnemonic('r');
			mntmCargarRondas.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
			mntmCargarRondas.addActionListener(new MntmCargarRondasActionListener());
		}
		return mntmCargarRondas;
	}
	private JMenuItem getMntmCargarResultados() {
		if (mntmCargarResultados == null) {
			mntmCargarResultados = new JMenuItem("Cargar resultados previos...");
			mntmCargarResultados.setMnemonic('p');
			mntmCargarResultados.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));
			mntmCargarResultados.addActionListener(new MntmCargarResultadosActionListener());
		}
		return mntmCargarResultados;
	}
	private JSeparator getSeparator() {
		if (separator == null) {
			separator = new JSeparator();
		}
		return separator;
	}
	private JMenuItem getMntmSalir() {
		if (mntmSalir == null) {
			mntmSalir = new JMenuItem("Salir");
			mntmSalir.setMnemonic('S');
			mntmSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
			mntmSalir.addActionListener(new MntmSalirActionListener());
		}
		return mntmSalir;
	}
	private JMenu getMnCalcular() {
		if (mnCalcular == null) {
			mnCalcular = new JMenu("Calcular");
			mnCalcular.setMnemonic('C');
			mnCalcular.add(getMntmGenerarResultados());
		}
		return mnCalcular;
	}
	private JMenuItem getMntmGenerarResultados() {
		if (mntmGenerarResultados == null) {
			mntmGenerarResultados = new JMenuItem("Generar resultados");
			mntmGenerarResultados.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));
			mntmGenerarResultados.setMnemonic('G');
			mntmGenerarResultados.addActionListener(new MntmGenerarResultadosActionListener());
		}
		return mntmGenerarResultados;
	}
	class MntmCargarRondasActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			final JFileChooser fileChooser = new JFileChooser();
			int retVal = fileChooser.showOpenDialog(AMQResultCalcGUI.this);
			if (retVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				try {
					rondasCargadas = roundParser.parseRounds(file);
					getTextPaneRounds().setText(FileUtil.readTextFile(file));
					paneLogger.logInfo("Cargadas rondas desde archivo: " + file.getAbsolutePath());
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(AMQResultCalcGUI.this, String.format("El archivo %s no existe y no ha podido ser leído.", file.getAbsolutePath()), "Error al leer el archivo", JOptionPane.ERROR_MESSAGE);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(AMQResultCalcGUI.this, String.format("El archivo %s no ha podido ser leído.", file.getAbsolutePath()), "Error al leer el archivo", JOptionPane.ERROR_MESSAGE);
				} catch (BadLocationException e) {
					JOptionPane.showMessageDialog(AMQResultCalcGUI.this, "Un error ha ocurrido al escribir en el log.", "Error al escribir en el log", JOptionPane.ERROR_MESSAGE);
				} catch (AMQParserException e) {
					try {
						paneLogger.logError(String.format("Error al procesar los datos de entrada (%s): %s", file.getAbsolutePath(), e.getMessage()));
					} catch (BadLocationException e1) {
						JOptionPane.showMessageDialog(AMQResultCalcGUI.this, "Un error ha ocurrido al escribir en el log.", "Error al escribir en el log", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}
	class MntmCargarResultadosActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			final JFileChooser fileChooser = new JFileChooser();
			int retVal = fileChooser.showOpenDialog(AMQResultCalcGUI.this);
			if (retVal == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				try {
					resultadosCargados = resultsParser.parseResults(file);
					getTextPaneResult().setText(FileUtil.readTextFile(file));
					paneLogger.logInfo("Cargados resultados desde archivo: " + file.getAbsolutePath());
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(AMQResultCalcGUI.this, String.format("El archivo %s no existe y no ha podido ser leído.", file.getAbsolutePath()), "Error al leer el archivo", JOptionPane.ERROR_MESSAGE);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(AMQResultCalcGUI.this, String.format("El archivo %s no ha podido ser leído.", file.getAbsolutePath()), "Error al leer el archivo", JOptionPane.ERROR_MESSAGE);
				} catch (BadLocationException e) {
					JOptionPane.showMessageDialog(AMQResultCalcGUI.this, "Un error ha ocurrido al escribir en el log.", "Error al escribir en el log", JOptionPane.ERROR_MESSAGE);
				} catch (AMQParserException e) {
					try {
						paneLogger.logError(String.format("Error al procesar los datos de entrada (%s): %s", file.getAbsolutePath(), e.getMessage()));
					} catch (BadLocationException e1) {
						JOptionPane.showMessageDialog(AMQResultCalcGUI.this, "Un error ha ocurrido al escribir en el log.", "Error al escribir en el log", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}
	class MntmSalirActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			dispose();
		}
	}
	class MntmGenerarResultadosActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if (rondasCargadas == null) {
				JOptionPane.showMessageDialog(AMQResultCalcGUI.this, "No se han podido generar los resultados. Es necesario cargar las rondas.", "Error al generar resultados", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (resultadosCargados == null) {
				JOptionPane.showMessageDialog(AMQResultCalcGUI.this, "No se han podido generar los resultados. Es necesario cargar los resultados previos.", "Error al generar resultados", JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				File postBBC = new File("post_amq.bbc");
				File resultados = new File("resultados_out.amq");
				paneLogger.logInfo("Generando resultados...");
				bbcode.logRounds(rondasCargadas, resultadosCargados, postBBC);
				paneLogger.logSuccess("Resultados generados correctamente.");
				paneLogger.logSuccess(String.format("Post guardado en el archivo %s", postBBC.getAbsolutePath()));
				resultWriter.logResults(ResultUtil.calculateResultsTable(rondasCargadas, resultadosCargados), resultados);
				paneLogger.logSuccess(String.format("Resultados finales guardados en el archivo %s", resultados.getAbsolutePath()));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(AMQResultCalcGUI.this, "Los archivos generados no han podido ser guardados.", "Error al escribir archivos", JOptionPane.ERROR_MESSAGE);
			} catch (BadLocationException e) {
				JOptionPane.showMessageDialog(AMQResultCalcGUI.this, "Un error ha ocurrido al escribir en el log.", "Error al escribir en el log", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	class MntmSobreAmqresultcalcActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			JOptionPane.showMessageDialog(AMQResultCalcGUI.this, String.format("Calculadora simple para resultados de AMQ.\nLicencia GPL-3.0.\nVersión %s\nMás información en https://github.com/LavenderG/AMQResultCalc", AMQRC_VERSION), "Sobre AMQResultCalc", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	private JScrollPane getScrollPaneLog() {
		if (scrollPaneLog == null) {
			scrollPaneLog = new JScrollPane();
			scrollPaneLog.setViewportView(getTextPaneLog());
		}
		return scrollPaneLog;
	}
	private JTextPane getTextPaneLog() {
		if (textPaneLog == null) {
			textPaneLog = new JTextPane();
			textPaneLog.setToolTipText("Log");
			textPaneLog.setEditable(false);
		}
		return textPaneLog;
	}
	private JScrollPane getScrollPane_1() {
		if (scrollPaneRounds == null) {
			scrollPaneRounds = new JScrollPane();
			scrollPaneRounds.setViewportView(getTextPaneRounds());
		}
		return scrollPaneRounds;
	}
	JTextPane getTextPaneRounds() {
		if (textPaneRounds == null) {
			textPaneRounds = new JTextPane();
			textPaneRounds.setToolTipText("Rondas");
			textPaneRounds.setEditable(false);
		}
		return textPaneRounds;
	}
	private JScrollPane getScrollPane_2() {
		if (scrollPaneResults == null) {
			scrollPaneResults = new JScrollPane();
			scrollPaneResults.setViewportView(getTextPaneResult());
		}
		return scrollPaneResults;
	}
	JTextPane getTextPaneResult() {
		if (textPaneResult == null) {
			textPaneResult = new JTextPane();
			textPaneResult.setEditable(false);
			textPaneResult.setToolTipText("Resultados");
		}
		return textPaneResult;
	}
	private JMenu getMnAyuda() {
		if (mnAyuda == null) {
			mnAyuda = new JMenu("Ayuda");
			mnAyuda.setMnemonic('y');
			mnAyuda.add(getMntmSobreAmqresultcalc());
		}
		return mnAyuda;
	}
	private JMenuItem getMntmSobreAmqresultcalc() {
		if (mntmSobreAmqresultcalc == null) {
			mntmSobreAmqresultcalc = new JMenuItem("Sobre AMQResultCalc");
			mntmSobreAmqresultcalc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0));
			mntmSobreAmqresultcalc.setMnemonic('S');
			mntmSobreAmqresultcalc.addActionListener(new MntmSobreAmqresultcalcActionListener());
		}
		return mntmSobreAmqresultcalc;
	}
}

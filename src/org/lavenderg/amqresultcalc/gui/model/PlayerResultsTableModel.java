package org.lavenderg.amqresultcalc.gui.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import org.lavenderg.amqresultcalc.logic.result.Result;
import org.lavenderg.amqresultcalc.logic.result.ResultUtil;

public class PlayerResultsTableModel extends AbstractTableModel{


	private static final long serialVersionUID = 5332868247606271572L;

	private final PlayerResultsColumn[] columns = PlayerResultsColumn.values();
	private final List<Result> results = new ArrayList<>();

	public PlayerResultsTableModel(List<Result> results) {
		this();
		if (results != null) {
			this.results.addAll(ResultUtil.orderByPoints(results));
		}
	}

	public PlayerResultsTableModel() {
		this.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent arg0) {
				ResultUtil.orderByPoints(results);
			}
		});
	}

	@Override
	public int getColumnCount() {
		return this.columns.length;
	}

	@Override
	public int getRowCount() {
		return results.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		var row = results.get(rowIndex);
		var column = columns[columnIndex];
		switch (column) {
		case PUNTUACION:
			return row.getPlayerPoints();
		case NOMBRE:
			return row.getPlayerName();
		case POSICION:
			return rowIndex+ 1;
		default:
			throw new IndexOutOfBoundsException("Invalid column index: " + columnIndex);
		}
	}

	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		var row = results.get(rowIndex);
		var column = columns[columnIndex];
		switch (column) {
		case PUNTUACION:
			this.results.set(rowIndex, new Result(row.getPlayerName(), (Integer) value));
			this.fireTableDataChanged();
			break;
		default:
			throw new IllegalArgumentException("Cannot set value in column: " + columnIndex);
		}
	}

	 /**
     * Returns the column name for the column index.
     */
    @Override
    public String getColumnName(int column) {
        return this.columns[column].getColumnName();
    }

    /**
     * Returns data type of the column specified by its index.
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
    	return columns[columnIndex] == PlayerResultsColumn.PUNTUACION;
    }

    public void insertResult(Result result) {
    	this.results.add(result);
    	this.fireTableDataChanged();
    }

    public void removeRow(int rowIndex) {
    	this.results.remove(rowIndex);
    	this.fireTableDataChanged();
    }

    public List<Result> getResults() {
    	return this.results;
    }

}

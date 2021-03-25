package org.lavenderg.amqresultcalc.gui.model;

public enum PlayerResultsColumn {

	POSICION(0, "Posición"),
	NOMBRE(1, "Nombre"),
	PUNTUACION(2, "Puntuación");

	private final String columnName;
	private final int columnIndex;

	private PlayerResultsColumn(int columnIndex, String columnName) {
		this.columnName = columnName;
		this.columnIndex = columnIndex;
	}

	public int getColumnIndex() {
		return this.columnIndex;
	}

	public String getColumnName() {
		return this.columnName;
	}
}

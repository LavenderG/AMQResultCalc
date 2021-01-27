package org.lavenderg.amqresultcalc.io;


/**
 * Clase que representa una zona horaria de un país utilizada a la hora de mostrar los horarios
 * de la semana que viene.
 * @author lavenderg
 */
class CountryTime {
	private final String country;
	private final String timezone;
	private final String time;
	
	CountryTime(String country, String timezone, String time) {
		this.country = country;
		this.timezone = timezone;
		this.time = time;
	}

	String getCountry() {
		return country;
	}

	String getTimezone() {
		return timezone;
	}

	String getTime() {
		return time;
	}
}
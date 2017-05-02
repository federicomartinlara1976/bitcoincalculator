package net.bounceme.chronos.bitcoincalculator.dto;

import java.util.Date;
import java.util.Map;

public class ExchangeDTO {
	private String base;
	private Date date;
	private Map<String, Double> rates;
	/**
	 * @return the base
	 */
	public String getBase() {
		return base;
	}
	/**
	 * @param base the base to set
	 */
	public void setBase(String base) {
		this.base = base;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the rates
	 */
	public Map<String, Double> getRates() {
		return rates;
	}
	/**
	 * @param rates the rates to set
	 */
	public void setRates(Map<String, Double> rates) {
		this.rates = rates;
	}
}

package net.bounceme.chronos.bitcoincalculator.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class CoinItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5505348315610071173L;
	
	private String time;
	private BigDecimal coin;
	private BigDecimal currency;
	
	public CoinItem() {}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public BigDecimal getCoin() {
		return coin;
	}

	public void setCoin(BigDecimal coin) {
		this.coin = coin;
	}

	/**
	 * @return the currency
	 */
	public BigDecimal getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(BigDecimal currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "CoinItem [time=" + time + ", coin=" + coin + ", currency=" + currency + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coin == null) ? 0 : coin.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoinItem other = (CoinItem) obj;
		if (coin == null) {
			if (other.getCoin() != null)
				return false;
		}
		else if (!coin.equals(other.getCoin()))
			return false;
		if (currency == null) {
			if (other.getCurrency() != null)
				return false;
		}
		else if (!currency.equals(other.getCurrency()))
			return false;
		if (time == null) {
			if (other.getTime() != null)
				return false;
		}
		else if (!time.equals(other.getTime()))
			return false;
		return true;
	}
}

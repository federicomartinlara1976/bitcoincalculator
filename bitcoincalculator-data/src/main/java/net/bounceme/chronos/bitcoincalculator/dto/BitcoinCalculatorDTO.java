package net.bounceme.chronos.bitcoincalculator.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BitcoinCalculatorDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6678015140643175156L;

	private BigDecimal bc_per_block;

	private BigDecimal coins_before_retarget;

	private BigDecimal coins_per_hour;

	private BigDecimal coins_per_hour_after_retarget;

	private Double difficulty;

	private BigDecimal dollars_before_retarget;

	private BigDecimal dollars_per_hour;

	private BigDecimal dollars_per_hour_after_retarget;

	private Double exchange_rate;

	private String exchange_rate_source;

	private Double hashrate;

	private Double next_difficulty;

	private Double time_per_block;

	public BigDecimal getBc_per_block() {
		return bc_per_block;
	}

	public void setBc_per_block(BigDecimal bc_per_block) {
		this.bc_per_block = bc_per_block;
	}

	public BigDecimal getCoins_before_retarget() {
		return coins_before_retarget;
	}

	public void setCoins_before_retarget(BigDecimal coins_before_retarget) {
		this.coins_before_retarget = coins_before_retarget;
	}

	public BigDecimal getCoins_per_hour() {
		return coins_per_hour;
	}

	public void setCoins_per_hour(BigDecimal coins_per_hour) {
		this.coins_per_hour = coins_per_hour;
	}

	public BigDecimal getCoins_per_hour_after_retarget() {
		return coins_per_hour_after_retarget;
	}

	public void setCoins_per_hour_after_retarget(BigDecimal coins_per_hour_after_retarget) {
		this.coins_per_hour_after_retarget = coins_per_hour_after_retarget;
	}

	public Double getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Double difficulty) {
		this.difficulty = difficulty;
	}

	public BigDecimal getDollars_before_retarget() {
		return dollars_before_retarget;
	}

	public void setDollars_before_retarget(BigDecimal dollars_before_retarget) {
		this.dollars_before_retarget = dollars_before_retarget;
	}

	public BigDecimal getDollars_per_hour() {
		return dollars_per_hour;
	}

	public void setDollars_per_hour(BigDecimal dollars_per_hour) {
		this.dollars_per_hour = dollars_per_hour;
	}

	public BigDecimal getDollars_per_hour_after_retarget() {
		return dollars_per_hour_after_retarget;
	}

	public void setDollars_per_hour_after_retarget(BigDecimal dollars_per_hour_after_retarget) {
		this.dollars_per_hour_after_retarget = dollars_per_hour_after_retarget;
	}

	public Double getExchange_rate() {
		return exchange_rate;
	}

	public void setExchange_rate(Double exchange_rate) {
		this.exchange_rate = exchange_rate;
	}

	public String getExchange_rate_source() {
		return exchange_rate_source;
	}

	public void setExchange_rate_source(String exchange_rate_source) {
		this.exchange_rate_source = exchange_rate_source;
	}

	public Double getHashrate() {
		return hashrate;
	}

	public void setHashrate(Double hashrate) {
		this.hashrate = hashrate;
	}

	public Double getNext_difficulty() {
		return next_difficulty;
	}

	public void setNext_difficulty(Double next_difficulty) {
		this.next_difficulty = next_difficulty;
	}

	public Double getTime_per_block() {
		return time_per_block;
	}

	public void setTime_per_block(Double time_per_block) {
		this.time_per_block = time_per_block;
	}

	@Override
	public String toString() {
		return "BitcoinCalculatorDTO [bc_per_block=" + bc_per_block + ", coins_before_retarget=" + coins_before_retarget
				+ ", coins_per_hour=" + coins_per_hour + ", coins_per_hour_after_retarget="
				+ coins_per_hour_after_retarget + ", difficulty=" + difficulty + ", dollars_before_retarget="
				+ dollars_before_retarget + ", dollars_per_hour=" + dollars_per_hour
				+ ", dollars_per_hour_after_retarget=" + dollars_per_hour_after_retarget + ", exchange_rate="
				+ exchange_rate + ", exchange_rate_source=" + exchange_rate_source + ", hashrate=" + hashrate
				+ ", next_difficulty=" + next_difficulty + ", time_per_block=" + time_per_block + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bc_per_block == null) ? 0 : bc_per_block.hashCode());
		result = prime * result + ((coins_before_retarget == null) ? 0 : coins_before_retarget.hashCode());
		result = prime * result + ((coins_per_hour == null) ? 0 : coins_per_hour.hashCode());
		result = prime * result
				+ ((coins_per_hour_after_retarget == null) ? 0 : coins_per_hour_after_retarget.hashCode());
		result = prime * result + ((difficulty == null) ? 0 : difficulty.hashCode());
		result = prime * result + ((dollars_before_retarget == null) ? 0 : dollars_before_retarget.hashCode());
		result = prime * result + ((dollars_per_hour == null) ? 0 : dollars_per_hour.hashCode());
		result = prime * result
				+ ((dollars_per_hour_after_retarget == null) ? 0 : dollars_per_hour_after_retarget.hashCode());
		result = prime * result + ((exchange_rate == null) ? 0 : exchange_rate.hashCode());
		result = prime * result + ((exchange_rate_source == null) ? 0 : exchange_rate_source.hashCode());
		result = prime * result + ((hashrate == null) ? 0 : hashrate.hashCode());
		result = prime * result + ((next_difficulty == null) ? 0 : next_difficulty.hashCode());
		result = prime * result + ((time_per_block == null) ? 0 : time_per_block.hashCode());
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
		BitcoinCalculatorDTO other = (BitcoinCalculatorDTO) obj;
		if (bc_per_block == null) {
			if (other.bc_per_block != null)
				return false;
		}
		else if (!bc_per_block.equals(other.bc_per_block))
			return false;
		if (coins_before_retarget == null) {
			if (other.getCoins_before_retarget() != null)
				return false;
		}
		else if (!coins_before_retarget.equals(other.getCoins_before_retarget()))
			return false;
		if (coins_per_hour == null) {
			if (other.getCoins_per_hour() != null)
				return false;
		}
		else if (!coins_per_hour.equals(other.getCoins_per_hour()))
			return false;
		if (coins_per_hour_after_retarget == null) {
			if (other.getCoins_per_hour_after_retarget() != null)
				return false;
		}
		else if (!coins_per_hour_after_retarget.equals(other.getCoins_per_hour_after_retarget()))
			return false;
		if (difficulty == null) {
			if (other.getDifficulty() != null)
				return false;
		}
		else if (!difficulty.equals(other.getDifficulty()))
			return false;
		if (dollars_before_retarget == null) {
			if (other.getDollars_before_retarget() != null)
				return false;
		}
		else if (!dollars_before_retarget.equals(other.getDollars_before_retarget()))
			return false;
		if (dollars_per_hour == null) {
			if (other.getDollars_per_hour() != null)
				return false;
		}
		else if (!dollars_per_hour.equals(other.getDollars_per_hour()))
			return false;
		if (dollars_per_hour_after_retarget == null) {
			if (other.getDollars_per_hour_after_retarget() != null)
				return false;
		}
		else if (!dollars_per_hour_after_retarget.equals(other.getDollars_per_hour_after_retarget()))
			return false;
		if (exchange_rate == null) {
			if (other.getExchange_rate() != null)
				return false;
		}
		else if (!exchange_rate.equals(other.getExchange_rate()))
			return false;
		if (exchange_rate_source == null) {
			if (other.getExchange_rate_source() != null)
				return false;
		}
		else if (!exchange_rate_source.equals(other.getExchange_rate_source()))
			return false;
		if (hashrate == null) {
			if (other.getHashrate() != null)
				return false;
		}
		else if (!hashrate.equals(other.getHashrate()))
			return false;
		if (next_difficulty == null) {
			if (other.getNext_difficulty() != null)
				return false;
		}
		else if (!next_difficulty.equals(other.getNext_difficulty()))
			return false;
		if (time_per_block == null) {
			if (other.getTime_per_block() != null)
				return false;
		}
		else if (!time_per_block.equals(other.getTime_per_block()))
			return false;
		return true;
	}
}

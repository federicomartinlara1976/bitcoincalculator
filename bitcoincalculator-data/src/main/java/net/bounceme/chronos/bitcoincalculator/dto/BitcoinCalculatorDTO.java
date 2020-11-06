package net.bounceme.chronos.bitcoincalculator.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
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
}

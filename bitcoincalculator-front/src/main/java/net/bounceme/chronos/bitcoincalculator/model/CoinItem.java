package net.bounceme.chronos.bitcoincalculator.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class CoinItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5505348315610071173L;
	
	private String time;
	private BigDecimal coin;
	private BigDecimal currency;
}

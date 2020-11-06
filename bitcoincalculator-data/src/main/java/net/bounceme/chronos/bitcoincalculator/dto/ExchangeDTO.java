package net.bounceme.chronos.bitcoincalculator.dto;

import java.util.Date;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class ExchangeDTO {
	private Boolean success;
	private Long timestamp;
	private String base;
	private Date date;
	private Map<String, Double> rates;
}

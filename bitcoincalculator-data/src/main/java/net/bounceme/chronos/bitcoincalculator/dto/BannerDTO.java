package net.bounceme.chronos.bitcoincalculator.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class BannerDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8978802491253990450L;
	
	private String styleClass;
	private String content;
}

package net.bounceme.chronos.bitcoincalculator.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class FeatureDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7894851641386341899L;

	private String title;
	private String icon;
	private String content;
	private String styleClass;
}

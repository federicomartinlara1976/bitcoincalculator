package net.bounceme.chronos.bitcoincalculator.dto;

import java.io.Serializable;

public class BannerDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8978802491253990450L;
	
	private String styleClass;
	private String content;
	/**
	 * @return the styleClass
	 */
	public String getStyleClass() {
		return styleClass;
	}
	/**
	 * @param styleClass the styleClass to set
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
}

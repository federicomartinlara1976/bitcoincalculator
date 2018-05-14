package net.bounceme.chronos.bitcoincalculator.dto;

import java.io.Serializable;

public class FeatureDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7894851641386341899L;

	private String title;
	private String icon;
	private String content;
	private String styleClass;
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the icon
	 */
	public String getIcon() {
		return icon;
	}
	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
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
}

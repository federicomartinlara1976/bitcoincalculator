package net.bounceme.chronos.bitcoincalculator.services.trading;

public abstract class TraderBase implements Trader {
	
	private String url;
	private String name;
	private String icon;
	
	public TraderBase(String url, String name, String icon) {
		super();
		this.url = url;
		this.name = name;
		this.icon = icon;
	}
	
	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public String getIcon() {
		return icon;
	}
}

package net.bounceme.chronos.bitcoincalculator.services;

public interface PropertiesService {
	public static final String NAME = "propertiesService";
	
	String getProperty(String name);
}

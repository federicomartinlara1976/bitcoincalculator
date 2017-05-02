package net.bounceme.chronos.bitcoincalculator.services;

import java.util.Map;

import net.bounceme.chronos.bitcoincalculator.exceptions.ServiceException;

public interface GestorConexiones {
	public static String NAME = "gestorConexiones";
	
	String getData(Map<String, String> parameters) throws ServiceException;
}

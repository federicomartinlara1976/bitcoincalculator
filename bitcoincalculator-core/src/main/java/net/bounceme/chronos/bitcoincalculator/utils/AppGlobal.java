package net.bounceme.chronos.bitcoincalculator.utils;

import java.util.HashMap;

public class AppGlobal extends HashMap<String, Object> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static AppGlobal instance = null;
	
	public static AppGlobal getInstance() {
		if (instance == null) {
			instance = new AppGlobal();
		}
		return instance;
	}
	
	private AppGlobal() {
		
	}
}

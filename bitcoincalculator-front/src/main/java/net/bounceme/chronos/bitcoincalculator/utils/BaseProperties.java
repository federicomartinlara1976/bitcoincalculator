package net.bounceme.chronos.bitcoincalculator.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class BaseProperties {
	protected String bundleName;
	
	private ResourceBundle resourceBundle;
	
	/**
	 * @param key
	 * @return
	 */
	public String getString(String key, Locale locale) {
		try {
			resourceBundle = ResourceBundle.getBundle(bundleName, locale);
			return resourceBundle.getString(key);
		}
		catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	public String getString(String key, Locale locale, String... arguments) {
		try {
			resourceBundle = ResourceBundle.getBundle(bundleName, locale);
			return MessageFormat.format(resourceBundle.getString(key), arguments);
		}
		catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}

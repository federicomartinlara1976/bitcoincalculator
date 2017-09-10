package net.bounceme.chronos.bitcoincalculator.messages;

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
	public String getString(String key, Locale lang) {
		try {
			loadBundle(lang);
			return resourceBundle.getString(key);
		}
		catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	public String getString(String key, Locale lang, String... arguments) {
		try {
			loadBundle(lang);
			return MessageFormat.format(resourceBundle.getString(key), arguments);
		}
		catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	private void loadBundle(Locale lang) {
		resourceBundle = ResourceBundle.getBundle(bundleName, lang);
	}
}

package net.bounceme.chronos.bitcoincalculator.messages;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import net.bounceme.chronos.utils.common.Constantes.Locales;

public class BaseProperties {
	protected String bundleName;
	
	private ResourceBundle resourceBundle;
	
	/**
	 * @param key
	 * @return
	 */
	public String getString(String key, String lang) {
		try {
			loadBundle(lang);
			return resourceBundle.getString(key);
		}
		catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	public String getString(String key, String lang, String... arguments) {
		try {
			loadBundle(lang);
			return MessageFormat.format(resourceBundle.getString(key), arguments);
		}
		catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	private void loadBundle(String lang) {
		Locale locale = (Locales.valueOf(Locales.class, lang)).value();
		resourceBundle = ResourceBundle.getBundle(bundleName, locale);
	}
}

package net.bounceme.chronos.bitcoincalculator.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * @author frederik
 *
 */
public class MessageProperties {
	private static final String BUNDLE_NAME = "net.bounceme.chronos.bitcoincalculator.MessageBundle"; //$NON-NLS-1$

	private static MessageProperties instance;

	private ResourceBundle RESOURCE_BUNDLE; 

	private static final Logger LOGGER = Logger.getLogger(MessageProperties.class);

	private MessageProperties() {
	}

	public static MessageProperties getInstance() {
		if (instance == null) {
			instance = new MessageProperties();
		}
		return instance;
	}

	/**
	 * @param key
	 * @return
	 */
	public String getString(String key, Locale locale) {
		try {
			RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, locale);
			return RESOURCE_BUNDLE.getString(key);
		}
		catch (MissingResourceException e) {
			LOGGER.error(e);
			return '!' + key + '!';
		}
	}
	
	public String getString(String key, Locale locale, String... arguments) {
		try {
			RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, locale);
			String message = RESOURCE_BUNDLE.getString(key);
			return MessageFormat.format(message, arguments);
		}
		catch (MissingResourceException e) {
			LOGGER.error(e);
			return '!' + key + '!';
		}
	}
}

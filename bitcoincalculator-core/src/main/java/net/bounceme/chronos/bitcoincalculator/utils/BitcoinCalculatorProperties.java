package net.bounceme.chronos.bitcoincalculator.utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import net.bounceme.chronos.utils.properties.CollectionProperties;
import net.bounceme.chronos.utils.properties.service.dto.PropertyDTO;

/**
 * @author frederik
 *
 */
public class BitcoinCalculatorProperties implements CollectionProperties {
	private static final String BUNDLE_NAME = "BitcoinCalculator"; //$NON-NLS-1$
	
	private static BitcoinCalculatorProperties instance;

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private static final Logger LOGGER = Logger.getLogger(BitcoinCalculatorProperties.class);
	
	private BitcoinCalculatorProperties() {}
	
	public static BitcoinCalculatorProperties getInstance() {
		if (instance == null) {
			instance = new BitcoinCalculatorProperties();
		}
		return instance;
	}

	/**
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			LOGGER.error(e);
			return '!' + key + '!';
		}
	}

	/* (non-Javadoc)
	 * @see net.bounceme.chronos.utils.properties.CollectionProperties#getProperties()
	 */
	@Override
	public Map<String, String> getProperties() {
		Enumeration<String> keys = RESOURCE_BUNDLE.getKeys();
		Map<String, String> properties = new HashMap<String, String>();
		
		while(keys.hasMoreElements()) {
			String key = keys.nextElement();
			String value = RESOURCE_BUNDLE.getString(key);
			properties.put(key, value);
		}
		
		return properties;
	}

	/* (non-Javadoc)
	 * @see net.bounceme.chronos.utils.properties.CollectionProperties#getListProperties()
	 */
	@Override
	public List<PropertyDTO> getListProperties() {
		Map<String, String> properties = getProperties();
		List<PropertyDTO> listProperties = new ArrayList<PropertyDTO>();
		
		for (Map.Entry<String, String> key : properties.entrySet()) {
			PropertyDTO propertyDTO = new PropertyDTO(key.getKey(), key.getValue());
			listProperties.add(propertyDTO);
		}
		
		return listProperties;
	}
}

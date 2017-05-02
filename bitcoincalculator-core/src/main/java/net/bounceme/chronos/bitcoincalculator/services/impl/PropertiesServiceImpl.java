package net.bounceme.chronos.bitcoincalculator.services.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.bounceme.chronos.bitcoincalculator.services.PropertiesService;
import net.bounceme.chronos.bitcoincalculator.utils.BitcoinCalculatorProperties;

/**
 * @author frederik
 *
 */
@Service(PropertiesService.NAME)
@Scope("singleton")
public class PropertiesServiceImpl implements PropertiesService {

	@Override
	public String getProperty(String name) {
		return BitcoinCalculatorProperties.getInstance().getString(name);
	}

}

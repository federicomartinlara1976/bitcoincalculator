package net.bounceme.chronos.bitcoincalculator.services.trading;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.bounceme.chronos.bitcoincalculator.exceptions.TraderException;
import net.bounceme.chronos.bitcoincalculator.services.CalculatorService;

@Component
public class DefaultTrader extends TraderBase  {
	private static final String NAME = "Default";
	
	@Autowired
	private CalculatorService calculatorService;

	public DefaultTrader() {
		super(null, NAME, null);
	}

	public BigDecimal getExchange() throws TraderException {
		return calculatorService.getCurrentExchange();
	}

	@Override
	public Boolean getDisabled() {
		return Boolean.FALSE;
	}
}

package net.bounceme.chronos.bitcoincalculator.services;

import java.math.BigDecimal;

import net.bounceme.chronos.bitcoincalculator.common.ConstantesBitcoinCalculator.ExchangeTypes;
import net.bounceme.chronos.bitcoincalculator.exceptions.ServiceException;

public interface ExchangeService {
	static final String NAME = "exchangeService";

	BigDecimal initCurrency(BigDecimal sCurrency, ExchangeTypes symbol) throws ServiceException;
	
	BigDecimal changeCurrency(BigDecimal sCurrency, ExchangeTypes symbol) throws ServiceException;
}
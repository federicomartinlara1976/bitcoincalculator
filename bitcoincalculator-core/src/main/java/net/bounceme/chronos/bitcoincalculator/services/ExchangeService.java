package net.bounceme.chronos.bitcoincalculator.services;

import java.math.BigDecimal;

import net.bounceme.chronos.bitcoincalculator.common.Constantes.ExchangeTypes;
import net.bounceme.chronos.bitcoincalculator.exceptions.ServiceException;

public interface ExchangeService {
	static final String NAME = "exchangeService";

	BigDecimal changeCurrency(BigDecimal sCurrency, ExchangeTypes base, ExchangeTypes symbol) throws ServiceException;
}
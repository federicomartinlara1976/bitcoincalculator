package net.bounceme.chronos.bitcoincalculator.services;

import java.math.BigDecimal;
import java.util.List;

import net.bounceme.chronos.bitcoincalculator.common.ConstantesBitcoinCalculator.Traders;
import net.bounceme.chronos.bitcoincalculator.dto.BitcoinCalculatorDTO;
import net.bounceme.chronos.bitcoincalculator.exceptions.ServiceException;
import net.bounceme.chronos.bitcoincalculator.services.trading.Trader;

public interface CalculatorService {
	static final String NAME = "calculatorService";
	
	Double getCurrentDifficultyFactor();
	
	Double getNextDifficultyFactor();
	
	BigDecimal getCurrentExchange();
	
	BigDecimal getExchange(Trader trader) throws ServiceException;
	
	List<Trader> getTraders() throws ServiceException;
	
	Trader getTrader(Traders traders) throws ServiceException;
	
	BigDecimal getCurrentBcPerBlock();
	
	String getCurrentExchangeRateSource();
	
	BitcoinCalculatorDTO getData(Long hashRate, BigDecimal exchangeAmount) throws ServiceException;
}

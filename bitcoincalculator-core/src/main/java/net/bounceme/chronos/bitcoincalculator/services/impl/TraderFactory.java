package net.bounceme.chronos.bitcoincalculator.services.impl;

import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j;
import net.bounceme.chronos.bitcoincalculator.common.ConstantesBitcoinCalculator.Traders;
import net.bounceme.chronos.bitcoincalculator.exceptions.TraderException;
import net.bounceme.chronos.bitcoincalculator.services.trading.Trader;
import net.bounceme.chronos.bitcoincalculator.utils.LogWrapper;

@Service(TraderFactory.NAME)
@Log4j
public class TraderFactory {

	public static final String NAME = "traderFactory";
	
	/**
	 * @param name
	 * @return
	 * @throws TraderException
	 */
	public Trader getTrader(String name) throws TraderException {
		try {
			Class<?> traderClass = Traders.valueOf(Traders.class, name).getTraderClass();
			return (Trader) traderClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			LogWrapper.error(log, "ERROR", e);
			throw new TraderException(e);
		}
	}
	
	/**
	 * @param name
	 * @return
	 * @throws TraderException
	 */
	public Trader getTrader(Traders trader) throws TraderException {
		try {
			Class<?> traderClass = trader.getTraderClass();
			return (Trader) traderClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			log.error("ERROR", e);
			throw new TraderException(e);
		}
	}
	
	public Traders[] getTraders() {
		return Traders.values();
	}
}

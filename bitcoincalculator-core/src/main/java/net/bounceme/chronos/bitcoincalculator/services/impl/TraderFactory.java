package net.bounceme.chronos.bitcoincalculator.services.impl;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import net.bounceme.chronos.bitcoincalculator.common.ConstantesBitcoinCalculator.Traders;
import net.bounceme.chronos.bitcoincalculator.exceptions.TraderException;
import net.bounceme.chronos.bitcoincalculator.services.trading.Trader;

@Service(TraderFactory.NAME)
public class TraderFactory {

	public static final String NAME = "traderFactory";
	
	private static Logger log = Logger.getLogger(TraderFactory.class.getName());
	
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
			log.error("ERROR", e);
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

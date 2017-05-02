package net.bounceme.chronos.bitcoincalculator.services.impl;

import org.springframework.stereotype.Service;

import net.bounceme.chronos.bitcoincalculator.common.Constantes.Traders;
import net.bounceme.chronos.bitcoincalculator.exceptions.TraderException;
import net.bounceme.chronos.bitcoincalculator.services.trading.Trader;
import net.bounceme.chronos.utils.log.Log;
import net.bounceme.chronos.utils.log.Log.LogLevels;
import net.bounceme.chronos.utils.log.LogFactory;

@Service(TraderFactory.NAME)
public class TraderFactory {

	public static final String NAME = "traderFactory";
	
	private static final Log LOGGER = LogFactory.getInstance().getLog(TraderFactory.class);
	
	public TraderFactory() {}

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
			LOGGER.log(LogLevels.ERROR, e);
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
			LOGGER.log(LogLevels.ERROR, e);
			throw new TraderException(e);
		}
	}
	
	public Traders[] getTraders() {
		return Traders.values();
	}
}

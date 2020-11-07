package net.bounceme.chronos.bitcoincalculator.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j;
import net.bounceme.chronos.bitcoincalculator.aspect.TrackTime;
import net.bounceme.chronos.bitcoincalculator.common.ConstantesBitcoinCalculator;
import net.bounceme.chronos.bitcoincalculator.common.ConstantesBitcoinCalculator.Traders;
import net.bounceme.chronos.bitcoincalculator.config.AppConfig;
import net.bounceme.chronos.bitcoincalculator.dto.BitcoinCalculatorDTO;
import net.bounceme.chronos.bitcoincalculator.exceptions.ServiceException;
import net.bounceme.chronos.bitcoincalculator.exceptions.TraderException;
import net.bounceme.chronos.bitcoincalculator.services.trading.Trader;
import net.bounceme.chronos.bitcoincalculator.services.trading.TraderFactory;
import net.bounceme.chronos.bitcoincalculator.utils.LogWrapper;
import net.bounceme.chronos.utils.mapping.JacksonConverter;

/**
 * @author frederik
 *
 */
@Service
@Log4j
public class CalculatorService {
	
	private enum Params {
		hashrate, exchange_rate
	}

	@Autowired
	private GestorConexiones gestorConexiones;

	@Autowired
	private TraderFactory traderFactory;

	@Autowired
	@Qualifier(AppConfig.BITCOIN_CONVERTER)
	private JacksonConverter<BitcoinCalculatorDTO> bitcoinConverter;

	private BitcoinCalculatorDTO data;

	@PostConstruct
	public void initialize() {
		initializeData();
	}

	public BigDecimal getExchange(Trader trader) throws ServiceException {
		try {
			return (trader!=null) ? trader.getExchange() : BigDecimal.ZERO;
		}
		catch (TraderException e) {
			log.error("ERROR", e);
			throw new ServiceException(e);
		}
	}
	
	public BitcoinCalculatorDTO getData() {
		return data;
	}

	@TrackTime
	public BitcoinCalculatorDTO getData(Long hashRate, BigDecimal exchangeAmount) throws ServiceException {
		try {
			Map<String, Object> parameters = new HashMap<>();
			parameters.put(Params.hashrate.name(), hashRate.toString());
			parameters.put(Params.exchange_rate.name(),
					exchangeAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			String result = gestorConexiones.getData(parameters);
			return bitcoinConverter.reverseAssemble(result);
		}
		catch (ServiceException e) {
			LogWrapper.error(log, "ERROR", e);
			throw new ServiceException(e);
		}
	}

	private void initializeData() {
		try {
			Map<String, Object> parameters = new HashMap<>();
			parameters.put(Params.hashrate.name(), ConstantesBitcoinCalculator.INITIAL_HASHRATE);
			String result = gestorConexiones.getData(parameters);
			data = bitcoinConverter.reverseAssemble(result);
		}
		catch (ServiceException e) {
			LogWrapper.error(log, "ERROR", e);
		}
	}

	public List<Trader> getTraders() throws ServiceException {
		try {
			List<Trader> results = new ArrayList<>();
			Traders[] traders = traderFactory.getTraders();
			for (Traders trader : traders) {
				Trader t = traderFactory.getTrader(trader);
				results.add(t);
			}
			return results;
		}
		catch (TraderException e) {
			LogWrapper.error(log, "ERROR", e);
			throw new ServiceException(e);
		}
	}

	public Trader getTrader(Traders traders) throws ServiceException {
		try {
			return traderFactory.getTrader(traders);
		}
		catch (TraderException e) {
			LogWrapper.error(log, "ERROR", e);
			throw new ServiceException(e);
		}
	}
}

package net.bounceme.chronos.bitcoincalculator.services.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.bounceme.chronos.bitcoincalculator.common.ConstantesBitcoinCalculator;
import net.bounceme.chronos.bitcoincalculator.common.ConstantesBitcoinCalculator.Traders;
import net.bounceme.chronos.bitcoincalculator.config.AppConfig;
import net.bounceme.chronos.bitcoincalculator.dto.BitcoinCalculatorDTO;
import net.bounceme.chronos.bitcoincalculator.exceptions.ServiceException;
import net.bounceme.chronos.bitcoincalculator.exceptions.TraderException;
import net.bounceme.chronos.bitcoincalculator.services.CalculatorService;
import net.bounceme.chronos.bitcoincalculator.services.GestorConexiones;
import net.bounceme.chronos.bitcoincalculator.services.trading.Trader;
import net.bounceme.chronos.utils.exceptions.AssembleException;
import net.bounceme.chronos.utils.log.Log;
import net.bounceme.chronos.utils.log.Log.LogLevels;
import net.bounceme.chronos.utils.log.LogFactory;
import net.bounceme.chronos.utils.mapping.JacksonConverter;

/**
 * @author frederik
 *
 */
@Service(CalculatorService.NAME)
@Scope("session")
public class CalculatorServiceImpl implements CalculatorService {
	private static final Log LOGGER = LogFactory.getInstance().getLog(CalculatorServiceImpl.class);

	private enum Params {
		hashrate, exchange_rate
	}

	@Autowired
	@Qualifier(GestorConexiones.NAME)
	private GestorConexiones gestorConexiones;

	@Autowired
	@Qualifier(TraderFactory.NAME)
	private TraderFactory traderFactory;

	@Autowired
	@Qualifier(AppConfig.BITCOIN_CONVERTER)
	private JacksonConverter<BitcoinCalculatorDTO> bitcoinConverter;

	private BitcoinCalculatorDTO data;

	@PostConstruct
	public void initialize() {
		initializeData();
	}

	@Override
	public Double getCurrentDifficultyFactor() {
		return (data != null) ? data.getDifficulty() : null;
	}

	@Override
	public Double getNextDifficultyFactor() {
		return (data != null) ? data.getNext_difficulty() : null;
	}

	@Override
	public BigDecimal getCurrentExchange() {
		return (data != null) ? new BigDecimal(data.getExchange_rate()) : null;
	}

	@Override
	public BigDecimal getCurrentBcPerBlock() {
		return (data != null) ? data.getBc_per_block() : null;
	}

	@Override
	public String getCurrentExchangeRateSource() {
		return (data != null) ? data.getExchange_rate_source() : null;
	}

	@Override
	public BigDecimal getExchange(Trader trader) throws ServiceException {
		try {
			return (trader!=null) ? trader.getExchange() : BigDecimal.ZERO;
		}
		catch (TraderException e) {
			LOGGER.log(LogLevels.ERROR, e);
			throw new ServiceException(e);
		}
	}

	@Override
	public BitcoinCalculatorDTO getData(Long hashRate, BigDecimal exchangeAmount) throws ServiceException {
		try {
			Map<String, String> parameters = new HashMap<>();
			parameters.put(Params.hashrate.name(), hashRate.toString());
			parameters.put(Params.exchange_rate.name(),
					exchangeAmount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			String result = gestorConexiones.getData(parameters);
			return bitcoinConverter.reverseAssemble(result);
		}
		catch (ServiceException | AssembleException e) {
			LOGGER.log(LogLevels.ERROR, e);
			throw new ServiceException(e);
		}
	}

	private void initializeData() {
		try {
			Map<String, String> parameters = new HashMap<>();
			parameters.put(Params.hashrate.name(), ConstantesBitcoinCalculator.INITIAL_HASHRATE);
			String result = gestorConexiones.getData(parameters);
			data = bitcoinConverter.reverseAssemble(result);
		}
		catch (ServiceException | AssembleException e) {
			LOGGER.log(LogLevels.ERROR, e);
		}
	}

	@Override
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
			LOGGER.log(LogLevels.ERROR, e);
			throw new ServiceException(e);
		}
	}

	@Override
	public Trader getTrader(Traders traders) throws ServiceException {
		try {
			return traderFactory.getTrader(traders);
		}
		catch (TraderException e) {
			LOGGER.log(LogLevels.ERROR, e);
			throw new ServiceException(e);
		}
	}
}

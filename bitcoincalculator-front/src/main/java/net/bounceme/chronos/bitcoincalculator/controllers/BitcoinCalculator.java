package net.bounceme.chronos.bitcoincalculator.controllers;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import net.bounceme.chronos.bitcoincalculator.common.Constantes;
import net.bounceme.chronos.bitcoincalculator.common.Constantes.ExchangeTypes;
import net.bounceme.chronos.bitcoincalculator.common.Constantes.HashRates;
import net.bounceme.chronos.bitcoincalculator.common.Constantes.Traders;
import net.bounceme.chronos.bitcoincalculator.dto.BitcoinCalculatorDTO;
import net.bounceme.chronos.bitcoincalculator.exceptions.ServiceException;
import net.bounceme.chronos.bitcoincalculator.exceptions.TraderException;
import net.bounceme.chronos.bitcoincalculator.model.CoinItem;
import net.bounceme.chronos.bitcoincalculator.services.CalculatorService;
import net.bounceme.chronos.bitcoincalculator.services.ExchangeService;
import net.bounceme.chronos.bitcoincalculator.services.trading.Trader;
import net.bounceme.chronos.bitcoincalculator.utils.MessageUtils;
import net.bounceme.chronos.utils.jsf.controller.BaseBean;
import net.bounceme.chronos.utils.log.Log;
import net.bounceme.chronos.utils.log.Log.LogLevels;
import net.bounceme.chronos.utils.log.LogFactory;

@ManagedBean(name = BitcoinCalculator.NAME)
@ViewScoped
public class BitcoinCalculator extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 354285024366651540L;

	public static final String NAME = "bitcoinCalculator";

	private static final Log LOGGER = LogFactory.getInstance().getLog(BitcoinCalculator.class);

	@ManagedProperty(value = "#{sessionBean}")
	private SessionBean sessionBean;

	@Autowired
	@Qualifier(CalculatorService.NAME)
	private CalculatorService calculatorService;

	@Autowired
	@Qualifier(ExchangeService.NAME)
	private ExchangeService exchangeService;

	private Double hashRateAmount;

	private String hashRateMultiply;

	private Long hashRate;

	private String exchangeType;

	private BigDecimal exchangeAmount;

	private BigDecimal exchange;

	private String exchangeRateSource;

	private List<Trader> traders;

	private List<CoinItem> thisDifficulty;

	private List<CoinItem> nextDifficulty;

	private ExchangeTypes lastType;

	public BitcoinCalculator() {
	}

	@PostConstruct
	public void init() {
		initVars();
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	public void calculate(ActionEvent actionEvent) {
		try {
			if (validateHash() && validateExchange()) {
				calculateHashRate();
				BitcoinCalculatorDTO resultados = calculatorService.getData(hashRate, exchangeAmount);
				thisDifficulty = buildThisFromResults(resultados);
				nextDifficulty = buildNextFromResults(resultados);
			}
		}
		catch (ServiceException e) {
			LOGGER.log(LogLevels.ERROR, e);
			addErrorMessage(e);
		}
	}

	public void reset(ActionEvent actionEvent) {
		try {
			initVars();
			sessionBean.setCurrentTrader(calculatorService.getTrader(Traders.Default));
		}
		catch (ServiceException e) {
			LOGGER.log(LogLevels.ERROR, e);
			addErrorMessage(e);
		}
	}

	public void onChangeHashRate() {
		if (validateHash()) {
			calculateHashRate();
			String message = MessageUtils.getMessage("calculator.tasaSeleccionada", sessionBean.getLang(),
					hashRate.toString());
			addMessage(FacesMessage.SEVERITY_INFO, message);
		}
	}

	/**
	 * Cuando se cambia el tipo de moneda
	 */
	public void onChangeExchange() {
		try {
			setNewExchange();
		}
		catch (ServiceException e) {
			LOGGER.log(LogLevels.ERROR, e);
			addErrorMessage(e);
		}
	}

	/**
	 * Cuando se elige otro mercado del cuadro de diálogo
	 */
	public void onSelectTrader() {
		try {
			exchangeAmount = sessionBean.getCurrentTrader().getExchange();
			setNewExchange();
		}
		catch (ServiceException | TraderException e) {
			LOGGER.log(LogLevels.ERROR, e);
			addErrorMessage(e);
		}
	}

	private void setNewExchange() throws ServiceException {
		ExchangeTypes eType = getEType(exchangeType);
		exchangeAmount = getExchangeAmount(exchangeAmount, eType).setScale(2, BigDecimal.ROUND_HALF_UP);
		String message = MessageUtils.getMessage("calculator.tasaCambioSeleccionado", sessionBean.getLang(),
				exchangeAmount.toString(), exchangeType);
		addMessage(FacesMessage.SEVERITY_INFO, message);
	}

	/**
	 * @param exchangeType
	 * @return
	 */
	private ExchangeTypes getEType(String exchangeType) {
		return ExchangeTypes.valueOf(ExchangeTypes.class, exchangeType);
	}

	private BigDecimal getExchangeAmount(BigDecimal source, ExchangeTypes eType) throws ServiceException {
		BigDecimal value = source;
		if (!eType.equals(lastType)) {
			value = exchangeService.changeCurrency(source, lastType, eType);
			lastType = eType;
		}
		return value;
	}

	private void calculateHashRate() {
		Long hashRateNum = HashRates.valueOf(HashRates.class, hashRateMultiply).getMultiply();
		hashRate = (Double.valueOf(hashRateAmount * hashRateNum)).longValue();
	}

	private Boolean validateHash() {
		if (StringUtils.isEmpty(hashRateMultiply)) {
			String message = MessageUtils.getMessage("calculator.medidaHashVacia", sessionBean.getLang());
			addMessage(FacesMessage.SEVERITY_WARN, message);
			return Boolean.FALSE;
		}

		if (hashRateAmount == 0L) {
			String message = MessageUtils.getMessage("calculator.tasaHashVacia", sessionBean.getLang());
			addMessage(FacesMessage.SEVERITY_WARN, message);
			return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}

	private Boolean validateExchange() {
		if (StringUtils.isEmpty(exchangeType)) {
			String message = MessageUtils.getMessage("calculator.tipoCambioVacio", sessionBean.getLang());
			addMessage(FacesMessage.SEVERITY_WARN, message);
			return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}

	/**
	 * @param resultados2
	 * @return
	 */
	private List<CoinItem> buildThisFromResults(BitcoinCalculatorDTO dto) {
		List<CoinItem> data = new ArrayList<>();
		try {
			// Ganancia al día
			CoinItem item = new CoinItem();
			String time = MessageUtils.getMessage("calculator.porDia", sessionBean.getLang());
			BigDecimal coins24 = dto.getCoins_per_hour().multiply(Constantes.DAY_TIME_FACTOR);
			BigDecimal currency24 = dto.getDollars_per_hour().multiply(Constantes.DAY_TIME_FACTOR)
					.multiply(Constantes.DOLLAR_MULTIPLY_FACTOR);

			ExchangeTypes eType = getEType(exchangeType);
			if (!eType.equals(ExchangeTypes.USD)) {
				currency24 = exchangeService.changeCurrency(currency24, ExchangeTypes.USD, eType);
			}

			item.setTime(time);
			item.setCoin(coins24);
			item.setCurrency(currency24);
			data.add(item);

			// Ganancia a la semana
			item = new CoinItem();
			time = MessageUtils.getMessage("calculator.porSemana", sessionBean.getLang());
			BigDecimal coinsSem = coins24.multiply(Constantes.WEEK_TIME_FACTOR);
			BigDecimal currencySem = currency24.multiply(Constantes.WEEK_TIME_FACTOR);
			item.setTime(time);
			item.setCoin(coinsSem);
			item.setCurrency(currencySem);
			data.add(item);

			// Ganancia al mes
			item = new CoinItem();
			time = MessageUtils.getMessage("calculator.porMes", sessionBean.getLang());
			BigDecimal coinsMes = coins24.multiply(Constantes.AVERAGE_MONTH_TIME_FACTOR);
			BigDecimal currencyMes = currency24.multiply(Constantes.AVERAGE_MONTH_TIME_FACTOR);
			item.setTime(time);
			item.setCoin(coinsMes);
			item.setCurrency(currencyMes);
			data.add(item);

			return data;
		}
		catch (ServiceException e) {
			LOGGER.log(LogLevels.ERROR, e);
			addErrorMessage(e);
		}
		return data;
	}

	/**
	 * @param resultados2
	 * @return
	 */
	private List<CoinItem> buildNextFromResults(BitcoinCalculatorDTO dto) {
		List<CoinItem> data = new ArrayList<>();
		try {
			// Ganancia al día
			CoinItem item = new CoinItem();
			String time = MessageUtils.getMessage("calculator.porDia", sessionBean.getLang());
			BigDecimal coins24 = dto.getCoins_per_hour_after_retarget().multiply(Constantes.DAY_TIME_FACTOR);
			BigDecimal currency24 = dto.getDollars_per_hour_after_retarget().multiply(Constantes.DAY_TIME_FACTOR)
					.multiply(Constantes.DOLLAR_MULTIPLY_FACTOR);
			
			ExchangeTypes eType = getEType(exchangeType);
			if (!eType.equals(ExchangeTypes.USD)) {
				currency24 = exchangeService.changeCurrency(currency24, ExchangeTypes.USD, eType);
			}
			
			item.setTime(time);
			item.setCoin(coins24);
			item.setCurrency(currency24);
			data.add(item);

			// Ganancia a la semana
			item = new CoinItem();
			time = MessageUtils.getMessage("calculator.porSemana", sessionBean.getLang());
			BigDecimal coinsSem = coins24.multiply(Constantes.WEEK_TIME_FACTOR);
			BigDecimal currencySem = currency24.multiply(Constantes.WEEK_TIME_FACTOR);
			item.setTime(time);
			item.setCoin(coinsSem);
			item.setCurrency(currencySem);
			data.add(item);

			// Ganancia al mes
			item = new CoinItem();
			time = MessageUtils.getMessage("calculator.porMes", sessionBean.getLang());
			BigDecimal coinsMes = coins24.multiply(Constantes.AVERAGE_MONTH_TIME_FACTOR);
			BigDecimal currencyMes = currency24.multiply(Constantes.AVERAGE_MONTH_TIME_FACTOR);
			item.setTime(time);
			item.setCoin(coinsMes);
			item.setCurrency(currencyMes);
			data.add(item);
		} catch (ServiceException e) {
			LOGGER.log(LogLevels.ERROR, e);
			addErrorMessage(e);
		}
		return data;
	}

	/**
	 * 
	 */
	private void initVars() {
		try {
			hashRateMultiply = HashRates.GH.name();
			hashRateAmount = 0.0;
			hashRate = 0L;
			lastType = ExchangeTypes.USD;
			exchangeType = lastType.name();
			exchangeAmount = calculatorService.getCurrentExchange();
			exchangeRateSource = calculatorService.getCurrentExchangeRateSource();
			exchange = BigDecimal.ZERO;
			traders = calculatorService.getTraders();
			if (thisDifficulty != null) {
				thisDifficulty.clear();
			}
			if (nextDifficulty != null) {
				nextDifficulty.clear();
			}
		}
		catch (ServiceException e) {
			String message = MessageUtils.getMessage("calculator.noTraders", sessionBean.getLang());
			addMessage(FacesMessage.SEVERITY_ERROR, message);
		}
	}

	/**
	 * @return the hashRateMultiply
	 */
	public String getHashRateMultiply() {
		return hashRateMultiply;
	}

	/**
	 * @param hashRateMultiply the hashRateMultiply to set
	 */
	public void setHashRateMultiply(String hashRateMultiply) {
		this.hashRateMultiply = hashRateMultiply;
	}

	/**
	 * @return the hashRateAmount
	 */
	public Double getHashRateAmount() {
		return hashRateAmount;
	}

	/**
	 * @param hashRateAmount the hashRateAmount to set
	 */
	public void setHashRateAmount(Double hashRateAmount) {
		this.hashRateAmount = hashRateAmount;
	}

	/**
	 * @return the hashRate
	 */
	public Long getHashRate() {
		return hashRate;
	}

	/**
	 * @return the exchangeType
	 */
	public String getExchangeType() {
		return exchangeType;
	}

	/**
	 * @param exchangeType the exchangeType to set
	 */
	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
	}

	/**
	 * @return the exchangeAmount
	 */
	public BigDecimal getExchangeAmount() {
		return exchangeAmount;
	}

	/**
	 * @param exchangeAmount the exchangeAmount to set
	 */
	public void setExchangeAmount(BigDecimal exchangeAmount) {
		this.exchangeAmount = exchangeAmount;
	}

	/**
	 * @return the exchange
	 */
	public BigDecimal getExchange() {
		return exchange;
	}

	/**
	 * @return
	 */
	public String getExchangeRateSource() {
		return exchangeRateSource;
	}

	/**
	 * @param exchangeRateSource
	 */
	public void setExchangeRateSource(String exchangeRateSource) {
		this.exchangeRateSource = exchangeRateSource;
	}

	/**
	 * @return
	 */
	public List<Trader> getTraders() {
		return traders;
	}

	/**
	 * @param traderNames
	 */
	public void setTraders(List<Trader> traders) {
		this.traders = traders;
	}

	/**
	 * @return the thisDifficulty
	 */
	public List<CoinItem> getThisDifficulty() {
		return thisDifficulty;
	}

	/**
	 * @param thisDifficulty the thisDifficulty to set
	 */
	public void setThisDifficulty(List<CoinItem> thisDifficulty) {
		this.thisDifficulty = thisDifficulty;
	}

	/**
	 * @return the nextDifficulty
	 */
	public List<CoinItem> getNextDifficulty() {
		return nextDifficulty;
	}

	/**
	 * @param nextDifficulty the nextDifficulty to set
	 */
	public void setNextDifficulty(List<CoinItem> nextDifficulty) {
		this.nextDifficulty = nextDifficulty;
	}

	public String getCharset() {
		return Constantes.ENCODING_EXPORTS;
	}
}

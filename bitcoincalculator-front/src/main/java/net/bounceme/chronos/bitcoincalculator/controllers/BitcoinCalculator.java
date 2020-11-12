package net.bounceme.chronos.bitcoincalculator.controllers;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.event.FlowEvent;
import org.springframework.beans.factory.annotation.Autowired;

import net.bounceme.chronos.bitcoincalculator.common.ConstantesBitcoinCalculator;
import net.bounceme.chronos.bitcoincalculator.common.ConstantesBitcoinCalculator.ExchangeTypes;
import net.bounceme.chronos.bitcoincalculator.common.ConstantesBitcoinCalculator.HashRates;
import net.bounceme.chronos.bitcoincalculator.common.ConstantesBitcoinCalculator.Traders;
import net.bounceme.chronos.bitcoincalculator.dto.BitcoinCalculatorDTO;
import net.bounceme.chronos.bitcoincalculator.exceptions.ServiceException;
import net.bounceme.chronos.bitcoincalculator.exceptions.TraderException;
import net.bounceme.chronos.bitcoincalculator.messages.MessageProperties;
import net.bounceme.chronos.bitcoincalculator.model.CoinItem;
import net.bounceme.chronos.bitcoincalculator.services.CalculatorService;
import net.bounceme.chronos.bitcoincalculator.services.ExchangeService;
import net.bounceme.chronos.bitcoincalculator.services.trading.Trader;
import net.bounceme.chronos.utils.jsf.controller.BaseBean;

@ManagedBean(name = BitcoinCalculator.NAME)
@ViewScoped
public class BitcoinCalculator extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 354285024366651540L;

	public static final String NAME = "bitcoinCalculator";

	@ManagedProperty(value = "#{sessionBean}")
	private SessionBean sessionBean;

	@Autowired
	private transient CalculatorService calculatorService;

	@Autowired
	private transient ExchangeService exchangeService;

	@Autowired
	private transient MessageProperties messageProperties;

	private Double hashRateAmount;

	private String hashRateMultiply;

	private Long hashRate;

	private String exchangeType;

	private BigDecimal exchangeAmount;

	private BigDecimal exchangeBase;

	private BigDecimal exchange;

	private String exchangeRateSource;

	private transient List<Trader> traders;

	private List<CoinItem> thisDifficulty;

	private List<CoinItem> nextDifficulty;

	private ExchangeTypes lastType;

	private ExchangeTypes baseType;

	/**
	 * 
	 */
	@PostConstruct
	public void initVars() {
		try {
			hashRateMultiply = HashRates.GH.name();
			hashRateAmount = 14.0;
			hashRate = 0L;
			baseType = ExchangeTypes.EUR;
			lastType = baseType;
			exchangeType = lastType.name();
			exchange = BigDecimal.ZERO;

			BitcoinCalculatorDTO data = calculatorService.getData();

			if (!Objects.isNull(data)) {
				BigDecimal amount = new BigDecimal(data.getExchange_rate());
				exchangeBase = exchangeService.initCurrency(amount, ExchangeTypes.USD);
				exchangeAmount = exchangeBase;
				exchangeRateSource = data.getExchange_rate_source();
				traders = calculatorService.getTraders();
			}

			if (thisDifficulty != null) {
				thisDifficulty.clear();
			}
			if (nextDifficulty != null) {
				nextDifficulty.clear();
			}
		} catch (ServiceException e) {
			String message = messageProperties.getString("calculator.noTraders", sessionBean.getLang());
			addMessage(FacesMessage.SEVERITY_ERROR, message);
		}
	}

	public void calculate(ActionEvent actionEvent) {
		try {
			if (validateHash() && validateExchange()) {
				calculateHashRate();
				BitcoinCalculatorDTO resultados = calculatorService.getData(hashRate, exchangeAmount);
				thisDifficulty = buildThisFromResults(resultados);
				nextDifficulty = buildNextFromResults(resultados);
			}
		} catch (ServiceException e) {
			addErrorMessage(e);
		}
	}

	public void reset(ActionEvent actionEvent) {
		try {
			initVars();
			sessionBean.setCurrentTrader(calculatorService.getTrader(Traders.Default));
		} catch (ServiceException e) {
			addErrorMessage(e);
		}
	}

	public void onChangeHashRate() {
		if (validateHash()) {
			calculateHashRate();
			String message = messageProperties.getString("calculator.tasaSeleccionada", sessionBean.getLang(),
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
		} catch (ServiceException e) {
			addErrorMessage("Error al cambiar de divisa");
		}
	}

	/**
	 * Cuando se elige otro mercado del cuadro de diálogo
	 */
	public void onSelectTrader() {
		try {
			exchangeAmount = sessionBean.getCurrentTrader().getExchange();
			setNewExchange();
		} catch (ServiceException | TraderException e) {
			addErrorMessage(e);
		}
	}

	private void setNewExchange() throws ServiceException {
		ExchangeTypes eType = getEType(exchangeType);
		exchangeAmount = getExchangeAmount(exchangeAmount, eType).setScale(2, BigDecimal.ROUND_HALF_UP);
		String message = messageProperties.getString("calculator.tasaCambioSeleccionado", sessionBean.getLang(),
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
			value = (eType.equals(baseType)) ? exchangeBase : exchangeService.changeCurrency(source, eType);
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
			String message = messageProperties.getString("calculator.medidaHashVacia", sessionBean.getLang());
			addMessage(FacesMessage.SEVERITY_WARN, message);
			return Boolean.FALSE;
		}

		if (hashRateAmount == 0L) {
			String message = messageProperties.getString("calculator.tasaHashVacia", sessionBean.getLang());
			addMessage(FacesMessage.SEVERITY_WARN, message);
			return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}

	private Boolean validateExchange() {
		if (StringUtils.isEmpty(exchangeType)) {
			String message = messageProperties.getString("calculator.tipoCambioVacio", sessionBean.getLang());
			addMessage(FacesMessage.SEVERITY_WARN, message);
			return Boolean.FALSE;
		}

		return Boolean.TRUE;
	}

	private List<CoinItem> buildThisFromResults(BitcoinCalculatorDTO dto) {
		return buildEarnings(dto.getCoins_per_hour(), dto.getDollars_per_hour());
	}

	private List<CoinItem> buildNextFromResults(BitcoinCalculatorDTO dto) {
		return buildEarnings(dto.getCoins_per_hour_after_retarget(), dto.getDollars_per_hour_after_retarget());
	}

	/**
	 * @param resultados2
	 * @return
	 */
	private List<CoinItem> buildEarnings(BigDecimal coins, BigDecimal dollars) {
		List<CoinItem> data = new ArrayList<>();
		try {
			if (!Objects.isNull(coins)) {
				// Ganancia al día
				CoinItem item = new CoinItem();
				String time = messageProperties.getString("calculator.porDia", sessionBean.getLang());
				BigDecimal coins24 = coins.multiply(ConstantesBitcoinCalculator.DAY_TIME_FACTOR);
				BigDecimal currency24 = dollars.multiply(ConstantesBitcoinCalculator.DAY_TIME_FACTOR)
						.multiply(ConstantesBitcoinCalculator.DOLLAR_MULTIPLY_FACTOR);

				ExchangeTypes eType = getEType(exchangeType);
				if (!eType.equals(ExchangeTypes.USD)) {
					currency24 = exchangeService.changeCurrency(currency24, eType);
				}

				item.setTime(time);
				item.setCoin(coins24);
				item.setCurrency(currency24);
				data.add(item);

				// Ganancia a la semana
				item = new CoinItem();
				time = messageProperties.getString("calculator.porSemana", sessionBean.getLang());
				BigDecimal coinsSem = coins24.multiply(ConstantesBitcoinCalculator.WEEK_TIME_FACTOR);
				BigDecimal currencySem = currency24.multiply(ConstantesBitcoinCalculator.WEEK_TIME_FACTOR);
				item.setTime(time);
				item.setCoin(coinsSem);
				item.setCurrency(currencySem);
				data.add(item);

				// Ganancia al mes
				item = new CoinItem();
				time = messageProperties.getString("calculator.porMes", sessionBean.getLang());
				BigDecimal coinsMes = coins24.multiply(ConstantesBitcoinCalculator.AVERAGE_MONTH_TIME_FACTOR);
				BigDecimal currencyMes = currency24.multiply(ConstantesBitcoinCalculator.AVERAGE_MONTH_TIME_FACTOR);
				item.setTime(time);
				item.setCoin(coinsMes);
				item.setCurrency(currencyMes);
				data.add(item);
			}
			return data;
		} catch (ServiceException e) {
			addErrorMessage(e);
		}
		return data;
	}

	public String onFlowProcess(FlowEvent event) {
		return event.getNewStep();
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
		return ConstantesBitcoinCalculator.ENCODING_EXPORTS;
	}

	/**
	 * @return the sessionBean
	 */
	public SessionBean getSessionBean() {
		return sessionBean;
	}

	/**
	 * @param sessionBean the sessionBean to set
	 */
	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}
}

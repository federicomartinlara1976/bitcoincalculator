package net.bounceme.chronos.bitcoincalculator.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j;
import net.bounceme.chronos.bitcoincalculator.common.ConstantesBitcoinCalculator.ExchangeTypes;
import net.bounceme.chronos.bitcoincalculator.config.AppConfig;
import net.bounceme.chronos.bitcoincalculator.dto.ExchangeDTO;
import net.bounceme.chronos.bitcoincalculator.exceptions.ServiceException;
import net.bounceme.chronos.bitcoincalculator.utils.LogWrapper;
import net.bounceme.chronos.utils.exceptions.AssembleException;
import net.bounceme.chronos.utils.mapping.JacksonConverter;
import net.bounceme.chronos.utils.net.exceptions.JSONClientException;
import net.bounceme.chronos.utils.net.json.JSONClient;

@Service
@Scope("session")
@Log4j
public class ExchangeService {
	
	@Value("${BitcoinCalculator.exchangeCurrency}")
	private String exchangeCurrencyUrl;
	
	@Value("${BitcoinCalculator.accessKey}")
	private String accessKey;
	
	@Autowired
	@Qualifier(AppConfig.EXCHANGE_CONVERTER)
	private JacksonConverter<ExchangeDTO> exchangeConverter;
	
	public BigDecimal changeCurrency(BigDecimal sCurrency, ExchangeTypes symbol)
			throws ServiceException {
		try {
			ExchangeDTO exchangeDTO = queryExchangeRates();
			BigDecimal value = BigDecimal.valueOf(exchangeDTO.getRates().get(symbol.name()));
			
			return sCurrency.divide(value, 2, RoundingMode.HALF_UP);
		} catch (JSONClientException | AssembleException | ArithmeticException e) {
			LogWrapper.error(log, "ERROR", e);
			throw new ServiceException(e);
		}
	}

	private ExchangeDTO queryExchangeRates() throws JSONClientException, AssembleException {
		LogWrapper.debug(log, "Loading currency rates...");
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("access_key", accessKey);
		JSONClient client = new JSONClient();
		client.setUrl(exchangeCurrencyUrl);
		client.setParameters(parameters);
		String result = client.get();
		
		return exchangeConverter.reverseAssemble(result);
	}

	public BigDecimal initCurrency(BigDecimal sCurrency, ExchangeTypes symbol) throws ServiceException {
		try {
			ExchangeDTO exchangeDTO = queryExchangeRates();
			BigDecimal value = BigDecimal.valueOf(exchangeDTO.getRates().get(symbol.name()));
			
			return sCurrency.multiply(value);
		} catch (JSONClientException | AssembleException e) {
			LogWrapper.error(log, "ERROR", e);
			throw new ServiceException(e);
		}
	}

}

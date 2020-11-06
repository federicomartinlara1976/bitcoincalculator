package net.bounceme.chronos.bitcoincalculator.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.bounceme.chronos.bitcoincalculator.common.ConstantesBitcoinCalculator.ExchangeTypes;
import net.bounceme.chronos.bitcoincalculator.config.AppConfig;
import net.bounceme.chronos.bitcoincalculator.dto.ExchangeDTO;
import net.bounceme.chronos.bitcoincalculator.exceptions.ServiceException;
import net.bounceme.chronos.bitcoincalculator.services.ExchangeService;
import net.bounceme.chronos.utils.exceptions.AssembleException;
import net.bounceme.chronos.utils.mapping.JacksonConverter;
import net.bounceme.chronos.utils.net.exceptions.JSONClientException;
import net.bounceme.chronos.utils.net.json.JSONClient;

@Service(ExchangeService.NAME)
@Scope("session")
public class ExchangeServiceImpl implements ExchangeService {
	
	private static Logger log = Logger.getLogger(ExchangeServiceImpl.class.getName());
	
	@Value("${BitcoinCalculator.exchangeCurrency}")
	private String exchangeCurrencyUrl;
	
	@Value("${BitcoinCalculator.accessKey}")
	private String accessKey;
	
	@Autowired
	@Qualifier(AppConfig.EXCHANGE_CONVERTER)
	private JacksonConverter<ExchangeDTO> exchangeConverter;
	
	@Override
	@Cacheable("bitcoincalculator")
	public BigDecimal changeCurrency(BigDecimal sCurrency, ExchangeTypes symbol)
			throws ServiceException {
		try {
			ExchangeDTO exchangeDTO = queryExchangeRates();
			BigDecimal value = BigDecimal.valueOf(exchangeDTO.getRates().get(symbol.name()));
			
			return sCurrency.divide(value, 2, RoundingMode.HALF_UP);
		} catch (JSONClientException | AssembleException | ArithmeticException e) {
			log.error(e.getMessage());
			throw new ServiceException(e);
		}
	}

	private ExchangeDTO queryExchangeRates() throws JSONClientException, AssembleException {
		log.debug("Loading currency rates...");
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("access_key", accessKey);
		JSONClient client = new JSONClient();
		client.setUrl(exchangeCurrencyUrl);
		client.setParameters(parameters);
		String result = client.get();
		
		return exchangeConverter.reverseAssemble(result);
	}

	@Override
	public BigDecimal initCurrency(BigDecimal sCurrency, ExchangeTypes symbol) throws ServiceException {
		try {
			ExchangeDTO exchangeDTO = queryExchangeRates();
			BigDecimal value = BigDecimal.valueOf(exchangeDTO.getRates().get(symbol.name()));
			
			return sCurrency.multiply(value);
		} catch (JSONClientException | AssembleException e) {
			log.error(e.getMessage());
			throw new ServiceException(e);
		}
	}

}

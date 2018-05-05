package net.bounceme.chronos.bitcoincalculator.services.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	
	@Autowired
	@Qualifier(AppConfig.EXCHANGE_CONVERTER)
	private JacksonConverter<ExchangeDTO> exchangeConverter;
	
	@Override
	public BigDecimal changeCurrency(BigDecimal sCurrency, ExchangeTypes base, ExchangeTypes symbol)
			throws ServiceException {
		try {
			// Types are equal - No change
			if (base.equals(symbol)) {
				return sCurrency;
			}
			
			Map<String, String> parameters = new HashMap<>();
			parameters.put("access_key", "83c697dc432b65773d1d83c98ea09e77");
			parameters.put("base", base.name());
			parameters.put("symbols", symbol.name());
			JSONClient client = new JSONClient();
			client.setUrl("http://data.fixer.io/api/latest");
			client.setParameters(parameters);
			String result = client.get();
			
			ExchangeDTO exchangeDTO = exchangeConverter.reverseAssemble(result);
			BigDecimal value = BigDecimal.valueOf(exchangeDTO.getRates().get(symbol.name()));
			
			return sCurrency.multiply(value);
		} catch (JSONClientException | AssembleException e) {
			log.error(e.getMessage());
			throw new ServiceException(e);
		}
	}

}

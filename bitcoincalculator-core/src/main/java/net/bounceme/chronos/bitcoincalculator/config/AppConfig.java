package net.bounceme.chronos.bitcoincalculator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import net.bounceme.chronos.bitcoincalculator.common.Constantes;
import net.bounceme.chronos.bitcoincalculator.dto.BitcoinCalculatorDTO;
import net.bounceme.chronos.bitcoincalculator.dto.ExchangeDTO;
import net.bounceme.chronos.utils.mapping.JacksonConverter;
import net.bounceme.chronos.utils.net.json.JSONPool;

@Configuration
@PropertySource(value={"classpath:application.properties"})
public class AppConfig {

	public static final String BITCOIN_CONVERTER = "bitcoinConverter";
	
	public static final String EXCHANGE_CONVERTER = "exchangeConverter";
	
	@SuppressWarnings({ Constantes.UNCHECKED, Constantes.RAWTYPES })
	@Bean(name = BITCOIN_CONVERTER)
	public JacksonConverter<BitcoinCalculatorDTO> bitcoinConverter() {
		return new JacksonConverter(BitcoinCalculatorDTO.class);
	}
	
	@SuppressWarnings({ Constantes.UNCHECKED, Constantes.RAWTYPES })
	@Bean(name = EXCHANGE_CONVERTER)
	public JacksonConverter<ExchangeDTO> exchangeConverter() {
		return new JacksonConverter(ExchangeDTO.class);
	}
	
	//this bean needed to resolve ${property.name} syntax
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
	
    @Bean
	public JSONPool jsonPool() {
		return new JSONPool();
	}
}

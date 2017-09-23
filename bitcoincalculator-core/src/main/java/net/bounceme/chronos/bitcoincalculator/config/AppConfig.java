package net.bounceme.chronos.bitcoincalculator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import net.bounceme.chronos.bitcoincalculator.dto.BitcoinCalculatorDTO;
import net.bounceme.chronos.bitcoincalculator.dto.ExchangeDTO;
import net.bounceme.chronos.bitcoincalculator.messages.MessageProperties;
import net.bounceme.chronos.bitcoincalculator.messages.PubliProperties;
import net.bounceme.chronos.utils.cache.config.RemoteCacheConfiguration;
import net.bounceme.chronos.utils.mapping.JacksonConverter;
import net.bounceme.chronos.utils.net.json.JSONPool;

@Configuration
@Import(RemoteCacheConfiguration.class)
@PropertySource(value={"classpath:application.properties"})
public class AppConfig {
	public static final String BITCOIN_CONVERTER = "bitcoinConverter";
	
	public static final String EXCHANGE_CONVERTER = "exchangeConverter";
	
	public static final String CACHE = "bitcoin-calculator";
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean(name = BITCOIN_CONVERTER)
	public JacksonConverter<BitcoinCalculatorDTO> bitcoinConverter() {
		return new JacksonConverter(BitcoinCalculatorDTO.class);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean(name = EXCHANGE_CONVERTER)
	public JacksonConverter<ExchangeDTO> exchangeConverter() {
		return new JacksonConverter(ExchangeDTO.class);
	}
	
	@Bean
	public MessageProperties messageProperties() {
		return new MessageProperties();
	}
	
	@Bean
	public PubliProperties publiProperties() {
		return new PubliProperties();
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

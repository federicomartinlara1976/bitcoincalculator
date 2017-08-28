package net.bounceme.chronos.bitcoincalculator.config;

import java.util.Date;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import net.bounceme.chronos.bitcoincalculator.common.Constantes;
import net.bounceme.chronos.bitcoincalculator.dto.BitcoinCalculatorDTO;
import net.bounceme.chronos.bitcoincalculator.dto.ExchangeDTO;
import net.bounceme.chronos.utils.cache.config.RemoteCacheConfiguration;
import net.bounceme.chronos.utils.log.Log;
import net.bounceme.chronos.utils.log.LogFactory;
import net.bounceme.chronos.utils.log.Log.LogLevels;
import net.bounceme.chronos.utils.mapping.JacksonConverter;
import net.bounceme.chronos.utils.net.json.JSONPool;

@Configuration
@Import(RemoteCacheConfiguration.class)
@PropertySource(value={"classpath:application.properties"})
@EnableScheduling
public class AppConfig {
	private static final Log LOGGER = LogFactory.getInstance().getLog(AppConfig.class);
	
	public static final String BITCOIN_CONVERTER = "bitcoinConverter";
	
	public static final String EXCHANGE_CONVERTER = "exchangeConverter";
	
	public static final String CACHE = "bitcoin-calculator";
	
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
    
    // Evict cache every 10 minutes
    @CacheEvict(allEntries = true, value = CACHE)
    @Scheduled(fixedDelay = 10 * 60 * 1000 ,  initialDelay = 500)
    public void reportCacheEvict() {
        LOGGER.log(LogLevels.DEBUG, "Flush Cache " + new Date());
    }
}

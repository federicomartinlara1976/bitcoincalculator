package net.bounceme.chronos.bitcoincalculator.common;

import java.math.BigDecimal;

import net.bounceme.chronos.bitcoincalculator.services.trading.BitStampTrader;
import net.bounceme.chronos.bitcoincalculator.services.trading.DefaultTrader;
import net.bounceme.chronos.bitcoincalculator.services.trading.MtGoxTrader;
import net.bounceme.chronos.bitcoincalculator.services.trading.Plus500Trader;

public class ConstantesBitcoinCalculator extends net.bounceme.chronos.utils.common.Constantes {

	public enum ExchangeTypes {
		USD, EUR
	}
	
	public enum HashRates {
		MH(1000L), GH(1000000L), TH(1000000000L);

		private Long multiply;

		private HashRates(Long multiply) {
			this.multiply = multiply;
		}

		public Long getMultiply() {
			return multiply;
		}
	}
	
	public static enum Traders {
		Default(DefaultTrader.class), BitStamp(BitStampTrader.class), MtGox(MtGoxTrader.class),Plus500(Plus500Trader.class);
		
		private Class<?> traderClass;
		
		private Traders(Class<?> traderClass) {
			this.traderClass = traderClass;
		}
		
		public Class<?> getTraderClass() {
			return traderClass;
		}
	}
	
	public static final String INITIAL_HASHRATE = "1";
	
	public static final BigDecimal DAY_TIME_FACTOR = BigDecimal.valueOf(24);
	
	public static final BigDecimal WEEK_TIME_FACTOR = BigDecimal.valueOf(7);
	
	public static final BigDecimal AVERAGE_MONTH_TIME_FACTOR = BigDecimal.valueOf(30.4);
	
	public static final BigDecimal DOLLAR_MULTIPLY_FACTOR = BigDecimal.valueOf(1000);
	
	public static final String ENCODING_EXPORTS = "ISO-8859-1";
}

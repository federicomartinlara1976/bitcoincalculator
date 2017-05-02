package net.bounceme.chronos.bitcoincalculator.services.trading;

import java.math.BigDecimal;

import net.bounceme.chronos.bitcoincalculator.exceptions.TraderException;

public class MtGoxTrader extends TraderBase {
	private static final String NAME = "MtGox"; 
	private static final String URL = "https://www.mtgox.com";
	private static final String ICON = "http://www.mtgox.com/img/mtgox_logo.png";

	public MtGoxTrader() {
		super(URL, NAME, ICON);
	}

	@Override
	public BigDecimal getExchange() throws TraderException {
		return BigDecimal.ZERO;
	}

	@Override
	public Boolean getDisabled() {
		return Boolean.TRUE;
	}
}

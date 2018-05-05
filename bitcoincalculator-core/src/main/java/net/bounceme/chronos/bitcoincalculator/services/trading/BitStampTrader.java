package net.bounceme.chronos.bitcoincalculator.services.trading;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ResourceBundle;

import org.jboss.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.bounceme.chronos.bitcoincalculator.common.ConstantesBitcoinCalculator.ExchangeTypes;
import net.bounceme.chronos.bitcoincalculator.exceptions.TraderException;
import net.bounceme.chronos.utils.log.Log.LogLevels;

public class BitStampTrader extends TraderBase  {
	private static final String NAME = "BitStamp";

	private static Logger log = Logger.getLogger(BitStampTrader.class.getName());
	
	private static final String BUNDLE_NAME = "net.bounceme.chronos.bitcoincalculator.traders.BitStamp"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	public BitStampTrader() {
		super(RESOURCE_BUNDLE.getString("trader.exchangeSource"), NAME, RESOURCE_BUNDLE.getString("trader.icon"));
	}

	public BigDecimal getExchange() throws TraderException {
		try {
			Document doc;
			doc = Jsoup.connect(getUrl())
					.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
					.referrer("http://www.google.com").get();
			Elements select = doc.select("span#live_" + ExchangeTypes.USD.name().toLowerCase() + "_price");
			for (Element option : select) {
				return new BigDecimal(option.text());
			}
			return BigDecimal.ZERO;
		}
		catch (IOException e) {
			log.error(LogLevels.ERROR.name(), e);
			throw new TraderException(e);
		}
	}

	@Override
	public Boolean getDisabled() {
		return Boolean.FALSE;
	}
}

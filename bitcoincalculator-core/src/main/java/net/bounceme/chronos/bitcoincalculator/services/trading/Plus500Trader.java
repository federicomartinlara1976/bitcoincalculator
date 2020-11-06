package net.bounceme.chronos.bitcoincalculator.services.trading;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ResourceBundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.extern.log4j.Log4j;
import net.bounceme.chronos.bitcoincalculator.exceptions.TraderException;
import net.bounceme.chronos.bitcoincalculator.utils.LogWrapper;

@Log4j
public class Plus500Trader extends TraderBase {

	private static final String NAME = "Plus500";

	private static final String BUNDLE_NAME = "net.bounceme.chronos.bitcoincalculator.traders.Plus500"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	public Plus500Trader() {
		super(RESOURCE_BUNDLE.getString("trader.exchangeSource"), NAME, RESOURCE_BUNDLE.getString("trader.icon"));
	}

	@Override
	public BigDecimal getExchange() throws TraderException {
		try {
			return getCurrencyUSD();
		} catch (IOException e) {
			LogWrapper.error(log, "ERROR", e);
			throw new TraderException(e);
		}
	}

	/**
	 * @return
	 * @throws IOException
	 */
	private BigDecimal getCurrencyUSD() throws IOException {
		Document doc;
		BigDecimal value = BigDecimal.ZERO;
		doc = Jsoup.connect(getUrl()).header("Accept-Encoding", "gzip, deflate")
				.userAgent(
						"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36")
				.maxBodySize(0).timeout(600000).get();
		Elements select = doc.select("span#ctl00_ContentPlaceMain1_LabelSellPrice");
		if (!select.isEmpty()) {
			for (Element option : select) {
				value = new BigDecimal(option.text());
			}
		}
		else {
			LogWrapper.warn(log, "Error: no se ha podido obtener información de " + getName());
		}

		return value;
	}

	@Override
	public Boolean getDisabled() {
		return Boolean.FALSE;
	}

}

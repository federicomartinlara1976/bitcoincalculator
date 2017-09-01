package net.bounceme.chronos.bitcoincalculator.utils;

/**
 * @author frederik
 *
 */
public class PubliProperties extends BaseProperties {

	private static PubliProperties instance;

	private PubliProperties() {
		bundleName = "net.bounceme.chronos.bitcoincalculator.PubliBundle";
	}

	public static PubliProperties getInstance() {
		if (instance == null) {
			instance = new PubliProperties();
		}
		return instance;
	}
}

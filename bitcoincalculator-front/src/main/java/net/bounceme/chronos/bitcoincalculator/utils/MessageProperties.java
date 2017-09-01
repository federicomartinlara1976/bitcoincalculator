package net.bounceme.chronos.bitcoincalculator.utils;

/**
 * @author frederik
 *
 */
public class MessageProperties extends BaseProperties {

	private static MessageProperties instance;
	 

	private MessageProperties() {
		bundleName = "net.bounceme.chronos.bitcoincalculator.MessageBundle";
	}

	public static MessageProperties getInstance() {
		if (instance == null) {
			instance = new MessageProperties();
		}
		return instance;
	}
}

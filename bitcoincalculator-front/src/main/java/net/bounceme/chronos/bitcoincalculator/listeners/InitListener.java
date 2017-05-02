package net.bounceme.chronos.bitcoincalculator.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.bounceme.chronos.utils.log.Log;
import net.bounceme.chronos.utils.log.LogFactory;

/**
 * @author frederik
 *
 */
public class InitListener implements ServletContextListener {

	private static final Log LOGGER = LogFactory.getInstance().getLog(InitListener.class);
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		
	}
}

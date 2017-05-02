package net.bounceme.chronos.bitcoincalculator.services.impl;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.bounceme.chronos.bitcoincalculator.exceptions.ServiceException;
import net.bounceme.chronos.bitcoincalculator.services.GestorConexiones;
import net.bounceme.chronos.bitcoincalculator.services.PropertiesService;
import net.bounceme.chronos.utils.exceptions.JSONClientException;
import net.bounceme.chronos.utils.json.JSONClient;
import net.bounceme.chronos.utils.json.JSONPoolSingleton;
import net.bounceme.chronos.utils.log.Log;
import net.bounceme.chronos.utils.log.Log.LogLevels;
import net.bounceme.chronos.utils.log.LogFactory;

@Service(GestorConexiones.NAME)
@Scope("singleton")
public class GestorConexionesImpl implements GestorConexiones {
	private static final Log LOGGER = LogFactory.getInstance().getLog(GestorConexionesImpl.class);

	@Autowired
	@Qualifier(PropertiesService.NAME)
	private PropertiesService propertiesService;

	private JSONPoolSingleton pool;

	@PostConstruct
	public void initializePool() {
		try {
			String url = propertiesService.getProperty("BitcoinCalculator.api");

			pool = JSONPoolSingleton.getInstance();
			pool.setLimit(5);
			pool.initialize();
			pool.broadcastURL(url);

		}
		catch (JSONClientException e) {
			pool.setInitialized(Boolean.FALSE);
			LOGGER.log(LogLevels.ERROR, "Pool not initialized!");
		}
	}

	@Override
	public String getData(Map<String, String> parameters) throws ServiceException {
		JSONClient conn = pool.get();
		try {
			conn.setParameters(parameters);
			String result = conn.get();
			
			return result;
		} catch (JSONClientException e) {
			LOGGER.log(LogLevels.ERROR, e);
			throw new ServiceException(e);
		} finally {
			pool.put(conn);
		}
	}

}

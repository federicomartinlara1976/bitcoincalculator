package net.bounceme.chronos.bitcoincalculator.services.impl;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import net.bounceme.chronos.bitcoincalculator.exceptions.ServiceException;
import net.bounceme.chronos.bitcoincalculator.services.GestorConexiones;
import net.bounceme.chronos.utils.net.exceptions.JSONClientException;
import net.bounceme.chronos.utils.net.json.JSONClient;
import net.bounceme.chronos.utils.net.json.JSONPool;

@Service(GestorConexiones.NAME)
@Scope("singleton")
public class GestorConexionesImpl implements GestorConexiones {
	private static Logger log = Logger.getLogger(GestorConexionesImpl.class.getName());
	
	@Value("${BitcoinCalculator.api}")
	private String url;

	@Autowired
	private JSONPool pool;

	@PostConstruct
	public void initializePool() {
		try {
			pool.setLimit(5);
			pool.initialize();
			pool.broadcastURL(url);

		}
		catch (JSONClientException e) {
			log.error("ERROR", e);
			pool.setInitialized(Boolean.FALSE);
		}
	}

	@Override
	public String getData(Map<String, Object> parameters) throws ServiceException {
		JSONClient conn = pool.get();
		try {
			conn.setParameters(parameters);
			return conn.get();
		} catch (JSONClientException e) {
			log.error("ERROR", e);
			throw new ServiceException(e);
		} finally {
			pool.put(conn);
		}
	}

}

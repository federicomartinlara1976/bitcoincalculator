package net.bounceme.chronos.bitcoincalculator.controllers;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import net.bounceme.chronos.bitcoincalculator.services.PropertiesService;
import net.bounceme.chronos.utils.jsf.controller.BaseBean;
import net.bounceme.chronos.utils.log.Log;
import net.bounceme.chronos.utils.log.Log.LogLevels;
import net.bounceme.chronos.utils.log.LogFactory;

/**
 * @author frederik
 *
 */
@ManagedBean(name=AppBean.NAME)
@ApplicationScoped
public class AppBean extends BaseBean {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -195766865081737425L;

	public static final String NAME = "appBean";

	private static final Log LOGGER = LogFactory.getInstance().getLog(AppBean.class);

	@Autowired
	@Qualifier(PropertiesService.NAME)
	private PropertiesService propertiesService;
	
	private String name;
	private String title;
	private String context;
	private ExternalContext externalContext;
	
	
	/* (non-Javadoc)
	 * @see net.bounceme.chronos.utils.jsf.controller.BaseBean#postContruct()
	 */
	@PostConstruct
	public void postContruct() {
		try {
			name = propertiesService.getProperty("BitcoinCalculator.name");
			title = propertiesService.getProperty("BitcoinCalculator.title");
			
			HttpServletRequest request = getJsfHelper().getRequest();
			context = request.getContextPath();
			externalContext = FacesContext.getCurrentInstance().getExternalContext();
		} catch (Exception e) {
			LOGGER.log(LogLevels.ERROR, e);
		}
	}
	
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * @return the externalContext
	 */
	public ExternalContext getExternalContext() {
		return externalContext;
	}

	/**
	 * @param externalContext the externalContext to set
	 */
	public void setExternalContext(ExternalContext externalContext) {
		this.externalContext = externalContext;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
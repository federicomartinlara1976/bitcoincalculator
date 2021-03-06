package net.bounceme.chronos.bitcoincalculator.controllers;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;

import net.bounceme.chronos.logger.Log;
import net.bounceme.chronos.logger.LogFactory;
import net.bounceme.chronos.utils.jsf.controller.BaseBean;

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

	private static final Log LOGGER = LogFactory.getInstance().getLogger(AppBean.class, "LOG4J");

	@Value("${BitcoinCalculator.name}")
	private String sname;
	
	@Value("${BitcoinCalculator.title}")
	private String title;
	private String context;
	private transient ExternalContext externalContext;
	
	
	/* (non-Javadoc)
	 * @see net.bounceme.chronos.utils.jsf.controller.BaseBean#postContruct()
	 */
	@PostConstruct
	public void postContruct() {
		try {
			HttpServletRequest request = getJsfHelper().getRequest();
			context = request.getContextPath();
			externalContext = FacesContext.getCurrentInstance().getExternalContext();
		} catch (Exception e) {
			LOGGER.error("ERROR", e);
		}
	}
	
	public String getName() {
        return sname;
    }

    public void setName(String name) {
        this.sname = name;
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
package net.bounceme.chronos.bitcoincalculator.controllers;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import net.bounceme.chronos.bitcoincalculator.common.Constantes.Traders;
import net.bounceme.chronos.bitcoincalculator.exceptions.ServiceException;
import net.bounceme.chronos.bitcoincalculator.messages.MessageProperties;
import net.bounceme.chronos.bitcoincalculator.messages.PubliProperties;
import net.bounceme.chronos.bitcoincalculator.services.CalculatorService;
import net.bounceme.chronos.bitcoincalculator.services.trading.Trader;
import net.bounceme.chronos.utils.jsf.controller.BaseBean;

@ManagedBean(name = SessionBean.NAME)
@SessionScoped
public class SessionBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4764455202310413427L;
	
	public static final String NAME = "sessionBean";

	@Autowired
	@Qualifier(CalculatorService.NAME)
	private transient CalculatorService calculatorService;
	
	@Autowired
	private transient PubliProperties publiProperties;
	
	@Autowired
	private transient MessageProperties messageProperties;

	private Locale lang;

	private Double difficultyFactor;

	private Double nextDifficultyFactor;

	private BigDecimal bcPerBlock;

	private transient Trader prevTrader;
	
	private transient Trader currentTrader;
	
	@PostConstruct
	public void init() {
		try {
			lang = FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
			difficultyFactor = calculatorService.getCurrentDifficultyFactor();
			nextDifficultyFactor = calculatorService.getNextDifficultyFactor();
			bcPerBlock = calculatorService.getCurrentBcPerBlock();
			currentTrader = calculatorService.getTrader(Traders.Default);
		}
		catch (ServiceException e) {
			String message = messageProperties.getString("calculator.noTraders", lang);
			addMessage(FacesMessage.SEVERITY_ERROR, message);
		}
	}

	public Double getDifficultyFactor() {
		return difficultyFactor;
	}

	public void setDifficultyFactor(Double difficultyFactor) {
		this.difficultyFactor = difficultyFactor;
	}

	public BigDecimal getBcPerBlock() {
		return bcPerBlock;
	}

	public void setBcPerBlock(BigDecimal bcPerBlock) {
		this.bcPerBlock = bcPerBlock;
	}

	public Double getNextDifficultyFactor() {
		return nextDifficultyFactor;
	}

	public void setNextDifficultyFactor(Double nextDifficultyFactor) {
		this.nextDifficultyFactor = nextDifficultyFactor;
	}

	/**
	 * @return
	 */
	public Trader getCurrentTrader() {
		return currentTrader;
	}

	/**
	 * @param trader
	 */
	public void setCurrentTrader(Trader currentTrader) {
		// Guarda el trader anterior antes de cambiarlo
		if (this.currentTrader!=null) {
			prevTrader = this.currentTrader;
		}
		this.currentTrader = currentTrader;
	}

	/**
	 * @return the prevTrader
	 */
	public Trader getPrevTrader() {
		return prevTrader;
	}

	/**
	 * @param prevTrader the prevTrader to set
	 */
	public void setPrevTrader(Trader prevTrader) {
		this.prevTrader = prevTrader;
	}

	public List<String> getBanners() {
		List<String> banners = new ArrayList<>();
		
		banners.add(publiProperties.getString("index.banner.1", lang));
		banners.add(publiProperties.getString("index.banner.2", lang));
		banners.add(publiProperties.getString("index.banner.3", lang));
		
		return banners;
	}

	/**
	 * @return the lang
	 */
	public synchronized Locale getLang() {
		return lang;
	}

	/**
	 * @param lang the lang to set
	 */
	public synchronized void setLang(Locale lang) {
		this.lang = lang;
	}
}

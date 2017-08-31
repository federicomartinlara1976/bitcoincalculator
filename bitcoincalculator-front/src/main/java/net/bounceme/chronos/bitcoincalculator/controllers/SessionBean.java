package net.bounceme.chronos.bitcoincalculator.controllers;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import net.bounceme.chronos.bitcoincalculator.common.Constantes.Traders;
import net.bounceme.chronos.bitcoincalculator.exceptions.ServiceException;
import net.bounceme.chronos.bitcoincalculator.services.CalculatorService;
import net.bounceme.chronos.bitcoincalculator.services.trading.Trader;
import net.bounceme.chronos.bitcoincalculator.utils.MessageUtils;
import net.bounceme.chronos.utils.jsf.controller.BaseBean;
import net.bounceme.chronos.utils.log.Log;
import net.bounceme.chronos.utils.log.LogFactory;
import net.bounceme.chronos.utils.log.Log.LogLevels;

@ManagedBean(name = SessionBean.NAME)
@SessionScoped
public class SessionBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4764455202310413427L;
	
	private static final Log LOGGER = LogFactory.getInstance().getLog(SessionBean.class);

	public static final String NAME = "sessionBean";

	@Autowired
	@Qualifier(CalculatorService.NAME)
	private transient CalculatorService calculatorService;

	private String lang;

	private Double difficultyFactor;

	private Double nextDifficultyFactor;

	private BigDecimal bcPerBlock;

	private transient Trader prevTrader;
	
	private transient Trader currentTrader;
	
	@PostConstruct
	public void init() {
		try {
			lang = "en";
			difficultyFactor = calculatorService.getCurrentDifficultyFactor();
			nextDifficultyFactor = calculatorService.getNextDifficultyFactor();
			bcPerBlock = calculatorService.getCurrentBcPerBlock();
			currentTrader = calculatorService.getTrader(Traders.Default);
		}
		catch (ServiceException e) {
			String message = MessageUtils.getMessage("calculator.noTraders", lang);
			addMessage(FacesMessage.SEVERITY_ERROR, message);
		}
	}

	public void reset() {
		LOGGER.log(LogLevels.DEBUG, "Change lang to " + lang);
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
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
}

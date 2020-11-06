package net.bounceme.chronos.bitcoincalculator.listeners;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.faces.application.Application;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

import lombok.extern.log4j.Log4j;
import net.bounceme.chronos.bitcoincalculator.utils.LogWrapper;
import net.bounceme.chronos.utils.i18n.service.TranslationService;
import net.bounceme.chronos.utils.spring.BeanUtils;

/**
 * @author frederik
 *
 */
@Log4j
public class InitListener implements SystemEventListener {
	
	private static final String basePackage = "net.bounceme.chronos.bitcoincalculator";
	private static final String[] resources = {"MessageBundle", "PubliBundle"};
	private static final String baseLang = "es";
	
	@Override
	public void processEvent(SystemEvent event) throws AbortProcessingException {
		Application application = (Application) event.getSource();
		
		initSupportedLocales(application);
	}

	@Override
	public boolean isListenerForSource(Object source) {
		return source instanceof Application;
	}
	
	/**
	 * @param application
	 */
	private void initSupportedLocales(Application application) {
		LogWrapper.debug(log, "Loading i18n...");
		List<String> locales = getLocalesFromApplication(application);
		TranslationService translationService = loadServiceFromContext();
		if (!Objects.isNull(translationService)) {
			translationService.buildTranslations(basePackage, baseLang, resources, locales);
		}
		LogWrapper.debug(log, "Ok");
	}

	/**
	 * @return
	 */
	private TranslationService loadServiceFromContext() {
		return (TranslationService) BeanUtils.getBean("translationService");
	}

	/**
	 * @param application
	 * @return
	 */
	private List<String> getLocalesFromApplication(Application application) {
		Iterator<Locale> itLocales = application.getSupportedLocales();
		
		List<String> supportedLangs = new ArrayList<>();
		while (itLocales.hasNext()) {
			Locale locale = itLocales.next();
			supportedLangs.add(locale.getLanguage());
		}
		return supportedLangs;
	}
}

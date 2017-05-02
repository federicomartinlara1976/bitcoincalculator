package net.bounceme.chronos.bitcoincalculator.error;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * @author frederik
 *
 */
public class CustomExceptionHandler extends ExceptionHandlerWrapper {

	private static final Logger LOGGER = Logger.getLogger(CustomExceptionHandler.class);
	private final ExceptionHandler wrapped;

	/**
	 * @param wrapped
	 */
	public CustomExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;

	}

	@Override
	public void handle() throws FacesException {
		final Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();

		while (iterator.hasNext()) {
			ExceptionQueuedEvent event = iterator.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
			// get the exception from context
			Throwable throwable = context.getException();
			final FacesContext facesContext = FacesContext.getCurrentInstance();
			
			try {
				// log error ?
				LOGGER.error("Severe Exception Occured", throwable);
				HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
				URL url = new URL(request.getScheme(), request.getServerName(), request.getServerPort(), request.getContextPath());
				
		        String rootContext = url.toString();
				
				facesContext.getExternalContext().redirect(rootContext + "/error.xhtml");
				
			} catch (IOException e) {
				LOGGER.error(e);
			} finally {
				// remove it from queue
				iterator.remove();
			}
		}
		// parent hanle
		getWrapped().handle();
	}

}
package net.bounceme.chronos.bitcoincalculator.controllers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.servlet.ServletException;

import net.bounceme.chronos.utils.jsf.controller.BaseBean;

@ManagedBean(name = ErrorBean.NAME)
@RequestScoped
public class ErrorBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String NAME = "errorBean";

	public String getStackTrace() {
		Map<String, Object> request = getJsfHelper().getRequestMap();
		Throwable ex = (Throwable) request.get("javax.servlet.error.exception");
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		fillStackTrace(ex, pw);
		return sw.toString();
	}

	private void fillStackTrace(Throwable t, PrintWriter w) {
		if (t == null)
			return;
		t.printStackTrace(w);
		if (t instanceof ServletException) {
			Throwable cause = ((ServletException) t).getRootCause();
			if (cause != null) {
				w.println("Root cause:");
				fillStackTrace(cause, w);
			}
		}
		else if (t instanceof SQLException) {
			Throwable cause = ((SQLException) t).getNextException();
			if (cause != null) {
				w.println("Next exception:");
				fillStackTrace(cause, w);
			}
		}
		else {
			Throwable cause = t.getCause();
			if (cause != null) {
				w.println("Cause:");
				fillStackTrace(cause, w);
			}
		}
	}
}

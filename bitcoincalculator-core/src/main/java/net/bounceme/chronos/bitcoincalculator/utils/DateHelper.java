package net.bounceme.chronos.bitcoincalculator.utils;

import java.util.Calendar;
import java.util.Date;

public class DateHelper {
	private Calendar calendar;
	
	public DateHelper() {
		Date date = new Date();
		init(date);
	}
	
	public DateHelper(Date date) {
		init(date);
	}
	
	private void init(Date date) {
		calendar = Calendar.getInstance();
		calendar.setTime(date);
	}
	
	public int getWeek() {
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}
}

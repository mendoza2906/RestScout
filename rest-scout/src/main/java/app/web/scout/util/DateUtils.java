package app.web.scout.util;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	/**
	 * Return current timestamp. 
	 * @return
	 */
	public static Timestamp getCurrentTimestamp() {
		return dateToTimestamp(new Date());
	}
	
	/**
	 * Return new Timestamp from a date, id date is null returns current timestamp.
	 * @param date
	 * @return
	 */
	public static Timestamp dateToTimestamp(Date date) {
		if (date == null) {
			date = new Date();
		}
		return new Timestamp(date.getTime());
	}

	/**
	 * Get last day of month.
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	/**
	 * Add date part to date.
	 * @param date
	 * @param minute
	 * @param offsetToSend
	 * @return
	 */
	public static Date addToDate(Date date, int datePart, Integer offset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(datePart, offset);
		return cal.getTime();
	}
}

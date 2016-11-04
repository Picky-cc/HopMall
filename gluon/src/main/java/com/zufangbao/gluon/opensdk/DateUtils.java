/**
 * 
 */
package com.zufangbao.gluon.opensdk;

import java.util.Calendar;
import java.util.Date;

/**
 * @author wukai
 *
 */
public abstract class DateUtils extends com.demo2do.core.utils.DateUtils{

	public static int distanceDay(Date begin,Date end){
		
		Calendar beginCalendar = Calendar.getInstance();
		
		Calendar endCalendar = Calendar.getInstance();
		
		beginCalendar.setTime(begin);
		
		endCalendar.setTime(end);
		
		setTimeToZero(beginCalendar);
		
		setTimeToZero(endCalendar);
		
		return (int) ((beginCalendar.getTimeInMillis() - endCalendar.getTimeInMillis()) / 86400000);
	}
	
	private static void setTimeToZero(Calendar calendar){
		
		int Zero = 0;
		
		calendar.set(Calendar.HOUR_OF_DAY,Zero);
		
		calendar.set(Calendar.MINUTE, Zero);
		
		calendar.set(Calendar.SECOND, Zero);
	}
	
	public static Date parseDateByDay(String dateString) {
		Date date = null;
		try {
			date = DateUtils.parseDate(dateString, "yyyy-MM-dd");
		} catch (Exception e){
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date parseDate(String dateString) {
		Date date = null;
		try {
			date = DateUtils.parseDate(dateString, "yyyy-MM-dd HH:mm:ss");
		} catch (Exception e){
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 比较获取最大时间
	 */
	public static Date getMaxDate(Date date1, Date date2) {
		if(date1 != null && date2 != null) {
			if(date1.before(date2)) {
				return date2;
			}else {
				return date1;
			}
		}else {
			return date2 == null ? date1 : date2;
		}
	}
	
}

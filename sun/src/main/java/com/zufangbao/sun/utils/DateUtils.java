package com.zufangbao.sun.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.zufangbao.sun.Constant;

public class DateUtils extends com.demo2do.core.utils.DateUtils {

	public static String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
	public static String DATE_FORMAT_HHMM = "HHmm";
	public static String DATE_FORMAT_YYMMDDHH = "yyMMddHH";
	public static String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	
	public static String YEAR_MONTH_FORMAT = "yyyyMM";
	
	public static String DATE_FORMAT = "yyyy-MM-dd";
	
	public static String DATE_FORMAT_UNDERLINE = "yyyy_MM_dd";
	
	public static String DATE_FORMAT_DOT = "yyyy.MM.dd";
	
	public static String DATE_FORMAT_NO_SPACE = "yyyyMMdd";
	
	public static String DATE_FORMAT_SLASH_AMERICAN = "MM/dd/yy";
	
	public static String DATE_FORMAT_SLASH = "yyyy/MM/dd";
	
	public static String LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static Date MIN_DATE = DateUtils.parseDate("1900-01-01", DATE_FORMAT);

	public static Date MAX_DATE = DateUtils.parseDate("2900-01-01", DATE_FORMAT);

	public static String DATE_FORMAT_CHINESE = "yyyy年MM月dd日";
	
	public static Date getToday() {
		return DateUtils.parseDate(today());
	}
	
	public static Date plusDay(Date date,int dayIntervals){
		if (date ==null){
			return null;
		}
		long time = date.getTime();
		Date newDate = new Date(time + dayIntervals * Constant.ONE_DAY_TIME);
		return newDate;
	}

	public static String getCurrentTimeMillis() {
		return DateUtils.format(new Date(), "yyyyMMddHHmmssSSS");
	}

	public static Date getFirstDayOfYear(Date date) {
		return getFirstDayOfYear(Integer.valueOf(DateUtils.getYear(date)));
	}

	public static Date getLastDayOfYear(Date date) {
		return getLastDayOfYear(Integer.valueOf(DateUtils.getYear(date)));
	}

	public static Date getLastDayOfYear(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, calendar.getActualMaximum(Calendar.MONTH));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
		return calendar.getTime();
	}

	public static Date getFirstDayOfYear(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, calendar.getActualMinimum(Calendar.MONTH));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
		return calendar.getTime();
	}

	public static Date getLaterDate(List<Date> dates) {
		if (CollectionUtils.isEmpty(dates)) {
			return null;
		}
		Date laterDate = null;
		for (Date date : dates) {
			if (date == null)
				continue;
			if (laterDate == null) {
				laterDate = date;
			}
			if (DateUtils.compareTwoDatesOnDay(laterDate, date) < 0) {
				laterDate = date;
			}
		}
		return laterDate;
	}

	/**
	 * 完整格式
	 * 
	 * yyyyMMddHHmmss
	 */
	public static final String DATE_FORMAT_FULL_DATE_TIME = "yyyyMMddHHmmss";

	/**
	 * 获取完整格式的时间：yyyyMMddHHmmss
	 * 
	 * @param date
	 * @return
	 */
	public static String getFullDateTime(Date date) {
		return format(date, DATE_FORMAT_FULL_DATE_TIME);
	}

	/**
	 * 获取完整格式的当前时间：yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String getNowFullDateTime() {
		return getFullDateTime(new Date());
	}
	
	/**
	 * 将 完整格式的当前时间字符串转换成Date类型
	 * @param dateTime yyyyMMddHHmmss
	 * @return
	 */
	public static Date parseFullDateTimeToDate(String dateTime) {
		if(StringUtils.isEmpty(dateTime)) {
			return null;
		}
		return parseDate(dateTime, DATE_FORMAT_FULL_DATE_TIME);
	}

	private final static int ZERO = 0;
	private final static int ONE = 1;
	private final static int TWO = 2;
	/**
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date addDaysNotIncludingWeekend(Date currentDate, int days) {
		if(days <= ZERO) {
			return currentDate;
		}
		Date date = currentDate;
		for (int index = ZERO; index < days; index++) {
			Date nextDay = addDays(date, ONE);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(nextDay);
			int dayOfWeekend = calendar.get(Calendar.DAY_OF_WEEK);
			if(dayOfWeekend == Calendar.SATURDAY) {
				date = addDays(nextDay, TWO);
			}else if(dayOfWeekend == Calendar.SUNDAY) {
				date = addDays(nextDay, ONE);
			}else {
				date = nextDay;
			}
		}
		return date;
	}
	public static Date parseDate(String dateStr){
		
		String[] dateFormatArray = new String[]{DATE_FORMAT,DATE_FORMAT_DOT,DATE_FORMAT_SLASH};
		
		return parseDate(dateStr, dateFormatArray);
	}
	public static String formatByYearMonth(Date date){
		return format(date, YEAR_MONTH_FORMAT);
	}
	public static Date parseDateByMonthFirstDay(Date date){
		if(null == date){
			return null;
		}
		int year = year(date);
		
		int month = month(date);
		
		return DateUtils.parseDate(year, month, 1);
		
	}
	public static Date parseDateByMonthFirstDay(String dateStr){
		
		if(StringUtils.isEmpty(dateStr)){
			return null;
		}
		
		Pattern pattern = Pattern.compile(ValidatorUtil.YearMonth);
		
		Matcher matcher = pattern.matcher(dateStr);
		
		if(!matcher.matches()){
			return null;
		}
//		//to fix
//		int year = Integer.parseInt(matcher.group(0));
//		//to fix
//		int month = Integer.parseInt(matcher.group(1));
		
		String[] res = dateStr.split("[-/]");
	
		if(res == null || res.length < 2){
			return null;
		}
		int year = Integer.parseInt(res[0]);
		
		int month = Integer.parseInt(res[1]);
		
		return DateUtils.parseDate(year, month, 1);
	}
	public static int distanceDaysBetween(Date startDate,Date endDate){
		return Math.abs(compareTwoDatesOnDay(startDate, endDate));
	}
}


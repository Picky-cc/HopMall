package com.zufangbao.sun.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

import com.demo2do.core.entity.Result;
import com.demo2do.core.utils.JsonUtils;

public class DateUtilsTest {
	@Test
	public void testGetFirstDayOfYear(){
		Date date = DateUtils.parseDate("2015-10-10 12:12:12", "yyyy-MM-dd HH:mm:ss");
		Date firstDay = DateUtils.getFirstDayOfYear(date);
		assertEquals("1",DateUtils.getDay(firstDay));
		assertEquals("01",DateUtils.getMonth(firstDay));
		assertEquals("2015",DateUtils.getYear(date));
	}
	
	@Test
	public void testGetLastDayOfYear(){
		Date date = DateUtils.parseDate("2015-10-10 12:12:12", "yyyy-MM-dd HH:mm:ss");
		Date lastDay = DateUtils.getLastDayOfYear(date);
		assertEquals("31",DateUtils.getDay(lastDay));
		assertEquals("12",DateUtils.getMonth(lastDay));
		assertEquals("2015",DateUtils.getYear(date));
	}
	@Test
	public void testToday(){
		
		Date date = DateUtils.getToday();
		
		String dateStr = DateUtils.format(date, DateUtils.LONG_DATE_FORMAT);
		
		assertTrue(dateStr.contains("00:00:00"));
	}
	@Test
	public void testNow(){
		
		Date date = new Date();
		
		String dateStr = DateUtils.format(date, DateUtils.LONG_DATE_FORMAT);
		
		System.out.println(dateStr);
	}
	/**
	 * 去扩充
	 */
	@Test
	public void testGetMonthFirstDay(){
		
		Date date = DateUtils.parseDate("2017-02-28");
		
        date = DateUtils.addMonths(date, 1);
        
        System.out.println(DateUtils.format(date, "yyyy-MM-dd"));
	}
	/**
	 * 测试添加月份
	 */
	@Test
	public void testAddYear(){
		
		Date date = DateUtils.parseDate("2015-02-28");
		
		date = DateUtils.addMonths(date, 12);
		
		assertEquals("2016-02-28",DateUtils.format(date));
		
		Date date1 = DateUtils.parseDate("2015-01-01");
		
		date1 = DateUtils.addMonths(date1, 12);
		
		assertEquals("2016-01-01",DateUtils.format(date1));
		
		Date date2 = DateUtils.parseDate("2015-01-31");
		
		date2 = DateUtils.addMonths(date2, 12);
		
		assertEquals("2016-01-31",DateUtils.format(date2));
		
		Date date3 = DateUtils.parseDate("2015-02-28");
		
		date3 = DateUtils.addMonths(date3, 1);
		
		assertEquals("2015-03-28",DateUtils.format(date3));
		
		Date date4 = DateUtils.parseDate("2015-01-31");
		
		date4 = DateUtils.addMonths(date4, 1);
		
		assertEquals("2015-02-28",DateUtils.format(date4));
	}
	@Test
	public void testdistanceDaysBetween4SameYearMonth(){
		
		Date startDate = DateUtils.parseDate("2015-01-01");
		
		Date endDate = DateUtils.parseDate("2015-01-03");
		
		assertEquals(2,DateUtils.distanceDaysBetween(startDate, endDate));
	}
	@Test
	public void testdistanceDaysBetween4SameYearDifferentMonth(){
		
		Date startDate = DateUtils.parseDate("2015-01-01");
		
		Date endDate = DateUtils.parseDate("2015-04-01");
		
		assertEquals(90,DateUtils.distanceDaysBetween(startDate, endDate));
		
		endDate = DateUtils.parseDate("2015-06-01");
		
		assertEquals(151,DateUtils.distanceDaysBetween(startDate, endDate));
	}
	@Test
	public void testdistanceDaysBetween4DifferenetYear(){
		//平年
		Date startDate = DateUtils.parseDate("2015-01-01");
		
		Date endDate = DateUtils.parseDate("2016-01-01");
		
		assertEquals(365,DateUtils.distanceDaysBetween(startDate, endDate));
		
//		闰年
		startDate = DateUtils.parseDate("2016-01-01");
		
		endDate = DateUtils.parseDate("2017-01-01");
		
		assertEquals(366,DateUtils.distanceDaysBetween(startDate, endDate));
	}
	@Test
	public void getTimestamp(){
		
		System.out.println(System.currentTimeMillis());
		
		System.out.println(UUID.randomUUID().toString());
		
		Result result = new Result();
		
		Map<String,Object> value = new HashMap<String,Object>();
		
		value.put("Uuid", UUID.randomUUID().toString());
		value.put("propertyNo", "0012");
		value.put("isDelete", false);
		
		List<Map<String,Object>> valueList = new ArrayList<Map<String,Object>>();
		
		valueList.add(value);
		
		result.data("propertyList", valueList);
		
		System.out.println(JsonUtils.toJsonString(result.success()));
		
		List<String> houseUuids = new ArrayList<String>(){{
			add(UUID.randomUUID().toString());
			add(UUID.randomUUID().toString());
			add(UUID.randomUUID().toString());
		}};
		System.out.println(JsonUtils.toJsonString(houseUuids));
		
	}
	@Test
	public void test_addDaysNotIncludingWeekend_of_1() {
		Date currentDay = DateUtils.asDay("2016-04-12");// 周二
		Date result = DateUtils.asDay("2016-04-15");// 周五
		Date date = DateUtils.addDaysNotIncludingWeekend(currentDay, 3);
		assertEquals(result, date);
	}

	@Test
	public void test_addDaysNotIncludingWeekend_of_2() {
		Date currentDay = DateUtils.asDay("2016-04-13");// 周三
		Date result = DateUtils.asDay("2016-04-18");// 下周一
		Date date = DateUtils.addDaysNotIncludingWeekend(currentDay, 3);
		assertEquals(result, date);
	}

	@Test
	public void test_addDaysNotIncludingWeekend_of_3() {
		Date currentDay = DateUtils.asDay("2016-04-14");// 周四
		Date result = DateUtils.asDay("2016-04-19");// 下周二
		Date date = DateUtils.addDaysNotIncludingWeekend(currentDay, 3);
		assertEquals(result, date);
	}

	@Test
	public void test_addDaysNotIncludingWeekend_of_4() {
		Date currentDay = DateUtils.asDay("2016-04-15");// 周五
		Date result = DateUtils.asDay("2016-04-20");// 下周三
		Date date = DateUtils.addDaysNotIncludingWeekend(currentDay, 3);
		assertEquals(result, date);
	}

	@Test
	public void test_addDaysNotIncludingWeekend_of_5() {
		Date currentDay = DateUtils.asDay("2016-04-15");// 周六
		Date result = DateUtils.asDay("2016-04-20");// 下周三
		Date date = DateUtils.addDaysNotIncludingWeekend(currentDay, 3);
		assertEquals(result, date);
	}

	@Test
	public void test_addDaysNotIncludingWeekend_of_6() {
		Date currentDay = DateUtils.asDay("2016-04-17");// 周日
		Date result = DateUtils.asDay("2016-04-20");// 下周三
		Date date = DateUtils.addDaysNotIncludingWeekend(currentDay, 3);
		assertEquals(result, date);
	}
	
	@Test
	public void test_uuid(){
		for (int i=0;i<6;i++) {
			System.out.println(UUID.randomUUID().toString());
		}
	}

}

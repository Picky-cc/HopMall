package com.zufangbao.earth.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RegexUtilsTest {

	@Test
	public void testIsDecimal() {
		String string = "6.001";
		String string2 = "0.04";
		String string3 = "-0.4";
		
		assertEquals(true, RegexUtils.isDecimal(string));
		assertEquals(true, RegexUtils.isDecimal(string2));
		assertEquals(true, RegexUtils.isDecimal(string3));
	}
	
	@Test
	public void testIsDecimalForWrong() {
		String string = "6";
		String string2 = "0";
		String string3 = "-0ab";
		
		assertEquals(false, RegexUtils.isDecimal(string));
		assertEquals(false, RegexUtils.isDecimal(string2));
		assertEquals(false, RegexUtils.isDecimal(string3));
	}
	

	@Test
	public void testIsNumberic() {
		String string = "6";
		String string2 = "0";
		String string3 = "-5";
		String string4 = "8";
		
		
		assertEquals(true, RegexUtils.isNumberic(string));
		assertEquals(true, RegexUtils.isNumberic(string2));
		assertEquals(true, RegexUtils.isNumberic(string3));
		assertEquals(true, RegexUtils.isNumberic(string4));
	}
	
	@Test
	public void testIsNumbericForWrong() {
		String string = "abc";
		String string2 = "0.01";
		String string3 = "-5.5";
		String string4 = " 5";
		
		assertEquals(false, RegexUtils.isNumberic(string));
		assertEquals(false, RegexUtils.isNumberic(string2));
		assertEquals(false, RegexUtils.isNumberic(string3));
		assertEquals(false, RegexUtils.isNumberic(string4));
	}

	@Test
	public void testIsRealNumber() {
		String string = "6.001";
		String string2 = "0.04";
		String string3 = "5";
		String string4 = "-0.4";
		
		assertEquals(true, RegexUtils.isRealNumber(string));
		assertEquals(true, RegexUtils.isRealNumber(string2));
		assertEquals(true, RegexUtils.isRealNumber(string3));
		assertEquals(true, RegexUtils.isRealNumber(string4));
	}
	
	@Test
	public void testIsRealNumberForWrong(){
		String string = "0.1ab";
		String string2 = "0.1  ";
		String string3 = "90.";
		String string4 = " 5";
		
		assertEquals(false, RegexUtils.isRealNumber(string));
		assertEquals(false, RegexUtils.isRealNumber(string2));
		assertEquals(false, RegexUtils.isRealNumber(string3));
		assertEquals(false, RegexUtils.isRealNumber(string4));
	}
	
	@Test
	public void testIsMobile(){
		String mobile = "13051364779";
		assertTrue(RegexUtils.isMobile(mobile));
	}
	
	@Test
	public void testIsMobileForInvaild(){
		
		assertFalse(RegexUtils.isMobile(null));
		
		String longMobile = "130513647790";
		assertFalse(RegexUtils.isMobile(longMobile));
		
		String invalidNumber = "13051364779a";
		assertFalse(RegexUtils.isMobile(invalidNumber));
		
		String empty = "";
		assertFalse(RegexUtils.isMobile(empty));
		
		
	}

}

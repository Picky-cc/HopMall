/**
 * 
 */
package com.zufangbao.sun.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author wukai
 *
 */
public class FinanceUtilsTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link com.zufangbao.sun.utils.FinanceUtils#convert_yuan_to_cent(java.math.BigDecimal)}.
	 */
	@Test
	public void testConvert_yuan_to_cent() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.zufangbao.sun.utils.FinanceUtils#convert_cent_to_yuan(java.math.BigDecimal)}.
	 */
	@Test
	public void testConvert_cent_to_yuan() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.zufangbao.sun.utils.FinanceUtils#convertToBigDecimal(java.lang.Double)}.
	 */
	@Test
	public void testConvertToBigDecimal() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.zufangbao.sun.utils.FinanceUtils#convertToDouble(java.math.BigDecimal)}.
	 */
	@Test
	public void testConvertToDouble() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.zufangbao.sun.utils.FinanceUtils#divide(java.math.BigDecimal, java.math.BigDecimal)}.
	 */
	@Test
	public void testDivide() {
		
		BigDecimal dividend = new BigDecimal("1000");
		
		BigDecimal divisor = new BigDecimal("40");
		
		assertEquals(new BigDecimal("25.00"),FinanceUtils.divide(dividend, divisor));
	}
	@Test
	public void testDivideForIntDivisor() {
		
		BigDecimal dividend = new BigDecimal("1000");
		
		int divisor = 40;
		
		assertEquals(new BigDecimal("25.00"),FinanceUtils.divide(dividend, divisor));
	}
}

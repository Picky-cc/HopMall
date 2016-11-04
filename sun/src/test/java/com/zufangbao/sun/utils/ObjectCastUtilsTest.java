package com.zufangbao.sun.utils;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/**
 * 
 * @author wukai
 *
 */
public class ObjectCastUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCastString() {
		
		Object obj = "string";
		
		assertEquals("string",ObjectCastUtils.castToString(obj));
		assertEquals(StringUtils.EMPTY,ObjectCastUtils.castToString(""));
		
		assertEquals(StringUtils.EMPTY,ObjectCastUtils.castToString(null));
		
	}
	@Test
	public void testCastInteger() {
		
		assertEquals(121221,ObjectCastUtils.castToInteger("121221"));
		Object obj = 121221;
		assertEquals(121221,ObjectCastUtils.castToInteger(121221));
		assertEquals(0,ObjectCastUtils.castToInteger("ssss"));
		assertEquals(0,ObjectCastUtils.castToInteger(""));
		
		assertEquals(0,ObjectCastUtils.castToInteger(null));
		
	}
	@Test
	public void testCastIntegerWithDefault() {
		
		assertEquals(121221,ObjectCastUtils.castToInteger("121221",12));
		assertEquals(121221,ObjectCastUtils.castToInteger(121221,12));
		assertEquals(12,ObjectCastUtils.castToInteger("ssss",12));
		assertEquals(12,ObjectCastUtils.castToInteger(null,12));
	}
	@Test
	public void testCastDouble() {
		
		assertEquals(121221D,ObjectCastUtils.castToDouble("121221"),0);
		assertEquals(121221D,ObjectCastUtils.castToDouble(121221),0);
		assertEquals(0D,ObjectCastUtils.castToDouble("ssss"),0);
		assertEquals(0D,ObjectCastUtils.castToDouble(null),0);
	}

}

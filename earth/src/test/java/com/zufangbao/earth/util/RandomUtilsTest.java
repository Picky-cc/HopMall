/**
 * 
 */
package com.zufangbao.earth.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author zsy
 *
 */
public class RandomUtilsTest {

	@Test
	public void getRandomNumberCodeTest() {
		int length = 100;
		for (int i = 0; i < length; i++) {
			String result = RandomUtils.getRandomNumberCode(i);
			assertEquals(i, result.length());
		}
	}

}

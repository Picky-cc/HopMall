/**
 * 
 */
package com.zufangbao.earth.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.util.StringUtils;

/**
 * @author zsy
 *
 */
public class Md5UtilTest {

	@Test
	public void testEncode() {
		String[] testDataArray = {"123456", "abcdefg"};
		String[] expectResultArray = {"e10adc3949ba59abbe56e057f20f883e","7ac66c0f148de9519b8bd264312c4d64"};
		String[] testResultArray = new String[10];
		for (int i = 0; i < testDataArray.length; i++) {
			testResultArray[i] = Md5Util.encode(testDataArray[i]);
			StringUtils.isEmpty(testResultArray[i]);
			assertEquals(expectResultArray[i],testResultArray[i]);
		}
	}

}

package com.zufangbao.earth.util;


import org.junit.Assert;
import org.junit.Test;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

public class MessageUtilsTest {

	@Test
	public void test() {
		
		String message = ApplicationMessageUtils.getChineseMessage("enum.agreement-type.suit");
		
		Assert.assertEquals("集合", message);
	}

}

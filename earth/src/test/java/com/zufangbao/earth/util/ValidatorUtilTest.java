package com.zufangbao.earth.util;



import org.junit.Assert;
import org.junit.Test;

import com.zufangbao.sun.utils.StringUtils;

public class ValidatorUtilTest {
	
	
    @Test
	public void testEmptyEmailValidatorUtilTest() {
    	String input_mail ="";
    	
    	Assert.assertFalse(ValidatorUtil.isEmail(input_mail));
	}
    @Test
	public void testNormalEmailValidatorUtilTest() {
    	String input_mail ="adb@qq.com";
    	
    	boolean judge = ValidatorUtil.isEmail(input_mail);
    	
    	Assert.assertTrue(judge);
	}
    @Test
	public void testWrongEmailValidatorUtilTest() {
    	String string ="adbqq.com";
    	
    	boolean judge = ValidatorUtil.isEmail(string);
    	
    	Assert.assertFalse(judge);
    	
 

	}

    @Test
	public void testErrorEmailValidatorUtilTest() {
    	String string ="adb@qqcom";
    	
    	boolean judge = ValidatorUtil.isEmail(string);
    	
    	Assert.assertFalse(judge);
    	}
    
    @Test
    public void testNumberToStr() {
    	String str = "1234.56";
    	String strAfter = StringUtils.removeDecimal(str);
    	Assert.assertTrue("1234".equals(strAfter));
    }
    @Test
    public void testNumberToStr2() {
    	String str = "1234";
    	String strAfter = StringUtils.removeDecimal(str);
    	Assert.assertTrue(str.equals(strAfter));
    }
    
    @Test
    public void testNumberToStr3() {
    	String str = null;
    	String strAfter = StringUtils.removeDecimal(str);
    	Assert.assertTrue(str == strAfter);
    }
    
    @Test
    public void testNumberToStr4() {
    	String str = "0.01";
    	String strAfter = StringUtils.removeDecimal(str);
    	Assert.assertTrue("0".equals(strAfter));
    }

    @Test
    public void testNumberToStr5() {
    	String str = "";
    	String strAfter = StringUtils.removeDecimal(str);
    	Assert.assertTrue(str.equals(strAfter));
    }
}

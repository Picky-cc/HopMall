package com.suidifu.hathaway.util;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.suidifu.hathaway.mq.exceptions.DuplicateMethodNameException;

public class ReflectionUtilsTest {
	
	interface A {
		
		public String aa(String bb);
		
		public String aa(String aa,String bb);
	}
	class AA implements A{

		@Override
		public String aa(String bb) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public String aa(String aa, String bb) {
			// TODO Auto-generated method stub
			return null;
		}
		public String aa(String aa, String bb,String cc) {
			// TODO Auto-generated method stub
			return null;
		}
		public String bb(){
			return "hi,only one method in AA";
		}
		private String bb(String bb){
			return "this is private method,not choose";
		}
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Test
	public void testFindOnlyMethod() throws DuplicateMethodNameException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		A aa = new AA();
		
		Method method = ReflectionUtils.findOnlyPublicAccessMethod(aa.getClass(), "bb");
		
		String expectedValue =  "hi,only one method in AA";
		
		assertEquals(expectedValue,method.invoke(aa).toString());
		
	}
	
	@Test(expected=NoSuchMethodException.class)
	public void testNoSuchMethodException() throws DuplicateMethodNameException, NoSuchMethodException {
		
		ReflectionUtils.findOnlyPublicAccessMethod(new AA().getClass(), "xxxx");
	}
	@Test(expected=DuplicateMethodNameException.class)
	public void testDuplicateMethodNameException() throws DuplicateMethodNameException, NoSuchMethodException {
		
		ReflectionUtils.findOnlyPublicAccessMethod(new AA().getClass(), "aa");
	}

}

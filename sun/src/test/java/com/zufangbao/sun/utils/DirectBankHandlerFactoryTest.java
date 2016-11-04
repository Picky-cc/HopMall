package com.zufangbao.sun.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.support.AopUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zufangbao.sun.Constant.BankCorpEps;
import com.zufangbao.sun.handler.IDirectBankHandler;
import com.zufangbao.sun.handler.impl.CMBDirectBankHandlerImpl;
import com.zufangbao.sun.handler.impl.DefaultDirectBankHandlerImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml"
		})
public class DirectBankHandlerFactoryTest{

	@Test
	public void testNewInstance(){
		IDirectBankHandler directBankHandlerProxy = DirectBankHandlerFactory.newInstance("");
		
		assertTrue(AopUtils.isAopProxy(directBankHandlerProxy));
		
		Class<?> targetDirectBankHandler = AopUtils.getTargetClass(directBankHandlerProxy);
		
		System.out.println("targetClass:"+targetDirectBankHandler.getName()+",directBankHandler:"+directBankHandlerProxy.getClass().getName()+",DefaultDirectBankHandlerImpl:"+DefaultDirectBankHandlerImpl.class.getName());
		
		assertEquals(targetDirectBankHandler.getName(),  DefaultDirectBankHandlerImpl.class.getName());
		
		directBankHandlerProxy = DirectBankHandlerFactory.newInstance(BankCorpEps.CMB_CODE);
		targetDirectBankHandler = AopUtils.getTargetClass(directBankHandlerProxy);
		assertEquals(targetDirectBankHandler.getName(),  CMBDirectBankHandlerImpl.class.getName());
		
	}
	
}

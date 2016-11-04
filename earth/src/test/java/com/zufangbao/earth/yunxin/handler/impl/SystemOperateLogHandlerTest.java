package com.zufangbao.earth.yunxin.handler.impl;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.service.PrincipalService;
import com.zufangbao.earth.yunxin.handler.SystemOperateLogHandler;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.service.FinancialContractService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class SystemOperateLogHandlerTest {

	
	
	@Autowired
	private SystemOperateLogHandler systemOperateLogHandler;
	@Autowired
	private PrincipalService principalService;
	@Autowired
	private FinancialContractService financialContractService;
	
	@Test
	@Sql("classpath:test/yunxin/systemOperateLog/")
	public void testGenerateCommonSystemOperateLog(){
		Principal principal = principalService.load(Principal.class, 1l);
		FinancialContract financialContract = financialContractService.load(FinancialContract.class,1l);
//		systemOperateLogHandler.generateCommonSystemOperateLog(principal,"0.0.0.0.0", "contractNo", LogFunctionType.ADDFINANCIALCONTRACT, LogOperateType.ADD, object);
	}
}

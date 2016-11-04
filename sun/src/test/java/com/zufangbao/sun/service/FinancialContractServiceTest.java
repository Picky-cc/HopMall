package com.zufangbao.sun.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zufangbao.sun.entity.financial.FinancialContract;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml"})
public class FinancialContractServiceTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Autowired
	private FinancialContractService financialContractService;
	
	@Autowired
	private ContractService contractService;
	
	@Test
	@Sql("classpath:test/getFinancialContractTest.sql")
	public void getFinancialContractTestNotExistNO(){
		String contractNo = "DCF-RX-FR906AF";
		FinancialContract financialContract = this.financialContractService.getUniqueFinancialContractBy(contractNo);
		Assert.assertNull(financialContract);
	}
	
}

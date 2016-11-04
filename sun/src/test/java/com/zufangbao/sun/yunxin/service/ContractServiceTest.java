package com.zufangbao.sun.yunxin.service;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.FinancialContractService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
})
@TransactionConfiguration(defaultRollback = true)
public class ContractServiceTest extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private ContractService contractService;
	@Autowired
	private FinancialContractService financialContractService;
	
	/*************** test calculateBeginningPrincipal start ***************/
	@Test
	public void test_calculateBeginningPrincipal_no_contract() {
		Date startDate = DateUtils.asDay("2016-07-21");
		BigDecimal beginningPrincipal = contractService.calculateBeginningPrincipal(null, startDate);
		Assert.assertEquals(BigDecimal.ZERO, beginningPrincipal);
	}

	@Test
	@Sql("classpath:test/yunxin/repaymentplan/empty_database.sql")
	public void test_calculateBeginningPrincipal_no_result() {
		Date startDate = DateUtils.asDay("2015-05-21");	
		FinancialContract financialContract = new FinancialContract();
		financialContract.setId(1L);
		BigDecimal beginningPrincipal = contractService.calculateBeginningPrincipal(financialContract, startDate);
		Assert.assertEquals(BigDecimal.ZERO, beginningPrincipal);
	}

	@Test
	@Sql("classpath:test/yunxin/repaymentplan/calculateBeginningPrincipal.sql")
	public void test_calculateBeginningPrincipal() {
		Date startDate = DateUtils.asDay("2016-07-01");	
		FinancialContract financialContract = financialContractService.load(FinancialContract.class, 1L);
		BigDecimal beginningPrincipal = contractService.calculateBeginningPrincipal(financialContract, startDate);
		Assert.assertEquals(new BigDecimal("4800.00"), beginningPrincipal);
	}
	/*************** test calculateBeginningPrincipal end ***************/

	
	/*************** test calculateNewLoans start ***************/
	@Test
	public void test_calculateNewLoans_no_contract() {
		Date startDate = DateUtils.asDay("2016-07-01");	
		Date endDate = DateUtils.asDay("2016-07-21");
		BigDecimal newLoans = contractService.calculateNewLoansPrincipal(null, startDate, endDate);
		Assert.assertEquals(BigDecimal.ZERO, newLoans);
	}
	
	@Test
	@Sql("classpath:test/yunxin/repaymentplan/empty_database.sql")
	public void test_calculateNewLoans_no_result() {
		Date startDate = DateUtils.asDay("2015-07-01");	
		Date endDate = DateUtils.asDay("2015-07-21");
		FinancialContract financialContract = financialContractService.getFinancialContractById(1L);
		BigDecimal newLoans = contractService.calculateNewLoansPrincipal(financialContract, startDate, endDate);
		Assert.assertEquals(BigDecimal.ZERO, newLoans);
	}

	@Test
	@Sql("classpath:test/yunxin/repaymentplan/calculateNewLoans.sql")
	public void test_calculateNewLoans() {
		Date startDate = DateUtils.asDay("2016-07-01");	
		Date endDate = DateUtils.asDay("2016-07-21");
		FinancialContract financialContract = financialContractService.load(FinancialContract.class, 1L);
		BigDecimal newLoans = contractService.calculateNewLoansPrincipal(financialContract, startDate, endDate);
		Assert.assertEquals(new BigDecimal("2100.00"), newLoans);
	}
	/*************** test calculateNewLoans end ***************/
}

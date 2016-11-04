package com.zufangbao.earth.yunxin.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.demo2do.core.utils.DateUtils;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.web.controller.financial.FinancialContractController;
import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.yunxin.entity.model.CreateFinancialContractModel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
		"classpath:/DispatcherServlet.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional()
public class FinancialContractControllerTest extends
		AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private FinancialContractService financialContractService;
	@Autowired
	private FinancialContractController financialContractController;
	
	private Principal principal;
	private HttpServletRequest request;
	private HttpServletResponse httpResponse;
	
	@Before
	public void setUp() {
		principal = new Principal();
		principal.setId(1L);
		
		request = new MockHttpServletRequest();
	}
	
	@Test
	@Sql("classpath:test/testAddNewFinancialContract.sql")
	public void testAddNewFinancialContract() {
		CreateFinancialContractModel createFinancialContractModel = new CreateFinancialContractModel(
				"factoring_contract_no", "conrtact_name", 1l, 1l, 1, 3, 90, 90, 3,
				"2016-5-4", "2016-5-20", "account_name", "bank_name",
				"account_no", 1l,1);

		String response = financialContractController
				.addNewFinancialContract(createFinancialContractModel, principal, request, httpResponse);

		FinancialContract financialContract = financialContractService.getUniqueFinancialContractBy("factoring_contract_no");
		Assert.assertEquals("factoring_contract_no",
				financialContract.getContractNo());
		Assert.assertEquals("conrtact_name", financialContract.getContractName());
		Assert.assertEquals(1, financialContract.getCompany().getId()
				.intValue());
		Assert.assertEquals(1, financialContract.getApp().getId().intValue());
		Assert.assertEquals(1, financialContract.getFinancialContractType()
				.ordinal());
		Assert.assertEquals(3, financialContract.getLoanOverdueStartDay());
		Assert.assertEquals(90, financialContract.getLoanOverdueEndDay());
		Assert.assertEquals(90, financialContract.getAdvaRepoTerm());
		Assert.assertEquals(3, financialContract.getAdvaMatuterm());
		Assert.assertEquals("2016-05-04",
				DateUtils.format(financialContract.getAdvaStartDate()));
		Assert.assertEquals("2016-05-20",
				DateUtils.format(financialContract.getThruDate()));
		Account account = financialContract.getCapitalAccount();
		Assert.assertEquals("account_name", account.getAccountName());
		Assert.assertEquals("bank_name", account.getBankName());
		Assert.assertEquals("account_no", account.getAccountNo());

	}


}

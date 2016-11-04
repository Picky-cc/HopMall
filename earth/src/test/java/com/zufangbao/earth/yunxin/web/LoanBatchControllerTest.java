package com.zufangbao.earth.yunxin.web;


import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.ModelAndView;

import com.demo2do.core.web.resolver.Page;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.service.PrincipalService;
import com.zufangbao.earth.yunxin.web.controller.LoanBatchController;
import com.zufangbao.sun.entity.financial.LoanBatch;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
		"classpath:/DispatcherServlet.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional()
public class LoanBatchControllerTest extends
AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private PrincipalService principalService;
	
	private LoanBatchController  loanBatchController;
	
	@Before
	public void init() {
		loanBatchController = this.applicationContext
				.getBean(LoanBatchController.class);
	}
	@Test
	@Sql("classpath:test/testLoanBatch.sql")
	public void testLoanBatch(){
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setQueryString("ashd&sabdlj");
		Principal principal = principalService.load(Principal.class, 1L);
		Page page = new Page();
		page.setEveryPage(12);
		ModelAndView modelAndView = loanBatchController.loadAll(principal, page,request);
		Map<String,Object> map = modelAndView.getModel();
		List<LoanBatch> loanBatchVOList = (List<LoanBatch>)map.get("loanBatchList");
		Assert.assertEquals("DCF-NFQ-LR903A 20160511 18:09:322", loanBatchVOList.get(0).getCode());
	}
	
}

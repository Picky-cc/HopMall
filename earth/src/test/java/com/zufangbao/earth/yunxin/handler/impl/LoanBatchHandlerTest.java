package com.zufangbao.earth.yunxin.handler.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.demo2do.core.web.resolver.Page;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.service.PrincipalService;
import com.zufangbao.earth.yunxin.handler.LoanBatchHandler;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.financial.LoanBatch;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.model.loanbatch.LoanBatchQueryModel;
import com.zufangbao.sun.yunxin.entity.model.loanbatch.LoanBatchShowModel;
import com.zufangbao.sun.yunxin.service.LoanBatchService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@Transactional
public class LoanBatchHandlerTest {

	@Autowired
	private LoanBatchService loanBatchService;
	@Autowired
	private LoanBatchHandler loanBatchHandler;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private ContractService contractService;
	@Autowired 
	private PrincipalService principalService;

	@Test
	@Sql("classpath:test/yunxin/LoanBatch/testGenerateLoanBatchVoList.sql")
	public void testGenerateLoanBatchVoList() {
		LoanBatchQueryModel queryModel = new LoanBatchQueryModel();
		queryModel.setFinancialContractUuids("[\"1\"]");
		List<LoanBatchShowModel> loanBatchVOlist = loanBatchHandler
				.generateLoanBatchShowModelList(queryModel, new Page(0,12));
		Assert.assertEquals(1, loanBatchVOlist.size());
		Assert.assertEquals(new String("云信信2016-78-DK(ZQ2016042522479)"),
				loanBatchVOlist.get(0).getLoanBatchFromRange());
		Assert.assertEquals(new String("云信信2016-78-DK(ZQ2016042522479)"),
				loanBatchVOlist.get(0).getLoanBatchThruRange());
		Assert.assertEquals(new String("1200.00"), loanBatchVOlist.get(0)
				.getLoanBatchTotalAmount());
		Assert.assertEquals(new String("DCF-NFQ-LR903A 20160518 15:14:100"),
				loanBatchVOlist.get(0).getLoanBatchCode());
	}

	@Test
	@Sql("classpath:test/yunxin/LoanBatch/testDeleteLoanBatchData.sql")
	public void testDeleteLoanBatchData() {
		try{
		Principal principal = principalService.load(Principal.class, 1l);
		loanBatchHandler.deleteLoanBatchData(principal,"0.0.0.0.0",1l);
		loanBatchService.loadAll(LoanBatch.class);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		Assert.assertEquals(0, loanBatchService.loadAll(LoanBatch.class).size());
		Assert.assertEquals(0, repaymentPlanService.loadAll(AssetSet.class).size());
		Assert.assertEquals(0, contractService.loadAll(Contract.class).size());
		
	}
}

package com.zufangbao.sun.yunxin.handler;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.model.assetset.AssetSetQueryModel;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
})
public class RepaymentPlanHandlerTest extends AbstractTransactionalJUnit4SpringContextTests{
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private RepaymentPlanHandler repaymentPlanHandler;
	@Autowired
	private ContractService contractService;
	@Test
	@Sql("classpath:test/yunxin/modifyRepaymentPlan/testGetMaxVersion.sql")
	public void testGetMaxVersionNo(){
		Contract contract = contractService.getContract(1L);
		Integer maxVersionNo = repaymentPlanService.getMaxVersionNo(contract);
		assertEquals(new Integer(3),maxVersionNo);
	}
	
	@Test
	@Sql("classpath:test/yunxin/modifyRepaymentPlan/testGetMaxVersion.sql")
	public void testGetMaxVersionNoForNoneContract(){
		Contract contract = null;
		Integer maxVersionNo = repaymentPlanService.getMaxVersionNo(contract);
		assertEquals(new Integer(0),maxVersionNo);
		
	}
	@Test
	@Sql("classpath:test/yunxin/modifyRepaymentPlan/testGetMaxVersion.sql")
	public void testUpdateContractActiveVersionNo(){
		Contract contract = contractService.getContract(1L);
		Integer activeVersionNo = contractService.getActiveVersionNo(contract.getUuid());
		repaymentPlanHandler.updateContractActiveVersionNo(contract, activeVersionNo);
		Contract contractInDb = contractService.getContract(1L);
		assertEquals(4,contractInDb);
	}
	
	@Test
	@Sql("classpath:test/yunxin/repaymentplan/testQueryAssetSetIds_querymodelIsNull.sql")
	public void testQueryAssetSetIds_querymodelIsNoparam(){
		
		
		AssetSetQueryModel assetSetQueryModel = new AssetSetQueryModel();
		assetSetQueryModel.setFinancialContractIds("[1]");
		List<AssetSet> responseResults  = repaymentPlanHandler.queryAssetSetIds(assetSetQueryModel, null);
		assertEquals(2, responseResults.size());
		
		AssetSet result1 = responseResults.get(0);
		assertEquals("ZC2730FAE4092E0A6E", result1.getSingleLoanContractNo() );
		
		AssetSet result2 = responseResults.get(1);
		assertEquals("ZC2730FAE4095260A1", result2.getSingleLoanContractNo() );
	}
	
	@Test
	public void test_Is_exsit_processing_or_success_Repaymentplan_contract_is_null() {
		try {
			boolean result = repaymentPlanHandler.is_exsit_processing_or_success_RepaymentPlan(Collections.emptyList());
			org.junit.Assert.assertFalse(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}

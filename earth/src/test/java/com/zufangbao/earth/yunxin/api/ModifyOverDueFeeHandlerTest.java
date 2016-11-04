package com.zufangbao.earth.yunxin.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.model.modify.ModifyOverDueFeeDetail;
import com.zufangbao.earth.yunxin.api.model.modify.ModifyOverDueFeeRequestModel;
import com.zufangbao.earth.yunxin.handler.modifyOverDueFee.ModifyOverDueFeeHandler;
import com.zufangbao.sun.ledgerbook.ChartOfAccount;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.service.RepaymentPlanExtraChargeService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class ModifyOverDueFeeHandlerTest {

	
	@Autowired
	private ModifyOverDueFeeHandler modifyOverDueFeeHandler;
	@Autowired
	private RepaymentPlanExtraChargeService repaymentPlanExtraChargeService;
	
	@Autowired
	private  RepaymentPlanService repaymentPlanService;
	
	
	@Test
	@Sql("classpath:test/yunxin/modifyOverDueFee/tetsModifyOverDueFeeHandlerSuccess.sql")
	public void  testModifyOverDueFee(){
			
		ModifyOverDueFeeRequestModel model = new ModifyOverDueFeeRequestModel();
		model.setRequestNo(UUID.randomUUID().toString());
		List<ModifyOverDueFeeDetail> modifyOverDueFeeDetails =new ArrayList<ModifyOverDueFeeDetail>();
		ModifyOverDueFeeDetail modifyOverDueFeeDetail =new ModifyOverDueFeeDetail();
		modifyOverDueFeeDetail.setContractUniqueId("1234567890");
		modifyOverDueFeeDetail.setLateFee("10.00");
		modifyOverDueFeeDetail.setLateOtherCost("0.00");
		modifyOverDueFeeDetail.setLatePenalty("100.00");
		modifyOverDueFeeDetail.setOverDueFeeCalcDate("2016-06-08");
		modifyOverDueFeeDetail.setPenaltyFee("90.00");
		modifyOverDueFeeDetail.setRepaymentPlanNo("ZC27375ACFF4234805");
		modifyOverDueFeeDetail.setTotalOverdueFee("140.00");
		modifyOverDueFeeDetails.add(modifyOverDueFeeDetail);
		model.setModifyOverDueFeeDetailsParseJson(modifyOverDueFeeDetails);
		System.out.println(JsonUtils.toJsonString(modifyOverDueFeeDetails));
		
		
		modifyOverDueFeeHandler.modifyOverDueFeeAndCheckData(model, null);
		AssetSet repaymentPlan  = repaymentPlanService.getRepaymentPlanByRepaymentCode( modifyOverDueFeeDetail.getRepaymentPlanNo());
		Map<String, BigDecimal> amountMap = repaymentPlanExtraChargeService.getAssetSetExtraChargeModels(repaymentPlan.getAssetUuid());
		
		Assert.assertEquals(new BigDecimal("90.00"), amountMap.get(ChartOfAccount.SND_RECIEVABLE_LOAN_PENALTY));
		Assert.assertEquals(new BigDecimal("100.00"),amountMap.get(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OBLIGATION));
		Assert.assertEquals(new BigDecimal("10.00"),amountMap.get(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_SERVICE_FEE));
		Assert.assertEquals(new BigDecimal("0.00"),amountMap.get(ChartOfAccount.TRD_RECIEVABLE_OVERDUE_FEE_OTHER_FEE));
		
	}
	

	@Test
	@Sql("classpath:test/yunxin/modifyOverDueFee/testModifyOverDueFeeRepaymentPlanNotInContract.sql")
	public void  testModifyOverDueFeeRepaymentPlanNotInContract(){
			
		ModifyOverDueFeeRequestModel model = new ModifyOverDueFeeRequestModel();
		model.setRequestNo(UUID.randomUUID().toString());
		List<ModifyOverDueFeeDetail> modifyOverDueFeeDetails =new ArrayList<ModifyOverDueFeeDetail>();
		ModifyOverDueFeeDetail modifyOverDueFeeDetail =new ModifyOverDueFeeDetail();
		modifyOverDueFeeDetail.setContractUniqueId("1234567890");
		modifyOverDueFeeDetail.setLateFee("0.00");
		modifyOverDueFeeDetail.setLateOtherCost("0.00");
		modifyOverDueFeeDetail.setLatePenalty("100.00");
		modifyOverDueFeeDetail.setOverDueFeeCalcDate("2016-06-08");
		modifyOverDueFeeDetail.setPenaltyFee("40.00");
		modifyOverDueFeeDetail.setRepaymentPlanNo("ZC27375ACFF4234805TEST");
		modifyOverDueFeeDetail.setTotalOverdueFee("140.00");
		modifyOverDueFeeDetails.add(modifyOverDueFeeDetail);
		try {
			model.setModifyOverDueFeeDetailsParseJson(modifyOverDueFeeDetails);
			modifyOverDueFeeHandler.modifyOverDueFeeAndCheckData(model, null);
		} catch (ApiException e) {
			// TODO: handle exception
			System.out.println("a");
			Assert.assertEquals(22203, e.getCode());
		}
	}

	
	@Test
	@Sql("classpath:test/yunxin/modifyOverDueFee/testModifyOverDueFeeOverDueFeeCalcDateAfterCalcOverDueFee.sql")
	public void  testModifyOverDueFeeOverDueFeeCalcDateAfterCalcOverDueFee(){
			
		ModifyOverDueFeeRequestModel model = new ModifyOverDueFeeRequestModel();
		model.setRequestNo(UUID.randomUUID().toString());
		List<ModifyOverDueFeeDetail> modifyOverDueFeeDetails =new ArrayList<ModifyOverDueFeeDetail>();
		ModifyOverDueFeeDetail modifyOverDueFeeDetail =new ModifyOverDueFeeDetail();
		modifyOverDueFeeDetail.setContractUniqueId("1234567890");
		modifyOverDueFeeDetail.setLateFee("0.00");
		modifyOverDueFeeDetail.setLateOtherCost("0.00");
		modifyOverDueFeeDetail.setLatePenalty("100.00");
		modifyOverDueFeeDetail.setOverDueFeeCalcDate("2016-06-02");
		modifyOverDueFeeDetail.setPenaltyFee("40.00");
		modifyOverDueFeeDetail.setRepaymentPlanNo("ZC27375ACFF4234805");
		modifyOverDueFeeDetail.setTotalOverdueFee("140.00");
		modifyOverDueFeeDetails.add(modifyOverDueFeeDetail);
		try {
			model.setModifyOverDueFeeDetailsParseJson(modifyOverDueFeeDetails);
			modifyOverDueFeeHandler.modifyOverDueFeeAndCheckData(model, null);
		} catch (ApiException e) {
			// TODO: handle exception
			Assert.assertEquals(23100, e.getCode());
		}
		
		
	}
}

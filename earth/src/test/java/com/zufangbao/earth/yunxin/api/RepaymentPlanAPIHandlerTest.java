package com.zufangbao.earth.yunxin.api;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.exception.ApiResponseCode;
import com.zufangbao.earth.yunxin.api.handler.RepaymentPlanApiHandler;
import com.zufangbao.earth.yunxin.api.model.query.RepaymentPlanDetail;
import com.zufangbao.earth.yunxin.api.model.query.RepaymentPlanQueryModel;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@Transactional
public class RepaymentPlanAPIHandlerTest {

	@Autowired
	private RepaymentPlanApiHandler repaymentPlanAPIHandler;
	
	@Test
	@Sql("classpath:test/yunxin/api/testQueryRepaymentPlanDetail.sql")
	public void testQueryRepaymentPlanDetail(){
		String requestParamInfo = new  String("{\"contractNo\":\"云信信2016-78-DK(ZQ2016042522479)\",\"requestNo\":\"123566\",\"uniqueId\":\"\"}");
		RepaymentPlanQueryModel queryModel = JsonUtils.parse(requestParamInfo, RepaymentPlanQueryModel.class);
		List<RepaymentPlanDetail> repaymentPlanDetails = repaymentPlanAPIHandler.queryRepaymentPlanDetail(queryModel);
	
		//asset2 为已作废的asset
		Assert.assertEquals(1, repaymentPlanDetails.size());
		Assert.assertEquals("ZC27367D23EF4A36F4", repaymentPlanDetails.get(0).getRepaymentNumber());
		Assert.assertEquals("2016-05-17", repaymentPlanDetails.get(0).getPlanRepaymentDate());
		Assert.assertEquals("0.00", repaymentPlanDetails.get(0).getPlanRepaymentPrincipal());
		Assert.assertEquals("1200.00", repaymentPlanDetails.get(0).getPlanRepaymentInterest());
		Assert.assertEquals("还款异常", repaymentPlanDetails.get(0).getRepaymentExecutionState());
		Assert.assertEquals("0", repaymentPlanDetails.get(0).getTechnicalServicesFees());
		Assert.assertEquals("0", repaymentPlanDetails.get(0).getOtherFees());
		Assert.assertEquals("0", repaymentPlanDetails.get(0).getLoanFees());
	}
	
	@Test
	@Sql("classpath:test/yunxin/api/testQueryRepaymentPlanDetailError_HaveUniqueIdAndContractNo.sql")
	public  void testQueryRepaymentPlanDetailError_HaveUniqueIdAndContractNo(){
		String requestParamInfo = new String("{\"contractNo\":\"云信信2016-78-DK(ZQ2016042522479)\",\"requestNo\":\"123566\",\"uniqueId\":\"12345789\"}");
		RepaymentPlanQueryModel queryModel = JsonUtils.parse(requestParamInfo, RepaymentPlanQueryModel.class);
		Assert.assertFalse(queryModel.isValid());
	} 
	
	@Test
	@Sql("classpath:test/yunxin/api/testQueryRepaymentPlanDetailError_noRequestNo.sql")
	public  void testQueryRepaymentPlanDetailError_noRequestNo(){
		String requestParamInfo = new String("{\"contractNo\":\"云信信2016-78-DK(ZQ2016042522479)\",\"requestNo\":\"\",\"uniqueId\":\"\"}");
		RepaymentPlanQueryModel queryModel = JsonUtils.parse(requestParamInfo, RepaymentPlanQueryModel.class);
		Assert.assertFalse(queryModel.isValid());
	} 
	
	@Test
	@Sql("classpath:test/yunxin/api/testQueryRepaymentPlanDetailError_No_data.sql")
	public  void testQueryRepaymentPlanDetailError_No_data(){
		try {
			String requestParamInfo = new  String("{\"contractNo\":\"云信信2016-78-DK(ZQ2016042522479)\",\"requestNo\":\"123566\",\"uniqueId\":\"\"}");
			RepaymentPlanQueryModel queryModel = JsonUtils.parse(requestParamInfo, RepaymentPlanQueryModel.class);
			repaymentPlanAPIHandler.queryRepaymentPlanDetail(queryModel);
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertEquals(ApiResponseCode.CONTRACT_NOT_EXIST, e.getCode());
		}
	
	}
	@Test
	@Sql("classpath:test/yunxin/api/testQueryRepaymentPlanDetailError_justHaveUniqueId.sql")
	public  void testQueryRepaymentPlanDetailError_justHaveUniqueId(){
			String requestParamInfo = new  String("{\"contractNo\":\"\",\"requestNo\":\"123566\",\"uniqueId\":\"1234567\"}");
			RepaymentPlanQueryModel queryModel = JsonUtils.parse(requestParamInfo, RepaymentPlanQueryModel.class);
			List<RepaymentPlanDetail> repaymentPlanDetails = repaymentPlanAPIHandler.queryRepaymentPlanDetail(queryModel);
			Assert.assertEquals(1, repaymentPlanDetails.size());
			Assert.assertEquals("ZC27367D23EF4A36F4", repaymentPlanDetails.get(0).getRepaymentNumber());
			Assert.assertEquals("2016-05-17", repaymentPlanDetails.get(0).getPlanRepaymentDate());
			Assert.assertEquals("0.00", repaymentPlanDetails.get(0).getPlanRepaymentPrincipal());
			Assert.assertEquals("1200.00", repaymentPlanDetails.get(0).getPlanRepaymentInterest());
			Assert.assertEquals("还款异常", repaymentPlanDetails.get(0).getRepaymentExecutionState());
			Assert.assertEquals("0", repaymentPlanDetails.get(0).getTechnicalServicesFees());
			Assert.assertEquals("0", repaymentPlanDetails.get(0).getOtherFees());
			Assert.assertEquals("0", repaymentPlanDetails.get(0).getLoanFees());		
	}
	
	
	
	@Test
	@Sql("classpath:test/yunxin/api/testQueryRepaymentPlanDetail.sql")
	public void testQueryRepaymentPlanDetail_testPenaltyFee(){
		String requestParamInfo = new  String("{\"contractNo\":\"云信信2016-78-DK(ZQ2016042522479)\",\"requestNo\":\"123566\",\"uniqueId\":\"\"}");
		RepaymentPlanQueryModel queryModel = JsonUtils.parse(requestParamInfo, RepaymentPlanQueryModel.class);
		List<RepaymentPlanDetail> repaymentPlanDetails = repaymentPlanAPIHandler.queryRepaymentPlanDetail(queryModel);
	
		//asset2 为已作废的asset
		Assert.assertEquals(1, repaymentPlanDetails.size());
		Assert.assertEquals("ZC27367D23EF4A36F4", repaymentPlanDetails.get(0).getRepaymentNumber());
		Assert.assertEquals("2016-05-17", repaymentPlanDetails.get(0).getPlanRepaymentDate());
		Assert.assertEquals("0.00", repaymentPlanDetails.get(0).getPlanRepaymentPrincipal());
		Assert.assertEquals("1200.00", repaymentPlanDetails.get(0).getPlanRepaymentInterest());
		Assert.assertEquals("还款异常", repaymentPlanDetails.get(0).getRepaymentExecutionState());
		Assert.assertEquals("0", repaymentPlanDetails.get(0).getTechnicalServicesFees());
		Assert.assertEquals("0", repaymentPlanDetails.get(0).getOtherFees());
		Assert.assertEquals("0", repaymentPlanDetails.get(0).getLoanFees());
		Assert.assertEquals("0", repaymentPlanDetails.get(0).getPenaltyFee());
	}

}

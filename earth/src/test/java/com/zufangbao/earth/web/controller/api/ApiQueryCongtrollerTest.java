package com.zufangbao.earth.web.controller.api;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.earth.yunxin.api.controller.QueryApiController;
import com.zufangbao.earth.yunxin.api.model.ApiResult;
import com.zufangbao.earth.yunxin.api.model.query.RepaymentPlanDetail;
import com.zufangbao.earth.yunxin.api.model.query.RepaymentPlanQueryModel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
		"classpath:/DispatcherServlet.xml" })
@Transactional()
public class ApiQueryCongtrollerTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private QueryApiController queryApiController;

	
	@Test
	@Sql("classpath:test/yunxin/api/testQueryRepaymentPlanController.sql")
	public void testQueryRepaymentPlanController() {

		RepaymentPlanQueryModel queryModel = new RepaymentPlanQueryModel();

		queryModel.setContractNo("云信信2016-78--DK(ZQ2016042522479)");
		queryModel.setRequestNo("123566");
		queryModel.setUniqueId("");
		// FIXME add response
		MockHttpServletResponse response = new MockHttpServletResponse();
		String RTNContent = queryApiController.queryRepaymentPlan(queryModel, response);

		ApiResult apiResult = JsonUtils.parse(RTNContent, ApiResult.class);
		Assert.assertEquals(0, apiResult.getCode());
		Assert.assertEquals("成功!", apiResult.getMessage());
		String repaymentPlanDetailsStr = apiResult.getData()
				.get("repaymentPlanDetails").toString();
		List<RepaymentPlanDetail> repaymentPlanDetails = JsonUtils.parseArray(
				repaymentPlanDetailsStr, RepaymentPlanDetail.class);
		Assert.assertEquals(1, repaymentPlanDetails.size());
		Assert.assertEquals("ZC27367D23EF4A36F4", repaymentPlanDetails.get(0)
				.getRepaymentNumber());
		Assert.assertEquals("2016-05-17", repaymentPlanDetails.get(0)
				.getPlanRepaymentDate());
		Assert.assertEquals("0.00", repaymentPlanDetails.get(0)
				.getPlanRepaymentPrincipal());
		Assert.assertEquals("1200.00", repaymentPlanDetails.get(0)
				.getPlanRepaymentInterest());
		Assert.assertEquals("还款异常", repaymentPlanDetails.get(0)
				.getRepaymentExecutionState());
		Assert.assertEquals("", repaymentPlanDetails.get(0)
				.getTechnicalServicesFees());
		Assert.assertEquals("", repaymentPlanDetails.get(0).getOtherFees());
		Assert.assertEquals("", repaymentPlanDetails.get(0).getLoanFees());
	}

	@Test
	@Sql("classpath:test/yunxin/api/testQueryRepaymentPlanController.sql")
	public void testQueryRepaymentPlanApi() {

		RepaymentPlanQueryModel queryModel = new RepaymentPlanQueryModel();

		queryModel.setUniqueId("4c05b1ea-fc25-47eb-9c76-dcabd0271e1e");
		queryModel.setRequestNo("123566");
		queryModel.setUniqueId("");
		// FIXME add response
		MockHttpServletResponse response = new MockHttpServletResponse();
		String RTNContent = queryApiController.queryRepaymentPlan(queryModel, response);

		ApiResult apiResult = JsonUtils.parse(RTNContent, ApiResult.class);
		Assert.assertEquals(21001, apiResult.getCode());
		Assert.assertEquals("贷款合同不存在", apiResult.getMessage());
	}
	
	@Test
	@Sql("classpath:test/yunxin/api/testQueryRepaymentPlanApi_noRequestNo.sql")
	public void testQueryRepaymentPlanApi_noRequestNo() {

		RepaymentPlanQueryModel queryModel = new RepaymentPlanQueryModel();

		queryModel.setContractNo("云信信2016-78--DK(ZQ2016042522479)");
		queryModel.setRequestNo("");
		queryModel.setUniqueId("");
		// FIXME add response
		MockHttpServletResponse response = new MockHttpServletResponse();
		String RTNContent = queryApiController.queryRepaymentPlan(queryModel, response);

		ApiResult apiResult = JsonUtils.parse(RTNContent, ApiResult.class);
		Assert.assertEquals(20001, apiResult.getCode());
	}
	@Test
	@Sql("classpath:test/yunxin/api/testQueryRepaymentPlanApi_data_error.sql")
	public void testQueryRepaymentPlanApi_data_error() {

		RepaymentPlanQueryModel queryModel = new RepaymentPlanQueryModel();

		queryModel.setContractNo("云信信2016-78--DK(ZQ2016042522479)");
		queryModel.setRequestNo("1234567");
		queryModel.setUniqueId("123");
		// FIXME add response
		MockHttpServletResponse response = new MockHttpServletResponse();
		String RTNContent = queryApiController.queryRepaymentPlan(queryModel, response);

		ApiResult apiResult = JsonUtils.parse(RTNContent, ApiResult.class);
		Assert.assertEquals(20001, apiResult.getCode());
	}
	@Test
	@Sql("classpath:test/yunxin/api/testQueryRepaymentPlanApi_no_data.sql")
	public void testQueryRepaymentPlanApi_no_data() {

		RepaymentPlanQueryModel queryModel = new RepaymentPlanQueryModel();

		queryModel.setContractNo("");
		queryModel.setRequestNo("1234");
		queryModel.setUniqueId("123");
		// FIXME add response
		MockHttpServletResponse response = new MockHttpServletResponse();
		String RTNContent = queryApiController.queryRepaymentPlan(queryModel, response);

		ApiResult apiResult = JsonUtils.parse(RTNContent, ApiResult.class);
		Assert.assertEquals(21001, apiResult.getCode());
	}
	
}

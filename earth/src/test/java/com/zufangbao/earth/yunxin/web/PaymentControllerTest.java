package com.zufangbao.earth.yunxin.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;
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

import com.alibaba.fastjson.JSON;
import com.demo2do.core.entity.Result;
import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.JsonUtils;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.service.PrincipalService;
import com.zufangbao.earth.yunxin.web.controller.YunxinPaymentController;
import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetValuationDetail;
import com.zufangbao.sun.yunxin.entity.AssetValuationSubject;
import com.zufangbao.sun.yunxin.entity.ExecutingDeductStatus;
import com.zufangbao.sun.yunxin.entity.ExecutingSettlingStatus;
import com.zufangbao.sun.yunxin.entity.OverDueStatus;
import com.zufangbao.sun.yunxin.entity.PaymentWay;
import com.zufangbao.sun.yunxin.entity.model.OrderQueryModel;
import com.zufangbao.sun.yunxin.entity.model.OrderViewDetail;
import com.zufangbao.sun.yunxin.entity.model.TransferApplicationQueryModel;
import com.zufangbao.sun.yunxin.handler.AssetValuationDetailHandler;
import com.zufangbao.sun.yunxin.service.AssetValuationDetailService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
		"classpath:/DispatcherServlet.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional()
public class PaymentControllerTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private PrincipalService principalService;

	private YunxinPaymentController yunxinPaymentController;

	@Autowired
	private AssetValuationDetailService assetValuationDetailService;
	
	@Autowired
	private AssetValuationDetailHandler assetValuationDetailHandler;
	
	@Autowired
	private OrderService orderService;

	private MockHttpServletRequest request = new MockHttpServletRequest();

	private Principal principal = new Principal();

	@Autowired
	private RepaymentPlanService repaymentPlanService;
	
	@Before
	public void init() {
		yunxinPaymentController = this.applicationContext
				.getBean(YunxinPaymentController.class);
	}

	@Test
	@Sql("classpath:test/yunxin/testPaymentNoStatusList.sql")
	public void testPaymentNoStatusList() {

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setQueryString("ashd&sabdlj");

		String paymentWay = "0";
		String executingDeductStatus = "1";
		String AccountName = "payer_name_1";
		String paymentNo = "DKHD-001-01-20160308-1910";
		String repaymentNo = "DKHD-1-01";
		String billingNo = "DKHD-001-01-20160308";
		String payAcNo = "pay_ac_no_1";
		String startDate = "2016-10-10";
		String endDate = "2016-10-10";
		Principal principal = principalService.load(Principal.class, 1l);
		Page page = new Page(1, 12);
		TransferApplicationQueryModel transferApplicationQueryModel = new TransferApplicationQueryModel(
				Integer.valueOf(paymentWay).intValue(), Integer.valueOf(
						executingDeductStatus).intValue(), AccountName,
				paymentNo, repaymentNo, billingNo, payAcNo, startDate, endDate);
		String resultStr = yunxinPaymentController.paymentShowView(
				request, transferApplicationQueryModel, principal, page);
		Result result = JSON.parseObject(resultStr, Result.class);
		List<Map<String, Object>> resultList = (List<Map<String, Object>>) result.getData().get("list");
		Map<String, Object> resultMap = resultList.get(0);
		Assert.assertEquals(new String("payer_name_1"), resultMap.get("contractAccount.payerName"));
		Assert.assertEquals(new String("DKHD-001-01-20160308-1910"),resultMap.get("transferApplicationNo"));
		Assert.assertEquals(new BigDecimal("1.00"), resultMap.get("amount"));
		Assert.assertEquals(new String("DKHD-001-01-20160308"),resultMap.get("order.orderNo"));
		assertEquals(new String("处理中"),resultMap.get("executingDeductStatusMsg"));
		Assert.assertEquals(new String("中国银行滨江支行"), resultMap.get("contractAccount.bank"));
	}

	@Test
	@Sql("classpath:test/yunxin/testPaymentList.sql")
	public void testPaymentList() {

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setQueryString("ashd&sabdlj");

	
		String AccountName = "payer_name_1";
		String paymentNo = "zxcvbnm";
		String repaymentNo = "DKHD-1-01";
		String billingNo = "DKHD-001-01-20160308";
		String bankNo = "pay_ac_no_1";
		Principal principal = principalService.load(Principal.class, 1l);
		Page page = new Page(1, 12);
		TransferApplicationQueryModel transferApplicationQueryModel = new TransferApplicationQueryModel(
				-1, -1, AccountName, paymentNo, repaymentNo, billingNo, bankNo, "", "");
		String resultStr = yunxinPaymentController.paymentShowView(
				request, transferApplicationQueryModel, principal, page);
		Result result = JSON.parseObject(resultStr, Result.class);
		List<Map<String, Object>> resultList = (List<Map<String, Object>>) result.getData().get("list");
		Map<String, Object> resultMap = resultList.get(0);
		Assert.assertEquals(new String("payer_name_1"), resultMap.get("contractAccount.payerName"));
		Assert.assertEquals(new String("zxcvbnm"),resultMap.get("transferApplicationNo"));
		Assert.assertEquals(new BigDecimal("1.00"), resultMap.get("amount"));
		assertEquals(new String("处理中"),resultMap.get("executingDeductStatusMsg"));
		Assert.assertEquals(new String("DKHD-001-01-20160308"),resultMap.get("order.orderNo"));
		Assert.assertEquals(new String("中国银行滨江支行"), resultMap.get("contractAccount.bank"));
	}

	@Test
	@Sql("classpath:test/yunxin/testPaymentDetailList.sql")
	public void testPaymentDetailList() {

		Long transferApplicationId = 1L;
		Principal principal = principalService.load(Principal.class, 1l);
		Page page = new Page(1, 12);
		ModelAndView modelAndView = yunxinPaymentController.showOnlinePaymentOrderDetail(
				transferApplicationId, principal,page);
		Map<String, Object> map = modelAndView.getModel();
		TransferApplication transferApplication = (TransferApplication) map
				.get("transferApplication");
		Assert.assertEquals(new String("DKHD-001-01-20160308-1910"),
				transferApplication.getTransferApplicationNo());
		Assert.assertEquals(new String("王二"), transferApplication
				.getContractAccount().getPayerName());
		Assert.assertEquals(new String("2016-03-31 19:10:46.0"),
				transferApplication.getLastModifiedTime().toString());
		Assert.assertEquals(new String("中国银行滨江支行"), transferApplication
				.getContractAccount().getBank());
		Assert.assertEquals(new BigDecimal("1.00"),
				transferApplication.getAmount());
		Assert.assertEquals(ExecutingDeductStatus.CREATE,
				transferApplication.getExecutingDeductStatus());
		Assert.assertEquals(new String("123456789x"), transferApplication
				.getContractAccount().getBindId());
		Assert.assertEquals(PaymentWay.UNIONDEDUCT,
				transferApplication.getPaymentWay());
		Assert.assertEquals(new String("123456789"),
				transferApplication.getUnionPayOrderNo());
		Assert.assertEquals(new String(""), transferApplication.getComment());

	}

	@Test
	@Sql("classpath:test/yunxin/testPaymentNoStatusList.sql")
	public void testPaymentList_endDate_earlier_startDate() {

		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setQueryString("ashd&sabdlj");
		/*
		 * String paymentWay = "0"; String executingDeductStatus = "0";
		 */
		String AccountName = "payer_name_1";
		String paymentNo = "zxcvbnm";
		String repaymentNo = "DKHD-1-01";
		String billingNo = "DKHD-001-01-20160308";
		String bankNo = "pay_ac_no_1";
		String startDate = "2016-03-03";
		String endDate = "2016-03-04";
		Principal principal = principalService.load(Principal.class, 1l);
		Page page = new Page(1, 12);
		TransferApplicationQueryModel transferApplicationQueryModel = new TransferApplicationQueryModel(
				-1, -1, AccountName, paymentNo, repaymentNo, billingNo, bankNo,
				startDate, endDate);
//		ModelAndView modelAndView = yunxinPaymentController.paymentShowView(
//				request, transferApplicationQueryModel, principal, page);
//		Map<String, Object> map = modelAndView.getModel();
//		List<TransferApplication> transferApplications = (List<TransferApplication>) map
//				.get("transferApplicationList");
		
		String resultStr = yunxinPaymentController.paymentShowView(
				request, transferApplicationQueryModel, principal, page);
		Result result = JSON.parseObject(resultStr, Result.class);
		
		List<TransferApplication> transferApplicationList = (List<TransferApplication>) result.getData().get("list");
		Assert.assertEquals(0, transferApplicationList.size());

	}

	@Test
	@Sql("classpath:test/yunxin/settlementOrder/testListOrder.sql")
	public void testListOrder() {
		OrderQueryModel orderQueryModel = new OrderQueryModel();
		Page page = new Page();
		page.setEveryPage(12);

		orderQueryModel.setFinancialContractIds("[1]");
		orderQueryModel.setOrderNo("DKHD-001-02-201");
		orderQueryModel.setOverDueStatus(OverDueStatus.NOT_OVERDUE.ordinal());
		orderQueryModel.setSingleLoanContractNo("DKHD-001-02");
		orderQueryModel.setSettlementStartDateString("2016-10-09");
		orderQueryModel.setSettlementStartDateString("2016-10-09");
		orderQueryModel.setAssetRecycleStartDateString("2016-10-10");
		orderQueryModel.setAssetRecycleEndDateString("2016-10-10");
		String resultStr = yunxinPaymentController.queryOrders(orderQueryModel, page, principal, request);
		Result result = JSON.parseObject(resultStr, Result.class);
		List<Order> orderList = JSON.parseArray(result.get("list").toString(), Order.class);
		assertEquals(1, orderList.size());
		Order order = orderList.get(0);
		assertEquals(new Long(3L), order.getId());
		assertEquals("DKHD-001-02-20160409", order.getOrderNo());
		assertEquals("DKHD-001-02", order.getSingleLoanContractNo());
		assertEquals("2016-10-10",
				DateUtils.format(order.getAssetRecycleDate()));
		assertEquals("D001", order.getCustomerNo());
		assertEquals(0,
				new BigDecimal("1000").compareTo(order.getAssetInitialValue()));
		assertEquals(0, BigDecimal.ZERO.compareTo(order.getPenaltyAmount()));
		assertEquals(new Integer(0), order.getNumbersOfOverdueDays());
		assertEquals("2016-03-19", DateUtils.format(order.getModifyTime()));
		assertEquals(0, new BigDecimal("1000").compareTo(order.getTotalRent()));
		assertEquals(ExecutingSettlingStatus.DOING,
				order.getExecutingSettlingStatus());
	}
	
	@Test
	@Sql("classpath:test/yunxin/settlementOrder/testListOrder.sql")
	public void testQueryOrders(){
		OrderQueryModel orderQueryModel = new OrderQueryModel();
		Page page = new Page();
		page.setEveryPage(12);
		
		orderQueryModel.setFinancialContractIds("[1]");
		orderQueryModel.setOrderNo("DKHD-001-02-201");
		orderQueryModel.setOverDueStatus(OverDueStatus.NOT_OVERDUE.ordinal());
		orderQueryModel.setSingleLoanContractNo("DKHD-001-02");
		orderQueryModel.setSettlementStartDateString("2016-10-09");
		orderQueryModel.setSettlementStartDateString("2016-10-09");
		orderQueryModel.setAssetRecycleStartDateString("2016-10-10");
		orderQueryModel.setAssetRecycleEndDateString("2016-10-10");
		String resultJson = yunxinPaymentController.queryOrders(orderQueryModel, page, principal, request);
		Result result = JsonUtils.parse(resultJson,Result.class);
		assertTrue(result.isValid());
		List<Order> orderList = JSON.parseArray((result.get("list").toString()),Order.class);

		assertEquals(1, orderList.size());
		Order order = orderList.get(0);
		assertEquals(new Long(3L), order.getId());
		assertEquals("DKHD-001-02-20160409", order.getOrderNo());
		assertEquals("DKHD-001-02", order.getSingleLoanContractNo());
		assertEquals("2016-10-10",
				DateUtils.format(order.getAssetRecycleDate()));
		assertEquals("D001", order.getCustomerNo());
		assertEquals(0,
				new BigDecimal("1000").compareTo(order.getAssetInitialValue()));
		assertEquals(0, BigDecimal.ZERO.compareTo(order.getPenaltyAmount()));
		assertEquals(new Integer(0), order.getNumbersOfOverdueDays());
		assertEquals("2016-03-19", DateUtils.format(order.getModifyTime()));
		assertEquals(0, new BigDecimal("1000").compareTo(order.getTotalRent()));
		assertEquals(ExecutingSettlingStatus.DOING,
				order.getExecutingSettlingStatus());
	}

	@Test
	@Sql("classpath:test/yunxin/settlementOrder/testOrderDetailView.sql")
	public void testShowOrderDetail() {
		
		
		Page page = new Page(1, 12);
		ModelAndView modelAndView = yunxinPaymentController.showOrderDetail(1L,
				principal, request,page);
		assertEquals("yunxin/payment/order-show-detail",
				modelAndView.getViewName());

		OrderViewDetail orderDetail = (OrderViewDetail) modelAndView.getModel()
				.get("orderViewDetail");
		assertNotNull(orderDetail);
		Order orderView = orderDetail.getOrder();
		assertNotNull(orderView);
		assertEquals(new Long(1L), orderView.getId());
		assertEquals("DKHD-001-01-20160307", orderView.getOrderNo());
		Customer customer = orderDetail.getCustomer();
		assertEquals("D001", customer.getSource());
		ContractAccount contractAccount = orderDetail.getContractAccount();
		assertEquals("payer_name_1", contractAccount.getPayerName());
		assertEquals("pay_ac_no_1", contractAccount.getPayAcNo());
		assertEquals("bind_id", contractAccount.getBindId());
	}

	@Test
	@Sql("classpath:test/yunxin/settlementOrder/testOrderDetailView.sql")
	public void testShowOrderDetailForValid() {
		Page page = new Page(1, 12);
		ModelAndView modelAndView = yunxinPaymentController.showOrderDetail(1L,
				principal, request,page);
		assertEquals("yunxin/payment/order-show-detail",
				modelAndView.getViewName());
		assertNotNull(modelAndView.getModel().get("orderViewDetail"));

	}
	
	@Test
	@Sql("classpath:test/yunxin/editOrder/edit_order_amount_and_value_asset.sql")
	public void testPreEditOrder(){
		String repaymentBillUuid = "repayment_bill_id_1";
		ModelAndView modelAndView = yunxinPaymentController.preEditOrder(repaymentBillUuid, principal, request);
		assertEquals("error-modal",modelAndView.getViewName());
		
		repaymentBillUuid = "repayment_bill_id_2";
		modelAndView = yunxinPaymentController.preEditOrder(repaymentBillUuid, principal, request);
		assertEquals("error-modal",modelAndView.getViewName());
		
		repaymentBillUuid = "repayment_bill_id_3";
		modelAndView = yunxinPaymentController.preEditOrder(repaymentBillUuid, principal, request);
		assertEquals("yunxin/payment/order-pre-edit",modelAndView.getViewName());
		Order order = (Order)modelAndView.getModel().get("order");
		
		assertEquals(repaymentBillUuid,order.getRepaymentBillId());
	}
	
	@Test
	@Sql("classpath:test/yunxin/editOrder/edit_order_amount_and_value_asset.sql")
	public void testEditOrder(){
		String penaltyAmount = "5";
		String repaymentBillUuid = "repayment_bill_id_3";
		Long id_3 = 3L;
		String comment = "comment1";
		String resultJson = yunxinPaymentController.editOrder(id_3, principal, request, penaltyAmount, comment);
		Result result = JsonUtils.parse(resultJson,Result.class);
		assertTrue(result.isValid());
		
		AssetSet assetSet_after = repaymentPlanService.load(AssetSet.class,1L);
		
		assertEquals(0,new BigDecimal ("10005").compareTo(assetSet_after.getAssetFairValue()));
		AssetValuationDetail valuationDetail = assetValuationDetailService.get_asset_valuation_detail_by_asset_set_subject_and_date(assetSet_after, AssetValuationSubject.AMOUNT_ADJUSTMENT, new Date());
		assertNotNull(valuationDetail);
		assertEquals(0,new BigDecimal("-5").compareTo(valuationDetail.getAmount()));
		Order order = orderService.load(Order.class, 3L);
		
		assertEquals(0,new BigDecimal("10005").compareTo(order.getTotalRent()));
		
		List<AssetValuationDetail> assetValuationDetails = assetValuationDetailService.getAssetValuationDetailListByAsset(assetSet_after, new Date());
		String assetValuationDetailIds = assetValuationDetailHandler.extractIdsJsonFrom(assetValuationDetails);
		assertEquals(assetValuationDetailIds,order.getUserUploadParam());
	}
	
	@Test
	@Sql("classpath:test/yunxin/editOrder/edit_order_amount_and_value_asset.sql")
	public void testEditOrderForInvalid(){
		//repaymentBillUuid 不可编辑
		String deltaAmount = "-11";
		String repaymentBillUuid = "repayment_bill_id_1";
		Long id_1 = 1L;
		String comment = "comment1";
		String resultJson = yunxinPaymentController.editOrder(id_1, principal, request, deltaAmount, comment);
		Result result = JsonUtils.parse(resultJson,Result.class);
		assertFalse(result.isValid());
		
		AssetSet assetSet_after = repaymentPlanService.load(AssetSet.class,1L);
		
		assertEquals(0,new BigDecimal ("10010").compareTo(assetSet_after.getAssetFairValue()));
		AssetValuationDetail valuationDetail = assetValuationDetailService.get_asset_valuation_detail_by_asset_set_subject_and_date(assetSet_after, AssetValuationSubject.AMOUNT_ADJUSTMENT, new Date());
		assertNull(valuationDetail);
		Order order = orderService.load(Order.class, 3L);
		
		assertEquals(0,new BigDecimal("10010").compareTo(order.getTotalRent()));
		
		List<AssetValuationDetail> assetValuationDetails = assetValuationDetailService.getAssetValuationDetailListByAsset(assetSet_after, new Date());
		String assetValuationDetailIds = assetValuationDetailHandler.extractIdsJsonFrom(assetValuationDetails);
		assertEquals(assetValuationDetailIds,order.getUserUploadParam());
	}

}

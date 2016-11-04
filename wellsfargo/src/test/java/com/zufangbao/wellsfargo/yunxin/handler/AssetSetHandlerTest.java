package com.zufangbao.wellsfargo.yunxin.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.AutoTestUtils;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetSetActiveStatus;
import com.zufangbao.sun.yunxin.entity.AuditOverdueStatus;
import com.zufangbao.sun.yunxin.entity.ExecutingSettlingStatus;
import com.zufangbao.sun.yunxin.entity.OrderClearStatus;
import com.zufangbao.sun.yunxin.entity.PaymentStatus;
import com.zufangbao.sun.yunxin.entity.excel.OverDueRepaymentDetailExcelVO;
import com.zufangbao.sun.yunxin.entity.excel.RepaymentPlanDetailExcelVO;
import com.zufangbao.sun.yunxin.entity.model.assetset.AssetSetQueryModel;
import com.zufangbao.sun.yunxin.handler.RepaymentPlanHandler;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@Transactional
public class AssetSetHandlerTest {

	@Autowired
	private RepaymentPlanService repaymentPlanService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private RepaymentPlanHandler repaymentPlanHandler;
	
	@Autowired
	private OrderHandler orderHandler;
	
	@Test
	@Sql("classpath:test/yunxin/settlementOrder/testCreateSettlementOrder.sql")
	public void testCreateOrderByAssetSet() {
		AssetSet assetSet = repaymentPlanService.load(AssetSet.class, 2L);
		repaymentPlanHandler.createOrderByAssetSet(assetSet, new Date());
		List<Order> orderList = orderService.getOrderListByAssetSetId(2L);
		assertEquals(2,orderList.size());
		Order order_new = orderList.get(1);
		assertEquals(0,DateUtils.compareTwoDatesOnDay(new Date(),order_new.getDueDate()));
		//assertTrue(order_new.getOrderNo().contains("DKHD-001-02"));
		assertEquals(0,new BigDecimal("1000.00").compareTo(order_new.getTotalRent()));
		assertEquals("[3]",order_new.getUserUploadParam());
		assertEquals(OrderClearStatus.UNCLEAR,order_new.getClearingStatus());
		assertEquals(ExecutingSettlingStatus.CREATE,order_new.getExecutingSettlingStatus());
		assertFalse(StringUtils.isEmpty(order_new.getRepaymentBillId()));
	}
	
	@Test
	@Sql("classpath:test/yunxin/settlementOrder/testCreateSettlementOrder.sql")
	public void testCreateOrderListByAssetSetList(){
		AssetSet assetSet_2 = repaymentPlanService.load(AssetSet.class, 2L);
		repaymentPlanHandler.createOrderByAssetSet(assetSet_2, new Date());
		List<Order> orderList_by_assetSet1 = orderService.getOrderListByAssetSetId(1L);
		assertEquals(2,orderList_by_assetSet1.size());
		
		List<Order> orderList_by_assetSet2 = orderService.getOrderListByAssetSetId(2L);
		assertEquals(2,orderList_by_assetSet2.size());
		Order new_order_by_asset2 = orderList_by_assetSet2.get(1);
		assertEquals(0,DateUtils.compareTwoDatesOnDay(new Date(),new_order_by_asset2.getDueDate()));
		//assertTrue(new_order_by_asset2.getOrderNo().contains("DKHD-001-02"));
		assertEquals(0,new BigDecimal("1000").compareTo(new_order_by_asset2.getTotalRent()));
		assertEquals("[3]",new_order_by_asset2.getUserUploadParam());
		assertEquals(OrderClearStatus.UNCLEAR,new_order_by_asset2.getClearingStatus());
		assertEquals(ExecutingSettlingStatus.CREATE,new_order_by_asset2.getExecutingSettlingStatus());
		assertEquals(new Long(2L),new_order_by_asset2.getAssetSet().getId());
	}
	
	@Test
	@Sql("classpath:test/yunxin/settlementOrder/testCreateSettlementOrder.sql")
	public void testCreateOrderListByAssetSetListForEmptyList(){
		//order鏁版病鏈夊彉
		List<Order> orderList_by_assetSet1 = orderService.getOrderListByAssetSetId(1L);
		assertEquals(2,orderList_by_assetSet1.size());
	}
	
	@Test
	@Sql("classpath:test/yunxin/settlementOrder/testCreateSettlementOrderByTask.sql")
	public void testCreateOrderListByTodayRecycleAsset(){
		List<Order> orderList = orderService.list(Order.class, new Filter());
		assertEquals(4,orderList.size());
		
		List<Long> assetSetIds = repaymentPlanHandler.get_all_allow_valuation_and_create_normal_order_asset_set_list(new Date());
		for (Long assetSetId : assetSetIds) {
			try {
				orderHandler.assetValuationAndCreateOrder(assetSetId, new Date());
			} catch (Exception e) {
				fail();
			}
		}
		
		orderList = orderService.list(Order.class, new Filter());
		assertEquals(5,orderList.size());
		
		
		//璧勪骇2鐢熸垚鏂扮殑order
		List<Order> orderList_byAssetSet2 = orderService.getOrderListByAssetSetId(2L);
		assertEquals(2,orderList_byAssetSet2.size());
		Order new_order_by_Asset2 = orderList_byAssetSet2.get(1);
		assertEquals(0,DateUtils.compareTwoDatesOnDay(new Date(),new_order_by_Asset2.getCreateTime()));
		assertEquals(new Long(2L),new_order_by_Asset2.getAssetSet().getId());
		assertEquals(0,DateUtils.compareTwoDatesOnDay(new Date(),new_order_by_Asset2.getDueDate()));
		assertEquals("[3]",new_order_by_Asset2.getUserUploadParam());
		assertEquals(1,new BigDecimal("1010").compareTo(new_order_by_Asset2.getTotalRent()));
		assertEquals(OrderClearStatus.UNCLEAR,new_order_by_Asset2.getClearingStatus());
		assertEquals(ExecutingSettlingStatus.CREATE,new_order_by_Asset2.getExecutingSettlingStatus());
		//璧勪骇3锛�5娌℃湁鐢熸垚order
		List<Order> orderList_byAssetSet3 = orderService.getOrderListByAssetSetId(3L);
		assertEquals(0,orderList_byAssetSet3.size());
		//璧勪骇4宸叉湁褰撳ぉ鐨刼rder
		List<Order> orderList_byAssetSet4 = orderService.getOrderListByAssetSetId(4L);
		assertEquals(1,orderList_byAssetSet4.size());
		List<Order> orderList_byAssetSet5 = orderService.getOrderListByAssetSetId(5L);
		assertEquals(0,orderList_byAssetSet5.size());
	}
	
	@Test
	@Sql("classpath:test/yunxin/settlementOrder/testRepaymentPlanDetailExcel.sql")
	public void  testrepaymentPlanDetailExcel(){
	
		AssetSetQueryModel assetSetQueryModel =new AssetSetQueryModel();
		assetSetQueryModel.setStartDate("2016-05-17");
		assetSetQueryModel.setEndDate("2016-05-17");
		assetSetQueryModel.setPaymentStatusOrdinals(AutoTestUtils.getOrdinals(PaymentStatus.values()));
		assetSetQueryModel.setAuditOverDueStatusOrdinals(AutoTestUtils.getOrdinals(AuditOverdueStatus.values()));
		assetSetQueryModel.setFinancialContractIds(JsonUtils.toJsonString(Arrays.asList(1l,2L)));
		assetSetQueryModel.setActiveStatus(AssetSetActiveStatus.OPEN.ordinal());
		//杩囨护鎺� invalid asset
		 List<RepaymentPlanDetailExcelVO> repaymentPlanDetailExcels = repaymentPlanHandler.get_repayment_plan_Detail_excel(assetSetQueryModel);
		 assertEquals(2, repaymentPlanDetailExcels.size());
		 RepaymentPlanDetailExcelVO repaymentPlanDetailExcel = repaymentPlanDetailExcels.get(0);
		 assertEquals("nongfenqi", repaymentPlanDetailExcel.getAppName());
		 assertEquals("2400.00", repaymentPlanDetailExcel.getAssetInterestValue());
		 assertEquals("0.00", repaymentPlanDetailExcel.getAssetPrincipalValue());
		 assertEquals("2016-05-17", repaymentPlanDetailExcel.getAssetRecycleDate());
		 assertEquals("中国建设银行", repaymentPlanDetailExcel.getBankName());
		 assertEquals("亳州", repaymentPlanDetailExcel.getCity());
		 assertEquals("测试用户3", repaymentPlanDetailExcel.getCustomerName());
		 assertEquals("sadas", repaymentPlanDetailExcel.getFinancialAccountNo());
		 assertEquals("DCF-NFQ-LR903", repaymentPlanDetailExcel.getFinancialContractNo());
		 assertEquals(null, repaymentPlanDetailExcel.getIdCardNo());
		 assertEquals("云信信2016-78-DK(ZQ2016042522502)", repaymentPlanDetailExcel.getLoanContractNo());
		 assertEquals("2016-04-17", repaymentPlanDetailExcel.getLoanDate());
		 assertEquals("621************3015", repaymentPlanDetailExcel.getPayAcNo());
		 assertEquals("安徽省", repaymentPlanDetailExcel.getProvince());
	}
	
	@Test
	@Sql("classpath:test/yunxin/settlementOrder/testrepaymentPlanDetailExcelForWholeFinancialContractId.sql")
	public void  testrepaymentPlanDetailExcelForWholeFinancialContractId(){
	
		
		AssetSetQueryModel assetSetQueryModel =new AssetSetQueryModel();
		assetSetQueryModel.setStartDate("2016-05-17");
		assetSetQueryModel.setEndDate("2016-08-17");
		assetSetQueryModel.setFinancialContractIds(JsonUtils.toJsonString(Arrays.asList(1L,2L)));
		assetSetQueryModel.setPaymentStatusOrdinals(AutoTestUtils.getOrdinals(PaymentStatus.values()));
		assetSetQueryModel.setAuditOverDueStatusOrdinals(AutoTestUtils.getOrdinals(AuditOverdueStatus.values()));
		 List<RepaymentPlanDetailExcelVO> repaymentPlanDetailExcels = repaymentPlanHandler.get_repayment_plan_Detail_excel(assetSetQueryModel);
		 
		 assertEquals(3, repaymentPlanDetailExcels.size());
		 RepaymentPlanDetailExcelVO repaymentPlanDetailExcel = repaymentPlanDetailExcels.get(0);
		 assertEquals("nongfenqi", repaymentPlanDetailExcel.getAppName());
		 assertEquals("2400.00", repaymentPlanDetailExcel.getAssetInterestValue());
		 assertEquals("0.00", repaymentPlanDetailExcel.getAssetPrincipalValue());
		 assertEquals("2016-05-17", repaymentPlanDetailExcel.getAssetRecycleDate());
		 assertEquals("中国建设银行", repaymentPlanDetailExcel.getBankName());
		 assertEquals("亳州", repaymentPlanDetailExcel.getCity());
		 assertEquals("测试用户3", repaymentPlanDetailExcel.getCustomerName());
		 assertEquals("sadas", repaymentPlanDetailExcel.getFinancialAccountNo());
		 assertEquals("DCF-NFQ-LR903", repaymentPlanDetailExcel.getFinancialContractNo());
		 assertEquals(null, repaymentPlanDetailExcel.getIdCardNo());
		 assertEquals("云信信2016-78-DK(ZQ2016042522502)", repaymentPlanDetailExcel.getLoanContractNo());
		 assertEquals("2016-04-17", repaymentPlanDetailExcel.getLoanDate());
		 assertEquals("621************3015", repaymentPlanDetailExcel.getPayAcNo());
		 assertEquals("安徽省", repaymentPlanDetailExcel.getProvince());
		 
		 
		 RepaymentPlanDetailExcelVO repaymentPlanDetailExcel1 = repaymentPlanDetailExcels.get(2);
		 assertEquals("云信信2016-79-DK(ZQ2016042522502)", repaymentPlanDetailExcel1.getLoanContractNo());
	}
	
	@Test
	@Sql("classpath:test/yunxin/settlementOrder/testOverDueThanFiveRepaymentDetailExcelForAssetSetActiveStatus.sql")
	public void testOverDueForAssetSetActiveStatus(){
		AssetSetQueryModel assetSetQueryModel =new AssetSetQueryModel();
		assetSetQueryModel.setActiveStatus(AssetSetActiveStatus.OPEN.ordinal());
		assetSetQueryModel.setStartDate("2016-05-17");
		assetSetQueryModel.setEndDate("2016-05-17");
		assetSetQueryModel.setPaymentStatusOrdinals(AutoTestUtils.getOrdinals(PaymentStatus.values()));
		assetSetQueryModel.setAuditOverDueStatusOrdinals(AutoTestUtils.getOrdinals(AuditOverdueStatus.values()));
		assetSetQueryModel.setFinancialContractIds(JsonUtils.toJsonString(Arrays.asList(new Long(1l))));
		List<OverDueRepaymentDetailExcelVO> overDueExcels = repaymentPlanHandler.get_overdue_than_over_due_start_day_repayment_detail_excel(assetSetQueryModel);
		//绗笁鏉′负浣滃簾锛屼笉琚煡鍑�
		assertEquals(2,overDueExcels.size());
		assertEquals("2130.45",overDueExcels.get(1).getAssetFairValue());
		assertEquals("2434.80",overDueExcels.get(0).getAssetFairValue());
	}
	
	@Test
	@Sql("classpath:test/yunxin/settlementOrder/testOverDueThanFiveRepaymentDetailExcel.sql")
	public void  testOverDueThanFiveRepaymentDetailExcel(){
		AssetSetQueryModel assetSetQueryModel =new AssetSetQueryModel();
		assetSetQueryModel.setStartDate("2016-05-17");
		assetSetQueryModel.setEndDate("2016-05-17");
		assetSetQueryModel.setPaymentStatusOrdinals(AutoTestUtils.getOrdinals(PaymentStatus.values()));
		assetSetQueryModel.setAuditOverDueStatusOrdinals(AutoTestUtils.getOrdinals(AuditOverdueStatus.values()));
		assetSetQueryModel.setFinancialContractIds(JsonUtils.toJsonString(Arrays.asList(1L)));
		
		List<OverDueRepaymentDetailExcelVO> OverDueRepaymentDetailExcelVOs = repaymentPlanHandler.get_overdue_than_over_due_start_day_repayment_detail_excel(assetSetQueryModel);
		assertEquals(2, OverDueRepaymentDetailExcelVOs.size());
		OverDueRepaymentDetailExcelVO OverDueRepaymentDetailExcelVO = OverDueRepaymentDetailExcelVOs.get(0);
		
		assertEquals("nongfenqi", OverDueRepaymentDetailExcelVO.getAppName());
		assertEquals("云信信2016-78-DK(ZQ2016042522502)", OverDueRepaymentDetailExcelVO.getLoanContractNo());
		assertEquals("2016-04-17", OverDueRepaymentDetailExcelVO.getLoanDate());
		assertEquals("测试用户3", OverDueRepaymentDetailExcelVO.getCustomerName());
		assertEquals("2016-05-17", OverDueRepaymentDetailExcelVO.getAssetRecycleDate());
		assertEquals(null, OverDueRepaymentDetailExcelVO.getActualRecycleDate());
		assertEquals(1, OverDueRepaymentDetailExcelVO.getCurrentPeriod());
		assertEquals("0.00", OverDueRepaymentDetailExcelVO.getAssetPrincipalValue());
		assertEquals("2400.00", OverDueRepaymentDetailExcelVO.getAssetInterestValue());
		assertEquals("2400.00", OverDueRepaymentDetailExcelVO.getAssetInitialValue());
		assertEquals("34.80", OverDueRepaymentDetailExcelVO.getDifferencesPenaltyAmount());
		assertEquals("2434.80", OverDueRepaymentDetailExcelVO.getAssetFairValue());
		assertEquals("0.00", OverDueRepaymentDetailExcelVO.getRefundAmount());
		assertEquals("还款失败", OverDueRepaymentDetailExcelVO.getRepaymentStatus());
		assertEquals("待补足", OverDueRepaymentDetailExcelVO.getGuaranteeStatus());
		assertEquals(null, OverDueRepaymentDetailExcelVO.getComment());
		
	}
	
	@Test
	@Sql("classpath:test/yunxin/settlementOrder/testOverDueThanFiveRepaymentDetailExcelForNoThanFive.sql")
	public void testOverDueThanFiveRepaymentDetailExcelForNoThanFive(){
		AssetSetQueryModel assetSetQueryModel =new AssetSetQueryModel();
		assetSetQueryModel.setStartDate("2016-05-17");
		assetSetQueryModel.setEndDate("2016-05-17");
		assetSetQueryModel.setPaymentStatusOrdinals(AutoTestUtils.getOrdinals(PaymentStatus.values()));
		assetSetQueryModel.setAuditOverDueStatusOrdinals(AutoTestUtils.getOrdinals(AuditOverdueStatus.values()));
		assetSetQueryModel.setFinancialContractIds(JsonUtils.toJsonString(Arrays.asList(1l)));
		List<OverDueRepaymentDetailExcelVO> OverDueRepaymentDetailExcelVOs = repaymentPlanHandler.get_overdue_than_over_due_start_day_repayment_detail_excel(assetSetQueryModel);
		assertEquals(0, OverDueRepaymentDetailExcelVOs.size());
	}
	
	
	@Test
	@Sql("classpath:test/yunxin/settlementOrder/testOverDueThanFiveRepaymentDetailExcelForisFive.sql")
	public void testOverDueThanFiveRepaymentDetailExcelForisFive(){
		
		AssetSetQueryModel assetSetQueryModel =new AssetSetQueryModel();
		assetSetQueryModel.setStartDate("2016-05-17");
		assetSetQueryModel.setEndDate("2016-05-17");
		assetSetQueryModel.setPaymentStatusOrdinals(AutoTestUtils.getOrdinals(PaymentStatus.values()));
		assetSetQueryModel.setAuditOverDueStatusOrdinals(AutoTestUtils.getOrdinals(AuditOverdueStatus.values()));
		assetSetQueryModel.setFinancialContractIds(JsonUtils.toJsonString(Arrays.asList(1L)));
		List<OverDueRepaymentDetailExcelVO> OverDueRepaymentDetailExcelVOs = repaymentPlanHandler.get_overdue_than_over_due_start_day_repayment_detail_excel(assetSetQueryModel);
		assertEquals(1, OverDueRepaymentDetailExcelVOs.size());
		
		
		OverDueRepaymentDetailExcelVO OverDueRepaymentDetailExcelVO = OverDueRepaymentDetailExcelVOs.get(0);
		assertEquals("nongfenqi", OverDueRepaymentDetailExcelVO.getAppName());
		assertEquals("云信信2016-78-DK(ZQ2016042522502)", OverDueRepaymentDetailExcelVO.getLoanContractNo());
		assertEquals("2016-04-17", OverDueRepaymentDetailExcelVO.getLoanDate());
		assertEquals("测试用户3", OverDueRepaymentDetailExcelVO.getCustomerName());
		assertEquals("2016-05-17", OverDueRepaymentDetailExcelVO.getAssetRecycleDate());
		assertEquals("2016-05-22", OverDueRepaymentDetailExcelVO.getActualRecycleDate());
		assertEquals(2, OverDueRepaymentDetailExcelVO.getCurrentPeriod());
		assertEquals("0.00", OverDueRepaymentDetailExcelVO.getAssetPrincipalValue());
		assertEquals("2100.00", OverDueRepaymentDetailExcelVO.getAssetInterestValue());
		assertEquals("2100.00", OverDueRepaymentDetailExcelVO.getAssetInitialValue());
		assertEquals(5, OverDueRepaymentDetailExcelVO.getDaysDifference());
		assertEquals("30.45", OverDueRepaymentDetailExcelVO.getDifferencesPenaltyAmount());
		assertEquals("2130.45", OverDueRepaymentDetailExcelVO.getAssetFairValue());
		assertEquals("0.00", OverDueRepaymentDetailExcelVO.getRefundAmount());
		assertEquals("还款成功", OverDueRepaymentDetailExcelVO.getRepaymentStatus());
		assertEquals("已补足", OverDueRepaymentDetailExcelVO.getGuaranteeStatus());
		assertEquals(null, OverDueRepaymentDetailExcelVO.getComment());
	}
	
	@Test
	@Sql("classpath:test/yunxin/settlementOrder/testOverDueThanFiveRepaymentDetailExcelForNotPaidOffThanFiveDay.sql")
	public void testOverDueThanFiveRepaymentDetailExcelForNotPaidOffThanFiveDay(){
		AssetSetQueryModel assetSetQueryModel =new AssetSetQueryModel();
		assetSetQueryModel.setStartDate("2016-05-17");
		assetSetQueryModel.setEndDate("2016-05-17");
		assetSetQueryModel.setPaymentStatusOrdinals(AutoTestUtils.getOrdinals(PaymentStatus.values()));
		assetSetQueryModel.setAuditOverDueStatusOrdinals(AutoTestUtils.getOrdinals(AuditOverdueStatus.values()));
		assetSetQueryModel.setFinancialContractIds(JsonUtils.toJsonString(Arrays.asList(1l)));
		List<OverDueRepaymentDetailExcelVO> OverDueRepaymentDetailExcelVOs = repaymentPlanHandler.get_overdue_than_over_due_start_day_repayment_detail_excel(assetSetQueryModel);
		assertEquals(1, OverDueRepaymentDetailExcelVOs.size());
		
		OverDueRepaymentDetailExcelVO OverDueRepaymentDetailExcelVO = OverDueRepaymentDetailExcelVOs.get(0);
		assertEquals("nongfenqi", OverDueRepaymentDetailExcelVO.getAppName());
		assertEquals("云信信2016-78-DK(ZQ2016042522502)", OverDueRepaymentDetailExcelVO.getLoanContractNo());
		assertEquals("2016-04-17", OverDueRepaymentDetailExcelVO.getLoanDate());
		assertEquals("测试用户3", OverDueRepaymentDetailExcelVO.getCustomerName());
		assertEquals("2016-05-17", OverDueRepaymentDetailExcelVO.getAssetRecycleDate());
		assertEquals(null, OverDueRepaymentDetailExcelVO.getActualRecycleDate());
		assertEquals(1, OverDueRepaymentDetailExcelVO.getCurrentPeriod());
		assertEquals("0.00", OverDueRepaymentDetailExcelVO.getAssetPrincipalValue());
		assertEquals("2400.00", OverDueRepaymentDetailExcelVO.getAssetInterestValue());
		assertEquals("2400.00", OverDueRepaymentDetailExcelVO.getAssetInitialValue());
		assertEquals("34.80", OverDueRepaymentDetailExcelVO.getDifferencesPenaltyAmount());
		assertEquals("2434.80", OverDueRepaymentDetailExcelVO.getAssetFairValue());
		assertEquals("0.00", OverDueRepaymentDetailExcelVO.getRefundAmount());
		assertEquals("还款失败", OverDueRepaymentDetailExcelVO.getRepaymentStatus());
		assertEquals("待补足", OverDueRepaymentDetailExcelVO.getGuaranteeStatus());
		assertEquals("逾期", OverDueRepaymentDetailExcelVO.getComment());
		
	}
	
	@Test
	@Sql("classpath:test/yunxin/settlementOrder/testOverDueThanFiveRepaymentDetailExcelForTheWholeFinancialContractId.sql")
	public void testOverDueThanFiveRepaymentDetailExcelForTheWholeFinancialContractId(){
		AssetSetQueryModel assetSetQueryModel =new AssetSetQueryModel();
		assetSetQueryModel.setStartDate("2016-05-17");
		assetSetQueryModel.setEndDate("2016-05-17");
		assetSetQueryModel.setPaymentStatusOrdinals(AutoTestUtils.getOrdinals(PaymentStatus.values()));
		assetSetQueryModel.setAuditOverDueStatusOrdinals(AutoTestUtils.getOrdinals(AuditOverdueStatus.values()));
		assetSetQueryModel.setFinancialContractIds(JsonUtils.toJsonString(Arrays.asList(1l,2L)));
		List<OverDueRepaymentDetailExcelVO> OverDueRepaymentDetailExcelVOs = repaymentPlanHandler.get_overdue_than_over_due_start_day_repayment_detail_excel(assetSetQueryModel);
		assertEquals(3, OverDueRepaymentDetailExcelVOs.size());
		
		OverDueRepaymentDetailExcelVO OverDueRepaymentDetailExcelVO = OverDueRepaymentDetailExcelVOs.get(0);
		assertEquals("nongfenqi", OverDueRepaymentDetailExcelVO.getAppName());
		assertEquals("云信信2016-78-DK(ZQ2016042522502)", OverDueRepaymentDetailExcelVO.getLoanContractNo());
		assertEquals("2016-04-17", OverDueRepaymentDetailExcelVO.getLoanDate());
		assertEquals("测试用户3", OverDueRepaymentDetailExcelVO.getCustomerName());
		assertEquals("2016-05-17", OverDueRepaymentDetailExcelVO.getAssetRecycleDate());
		assertEquals(null, OverDueRepaymentDetailExcelVO.getActualRecycleDate());
		assertEquals(1, OverDueRepaymentDetailExcelVO.getCurrentPeriod());
		assertEquals("0.00", OverDueRepaymentDetailExcelVO.getAssetPrincipalValue());
		assertEquals("2400.00", OverDueRepaymentDetailExcelVO.getAssetInterestValue());
		assertEquals("2400.00", OverDueRepaymentDetailExcelVO.getAssetInitialValue());
		assertEquals("34.80", OverDueRepaymentDetailExcelVO.getDifferencesPenaltyAmount());
		assertEquals("2434.80", OverDueRepaymentDetailExcelVO.getAssetFairValue());
		assertEquals("0.00", OverDueRepaymentDetailExcelVO.getRefundAmount());
		assertEquals("还款失败", OverDueRepaymentDetailExcelVO.getRepaymentStatus());
		assertEquals("待补足", OverDueRepaymentDetailExcelVO.getGuaranteeStatus());
		assertEquals(null, OverDueRepaymentDetailExcelVO.getComment());
		
		OverDueRepaymentDetailExcelVO OverDueRepaymentDetailExcelVO1 = OverDueRepaymentDetailExcelVOs.get(2);
		assertEquals("nongfenqi", OverDueRepaymentDetailExcelVO1.getAppName());
		assertEquals("云信信2016-79-DK(ZQ2016042522502)", OverDueRepaymentDetailExcelVO1.getLoanContractNo());
		
	}
	
	
	
}

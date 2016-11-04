package com.zufangbao.earth.yunxin.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.demo2do.core.entity.Result;
import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.utils.JsonUtils;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.service.PrincipalService;
import com.zufangbao.earth.yunxin.web.controller.YunxinOfflinePaymentController;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.entity.order.RepaymentAuditStatus;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.OfflineBill;
import com.zufangbao.sun.yunxin.entity.OfflineBillStatus;
import com.zufangbao.sun.yunxin.entity.SourceDocumentStatus;
import com.zufangbao.sun.yunxin.entity.model.OfflineBillCreateModel;
import com.zufangbao.sun.yunxin.entity.model.OfflineBillQueryModel;
import com.zufangbao.sun.yunxin.entity.model.OrderMatchShowModel;
import com.zufangbao.sun.yunxin.service.OfflineBillService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.OfflineBillModel;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentType;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
		"classpath:/DispatcherServlet.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional()
public class YunxinOfflinePaymentControllerTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	private YunxinOfflinePaymentController yunxinPaymentController;

	@Autowired
	private SourceDocumentService sourceDocumentService;

	@Autowired
	private OfflineBillService offlineBillService;
	@Autowired
	private PrincipalService principalService;

	@Before
	public void init() {
		yunxinPaymentController = this.applicationContext
				.getBean(YunxinOfflinePaymentController.class);
	}

	@Test
	@Sql("classpath:test/yunxin/testOfflineBillList.sql")
	public void testOfflineBillList() {

		Page page = new Page(1, 12);
		String resultStr = yunxinPaymentController.queryOfflinePaymentOrders(page, new OfflineBillQueryModel());;
		Result result = JsonUtils.parse(resultStr, Result.class);
		List<OfflineBillModel> offlineBills = JsonUtils.parseArray(result.get("list").toString(), OfflineBillModel.class);
		OfflineBill offlineBill = offlineBills.get(0).getOfflineBill();
		Assert.assertEquals(new BigDecimal("1000.00"), offlineBill.getAmount());
		Assert.assertEquals(new String("备注"), offlineBill.getComment());
		Assert.assertEquals(new String("中国银行"), offlineBill.getBankShowName());
		Assert.assertEquals(new String("BC00001"),
				offlineBill.getOfflineBillNo());

	}

	@Test
	@Sql("classpath:test/yunxin/testOfflineBillList.sql")
	public void testOfflineBillQueryList() {
		String accountName = "payer_account_name";
		String payAcNo = "account_no";
		OfflineBillQueryModel offlineBillQueryModel = new OfflineBillQueryModel(accountName, payAcNo);
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setQueryString("21312");
		Page page = new Page(1, 12);
		String resultStr = yunxinPaymentController.queryOfflinePaymentOrders(page, offlineBillQueryModel);
		Result result = JSON.parseObject(resultStr, Result.class);
		List<OfflineBillModel> offlineBills = JsonUtils.parseArray(result.get("list").toString(), OfflineBillModel.class);
		OfflineBill offlineBill = offlineBills.get(0).getOfflineBill();
		Assert.assertEquals(new BigDecimal("1000.00"), offlineBill.getAmount());
		Assert.assertEquals(new String("备注"), offlineBill.getComment());
		Assert.assertEquals(new String("中国银行"), offlineBill.getBankShowName());
		Assert.assertEquals(new String("BC00001"),offlineBill.getOfflineBillNo());
		Assert.assertEquals(new String("payer_account_name"),offlineBill.getPayerAccountName());
		Assert.assertEquals(new String("account_no"),offlineBill.getPayerAccountNo());
	}

	@Test
	@Sql("classpath:test/yunxin/testOfflineBillRelatedStatus.sql")
	public void testOfflineBillRelatedStatus_ISSUING() {
		String accountName = "payer_account_name";
		String payAcNo = "account_no";
		OfflineBillQueryModel offlineBillQueryModel = new OfflineBillQueryModel(accountName, payAcNo);
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setQueryString("21312");
		Page page = new Page(1, 12);
		String resultStr = yunxinPaymentController.queryOfflinePaymentOrders(page, offlineBillQueryModel);
		Result result = JSON.parseObject(resultStr, Result.class);
		List<OfflineBillModel> offlineBills = JsonUtils.parseArray(result.get("list").toString(),OfflineBillModel.class);
		OfflineBillModel offlineBillModel = offlineBills.get(0);
		Assert.assertEquals(1, offlineBills.size());
		Assert.assertEquals(RepaymentAuditStatus.formValue(1), offlineBillModel.getSourceDocument().getAuditStatus());
		Assert.assertEquals(new String("备注"), offlineBillModel.getOfflineBill().getComment());
		Assert.assertEquals(new String("中国银行"), offlineBillModel.getOfflineBill().getBankShowName());
		Assert.assertEquals(new String("BC00001"), offlineBillModel.getOfflineBill().getOfflineBillNo());
		Assert.assertEquals(new String("payer_account_name"), offlineBillModel.getOfflineBill().getPayerAccountName());
		Assert.assertEquals(new String("account_no"), offlineBillModel.getOfflineBill().getPayerAccountNo());
	}

	@Test
	@Sql("classpath:test/yunxin/testOfflineBillDetail.sql")
	public void testOfflineBillPaymentDetail() {

		Long offlineBillId = 1l;
		Principal principal = principalService.load(Principal.class, 1l);
		Page page = new Page(1,12);
		ModelAndView modelAndView = yunxinPaymentController
				.showOfflineBillDetail(offlineBillId, principal,page);

		Map<String, Object> map = modelAndView.getModel();
		OfflineBill offlineBill = (OfflineBill) map.get("offlineBill");

		Assert.assertEquals(new BigDecimal("1000.00"), offlineBill.getAmount());
		Assert.assertEquals(new String("备注"), offlineBill.getComment());
		Assert.assertEquals(new String("中国银行"), offlineBill.getBankShowName());
		Assert.assertEquals(new String("BC00001"),
				offlineBill.getOfflineBillNo());
		Assert.assertEquals(new String("payer_account_name"),
				offlineBill.getPayerAccountName());
		Assert.assertEquals(new String("account_no"),
				offlineBill.getPayerAccountNo());

		// 校验orderMatchShowModel
		List<OrderMatchShowModel> orderMatchShowModelList = (List<OrderMatchShowModel>) map
				.get("orderMatchShowModelList");
		assertEquals(2, orderMatchShowModelList.size());
		OrderMatchShowModel orderMatchShowModel_1 = orderMatchShowModelList
				.get(0);
		Order order_1 = orderMatchShowModel_1.getOrder();
		assertEquals("repayment_bill_id_1", order_1.getRepaymentBillId());
		assertEquals(0, new BigDecimal("1000").compareTo(orderMatchShowModel_1
				.getPaidAmount()));
		assertEquals(0, new BigDecimal("1000").compareTo(orderMatchShowModel_1
				.getIssuedAmount()));

		OrderMatchShowModel orderMatchShowModel_2 = orderMatchShowModelList
				.get(1);
		Order order_2 = orderMatchShowModel_2.getOrder();
		assertEquals("repayment_bill_id_2", order_2.getRepaymentBillId());
		assertEquals(0, new BigDecimal("1200").compareTo(orderMatchShowModel_2
				.getPaidAmount()));
		assertEquals(0, new BigDecimal("1200").compareTo(orderMatchShowModel_2
				.getIssuedAmount()));

	}

	@Test
	@Sql("classpath:test/yunxin/offlineBill/testCreateOfflineBill.sql")
	public void testCreateOfflineBill() {
		String bankShowName = "中国银行_1";
		String accountNo = "accountNo_1";
		String accountName = "开户人_1";
		String serialNo = "serial_no_1";
		String comment = "comment_1";
		String amount = new String("10.00");
		String tradeTimeString = "2016-10-10 10:10:10";
		OfflineBillCreateModel offlineBillCreateModel = new OfflineBillCreateModel(
				bankShowName, accountName, serialNo, accountNo, comment,
				amount, tradeTimeString);

		Principal principal = new Principal();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		yunxinPaymentController.createOfflineBill(principal, request, response,
				offlineBillCreateModel);

		List<OfflineBill> offlineBills = offlineBillService.list(
				OfflineBill.class, new Filter());
		assertEquals(1, offlineBills.size());
		OfflineBill offlineBill = offlineBills.get(0);

		assertEquals(amount.toString(), offlineBill.getAmount().toString());
		assertEquals(bankShowName, offlineBill.getBankShowName());
		assertEquals(comment, offlineBill.getComment());

		assertEquals(
				0,
				DateUtils.compareTwoDatesOnDay(new Date(),
						offlineBill.getCreateTime()));
		assertFalse(StringUtils.isEmpty(offlineBill.getOfflineBillNo()));
		assertEquals(OfflineBillStatus.PAID, offlineBill.getOfflineBillStatus());
		assertEquals(accountName, offlineBill.getPayerAccountName());
		assertEquals(accountNo, offlineBill.getPayerAccountNo());
		assertEquals(tradeTimeString, DateUtils.format(
				offlineBill.getTradeTime(), "yyyy-MM-dd HH:mm:ss"));

		List<SourceDocument> sourceDocumentList = sourceDocumentService.list(
				SourceDocument.class, new Filter());
		assertEquals(1, sourceDocumentList.size());
		SourceDocument sourceDocument = sourceDocumentList.get(0);
		assertEquals(new Long(1L), sourceDocument.getCompanyId());
		assertEquals(0,
				BigDecimal.ZERO.compareTo(sourceDocument.getBookingAmount()));
		assertEquals(SourceDocument.FIRSTOUTLIER_OFFLINEBILL,
				sourceDocument.getFirstOutlierDocType());
		assertEquals(accountName, sourceDocument.getOutlierCounterPartyName());
		assertEquals(accountNo, sourceDocument.getOutlierCounterPartyAccount());
		assertEquals(AccountSide.DEBIT, sourceDocument.getSourceAccountSide());
		assertEquals(SourceDocumentStatus.CREATE,
				sourceDocument.getSourceDocumentStatus());
		assertEquals(SourceDocumentType.NOTIFY,
				sourceDocument.getSourceDocumentType());
		assertEquals(RepaymentAuditStatus.CREATE,
				sourceDocument.getAuditStatus());
		assertEquals(tradeTimeString, DateUtils.format(
				sourceDocument.getOutlierTradeTime(), "yyyy-MM-dd HH:mm:ss"));
	}

}

package com.zufangbao.earth.yunxin.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.web.servlet.ModelAndView;

import com.demo2do.core.persistence.support.Filter;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.yunxin.web.controller.GuaranteeController;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.OfflineBill;
import com.zufangbao.sun.yunxin.entity.OfflineBillStatus;
import com.zufangbao.sun.yunxin.entity.model.OfflineBillCreateModel;
import com.zufangbao.sun.yunxin.service.OfflineBillService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
		"classpath:/DispatcherServlet.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional()
public class GuaranteeControllerTest {

	@Autowired
	private GuaranteeController guaranteeController;
	
	private MockHttpServletRequest request = new MockHttpServletRequest();

	private Principal principal = new Principal();
	
	@Autowired
	private OfflineBillService offlineBillService;
	
	@Test
	@Sql("classpath:test/yunxin/offlineBill/testCreateOfflineBill.sql")
	public void testPreCreateOfflineBill(){
		String guaranteeOrderRepaymentUuids = "[\"repayment_bill_id_1\",\"repayment_bill_id_2\"]";
		ModelAndView modelAndView = guaranteeController.preCreateOfflineBill(guaranteeOrderRepaymentUuids, principal, request);
		assertEquals("yunxin/payment/offline-bill-pre-create",modelAndView.getViewName());
		Map<String,Object> params = modelAndView.getModel();
		assertEquals(guaranteeOrderRepaymentUuids,params.get("guaranteeRepaymentUuids").toString());
		assertEquals("DKHD-001-01-20160308,DKHD-001-01-20160307",params.get("orderNoSet"));
		assertEquals("2000.00",params.get("totalAmount").toString());
		assertEquals(0,DateUtils.compareTwoDatesOnDay(new Date(),(Date)params.get("date")));
	}
	
	@Test
	@Sql("classpath:test/yunxin/offlineBill/testCreateOfflineBill.sql")
	public void testCreateOfflineBill(){
		
		String guaranteeOrderRepaymentUuids = "[\"repayment_bill_id_1\",\"repayment_bill_id_2\"]";
		String bankShowName = "中国银行";
		String accountNo = "accountNo";
		String accountName = "开户人";
		String serialNo = "serial_no_1";
		String comment = "comment_1";
		String offlineBillNo = "offlineBillNo_1";
		OfflineBillCreateModel offlineBillCreateModel = new OfflineBillCreateModel(bankShowName, accountName,
				serialNo, accountNo, guaranteeOrderRepaymentUuids, comment);
		
		guaranteeController.createOfflineBill(offlineBillCreateModel, principal, request);
		
		//校验 offlineBill
		List<OfflineBill> offlineBills = offlineBillService.list(OfflineBill.class, new Filter());
		assertEquals(1,offlineBills.size());
		OfflineBill offlineBill = offlineBills.get(0);
		
		assertEquals("2000.00",offlineBill.getAmount().toString());
		assertEquals(bankShowName,offlineBill.getBankShowName());
		assertEquals(comment,offlineBill.getComment());
		
		assertEquals(0,DateUtils.compareTwoDatesOnDay(new Date(), offlineBill.getCreateTime()));
		assertFalse(StringUtils.isEmpty(offlineBill.getOfflineBillNo()));
		assertEquals(OfflineBillStatus.PAID,offlineBill.getOfflineBillStatus());
		assertEquals(accountName,offlineBill.getPayerAccountName());
		assertEquals(accountNo,offlineBill.getPayerAccountNo());
	}
}

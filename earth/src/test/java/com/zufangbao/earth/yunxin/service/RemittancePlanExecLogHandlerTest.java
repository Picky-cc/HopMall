package com.zufangbao.earth.yunxin.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zufangbao.earth.yunxin.handler.remittance.RemittancePlanExecLogHandler;
import com.zufangbao.sun.service.CashFlowService;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlanExecLog;
import com.zufangbao.sun.yunxin.entity.remittance.ReverseStatus;
import com.zufangbao.sun.yunxin.service.remittance.IRemittancePlanExecLogService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/context/applicationContext-*.xml", "classpath:/local/applicationContext-*.xml"})
public class RemittancePlanExecLogHandlerTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Autowired
	private RemittancePlanExecLogHandler remittancePlanExecLogHandler;
	
	@Autowired
	private IRemittancePlanExecLogService iRemittancePlanExecLogService;
	
	@Autowired
	private CashFlowService cashFlowService;
	
	@Test
	@Sql("classpath:/test/yunxin/remittance/test4RemittancePlanExecLogHandler.sql")
	public void testconfirm_Whether_Exist_Credit_CashFlow(){
		Long remittancePlanExecLogId = null;
		remittancePlanExecLogHandler.confirmWhetherExistCreditCashFlow(remittancePlanExecLogId);
		RemittancePlanExecLog execLog = iRemittancePlanExecLogService.load(RemittancePlanExecLog.class, 1L);
		Assert.assertEquals(0,execLog.getActualCreditCashFlowCheckNumber());
		
		remittancePlanExecLogId = 2l;
		remittancePlanExecLogHandler.confirmWhetherExistCreditCashFlow(remittancePlanExecLogId);
		execLog = iRemittancePlanExecLogService.load(RemittancePlanExecLog.class, 2l);
		Assert.assertEquals(1, execLog.getActualCreditCashFlowCheckNumber());
		
		remittancePlanExecLogId = 5l;
		remittancePlanExecLogHandler.confirmWhetherExistCreditCashFlow(remittancePlanExecLogId);
		execLog = iRemittancePlanExecLogService.load(RemittancePlanExecLog.class, 5l);
		Assert.assertEquals(1, execLog.getActualCreditCashFlowCheckNumber());
		
//		remittancePlanExecLogId = 4l;
//		remittancePlanExecLogHandler.confirmWhetherExistCreditCashFlow(remittancePlanExecLogId);
//		execLog = iRemittancePlanExecLogService.load(RemittancePlanExecLog.class, 4L);
//		Assert.assertEquals(ReverseStatus.UNOCCUR,execLog.getReverseStatus());
//		Assert.assertEquals("credit_cash_flow_uuid4",execLog.getCreditCashFlowUuid());
//		Assert.assertEquals(1,execLog.getActualCreditCashFlowCheckNumber());
		
		remittancePlanExecLogId = 3l;
		remittancePlanExecLogHandler.confirmWhetherExistCreditCashFlow(remittancePlanExecLogId);
		execLog = iRemittancePlanExecLogService.load(RemittancePlanExecLog.class, 3L);
		Assert.assertEquals(ReverseStatus.UNOCCUR,execLog.getReverseStatus());
		Assert.assertEquals("credit_cash_flow_uuid3",execLog.getCreditCashFlowUuid());
		Assert.assertEquals(1,execLog.getActualCreditCashFlowCheckNumber());
		
		remittancePlanExecLogId = 1l;
		remittancePlanExecLogHandler.confirmWhetherExistCreditCashFlow(remittancePlanExecLogId);
		execLog = iRemittancePlanExecLogService.load(RemittancePlanExecLog.class, 1L);
		Assert.assertEquals(ReverseStatus.NOTREVERSE,execLog.getReverseStatus());
		Assert.assertEquals("cash_flow_uuid1",execLog.getCreditCashFlowUuid());
		Assert.assertEquals(1,execLog.getActualCreditCashFlowCheckNumber());

	}
	
	@Test
	@Sql("classpath:/test/yunxin/remittance/test4RemittancePlanExecLogHandler1.sql")
	public void confirm_Whether_Exist_Debit_CashFlow(){
		Long remittancePlanExecLogId = null;
		remittancePlanExecLogHandler.confirmWhetherExistDebitCashFlow(remittancePlanExecLogId);
		RemittancePlanExecLog execLog = iRemittancePlanExecLogService.load(RemittancePlanExecLog.class, 1L);
		Assert.assertEquals(ReverseStatus.UNOCCUR,execLog.getReverseStatus());
		
		remittancePlanExecLogId = 2l;
		remittancePlanExecLogHandler.confirmWhetherExistDebitCashFlow(remittancePlanExecLogId);
		execLog = iRemittancePlanExecLogService.load(RemittancePlanExecLog.class, 2l);
		Assert.assertEquals(ReverseStatus.UNOCCUR,execLog.getReverseStatus());
		
		remittancePlanExecLogId = 3l;
		remittancePlanExecLogHandler.confirmWhetherExistDebitCashFlow(remittancePlanExecLogId);
		execLog = iRemittancePlanExecLogService.load(RemittancePlanExecLog.class, 3L);
		Assert.assertEquals(ReverseStatus.UNOCCUR,execLog.getReverseStatus());
		
		remittancePlanExecLogId = 4l;
		remittancePlanExecLogHandler.confirmWhetherExistDebitCashFlow(remittancePlanExecLogId);
		execLog = iRemittancePlanExecLogService.load(RemittancePlanExecLog.class, 4L);
		Assert.assertEquals(ReverseStatus.UNOCCUR,execLog.getReverseStatus());

		remittancePlanExecLogId = 5l;
		remittancePlanExecLogHandler.confirmWhetherExistDebitCashFlow(remittancePlanExecLogId);
		execLog = iRemittancePlanExecLogService.load(RemittancePlanExecLog.class, 5L);
		Assert.assertEquals(ReverseStatus.REVERSED,execLog.getReverseStatus());
		Assert.assertEquals("cash_flow_uuid5",execLog.getDebitCashFlowUuid());
		
		remittancePlanExecLogId = 6l;
		remittancePlanExecLogHandler.confirmWhetherExistDebitCashFlow(remittancePlanExecLogId);
		execLog = iRemittancePlanExecLogService.load(RemittancePlanExecLog.class, 6L);
		Assert.assertEquals(ReverseStatus.UNOCCUR,execLog.getReverseStatus());

		remittancePlanExecLogId = 7l;
		remittancePlanExecLogHandler.confirmWhetherExistDebitCashFlow(remittancePlanExecLogId);
		execLog = iRemittancePlanExecLogService.load(RemittancePlanExecLog.class, 7L);
		Assert.assertEquals(ReverseStatus.UNOCCUR,execLog.getReverseStatus());
		
		remittancePlanExecLogId = 1l;
		remittancePlanExecLogHandler.confirmWhetherExistDebitCashFlow(remittancePlanExecLogId);
		execLog = iRemittancePlanExecLogService.load(RemittancePlanExecLog.class, 1L);
		Assert.assertEquals(ReverseStatus.REVERSED,execLog.getReverseStatus());
		Assert.assertEquals("cash_flow_uuid1",execLog.getDebitCashFlowUuid());
		
	}

}

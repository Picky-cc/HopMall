package com.zufangbao.sun.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.demo2do.core.persistence.support.Filter;
import com.zufangbao.sun.entity.account.AuditStatus;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.icbc.business.AppArriveRecord;
import com.zufangbao.sun.entity.icbc.business.ArriveRecordStatus;
import com.zufangbao.sun.entity.icbc.business.BankSide;
import com.zufangbao.sun.entity.icbc.business.CashFlowChannelType;
import com.zufangbao.sun.entity.icbc.business.TransactionType;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.model.CashFlowConditionModel;
/**
 * 
 * @author wukai
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" 
})
@TransactionConfiguration(defaultRollback=true)
@Transactional
public class AppArriveRecordServiceTest {
	
	@Autowired
	private AppArriveRecordService appArriveRecordService;
	
	@Autowired
	private AppService appService;
	
	@Test
	@Sql("classpath:test/yunxin/appArriveRecord/test4AppArriveRecordService.sql")
	public void testAppArriveRecordContructor(){
		
		App app = appService.getApp("xiaoyu");
		
		Date date = DateUtils.asDay("2015-10-14");
		
		AppArriveRecord appArriveRecord = new AppArriveRecord(app, "serialNo", "1", "payAcNo", "payName", "receiveAcNo","receiveName", new BigDecimal("1.00"), date, ArriveRecordStatus.Deducted, "remark", "vouhNo", "summary");
		
		appArriveRecordService.save(appArriveRecord);
		
		Filter filter = new Filter();
		filter.addEquals("serialNo", appArriveRecord.getSerialNo());
		filter.addEquals("payAcNo", appArriveRecord.getPayAcNo());
		filter.addEquals("drcrf", "1");
		filter.addEquals("receiveAcNo", appArriveRecord.getReceiveAcNo());
		
		List<AppArriveRecord> appArriveRecords = appArriveRecordService.list(AppArriveRecord.class, filter);
		
		assertEquals(1,appArriveRecords.size());
		
		AppArriveRecord appArriveRecordFromDb = appArriveRecords.get(0);
		
		assertEquals(appArriveRecord.getApp(),appArriveRecordFromDb.getApp());
		assertEquals(appArriveRecord.getAmount(),appArriveRecordFromDb.getAmount());
		assertEquals(appArriveRecord.getArriveRecordStatus(),appArriveRecordFromDb.getArriveRecordStatus());
		assertEquals(appArriveRecord.getCashFlowChannelType(),appArriveRecordFromDb.getCashFlowChannelType());
		assertEquals(appArriveRecord.getCashFlowUid(),appArriveRecordFromDb.getCashFlowUid());
		assertEquals(appArriveRecord.getDetailData(),appArriveRecordFromDb.getDetailData());
		assertEquals(appArriveRecord.getDrcrf(),appArriveRecordFromDb.getDrcrf());
		assertEquals(appArriveRecord.getOperateRemark(),appArriveRecordFromDb.getOperateRemark());
		assertEquals(StringUtils.EMPTY,appArriveRecordFromDb.getPartnerId());
		assertEquals(appArriveRecord.getPayAcNo(),appArriveRecordFromDb.getPayAcNo());
		assertEquals(appArriveRecord.getPayName(),appArriveRecordFromDb.getPayName());
		assertEquals(appArriveRecord.getReceiveAcNo(),appArriveRecordFromDb.getReceiveAcNo());
		assertEquals(appArriveRecord.getRemark(),appArriveRecordFromDb.getRemark());
		assertEquals(appArriveRecord.getSerialNo(),appArriveRecordFromDb.getSerialNo());
		assertEquals(appArriveRecord.getSummary(),appArriveRecordFromDb.getSummary());
		assertEquals(date,appArriveRecordFromDb.getTime());
		assertEquals(null,appArriveRecordFromDb.getTransactionType());//默认的工行银企的流水的业务类型为空
		assertEquals(appArriveRecord.getVouhNo(),appArriveRecordFromDb.getVouhNo());
		assertNotNull(appArriveRecord.getAuditStatus());
		assertNotNull(appArriveRecordFromDb.getAuditStatus());
	}

	@Test
	@Sql("classpath:test/yunxin/appArriveRecord/test4AppArriveRecordService.sql")
	public void testGetAlipayCreditAppArriveRecords() {
		
		List<AppArriveRecord> appArriveRecords = appArriveRecordService.getAlipayAppArriveRecords();
		
		Assert.assertEquals(2, appArriveRecords.size());
		
		AppArriveRecord appArriveRecord = appArriveRecords.get(0);
		
		App app = appService.load(App.class, 3L);
		
		Assert.assertEquals(new BigDecimal("1650.00").doubleValue(), appArriveRecord.getAmount().doubleValue(),0);
		assertEquals(ArriveRecordStatus.NotDeducted, appArriveRecord.getArriveRecordStatus());
		assertEquals("20884023249019440156", appArriveRecord.getPayAcNo());
		assertEquals("20889112143230040156", appArriveRecord.getReceiveAcNo());
		assertEquals("", appArriveRecord.getRemark());
		assertEquals("20151012072517653350666284500992", appArriveRecord.getSerialNo());
		assertEquals(DateUtils.parseDate("2015-10-12 07:25:30", DateUtils.LONG_DATE_FORMAT), appArriveRecord.getTime());
		assertEquals(app,appArriveRecord.getApp());
		assertEquals(BankSide.Credit.getValue(),appArriveRecord.getDrcrf());
		assertEquals("",appArriveRecord.getPayName());
		assertEquals("",appArriveRecord.getSummary());
		assertEquals("2015101200001000940062595431",appArriveRecord.getVouhNo());
		assertEquals(Collections.emptyList(),appArriveRecord.getOperateRemark());
		assertEquals("9d425d23-8f69-4c72-923e-e48f712bbef4",appArriveRecord.getCashFlowUid());
		assertEquals(CashFlowChannelType.Alipay,appArriveRecord.getCashFlowChannelType());
		assertEquals(TransactionType.OnlinePay,appArriveRecord.getTransactionType());
		assertEquals("2088911214323004",appArriveRecord.getPartnerId());
	}
	
	@Test
	@Sql("classpath:test/yunxin/appArriveRecord/test4AppArriveRecordService.sql")
	public void testGetAppArriveRecordByCashFlowUuid(){
		String cashFlowUuid = "9d425d23-8f69-4c72-923e-e48f712bbef4";
		String serialNo = "2015101200001000940062595431";
		AppArriveRecord appArriveRecord = appArriveRecordService.getArriveRecordByCashFlowUuid(cashFlowUuid);
		assertEquals(cashFlowUuid, appArriveRecord.getCashFlowUid());
		assertEquals(serialNo,appArriveRecord.getVouhNo());
	}

	@Test
	@Sql("classpath:test/yunxin/appArriveRecord/test4AppArriveRecordService.sql")
	public void testGetAppArriveRecordByCashFlowUuidForEmpty(){
		String cashFlowUuid = "";
		AppArriveRecord appArriveRecord = appArriveRecordService.getArriveRecordByCashFlowUuid(cashFlowUuid);
		assertNull(appArriveRecord);
	}
	
	@Test
	@Sql("classpath:test/yunxin/appArriveRecord/receivable/test4AppArriveRecord_receivable.sql")
	public void testListAppArriveRecodBy(){
		
		int begin = 0;
		int max = 0;
		CashFlowConditionModel cashFlowConditionModel = new CashFlowConditionModel();
		cashFlowConditionModel.setAuditStatusValue(-1);
		List<AppArriveRecord> appArrivedRecordList = appArriveRecordService.listAppArriveRecodBy(cashFlowConditionModel, begin, max);
		assertEquals(7, appArrivedRecordList.size());
		
		cashFlowConditionModel.setAppId("yuanlai");
		appArrivedRecordList = appArriveRecordService.listAppArriveRecodBy(cashFlowConditionModel, begin, max);
		assertEquals(6, appArrivedRecordList.size());
		
		
		cashFlowConditionModel.setAccountNo("account_no_1");
		appArrivedRecordList = appArriveRecordService.listAppArriveRecodBy(cashFlowConditionModel, begin, max);
		assertEquals(4, appArrivedRecordList.size());
		
		cashFlowConditionModel.setAuditStatusValue(2);
		appArrivedRecordList = appArriveRecordService.listAppArriveRecodBy(cashFlowConditionModel, begin, max);
		assertEquals(2, appArrivedRecordList.size());

		assertEquals(6L, (long)appArrivedRecordList.get(0).getId());
		assertEquals(3L, (long)appArrivedRecordList.get(1).getId());
		
		
		String Time = "2015-10-03 09:40:33";//另一条为    '2015-10-03 09:40:33'		,    "yyyy-MM-dd HH:mm"
		String startTime = "2015-10-03";//String startTime = "2015-10-03 09:39";
		String endTime = "2015-10-03";
		//		String endTime = "2015-10-03 09:40";
		
		Date date = DateUtils.parseDate(Time, "yyyy-MM-dd HH:mm:ss");
		cashFlowConditionModel.setAuditStatusValue(2);
		cashFlowConditionModel.setStartTime(startTime);
		cashFlowConditionModel.setEndTime(endTime);
		
		appArrivedRecordList = appArriveRecordService.listAppArriveRecodBy(cashFlowConditionModel, begin, max);
		assertEquals(2,appArrivedRecordList.size());
		assertEquals(6L, (long)appArrivedRecordList.get(0).getId());
		assertEquals("account_no_1",appArrivedRecordList.get(0).getReceiveAcNo());
		assertEquals(date.getTime(), appArrivedRecordList.get(0).getTime().getTime());
	}
	
	@Test
	@Sql("classpath:test/yunxin/appArriveRecord/receivable/test4AppArriveRecord_receivable.sql")
	public void testListAppArriveRecodByForAmount(){
		
		int begin = 0;
		int max = 0;
		CashFlowConditionModel cashFlowConditionModel = new CashFlowConditionModel();
		cashFlowConditionModel.setAuditStatusValue(-1);
		
		//测试金额
		String amountString  = "60.00";
		cashFlowConditionModel.setAmountString(amountString);
		List<AppArriveRecord> appArrivedRecordList = appArriveRecordService.listAppArriveRecodBy(cashFlowConditionModel, begin, max);
		assertEquals(1, appArrivedRecordList.size());
		assertEquals(6,(long)appArrivedRecordList.get(0).getId());
		
		amountString = "aaaaa";
		cashFlowConditionModel.setAmountString(amountString);
		appArrivedRecordList = appArriveRecordService.listAppArriveRecodBy(cashFlowConditionModel, begin, max);
		assertEquals(0, appArrivedRecordList.size());
		
		amountString = "";
		cashFlowConditionModel.setAmountString(amountString);
		appArrivedRecordList = appArriveRecordService.listAppArriveRecodBy(cashFlowConditionModel, begin, max);
		assertEquals(7, appArrivedRecordList.size());

	}
	@Test
	@Sql("classpath:test/yunxin/appArriveRecord/receivable/test4AppArriveRecord_receivable.sql")
	public void testListAppArriveRecodByForKeyWord(){
		String keyWord = "aaaaa";
		int begin = 0;
		int max = 0;
		CashFlowConditionModel cashFlowConditionModel = new CashFlowConditionModel();
		cashFlowConditionModel.setAuditStatusValue(-1);
		
		cashFlowConditionModel.setQueryKeyWord(keyWord);
		List<AppArriveRecord> appArrivedRecordList = appArriveRecordService.listAppArriveRecodBy(cashFlowConditionModel, begin, max);
		assertEquals(0, appArrivedRecordList.size());
		
		keyWord = "";
		cashFlowConditionModel.setQueryKeyWord(keyWord);
		appArrivedRecordList = appArriveRecordService.listAppArriveRecodBy(cashFlowConditionModel, begin, max);
		assertEquals(7, appArrivedRecordList.size());
		
		keyWord = "pay_ac_no_2";
		cashFlowConditionModel.setQueryKeyWord(keyWord);
		appArrivedRecordList = appArriveRecordService.listAppArriveRecodBy(cashFlowConditionModel, begin, max);
		assertEquals(1, appArrivedRecordList.size());
		assertEquals(2,(long)appArrivedRecordList.get(0).getId());
		
		keyWord = "pay_name_1";
		cashFlowConditionModel.setQueryKeyWord(keyWord);
		appArrivedRecordList = appArriveRecordService.listAppArriveRecodBy(cashFlowConditionModel, begin, max);
		assertEquals(1, appArrivedRecordList.size());
		assertEquals(1,(long)appArrivedRecordList.get(0).getId());
		
		keyWord = "pay_ac_no_2";
		cashFlowConditionModel.setQueryKeyWord(keyWord);
		appArrivedRecordList = appArriveRecordService.listAppArriveRecodBy(cashFlowConditionModel, begin, max);
		assertEquals(1, appArrivedRecordList.size());
		assertEquals(2,(long)appArrivedRecordList.get(0).getId());
		
	}
	
	
	@Test
	@Sql("classpath:test/yunxin/appArriveRecord/receivable/test4AppArriveRecord_receivable.sql")
	public void testCountAppArriveRecodeBy() {
		
		CashFlowConditionModel cashFlowConditionModel = new CashFlowConditionModel();
		cashFlowConditionModel.setAuditStatusValue(-1);
		int size = appArriveRecordService.countAppArriveRecodeBy(cashFlowConditionModel);
		assertEquals(7, size);
		
		cashFlowConditionModel.setAppId("yuanlai");
		size = appArriveRecordService.countAppArriveRecodeBy(cashFlowConditionModel);
		assertEquals(6, size);
		
		//借方流水查询的账号为 pay_account_no
		cashFlowConditionModel.setAccountNo("account_no_1");
		cashFlowConditionModel.setAccountSide("debit");
		size = appArriveRecordService.countAppArriveRecodeBy(cashFlowConditionModel);
		assertEquals(0, size);
		
		cashFlowConditionModel.setAuditStatusValue(2);
		size = appArriveRecordService.countAppArriveRecodeBy(cashFlowConditionModel);
		assertEquals(0, size);
		
		//贷方流水 查询的账号为receive_account_no
		cashFlowConditionModel.setAccountNo("account_no_1");
		cashFlowConditionModel.setAccountSide("credit");
		size = appArriveRecordService.countAppArriveRecodeBy(cashFlowConditionModel);
		assertEquals(0, size);
	}
	
	@Test
	@Sql("classpath:test/yunxin/appArriveRecord/test4AppArriveRecordService.sql")
	public void test(){
		Date date = DateUtils.asDay("2015-10-14");
		List<AppArriveRecord> appArriveRecordList = appArriveRecordService.getArriveRecordBy(date, BankSide.Debit, AuditStatus.CREATE);
		assertEquals(1,appArriveRecordList.size());
		assertEquals(new Long(3L),appArriveRecordList.get(0).getId());
	}
}
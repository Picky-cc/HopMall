package com.zufangbao.earth.yunxin.handler.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.utils.DateUtils;
import com.zufangbao.earth.yunxin.handler.DataStatisticsCacheHandler;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AuditOverdueStatus;
import com.zufangbao.sun.yunxin.entity.model.vo.welcom.RepaymentDataStatistic;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@Transactional
public class DataStatisticsCacheHandlerTest {
	@Autowired
	private DataStatisticsCacheHandler dataStatisticsCacheHandler;
	
	@Autowired
	private ContractService contractService;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private GenericDaoSupport genericDaoSupport;
	private long timeout = 1000;
	@Before
	public void init_clear_cache(){
		DataStatisticsCacheHandlerImpl.setTIMEOUT(timeout);
		dataStatisticsCacheHandler.cacheEvict(Arrays.asList(1L,2L));
	}
	
	@Test
	@Sql("classpath:test/yunxin/test_welcome_page_data/test_welcome_data_cache.sql")
	public void testGetData_when_time_out(){
		// asset1,2为已逾期,asset3为待确认逾期， asset1为处理中,asset2,3为异常
		List<Long> financialContractIds = Arrays.asList(1L,2L);
		RepaymentDataStatistic data = dataStatisticsCacheHandler.getRepaymentDataFromOrRefreshCache(financialContractIds);
		assertEquals(2,data.getOverdue_and_processing_unusual_payment_status_assets_nums());
		
		//插入一条新的逾期处理中的asset
		Contract contract = contractService.load(Contract.class, 1L);
		AssetSet assetSet = new AssetSet(contract, 3, DateUtils.asDay(new Date()),new BigDecimal("1000"), new BigDecimal("10"),BigDecimal.ZERO);
		assetSet.setOverdueStatus(AuditOverdueStatus.OVERDUE);
		repaymentPlanService.save(assetSet);
		
		//查询验证为从缓存中获取
		data = dataStatisticsCacheHandler.getRepaymentDataFromOrRefreshCache(financialContractIds);
		assertEquals(2,data.getOverdue_and_processing_unusual_payment_status_assets_nums());
		
		//超过缓存时间
		try {
			Thread.sleep(timeout+10);
		} catch (InterruptedException e) {
			fail();
		}
		//从数据库中获取
		data = dataStatisticsCacheHandler.getRepaymentDataFromOrRefreshCache(financialContractIds);
		assertEquals(3,data.getOverdue_and_processing_unusual_payment_status_assets_nums());
	}
	
	@Test
	@Sql("classpath:test/yunxin/test_welcome_page_data/test_welcome_data_cache.sql")
	public void testGetData_when_evictCache(){
		// asset1,2为已逾期,asset3为待确认逾期， asset1为处理中,asset2,3为异常
		List<Long> financialContractIds = Arrays.asList(1L,2L);
		RepaymentDataStatistic data = dataStatisticsCacheHandler.getRepaymentDataFromOrRefreshCache(financialContractIds);
		assertEquals(2,data.getOverdue_and_processing_unusual_payment_status_assets_nums());
		
		//插入一条新的逾期处理中的asset
		Contract contract = contractService.load(Contract.class, 1L);
		AssetSet assetSet = new AssetSet(contract, 3, new Date(),new BigDecimal("1000"), new BigDecimal("10"),BigDecimal.ZERO);
		assetSet.setOverdueStatus(AuditOverdueStatus.OVERDUE);
		repaymentPlanService.save(assetSet);
		
		//查询验证为从缓存中获取
		data = dataStatisticsCacheHandler.getRepaymentDataFromOrRefreshCache(financialContractIds);
		assertEquals(2,data.getOverdue_and_processing_unusual_payment_status_assets_nums());
		
		dataStatisticsCacheHandler.cacheEvict(financialContractIds);
		//从数据库中获取
		data = dataStatisticsCacheHandler.getRepaymentDataFromOrRefreshCache(financialContractIds);
		assertEquals(3,data.getOverdue_and_processing_unusual_payment_status_assets_nums());
	}

	
	
}

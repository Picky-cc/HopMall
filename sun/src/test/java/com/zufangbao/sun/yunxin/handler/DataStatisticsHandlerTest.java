package com.zufangbao.sun.yunxin.handler;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.yunxin.entity.model.vo.welcom.RepaymentDataStatistic;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
})
public class DataStatisticsHandlerTest extends AbstractTransactionalJUnit4SpringContextTests{
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private DataStatHandler dataStatisticsHandler;
	@Autowired
	private ContractService contractService;
	
	@Test
	@Sql("classpath:test/yunxin/test_welcome_page_data/test_count_assets_of_processing_payment_status_and_not_overdue.sql")
	public void testCountRepaymentData_assets_of_processing_payment_status_and_not_overdue(){
		
		// asset1为未确认逾期， asset1，2，4已收。asset3为处理中
		RepaymentDataStatistic stat = dataStatisticsHandler.countRepaymentData(Arrays.asList(1L,2L));
		assertEquals(1,stat.getProcessing_payment_status_and_not_overdue_assets_nums());
	}
	
	@Test
	@Sql("classpath:test/yunxin/test_welcome_page_data/test_count_assets_of_unusual_payment_status_and_not_overdue.sql")
	public void testCountRepaymentData_count_assets_of_unusual_payment_status_and_not_overdue(){
		
		RepaymentDataStatistic stat = dataStatisticsHandler.countRepaymentData(Arrays.asList(1L,2L));
		assertEquals(2,stat.getUnusual_payment_status_and_not_overdue_assets_nums());
	}
	
	@Test
	@Sql("classpath:test/yunxin/test_welcome_page_data/test_count_assets_of_unconfirmed_overdue.sql")
	public void testCountRepaymentData_count_assets_of_unconfirmed_overdue(){
		// asset1,2为未确认逾期
		RepaymentDataStatistic stat = dataStatisticsHandler.countRepaymentData(Arrays.asList(1L,2L));
		assertEquals(2,stat.getUnconfirmed_overdue_assets_nums());
	}
	
	@Test
	@Sql("classpath:test/yunxin/test_welcome_page_data/test_count_assets_of_overdue_and_processing_unusual_payment_status.sql")
	public void testCountRepaymentData_count_assets_of_overdue_and_processing_unusual_payment_status(){
		// asset1,2，3为已逾期， asset1为处理中,asset2,3为异常
		RepaymentDataStatistic stat = dataStatisticsHandler.countRepaymentData(Arrays.asList(1L,2L));
		assertEquals(3,stat.getOverdue_and_processing_unusual_payment_status_assets_nums());
		stat = dataStatisticsHandler.countRepaymentData(Arrays.asList(1L));
		assertEquals(2,stat.getOverdue_and_processing_unusual_payment_status_assets_nums());
		stat = dataStatisticsHandler.countRepaymentData(Arrays.asList(2L));
		assertEquals(1,stat.getOverdue_and_processing_unusual_payment_status_assets_nums());
	}
}

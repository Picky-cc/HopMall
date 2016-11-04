package com.zufangbao.sun.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.yunxin.entity.remittance.ExecutionStatus;
import com.zufangbao.sun.yunxin.entity.remittance.RemittanceApplication;
import com.zufangbao.sun.yunxin.entity.remittance.RemittanceApplicationQueryModel;
import com.zufangbao.sun.yunxin.service.remittance.IRemittanceApplicationService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/context/applicationContext-*.xml", "classpath:/local/applicationContext-*.xml"})
public class RemittanceApplicationServiceTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Autowired
	private IRemittanceApplicationService remittanceApplicationService;
	
	@Test
	@Sql("classpath:/test/yunxin/remittance/test4RemittanceApplicationService.sql")
	public void testQueryRemittanceApplication_empty_exists_RequestNo(){
		String requestNo = new String();
		boolean result = remittanceApplicationService.existsRequestNo(requestNo);
		Assert.assertTrue(result);

		requestNo = new String("request_no1");
		result = remittanceApplicationService.existsRequestNo(requestNo);
		Assert.assertTrue(result);
		
		requestNo = new String("request_no4");
		result = remittanceApplicationService.existsRequestNo(requestNo);
		Assert.assertFalse(result);
	}
	
	@Test
	@Sql("classpath:/test/yunxin/remittance/test4RemittanceApplicationService.sql")
	public void testQueryRemittanceApplication_empty_execution_status_list(){
		RemittanceApplicationQueryModel queryModel = new RemittanceApplicationQueryModel();
		List<RemittanceApplication> list = remittanceApplicationService.queryRemittanceApplication(queryModel, null);
		Assert.assertEquals(0, list.size());
		
		queryModel = new RemittanceApplicationQueryModel();
		queryModel.setOrderStatus("[6,7]");
		list = remittanceApplicationService.queryRemittanceApplication(queryModel, null);
		Assert.assertEquals(0, list.size());
		
		queryModel = new RemittanceApplicationQueryModel();
		Page page = new Page(1);
		queryModel.setFinancialContractIds("[1]");//这个String类型为啥要用[]？为啥"[1，2]"也可以？
		queryModel.setOrderStatus("[2,3]");
		list = remittanceApplicationService.queryRemittanceApplication(queryModel, page);
		Assert.assertEquals(3, list.size());
	}
	
	@Test
	@Sql("classpath:/test/yunxin/remittance/test4RemittanceApplicationService.sql")
	public void testQueryRemittanceApplication_query_by_order_status_list(){
		RemittanceApplicationQueryModel queryModel = new RemittanceApplicationQueryModel();
		
		queryModel.setFinancialContractIds("[1]");
		queryModel.setOrderStatus("[2,3]");
		List<RemittanceApplication> list = remittanceApplicationService.queryRemittanceApplication(queryModel, null);
		Assert.assertEquals(3, list.size());
		
		queryModel = new RemittanceApplicationQueryModel();
		queryModel.setFinancialContractIds("[1]");
		queryModel.setOrderStatus("[2,3]");
		queryModel.setLoanContractNo("contract_no2");
		list = remittanceApplicationService.queryRemittanceApplication(queryModel, null);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals("contract_no2", list.get(0).getContractNo());

		queryModel = new RemittanceApplicationQueryModel();
		queryModel.setFinancialContractIds("[1]");
		queryModel.setOrderStatus("[2,3]");
//		queryModel.setLoanContractNo("contract_no1");
		queryModel.setOrderNo("remittance_application_uuid1");
		list = remittanceApplicationService.queryRemittanceApplication(queryModel, null);
		Assert.assertEquals(2, list.size());
		Assert.assertEquals("remittance_application_uuid1", list.get(0).getRemittanceApplicationUuid());
		
		queryModel = new RemittanceApplicationQueryModel();
		queryModel.setFinancialContractIds("[1]");
		queryModel.setOrderStatus("[2,3]");
//		queryModel.setLoanContractNo("contract_no1");
//		queryModel.setOrderNo("remittance_application_uuid1");
		queryModel.setReceiveStartDate("2016-09-19 00:00:00");
		list = remittanceApplicationService.queryRemittanceApplication(queryModel, null);
		Assert.assertEquals(2, list.size());
		Date expectedDate = DateUtils.parseDate("2016-09-19 17:32:23", "yyyy-MM-dd HH:mm:ss");
		Assert.assertEquals(expectedDate, list.get(0).getCreateTime());
		
		queryModel = new RemittanceApplicationQueryModel();
		queryModel.setFinancialContractIds("[1]");
		queryModel.setOrderStatus("[2,3]");
//		queryModel.setLoanContractNo("contract_no1");
//		queryModel.setOrderNo("remittance_application_uuid1");
		queryModel.setReceiveEndDate("2016-09-20");
		list = remittanceApplicationService.queryRemittanceApplication(queryModel, null);
		Assert.assertEquals(3, list.size());
		Date expectedDate1 = DateUtils.parseDate("2016-09-19 17:32:23", "yyyy-MM-dd HH:mm:ss");
		Assert.assertEquals(expectedDate1, list.get(1).getCreateTime());
	}
	
	@Test
	@Sql("classpath:/test/yunxin/remittance/test4RemittanceApplicationService.sql")
	public void testQueryRemittanceApplication_empty_query_RemittanceApplication_Count(){
		RemittanceApplicationQueryModel queryModel = new RemittanceApplicationQueryModel();
		int i = remittanceApplicationService.queryRemittanceApplicationCount(queryModel);
		Assert.assertEquals(0, i);
		
		queryModel = new RemittanceApplicationQueryModel();
		queryModel.setOrderStatus("[6,7]");
		i = remittanceApplicationService.queryRemittanceApplicationCount(queryModel);
		Assert.assertEquals(0, i);
		
		queryModel = new RemittanceApplicationQueryModel();
		queryModel.setFinancialContractIds("[1]");
		queryModel.setOrderStatus("[2,3]");
		i = remittanceApplicationService.queryRemittanceApplicationCount(queryModel);
		Assert.assertEquals(3, i);
	}
	
	@Test
	@Sql("classpath:/test/yunxin/remittance/test4RemittanceApplicationService.sql")
	public void testQueryRemittanceApplication_empty_get_Unique_RemittanceApplication_ByUuid(){
		String remittanceApplicationUuid = new String();
		RemittanceApplication remittanceApplication = remittanceApplicationService.getUniqueRemittanceApplicationByUuid(remittanceApplicationUuid);
		Assert.assertNull(remittanceApplication);
		
		remittanceApplicationUuid = new String("remittance_application_uuid1");
		remittanceApplication = remittanceApplicationService.getUniqueRemittanceApplicationByUuid(remittanceApplicationUuid);
		Date expectedDate1 = DateUtils.parseDate("2016-09-18 17:32:23", "yyyy-MM-dd HH:mm:ss");
		Assert.assertEquals(expectedDate1, remittanceApplication.getCreateTime());
	
		remittanceApplicationUuid = new String("remittance_application_uuid4");
		remittanceApplication = remittanceApplicationService.getUniqueRemittanceApplicationByUuid(remittanceApplicationUuid);
		Assert.assertNull(remittanceApplication);
	}
	
	@Test
	@Sql("classpath:/test/yunxin/remittance/test4RemittanceApplicationService.sql")
	public void testQueryRemittanceApplication_get_RemittanceApplications_By(){
		String contractUniqueId = new String();
		List<RemittanceApplication> list = remittanceApplicationService.getRemittanceApplicationsBy(contractUniqueId);
//		Assert.assertEquals(0,list.size()); //是因为返回值是null吗？
		Assert.assertNull(list);
			
		contractUniqueId = new String("   ");
		list = remittanceApplicationService.getRemittanceApplicationsBy(contractUniqueId);
		Assert.assertNull(list);	
		
		contractUniqueId = new String("contract_unique_id1");
		list = remittanceApplicationService.getRemittanceApplicationsBy(contractUniqueId);
		Assert.assertEquals(1,list.size());	
	}
	

	@Test
	@Sql("classpath:/test/yunxin/remittance/test4RemittanceApplicationService.sql")
	public void testQueryRemittanceApplication_count_RemittanceApplications_By(){
		List<Long> financialContractIds = new ArrayList<Long>();
		List<ExecutionStatus> executionStatusList = new ArrayList<ExecutionStatus>();
		Date calculateDate = new Date();
		int count = remittanceApplicationService.countRemittanceApplicationsBy(financialContractIds, executionStatusList, calculateDate);
		Assert.assertEquals(0,count);
		 
		financialContractIds = new ArrayList<Long>();
		financialContractIds.add(1L);
		executionStatusList = new ArrayList<ExecutionStatus>();
		calculateDate = new Date();
		count = remittanceApplicationService.countRemittanceApplicationsBy(financialContractIds, executionStatusList, calculateDate);
		Assert.assertEquals(0,count);
				
		financialContractIds = new ArrayList<Long>();
		financialContractIds.add(1L);
		executionStatusList = new ArrayList<ExecutionStatus>();
		RemittanceApplicationQueryModel queryModel = new RemittanceApplicationQueryModel();
		queryModel.setOrderStatus("[2,3]");
		executionStatusList = queryModel.getExecutionStatusEnumList();
		calculateDate = null; //怎么使Date对象的值为空？
		count = remittanceApplicationService.countRemittanceApplicationsBy(financialContractIds, executionStatusList, calculateDate);
		Assert.assertEquals(0,count);

		financialContractIds = new ArrayList<Long>();
		financialContractIds.add(1L);
		executionStatusList = new ArrayList<ExecutionStatus>();
		queryModel = new RemittanceApplicationQueryModel();
		queryModel.setOrderStatus("[2]");
		executionStatusList = queryModel.getExecutionStatusEnumList();
		calculateDate = DateUtils.parseDate("2016-09-19 17:32:23", "yyyy-MM-dd HH:mm:ss");
//		calculateDate = new Date(2016,9,19,17,32,23); //这个值应该怎么传？
		count = remittanceApplicationService.countRemittanceApplicationsBy(financialContractIds, executionStatusList, calculateDate);
		Assert.assertEquals(2,count);
	}
	
	@Test
	@Sql("classpath:/test/yunxin/remittance/test4RemittanceApplicationService.sql")
	public void testQueryRemittanceApplication_add_Plan_Notify_Number(){
		String remittanceApplicationUuid = new String("remittance_application_uuid1");
		int number = 1;
		boolean status = remittanceApplicationService.addPlanNotifyNumber(remittanceApplicationUuid, number);
		Assert.assertTrue(status); //这个怎么看sql执行成功的效果？
		Assert.assertEquals(1,remittanceApplicationService.load(RemittanceApplication.class, 1L).getPlanNotifyNumber());
	}
}

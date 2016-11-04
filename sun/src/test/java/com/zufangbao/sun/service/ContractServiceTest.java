package com.zufangbao.sun.service;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.zufangbao.sun.VO.ProjectInformationSQLReturnData;
import com.zufangbao.sun.yunxin.entity.model.ProjectInformationQueryModel;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml"})
@Transactional()
@TransactionConfiguration(defaultRollback=true)
public class ContractServiceTest extends AbstractTransactionalJUnit4SpringContextTests
 {

	@Autowired
	private ContractService contractService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private AppService appService;
	@Autowired
	private HouseService houseService;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private FinancialContractService financialContractService;
	@Autowired
	private AssetPackageService assetPackageService;
	@Autowired
	private ContractAccountService contractAccountService;
	@Autowired
	private GenericDaoSupport genericDaoSupport;
	
	@Test
	@Sql("classpath:test/yunxin/Contract/testQueryLoanContractForNormal.sql")
	public void testGetContractBy(){
		
		ProjectInformationQueryModel projectInformationQueryModel = new ProjectInformationQueryModel();
		projectInformationQueryModel.setContractNo("云信");
		List<ProjectInformationSQLReturnData> datas = contractService.getContractListBy(projectInformationQueryModel, null);
		Assert.assertEquals(1, datas.size());
		ProjectInformationSQLReturnData data = datas.get(0);
		Assert.assertEquals(325l , data.getId().longValue());
		Assert.assertEquals("2016-06-17", data.getMaxAssetRecycleDate());
	}
	
	
	@Test
	@Sql("classpath:test/yunxin/Contract/testQueryLoanContractForNormal.sql")
	public void testGetContractBytestDate(){
		
		ProjectInformationQueryModel projectInformationQueryModel = new ProjectInformationQueryModel();
		projectInformationQueryModel.setFinancialContractId(-1l);
		projectInformationQueryModel.setContractNo("云信");
		projectInformationQueryModel.setLoanExpectTerminateStartDate("2016-06-17");
		projectInformationQueryModel.setLoanExpectTerminateEndDate("2016-06-17");
		
		
		List<ProjectInformationSQLReturnData> datas = contractService.getContractListBy(projectInformationQueryModel, null);
		Assert.assertEquals(1, datas.size());
		ProjectInformationSQLReturnData data = datas.get(0);
		Assert.assertEquals(325l, data.getId().longValue());
		Assert.assertEquals("2016-06-17", data.getMaxAssetRecycleDate());
	}
	
	@Test
	@Sql("classpath:test/yunxin/Contract/testGetContractBytestDateNoDataw.sql")
	public void testGetContractBytestDateNoDataw(){
		
		ProjectInformationQueryModel projectInformationQueryModel = new ProjectInformationQueryModel();
		projectInformationQueryModel.setFinancialContractId(-1l);
		projectInformationQueryModel.setContractNo("云信");
		projectInformationQueryModel.setLoanExpectTerminateStartDate("2016-06-17");
		projectInformationQueryModel.setLoanExpectTerminateEndDate("2016-06-17");
		
		
		List<ProjectInformationSQLReturnData> data = contractService.getContractListBy(projectInformationQueryModel, null);
		Assert.assertEquals(0, data.size());
	}
}

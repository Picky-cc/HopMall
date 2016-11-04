package com.zufangbao.sun.service.impl;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.zufangbao.sun.AutoTestUtils;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.service.ContractService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "classpath:/context/applicationContext-*.xml",
    "classpath:/local/applicationContext-*.xml" })
public class ContractServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Autowired
	private ContractService contractService;
	@Autowired
	private DataSource	dataSource;
	@Autowired
	private GenericDaoSupport genericDaoSupport;
	
	@Test
	@Sql("classpath:test/getContractByContractNoTestData.sql")
	public void getContractByExistContractNoTestData(){
		String contract_no = "KXHY1#1803";
		Contract contract = this.contractService.getContractByContractNo(contract_no);
		Assert.assertEquals(contract_no, contract.getContractNo());
	}
	
	@Test
	@Sql("classpath:test/getContractByContractNoTestData.sql")
	public void getContractByNotExistContractNoTestData(){
		String contract_no = "KXHY1dddd#1803";
		Contract contract = this.contractService.getContractByContractNo(contract_no);
		Assert.assertNull(contract);
	}
	@Test
	public void getContractByNotExistContractIdTest(){
		AutoTestUtils.dbExecute("test/getContractTestData.sql", dataSource);
		Long id = 20L;//not exits
		Contract contract = this.contractService.getContract(id);
		Assert.assertNull(contract);
	}
	@Test
	public void getContractByNullContractIdTest(){
		Long id = null;
		Contract contract = this.contractService.getContract(id);
		Assert.assertNull(contract);
	}
	@Test
	public void getContractByNormalConditionTest(){
		AutoTestUtils.dbExecute("test/getContractTestData.sql", dataSource);
		Long id = 19L;
		Contract contract = this.contractService.getContract(id);
		Contract contract2 = this.genericDaoSupport.load(Contract.class, 19L);
		Assert.assertNotNull(contract);
		Assert.assertEquals(Long.valueOf(19L),contract2.getId());
	}

}

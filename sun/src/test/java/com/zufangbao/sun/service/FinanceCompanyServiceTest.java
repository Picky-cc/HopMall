package com.zufangbao.sun.service;

import static org.junit.Assert.assertEquals;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@Transactional()
public class FinanceCompanyServiceTest {
	@Autowired
	private FinanceCompanyService financeCompanyService;
	
	@Test
	@Sql("classpath:test/yunxin/testFinanceCompany.sql")
	public void testGetCompanyIdOfOneFinanceCompany(){
		Long companyId = financeCompanyService.getCompanyIdOfOneFinanceCompany();
		assertEquals(new Long(1L),companyId);
	}
}

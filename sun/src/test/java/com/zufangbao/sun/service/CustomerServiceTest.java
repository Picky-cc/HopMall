package com.zufangbao.sun.service;

import javax.sql.DataSource;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.zufangbao.sun.AutoTestUtils;
import com.zufangbao.sun.entity.customer.Customer;
/**
 * 
 * @author Dong
 * @Date 2015-5-27
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml"})
@TransactionConfiguration(defaultRollback=true)
@Transactional
public class CustomerServiceTest {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private GenericDaoSupport genericDaoSupport;
	@Autowired
	private CustomerService customerService;
	
	@Test
	public void getCustomerByEmptyAppIdAndNullCustomerId(){
		String appId = null;
		Long customerId = null;
		Customer customer = this.customerService.getCustomer(appId, customerId);
		Assert.assertNull(customer);
	}
	@Test
	public void getCustomerByAppIdAndCustomerIdConflicting(){
		AutoTestUtils.dbExecute("test/getCustomerTestData.sql", dataSource);
		String appId = "zhangsan";
		Long customerId = 13L;
		Customer customer = this.customerService.getCustomer(appId, customerId);
		Assert.assertNull(customer);
	}
	@Test
	public void getCustomerByAppIdAndCustomerIdNormalCondition(){
		AutoTestUtils.dbExecute("test/getCustomerTestData.sql", dataSource);
		String appId = "youpark";
		Long customerId = 107L;
		Customer customer = this.customerService.getCustomer(appId, customerId);
		Assert.assertEquals(Long.valueOf(107L), customer.getId());
		Assert.assertEquals(appId, customer.getApp().getAppId());
	}
}

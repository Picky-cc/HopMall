package com.zufangbao.sun.service.impl;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.house.House;
import com.zufangbao.sun.service.HouseService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HouseServiceTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Resource
	private HouseService hourseService;
	@Resource
	private GenericDaoSupport genericDaoSupport;
	
	@Test
	@Sql("classpath:test/hourseServiceImplDataTest.sql")
	public void houseServiceImplTest(){
		
		Contract contract = this.genericDaoSupport.load(Contract.class, 11L);
		House hourse = contract.getHouse();
		Assert.assertEquals("汇川路88号1#1803", hourse.getAddress());
	}
}

package com.zufangbao.earth.yunxin.handler.impl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.zufangbao.earth.yunxin.handler.reportform.LoansReportFormHandler;
import com.zufangbao.sun.yunxin.entity.model.reportform.LoansQueryModel;
import com.zufangbao.sun.yunxin.entity.model.reportform.LoansShowModel;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@Transactional
public class LoansReportFormHandlerTest {
	@Autowired 
	private LoansReportFormHandler handler;
	
	@Test
	@Sql("classpath:test/yunxin/reportform/loans/LoadAllLoans.sql")
	public void testLoadAllLoans() {
		LoansQueryModel queryModel = new LoansQueryModel();
		List<LoansShowModel> list = handler.query(queryModel);
		
	}
}

package com.zufangbao.earth.yunxin.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.web.servlet.ModelAndView;

import com.demo2do.core.entity.Result;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.service.PrincipalService;
import com.zufangbao.earth.yunxin.web.controller.AssetSetController;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
		"classpath:/DispatcherServlet.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional()
public class AssetSetControllerTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private PrincipalService principalService;
	@Autowired
	private AssetSetController assetSetController;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Test
	@Sql("classpath:test/yunxin/assets/test_asset_confirm_recycleDate.sql")
	public void testPreConfirmPayTime(){
		//资产1未结转不可修改
		Long assetSetId = 1L;
		ModelAndView modelAndView = assetSetController.preConfirmPayTime(assetSetId);
		assertEquals("error-modal",modelAndView.getViewName());
		
		assetSetId = 2L;
		modelAndView = assetSetController.preConfirmPayTime(assetSetId);
		assertEquals("assets/assets-pre-confirm-recycle-date",modelAndView.getViewName());
		AssetSet assetSet = (AssetSet)modelAndView.getModel().get("assetSet");
		assertEquals(new Long(2L),assetSet.getId());
		
	}
	
	@Test
	@Sql("classpath:test/yunxin/assets/test_asset_confirm_recycleDate.sql")
	public void testConfirmPayTime(){
		//资产1未结转不可修改
		Principal principal = principalService.load(Principal.class, 1L);
		Long assetSetId = 1L;
		String confirmRecycleDateString = "2016-01-01";
		String comment = "comment";
		String resultJson = assetSetController.confirmPayTime(principal, assetSetId, confirmRecycleDateString, comment);
		Result result = JsonUtils.parse(resultJson,Result.class);
		assertFalse(result.isValid());
		AssetSet assetSet1InDb = repaymentPlanService.load(AssetSet.class, assetSetId);
		assertNull(assetSet1InDb.getConfirmRecycleDate());
		assertFalse(StringUtils.equals(comment, assetSet1InDb.getComment()));
		
		//成功
		assetSetId = 2L;
		resultJson = assetSetController.confirmPayTime(principal, assetSetId, confirmRecycleDateString, comment);
		result = JsonUtils.parse(resultJson,Result.class);
		assertTrue(result.isValid());
		AssetSet assetSet = repaymentPlanService.load(AssetSet.class, assetSetId);
		assertNotNull(assetSet.getConfirmRecycleDate());
		assertEquals(0,DateUtils.compareTwoDatesOnDay(DateUtils.asDay(confirmRecycleDateString),assetSet.getConfirmRecycleDate()));
		assertTrue(StringUtils.equals(comment, assetSet.getComment()));
	}
	
	@Test
	@Sql("classpath:test/yunxin/assets/test_asset_confirm_recycleDate.sql")
	public void testConfirmPayTimeForInValid(){
		Principal principal = principalService.load(Principal.class, 1L);
		Long assetSetId = 1L;
		String confirmRecycleDateString = "";
		String comment = "comment";
		//传入null则不变
		assetSetId = 2L;
		String resultJson = assetSetController.confirmPayTime(principal, assetSetId, confirmRecycleDateString, comment);
		Result result = JsonUtils.parse(resultJson,Result.class);
		assertFalse(result.isValid());
		AssetSet assetSet = repaymentPlanService.load(AssetSet.class, assetSetId);
		assertNull(assetSet.getConfirmRecycleDate());
		assertFalse(StringUtils.equals(comment, assetSet.getComment()));
	}
	
	@Test
	@Sql("classpath:test/yunxin/assets/test_asset_refund.sql")
	public void testPreUpdateRefund(){
		Long assetSetId = 2L;
		ModelAndView modelAndView = assetSetController.preUpdateRefund(assetSetId);
		assertEquals("assets/assets-pre-update-refund",modelAndView.getViewName());
		AssetSet assetSet = (AssetSet)modelAndView.getModel().get("assetSet");
		assertEquals(new Long(assetSetId),assetSet.getId());
	}

	@Test
	@Sql("classpath:test/yunxin/assets/test_asset_refund.sql")
	public void testUpdateRefund(){
		//asset1未结转不可操作
		Long assetSetId = 1L;
		Principal principal = principalService.load(Principal.class, 1L);
		String comment = "comment";
		BigDecimal refundAmount = new BigDecimal("10.00");
		String resultJson = assetSetController.updateRefund(principal, assetSetId, refundAmount, comment);
		Result result = JsonUtils.parse(resultJson,Result.class);
		assertFalse(result.isValid());
		
		//asset2已结转
		assetSetId = 2L;
		principal = principalService.load(Principal.class, 1L);
		comment = "comment";
		refundAmount = new BigDecimal("10.00");
		resultJson = assetSetController.updateRefund(principal, assetSetId, refundAmount, comment);
		result = JsonUtils.parse(resultJson,Result.class);
		assertTrue(result.isValid());
		AssetSet assetSet = repaymentPlanService.load(AssetSet.class, assetSetId);
		assertTrue(StringUtils.equals(comment,assetSet.getComment()));
		assertEquals(0,refundAmount.compareTo(assetSet.getRefundAmount()));
	}
}

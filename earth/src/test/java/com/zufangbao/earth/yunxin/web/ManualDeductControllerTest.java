package com.zufangbao.earth.yunxin.web;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.demo2do.core.entity.Result;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.model.ManualDeductWebModel;
import com.zufangbao.earth.service.PrincipalService;
import com.zufangbao.earth.yunxin.web.controller.ManualDeductController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml",
		"classpath:/DispatcherServlet.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional()
public class ManualDeductControllerTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private PrincipalService principalService;
	@Autowired
	private ManualDeductController batchDeductManualController;
	
	//此方法非单元测试，用于实际环境银联扣款的测试。
	//@Test
	//@Sql("classpath:test/yunxin/test_batch_deduct_manual.sql")
	public void testManualBatchDeduct(){
		String bankCode="102";
		String accountNo="605810028220436120";
		String accountName = "邹力";
		BigDecimal amount =new BigDecimal("0.01");
		String remark = "remark";
		String idCardNum=null;
		String province=null;
		String city=null;
		ManualDeductWebModel manualBatchDeductWebModel = new ManualDeductWebModel(bankCode, accountNo,
				accountName, amount, remark, idCardNum, province, city);
		Long paymentChannelId = 1L;
		Principal principal = principalService.load(Principal.class, 1L);
		String resultJson = batchDeductManualController.manualDeduct(principal, manualBatchDeductWebModel, paymentChannelId,1);
		Result result = JsonUtils.parse(resultJson,Result.class);
		assertTrue(result.isValid());
	}
}

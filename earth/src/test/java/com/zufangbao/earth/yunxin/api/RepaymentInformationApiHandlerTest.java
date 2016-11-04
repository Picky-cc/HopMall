package com.zufangbao.earth.yunxin.api;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.exception.ApiResponseCode;
import com.zufangbao.earth.yunxin.api.handler.RepaymentInformationApiHandler;
import com.zufangbao.earth.yunxin.api.model.modify.RepaymentInformationModifyModel;
import com.zufangbao.earth.yunxin.api.model.modify.UpdateRepaymentInformationLog;
import com.zufangbao.earth.yunxin.api.service.UpdateRepaymentInformationLogService;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.service.ContractAccountService;
import com.zufangbao.sun.service.ContractService;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@Transactional
public class RepaymentInformationApiHandlerTest {

	@Autowired
	private RepaymentInformationApiHandler repaymentInformationApiHandler;

	@Autowired
	private ContractService contractService;
	@Autowired
	private ContractAccountService contractAccountService;
	@Autowired
	private UpdateRepaymentInformationLogService updateRepaymentInformationLogService;
	
	@Test
	@Sql("classpath:test/yunxin/api/testModifyRepaymentInformationNormal.sql")
	public void testModifyRepaymentInformationNormal(){
			
		    RepaymentInformationModifyModel modifyModel = new RepaymentInformationModifyModel();
			MockHttpServletRequest  request = new MockHttpServletRequest();
			modifyModel.setRequestNo("1234567");
			modifyModel.setContractNo("云信信2016-78-DK(ZQ2016042522479)");
			modifyModel.setBankCode("678");
			modifyModel.setBankAccount("sdfghjkl");
			Contract contract = contractService.load(Contract.class, 323l);
			repaymentInformationApiHandler.modifyRepaymentInformation(modifyModel, request, contract);;
			
			ContractAccount contractAccout = contractAccountService.getTheEfficientContractAccountBy(contract);
			Assert.assertEquals("678", contractAccout.getBankCode());
			Assert.assertEquals("sdfghjkl", contractAccout.getPayAcNo());
			List<UpdateRepaymentInformationLog> logs = updateRepaymentInformationLogService.loadAll(UpdateRepaymentInformationLog.class);
			Assert.assertEquals(1, logs.size());
			Assert.assertEquals(contract, logs.get(0).getContract());
			Assert.assertEquals("1234567", logs.get(0).getRequestNo());
	}
	
	@Test
	@Sql("classpath:test/yunxin/api/testModifyRepaymentInformationForRequestNoRepeat.sql")
	public void testModifyRepaymentInformationForRequestNoRepeat(){
			
		    RepaymentInformationModifyModel modifyModel = new RepaymentInformationModifyModel();
			MockHttpServletRequest  request = new MockHttpServletRequest();
			modifyModel.setRequestNo("1234567");
			modifyModel.setContractNo("云信信2016-78-DK(ZQ2016042522479)");
			modifyModel.setBankCode("678");
			modifyModel.setBankAccount("sdfghjkl");
			Contract contract = contractService.load(Contract.class, 323l);
			
			try {
				repaymentInformationApiHandler.modifyRepaymentInformation(modifyModel, request, contract);
				
			} catch (ApiException e) {
				Assert.assertEquals(ApiResponseCode.REPEAT_REQUEST_NO, e.getCode());
			}
			
	}
	
	
	
	
}

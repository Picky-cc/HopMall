package com.zufangbao.earth.yunxin.api;

import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.CONTRACT_NOT_EXIST;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.handler.ContractApiHandler;
import com.zufangbao.earth.yunxin.api.util.ApiMessageUtil;
import com.zufangbao.sun.entity.contract.Contract;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class AssetSetApiHandlerTest {
	
	@Autowired
	private ContractApiHandler contractApiHandler;
	
	@Test
	@Sql("classpath:test/yunxin/api/GetContract_throw_error.sql")
	public void testGetContract_throw_error() {
		try {
			contractApiHandler.getContractBy("", "");
			Assert.fail();
		} catch (ApiException e) {
			Assert.assertEquals(CONTRACT_NOT_EXIST, e.getCode());
			Assert.assertEquals(ApiMessageUtil.getMessage(CONTRACT_NOT_EXIST), e.getMsg());
		}
	}
	
	@Test
	@Sql("classpath:test/yunxin/api/GetContract.sql")
	public void testGetContract() {
		try {
			String contractNo = "云信信2016-78-DK(ZQ2016000000001)";
			Contract contract = contractApiHandler.getContractBy("", contractNo);
			Assert.assertEquals(contractNo, contract.getContractNo());
			Assert.assertEquals(1L, contract.getId().longValue());
		} catch (ApiException e) {
			Assert.fail();
		}
	}
}

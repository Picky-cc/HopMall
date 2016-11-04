package com.zufangbao.earth.api.test.post;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

public class ModifyRepaymentInformationApiPostTest extends BaseApiTestPost{

	@Test
	public void  testApi() {
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("fn", "200003");
		requestParams.put("requestNo",UUID.randomUUID().toString());
		requestParams.put("uniqueId", "db74cb60-4ab3-4796-8768-26010a0f5a82");
		requestParams.put("contractNo", "");
		requestParams.put("bankCode", "C10105");
		requestParams.put("bankAccount", "6217001210075327591");
		requestParams.put("bankName", "jianzhang");
		requestParams.put("bankProvince", "310000");
		requestParams.put("bankCity", "310100");
		try {
			String sr = PostTestUtil.sendPost(MODIFY_URL_TEST, requestParams, getIdentityInfoMap(requestParams));
			System.out.println(sr+"1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void  testApiUnqueIdAndContractNo() {
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("fn", "200003");
		requestParams.put("requestNo", "123456789");
		requestParams.put("uniqueId", "12342354");
		requestParams.put("contractNo", "云信信2016-78-DK(ZQ2016042522479)");
		requestParams.put("bankCode", "604");
		requestParams.put("bankAccount", "bankAccount");
		try {
			String sr = PostTestUtil.sendPost(MODIFY_URL_TEST, requestParams, getIdentityInfoMap(requestParams));
			System.out.println(sr+"2");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void  testApiNoBankNo() {
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("fn", "200003");
		requestParams.put("requestNo", "1s23456789");
		requestParams.put("uniqueId", "12342354");
		requestParams.put("contractNo", "云信信2016-78-DK(ZQ2016042522479)");
		requestParams.put("bankCode", "");
		requestParams.put("bankAccount", "bankAccount");
		try {
			String sr = PostTestUtil.sendPost(MODIFY_URL_TEST, requestParams, getIdentityInfoMap(requestParams));
			System.out.println(sr+"3");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void  testApiNoBankAccount() {
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("fn", "200003");
		requestParams.put("requestNo", "123d456789");
		requestParams.put("uniqueId", "12342354");
		requestParams.put("contractNo", "云信信2016-78-DK(ZQ2016042522479)");
		requestParams.put("bankCode", "asdrt");
		requestParams.put("bankAccount", "");
		try {
			String sr = PostTestUtil.sendPost(MODIFY_URL_TEST, requestParams, getIdentityInfoMap(requestParams));
			System.out.println(sr+"4");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void  testApiNoBankNoContract() {
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("fn", "200003");
		requestParams.put("requestNo", "1234567f89");
		requestParams.put("uniqueId", "");
		requestParams.put("contractNo", "云信信2016-78-DK(ZQ201042522479)");
		requestParams.put("bankCode", "asdrt");
		requestParams.put("bankAccount", "asdas");
		try {
			String sr = PostTestUtil.sendPost(MODIFY_URL_TEST, requestParams, getIdentityInfoMap(requestParams));
			System.out.println(sr+"5");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

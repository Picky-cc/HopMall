package com.zufangbao.earth.api.test.post;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

public class QueryRepaymentPlanApiPostTest extends BaseApiTestPost{
	
	@Test
	public void testApiQueryRepaymentPlan() {
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("fn", "100002");
		requestParams.put("contractNo", "");
		requestParams.put("requestNo", UUID.randomUUID().toString());
		requestParams.put("uniqueId", "b1983b3f-88d5-45ae-aacc-90118f430718");
		try {
			String sr = PostTestUtil.sendPost(QUERY_URL_TEST, requestParams, getIdentityInfoMap(requestParams));
			System.out.println(sr+1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testApiQueryRepaymentPlanUniqueId() {
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("fn", "100001");
		requestParams.put("contractNo", "");
		requestParams.put("requestNo", "456");
		requestParams.put("uniqueId", "12345678");
		try {
			String sr = PostTestUtil.sendPost(QUERY_URL_TEST, requestParams, getIdentityInfoMap(requestParams));
			System.out.println(sr+2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public  void testApiQueryRepaymentError(){
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("fn", "100001");
		requestParams.put("contractNo", "2016-78-DK(ZQ2016042522479)");
		requestParams.put("requestNo", "456");
		requestParams.put("uniqueId", "");
		try {
			String sr = PostTestUtil.sendPost(QUERY_URL_TEST, requestParams, getIdentityInfoMap(requestParams));
			System.out.println(sr+3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public  void testApiQueryRepaymentError_no(){
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("fn", "100001");
		requestParams.put("requestNo", "");
		try {
			String sr = PostTestUtil.sendPost(QUERY_URL_TEST, requestParams, getIdentityInfoMap(requestParams));
			System.out.println(sr+4);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

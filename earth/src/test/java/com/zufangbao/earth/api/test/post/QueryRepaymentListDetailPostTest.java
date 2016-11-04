package com.zufangbao.earth.api.test.post;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class QueryRepaymentListDetailPostTest extends BaseApiTestPost{
	
	@Test
	public void testApiQueryRepaymentList() {
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("fn", "100003");
		requestParams.put("requestNo", "123456789");
		requestParams.put("financialContractNo", "DCF-NFQ-LR903");
		requestParams.put("queryStartDate", "2016-06-03");
		requestParams.put("queryEndDate", "2016-07-04");
		try {
			String sr = PostTestUtil.sendPost(QUERY_URL_TEST, requestParams, getIdentityInfoMap(requestParams));
			System.out.println(sr+1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testApiQueryRepaymentListForNoFinancialContractNo() {
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("fn", "100003");
		requestParams.put("requestNo", "123456789");
		requestParams.put("financialContractNo", "");
		requestParams.put("queryStartDate", "2016-06-03");
		requestParams.put("queryEndDate", "2016-07-04");
		try {
			String sr = PostTestUtil.sendPost(QUERY_URL_TEST, requestParams, getIdentityInfoMap(requestParams));
			System.out.println(sr+2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testApiQueryRepaymentListForDateRangeErrorNotThan3Months() {
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("fn", "100003");
		requestParams.put("requestNo", "123456789");
		requestParams.put("financialContractNo", "DCF-NFQ-LR903");
		requestParams.put("queryStartDate", "2016-06-03");
		requestParams.put("queryEndDate", "2016-10-04");
		try {
			String sr = PostTestUtil.sendPost(QUERY_URL_TEST, requestParams, getIdentityInfoMap(requestParams));
			System.out.println(sr+3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void  testApiQueryRepaymentListForDateRangeError() {
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("fn", "100003");
		requestParams.put("requestNo", "123456789");
		requestParams.put("financialContractNo", "DCF-NFQ-LR903");
		requestParams.put("queryStartDate", "2016-08-03");
		requestParams.put("queryEndDate", "2016-07-04");
		try {
			String sr = PostTestUtil.sendPost(QUERY_URL_TEST, requestParams, getIdentityInfoMap(requestParams));
			System.out.println(sr+4);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void  testApiQueryRepaymentListForOverDueRepaymentList() {
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("fn", "100003");
		requestParams.put("requestNo", "123456789");
		requestParams.put("financialContractNo", "DCF-NFQ-LR903");
		requestParams.put("queryStartDate", "2016-06-16");
		requestParams.put("queryEndDate", "2016-06-16");
		try {
			String sr = PostTestUtil.sendPost(QUERY_URL_TEST, requestParams, getIdentityInfoMap(requestParams));
			System.out.println(sr+5);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void  testApiQueryRepaymentListForNoQueryDate() {
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("fn", "100003");
		requestParams.put("requestNo", "123456789");
		requestParams.put("financialContractNo", "DCF-NFQ-LR903");
		requestParams.put("queryStartDate", "");
		requestParams.put("queryEndDate", "2016-06-16");
		try {
			String sr = PostTestUtil.sendPost(QUERY_URL_TEST, requestParams, getIdentityInfoMap(requestParams));
			System.out.println(sr+6);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

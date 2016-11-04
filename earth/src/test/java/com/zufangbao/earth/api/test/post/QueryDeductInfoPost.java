package com.zufangbao.earth.api.test.post;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

public class QueryDeductInfoPost extends BaseApiTestPost{
	
	
	
	
	@Test
	public void testDeductInfoList() {
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("fn", "100002");
		requestParams.put("deductId", "8c38bd21-d8a8-45cb-8fce-2c69aa89bf04");
		requestParams.put("requestNo", UUID.randomUUID().toString());
		requestParams.put("uniqueId", "808f4803-6836-49f3-b0b7-f98f27a27980");
		try {
			String sr = PostTestUtil.sendPost(QUERY_URL_TEST, requestParams, getIdentityInfoMap(requestParams));
			System.out.println(sr+1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

package com.zufangbao.earth.api.test.post;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * 提前全额还款post请求
 * @author zhanghongbing
 *
 */
public class ModifyPrepaymentApiPost extends BaseApiTestPost{
	
	@Test
	public void testApiModifyPrepaymentPlan() {
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("fn", "200002");
		requestParams.put("uniqueId", "");
		requestParams.put("contractNo", "20160801测试-2");
		requestParams.put("requestNo", "3");
		requestParams.put("assetRecycleDate", "2016-08-02");
		requestParams.put("assetInitialValue", "600");
		requestParams.put("type", "0");
		
		try {
			String sr = PostTestUtil.sendPost(MODIFY_URL_TEST, requestParams, getIdentityInfoMap(requestParams));
			System.out.println(sr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

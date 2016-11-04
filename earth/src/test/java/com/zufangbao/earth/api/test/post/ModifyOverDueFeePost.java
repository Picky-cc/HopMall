package com.zufangbao.earth.api.test.post;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

public class ModifyOverDueFeePost extends BaseApiTestPost {

	@Test
	public void testModifyOverDueFeePost(){
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("fn", "200005");
		requestParams.put("requestNo", UUID.randomUUID().toString());
		requestParams.put("modifyOverDueFeeDetails", "[{\"contractUniqueId\":\"c2b7d20e-a371-4d92-b847-a862ea2a79b7\",\"lateFee\":\"100.00\",\"lateOtherCost\":\"2.00\",\"latePenalty\":\"2.00\",\"overDueFeeCalcDate\":\"2016-11-08\",\"penaltyFee\":\"2.00\",\"repaymentPlanNo\":\"ZC2749A9264955607E\",\"totalOverdueFee\":\"106.00\"}]");
		
		try {
			String sr = PostTestUtil.sendPost(MODIFY_URL_TEST, requestParams, getIdentityInfoMap(requestParams));
			System.out.println(sr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

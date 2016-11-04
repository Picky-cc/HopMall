package com.zufangbao.earth.api.test.post;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

public class CommandOverdueDeductApiPost extends BaseApiTestPost{
	
	@Test
	public void testApiCommandOverdueDuduct() {
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("fn", "300001");
		requestParams.put("requestNo", UUID.randomUUID().toString());
		requestParams.put("deductId",  UUID.randomUUID().toString());
		requestParams.put("financialProductCode", "G31700");
		requestParams.put("uniqueId", "c2b7d20e-a371-4d92-b847-a862ea2a79b7");
		requestParams.put("apiCalledTime", "2016-09-23");
		requestParams.put("amount", "216");
		requestParams.put("repaymentType", "2");
		requestParams.put("repaymentDetails", "[{'loanFee':0.00,'otherFee':0.00,'repaymentAmount':216,'repaymentInterest':0.00,'repaymentPlanNo':'ZC2749A9264955607E','repaymentPrincipal':216,'techFee':0.00,'totalOverdueFee':0}]");
		try {
			String sr = PostTestUtil.sendPost(COMMAND_URL_TEST, requestParams, getIdentityInfoMap(requestParams));
			System.out.println(sr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	


}

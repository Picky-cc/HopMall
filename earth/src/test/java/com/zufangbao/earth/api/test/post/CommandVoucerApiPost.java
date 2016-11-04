package com.zufangbao.earth.api.test.post;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.earth.yunxin.api.model.command.BusinessPaymentVoucherDetail;
import com.zufangbao.earth.yunxin.api.util.ApiSignUtils;

public class CommandVoucerApiPost extends BaseApiTestPost {

	@Test
	public void testCommandBusinessPaymentVoucher() throws FileNotFoundException, IOException {
		
		String amount = "1011.5";

		List<BusinessPaymentVoucherDetail> details = new ArrayList<>();
		File file = new File("/Users/louguanyang/Desktop/test-voucher/test_"+ amount + "_1013.txt");//测试用json文件地址
		List<CommandVoucerParam> list = JsonUtils.parseArray(file, CommandVoucerParam.class); 
		
		for (CommandVoucerParam param : list) {
			BusinessPaymentVoucherDetail detail = new BusinessPaymentVoucherDetail();
			detail.setAmount(new BigDecimal(param.amount));
			detail.setUniqueId(param.unique_id);
			detail.setRepaymentPlanNo(param.single_loan_contract_no);
			detail.setPayer(0);
			details.add(detail);
		}
		String jsonString = JsonUtils.toJsonString(details);
		System.out.println(jsonString);
		
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("fn", "300003");
		requestParams.put("requestNo", UUID.randomUUID().toString());
		requestParams.put("transactionType", "0");
		requestParams.put("voucherType", "0");
		requestParams.put("voucherAmount", amount);
		requestParams.put("financialContractNo", "G32000");
		requestParams.put("receivableAccountNo", "20001");
		requestParams.put("paymentAccountNo", "10001");
		requestParams.put("bankTransactionNo", UUID.randomUUID().toString());
		requestParams.put("paymentName", "平安银行");
		requestParams.put("paymentBank", "bank");
		requestParams.put("detail", jsonString);
		
		ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);
		CloseableHttpClient client = HttpClientBuilder.create().build();
//		HttpPost httppost = new HttpPost("http://192.168.1.33:8080/earth-yunxin-0.1.0/api/command");  
		HttpPost httppost = new HttpPost("http://192.168.1.145:9090/api/command");  
        
		MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();  
        reqEntity.setCharset(Charset.forName("UTF-8"));
		
        for (Entry<String, String> e : requestParams.entrySet()) {
        	StringBody stringBody =  new StringBody(e.getValue(), contentType);
			reqEntity.addPart(e.getKey(), stringBody);
		}
        httppost.setEntity(reqEntity.build());  
		String signContent = ApiSignUtils.getSignCheckContent(requestParams);
		String sign = ApiSignUtils.rsaSign(signContent, privateKey);
        httppost.addHeader("merId", TEST_MERID);
        httppost.addHeader("secret", TEST_SECRET);
        httppost.addHeader("sign", sign);
        HttpResponse response = client.execute(httppost); 
		HttpEntity entity = response.getEntity();
		System.out.println(EntityUtils.toString(entity));
	}

}

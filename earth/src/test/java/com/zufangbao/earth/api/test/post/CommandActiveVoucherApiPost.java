package com.zufangbao.earth.api.test.post;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.zufangbao.earth.yunxin.api.util.ApiSignUtils;
import com.zufangbao.sun.utils.FileUtils;

public class CommandActiveVoucherApiPost extends BaseApiTestPost{
	

	@Test
	public void testCommandActivePaymentVoucher() throws FileNotFoundException, IOException {
		
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("fn", "300004");
		requestParams.put("transactionType", "0");
		requestParams.put("requestNo", "5141b3f0-52dc-44c5-bc8c-945a84e00ee9");
		requestParams.put("voucherType", "5");//5,6
		requestParams.put("uniqueId", "1f595d74-eb5c-49c8-8620-92170388685c");
		requestParams.put("receivableAccountNo", "6227007200341122450");
		requestParams.put("paymentBank", "建设银行");
		requestParams.put("bankTransactionNo", "123546");
		requestParams.put("voucherAmount", "510");
		requestParams.put("contractNo", "");
		
		requestParams.put("paymentName", "韩梅梅");
		requestParams.put("paymentAccountNo", "6227007200341122450");
		requestParams.put("repaymentPlanNo", "[\"ZC274F46CF62A73249\"]");

		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpPost httppost = new HttpPost("http://127.0.0.1:9090/api/command");  
//		HttpPost httppost = new HttpPost("http://yunxin.zufangbao.cn/api/command");  
        
		File file = FileUtils.getFile("/Users/louguanyang/Desktop/2.png");
//		File file2 = FileUtils.getFile("/Users/louguanyang/Desktop/gamersky_01origin_01_201412922358DD.jpg");
//		File file3 = FileUtils.getFile("/Users/louguanyang/Desktop/gamersky_21origin_41_201411812152E3.jpg");
//		File file4 = FileUtils.getFile("/Users/louguanyang/Desktop/gamersky_001origin_001_2015117171327B.jpg");
//		File file5 = FileUtils.getFile("/Users/louguanyang/Desktop/pdf.pdf");
		
		FileBody bin = new FileBody(file);
//		FileBody bin2 = new FileBody(file2);
//		FileBody bin3 = new FileBody(file3);
//		FileBody bin4 = new FileBody(file4);
//		FileBody bin5 = new FileBody(file5);

		ContentType contentType = ContentType.create(HTTP.PLAIN_TEXT_TYPE, HTTP.UTF_8);
		
		MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();  
		reqEntity.setCharset(Charset.forName(HTTP.UTF_8));
		
        reqEntity.addPart("file", bin);
//        reqEntity.addPart("file", bin2);
//        reqEntity.addPart("file3", bin3);
//        reqEntity.addPart("file4", bin4);
//        reqEntity.addPart("file5", bin5);
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

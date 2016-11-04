package com.zufangbao.earth.api.test.post;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.zufangbao.earth.yunxin.api.util.ApiSignUtils;
import com.zufangbao.gluon.opensdk.HttpClientUtils;
import com.zufangbao.gluon.util.PfxSignUtil;

public class CommandRemittanceApiPost extends BaseApiTestPost{

	@Test
	public void testApiCommandRemittance() {
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("fn", "300002");
		requestParams.put("requestNo", "requestNo6");
		requestParams.put("remittanceStrategy", "0");
		requestParams.put("productCode", "G00001");
		requestParams.put("uniqueId", "contractUniqueId1");
		requestParams.put("contractNo", "contractNo1");
		requestParams.put("plannedRemittanceAmount", "500");
		requestParams.put("auditorName", "auditorName1");
		requestParams.put("auditTime", "2016-08-20 00:00:00");
		requestParams.put("remark", "交易备注");
		requestParams.put("remittanceDetails", "[{'detailNo':'detailNo1','recordSn':'1','amount':'200','plannedDate':'2016-08-20 00:00:00','bankCode':'C10102','bankCardNo':'bankCardNo1','bankAccountHolder':'测试用户1','bankProvince':'bankProvince1','bankCity':'bankCity1','bankName':'bankName1','idNumber':'idNumber1'},{'detailNo':'detailNo2','recordSn':'2','amount':'300','plannedDate':'2016-08-20 00:00:00','bankCode':'C10102','bankCardNo':'bankCardNo2','bankAccountHolder':'测试用户2','bankProvince':'bankProvince2','bankCity':'bankCity2','bankName':'bankName2','idNumber':'idNumber2'}]");
		try {
			String sr = PostTestUtil.sendPost(COMMAND_URL_TEST, requestParams, getIdentityInfoMap(requestParams));
			System.out.println(sr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testApiCommandRemittanceFF() {
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("fn", "300002");
		requestParams.put("requestNo", UUID.randomUUID().toString());
		requestParams.put("remittanceStrategy", "0");
		requestParams.put("productCode", "G00001");
		requestParams.put("uniqueId", "contractUniqueId1");
		requestParams.put("contractNo", "");
		requestParams.put("plannedRemittanceAmount", "0.02");
		requestParams.put("auditorName", "auditorName1");
		requestParams.put("auditTime", "2016-08-20 00:00:00");
		requestParams.put("remark", "交易备注");
		requestParams.put("remittanceDetails", "[{'detailNo':'detailNo1','recordSn':'1','amount':'0.01','plannedDate':'2016-08-20 00:00:00','bankCode':'C10102','bankCardNo':'5685968545868856','bankAccountHolder':'汪水','bankProvince':'bankProvince1','bankCity':'bankCity1','bankName':'bankName1','idNumber':'idNumber1'},{'detailNo':'detailNo2','recordSn':'2','amount':'0.01','plannedDate':'2016-08-20 00:00:00','bankCode':'C10102','bankCardNo':'123456789','bankAccountHolder':'测试用户2','bankProvince':'bankProvince2','bankCity':'bankCity2','bankName':'bankName2','idNumber':'idNumber2'}]");
		try {
			String sr = PostTestUtil.sendPost(COMMAND_URL_TEST, requestParams, getIdentityInfoMap(requestParams));
			System.out.println(sr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testApiCommandRemittance1() {
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("fn", "300002");
		requestParams.put("requestNo", "54a70ae0-d0f6-496f-b71d-5f0a916e9005");
		requestParams.put("remittanceStrategy", "0");
		requestParams.put("productCode", "11");
		requestParams.put("uniqueId", "18f72b93-5d3a-4511-82ef-0491573424f6");
		requestParams.put("contractNo", "11(831180608947705450ht)号");
		requestParams.put("plannedRemittanceAmount", "0.02");
		requestParams.put("auditorName", null);
		requestParams.put("auditTime", null);
		requestParams.put("clearingAccount", null);
		requestParams.put("notifyUrl", null);
		requestParams.put("contractAmount", "0.02");
		requestParams.put("remark", null);
		requestParams.put("remittanceDetails", "[{\"detailNo\":\"1\",\"recordSn\":\"1\",\"amount\":\"0.01\",\"plannedDate\":null,\"bankCode\":\"C10105\",\"bankCardNo\":\"6217001210075327591\",\"bankAccountHolder\":\"韩方园\",\"bankProvince\":\"310000\",\"bankCity\":\"310100\",\"bankName\":\"tianlin\",\"idNumber\":\"410402198801115658\"},{\"detailNo\":\"2\",\"recordSn\":\"2\",\"amount\":\"0.01\",\"plannedDate\":null,\"bankCode\":\"C10301\",\"bankCardNo\":\"6222600110051482870\",\"bankAccountHolder\":\"王胜达\",\"bankProvince\":\"310000\",\"bankCity\":\"310100\",\"bankName\":\"交通银行\",\"idNumber\":null}]");
		try {
			String sr = PostTestUtil.sendPost(COMMAND_URL_TEST, requestParams, getIdentityInfoMap(requestParams));
			System.out.println(sr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPaidNotic() {
		Map<String, String> headerParams = new HashMap<String, String>();
		headerParams.put("Content-Type", "application/json");
		headerParams.put("merid", "suidifu");
		headerParams.put("secretkey", "suidifutest");
		
		String data = "{\"Requestid\":\""+UUID.randomUUID().toString()+"\",\"ReferenceId\":\"32c21a60-b35c-4b16-896c-82f20d5cba95\",\"UniqueId\":\"0c4e59be-0496-4462-8497-541c9a28b7aa\",\"PaidDetails\":[{\"DetailNo\":\"1\",\"Status\":\"1\",\"Result\":\"交易成功\",\"BankSerialNo\":\"6123456789\",\"ActExcutedTime\":\"2016-08-24 23:40:00\"},{\"DetailNo\":\"2\",\"Status\":\"1\",\"Result\":\"交易成功\",\"BankSerialNo\":\"6223456789\",\"ActExcutedTime\":\"2016-08-24 23:40:30\"}]}";
		String signedMsg = PfxSignUtil.sign("", "123456", data, "MD5withRSA", "UTF-8");
		headerParams.put("signedmsg", signedMsg);
		
//		String url = "http://101.52.128.166/Loan/PaidNotic";
		String url = "http://hello369.tunnel.qydev.com/loan/paidnotic";
		System.out.println(JSON.toJSONString(HttpClientUtils.executePostRequest(url, data, headerParams)));
	}
	
	@Test
	public void ttt() {
		for (int i = 0; i < 1; i++) {
			Map<String, String> requestParams = new HashMap<String, String>();
			requestParams.put("fn", "300002");
			requestParams.put("requestNo", UUID.randomUUID().toString());
			requestParams.put("remittanceStrategy", "0");
			requestParams.put("productCode", "G00003");
			requestParams.put("uniqueId", UUID.randomUUID().toString());
			requestParams.put("contractNo", "");
			requestParams.put("plannedRemittanceAmount", "0.03");
			requestParams.put("auditorName", "");
			requestParams.put("auditTime", "");
			requestParams.put("remark", "20180827平安测试");
			requestParams.put("remittanceDetails", "[{'detailNo':'1','recordSn':'1','amount':'0.01','plannedDate':'','bankCode':'C10305','bankCardNo':'6226227705568339','bankAccountHolder':'张建明','bankProvince':'','bankCity':'','bankName':'','idNumber':''},{'detailNo':'2','recordSn':'2','amount':'0.02','plannedDate':'','bankCode':'C10305','bankCardNo':'6226227705568339','bankAccountHolder':'张建明','bankProvince':'','bankCity':'','bankName':'','idNumber':''}]");
			String headerStr = "";
			for (Entry<String, String> header : getIdentityInfoMap(requestParams).entrySet()) {
				headerStr += header.getValue() + ",";
			}
			System.out.println(buildParams(requestParams)+","+headerStr);
		}
	}
	
	public static void main(String[] args) {
		String content = "productCode=G00001&auditTime=2016-08-20 00:00:00&contractNo=&fn=300002&remittanceStrategy=0&remark=交易备注&requestNo=477e6697-490c-4c9d-a1f6-4a793b397482&plannedRemittanceAmount=0.02&remittanceDetails=[{'detailNo':'detailNo1','recordSn':'1','amount':'0.01','plannedDate':'2016-08-20 00:00:00','bankCode':'C10102','bankCardNo':'5685968545868856','bankAccountHolder':'汪水','bankProvince':'bankProvince1','bankCity':'bankCity1','bankName':'bankName1','idNumber':'idNumber1'},{'detailNo':'detailNo2','recordSn':'2','amount':'0.01','plannedDate':'2016-08-20 00:00:00','bankCode':'C10102','bankCardNo':'123456789','bankAccountHolder':'测试用户2','bankProvince':'bankProvince2','bankCity':'bankCity2','bankName':'bankName2','idNumber':'idNumber2'}]&uniqueId=contractUniqueId1&auditorName=auditorName1&";
		String[] data = content.split("&");
		Map<String, String> map = new HashMap<String, String>();
		
		for (String string : data) {
			String[] cc = string.split("=");
			map.put(cc[0], cc.length ==2 ? cc[1] : null);
		}
		System.out.println(map);
		String content1 = ApiSignUtils.getSignCheckContent(map);
		String sign = "whOELLk2cg9daDRNbDeogAZ+hq8ikYUe4KDxa8fttUIMSJEslTQPy6Hj1yXgzqn2WpllOA2pJWAxpdWxoLF4j+vJDqHvKWYIgoCdhVK0dhZ00/NXqbeuaq28Sg8KP57h02n7f5u2NKmOTFW/EU4qYiwGMQIG5Wm5w5ChBZAC88I=";
		System.out.println(ApiSignUtils.rsaCheckContent(content1, sign, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDl1BjinRJLLDiNc6jcOKW+nph9aNSWMaKMk0OxTdSATakyS7rwNxrLMFyJLkI9IpnHussBv1zgsHPUdZeRcHDkbcMdhYoRgpe3gZIVMJ09BMBjhAET4fensvk377L0Whzp+u9r9UxIWowH7YJuJe3yQ7R3RgYxzrPuTJYq/WeuUQIDAQAB"));
	
	}
	
}

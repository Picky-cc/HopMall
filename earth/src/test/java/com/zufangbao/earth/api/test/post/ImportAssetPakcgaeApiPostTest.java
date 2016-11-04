package com.zufangbao.earth.api.test.post;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.junit.Test;

import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.earth.yunxin.api.model.modify.ImportAssetPackageContent;
import com.zufangbao.earth.yunxin.api.util.ApiSignUtils;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.model.api.ContractDetail;
import com.zufangbao.sun.yunxin.entity.model.api.ImportRepaymentPlanDetail;

public class ImportAssetPakcgaeApiPostTest extends BaseApiTestPost {

	private  String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOXUGOKdEkssOI1zqNw4pb6emH1o1JYxooyTQ7FN1IBNqTJLuvA3GsswXIkuQj0imce6ywG/XOCwc9R1l5FwcORtwx2FihGCl7eBkhUwnT0EwGOEARPh96ey+TfvsvRaHOn672v1TEhajAftgm4l7fJDtHdGBjHOs+5Mlir9Z65RAgMBAAECgYEArtAiUZR5yrYLGgTEhyWLZK+Le7CWKtv8MQL+tUlm/mST8s7JlVfEyJKzgCCwf4HnCJXbPiwJgFqW8B61uAmXw6cEoPftEnzvKBTyISt/iEf7DTWKGkDBnlYM9sFU6pU61jw17XEDQRtSBG6cfrlGSelqf25+c8onxu4YwTeLH/ECQQD/H69tPy0FYRvCJ5yXdXEVCKshNN01P+UdDzGtyysE/gmpalbewT+uznApa0qFntcYb8eSpUJzrUlItSCBGUpdAkEA5p4r3qF+4g5V7MBHm3+v1l9JKxYK76990AQJa122rfkY2EEVuvU+8KIAQpVflu/HpDe8QH4mQZTsZj24Skt8hQJAL5j2vrgRqzZB2ohPY8aKcXUrkEdvmdaw5SoHh7gm74iBvvTS/j4ppnBnZqLYxXMsCCgaoNZqPnCvAnygctWIFQJAHm2KLkKyohLwJV+tUwgC5E8IMWYkJUHLYNHXiFICE2xFaesUeel313oYfLCGvzx9493yubOrSoXitw63rR3OnQJBALwGSnGYodmJB5k7un0X6LlO4nSu/+SX9lweloZ1AUg15IuCIYxHAFKwOtOJmx/eMcITaLq8l1qzZ907UXY+Mfs=";
	
	@Test
	public void  testApi() {
		
//		for (int index = 0; index < 10; index++) {
			ImportAssetPackageContent importAssetPackageContent = new ImportAssetPackageContent();
			importAssetPackageContent.setThisBatchContractsTotalNumber(1);
			importAssetPackageContent.setThisBatchContractsTotalAmount("1011.5");
			importAssetPackageContent.setFinancialProductCode("G32000");

			List<ContractDetail> contracts = new ArrayList<ContractDetail>();
			ContractDetail contractDetail = new ContractDetail();
			contractDetail.setUniqueId(UUID.randomUUID().toString());
//			contractDetail.setLoanContractNo("test-04-0930-" + index);
			contractDetail.setLoanContractNo("test-01-1017-01");
			contractDetail.setLoanCustomerNo(UUID.randomUUID().toString());
			contractDetail.setLoanCustomerName("郑航波");
			contractDetail.setSubjectMatterassetNo(UUID.randomUUID().toString());
			contractDetail.setIDCardNo("330683199403062411");
			contractDetail.setBankCode("C10102");
			contractDetail.setBankOfTheProvince("330000");
			contractDetail.setBankOfTheCity("110100");
			contractDetail.setRepaymentAccountNo("23456787654323456");
			contractDetail.setLoanTotalAmount("1011.5");
			contractDetail.setLoanPeriods(1);
			contractDetail.setEffectDate("2016-8-1");
			contractDetail.setExpiryDate("2099-01-01");
			contractDetail.setLoanRates("0.156");
			contractDetail.setInterestRateCycle(1);
			contractDetail.setPenalty("0.0005");
			contractDetail.setRepaymentWay(2);

			List<ImportRepaymentPlanDetail> repaymentPlanDetails = new ArrayList<ImportRepaymentPlanDetail>();

			ImportRepaymentPlanDetail repaymentPlanDetail1 = new ImportRepaymentPlanDetail();
			repaymentPlanDetail1.setRepaymentPrincipal("1011.5");
			repaymentPlanDetail1.setRepaymentInterest("0.00");
			repaymentPlanDetail1.setRepaymentDate("2016-10-17");
			repaymentPlanDetail1.setOtheFee("0.00");
			repaymentPlanDetail1.setTechMaintenanceFee("0.00");
			repaymentPlanDetail1.setLoanServiceFee("0.00");
			repaymentPlanDetails.add(repaymentPlanDetail1);
			  
			contractDetail.setRepaymentPlanDetails(repaymentPlanDetails);

			contracts.add(contractDetail);
			importAssetPackageContent.setContractDetails(contracts);
			
			String param =  JsonUtils.toJsonString(importAssetPackageContent);
			System.out.println(param);
			Map<String,String> params =new HashMap<String,String>(); 
			params.put("fn", "200004");
			params.put("requestNo", UUID.randomUUID().toString());
			params.put("importAssetPackageContent", param); 
					
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("merId", "t_test_zfb");
			headers.put("secret", "123456");
			String signContent = ApiSignUtils.getSignCheckContent(params);
			String sign = ApiSignUtils.rsaSign(signContent, privateKey);
			System.out.println(sign);
			headers.put("sign", sign);
			
			try {
//				String sr = PostTestUtil.sendPost("http://yunxin.zufangbao.cn/api/modify", params, headers);
				String sr = PostTestUtil.sendPost("http://192.168.1.145:9090/api/modify", params, headers);
//				System.out.println( "===========" + index + sr);
				System.out.println( "===========" + sr);
			} catch (Exception e) {
				e.printStackTrace();
			}	
//		}
		
	}
	
	@Test
	public void testPressue(){
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("importAssetPackageContent", "{'contractDetails':[{'bankCode':'C10102','bankOfTheCity':'110100','bankOfTheProvince':'330000','effectDate':'2016-9-14','expiryDate':'2099-01-01','iDCardNo':'330683199403062410','interestRateCycle':1,'loanContractNo':'"+UUID.randomUUID().toString()+"','loanCustomerName':'猴塞雷','loanCustomerNo':'customerNo1','loanPeriods':1,'loanRates':'0.156','loanTotalAmount':'1.00','penalty':'0.0005','repaymentAccountNo':'111111111111','repaymentPlanDetails':[{'loanServiceFee':'0.00','otheFee':'0.00','repaymentDate':'2016-09-15','repaymentInterest':'0.00','repaymentPrincipal':'1.00','techMaintenanceFee':'0.00'}],'repaymentWay':2,'subjectMatterassetNo':'"+UUID.randomUUID().toString()+"','uniqueId':'"+UUID.randomUUID().toString()+"'}],'financialProductCode':'G31700','thisBatchContractsTotalAmount':'1.00','thisBatchContractsTotalNumber':1}");
		requestParams.put("requestNo", UUID.randomUUID().toString());
		requestParams.put("fn", "200004");
		String headerStr = "";
		for (Entry<String, String> header : getIdentityInfoMap(requestParams).entrySet()) {
			headerStr += header.getValue() + ",";
		}
		System.out.println(("curl --data \""+buildParams(requestParams)+"\" -H sign:"+headerStr).replaceAll(",t_test_zfb,123456,", " -H merId:t_test_zfb -H secret:123456 -X POST http://10.10.11.11/api/modify?fn=200004"));
	}
	
	@Test
	public void  test_api() throws IOException {
		
		for(int trytimes=0;trytimes<1;trytimes++)
		{
		  ImportAssetPackageContent importAssetPackageContent = new ImportAssetPackageContent();
		  importAssetPackageContent.setThisBatchContractsTotalNumber(1);
		  importAssetPackageContent.setThisBatchContractsTotalAmount("1.00");
		  importAssetPackageContent.setFinancialProductCode("G31700");
		  
		  List<ContractDetail> contracts= new ArrayList<ContractDetail>();
		  ContractDetail contractDetail = new ContractDetail();
		  contractDetail.setUniqueId(UUID.randomUUID().toString());
		  contractDetail.setLoanContractNo(UUID.randomUUID().toString());
		  contractDetail.setLoanCustomerNo("customerNo1");
		  contractDetail.setLoanCustomerName("阿旺晋美");
		  contractDetail.setSubjectMatterassetNo(UUID.randomUUID().toString());
		  contractDetail.setIDCardNo("330683199403062410");
		  contractDetail.setBankCode("C10102");
		  contractDetail.setBankOfTheProvince("330000");
		  contractDetail.setBankOfTheCity("110100");
		  contractDetail.setRepaymentAccountNo("111111111111");
		  contractDetail.setLoanTotalAmount("1.00");
		  contractDetail.setLoanPeriods(1);
		  contractDetail.setEffectDate("2016-9-14");
		  contractDetail.setExpiryDate("2099-01-01");
		  contractDetail.setLoanRates("0.156");
		  contractDetail.setInterestRateCycle(1);
		  contractDetail.setPenalty("0.0005");
		  contractDetail.setRepaymentWay(2);
		  
		  List<ImportRepaymentPlanDetail> repaymentPlanDetails = new ArrayList<ImportRepaymentPlanDetail>();
		  
		  for(int i=1 ;i<=1;i++){
		  ImportRepaymentPlanDetail repaymentPlanDetail1 = new ImportRepaymentPlanDetail();
		  repaymentPlanDetail1.setRepaymentPrincipal("1.00");
		  repaymentPlanDetail1.setRepaymentInterest("0.00");
		  repaymentPlanDetail1.setRepaymentDate(DateUtils.format(DateUtils.addDays(new Date(), i)));
		  repaymentPlanDetail1.setOtheFee("0.00");
		  repaymentPlanDetail1.setTechMaintenanceFee("0.00");
		  repaymentPlanDetail1.setLoanServiceFee("0.00");
		  repaymentPlanDetails.add(repaymentPlanDetail1);
		  
		  }
		  
		  contractDetail.setRepaymentPlanDetails(repaymentPlanDetails);
		  
		  contracts.add(contractDetail);
		  importAssetPackageContent.setContractDetails(contracts);
		
		String param =  JsonUtils.toJsonString(importAssetPackageContent);
		  System.out.println(param);
		Map<String,String> params =new HashMap<String,String>(); 
		params.put("fn", "200004");
		params.put("requestNo", UUID.randomUUID().toString());
		params.put("importAssetPackageContent", param); 
				
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("merId", "t_test_zfb");
		headers.put("secret", "123456");
		String signContent = ApiSignUtils.getSignCheckContent(params);
		
		
		try {
			RandomAccessFile file=new RandomAccessFile("d:\\package\\"+trytimes+"content.txt","rwd");
			file.writeBytes(signContent);
			file.close();
			
			System.out.println(signContent);
			String sign = ApiSignUtils.rsaSign(signContent, privateKey);
			System.out.println(sign);
			RandomAccessFile signfile=new RandomAccessFile("d:\\package\\"+trytimes+"sign.txt","rwd");
			signfile.writeUTF(sign);
			signfile.close();
			headers.put("sign", sign);
			String sr = PostTestUtil.sendPost(MODIFY_URL_TEST,params,headers);		
			System.out.println(sr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	}
	
	@Test
	public void  test_api_2() throws IOException {
		
		String[] uniqueIds = {"1a15f254c-f0-4808-ab27-b762f1cb650a","3e4e3b6f-6140-4eb9-bcb8-3a5ba73bbe9e","dec78e6e-a017-4eb5-8c48-ec134aff6673","37814934-470b-4068-b365-a0b660b88eb7","25fd33bf-0693-4433-b450-d4ca701b3c50","a25ed7c3-47cf-4892-b15a-72453c3070dd"};
		String[] contracts = {"sdf(f6a9sc58b-17b6-43c3-9669-e8049999f)","sdf(e887201a-dc89-4b4a-96d7-cba56494c7d8)","sdf(0c52b3b7-74a5-4499-b566-fa7bd908cf01)","sdf(050ac544-dba0-4e0a-8d4f-efa2b813e2bf)","sdf(ae1b56e1-1bd9-40ec-add5-f438b4c97621)","sdf(881ec708-8d8b-4bc2-8a1f-bf49eea0737d)"};
		String[] cardNo = {"330683199403066411","330683199403066411","330683199403066411","330683199403066411","330683199403066411","330683199403066411"};

		
		
		
		ImportAssetPackageContent importAssetPackageContent = new ImportAssetPackageContent();
		importAssetPackageContent.setThisBatchContractsTotalNumber(1);
		importAssetPackageContent.setThisBatchContractsTotalAmount("10000.00");
		importAssetPackageContent.setFinancialProductCode("nfqtest001");
		List<ContractDetail> contractDetails= new ArrayList<ContractDetail>();
		for(int trytimes=0;trytimes<1;trytimes++)
		{
		  
		  
		  
		  ContractDetail contractDetail = new ContractDetail();
		  contractDetail.setUniqueId(uniqueIds[trytimes]);
		  contractDetail.setLoanContractNo(contracts[trytimes]);
		  contractDetail.setLoanCustomerNo(UUID.randomUUID().toString());
		  contractDetail.setLoanCustomerName("张锋");
		  contractDetail.setSubjectMatterassetNo(UUID.randomUUID().toString());
		  contractDetail.setIDCardNo(cardNo[trytimes]);
		  contractDetail.setBankCode("C10102");
		  contractDetail.setBankOfTheProvince("330000");
		  contractDetail.setBankOfTheCity("110100");
		  contractDetail.setRepaymentAccountNo(cardNo[trytimes]);
		  contractDetail.setLoanTotalAmount("10000.00");
		  contractDetail.setLoanPeriods(1);
		  contractDetail.setEffectDate("2016-9-22");
		  contractDetail.setExpiryDate("2099-01-01");
		  contractDetail.setLoanRates("0.156");
		  contractDetail.setInterestRateCycle(1);
		  contractDetail.setPenalty("0.0005");
		  contractDetail.setRepaymentWay(2);
		  
		  List<ImportRepaymentPlanDetail> repaymentPlanDetails = new ArrayList<ImportRepaymentPlanDetail>();
		  
		  for(int i=1 ;i<=1;i++){
		  ImportRepaymentPlanDetail repaymentPlanDetail1 = new ImportRepaymentPlanDetail();
		  repaymentPlanDetail1.setRepaymentPrincipal("10000.00");
		  repaymentPlanDetail1.setRepaymentInterest("0.00");
		  repaymentPlanDetail1.setRepaymentDate("2016-9-22");
		  repaymentPlanDetail1.setOtheFee("0.00");
		  repaymentPlanDetail1.setTechMaintenanceFee("0.00");
		  repaymentPlanDetail1.setLoanServiceFee("0.00");
		  repaymentPlanDetails.add(repaymentPlanDetail1);
		  
		  }
		  
		  contractDetail.setRepaymentPlanDetails(repaymentPlanDetails);
		  
		  contractDetails.add(contractDetail);
		}
		 importAssetPackageContent.setContractDetails(contractDetails);
		
		String param =  JsonUtils.toJsonString(importAssetPackageContent);
		  System.out.println(param);
		Map<String,String> params =new HashMap<String,String>(); 
		params.put("fn", "200004");
		params.put("requestNo", UUID.randomUUID().toString());
		params.put("importAssetPackageContent", param); 
				
		
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("merId", "t_test_zfb");
		headers.put("secret", "123456");
		String signContent = ApiSignUtils.getSignCheckContent(params);
		
		
		try {
			String sign = ApiSignUtils.rsaSign(signContent, privateKey);
			headers.put("sign", sign);
			String sr = PostTestUtil.sendPost(MODIFY_URL_TEST,params,headers);	
			System.out.println(sr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	
	private String publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDl1BjinRJLLDiNc6jcOKW+nph9aNSWMaKMk0OxTdSATakyS7rwNxrLMFyJLkI9IpnHussBv1zgsHPUdZeRcHDkbcMdhYoRgpe3gZIVMJ09BMBjhAET4fensvk377L0Whzp+u9r9UxIWowH7YJuJe3yQ7R3RgYxzrPuTJYq/WeuUQIDAQAB";
	
	@Test
	public void test() {
		String sign = ApiSignUtils.rsaSign("123456", privateKey);
		if(ApiSignUtils.rsaCheckContent("123456", sign, publickey)) {
			System.out.println("succ");
		}
	}
	
	@Test
	public void testImport() {
		
	}
	
}
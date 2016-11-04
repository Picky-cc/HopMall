package com.zufangbao.earth.yunxin.unionpay;

import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import com.gnete.security.crypt.Crypt;
import com.gnete.security.crypt.CryptException;
import com.zufangbao.earth.api.exception.TransactionDetailApiException;
import com.zufangbao.earth.yunxin.unionpay.component.IGZUnionPayApiComponent;
import com.zufangbao.earth.yunxin.unionpay.component.impl.GZUnionPayApiComponentImpl;
import com.zufangbao.earth.yunxin.unionpay.model.AccountDetailQueryInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.BatchDeductInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.BatchQueryInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.DeductDetailInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.RealTimeDeductInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.TransactionDetailQueryInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.TransactionDetailQueryResult;
import com.zufangbao.earth.yunxin.unionpay.model.basic.IGZUnionPayApiParams;

public class GZUnionPayTest {
	
	private IGZUnionPayApiComponent handler = new GZUnionPayApiComponentImpl();

	private String apiUrl = "http://59.41.103.98:333/gzdsf/ProcessServlet";

	private String cerFilePath = "src/main/resources/certificate/gzdsf.cer";
	
	private String pfxFilePath = "src/main/resources/certificate/ORA@TEST1.pfx";
	
	private String pfxFileKey = "123456";

	private String num = "0000000000000000003";
	
	//批扣
	@Test
	public void testExecBatchDeduct() throws CryptException {
		BatchDeductInfoModel model = getTestBatchDeductInfoModel();
		setGZUnionPayParams(model);
		handler.execBatchDeduct(model);
	}
	
	//实时扣
	@Test
	public void testExecRealTimeDeduct() throws CryptException {
//		RealTimeDeductInfoModel model1 = getTestRealTimeDeductInfoModel("rtxyz1" + num, "102", "605810028220436120", "邹力");
//		setGZUnionPayParams(model1);
//		handler.execRealTimeDeductPacket(model1);
//		
		RealTimeDeductInfoModel model2 = getTestRealTimeDeductInfoModel(UUID.randomUUID().toString(), "102", "6225881242283025", "段春野");
		setGZUnionPayParams(model2);
		handler.execRealTimeDeductPacket(model2);
		
//		RealTimeDeductInfoModel model3 = getTestRealTimeDeductInfoModel("rtxyz3" + num, "103", "123123123123123", "农行测试");
//		setGZUnionPayParams(model3);
//		handler.execRealTimeDeductPacket(model3);
	}
	
	//批扣/实时查询
	@Test
	public void testExecBatchQuery() throws CryptException {
//		BatchQueryInfoModel batchQueryInfoModel1 = new BatchQueryInfoModel("rtqxyz1" + num, "rtxyz1" + num);
//		batchQueryInfoModel1.setUserName("operator");
//		batchQueryInfoModel1.setUserPwd("operator");
//		setGZUnionPayParams(batchQueryInfoModel1);
//		handler.execBatchQuery(batchQueryInfoModel1);
		
		BatchQueryInfoModel batchQueryInfoModel2 = new BatchQueryInfoModel(UUID.randomUUID().toString(), "81f3d391-88ca-46f3-97c5-50b2398d2e67");
		batchQueryInfoModel2.setUserName("operator");
		batchQueryInfoModel2.setUserPwd("operator");
		setGZUnionPayParams(batchQueryInfoModel2);
		handler.execBatchQuery(batchQueryInfoModel2);
		
//		BatchQueryInfoModel batchQueryInfoModel3 = new BatchQueryInfoModel("rtqxyz3" + num, "rtxyz3" + num);
//		batchQueryInfoModel3.setUserName("operator");
//		batchQueryInfoModel3.setUserPwd("operator");
//		setGZUnionPayParams(batchQueryInfoModel3);
//		handler.execBatchQuery(batchQueryInfoModel3);
	}
	
	//交易明细查询
	@Test
	public void testExecTransactionDetailQuery() throws CryptException {
		TransactionDetailQueryInfoModel model = new TransactionDetailQueryInfoModel();
		model.setUserName("operator");
		model.setUserPwd("operator");
		model.setMerchantId("001053110000001");
		model.setReqNo("cash00000000000000000001");
		model.setBeginDate("20160505");
		model.setEndDate("20160505");
		model.setPageNum("1");
		model.setPageSize("");
		setGZUnionPayParams(model);
		try {
			TransactionDetailQueryResult result = handler.execTransactionDetailQuery(model);
		} catch (TransactionDetailApiException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	private BatchDeductInfoModel getTestBatchDeductInfoModel() {
		BatchDeductInfoModel batchDeductInfoModel = new BatchDeductInfoModel();
		batchDeductInfoModel.setUserName("operator");
		batchDeductInfoModel.setUserPwd("operator");
		batchDeductInfoModel.setMerchantId("001053110000001");
		batchDeductInfoModel.setReqNo("xyz00000000000000000024");
		batchDeductInfoModel.setBusinessCode("14901");
		batchDeductInfoModel.setTotalItem(3);
		batchDeductInfoModel.setTotalSum(new BigDecimal("0.3"));
		
		List<DeductDetailInfoModel> list = new ArrayList<DeductDetailInfoModel>();
		DeductDetailInfoModel deductDetailInfoModel = new DeductDetailInfoModel();
		deductDetailInfoModel.setSn("0001");
		deductDetailInfoModel.setBankCode("102");
		deductDetailInfoModel.setAccountNo("605810028220436120");
		deductDetailInfoModel.setAccountName("邹力");
		deductDetailInfoModel.setAmount(new BigDecimal("0.1"));
		deductDetailInfoModel.setRemark("0001-代扣");
		list.add(deductDetailInfoModel);
		
		DeductDetailInfoModel deductDetailInfoModel2 = new DeductDetailInfoModel();
		deductDetailInfoModel2.setSn("0002");
		deductDetailInfoModel2.setBankCode("102");
		deductDetailInfoModel2.setAccountNo("655880360213125310");
		deductDetailInfoModel2.setAccountName("662145");
		deductDetailInfoModel2.setAmount(new BigDecimal("0.1"));
		deductDetailInfoModel2.setRemark("0002-代扣");
		list.add(deductDetailInfoModel2);
		
		DeductDetailInfoModel deductDetailInfoModel3 = new DeductDetailInfoModel();
		deductDetailInfoModel3.setSn("0003");
		deductDetailInfoModel3.setBankCode("103");
		deductDetailInfoModel3.setAccountNo("213521361262340");
		deductDetailInfoModel3.setAccountName("测案例一");
		deductDetailInfoModel3.setAmount(new BigDecimal("0.1"));
		deductDetailInfoModel3.setRemark("0003-代扣");
		list.add(deductDetailInfoModel3);
		
		batchDeductInfoModel.setDetailInfos(list);
		
		setGZUnionPayParams(batchDeductInfoModel);
		return batchDeductInfoModel;
	}
	
	private RealTimeDeductInfoModel getTestRealTimeDeductInfoModel(String reqNo, String bankCode, String accountNo, String accountName) {
		
		RealTimeDeductInfoModel realTimeDeductInfoModel = new RealTimeDeductInfoModel();
		realTimeDeductInfoModel.setUserName("operator");
		realTimeDeductInfoModel.setUserPwd("operator");
		realTimeDeductInfoModel.setMerchantId("001053110000001");
		realTimeDeductInfoModel.setReqNo(reqNo);
		realTimeDeductInfoModel.setBusinessCode("14901");
		
		DeductDetailInfoModel deductDetailInfoModel = new DeductDetailInfoModel();
		deductDetailInfoModel.setSn("0001");
		deductDetailInfoModel.setBankCode(bankCode);
		deductDetailInfoModel.setAccountNo(accountNo);
		deductDetailInfoModel.setAccountName(accountName);
		deductDetailInfoModel.setAmount(new BigDecimal("5.99"));
		deductDetailInfoModel.setRemark("0001-实时代扣======test06132120");
		realTimeDeductInfoModel.setDeductDetailInfoModel(deductDetailInfoModel);
		
		setGZUnionPayParams(realTimeDeductInfoModel);
		
		return realTimeDeductInfoModel;
	}
	
	private void setGZUnionPayParams(IGZUnionPayApiParams apiParams) {
		apiParams.setApiUrl(apiUrl);
		apiParams.setCerFilePath(cerFilePath);
		apiParams.setPfxFilePath(pfxFilePath);
		apiParams.setPfxFileKey(pfxFileKey);
	}
	
	@Test
	public void testSend(){
		String setDate = "20111212";
		String SETT_NO = "01";
		String SF_TYPE = "S";
		String userName = "operator";//"abc";
		String merId = "001053110000001";//"mmm";
		String REQ_TIME = "20121213041313";//DateUtils.format(new Date(), "yyyyMMddHHmmss");
		
		Crypt  crypt = new Crypt("gbk");
		String url = "http://59.41.103.98:333/gzdsf/GetSettFile.do?SETT_DATE="+setDate+"&SETT_NO="+SETT_NO+"&SF_TYPE="+SF_TYPE+"&USER_NAME="+userName+"&MERCHANT_ID="+merId+"&REQ_TIME="+REQ_TIME+"&SIGNED_MSG=";
		//调用签名包签名接口(原文,私钥路径,密码)
		String stringBefore = setDate+"|"+SETT_NO+"|"+userName+"|"+merId+"|"+REQ_TIME;
		try {
			String signedMsg = crypt.sign(stringBefore, pfxFilePath, pfxFileKey);
			System.out.println(signedMsg);
			System.out.println(url+signedMsg);
		} catch (CryptException e) {
			e.printStackTrace();
		}
	}
	
	//账户明细查询
	@Test
	public void testAccountDetailQuery(){
		AccountDetailQueryInfoModel model = new AccountDetailQueryInfoModel();
		model.setUserName("operator");
		model.setUserPwd("operator");
		model.setMerchantId("001053110000001");
		model.setReqNo("cash00000000000000000001");
		model.setBeginDate("20160612");
		model.setEndDate("20160612");
		model.setPageNum("1");
		model.setPageSize("");
		model.setAccountType("0");
		setGZUnionPayParams(model);
		handler.execAccountDetailQuery(model);
	}
}

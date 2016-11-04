package com.zufangbao.earth.yunxin.unionpay;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.zufangbao.earth.yunxin.unionpay.model.BatchDeductInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.BatchQueryInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.DeductDetailInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.RealTimeDeductInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.TransactionDetailQueryInfoModel;
import com.zufangbao.earth.yunxin.unionpay.template.gz.GZUnionPayPacketFactory;

/**
 * 广州银联报文生成测试
 * @author zhanghongbing
 *
 */
public class GZUnionPayPacketFactoryTest {
	
	public String BATCH_DEDUCT_PACKET = "<?xml version=\"1.0\" encoding=\"gbk\"?><GZELINK><INFO><TRX_CODE>100001</TRX_CODE><VERSION>05</VERSION><DATA_TYPE>2</DATA_TYPE><LEVEL>5</LEVEL><USER_NAME>%s</USER_NAME><USER_PASS>%s</USER_PASS><REQ_SN>%s</REQ_SN><SIGNED_MSG></SIGNED_MSG></INFO><BODY><TRANS_SUM><BUSINESS_CODE>%s</BUSINESS_CODE><MERCHANT_ID>%s</MERCHANT_ID><TOTAL_ITEM>%s</TOTAL_ITEM><TOTAL_SUM>%s</TOTAL_SUM></TRANS_SUM><TRANS_DETAILS>%s</TRANS_DETAILS></BODY></GZELINK>";
	
	public String REAL_TIME_DEDUCT_PACKET = "<?xml version=\"1.0\" encoding=\"gbk\"?><GZELINK><INFO><TRX_CODE>100004</TRX_CODE><VERSION>05</VERSION><DATA_TYPE>2</DATA_TYPE><LEVEL>0</LEVEL><USER_NAME>%s</USER_NAME><USER_PASS>%s</USER_PASS><REQ_SN>%s</REQ_SN><SIGNED_MSG></SIGNED_MSG></INFO><BODY><TRANS_SUM><BUSINESS_CODE>%s</BUSINESS_CODE><MERCHANT_ID>%s</MERCHANT_ID><TOTAL_ITEM>1</TOTAL_ITEM><TOTAL_SUM>%s</TOTAL_SUM></TRANS_SUM><TRANS_DETAILS>%s</TRANS_DETAILS></BODY></GZELINK>";
	
	public String DEDUCT_PACKET_TRANS_DETAIL = "<TRANS_DETAIL><SN>%s</SN><BANK_CODE>%s</BANK_CODE><ACCOUNT_TYPE>00</ACCOUNT_TYPE><ACCOUNT_NO>%s</ACCOUNT_NO><ACCOUNT_NAME>%s</ACCOUNT_NAME><ID>%s</ID><PROVINCE>%s</PROVINCE><CITY>%s</CITY><AMOUNT>%s</AMOUNT><REMARK>%s</REMARK></TRANS_DETAIL>";
	
	public String BATCH_QUERY_PACKET = "<?xml version=\"1.0\" encoding=\"gbk\"?><GZELINK><INFO><TRX_CODE>200001</TRX_CODE><VERSION>05</VERSION><DATA_TYPE>2</DATA_TYPE><USER_NAME>%s</USER_NAME><USER_PASS>%s</USER_PASS><REQ_SN>%s</REQ_SN><SIGNED_MSG></SIGNED_MSG></INFO><BODY><QUERY_TRANS><QUERY_SN>%s</QUERY_SN></QUERY_TRANS></BODY></GZELINK>";
	
	public String TRANSACTION_DETAIL_QUERY_PACKET = "<?xml version=\"1.0\" encoding=\"gbk\"?><GZELINK><INFO><TRX_CODE>200002</TRX_CODE><VERSION>05</VERSION><DATA_TYPE>2</DATA_TYPE><USER_NAME>%s</USER_NAME><USER_PASS>%s</USER_PASS><REQ_SN>%s</REQ_SN><SIGNED_MSG></SIGNED_MSG></INFO><BODY><QUERY_TRANS><MERCHANT_ID>%s</MERCHANT_ID><BEGIN_DATE>%s</BEGIN_DATE><END_DATE>%s</END_DATE><PAGE_NUM>%s</PAGE_NUM><PAGE_SIZE>%s</PAGE_SIZE><SF_TYPE>S</SF_TYPE><REQ_TYPE>0</REQ_TYPE><RESULT_TYPE>2</RESULT_TYPE></QUERY_TRANS></BODY></GZELINK>";

	@Test
	public void testPacket() {
		assertEquals(BATCH_DEDUCT_PACKET, GZUnionPayPacketFactory.BATCH_DEDUCT_PACKET);
		assertEquals(REAL_TIME_DEDUCT_PACKET, GZUnionPayPacketFactory.REAL_TIME_DEDUCT_PACKET);
		assertEquals(DEDUCT_PACKET_TRANS_DETAIL, GZUnionPayPacketFactory.DEDUCT_PACKET_TRANS_DETAIL);
		assertEquals(BATCH_QUERY_PACKET, GZUnionPayPacketFactory.BATCH_QUERY_PACKET);
		assertEquals(TRANSACTION_DETAIL_QUERY_PACKET, GZUnionPayPacketFactory.TRANSACTION_DETAIL_QUERY_PACKET);
	}
	
	@Test
	public void testGenerateBatchDeductPacket() {
		BatchDeductInfoModel model = new BatchDeductInfoModel();
		model.setUserName("testUserName");
		model.setReqNo("testReqNo");
		model.setMerchantId("testMerchantId");
		model.setTotalItem(1);
		model.setTotalSum(new BigDecimal("0.1"));
		
		List<DeductDetailInfoModel> detailInfos = new ArrayList<DeductDetailInfoModel>();
		DeductDetailInfoModel detail1 = new DeductDetailInfoModel();
		detail1.setSn("testSn1");
		detail1.setBankCode("testBankCode1");
		detail1.setAccountName("testAccountName1");
		detail1.setAccountNo("testAccountNo1");
		detail1.setRemark("testRemark1");
		DeductDetailInfoModel detail2 = new DeductDetailInfoModel();
		detail2.setSn("testSn2");
		detail2.setBankCode("testBankCode2");
		detail2.setAccountName("testAccountName2");
		detail2.setAccountNo("testAccountNo2");
		detail2.setRemark("testRemark2");
		
		detailInfos.add(detail1);
		detailInfos.add(detail2);
		model.setDetailInfos(detailInfos);
		
		String xmlPacket = GZUnionPayPacketFactory.generateBatchDeductPacket(model);
		assertTrue(xmlPacket.contains("<TRX_CODE>100001</TRX_CODE>"));
		assertTrue(xmlPacket.contains("<LEVEL>5</LEVEL>"));
		assertTrue(xmlPacket.contains("<USER_NAME>testUserName</USER_NAME>"));
		assertTrue(xmlPacket.contains("<USER_PASS></USER_PASS>"));
		assertTrue(xmlPacket.contains("<REQ_SN>testReqNo</REQ_SN>"));
		assertTrue(xmlPacket.contains("<BUSINESS_CODE></BUSINESS_CODE>"));
		assertTrue(xmlPacket.contains("<MERCHANT_ID>testMerchantId</MERCHANT_ID>"));
		assertTrue(xmlPacket.contains("<TOTAL_ITEM>1</TOTAL_ITEM>"));
		assertTrue(xmlPacket.contains("<TOTAL_SUM>10</TOTAL_SUM>"));
		assertTrue(xmlPacket.contains("<SN>testSn1</SN>"));
		assertTrue(xmlPacket.contains("<SN>testSn2</SN>"));
		assertTrue(xmlPacket.contains("<BANK_CODE>testBankCode1</BANK_CODE>"));
		assertTrue(xmlPacket.contains("<BANK_CODE>testBankCode2</BANK_CODE>"));
		assertTrue(xmlPacket.contains("<ACCOUNT_NO>testAccountNo1</ACCOUNT_NO>"));
		assertTrue(xmlPacket.contains("<ACCOUNT_NO>testAccountNo2</ACCOUNT_NO>"));
		assertTrue(xmlPacket.contains("<ACCOUNT_NAME>testAccountName1</ACCOUNT_NAME>"));
		assertTrue(xmlPacket.contains("<ACCOUNT_NAME>testAccountName2</ACCOUNT_NAME>"));
		assertTrue(xmlPacket.contains("<AMOUNT>0</AMOUNT>"));
		assertTrue(xmlPacket.contains("<REMARK>testRemark1</REMARK>"));
		assertTrue(xmlPacket.contains("<REMARK>testRemark2</REMARK>"));
	}
	
	@Test
	public void tesGenerateRealTimeDeductPacket() {
		RealTimeDeductInfoModel model = new RealTimeDeductInfoModel();
		model.setUserName("testUserName");
		model.setReqNo("testReqNo");
		model.setMerchantId("testMerchantId");
		
		DeductDetailInfoModel deductDetailInfoModel = new DeductDetailInfoModel();
		deductDetailInfoModel.setSn("testSn");
		deductDetailInfoModel.setAccountName("testAccountName");
		deductDetailInfoModel.setRemark("testRemark");
		model.setDeductDetailInfoModel(deductDetailInfoModel);
		
		String xmlPacket = GZUnionPayPacketFactory.generateRealTimeDeductPacket(model);
		assertTrue(xmlPacket.contains("<TRX_CODE>100004</TRX_CODE>"));
		assertTrue(xmlPacket.contains("<LEVEL>0</LEVEL>"));
		assertTrue(xmlPacket.contains("<USER_NAME>testUserName</USER_NAME>"));
		assertTrue(xmlPacket.contains("<USER_PASS></USER_PASS>"));
		assertTrue(xmlPacket.contains("<REQ_SN>testReqNo</REQ_SN>"));
		assertTrue(xmlPacket.contains("<BUSINESS_CODE></BUSINESS_CODE>"));
		assertTrue(xmlPacket.contains("<MERCHANT_ID>testMerchantId</MERCHANT_ID>"));
		assertTrue(xmlPacket.contains("<TOTAL_SUM>0</TOTAL_SUM>"));
		assertTrue(xmlPacket.contains("<SN>testSn</SN>"));
		assertTrue(xmlPacket.contains("<BANK_CODE></BANK_CODE>"));
		assertTrue(xmlPacket.contains("<ACCOUNT_NO></ACCOUNT_NO>"));
		assertTrue(xmlPacket.contains("<ACCOUNT_NAME>testAccountName</ACCOUNT_NAME>"));
		assertTrue(xmlPacket.contains("<AMOUNT>0</AMOUNT>"));
		assertTrue(xmlPacket.contains("<REMARK>testRemark</REMARK>"));
	}
	
	@Test
	public void testGenerateBatchQueryPacket() {
		BatchQueryInfoModel model = new BatchQueryInfoModel();
		model.setUserName("testUserName");
		model.setReqNo("testReqNo");
		model.setQueryReqNo("testQueryReqNo");
		
		String xmlPacket = GZUnionPayPacketFactory.generateBatchQueryPacket(model);
		assertTrue(xmlPacket.contains("<TRX_CODE>200001</TRX_CODE>"));
		assertTrue(xmlPacket.contains("<USER_NAME>testUserName</USER_NAME>"));
		assertTrue(xmlPacket.contains("<USER_PASS></USER_PASS>"));
		assertTrue(xmlPacket.contains("<REQ_SN>testReqNo</REQ_SN>"));
		assertTrue(xmlPacket.contains("<QUERY_SN>testQueryReqNo</QUERY_SN>"));
	}
	
	@Test
	public void testGenerateTransactionDetailQueryPacket() {
		TransactionDetailQueryInfoModel model = new TransactionDetailQueryInfoModel();
		model.setUserName("testUserName");
		model.setReqNo("testReqNo");
		model.setMerchantId("testMerchantId");
		model.setEndDate("testEndDate");
		model.setPageNum("1");
		
		String xmlPacket = GZUnionPayPacketFactory.generateTransactionDetailQueryPacket(model);
		assertTrue(xmlPacket.contains("<TRX_CODE>200002</TRX_CODE>"));
		assertTrue(xmlPacket.contains("<USER_NAME>testUserName</USER_NAME>"));
		assertTrue(xmlPacket.contains("<USER_PASS></USER_PASS>"));
		assertTrue(xmlPacket.contains("<REQ_SN>testReqNo</REQ_SN>"));
		assertTrue(xmlPacket.contains("<MERCHANT_ID>testMerchantId</MERCHANT_ID>"));
		assertTrue(xmlPacket.contains("<BEGIN_DATE></BEGIN_DATE>"));
		assertTrue(xmlPacket.contains("<END_DATE>testEndDate</END_DATE>"));
		assertTrue(xmlPacket.contains("<PAGE_NUM>1</PAGE_NUM>"));
		assertTrue(xmlPacket.contains("<PAGE_SIZE>5000</PAGE_SIZE>"));
		assertTrue(xmlPacket.contains("<SF_TYPE>S</SF_TYPE>"));
		assertTrue(xmlPacket.contains("<REQ_TYPE>0</REQ_TYPE>"));
		assertTrue(xmlPacket.contains("<RESULT_TYPE>2</RESULT_TYPE>"));
	}
	
}

package com.zufangbao.earth.yunxin.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.thoughtworks.xstream.XStream;
import com.zufangbao.earth.api.GZUnionPayApiHandler;
import com.zufangbao.earth.api.TransactionDetailConstant.ErrMsg;
import com.zufangbao.earth.api.TransactionDetailConstant.FunctionName;
import com.zufangbao.earth.api.TransactionDetailConstant.RTNCode;
import com.zufangbao.earth.api.exception.TransactionDetailApiException;
import com.zufangbao.earth.api.xml.TransactionDetail;
import com.zufangbao.earth.api.xml.TransactionDetailQueryParams;
import com.zufangbao.earth.api.xml.TransactionQueryRtnXml;
import com.zufangbao.earth.util.MockUtils;
import com.zufangbao.earth.util.XmlUtil;
import com.zufangbao.earth.yunxin.unionpay.component.IGZUnionPayApiComponent;
import com.zufangbao.sun.utils.DateUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class GZUnionPayApiHandlerTest {
	@Autowired
	private GZUnionPayApiHandler gZUnionPayApiHandler;
	
	@Autowired
	private MockUtils mockUtils;
	
	@Autowired
	private IGZUnionPayApiComponent iGZUnionPayApiComponent;
	
	@BeforeClass
	public static void init(){
		MockUtils.init();
	}
	
	@AfterClass
	public static void end(){
		MockUtils.end();
	}
	
	private void assetValid(int code, String msg,TransactionQueryRtnXml transactionQueryRtnXml){
		assertEquals(code+"",transactionQueryRtnXml.getXmlPacketInfo().getRetCode());
		assertEquals(FunctionName.FUNC_TRANSACTION_DETAIL_QUERY+"",transactionQueryRtnXml.getXmlPacketInfo().getFunName());
		assertEquals(msg,transactionQueryRtnXml.getXmlPacketInfo().getErrMSG());
	}
	
	//@Test
	//需根据实际情况造一天符合签名的数据
	@Sql("classpath:test/yunxin/api/testMock_queryUionTransactionDetail.sql")
	public void testQueryTransactionDetail(){
		String info = "<?xml version=\"1.0\" encoding=\"gbk\"?><SDKPGK><INFO><FUNNAM>NTEBPINF</FUNNAM></INFO><NTOPRMODX><GATWAY>0002</GATWAY></NTOPRMODX>";
		XStream xStream = XmlUtil.xStream();
		String today_substract2 = DateUtils.format(DateUtils.addDays(new Date(), -2),DateUtils.DATE_FORMAT_YYYYMMDD);
		String today = DateUtils.format(new Date(),DateUtils.DATE_FORMAT_YYYYMMDD);
		TransactionDetailQueryParams transactionDetailOutQueryParams= new TransactionDetailQueryParams(today_substract2, today,
				"", "001053110000001", "");
		String reqPacketXml = info+xStream.toXML(transactionDetailOutQueryParams)+"</SDKPGK>";
		String expectedRtnXml = "<?xml version=\"1.0\" encoding=\"utf8\"?><GZELINK><INFO><TRX_CODE>200002</TRX_CODE><VERSION>05</VERSION><DATA_TYPE>2</DATA_TYPE><REQ_SN>25e106bc-3ebb-43e4-b249-3071b3ea3e85</REQ_SN><RET_CODE>0000</RET_CODE><ERR_MSG></ERR_MSG><SIGNED_MSG>534e97c192a142cec23033410cea9177ac4744724f5f722ccd4c4d5e27b20592378351e3d4761035262cb9b17ad83a6008382bc8daa00673eb61b48202f290e130a4f662f70610266d0fb5fe495f76c1c72de415558101e97193907a1c2af5b3053cd194399063c7e71848483dbd774e6fb7d97b2254ecfa81fe61c9b938db59</SIGNED_MSG></INFO><BODY><QUERY_TRANS><QUERY_SN></QUERY_SN><QUERY_REMARK></QUERY_REMARK><MERCHANT_ID>001053110000001</MERCHANT_ID><BEGIN_DATE>20160501</BEGIN_DATE><END_DATE>20160501</END_DATE><PAGE_SUM>1</PAGE_SUM><PAGE_NUM>1</PAGE_NUM><PAGE_SIZE>1000</PAGE_SIZE><TOTAL_ITEM>24</TOTAL_ITEM><TOTAL_SUM>30927651</TOTAL_SUM></QUERY_TRANS><RET_DETAILS><RET_DETAIL><REQ_SN>20160430071148587596</REQ_SN><SN>L201408070044802155</SN><SF_TYPE>S</SF_TYPE><TRX_CODE>100001</TRX_CODE><BUSINESS_CODE>14901</BUSINESS_CODE><ACCOUNT>6222081302002142160</ACCOUNT><ACCOUNT_NAME>account_name</ACCOUNT_NAME><AMOUNT>2602723</AMOUNT><CUST_USERID></CUST_USERID><FEE></FEE><COMPLETE_TIME>20160501002654</COMPLETE_TIME><SETT_DATE>20160501</SETT_DATE><RECKON_ACCOUNT>ZTX04</RECKON_ACCOUNT><REMARK>3</REMARK><RET_CODE>0000</RET_CODE><ERR_MSG>suc</ERR_MSG></RET_DETAIL></RET_DETAILS></BODY></GZELINK>";
		mockUtils.mockPostRequest("/test-transaction-detail", expectedRtnXml);
		String rtnPacketXml = gZUnionPayApiHandler.queryTransactionDetail(reqPacketXml);
		xStream.alias("SDKPGK", TransactionQueryRtnXml.class);
		TransactionQueryRtnXml transactionQueryRtnXml = (TransactionQueryRtnXml)xStream.fromXML(rtnPacketXml);
		assetValid(RTNCode.SUC, "",transactionQueryRtnXml);
		List<TransactionDetail> detailList = transactionQueryRtnXml.getTransactionDetailList();
		assertEquals(1, detailList.size());
		
		assertEquals("6222081302002142160",detailList.get(0).getAccount());
		assertEquals("account_name",detailList.get(0).getAccountName());
		assertEquals("D",detailList.get(0).getAccountSide());
		assertEquals("26027.23",detailList.get(0).getAmount());
		assertEquals("20160501002654",detailList.get(0).getCompleteTime());
		assertEquals("3",detailList.get(0).getRemark());
		assertEquals("ZTX04",detailList.get(0).getReckonAccount());
		assertEquals("20160430071148587596",detailList.get(0).getReqNo());
		
		System.out.println(rtnPacketXml);
	}
	
	//@Test
	//需根据实际情况造一天符合签名的数据
	@Sql("classpath:test/yunxin/api/testMock_queryUionTransactionDetail.sql")
	public void testQueryTransactionDetail_empty(){
		//校验银联返回空结果
		String info = "<?xml version=\"1.0\" encoding=\"gbk\"?><SDKPGK><INFO><FUNNAM>NTEBPINF</FUNNAM></INFO><NTOPRMODX><GATWAY>0002</GATWAY></NTOPRMODX>";
		XStream xStream = XmlUtil.xStream();
		String today_substract2 = DateUtils.format(DateUtils.addDays(new Date(), -2),DateUtils.DATE_FORMAT_YYYYMMDD);
		String today = DateUtils.format(new Date(),DateUtils.DATE_FORMAT_YYYYMMDD);
		TransactionDetailQueryParams transactionDetailOutQueryParams= new TransactionDetailQueryParams(today_substract2, today,
				"", "001053110000001", "");
		String reqPacketXml = info+xStream.toXML(transactionDetailOutQueryParams)+"</SDKPGK>";
		String expectedRtnXml = "<?xml version=\"1.0\" encoding=\"GBK\"?><GZELINK><INFO><TRX_CODE>200002</TRX_CODE><VERSION>05</VERSION><DATA_TYPE>2</DATA_TYPE><REQ_SN>25e106bc-3ebb-43e4-b249-3071b3ea3e85</REQ_SN><RET_CODE>0000</RET_CODE><ERR_MSG></ERR_MSG><SIGNED_MSG>534e97c192a142cec23033410cea9177ac4744724f5f722ccd4c4d5e27b20592378351e3d4761035262cb9b17ad83a6008382bc8daa00673eb61b48202f290e130a4f662f70610266d0fb5fe495f76c1c72de415558101e97193907a1c2af5b3053cd194399063c7e71848483dbd774e6fb7d97b2254ecfa81fe61c9b938db59</SIGNED_MSG></INFO><BODY><QUERY_TRANS><QUERY_SN></QUERY_SN><QUERY_REMARK></QUERY_REMARK><MERCHANT_ID>001053110000001</MERCHANT_ID><BEGIN_DATE>20160501</BEGIN_DATE><END_DATE>20160501</END_DATE><PAGE_SUM>1</PAGE_SUM><PAGE_NUM>1</PAGE_NUM><PAGE_SIZE>1000</PAGE_SIZE><TOTAL_ITEM>24</TOTAL_ITEM><TOTAL_SUM>30927651</TOTAL_SUM></QUERY_TRANS><RET_DETAILS></RET_DETAILS></BODY></GZELINK>";
		mockUtils.mockPostRequest("/test-transaction-detail", expectedRtnXml);
		String rtnPacketXml = gZUnionPayApiHandler.queryTransactionDetail(reqPacketXml);
		xStream.alias("SDKPGK", TransactionQueryRtnXml.class);
		TransactionQueryRtnXml transactionQueryRtnXml = (TransactionQueryRtnXml)xStream.fromXML(rtnPacketXml);
		assetValid(RTNCode.SUC, "",transactionQueryRtnXml);
		List<TransactionDetail> detailList = transactionQueryRtnXml.getTransactionDetailList();
		assertTrue(CollectionUtils.isEmpty(detailList));
		assertFalse(rtnPacketXml.contains("<NTEBPINFX>"));
		System.out.println(rtnPacketXml);
	}
	
	@Test
	@Sql("classpath:test/yunxin/api/testMock_queryUionTransactionDetail.sql")
	public void testForFormatInvalid_err_fmt_begindate(){
		//起始日期格式错误
		String info = "<?xml version=\"1.0\" encoding=\"gbk\"?><SDKPGK><INFO><FUNNAM>NTEBPINF</FUNNAM></INFO><NTOPRMODX><GATWAY>0002</GATWAY></NTOPRMODX>";
		XStream xStream = XmlUtil.xStream();
		TransactionDetailQueryParams transactionDetailOutQueryParams= new TransactionDetailQueryParams("2016050100x", "20160509",
				"", "001053110000001", "");
		String reqPacketXml = info+xStream.toXML(transactionDetailOutQueryParams)+"</SDKPGK>";
		String rtnPacketXml = gZUnionPayApiHandler.queryTransactionDetail(reqPacketXml);
		xStream.alias("SDKPGK", TransactionQueryRtnXml.class);
		TransactionQueryRtnXml transactionQueryRtnXml = (TransactionQueryRtnXml)xStream.fromXML(rtnPacketXml);
		assetValid(RTNCode.WRONG_FORMAT, ErrMsg.ERR_MSG_WRONG_BEGIN_DATE_FORMAT,transactionQueryRtnXml);
		assertTrue(CollectionUtils.isEmpty(transactionQueryRtnXml.getTransactionDetailList()));
		System.out.println(rtnPacketXml);
	}
	
	@Test
	@Sql("classpath:test/yunxin/api/testMock_queryUionTransactionDetail.sql")
	public void testForFormatInvalid_err_fmt_enddate(){
		//终止日期格式错误
		String info = "<?xml version=\"1.0\" encoding=\"gbk\"?><SDKPGK><INFO><FUNNAM>NTEBPINF</FUNNAM></INFO><NTOPRMODX><GATWAY>0002</GATWAY></NTOPRMODX>";
		XStream xStream = XmlUtil.xStream();
		TransactionDetailQueryParams transactionDetailOutQueryParams= new TransactionDetailQueryParams("20160501", "20160540x",
				"", "001053110000001", "");
		String reqPacketXml = info+xStream.toXML(transactionDetailOutQueryParams)+"</SDKPGK>";
		String rtnPacketXml = gZUnionPayApiHandler.queryTransactionDetail(reqPacketXml);
		xStream.alias("SDKPGK", TransactionQueryRtnXml.class);
		TransactionQueryRtnXml transactionQueryRtnXml = (TransactionQueryRtnXml)xStream.fromXML(rtnPacketXml);
		assetValid(RTNCode.WRONG_FORMAT, ErrMsg.ERR_MSG_WRONG_END_DATE_FORMAT,transactionQueryRtnXml);
		assertTrue(CollectionUtils.isEmpty(transactionQueryRtnXml.getTransactionDetailList()));
		System.out.println(rtnPacketXml);
	}
	
	@Test
	@Sql("classpath:test/yunxin/api/testMock_queryUionTransactionDetail.sql")
	public void testForFormatInvalid_err_today_gt_60(){
		
		//跨度超过60天
		String info = "<?xml version=\"1.0\" encoding=\"gbk\"?><SDKPGK><INFO><FUNNAM>NTEBPINF</FUNNAM></INFO><NTOPRMODX><GATWAY>0002</GATWAY></NTOPRMODX>";
		XStream xStream = XmlUtil.xStream();
		String today_substract61 = DateUtils.format(DateUtils.addDays(new Date(), -61),DateUtils.DATE_FORMAT_YYYYMMDD);
		String today_substract59 = DateUtils.format(DateUtils.addDays(new Date(), -59),DateUtils.DATE_FORMAT_YYYYMMDD);
		TransactionDetailQueryParams transactionDetailOutQueryParams= new TransactionDetailQueryParams(today_substract61, today_substract59,
				"", "001053110000001", "");
		String reqPacketXml = info+xStream.toXML(transactionDetailOutQueryParams)+"</SDKPGK>";
		String rtnPacketXml = gZUnionPayApiHandler.queryTransactionDetail(reqPacketXml);
		xStream.alias("SDKPGK", TransactionQueryRtnXml.class);
		TransactionQueryRtnXml transactionQueryRtnXml = (TransactionQueryRtnXml)xStream.fromXML(rtnPacketXml);
		assetValid(RTNCode.WRONG_FORMAT, ErrMsg.ERR_MSG_TODAY_BEGIN_DATE_GT_60,transactionQueryRtnXml);
		assertTrue(CollectionUtils.isEmpty(transactionQueryRtnXml.getTransactionDetailList()));
		System.out.println(rtnPacketXml);
	}
	
	@Test
	@Sql("classpath:test/yunxin/api/testMock_queryUionTransactionDetail.sql")
	public void testForFormatInvalid_err_end_begin_gt_3(){
		
		//跨度超过3天
		String info = "<?xml version=\"1.0\" encoding=\"gbk\"?><SDKPGK><INFO><FUNNAM>NTEBPINF</FUNNAM></INFO><NTOPRMODX><GATWAY>0002</GATWAY></NTOPRMODX>";
		XStream xStream = XmlUtil.xStream();
		String today_substract3 = DateUtils.format(DateUtils.addDays(new Date(), -3),DateUtils.DATE_FORMAT_YYYYMMDD);
		String today = DateUtils.format(new Date(),DateUtils.DATE_FORMAT_YYYYMMDD);
		TransactionDetailQueryParams transactionDetailOutQueryParams= new TransactionDetailQueryParams(today_substract3, today,
				"", "001053110000001", "");
		String reqPacketXml = info+xStream.toXML(transactionDetailOutQueryParams)+"</SDKPGK>";
		String rtnPacketXml = gZUnionPayApiHandler.queryTransactionDetail(reqPacketXml);
		xStream.alias("SDKPGK", TransactionQueryRtnXml.class);
		TransactionQueryRtnXml transactionQueryRtnXml = (TransactionQueryRtnXml)xStream.fromXML(rtnPacketXml);
		assetValid(RTNCode.WRONG_FORMAT, ErrMsg.ERR_MSG_END_DATE_BEGIN_DATE_GE_3,transactionQueryRtnXml);
		assertTrue(CollectionUtils.isEmpty(transactionQueryRtnXml.getTransactionDetailList()));
		System.out.println(rtnPacketXml);
	}
	
	@Test
	@Sql("classpath:test/yunxin/api/testMock_queryUionTransactionDetail.sql")
	public void testForFormatInvalid_empty_merId(){
		
		//商户号为空
		String info = "<?xml version=\"1.0\" encoding=\"gbk\"?><SDKPGK><INFO><FUNNAM>NTEBPINF</FUNNAM></INFO><NTOPRMODX><GATWAY>0002</GATWAY></NTOPRMODX>";
		XStream xStream = XmlUtil.xStream();
		String today_substract2 = DateUtils.format(DateUtils.addDays(new Date(), -2),DateUtils.DATE_FORMAT_YYYYMMDD);
		String today = DateUtils.format(new Date(),DateUtils.DATE_FORMAT_YYYYMMDD);
		TransactionDetailQueryParams transactionDetailOutQueryParams= new TransactionDetailQueryParams(today_substract2, today,
				"", "", "");
		String reqPacketXml = info+xStream.toXML(transactionDetailOutQueryParams)+"</SDKPGK>";
		String rtnPacketXml = gZUnionPayApiHandler.queryTransactionDetail(reqPacketXml);
		xStream.alias("SDKPGK", TransactionQueryRtnXml.class);
		TransactionQueryRtnXml transactionQueryRtnXml = (TransactionQueryRtnXml)xStream.fromXML(rtnPacketXml);
		assetValid(RTNCode.WRONG_FORMAT, ErrMsg.ERR_MSG_EMPTY_MERID,transactionQueryRtnXml);
		assertTrue(CollectionUtils.isEmpty(transactionQueryRtnXml.getTransactionDetailList()));
		System.out.println(rtnPacketXml);
	}
	
	@Test
	@Sql("classpath:test/yunxin/api/testMock_queryUionTransactionDetail.sql")
	public void testForFormatInvalid_err_fmt_xml(){
		
		//报文格式错误
		String info = "<?xml version=\"1.0\" encoding=\"gbk\"?><INFO><FUNNAM>NTEBPINF</FUNNAM></INFO><NTOPRMODX><GATWAY>0002</GATWAY></NTOPRMODX>";
		XStream xStream = XmlUtil.xStream();
		String today_substract2 = DateUtils.format(DateUtils.addDays(new Date(), -2),DateUtils.DATE_FORMAT_YYYYMMDD);
		String today = DateUtils.format(new Date(),DateUtils.DATE_FORMAT_YYYYMMDD);
		TransactionDetailQueryParams transactionDetailOutQueryParams= new TransactionDetailQueryParams(today_substract2, today,
				"", "", "");
		String reqPacketXml = info+xStream.toXML(transactionDetailOutQueryParams)+"</SDKPGK>";
		String rtnPacketXml = gZUnionPayApiHandler.queryTransactionDetail(reqPacketXml);
		xStream.alias("SDKPGK", TransactionQueryRtnXml.class);
		TransactionQueryRtnXml transactionQueryRtnXml = (TransactionQueryRtnXml)xStream.fromXML(rtnPacketXml);
		assetValid(RTNCode.WRONG_FORMAT, ErrMsg.ERR_XML_FORMAT,transactionQueryRtnXml);
		assertTrue(CollectionUtils.isEmpty(transactionQueryRtnXml.getTransactionDetailList()));
		System.out.println(rtnPacketXml);
	}
	
	@Test
	@Sql("classpath:test/yunxin/api/testMock_queryUionTransactionDetail.sql")
	public void testForFormatInvalid_err_fmt_xml_node(){
		
		//报文格式错误,空函数名
		String info = "<?xml version=\"1.0\" encoding=\"gbk\"?><NTOPRMODX><INFO><FUNNAM></FUNNAM></INFO><NTOPRMODX><GATWAY>0002</GATWAY></NTOPRMODX>";
		XStream xStream = XmlUtil.xStream();
		String today_substract2 = DateUtils.format(DateUtils.addDays(new Date(), -2),DateUtils.DATE_FORMAT_YYYYMMDD);
		String today = DateUtils.format(new Date(),DateUtils.DATE_FORMAT_YYYYMMDD);
		TransactionDetailQueryParams transactionDetailOutQueryParams= new TransactionDetailQueryParams(today_substract2, today,
				"", "", "");
		String reqPacketXml = info+xStream.toXML(transactionDetailOutQueryParams)+"</SDKPGK>";
		String rtnPacketXml = gZUnionPayApiHandler.queryTransactionDetail(reqPacketXml);
		xStream.alias("SDKPGK", TransactionQueryRtnXml.class);
		TransactionQueryRtnXml transactionQueryRtnXml = (TransactionQueryRtnXml)xStream.fromXML(rtnPacketXml);
		assetValid(RTNCode.WRONG_FORMAT, ErrMsg.ERR_XML_FORMAT,transactionQueryRtnXml);
		assertTrue(CollectionUtils.isEmpty(transactionQueryRtnXml.getTransactionDetailList()));
		System.out.println(rtnPacketXml);
	}
	
	//@Test
	//需根据实际情况造一天符合签名的数据
	@Sql("classpath:test/yunxin/api/testMock_queryUionTransactionDetail.sql")
	public void testQueryTransactionDetail_filterReckonAccount(){
		//校验是否过滤清分账户
		String today_substract2 = DateUtils.format(DateUtils.addDays(new Date(), -2),DateUtils.DATE_FORMAT_YYYYMMDD);
		String today = DateUtils.format(new Date(),DateUtils.DATE_FORMAT_YYYYMMDD);
		TransactionDetailQueryParams transactionDetailOutQueryParams= new TransactionDetailQueryParams(today_substract2, today,
				"", "001053110000001", "ZTX04");
		String expectedRtnXml = "<?xml version=\"1.0\" encoding=\"UTf8\"?><GZELINK><INFO><TRX_CODE>200002</TRX_CODE><VERSION>05</VERSION><DATA_TYPE>2</DATA_TYPE><REQ_SN>25e106bc-3ebb-43e4-b249-3071b3ea3e85</REQ_SN><RET_CODE>0000</RET_CODE><ERR_MSG></ERR_MSG><SIGNED_MSG>534e97c192a142cec23033410cea9177ac4744724f5f722ccd4c4d5e27b20592378351e3d4761035262cb9b17ad83a6008382bc8daa00673eb61b48202f290e130a4f662f70610266d0fb5fe495f76c1c72de415558101e97193907a1c2af5b3053cd194399063c7e71848483dbd774e6fb7d97b2254ecfa81fe61c9b938db59</SIGNED_MSG></INFO><BODY><QUERY_TRANS><QUERY_SN></QUERY_SN><QUERY_REMARK></QUERY_REMARK><MERCHANT_ID>001053110000001</MERCHANT_ID><BEGIN_DATE>20160501</BEGIN_DATE><END_DATE>20160501</END_DATE><PAGE_SUM>1</PAGE_SUM><PAGE_NUM>1</PAGE_NUM><PAGE_SIZE>1000</PAGE_SIZE><TOTAL_ITEM>24</TOTAL_ITEM><TOTAL_SUM>30927651</TOTAL_SUM></QUERY_TRANS><RET_DETAILS><RET_DETAIL><REQ_SN>20160430071148587596</REQ_SN><SN>L201408070044802155</SN><SF_TYPE>S</SF_TYPE><TRX_CODE>100001</TRX_CODE><BUSINESS_CODE>14901</BUSINESS_CODE><ACCOUNT>6222081302002142160</ACCOUNT><ACCOUNT_NAME>冯新明</ACCOUNT_NAME><AMOUNT>2602723</AMOUNT><CUST_USERID></CUST_USERID><FEE></FEE><COMPLETE_TIME>20160501002654</COMPLETE_TIME><SETT_DATE>20160501</SETT_DATE><RECKON_ACCOUNT>ZTX04</RECKON_ACCOUNT><REMARK>3</REMARK><RET_CODE>0000</RET_CODE><ERR_MSG>交易?成功</ERR_MSG></RET_DETAIL></RET_DETAILS></BODY></GZELINK>";
		mockUtils.mockPostRequest("/test-transaction-detail", expectedRtnXml);
		try {
			List<TransactionDetail> transactionDetailList = gZUnionPayApiHandler.execTransactionDetailQuery(transactionDetailOutQueryParams);
			assertEquals(1, transactionDetailList.size());
			assertEquals("ZTX04",transactionDetailList.get(0).getReckonAccount());
		} catch (TransactionDetailApiException e) {
			e.printStackTrace();
			fail();
		}
		
		transactionDetailOutQueryParams= new TransactionDetailQueryParams(today_substract2, today,
				"", "001053110000001", "test");
		mockUtils.mockPostRequest("/test-transaction-detail", expectedRtnXml);
		try {
			List<TransactionDetail> transactionDetailList = gZUnionPayApiHandler.execTransactionDetailQuery(transactionDetailOutQueryParams);
			assertEquals(0, transactionDetailList.size());
		} catch (TransactionDetailApiException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	@Sql("classpath:test/yunxin/api/testMock_queryUionTransactionDetail.sql")
	public void testDirectBank(){
		//测试引起直联 正常
		String expectedDirectBankXml= "<?xml version=\"1.0\" encoding=\"utf-8\" ?><CMBSDKPGK><INFO><DATTYP>2</DATTYP><ERRMSG/><FUNNAM>GetTransInfo</FUNNAM><LGNNAM>银企直连专用集团1</LGNNAM><RETCOD>0</RETCOD></INFO><NTQTSINFZ><AMTCDR>D</AMTCDR><APDFLG>Y</APDFLG><ATHFLG>N</ATHFLG><BBKNBR>59</BBKNBR><BUSNAM>企业银行代发</BUSNAM><C_ATHFLG>无</C_ATHFLG><C_BBKNBR>福州</C_BBKNBR><C_ETYDAT>2015年05月01日</C_ETYDAT><C_GSBBBK/><C_RPYBBK/><C_TRSAMT>0.01</C_TRSAMT><C_TRSAMTD>0.01</C_TRSAMTD><C_TRSBLV>285,433.21</C_TRSBLV><C_VLTDAT>2015年05月01日</C_VLTDAT><ETYDAT>20150501</ETYDAT><ETYTIM>163833</ETYTIM><GSBBBK/><INFFLG>1</INFFLG><NAREXT>R000004788</NAREXT><NARYUR>陆金所付款</NARYUR><REFNBR>K6021700000011C</REFNBR><REFSUB/><REQNBR>0028775967</REQNBR><RPYBBK/><RSV30Z>**</RSV30Z><RSV31Z>10</RSV31Z><RSV50Z/><TRSAMT>-0.01</TRSAMT><TRSAMTD>0.01</TRSAMTD><TRSANL>AIGATR</TRSANL><TRSBLV>285433.21</TRSBLV><TRSCOD>AGCI</TRSCOD><VLTDAT>20150501</VLTDAT><YURREF>P218696120</YURREF></NTQTSINFZ></CMBSDKPGK>";
		mockUtils.mockPostRequest("/test-directbank", expectedDirectBankXml);
		
		TransactionDetailQueryParams transactionDetailOutQueryParams= new TransactionDetailQueryParams("20150501", "20150501",
				"123456", "", "");
		try {
			List<TransactionDetail> transactionDetailList = gZUnionPayApiHandler.execDirectBankTransactionDetailQuery(transactionDetailOutQueryParams);
			assertEquals(1, transactionDetailList.size());
			TransactionDetail transactionDetail = transactionDetailList.get(0);
			assertEquals("",transactionDetail.getAccount());
			assertEquals("",transactionDetail.getAccountName());
			assertEquals("C",transactionDetail.getAccountSide());
			assertEquals("0.01",transactionDetail.getAmount().toString());
			assertEquals("20150501163833",transactionDetail.getCompleteTime());
			assertNull(transactionDetail.getReckonAccount());
			assertEquals("陆金所付款",transactionDetail.getRemark());
			assertEquals("P218696120",transactionDetail.getReqNo());
			assertEquals("K6021700000011C",transactionDetail.getSerialNo());
		} catch (TransactionDetailApiException e) {
			fail();
			e.printStackTrace();
		}
	}
	
	@Test
	@Sql("classpath:test/yunxin/api/testMock_queryUionTransactionDetail.sql")
	public void testDirectBank_xml(){
		//测试直联 正常
		String info = "<?xml version=\"1.0\" encoding=\"gbk\"?><SDKPGK><INFO><FUNNAM>NTEBPINF</FUNNAM></INFO><NTOPRMODX><GATWAY>0003</GATWAY></NTOPRMODX>";
		String expectedDirectBankXml= "<?xml version=\"1.0\" encoding=\"utf-8\" ?><CMBSDKPGK><INFO><DATTYP>2</DATTYP><ERRMSG/><FUNNAM>GetTransInfo</FUNNAM><LGNNAM>银企直连专用集团1</LGNNAM><RETCOD>0</RETCOD></INFO><NTQTSINFZ><AMTCDR>D</AMTCDR><APDFLG>Y</APDFLG><ATHFLG>N</ATHFLG><BBKNBR>59</BBKNBR><BUSNAM>企业银行代发</BUSNAM><C_ATHFLG>无</C_ATHFLG><C_BBKNBR>福州</C_BBKNBR><C_ETYDAT>2015年05月01日</C_ETYDAT><C_GSBBBK/><C_RPYBBK/><C_TRSAMT>0.01</C_TRSAMT><C_TRSAMTD>0.01</C_TRSAMTD><C_TRSBLV>285,433.21</C_TRSBLV><C_VLTDAT>2015年05月01日</C_VLTDAT><ETYDAT>20150501</ETYDAT><ETYTIM>163833</ETYTIM><GSBBBK/><INFFLG>1</INFFLG><NAREXT>R000004788</NAREXT><NARYUR>陆金所付款</NARYUR><REFNBR>K6021700000011C</REFNBR><REFSUB/><REQNBR>0028775967</REQNBR><RPYBBK/><RSV30Z>**</RSV30Z><RSV31Z>10</RSV31Z><RSV50Z/><TRSAMT>-0.01</TRSAMT><TRSAMTD>0.01</TRSAMTD><TRSANL>AIGATR</TRSANL><TRSBLV>285433.21</TRSBLV><TRSCOD>AGCI</TRSCOD><VLTDAT>20150501</VLTDAT><YURREF>P218696120</YURREF></NTQTSINFZ></CMBSDKPGK>";
		mockUtils.mockPostRequest("/test-directbank", expectedDirectBankXml);
		TransactionDetailQueryParams transactionDetailOutQueryParams= new TransactionDetailQueryParams("20150501", "20150501",
				"123456", "", "");
		XStream xStream = XmlUtil.xStream();
		String reqPacketXml = info+xStream.toXML(transactionDetailOutQueryParams)+"</SDKPGK>";
		try {
			String rtnXml = gZUnionPayApiHandler.queryTransactionDetail(reqPacketXml);
			assertTrue(rtnXml.contains("<YURREF>P218696120</YURREF>"));
			assertTrue(rtnXml.contains("<RPYACC></RPYACC>"));
			assertTrue(rtnXml.contains("<RPYNAM></RPYNAM>"));
			assertTrue(rtnXml.contains("<AMTCDR>C</AMTCDR>"));
			assertTrue(rtnXml.contains("<TRSAMT>0.01</TRSAMT>"));
			assertTrue(rtnXml.contains("<ETYTIM>20150501163833</ETYTIM>"));
			assertTrue(rtnXml.contains("<NARYUR>陆金所付款</NARYUR>"));
			assertTrue(rtnXml.contains("<REFNBR>K6021700000011C</REFNBR>"));
		} catch (Exception e) {
			fail();
			e.printStackTrace();
		}
	}

}

package com.zufangbao.earth.yunxin.api;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.thoughtworks.xstream.XStream;
import com.zufangbao.earth.api.GZUnionPayApiHandler;
import com.zufangbao.earth.api.xml.TransactionDetailQueryParams;
import com.zufangbao.earth.util.XmlUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/context/applicationContext-*.xml",
		"classpath:/local/applicationContext-*.xml" })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class TransactionDetailApiTest {
	@Autowired
	private GZUnionPayApiHandler gzUnionPayApiHandler;
	
	@Test
	@Sql("classpath:test/yunxin/api/queryUionTransactionDetail.sql")
	public void testQueryTransactionDetail(){
		String info = "<?xml version=\"1.0\" encoding=\"gbk\"?><SDKPGK><INFO><FUNNAM>NTEBPINF</FUNNAM></INFO><NTOPRMODX><GATWAY>0002</GATWAY></NTOPRMODX>";
		XStream xStream = XmlUtil.xStream();
		TransactionDetailQueryParams transactionDetailOutQueryParams= new TransactionDetailQueryParams("20160612", "20160612",
				"", "001053110000001", "");
		String reqPacketXml = info+xStream.toXML(transactionDetailOutQueryParams)+"</SDKPGK>";
		String rtnPacketXml = gzUnionPayApiHandler.queryTransactionDetail(reqPacketXml);
		System.out.println(rtnPacketXml);
		
	}
	
	@Test
	@Sql("classpath:test/yunxin/api/queryUionTransactionDetail.sql")
	public void testQueryDirectbankTransactionDetail_rtn_transactionDetail(){
		String info = "<?xml version=\"1.0\" encoding=\"gbk\"?><SDKPGK><INFO><FUNNAM>NTEBPINF</FUNNAM></INFO><NTOPRMODX><GATWAY>0003</GATWAY></NTOPRMODX>";
		XStream xStream = XmlUtil.xStream();
		TransactionDetailQueryParams transactionDetailOutQueryParams= new TransactionDetailQueryParams("20150501", "20150501",
				"591902896710201", "", "");
		String reqPacketXml = info+xStream.toXML(transactionDetailOutQueryParams)+"</SDKPGK>";
		String rtnPacketXml = gzUnionPayApiHandler.queryTransactionDetail(reqPacketXml);
		System.out.println(rtnPacketXml);
	}
}

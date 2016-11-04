package com.zufangbao.earth.api.xml;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("SDKPGK")
public class TransactionQueryRtnXml {
	@XStreamAlias("INFO")
	private XmlPacketRtnInfo xmlPacketInfo;
	@XStreamImplicit(itemFieldName = "NTEBPINFZ")
	private List<TransactionDetail> transactionDetailList;
	public XmlPacketRtnInfo getXmlPacketInfo() {
		return xmlPacketInfo;
	}
	public void setXmlPacketInfo(XmlPacketRtnInfo xmlPacketInfo) {
		this.xmlPacketInfo = xmlPacketInfo;
	}
	public List<TransactionDetail> getTransactionDetailList() {
		return transactionDetailList;
	}
	public void setTransactionDetailList(
			List<TransactionDetail> transactionDetailList) {
		this.transactionDetailList = transactionDetailList;
	}
	
	public TransactionQueryRtnXml(){
		
	}
	public TransactionQueryRtnXml(XmlPacketRtnInfo xmlPacketInfo,
			List<TransactionDetail> transactionDetailList) {
		super();
		this.xmlPacketInfo = xmlPacketInfo;
		this.transactionDetailList = transactionDetailList;
	}
	
}

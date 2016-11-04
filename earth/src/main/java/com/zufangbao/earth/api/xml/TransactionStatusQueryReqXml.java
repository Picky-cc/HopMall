package com.zufangbao.earth.api.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SDKPGK")
public class TransactionStatusQueryReqXml {
	
	@XStreamAlias("INFO")
	private XmlPacketReqInfo xmlPacketReqInfo;
	
	@XStreamAlias("NTWAUEBPY")
	private TransactionStatusQueryReqBodyXml queryBody;

	public XmlPacketReqInfo getXmlPacketReqInfo() {
		return xmlPacketReqInfo;
	}

	public void setXmlPacketReqInfo(XmlPacketReqInfo xmlPacketReqInfo) {
		this.xmlPacketReqInfo = xmlPacketReqInfo;
	}

	public TransactionStatusQueryReqBodyXml getQueryBody() {
		return queryBody;
	}

	public void setQueryBody(TransactionStatusQueryReqBodyXml queryBody) {
		this.queryBody = queryBody;
	}
	
}

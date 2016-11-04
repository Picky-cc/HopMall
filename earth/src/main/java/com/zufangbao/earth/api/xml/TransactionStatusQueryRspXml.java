package com.zufangbao.earth.api.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("SDKPGK")
public class TransactionStatusQueryRspXml {
	
	@XStreamAlias("INFO")
	private XmlPacketRtnInfo xmlPacketInfo;
	
	@XStreamAlias("NTWAUEBPZ")
	private TransactionStatusQueryRspDetailXml detail;

	public XmlPacketRtnInfo getXmlPacketInfo() {
		return xmlPacketInfo;
	}

	public void setXmlPacketInfo(XmlPacketRtnInfo xmlPacketInfo) {
		this.xmlPacketInfo = xmlPacketInfo;
	}

	public TransactionStatusQueryRspDetailXml getDetail() {
		return detail;
	}

	public void setDetail(TransactionStatusQueryRspDetailXml detail) {
		this.detail = detail;
	}

	public TransactionStatusQueryRspXml() {
		super();
	}
	
}

package com.zufangbao.earth.api.xml;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


@XStreamAlias("SDKPGK")
public class BatchPaymentRtnXml {
	
	@XStreamAlias("INFO")
	private XmlPacketRtnInfo xmlPacketInfo;
	
	
	@XStreamImplicit(itemFieldName = "NTOPRRTNZ")
	private List<BatchPaymentProcessingResultXml> execResultList;


	public XmlPacketRtnInfo getXmlPacketInfo() {
		return xmlPacketInfo;
	}


	public void setXmlPacketInfo(XmlPacketRtnInfo xmlPacketInfo) {
		this.xmlPacketInfo = xmlPacketInfo;
	}


	public List<BatchPaymentProcessingResultXml> getExecResultList() {
		return execResultList;
	}


	public void setExecResultList(
			List<BatchPaymentProcessingResultXml> execResultList) {
		this.execResultList = execResultList;
	}


	public BatchPaymentRtnXml() {
		super();
	}
	
}

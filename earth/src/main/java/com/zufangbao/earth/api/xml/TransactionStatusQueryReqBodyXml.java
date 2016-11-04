package com.zufangbao.earth.api.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("NTWAUEBPY")
public class TransactionStatusQueryReqBodyXml {

	@XStreamAlias("YURREF")
	private String reqNo; //交易唯一参考号

	public String getReqNo() {
		return reqNo;
	}

	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}
	
}

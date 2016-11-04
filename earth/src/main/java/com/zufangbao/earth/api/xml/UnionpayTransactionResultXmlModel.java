package com.zufangbao.earth.api.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.zufangbao.earth.yunxin.unionpay.model.basic.GZUnionPayRtnInfo;

@XStreamAlias("GZELINK")
public class UnionpayTransactionResultXmlModel {

	@XStreamAlias("INFO")
	private GZUnionPayRtnInfo rtnInfo;
	
	@XStreamAlias("BODY")
	private UnionpayTransactionResultBodyModel rtnBody;

	public GZUnionPayRtnInfo getRtnInfo() {
		return rtnInfo;
	}

	public void setRtnInfo(GZUnionPayRtnInfo rtnInfo) {
		this.rtnInfo = rtnInfo;
	}

	public UnionpayTransactionResultBodyModel getRtnBody() {
		return rtnBody;
	}

	public void setRtnBody(UnionpayTransactionResultBodyModel rtnBody) {
		this.rtnBody = rtnBody;
	}

	public UnionpayTransactionResultXmlModel() {
		super();
	}
	
}

package com.zufangbao.earth.api.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.zufangbao.earth.yunxin.unionpay.model.basic.GZUnionPayRtnInfo;


@XStreamAlias("GZELINK")
public class UnionpayRealTimePaymentRspXml {
	
	@XStreamAlias("INFO")
	public GZUnionPayRtnInfo unionpayRtnInfo;
	
	@XStreamAlias("BODY")
	public UnionpayRealTimePaymentRspXmlBody unionpayRtnBody;

	public GZUnionPayRtnInfo getUnionpayRtnInfo() {
		return unionpayRtnInfo;
	}

	public void setUnionpayRtnInfo(GZUnionPayRtnInfo unionpayRtnInfo) {
		this.unionpayRtnInfo = unionpayRtnInfo;
	}

	public UnionpayRealTimePaymentRspXmlBody getUnionpayRtnBody() {
		return unionpayRtnBody;
	}

	public void setUnionpayRtnBody(UnionpayRealTimePaymentRspXmlBody unionpayRtnBody) {
		this.unionpayRtnBody = unionpayRtnBody;
	}

	public UnionpayRealTimePaymentRspXml() {
		super();
	}
	
}

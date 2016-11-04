package com.zufangbao.earth.api.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("BODY")
public class UnionpayRealTimePaymentRspXmlBody {
	
	@XStreamAlias("RET_DETAILS")
	private UnionpayRealTimePaymentRspXmlRetDetails retDetails;

	public UnionpayRealTimePaymentRspXmlRetDetails getRetDetails() {
		return retDetails;
	}

	public void setRetDetails(UnionpayRealTimePaymentRspXmlRetDetails retDetails) {
		this.retDetails = retDetails;
	}

	public UnionpayRealTimePaymentRspXmlBody() {
		super();
	}
}

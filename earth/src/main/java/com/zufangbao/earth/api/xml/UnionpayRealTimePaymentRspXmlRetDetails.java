package com.zufangbao.earth.api.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XStreamAlias("RET_DETAILS")
public class UnionpayRealTimePaymentRspXmlRetDetails {

	@XStreamAlias("RET_DETAIL")
	private UnionpayRealTimePaymentRspXmlRetDetail retDetail;

	public UnionpayRealTimePaymentRspXmlRetDetail getRetDetail() {
		return retDetail;
	}

	public void setRetDetailList(UnionpayRealTimePaymentRspXmlRetDetail retDetail) {
		this.retDetail = retDetail;
	}

	public UnionpayRealTimePaymentRspXmlRetDetails() {
		super();
	}
}

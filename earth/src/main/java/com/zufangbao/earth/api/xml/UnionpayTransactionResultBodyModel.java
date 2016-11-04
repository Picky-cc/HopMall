package com.zufangbao.earth.api.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("BODY")
public class UnionpayTransactionResultBodyModel {

	@XStreamAlias("QUERY_TRANS")
	private UnionpayTransactionResultQueryTransModel queryTrans;
	
	@XStreamAlias("RET_DETAILS")
	private UnionpayTransactionResultQueryRetDetails retDetails;

	public UnionpayTransactionResultQueryTransModel getQueryTrans() {
		return queryTrans;
	}

	public void setQueryTrans(UnionpayTransactionResultQueryTransModel queryTrans) {
		this.queryTrans = queryTrans;
	}

	public UnionpayTransactionResultQueryRetDetails getRetDetails() {
		return retDetails;
	}

	public void setRetDetails(UnionpayTransactionResultQueryRetDetails retDetails) {
		this.retDetails = retDetails;
	}

	public UnionpayTransactionResultBodyModel() {
		super();
	}
	
}

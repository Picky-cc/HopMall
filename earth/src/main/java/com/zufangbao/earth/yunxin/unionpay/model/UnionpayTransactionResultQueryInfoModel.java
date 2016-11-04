package com.zufangbao.earth.yunxin.unionpay.model;

import com.zufangbao.earth.yunxin.unionpay.model.basic.GZUnionPayBasicInfoModel;

public class UnionpayTransactionResultQueryInfoModel extends GZUnionPayBasicInfoModel{
	
	private String queryReqNo; //要查询的交易流水号

	public String getQueryReqNo() {
		return queryReqNo;
	}

	public void setQueryReqNo(String queryReqNo) {
		this.queryReqNo = queryReqNo;
	}

	public UnionpayTransactionResultQueryInfoModel() {
		super();
	}
	
}

package com.zufangbao.earth.api.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("QUERY_TRANS")
public class UnionpayTransactionResultQueryTransModel {
	
	@XStreamAlias("QUERY_SN") //要查询的交易流水
	private String querySn;
	
	@XStreamAlias("QUERY_REMARK") //查询备注
	private String queryRemark;

	public String getQuerySn() {
		return querySn;
	}

	public void setQuerySn(String querySn) {
		this.querySn = querySn;
	}

	public String getQueryRemark() {
		return queryRemark;
	}

	public void setQueryRemark(String queryRemark) {
		this.queryRemark = queryRemark;
	}

	public UnionpayTransactionResultQueryTransModel() {
		super();
	}
	
}

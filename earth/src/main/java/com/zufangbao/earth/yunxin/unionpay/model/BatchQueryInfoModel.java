package com.zufangbao.earth.yunxin.unionpay.model;

import java.util.UUID;

import com.zufangbao.earth.yunxin.unionpay.model.basic.GZUnionPayBasicInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.interfaces.IDeductResultQueryModel;
import com.zufangbao.sun.entity.financial.PaymentChannel;

/**
 * 银联交易批量查询信息模型
 * @author zhanghongbing
 *
 */
public class BatchQueryInfoModel extends GZUnionPayBasicInfoModel {

	private String queryReqNo; //要查询的交易流水号

	public String getQueryReqNo() {
		return queryReqNo;
	}

	public void setQueryReqNo(String queryReqNo) {
		this.queryReqNo = queryReqNo;
	}

	public BatchQueryInfoModel() {
		super();
	}

	public BatchQueryInfoModel(String reqNo, String queryReqNo) {
		super();
		this.setReqNo(reqNo);
		this.queryReqNo = queryReqNo;
	}

	public BatchQueryInfoModel(PaymentChannel channel, String queryReqNo) {
		super(channel);
		this.setReqNo(UUID.randomUUID().toString());
		this.queryReqNo = queryReqNo;
	}
	
	public BatchQueryInfoModel(IDeductResultQueryModel queryModel) {
		super(queryModel.getPaymentChannel());
		this.setReqNo(UUID.randomUUID().toString());
		this.queryReqNo = queryModel.getQueryReqNo();
	}
	
}

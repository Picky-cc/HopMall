package com.zufangbao.earth.yunxin.unionpay.model;

import com.zufangbao.earth.yunxin.unionpay.model.basic.GZUnionPayBasicInfoModel;

public class RealTimePaymentInfoModel  extends GZUnionPayBasicInfoModel{
	
	private String businessCode; //业务代码
	
	private int totalItem; //总记录数
	
	private String totalSum; //总金额
	
	private PaymentDetailInfoModel detailInfo; //付款详情列表

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public int getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}

	public String getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(String totalSum) {
		this.totalSum = totalSum;
	}

	public RealTimePaymentInfoModel() {
		super();
	}

	public PaymentDetailInfoModel getDetailInfo() {
		return detailInfo;
	}

	public void setDetailInfo(PaymentDetailInfoModel detailInfo) {
		this.detailInfo = detailInfo;
	}
	
}

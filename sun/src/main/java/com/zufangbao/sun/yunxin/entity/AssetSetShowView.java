package com.zufangbao.sun.yunxin.entity;

public class AssetSetShowView {

	private AssetSet assetSet;

	private PaymentStatus paymentStatus;

	public AssetSetShowView() {
		super();
	}

	public AssetSetShowView(AssetSet assetSet, PaymentStatus paymentStatus) {
		super();
		this.assetSet = assetSet;
		this.paymentStatus = paymentStatus;
	}

	public AssetSet getAssetSet() {
		return assetSet;
	}

	public void setAssetSet(AssetSet assetSet) {
		this.assetSet = assetSet;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

}

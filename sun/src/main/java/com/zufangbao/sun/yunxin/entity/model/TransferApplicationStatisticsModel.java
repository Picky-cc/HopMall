package com.zufangbao.sun.yunxin.entity.model;

import java.util.Map;

public class TransferApplicationStatisticsModel {

	/**
	 * 支付通道uuid
	 */
	private String paymentChannelUuid;
	
	/**
	 * 通道名称:{网关名称}{000}
	 */
	private String paymentChannelName;
	
	private Map<String, Object> data;

	public String getPaymentChannelUuid() {
		return paymentChannelUuid;
	}

	public void setPaymentChannelUuid(String paymentChannelUuid) {
		this.paymentChannelUuid = paymentChannelUuid;
	}

	public String getPaymentChannelName() {
		return paymentChannelName;
	}

	public void setPaymentChannelName(String paymentChannelName) {
		this.paymentChannelName = paymentChannelName;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public TransferApplicationStatisticsModel(String paymentChannelUuid,
			String paymentChannelName) {
		super();
		this.paymentChannelUuid = paymentChannelUuid;
		this.paymentChannelName = paymentChannelName;
	}

	public TransferApplicationStatisticsModel() {
		super();
	}
}

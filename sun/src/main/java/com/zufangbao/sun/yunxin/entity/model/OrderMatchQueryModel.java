package com.zufangbao.sun.yunxin.entity.model;

import java.util.Date;

import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.OrderType;


public class OrderMatchQueryModel {
	private String orderNo;
	private int orderType=-1;
	private String customerName;
	private String assetRecycleStartDateString;
	private String assetRecycleEndDateString;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getOrderType() {
		return orderType;
	}
	public OrderType getOrderTypeEnum() {
		return OrderType.fromValue(orderType);
	}

	public void setOrderType(int orderType) {
		this.orderType = orderType;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAssetRecycleStartDateString() {
		return assetRecycleStartDateString;
	}
	public Date getAssetRecycleStartDate() {
		return assetRecycleStartDateString==null?null:DateUtils.asDay(assetRecycleStartDateString);
	}

	public void setAssetRecycleStartDateString(String assetRecycleStartDateString) {
		this.assetRecycleStartDateString = assetRecycleStartDateString;
	}

	public String getAssetRecycleEndDateString() {
		return assetRecycleEndDateString;
	}
	public Date getAssetRecycleEndDate() {
		return assetRecycleEndDateString==null?null:DateUtils.asDay(assetRecycleEndDateString);
	}

	public void setAssetRecycleEndDateString(String assetRecycleEndDateString) {
		this.assetRecycleEndDateString = assetRecycleEndDateString;
	}

	public OrderMatchQueryModel(){
		
	}
	
}

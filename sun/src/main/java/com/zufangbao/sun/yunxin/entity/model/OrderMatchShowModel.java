package com.zufangbao.sun.yunxin.entity.model;

import java.math.BigDecimal;

import com.zufangbao.sun.entity.order.Order;

public class OrderMatchShowModel {
	private Order order;
	private BigDecimal paidAmount;
	private BigDecimal issuedAmount;
	
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public BigDecimal getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(BigDecimal paidAmount) {
		this.paidAmount = paidAmount;
	}
	public BigDecimal getIssuedAmount() {
		return issuedAmount;
	}
	public void setIssuedAmount(BigDecimal issuedAmount) {
		this.issuedAmount = issuedAmount;
	}
	public OrderMatchShowModel() {
		super();
	}
	public OrderMatchShowModel(Order order, BigDecimal paidAmount, BigDecimal issuedAmount) {
		super();
		this.order = order;
		this.paidAmount = paidAmount;
		this.issuedAmount = issuedAmount;
	}
	
}

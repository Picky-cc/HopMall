package com.zufangbao.sun.yunxin.entity.model;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.yunxin.entity.OfflineBill;
import com.zufangbao.sun.yunxin.entity.PaymentWay;
import com.zufangbao.sun.yunxin.log.SystemOperateLogVO;

public class OrderViewDetail {
	private Order order;
	private ContractAccount contractAccount;
	private Customer customer;
	private List<String> paymentNoList;
	private List<String> serialNoList;
	private PaymentWay paymentWay;
	private List<SystemOperateLogVO> systemOperateLogVOList;
	private List<OfflineBill> offlineBillList;
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	public ContractAccount getContractAccount() {
		return contractAccount;
	}
	public void setContractAccount(ContractAccount contractAccount) {
		this.contractAccount = contractAccount;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public List<String> getSerialNoList() {
		return serialNoList;
	}
	public void setSerialNoList(List<String> serialNoList) {
		this.serialNoList = serialNoList;
	}
	public String getSerialNoListString(){
		return StringUtils.join(serialNoList,',');
	}
	public PaymentWay getPaymentWay() {
		return paymentWay;
	}
	public void setPaymentWay(PaymentWay paymentWay) {
		this.paymentWay = paymentWay;
	}
	public List<String> getPaymentNoList() {
		return paymentNoList;
	}
	public void setPaymentNoList(List<String> paymentNoList) {
		this.paymentNoList = paymentNoList;
	}
	public boolean isUnionDeduct(){
		return paymentWay==PaymentWay.UNIONDEDUCT;
	}
	public String getPaymentNoListString(){
		return StringUtils.join(paymentNoList,',');
	}
	public OrderViewDetail() {
		super();
	}
	public OrderViewDetail(Order order, ContractAccount contractAccount,
			Customer customer, List<String> paymentNoList, List<String> serialNoList) {
		super();
		this.order = order;
		this.contractAccount = contractAccount;
		this.customer = customer;
		this.paymentNoList = paymentNoList;
		this.serialNoList = serialNoList;
	}
	public List<SystemOperateLogVO> getSystemOperateLogVOList() {
		return systemOperateLogVOList;
	}
	public void setSystemOperateLogVOList(List<SystemOperateLogVO> systemOperateLogVOList) {
		this.systemOperateLogVOList = systemOperateLogVOList;
	}
	public List<OfflineBill> getOfflineBillList() {
		return offlineBillList;
	}
	public void setOfflineBillList(List<OfflineBill> offlineBillList) {
		this.offlineBillList = offlineBillList;
	}
	
	public void setPaymentWay(boolean isEmpty) {
		if(isEmpty){
			this.paymentWay = PaymentWay.UNIONDEDUCT;
		} else {
			this.paymentWay = PaymentWay.OUTLINEDEDUCT;
		}
	}
}

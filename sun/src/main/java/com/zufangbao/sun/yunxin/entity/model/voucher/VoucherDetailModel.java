package com.zufangbao.sun.yunxin.entity.model.voucher;

import java.math.BigDecimal;
import java.util.Date;

import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;

/**
 * 
 * @author louguanyang
 *
 */
public class VoucherDetailModel {
	private String voucherNo;
	private String voucherSource;
	private String voucherType;
	private BigDecimal voucherAmount;
	private String receivableAccountNo;
	private String paymentAccountNo;
	private String paymentName;
	private String paymentBank;
	private Date time;
	private String voucherStatus;
	private String requestNo;
	private CashFlow cashFlow;
	private boolean hasFails;
	private String comment;
	
	private Contract contract;
	private ContractAccount contractAccount;
	private Customer customer;

	public VoucherDetailModel() {
		super();
	}

	public VoucherDetailModel(String voucherNo, String voucherSource, String voucherType, BigDecimal voucherAmount,
			String receivableAccountNo, String paymentAccountNo, String paymentName, String paymentBank, Date time,
			String voucherStatus, String requestNo, CashFlow cashFlow, String comment) {
		super();
		this.voucherNo = voucherNo;
		this.voucherSource = voucherSource;
		this.voucherType = voucherType;
		this.voucherAmount = voucherAmount;
		this.receivableAccountNo = receivableAccountNo;
		this.paymentAccountNo = paymentAccountNo;
		this.paymentName = paymentName;
		this.paymentBank = paymentBank;
		this.time = time;
		this.voucherStatus = voucherStatus;
		this.requestNo = requestNo;
		this.cashFlow = cashFlow;
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getVoucherNo() {
		return voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

	public String getVoucherSource() {
		return voucherSource;
	}

	public void setVoucherSource(String voucherSource) {
		this.voucherSource = voucherSource;
	}

	public String getVoucherType() {
		return voucherType;
	}

	public void setVoucherType(String voucherType) {
		this.voucherType = voucherType;
	}

	public BigDecimal getVoucherAmount() {
		return voucherAmount;
	}

	public void setVoucherAmount(BigDecimal voucherAmount) {
		this.voucherAmount = voucherAmount;
	}

	public String getReceivableAccountNo() {
		return receivableAccountNo;
	}

	public void setReceivableAccountNo(String receivableAccountNo) {
		this.receivableAccountNo = receivableAccountNo;
	}

	public String getPaymentAccountNo() {
		return paymentAccountNo;
	}

	public void setPaymentAccountNo(String paymentAccountNo) {
		this.paymentAccountNo = paymentAccountNo;
	}

	public String getPaymentName() {
		return paymentName;
	}

	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}

	public String getPaymentBank() {
		return paymentBank;
	}

	public void setPaymentBank(String paymentBank) {
		this.paymentBank = paymentBank;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getVoucherStatus() {
		return voucherStatus;
	}

	public void setVoucherStatus(String voucherStatus) {
		this.voucherStatus = voucherStatus;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public CashFlow getCashFlow() {
		return cashFlow;
	}

	public void setCashFlow(CashFlow cashFlow) {
		this.cashFlow = cashFlow;
	}

	public boolean isHasFails() {
		return hasFails;
	}

	public void setHasFails(boolean hasFails) {
		this.hasFails = hasFails;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
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

	public void update(Contract contract, ContractAccount contractAccount) {
		this.contract = contract;
		if(null != contract) {
			this.customer = contract.getCustomer();
		}
		this.contractAccount = contractAccount;
	}

}

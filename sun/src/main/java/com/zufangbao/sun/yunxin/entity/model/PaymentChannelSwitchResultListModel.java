package com.zufangbao.sun.yunxin.entity.model;

import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.FinancialContractConfig;
import com.zufangbao.sun.entity.financial.PaymentStrategyMode;


public class PaymentChannelSwitchResultListModel {
	
	// 信托合同编号
	private String contractNo;

	private String financialContractUuid;
	
	private String paymentChannelUuidsForCredit;
	
	private String paymentChannelUuidsForDebit;
	
	// 付款通道策略 单一通道模式OR发卡行优先模式PaymentStrategyMode
	private String creditPaymentChannelMode;
	
	// 收款通道策略 单一通道模式OR发卡行优先模式 PaymentStrategyMode
	private String debitPaymentChannelMode;
	
	private String paymentChannelRouterForCredit;
	
	private String paymentChannelRouterForDebit;

	// 信托合同名称
	private String contractName;
	
	// 清算绑定行号
	private String bankNameUionAccountNo;

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getFinancialContractUuid() {
		return financialContractUuid;
	}

	public void setFinancialContractUuid(String financialContractUuid) {
		this.financialContractUuid = financialContractUuid;
	}

	public String getPaymentChannelUuidsForCredit() {
		return paymentChannelUuidsForCredit;
	}

	public void setPaymentChannelUuidsForCredit(String paymentChannelUuidsForCredit) {
		this.paymentChannelUuidsForCredit = paymentChannelUuidsForCredit;
	}

	public String getPaymentChannelUuidsForDebit() {
		return paymentChannelUuidsForDebit;
	}

	public void setPaymentChannelUuidsForDebit(String paymentChannelUuidsForDebit) {
		this.paymentChannelUuidsForDebit = paymentChannelUuidsForDebit;
	}

	public String getCreditPaymentChannelMode() {
		return creditPaymentChannelMode;
	}

	public void setCreditPaymentChannelMode(String creditPaymentChannelMode) {
		this.creditPaymentChannelMode = creditPaymentChannelMode;
	}
	
	public void setCreditPaymentChannelMode(PaymentStrategyMode creditPaymentChannelMode){
		this.creditPaymentChannelMode = ( creditPaymentChannelMode == null ? "" : creditPaymentChannelMode.getChineseMessage());
	}

	public String getDebitPaymentChannelMode() {
		return debitPaymentChannelMode;
	}

	public void setDebitPaymentChannelMode(String debitPaymentChannelMode) {
		this.debitPaymentChannelMode = debitPaymentChannelMode;
	}
	
	public void setDebitPaymentChannelMode(PaymentStrategyMode debitPaymentChannelMode){
		this.debitPaymentChannelMode = ( debitPaymentChannelMode == null ? "" : debitPaymentChannelMode.getChineseMessage());
	}

	public String getPaymentChannelRouterForCredit() {
		return paymentChannelRouterForCredit;
	}

	public void setPaymentChannelRouterForCredit(
			String paymentChannelRouterForCredit) {
		this.paymentChannelRouterForCredit = paymentChannelRouterForCredit;
	}

	public String getPaymentChannelRouterForDebit() {
		return paymentChannelRouterForDebit;
	}

	public void setPaymentChannelRouterForDebit(String paymentChannelRouterForDebit) {
		this.paymentChannelRouterForDebit = paymentChannelRouterForDebit;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getBankNameUionAccountNo() {
		return bankNameUionAccountNo;
	}

	public void setBankNameUionAccountNo(String bankNameUionAccountNo) {
		this.bankNameUionAccountNo = bankNameUionAccountNo;
	}

	public PaymentChannelSwitchResultListModel() {
		super();
	}
	
	public PaymentChannelSwitchResultListModel(FinancialContractConfig financialContractConfig, FinancialContract financialContract) {
		super();
		if(financialContract == null){
			return;
		}
		this.contractName = financialContract.getContractName();
		Account account = financialContract.getCapitalAccount();
		this.bankNameUionAccountNo = account.getBankName() + "(" + account.getMarkedAccountNo() + ")";
		this.contractNo = financialContract.getContractNo();
		this.setCreditPaymentChannelMode(financialContractConfig.getCreditPaymentChannelMode());
		this.setDebitPaymentChannelMode(financialContractConfig.getDebitPaymentChannelMode());
		this.financialContractUuid = financialContract.getFinancialContractUuid();
		this.paymentChannelRouterForCredit = financialContractConfig.getPaymentChannelRouterForCredit();
		this.paymentChannelRouterForDebit = financialContractConfig.getPaymentChannelRouterForDebit();
		this.paymentChannelUuidsForCredit = financialContractConfig.getPaymentChannelUuidsForCredit();
		this.paymentChannelUuidsForDebit = financialContractConfig.getPaymentChannelUuidsForDebit();
	}
}

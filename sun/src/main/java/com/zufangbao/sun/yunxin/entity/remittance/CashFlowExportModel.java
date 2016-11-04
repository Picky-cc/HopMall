package com.zufangbao.sun.yunxin.entity.remittance;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.ledgerbook.AccountSide;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.utils.excel.ExcelVoAttribute;

public class CashFlowExportModel {
	//流水UUID
	@ExcelVoAttribute(name = "交易流水号", column = "A")
	private String cashFlowUuid;
	
	@ExcelVoAttribute(name = "通道请求号", column = "B")//????
	private String transactionVoucherNo;
		
	// 交易时间
	@ExcelVoAttribute(name = "时间", column = "G")
	private String transactionTime;
		
	// 发生额（交易金额）
	@ExcelVoAttribute(name = "金额", column = "F")
	private BigDecimal transactionAmount;
	
	// 借贷标志
	@ExcelVoAttribute(name = "收付类型", column = "C")
	private String accountSide;
	
	// 对方名称
	@ExcelVoAttribute(name = "账户名", column = "E")
	private String counterAccountName;
	
	// 对方账号
	@ExcelVoAttribute(name = "账户", column = "D")
	private String counterAccountNo;
	
	// 附言
	@ExcelVoAttribute(name = "交易备注", column = "H")
	private String remark;
	
	public String getCashFlowUuid() {
		return cashFlowUuid;
	}

	public void setCashFlowUuid(String cashFlowUuid) {
		this.cashFlowUuid = cashFlowUuid;
	}

	public String getTransactionVoucherNo() {
		return transactionVoucherNo;
	}

	public void setTransactionVoucherNo(String transactionVoucherNo) {
		this.transactionVoucherNo = transactionVoucherNo;
	}

	public String getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}

	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getAccountSide() {
		return accountSide;
	}

	public void setAccountSide(String accountSide) {
		this.accountSide = accountSide;
	}
	
	public void setAccountSideUse(AccountSide accountSide) {
		this.accountSide = accountSide == null ? null : accountSide.getChineseMessage();
	}

	public String getCounterAccountName() {
		return counterAccountName;
	}

	public void setCounterAccountName(String counterAccountName) {
		this.counterAccountName = counterAccountName;
	}

	public String getCounterAccountNo() {
		return counterAccountNo;
	}

	public void setCounterAccountNo(String counterAccountNo) {
		this.counterAccountNo = counterAccountNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public CashFlowExportModel() {
		super();
	}
	
	public CashFlowExportModel(CashFlow cf) {
		super();
		this.cashFlowUuid = cf.getCashFlowUuid();
		this.counterAccountName = cf.getCounterAccountName();
		this.counterAccountNo = "\t" + cf.getCounterAccountNo();
		this.transactionAmount = cf.getTransactionAmount();
		this.transactionTime = cf.getTransactionTime() == null ? StringUtils.EMPTY : DateUtils.format(cf.getTransactionTime(), DateUtils.LONG_DATE_FORMAT);
		this.transactionVoucherNo = cf.getTradeUuid();
		this.remark = cf.getRemark();
		this.setAccountSideUse(cf.getAccountSide());
	}
	
}
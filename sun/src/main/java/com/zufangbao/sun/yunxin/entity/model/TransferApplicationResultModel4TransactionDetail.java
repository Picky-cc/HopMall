package com.zufangbao.sun.yunxin.entity.model;

import java.math.BigDecimal;
import java.util.Date;

import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.utils.excel.ExcelVoAttribute;

public class TransferApplicationResultModel4TransactionDetail {
	
	@ExcelVoAttribute(name = "交易时间", column = "A")
	private Date creatTime;
	
	@ExcelVoAttribute(name = "交易金额", column = "B")
	private BigDecimal amount;
	
	@ExcelVoAttribute(name = "通道费用", column = "C")
	private BigDecimal fee = BigDecimal.ZERO;

	@ExcelVoAttribute(name = "交易类型", column = "D")
	private String TransactionType = "付款";
	
	@ExcelVoAttribute(name = "系统流水号", column = "E")
	private String transferApplicationNo;

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getTransactionType() {
		return TransactionType;
	}

	public void setTransactionType(String transactionType) {
		TransactionType = transactionType;
	}

	public String getTransferApplicationNo() {
		return transferApplicationNo;
	}

	public void setTransferApplicationNo(String transferApplicationNo) {
		this.transferApplicationNo = transferApplicationNo;
	}

	public TransferApplicationResultModel4TransactionDetail() {
		super();
	}
	
	public TransferApplicationResultModel4TransactionDetail(TransferApplication transferApplication) {
		super();
		this.amount = transferApplication.getAmount();
		this.creatTime = transferApplication.getCreateTime();
//		this.fee = BigDecimal.ZERO;
//		this.TransactionType = "付款";
		this.transferApplicationNo = transferApplication.getTransferApplicationNo();
	}
}

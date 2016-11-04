package com.zufangbao.sun.entity.account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.zufangbao.sun.utils.GeneratorUtils;

@Entity
public class VirtualAccountFlow {
	@Id
	@GeneratedValue
	private Long id;
	private String uuid;
	private String virtualAccountFlowNo;
	private String businessDocumentNo;
	private AccountSide accountSide;
	private String virtualAccountUuid;
	private String virtualAccountNo;
	private String virtualAccountAlias;
	private BigDecimal transactionAmount;
	private BigDecimal balance;
	private VirtualAccountTransactionType transactionType;
	private Date createTime;
	
	public VirtualAccountFlow() {
		super();
	}
	
	
	
	public VirtualAccountFlow(String businessDocumentNo,
			AccountSide accountSide, String virtualAccountUuid, String virtualAccountNo,
			String virtualAccountAlias, BigDecimal transactionAmount,
			BigDecimal balance, VirtualAccountTransactionType transactionType) {
		super();
		this.uuid = UUID.randomUUID().toString();
		this.virtualAccountFlowNo = GeneratorUtils.generateVirtualAccountFlowNo();
		this.businessDocumentNo = businessDocumentNo;
		this.accountSide = accountSide;
		this.virtualAccountUuid = virtualAccountUuid;
		this.virtualAccountNo = virtualAccountNo;
		this.virtualAccountAlias = virtualAccountAlias;
		this.transactionAmount = transactionAmount;
		this.balance = balance;
		this.transactionType = transactionType;
		this.createTime = new Date();
	}



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getVirtualAccountFlowNo() {
		return virtualAccountFlowNo;
	}
	public void setVirtualAccountFlowNo(String virtualAccountFlowNo) {
		this.virtualAccountFlowNo = virtualAccountFlowNo;
	}
	public String getBusinessDocumentNo() {
		return businessDocumentNo;
	}
	public void setBusinessDocumentNo(String businessDocumentNo) {
		this.businessDocumentNo = businessDocumentNo;
	}
	public AccountSide getAccountSide() {
		return accountSide;
	}
	public void setAccountSide(AccountSide accountSide) {
		this.accountSide = accountSide;
	}
	public String getVirtualAccountNo() {
		return virtualAccountNo;
	}
	public void setVirtualAccountNo(String virtualAccountNo) {
		this.virtualAccountNo = virtualAccountNo;
	}
	public String getVirtualAccountAlias() {
		return virtualAccountAlias;
	}
	public void setVirtualAccountAlias(String virtualAccountAlias) {
		this.virtualAccountAlias = virtualAccountAlias;
	}
	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public VirtualAccountTransactionType getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(VirtualAccountTransactionType transactionType) {
		this.transactionType = transactionType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getVirtualAccountUuid() {
		return virtualAccountUuid;
	}
	public void setVirtualAccountUuid(String virtualAccountUuid) {
		this.virtualAccountUuid = virtualAccountUuid;
	}
	
	public String getAccountSideName(){
		return accountSide == null?"":accountSide.getIncomeTypeMsg();
	}
	
	public String getTransactionTypeName(){
		return transactionType==null?"":transactionType.getChineseName();
	}
	
}

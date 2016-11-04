package com.zufangbao.sun.entity.order;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.zufangbao.sun.ledgerbook.AccountSide;
import com.zufangbao.sun.ledgerbook.LedgerLifeCycle;

public class BusinessDocument {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String uuid;
	
	private String documentNo;
	
	private BigDecimal amount;
	
	private AccountSide accountSide;
	
	private String firstAccountName;
	
	private String firstAccountId;
	
	private String secondAccountName;
	
	private String secondAccountId;
	
	private String thirdAccountName;

	private String thirdAccountId;
		
	private String firstPartyId;
	
	private String secondPartyId;
	
	private String thirdPartyId;
	
	private Date createTime;
	
	private Date lastModifiedTime;
	
	private String relatedContractUuid;

	private String relatedFinancialContractUuid;
	
	private String relatedLv1AssetUuid;
	
	private String relatedLv1AssetOuterIdenity;
	
	private String relatedLv2AssetUuid;
	
	private String relatedLv2AssetOuterIdenity;
	
	private String relatedLv3AssetUuid;
	
	private String relatedLv3AssetOuterIdenity;
	
	@Enumerated(EnumType.ORDINAL)
	private LedgerLifeCycle lifeCycle;

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

	public String getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(String documentNo) {
		this.documentNo = documentNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public AccountSide getAccountSide() {
		return accountSide;
	}

	public void setAccountSide(AccountSide accountSide) {
		this.accountSide = accountSide;
	}

	public String getFirstAccountName() {
		return firstAccountName;
	}

	public void setFirstAccountName(String firstAccountName) {
		this.firstAccountName = firstAccountName;
	}

	public String getFirstAccountId() {
		return firstAccountId;
	}

	public void setFirstAccountId(String firstAccountId) {
		this.firstAccountId = firstAccountId;
	}

	public String getSecondAccountName() {
		return secondAccountName;
	}

	public void setSecondAccountName(String secondAccountName) {
		this.secondAccountName = secondAccountName;
	}

	public String getSecondAccountId() {
		return secondAccountId;
	}

	public void setSecondAccountId(String secondAccountId) {
		this.secondAccountId = secondAccountId;
	}

	public String getThirdAccountName() {
		return thirdAccountName;
	}

	public void setThirdAccountName(String thirdAccountName) {
		this.thirdAccountName = thirdAccountName;
	}

	public String getThirdAccountId() {
		return thirdAccountId;
	}

	public void setThirdAccountId(String thirdAccountId) {
		this.thirdAccountId = thirdAccountId;
	}

	public String getFirstPartyId() {
		return firstPartyId;
	}

	public void setFirstPartyId(String firstPartyId) {
		this.firstPartyId = firstPartyId;
	}

	public String getSecondPartyId() {
		return secondPartyId;
	}

	public void setSecondPartyId(String secondPartyId) {
		this.secondPartyId = secondPartyId;
	}

	public String getThirdPartyId() {
		return thirdPartyId;
	}

	public void setThirdPartyId(String thirdPartyId) {
		this.thirdPartyId = thirdPartyId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public String getRelatedContractUuid() {
		return relatedContractUuid;
	}

	public void setRelatedContractUuid(String relatedContractUuid) {
		this.relatedContractUuid = relatedContractUuid;
	}

	public String getRelatedFinancialContractUuid() {
		return relatedFinancialContractUuid;
	}

	public void setRelatedFinancialContractUuid(String relatedFinancialContractUuid) {
		this.relatedFinancialContractUuid = relatedFinancialContractUuid;
	}

	public String getRelatedLv1AssetUuid() {
		return relatedLv1AssetUuid;
	}

	public void setRelatedLv1AssetUuid(String relatedLv1AssetUuid) {
		this.relatedLv1AssetUuid = relatedLv1AssetUuid;
	}

	public String getRelatedLv1AssetOuterIdenity() {
		return relatedLv1AssetOuterIdenity;
	}

	public void setRelatedLv1AssetOuterIdenity(String relatedLv1AssetOuterIdenity) {
		this.relatedLv1AssetOuterIdenity = relatedLv1AssetOuterIdenity;
	}

	public String getRelatedLv2AssetUuid() {
		return relatedLv2AssetUuid;
	}

	public void setRelatedLv2AssetUuid(String relatedLv2AssetUuid) {
		this.relatedLv2AssetUuid = relatedLv2AssetUuid;
	}

	public String getRelatedLv2AssetOuterIdenity() {
		return relatedLv2AssetOuterIdenity;
	}

	public void setRelatedLv2AssetOuterIdenity(String relatedLv2AssetOuterIdenity) {
		this.relatedLv2AssetOuterIdenity = relatedLv2AssetOuterIdenity;
	}

	public String getRelatedLv3AssetUuid() {
		return relatedLv3AssetUuid;
	}

	public void setRelatedLv3AssetUuid(String relatedLv3AssetUuid) {
		this.relatedLv3AssetUuid = relatedLv3AssetUuid;
	}

	public String getRelatedLv3AssetOuterIdenity() {
		return relatedLv3AssetOuterIdenity;
	}

	public void setRelatedLv3AssetOuterIdenity(String relatedLv3AssetOuterIdenity) {
		this.relatedLv3AssetOuterIdenity = relatedLv3AssetOuterIdenity;
	}

	public LedgerLifeCycle getLifeCycle() {
		return lifeCycle;
	}

	public void setLifeCycle(LedgerLifeCycle lifeCycle) {
		this.lifeCycle = lifeCycle;
	}
	
	public BusinessDocument(){
		
	}
	
}

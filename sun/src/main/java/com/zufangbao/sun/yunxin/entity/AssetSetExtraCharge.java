package com.zufangbao.sun.yunxin.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.zufangbao.sun.ledgerbook.ChartOfAccount;
import com.zufangbao.sun.ledgerbook.TAccountInfo;

@Entity
@Table(name = "asset_set_extra_charge")
public class AssetSetExtraCharge {

	@Id
	@GeneratedValue
	private Long id;
	
	private String assetSetExtraChargeUuid;
	
	private String assetSetUuid;
	
	private Date  createTime;
	
	private Date  lastModifyTime;
	
	private String firstAccountName;
	
	private String firstAccountUuid;
	
	private String secondAccountName;
	
	private String secondAccountUuid;
	
	private String thirdAccountName;
	
	private String thirdAccountUuid;
	
	private BigDecimal accountAmount = BigDecimal.ZERO;
	

	public AssetSetExtraCharge(){
		
	}
	
	public AssetSetExtraCharge(String assetSetUuid, BigDecimal amount,
			String EntryNameString) {
		
		this.assetSetExtraChargeUuid = UUID.randomUUID().toString();
		this.assetSetUuid  =assetSetUuid;
		this.createTime = new Date();
		this.lastModifyTime = new Date();
		this.accountAmount  = amount;
		copyTAccount(ChartOfAccount.EntryBook().get(EntryNameString));
	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAssetSetExtraChargeUuid() {
		return assetSetExtraChargeUuid;
	}

	public void setAssetSetExtraChargeUuid(String assetSetExtraChargeUuid) {
		this.assetSetExtraChargeUuid = assetSetExtraChargeUuid;
	}

	public String getAssetSetUuid() {
		return assetSetUuid;
	}

	public void setAssetSetUuid(String assetSetUuid) {
		this.assetSetUuid = assetSetUuid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getFirstAccountName() {
		return firstAccountName;
	}

	public void setFirstAccountName(String firstAccountName) {
		this.firstAccountName = firstAccountName;
	}

	public String getFirstAccountUuid() {
		return firstAccountUuid;
	}

	public void setFirstAccountUuid(String firstAccountUuid) {
		this.firstAccountUuid = firstAccountUuid;
	}

	public String getSecondAccountName() {
		return secondAccountName;
	}

	public void setSecondAccountName(String secondAccountName) {
		this.secondAccountName = secondAccountName;
	}

	public String getSecondAccountUuid() {
		return secondAccountUuid;
	}

	public void setSecondAccountUuid(String secondAccountUuid) {
		this.secondAccountUuid = secondAccountUuid;
	}

	public String getThirdAccountName() {
		return thirdAccountName;
	}

	public void setThirdAccountName(String thirdAccountName) {
		this.thirdAccountName = thirdAccountName;
	}

	public String getThirdAccountUuid() {
		return thirdAccountUuid;
	}

	public void setThirdAccountUuid(String thirdAccountUuid) {
		this.thirdAccountUuid = thirdAccountUuid;
	}

	public BigDecimal getAccountAmount() {
		return accountAmount;
	}

	public void setAccountAmount(BigDecimal accountAmount) {
		this.accountAmount = accountAmount;
	}

	
	public void copyTAccount(TAccountInfo account)
	{
		if(account==null) return;
		if(account.getFirstLevelAccount()==null||StringUtils.isEmpty(account.getFirstLevelAccount().getAccountName())||
				StringUtils.isEmpty(account.getFirstLevelAccount().getAccountCode()))return;
		this.firstAccountName = account.getFirstLevelAccount().getAccountName();
		this.firstAccountUuid = account.getFirstLevelAccount().getAccountCode();

		if(account.getSecondLevelAccount()==null||StringUtils.isEmpty(account.getSecondLevelAccount().getAccountName())||
				StringUtils.isEmpty(account.getSecondLevelAccount().getAccountCode()))return;
		this.secondAccountName = account.getSecondLevelAccount().getAccountName();
		this.secondAccountUuid = account.getSecondLevelAccount().getAccountCode();
		if(account.getThirdLevelAccount()==null||StringUtils.isEmpty(account.getThirdLevelAccount().getAccountName())||
				StringUtils.isEmpty(account.getThirdLevelAccount().getAccountCode()))return;
		this.thirdAccountName = account.getThirdLevelAccount().getAccountName();
		this.thirdAccountUuid = account.getThirdLevelAccount().getAccountCode();
	
	}
	
	public String lastAccountName()
	{
		if(StringUtils.isEmpty(getThirdAccountName())==false) return getThirdAccountName();
		if(StringUtils.isEmpty(getSecondAccountName())==false) return getSecondAccountName();
		if(StringUtils.isEmpty(getFirstAccountName())==false) return getFirstAccountName();
		
		return "";
		
	}
	
}

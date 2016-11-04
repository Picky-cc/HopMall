package com.zufangbao.sun.entity.account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.zufangbao.sun.utils.GeneratorUtils;

@Entity
public class VirtualAccount {
	@Id
	@GeneratedValue
	private Long id;
	private String virtualAccountUuid;
	private Integer customerType;
	//父账户
	private String parentAccountUuid;
	private String virtualAccountAlias;

	private String virtualAccountNo;
	private String version;
	//余额
	private BigDecimal totalBalance;
	
	private String ownerUuid;
	private String ownerName;
	
	private String fstLevelContractUuid;
	private String sndLevelContractUuid;
	private String trdLevelContractUuid;
	
	private Date lastUpdateTime;
	
	private Date createTime;
	private Date lastModifiedTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getVirtualAccountUuid() {
		return virtualAccountUuid;
	}
	public void setVirtualAccountUuid(String virtualAccountUuid) {
		this.virtualAccountUuid = virtualAccountUuid;
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

	public String getParentAccountUuid() {
		return parentAccountUuid;
	}
	public void setParentAccountUuid(String parentAccountUuid) {
		this.parentAccountUuid = parentAccountUuid;
	}
	public String getVirtualAccountAlias() {
		return virtualAccountAlias;
	}
	public void setVirtualAccountAlias(String virtualAccountAlias) {
		this.virtualAccountAlias = virtualAccountAlias;
	}
	public String getVirtualAccountNo() {
		return virtualAccountNo;
	}
	public void setVirtualAccountNo(String virtualAccountNo) {
		this.virtualAccountNo = virtualAccountNo;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public BigDecimal getTotalBalance() {
		return totalBalance;
	}
	public void setTotalBalance(BigDecimal totalBalance) {
		this.totalBalance = totalBalance;
	}
	public String getOwnerUuid() {
		return ownerUuid;
	}
	public void setOwnerUuid(String ownerUuid) {
		this.ownerUuid = ownerUuid;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getFstLevelContractUuid() {
		return fstLevelContractUuid;
	}
	public void setFstLevelContractUuid(String fstLevelContractUuid) {
		this.fstLevelContractUuid = fstLevelContractUuid;
	}
	public String getSndLevelContractUuid() {
		return sndLevelContractUuid;
	}
	public void setSndLevelContractUuid(String sndLevelContractUuid) {
		this.sndLevelContractUuid = sndLevelContractUuid;
	}
	public String getTrdLevelContractUuid() {
		return trdLevelContractUuid;
	}
	public void setTrdLevelContractUuid(String trdLevelContractUuid) {
		this.trdLevelContractUuid = trdLevelContractUuid;
	}
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
	public Integer getCustomerType() {
		return customerType;
	}
	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}
	public VirtualAccount(){
	}
	
	
	public VirtualAccount(String ownerUuid, String ownerName,Integer customerType,
			String fstLevelContractUuid, String sndLevelContractUuid,
			String trdLevelContractUuid) {
		super();
		this.totalBalance = BigDecimal.ZERO;
		this.virtualAccountUuid = UUID.randomUUID().toString();
		this.version = UUID.randomUUID().toString();
		this.virtualAccountNo = GeneratorUtils.generateVirtualAccountNo();
		this.ownerUuid = ownerUuid;
		this.ownerName = ownerName;
		this.customerType = customerType;
		this.fstLevelContractUuid = fstLevelContractUuid;
		this.sndLevelContractUuid = sndLevelContractUuid;
		this.trdLevelContractUuid = trdLevelContractUuid;
		this.createTime= new Date();
		this.lastUpdateTime = new Date();
		this.lastModifiedTime = new Date();
	}
	public void updateBalance(BigDecimal balance){
		if(balance==null){
			balance= BigDecimal.ZERO;
		}
		this.totalBalance = balance;
		this.lastUpdateTime = new Date();
		this.version = UUID.randomUUID().toString();
		this.lastModifiedTime = new Date();
		
	}
}

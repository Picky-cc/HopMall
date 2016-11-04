package com.zufangbao.sun.ledgerbook;

import java.util.Date;

public class AssetCategory {
	
	private Long relatedContractId;
	private String relatedContractUuid;
	
	private String fstLvLAssetUuid;
	private String fstLvLAssetOuterIdentity;
	
	private String sndLvlAssetUuid;
	private String sndLvlAssetOuterIdentity;
	
	private String thrdLvlAssetUuid;
	private String thrdLvlAssetOuterIdentity;
	
	private Date defaultDate;
	private Date amortizedDate;
	public String getRelatedContractUuid() {
		return relatedContractUuid;
	}
	public void setRelatedContractUuid(String relatedContractUuid) {
		this.relatedContractUuid = relatedContractUuid;
	}
	public String getFstLvLAssetUuid() {
		return fstLvLAssetUuid;
	}
	public void setFstLvLAssetUuid(String fstLvLAssetUuid) {
		this.fstLvLAssetUuid = fstLvLAssetUuid;
	}
	public String getFstLvLAssetOuterIdentity() {
		return fstLvLAssetOuterIdentity;
	}
	public void setFstLvLAssetOuterIdentity(String fstLvLAssetOuterIdentity) {
		this.fstLvLAssetOuterIdentity = fstLvLAssetOuterIdentity;
	}
	public String getSndLvlAssetUuid() {
		return sndLvlAssetUuid;
	}
	public void setSndLvlAssetUuid(String sndLvlAssetUuid) {
		this.sndLvlAssetUuid = sndLvlAssetUuid;
	}
	public String getSndLvlAssetOuterIdentity() {
		return sndLvlAssetOuterIdentity;
	}
	public void setSndLvlAssetOuterIdentity(String sndLvlAssetOuterIdentity) {
		this.sndLvlAssetOuterIdentity = sndLvlAssetOuterIdentity;
	}
	public String getThrdLvlAssetUuid() {
		return thrdLvlAssetUuid;
	}
	public void setThrdLvlAssetUuid(String thrdLvlAssetUuid) {
		this.thrdLvlAssetUuid = thrdLvlAssetUuid;
	}
	public String getThrdLvlAssetOuterIdentity() {
		return thrdLvlAssetOuterIdentity;
	}
	public void setThrdLvlAssetOuterIdentity(String thrdLvlAssetOuterIdentity) {
		this.thrdLvlAssetOuterIdentity = thrdLvlAssetOuterIdentity;
	}
	public Date getDefaultDate() {
		return defaultDate;
	}
	public void setDefaultDate(Date defaultDate) {
		this.defaultDate = defaultDate;
	}
	public Date getAmortizedDate() {
		return amortizedDate;
	}
	public void setAmortizedDate(Date amortizedDate) {
		this.amortizedDate = amortizedDate;
	}
	public Long getRelatedContractId() {
		return relatedContractId;
	}
	public void setRelatedContractId(Long relatedContractId) {
		this.relatedContractId = relatedContractId;
	}
	
	
	
	

}

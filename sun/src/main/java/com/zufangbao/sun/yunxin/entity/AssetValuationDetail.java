package com.zufangbao.sun.yunxin.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.zufangbao.sun.utils.DateUtils;

/**
 * 资产评估明细表
 * 
 * @author louguanyang
 *
 */

@Entity
@Table(name = "asset_valuation_detail")
public class AssetValuationDetail {
	/**
	 * 资产评估明细ID
	 */
	@Id
	@GeneratedValue
	private long id;
	private String assetValuationDetailUuid;
	/**
	 * 资产ID
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private AssetSet assetSet;
	/**
	 * 创建时间
	 */
	@Type(type = "date")
	private Date createdDate;
	/**
	 * 明细科目: 0:还款额,1:罚息,2:金额调整
	 */
	@Enumerated(EnumType.ORDINAL)
	private AssetValuationSubject subject;
	
	/**
	 * 金额
	 */
	private BigDecimal amount;
	/**
	 * 资产价值日
	 */
	@Type(type = "date")
	private Date assetValueDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAssetValuationDetailUuid() {
		return assetValuationDetailUuid;
	}

	public void setAssetValuationDetailUuid(String assetValuationDetailUuid) {
		this.assetValuationDetailUuid = assetValuationDetailUuid;
	}

	public AssetSet getAssetSet() {
		return assetSet;
	}

	public void setAssetSet(AssetSet assetSet) {
		this.assetSet = assetSet;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public AssetValuationSubject getSubject() {
		return subject;
	}

	public void setSubject(AssetValuationSubject subject) {
		this.subject = subject;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getAssetValueDate() {
		return assetValueDate;
	}

	public void setAssetValueDate(Date assetValueDate) {
		this.assetValueDate = assetValueDate;
	}

	public AssetValuationDetail() {
		super();
	}

	public AssetValuationDetail(AssetSet assetSet, Date createdDate, AssetValuationSubject subject,
			BigDecimal amount, Date assetValueDate) {
		super();
		this.assetValuationDetailUuid=UUID.randomUUID().toString();
		this.assetSet = assetSet;
		this.createdDate = createdDate;
		this.subject = subject;
		this.amount = amount;
		this.assetValueDate = assetValueDate;
	}
	
	public AssetValuationDetail(AssetSet assetSet, AssetValuationSubject subject, BigDecimal amount, Date date) {
		this(assetSet, DateUtils.asDay(date), subject, amount, DateUtils.asDay(date));
	}

}

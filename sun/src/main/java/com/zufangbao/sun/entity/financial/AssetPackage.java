package com.zufangbao.sun.entity.financial;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zufangbao.sun.entity.contract.Contract;

@Entity
@Table(name = "asset_package")
public class AssetPackage {

	@Id
	@GeneratedValue
	private Long id;

	/**
	 * 信托合同
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private FinancialContract financialContract;

	/**
	 * 贷款合同
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Contract contract;

	/** 资产包编号 */
	private String assetPackageNo;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 是否有效
	 */
	@Column(name = "is_available")
	private Boolean available;

	/**
	 * 资产包批次Id
	 */
	private Long loanBatchId;
	
	public AssetPackage() {
		super();
	}
	
	public AssetPackage(FinancialContract financialContract, Contract contract, LoanBatch loanBatch) {
		super();
		this.financialContract = financialContract;
		this.contract = contract;
		this.loanBatchId = loanBatch.getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FinancialContract getFinancialContract() {
		return financialContract;
	}

	public void setFinancialContract(FinancialContract financialContract) {
		this.financialContract = financialContract;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public String getAssetPackageNo() {
		return assetPackageNo;
	}

	public void setAssetPackageNo(String assetPackageNo) {
		this.assetPackageNo = assetPackageNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Long getLoanBatchId() {
		return loanBatchId;
	}

	public void setLoanBatchId(Long loanBatchId) {
		this.loanBatchId = loanBatchId;
	}

}

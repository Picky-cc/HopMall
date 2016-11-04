package com.zufangbao.sun.entity.financial;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zufangbao.sun.utils.GeneratorUtils;

@Entity
@Table(name = "loan_batch")
public class LoanBatch {

	@Id
	@GeneratedValue
	private Long id;

	/**
	 * 信托合同id
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private FinancialContract financialContract;

	private String loanBatchUuid;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/** 批次编号 */
	private String code;

	/**
	 * 是否激活
	 */
	@Column(name = "is_available")
	private boolean available;

	/**
	 * 
	 */
	private Date loanDate;
	/**
	 * 最后修改时间
	 */
	private Date lastModifiedTime;

	public LoanBatch() {
		super();
	}

	public LoanBatch(FinancialContract financialContract, String code, boolean activeStatus) {
		super();
		this.loanBatchUuid = UUID.randomUUID().toString();
		this.financialContract = financialContract;
		this.code = code;
		this.createTime = new Date();
		this.available = activeStatus;
		this.loanDate = new Date();
		this.lastModifiedTime = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public FinancialContract getFinancialContract() {
		return financialContract;
	}

	public void setFinancialContract(FinancialContract financialContract) {
		this.financialContract = financialContract;
	}

    public String getUuid(){
    	return this.loanBatchUuid;
    }

	public String getLoanBatchUuid() {
		return loanBatchUuid;
	}

	public void setLoanBatchUuid(String loanBatchUuid) {
		this.loanBatchUuid = loanBatchUuid;
	} 
	
	public static String  castLoanBatchNo(FinancialContract financialContract){
		return  new String(financialContract.getContractNo()+" "+GeneratorUtils.generateLoanBatchNo());
	}

}

package com.zufangbao.sun.yunxin.entity.model.reportform;

import java.math.BigDecimal;

import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.utils.excel.ExcelVoAttribute;

/**
 * 贷款规模管理报表Model
 * 
 * @author louguanyang
 *
 */
public class LoansShowModel {
	@ExcelVoAttribute(name = "信托合同编号", column = "A")
	private String contractNo;
	@ExcelVoAttribute(name = "信托合同名称", column = "B")
	private String contractName;
	@ExcelVoAttribute(name = "期初", column = "C")
	private BigDecimal beginningLoans;
	@ExcelVoAttribute(name = "本期新增", column = "D")
	private BigDecimal newLoans;
	@ExcelVoAttribute(name = "本期减少", column = "E")
	private BigDecimal reduceLoans;
	@ExcelVoAttribute(name = "期末", column = "F")
	private BigDecimal endingLoans;

	
	public LoansShowModel() {
		super();
	}

	public LoansShowModel(FinancialContract financialContract, BigDecimal beginningLoans, BigDecimal newLoans, BigDecimal reduceLoans,
			BigDecimal endingLoans) {
		super();
		this.contractNo = financialContract.getContractNo();
		this.contractName = financialContract.getContractName();
		this.beginningLoans = beginningLoans;
		this.newLoans = newLoans;
		this.reduceLoans = reduceLoans;
		this.endingLoans = endingLoans;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public BigDecimal getBeginningLoans() {
		return beginningLoans;
	}

	public void setBeginningLoans(BigDecimal beginningLoans) {
		this.beginningLoans = beginningLoans;
	}

	public BigDecimal getNewLoans() {
		return newLoans;
	}

	public void setNewLoans(BigDecimal newLoans) {
		this.newLoans = newLoans;
	}

	public BigDecimal getReduceLoans() {
		return reduceLoans;
	}

	public void setReduceLoans(BigDecimal reduceLoans) {
		this.reduceLoans = reduceLoans;
	}

	public BigDecimal getEndingLoans() {
		return endingLoans;
	}

	public void setEndingLoans(BigDecimal endingLoans) {
		this.endingLoans = endingLoans;
	}

}

package com.zufangbao.sun.yunxin.entity.model.reportform;

import java.math.BigDecimal;

import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.utils.excel.ExcelVoAttribute;

/**
 * 应收利息管理 报表展示Model
 * 
 * @author louguanyang
 *
 */
public class InterestShowModel {
	@ExcelVoAttribute(name = "信托合同编号", column = "A")
	private String contractNo;
	@ExcelVoAttribute(name = "信托合同名称", column = "B")
	private String contractName;
	@ExcelVoAttribute(name = "期初", column = "C")
	private BigDecimal beginningInterest;
	@ExcelVoAttribute(name = "本期新增", column = "D")
	private BigDecimal newInterest;
	@ExcelVoAttribute(name = "本期减少", column = "E")
	private BigDecimal reduceInterest;
	@ExcelVoAttribute(name = "期末", column = "F")
	private BigDecimal endingInterest;

	public InterestShowModel() {
		super();
	}

	public InterestShowModel(FinancialContract financialContract, BigDecimal beginningInterest, BigDecimal newInterest, BigDecimal reduceInterest,
			BigDecimal endingInterest) {
		super();
		this.contractNo = financialContract.getContractNo();
		this.contractName = financialContract.getContractName();
		this.beginningInterest = beginningInterest;
		this.newInterest = newInterest;
		this.reduceInterest = reduceInterest;
		this.endingInterest = endingInterest;
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

	public BigDecimal getBeginningInterest() {
		return beginningInterest;
	}

	public void setBeginningInterest(BigDecimal beginningInterest) {
		this.beginningInterest = beginningInterest;
	}

	public BigDecimal getNewInterest() {
		return newInterest;
	}

	public void setNewInterest(BigDecimal newInterest) {
		this.newInterest = newInterest;
	}

	public BigDecimal getReduceInterest() {
		return reduceInterest;
	}

	public void setReduceInterest(BigDecimal reduceInterest) {
		this.reduceInterest = reduceInterest;
	}

	public BigDecimal getEndingInterest() {
		return endingInterest;
	}

	public void setEndingInterest(BigDecimal endingInterest) {
		this.endingInterest = endingInterest;
	}

}

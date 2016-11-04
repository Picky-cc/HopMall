package com.zufangbao.sun.yunxin.entity.model.financialcontract;

/**
 * 信托合同查询Model
 * 
 * @author louguanyang
 *
 */
public class FinancialContractQueryModel {
	private String financialContractNo;
	private String financialContractName;
	private Long appId = -1L;
	private int financialContractType = -1;
	private String financialAccountNo;

	public String getFinancialContractNo() {
		return financialContractNo;
	}

	public void setFinancialContractNo(String financialContractNo) {
		this.financialContractNo = financialContractNo;
	}

	public String getFinancialContractName() {
		return financialContractName;
	}

	public void setFinancialContractName(String financialContractName) {
		this.financialContractName = financialContractName;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public int getFinancialContractType() {
		return financialContractType;
	}

	public void setFinancialContractType(int financialContractType) {
		this.financialContractType = financialContractType;
	}

	public String getFinancialAccountNo() {
		return financialAccountNo;
	}

	public void setFinancialAccountNo(String financialAccountNo) {
		this.financialAccountNo = financialAccountNo;
	}

}

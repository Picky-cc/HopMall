package com.zufangbao.earth.yunxin.entity.deduct.model;

import java.util.List;
import java.util.stream.Collectors;

import com.zufangbao.sun.api.model.deduct.DeductApplicationRepaymentDetail;
import com.zufangbao.sun.entity.contract.Contract;

public class LoanInformation {
	 
	private String loanContractNo;
	
	private Long loanContractId;
	
	private String loanCustoemrNo;
	
	private String assetSetNo="";
	
	private String repaymentPlanNos;

	public LoanInformation(Contract contract,
			List<DeductApplicationRepaymentDetail> deductApplicationRepaymentDetails) {

		this.loanContractNo = contract.getContractNo();
		this.loanContractId = contract.getId();
		this.loanCustoemrNo = contract.getCustomer().getName();
        this.repaymentPlanNos = deductApplicationRepaymentDetails.stream().map(fc -> fc.getRepaymentPlanCode()).collect(Collectors.joining(" "));
	}

	

	public String getLoanContractNo() {
		return loanContractNo;
	}



	public void setLoanContractNo(String loanContractNo) {
		this.loanContractNo = loanContractNo;
	}



	public String getLoanCustoemrNo() {
		return loanCustoemrNo;
	}

	public void setLoanCustoemrNo(String loanCustoemrNo) {
		this.loanCustoemrNo = loanCustoemrNo;
	}

	public String getAssetSetNo() {
		return assetSetNo;
	}

	public void setAssetSetNo(String assetSetNo) {
		this.assetSetNo = assetSetNo;
	}

	public String getRepaymentPlanNos() {
		return repaymentPlanNos;
	}

	public void setRepaymentPlanNos(String repaymentPlanNos) {
		this.repaymentPlanNos = repaymentPlanNos;
	}

	public Long getLoanContractId() {
		return loanContractId;
	}

	public void setLoanContractId(Long loanContractId) {
		this.loanContractId = loanContractId;
	}



	
	
}

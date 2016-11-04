package com.zufangbao.earth.yunxin.api.model.modify;

import java.math.BigDecimal;
import java.util.List;

import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.exception.ApiResponseCode;
import com.zufangbao.sun.yunxin.entity.model.api.ContractDetail;

public class ImportAssetPackageContent {

	
	private  int thisBatchContractsTotalNumber;
	
	private  String financialProductCode;
	
	private  String thisBatchContractsTotalAmount;
	
	private  List<ContractDetail> contractDetails;


	public int getThisBatchContractsTotalNumber() {
		return thisBatchContractsTotalNumber;
	}

	public void setThisBatchContractsTotalNumber(int thisBatchContractsTotalNumber) {
		this.thisBatchContractsTotalNumber = thisBatchContractsTotalNumber;
	}
	
	public String getThisBatchContractsTotalAmount() {
		return thisBatchContractsTotalAmount;
	}

	public void setThisBatchContractsTotalAmount(
			String thisBatchContractsTotalAmount) {
		this.thisBatchContractsTotalAmount = thisBatchContractsTotalAmount;
	}

	public List<ContractDetail> getContractDetails() {
		return contractDetails;
	}

	public void setContractDetails(
			List<ContractDetail> contractDetails) {
		this.contractDetails = contractDetails;
	}
	
	
	
	public void validateTotalAmountAndNumber(){
		if( this.thisBatchContractsTotalNumber != this.contractDetails.size()){
			throw new  ApiException(ApiResponseCode.TOTAL_CONTRACTS_NUMBER_NOT_MATCH);
		}
		
		BigDecimal sum = BigDecimal.ZERO;
		for(ContractDetail contractDetail:contractDetails){
		    sum = new BigDecimal(contractDetail.getLoanTotalAmount()).add(sum);
		}
		
		if(sum.compareTo(new BigDecimal(thisBatchContractsTotalAmount)) != 0)
		{
			throw new ApiException(ApiResponseCode.TOTAL_CONTRACTS_AMOUNT_NOT_MATCH);
		}
		
	}

	public String getFinancialProductCode() {
		return financialProductCode;
	}

	public void setFinancialProductCode(String financialProductCode) {
		this.financialProductCode = financialProductCode;
	}
	
	
}

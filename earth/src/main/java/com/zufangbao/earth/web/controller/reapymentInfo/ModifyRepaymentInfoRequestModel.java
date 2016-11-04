package com.zufangbao.earth.web.controller.reapymentInfo;
/** 
* @author 作者 zhenghangbo
* @version 创建时间：Oct 2, 2016 3:53:25 PM 
* 类说明 
*/
public class ModifyRepaymentInfoRequestModel {
	
	private String bankCode;
	
	private String provinceCode;
	
	private String cityCode;
	
	private String bankAccount;
	
	private Long contractId;

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}


	public String getBankAccount() {
		return bankAccount.trim();
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount.trim();
	}
	
	public boolean checkData(){
		if(bankCode ==null || provinceCode ==null || cityCode ==null || bankAccount == null ||  contractId ==null){
			return false;
		}
		return true;
	}

	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	
	
	
}

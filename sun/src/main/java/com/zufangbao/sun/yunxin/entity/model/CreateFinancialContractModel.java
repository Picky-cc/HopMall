package com.zufangbao.sun.yunxin.entity.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.utils.StringUtils;
import com.zufangbao.sun.utils.Validator;

public class CreateFinancialContractModel implements Validator {
	private String financialContractNo;
	private String financialContractName;
	private Long companyId;
	private Long appId;
	private int financialContractType;
	private Integer loanOverdueStartDay;
	private Integer loanOverdueEndDay;
	private Integer advaRepoTerm;
	private Integer advaMatuterm;
	private String advaStartDate;
	private String thruDate;
	private String trustsAccountName;
	private String trustsBankName;
	private String trustsAccountNo;
	private Long paymentChannel;
	private int assetPackageFormat;

	

	public CreateFinancialContractModel(String trustsContractNo,
			String trustsContractName, Long companyId, Long appId,
			int trustsContractType, int loanOverdueStartDay,
			int loanOverdueEndDay, int advaRepoTerm,int advaMatuterm, String advaStartDate,
			String thruDate, String trustsAccountName, String trustsBankName,
			String trustsAccountNo, Long paymentChannel,int assetPackageFormat) {
		this.financialContractNo = trustsContractNo;
		this.financialContractName = trustsContractName;
		this.companyId = companyId;
		this.appId = appId;
		this.financialContractType = trustsContractType;
		this.loanOverdueStartDay = loanOverdueStartDay;
		this.loanOverdueEndDay = loanOverdueEndDay;
		this.advaRepoTerm = advaRepoTerm;
		this.advaMatuterm = advaMatuterm;
		this.advaStartDate = advaStartDate;
		this.thruDate = thruDate;
		this.trustsAccountName = trustsAccountName;
		this.trustsBankName = trustsBankName;
		this.trustsAccountNo = trustsAccountNo;
		this.paymentChannel = paymentChannel;
		this.assetPackageFormat = assetPackageFormat;
	}

	public CreateFinancialContractModel() {

	}

	public Integer getLoanOverdueEndDay() {
		return loanOverdueEndDay == null ? 0 : this.loanOverdueEndDay;
	}

	public void setLoanOverdueEndDay(Integer loanOverdueEndDay) {
		this.loanOverdueEndDay = loanOverdueEndDay;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long app) {
		this.appId = app;
	}

	public String getFinancialContractNo() {
		return financialContractNo;
	}

	public void setFinancialContractNo(String trustsContractNo) {
		this.financialContractNo = trustsContractNo;
	}

	public String getFinancialContractName() {
		return financialContractName;
	}

	public void setFinancialContractName(String trustsContractName) {
		this.financialContractName = trustsContractName;
	}

	public int getFinancialContractType() {
		return financialContractType;
	}

	public void setFinancialContractType(int trustsContractType) {
		this.financialContractType = trustsContractType;
	}

	public Integer getLoanOverdueStartDay() {
		return loanOverdueStartDay == null ? 0 : this.loanOverdueStartDay;
	}

	public void setLoanOverdueStartDay(Integer loanOverdueStartDay) {
		this.loanOverdueStartDay = loanOverdueStartDay;
	}

	public Integer getAdvaRepoTerm() {
		return advaRepoTerm == null ? 0 : this.advaRepoTerm;
	}

	public void setAdvaRepoTerm(Integer advaRepoTerm) {
		this.advaRepoTerm = advaRepoTerm;
	}

	public Integer getAdvaMatuterm() {
		return advaMatuterm == null ? 0 : this.advaMatuterm;
	}

	public void setAdvaMatuterm(Integer advaMatuterm) {
		this.advaMatuterm = advaMatuterm;
	}


	public Date getThruDate() {
		return thruDate == null ? null : DateUtils.parseDate(thruDate,
				"yyyy-MM-dd");
	}

	public void setThruDate(String thruDate) {
		this.thruDate = thruDate;
	}

	public String getTrustsAccountName() {
		return trustsAccountName;
	}

	public void setTrustsAccountName(String trustsAccountName) {
		this.trustsAccountName = trustsAccountName;
	}

	public String getTrustsBankName() {
		return trustsBankName;
	}

	public void setTrustsBankName(String trustsBankName) {
		this.trustsBankName = trustsBankName;
	}

	public String getTrustsAccountNo() {
		return trustsAccountNo;
	}

	public void setTrustsAccountNo(String trustsAccountNo) {
		this.trustsAccountNo = trustsAccountNo;
	}

	public Long getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(Long paymentChannelType) {
		this.paymentChannel = paymentChannelType;
	}

	public Date getAdvaStartDate() {
		return advaStartDate == null ? null : DateUtils.parseDate(
				advaStartDate, "yyyy-MM-dd");
	}

	public String getAdvaStartDateString() {
		return this.advaStartDate;
	}

	public void setAdvaStartDate(String advaStartDate) {
		this.advaStartDate = advaStartDate;
	}

	@Override
	public boolean valid() {
		return CollectionUtils.isEmpty(errorMap());
	}

	@Override
	public Map<String, Object> errorMap() {
		Map<String, Object> errorMap = new HashMap<String, Object>();
		return errorMap;

	}

	@Override
	public boolean newObject() {
		return false;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public int getAssetPackageFormat() {
		return assetPackageFormat;
	}

	public void setAssetPackageFormat(int assetPackageFormat) {
		this.assetPackageFormat = assetPackageFormat;
	}

	public void check() throws Exception {
		if(StringUtils.isEmpty(getFinancialContractNo()) || StringUtils.isEmpty(getFinancialContractName())
				|| StringUtils.isEmpty(getAdvaStartDateString()) || StringUtils.isEmpty(getTrustsAccountName()) 
				|| StringUtils.isEmpty(getTrustsAccountNo()) || StringUtils.isEmpty(getTrustsBankName())
				|| getLoanOverdueStartDay() == 0 || getAdvaRepoTerm() == 0){
			throw new Exception();
		}
		
	}

}

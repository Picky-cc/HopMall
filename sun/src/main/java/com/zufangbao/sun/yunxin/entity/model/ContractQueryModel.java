package com.zufangbao.sun.yunxin.entity.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.utils.EnumUtil;
import com.zufangbao.sun.yunxin.ContractState;


public class ContractQueryModel {
	private String financialContractIds;
	private String underlyingAsset;
	private String contractNo;
	private String startDateString;
	private String endDateString;
	private String customerName;
	
	private Map<String, FinancialContract> financialContractsMap;
	/**
	 * 合同状态
	 */
	private String contractStateOrdinals = "[\"0\",\"1\",\"2\",\"3\"]";
	public String getUnderlyingAsset() {
		return underlyingAsset;
	}
	public void setUnderlyingAsset(String underlyingAsset) {
		this.underlyingAsset = underlyingAsset;
	}
	public String getFinancialContractIds() {
		return financialContractIds;
	}
	public void setFinancialContractIds(String financialContractIds) {
		this.financialContractIds = financialContractIds;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getStartDateString() {
		return startDateString;
	}
	public Date getStartDate() {
		return startDateString==null?null:DateUtils.asDay(startDateString);
	}
	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
	}
	public String getEndDateString() {
		return endDateString;
	}
	public Date getEndDate() {
		return endDateString==null?null:DateUtils.asDay(endDateString);
	}
	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getContractStateOrdinals() {
		return contractStateOrdinals;
	}
	public void setContractStateOrdinals(String contractStateOrdinals) {
		this.contractStateOrdinals = contractStateOrdinals;
	}
	public List<ContractState> getContractStateList() {
		List<Integer> ordinals = JsonUtils.parseArray(this.contractStateOrdinals, Integer.class);
		if (ordinals == null) {
			ordinals = new ArrayList<Integer>();
		}
		List<ContractState> contractStateEnumList = new ArrayList<ContractState>();
		for(Integer ordinal : ordinals) {
			if (ordinal == null) continue;
			ContractState contractState = EnumUtil.fromOrdinal(ContractState.class, ordinal);
			if (contractState == null) continue;
			contractStateEnumList.add(contractState);
		}
		return contractStateEnumList;
	}
	
	public ContractQueryModel(){
		
	}

	public List<Long> getFinancialContractIdList(){
		List<Long> financialContractList = JsonUtils.parseArray(financialContractIds,Long.class);
		if(financialContractList==null){
			return Collections.emptyList();
		}
		return financialContractList;
	}
	
	public Map<String, FinancialContract> getFinancialContractsMap() {
		return financialContractsMap;
	}
	public void setFinancialContractsMap(
			Map<String, FinancialContract> financialContractsMap) {
		this.financialContractsMap = financialContractsMap;
	}
	public Set<String> getFinancialContractUuids() {
		return financialContractsMap == null ? Collections.emptySet() : financialContractsMap.keySet();
	}
	
	public ContractQueryModel(String financialContractIds,
			String underlyingAsset, String contractNo, String startDateString,
			String endDateString, String customerName,
			String contractStateOrdinals) {
		super();
		this.financialContractIds = financialContractIds;
		this.underlyingAsset = underlyingAsset;
		this.contractNo = contractNo;
		this.startDateString = startDateString;
		this.endDateString = endDateString;
		this.customerName = customerName;
		this.contractStateOrdinals = contractStateOrdinals;
	}
	
	public ContractQueryModel(String financialContractIds, String underlyingAsset,
			String contractNo, String startDateString, String endDateString,
			String customerName) {
		super();
		this.financialContractIds = financialContractIds;
		this.underlyingAsset = underlyingAsset;
		this.contractNo = contractNo;
		this.startDateString = startDateString;
		this.endDateString = endDateString;
		this.customerName = customerName;
	}
	
}

package com.zufangbao.sun.yunxin.entity.model.assetset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.yunxin.entity.AssetSetActiveStatus;
import com.zufangbao.sun.yunxin.entity.AuditOverdueStatus;
import com.zufangbao.sun.yunxin.entity.OverDueStatus;
import com.zufangbao.sun.yunxin.entity.PaymentStatus;

public class AssetSetQueryModel {
	private String paymentStatusOrdinals;
	private String financialContractIds;
	private String singleLoanContractNo;
	private String contractNo;
	private String startDate;
	private String endDate;
	private String customerName;
	private int overDueStatus=-1;
	private String auditOverDueStatusOrdinals;
	private int activeStatus=-1;

	private Map<String, FinancialContract> financialContractsMap;
	
	public String getPaymentStatusOrdinals() {
		return paymentStatusOrdinals;
	}
	public void setPaymentStatusOrdinals(String paymentStatusOrdinals) {
		this.paymentStatusOrdinals = paymentStatusOrdinals;
	}
	public List<PaymentStatus> getPaymentStatusEnumList(){
		List<Integer> ordinals = getPaymentStatusOrdinalList();
		List<PaymentStatus> paymentStatusEnumList = new ArrayList<PaymentStatus>();
		for (Integer ordinal : ordinals) {
			if(ordinal==null) continue;
			PaymentStatus paymentStatus = PaymentStatus.fromValue(ordinal);
			if(paymentStatus==null) continue;
			paymentStatusEnumList.add(paymentStatus);
		}
		return paymentStatusEnumList;
	}
	public List<Integer> getPaymentStatusOrdinalList(){
		List<Integer> ordinals = JsonUtils.parseArray(paymentStatusOrdinals,Integer.class);
		return ordinals;
	}
	public String getSingleLoanContractNo() {
		return singleLoanContractNo;
	}
	public void setSingleLoanContractNo(String singleLoanContractNo) {
		this.singleLoanContractNo = singleLoanContractNo;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getStartDate() {
		return startDate;
	}
	public Date getStartDateValue() {
		return startDate==null?null:DateUtils.asDay(startDate);
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public Date getEndDateValue() {
		return endDate==null?null:DateUtils.asDay(endDate);
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public int getOverDueStatus() {
		return overDueStatus;
	}
	public OverDueStatus getOverDueStatusEnum() {
		return OverDueStatus.fromValue(overDueStatus);
	}
	public void setOverDueStatus(int overDueStatus) {
		this.overDueStatus = overDueStatus;
	}
	public String getAuditOverDueStatusOrdinals() {
		return auditOverDueStatusOrdinals;
	}
	public void setAuditOverDueStatusOrdinals(String auditOverDueStatusOrdinals) {
		this.auditOverDueStatusOrdinals = auditOverDueStatusOrdinals;
	}
	public List<AuditOverdueStatus> getAuditOverDueStatusEnumList(){
		List<Integer> ordinals = transfer_json_integer_array_to_list(getAuditOverDueStatusOrdinals());
		List<AuditOverdueStatus> auditOverDueStatusEnumList = new ArrayList<AuditOverdueStatus>();
		for (Integer ordinal : ordinals) {
			if(ordinal==null) continue;
			AuditOverdueStatus auditOverdueStatus = AuditOverdueStatus.fromValue(ordinal);
			if(auditOverdueStatus==null) continue;
			auditOverDueStatusEnumList.add(auditOverdueStatus);
		}
		return auditOverDueStatusEnumList;
	}
	private List<Integer> transfer_json_integer_array_to_list(String json_integer_array){
		List<Integer> ordinals = JsonUtils.parseArray(json_integer_array,Integer.class);
		if(ordinals==null) return Collections.emptyList();
		return ordinals;
	}
	
	public AssetSetActiveStatus getActiveStatusEnum() {
		return AssetSetActiveStatus.fromValue(activeStatus);
	}
	public int getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(int activeStatus) {
		this.activeStatus = activeStatus;
	}
	public String getFinancialContractIds() {
		return financialContractIds;
	}
	public void setFinancialContractIds(String financialContractIds) {
		this.financialContractIds = financialContractIds;
	}
	// is there common methods for financial-contract-selectors in multiply pages?
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
	
	public FinancialContract getFinancialContractByUuid(String financialContractUuid) {
		return financialContractsMap == null ? null : financialContractsMap.get(financialContractUuid);
	}
	
	public boolean is_illegal_query_condition() {
		return CollectionUtils.isEmpty(getFinancialContractUuids())
		   || CollectionUtils.isEmpty(getAuditOverDueStatusEnumList()) || CollectionUtils.isEmpty(getPaymentStatusEnumList());
	}
	
	public AssetSetQueryModel(){
		
	}
	
}

package com.zufangbao.sun.yunxin.entity.remittance;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.entity.financial.PaymentInstitutionName;
import com.zufangbao.sun.utils.EnumUtil;

public class RemittancePlanExecLogQueryModel {
	
	/**
	 * 信托合同Uuids
	 */
	private String financialContractUuids;
	
	/**
	 * 代付单号
	 */
	private String execLogUuid;
	/**
	 * 放款编号
	 */
	private String planUuid;
	/**
	 * 付款方账户名
	 */
	private String payerAccountHolder;
	/**
	 * 收款方账户名
	 */
	private String cpBankAccountHolder;
	/**
	 * 放款通道
	 */
	private int remittanceChannel;
	/**
	 * 代付状态
	 */
	private int executionStatus=-1;
	/**
	 * 冲账状态
	 */
	private String reverseStatusOrdinals;
	
	/**
	 * 状态变更时间起始
	 */
	private String statusModifyTimeStart;
	
	/**
	 * 状态变更时间结束
	 */
	private String statusModifyTimeEnd;
	
	/**
	 * 排序方式
	 */
	private boolean sortOption=false;
	/**
	 * 排序字段
	 */
	private String field;
	
	public String getFinancialContractUuids() {
		return financialContractUuids;
	}

	public void setFinancialContractUuids(String financialContractUuids) {
		this.financialContractUuids = financialContractUuids;
	}
	
	public List<String> getFinancialContractUuidList(){
		List<String> financialContractList = JsonUtils.parseArray(this.financialContractUuids,String.class);
		if(financialContractList==null){
			return Collections.emptyList();
		}
		return financialContractList;
	}
	
	public String getExecLogUuid() {
		return execLogUuid;
	}
	
	public void setExecLogUuid(String execLogUuid) {
		this.execLogUuid = execLogUuid;
	}
	
	public String getPlanUuid() {
		return planUuid;
	}
	
	public void setPlanUuid(String planUuid) {
		this.planUuid = planUuid;
	}
	
	public String getPayerAccountHolder() {
		return payerAccountHolder;
	}
	
	public void setPayerAccountHolder(String payerAccountHolder) {
		this.payerAccountHolder = payerAccountHolder;
	}
	
	public String getCpBankAccountHolder() {
		return cpBankAccountHolder;
	}
	
	public void setCpBankAccountHolder(String cpBankAccountHolder) {
		this.cpBankAccountHolder = cpBankAccountHolder;
	}
	
	public int getRemittanceChannel() {
		return remittanceChannel;
	}
	
	public PaymentInstitutionName getRemittanceChannelEnum() {
		return EnumUtil.fromOrdinal(PaymentInstitutionName.class, this.remittanceChannel);
	}
	
	public void setRemittanceChannel(int remittanceChannel) {
		this.remittanceChannel = remittanceChannel;
	}
	
	public int getExecutionStatus() {
		return executionStatus;
	}
	
	public ExecutionStatus getExecutionStatusEnum() {
		return EnumUtil.fromOrdinal(ExecutionStatus.class, this.executionStatus);
	}
	
	public void setExecutionStatus(int executionStatus) {
		this.executionStatus = executionStatus;
	}
	
	public String getReverseStatusOrdinals() {
		return reverseStatusOrdinals;
	}

	public List<ReverseStatus> getReverseStatusList(){
		return EnumUtil.fromOrdinals(ReverseStatus.class, this.reverseStatusOrdinals);
	}
	
	public void setReverseStatusOrdinals(String reverseStatusOrdinals) {
		this.reverseStatusOrdinals = reverseStatusOrdinals;
	}
	
	public String getStatusModifyTimeStart() {
		return statusModifyTimeStart;
	}
	
	public Date getStatusModifyTimeStartValue() {
		return this.statusModifyTimeStart == null ? null : DateUtils.parseDate(this.statusModifyTimeStart, "yyyy-MM-dd HH:mm:ss");
	}

	public void setStatusModifyTimeStart(String statusModifyTimeStart) {
		this.statusModifyTimeStart = statusModifyTimeStart;
	}

	public String getStatusModifyTimeEnd() {
		return statusModifyTimeEnd;
	}
	
	public Date getStatusModifyTimeEndValue() {
		return this.statusModifyTimeEnd == null ? null : DateUtils.parseDate(this.statusModifyTimeEnd, "yyyy-MM-dd HH:mm:ss");
	}

	public void setStatusModifyTimeEnd(String statusModifyTimeEnd) {
		this.statusModifyTimeEnd = statusModifyTimeEnd;
	}

	public boolean getSortOption() {
		return sortOption;
	}
	
	public void setSortOption(boolean sortOption) {
		this.sortOption = sortOption;
	}
	
	public String getField() {
		return field;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	
	public String getOrderBySentence() {
		 String template = " ORDER BY %s %s, id ASC";
		 String dbField = "createTime";
		 String sortType = this.sortOption ? "ASC" : "DESC";
		 return String.format(template, dbField, sortType);
	}

}

package com.zufangbao.sun.yunxin.entity.remittance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.entity.financial.PaymentInstitutionName;
import com.zufangbao.sun.utils.EnumUtil;

public class RemittanceRefundBillQueryModel {
	
	/**
	 * 信托合同Uuids
	 */
	private String financialContractUuids;
	
	/**
	 * 通道流水号
	 */
	private String channelCashFlowNo;
	
	/**
	 * 放款通道
	 */
	private String paymentInstitutionOrdinals;
	
	/**
	 * 起始创建日期
	 */
	private String startDate;
	
	/**
	 * 终止创建日期
	 */
	private String endDate;
	
	/**
	 * 退回账户
	 */
	private String returnedAccountNo;
	
	private boolean isAsc=false;

	public String getFinancialContractUuids() {
		return financialContractUuids;
	}

	public void setFinancialContractUuids(String financialContractUuids) {
		this.financialContractUuids = financialContractUuids;
	}
	
	public List<String> getFinancialContractUuidList(){
		List<String> financialContractList = JsonUtils.parseArray(this.financialContractUuids,String.class);
		if(financialContractList==null){
			return new ArrayList<String>();
		}
		return financialContractList;
	}

	public String getChannelCashFlowNo() {
		return channelCashFlowNo;
	}

	public void setChannelCashFlowNo(String channelCashFlowNo) {
		this.channelCashFlowNo = channelCashFlowNo;
	}

	public String getPaymentInstitutionOrdinals() {
		return paymentInstitutionOrdinals;
	}

	public List<PaymentInstitutionName> getPaymentInstitutionNameList() {
		List<Integer> ordinals = JsonUtils.parseArray(this.paymentInstitutionOrdinals, Integer.class);
		if (ordinals == null) {
			ordinals = new ArrayList<Integer>();
		}
		List<PaymentInstitutionName> executionStatusEnumList = new ArrayList<PaymentInstitutionName>();
		for(Integer ordinal : ordinals) {
			if (ordinal == null) continue;
			PaymentInstitutionName executionStatus = EnumUtil.fromOrdinal(PaymentInstitutionName.class, ordinal);
			if (executionStatus == null) continue;
			executionStatusEnumList.add(executionStatus);
		}
		return executionStatusEnumList;
	}
	
	public void setPaymentInstitutionOrdinals(String paymentInstitutionOrdinals) {
		this.paymentInstitutionOrdinals = paymentInstitutionOrdinals;
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

	public String getReturnedAccountNo() {
		return returnedAccountNo;
	}

	public void setReturnedAccountNo(String returnedAccountNo) {
		this.returnedAccountNo = returnedAccountNo;
	}
	
	public boolean getIsAsc() {
		return isAsc;
	}

	public void setIsAsc(boolean isAsc) {
		this.isAsc = isAsc;
	}

	public RemittanceRefundBillQueryModel() {
		super();
	}
	
	public String getOrderBySentence() {
		 String template = " ORDER BY %s %s, id ASC";
		 String dbField = "createTime";
		 String sortType = this.isAsc ? "ASC" : "DESC";
		 return String.format(template, dbField, sortType);
	}

}

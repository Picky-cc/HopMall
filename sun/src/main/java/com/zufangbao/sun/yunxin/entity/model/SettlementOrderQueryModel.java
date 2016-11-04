package com.zufangbao.sun.yunxin.entity.model;

import java.util.Collections;
import java.util.List;

import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.yunxin.SettlementStatus;

public class SettlementOrderQueryModel {

	private static final int DEFAULT = -1;
	// TODO move it out of queryModel
	private String settlementOrderUuids;
	
	/** query params start */
	private String financialContractIds;
	
	private int settlementStatus = DEFAULT;
	
	private String settlementOrderNo;
	
	private String repaymentNo;
	
	private String appNo;
	/** query params end */
	
	
	public String getSettlementOrderUuids() {
		return settlementOrderUuids;
	}
	public String getFinancialContractIds() {
		return financialContractIds;
	}
	public List<Long> getFinancialContractIdList(){
		List<Long> financialContractList = JsonUtils.parseArray(financialContractIds,Long.class);
		if(financialContractList==null){
			return Collections.emptyList();
		}
		return financialContractList;
	}

	public void setFinancialContractIds(String financialContractIds) {
		this.financialContractIds = financialContractIds;
	}

	public void setSettlementOrderUuids(String settlementOrderUuids) {
		this.settlementOrderUuids = settlementOrderUuids;
	}

	public String getSettlementOrderNo() {
		return settlementOrderNo;
	}

	public void setSettlementOrderNo(String settlementOrderNo) {
		this.settlementOrderNo = settlementOrderNo;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public int getSettlementStatus() {
		return settlementStatus;
	}

	public void setSettlementStatus(int settlementStatus) {
		this.settlementStatus = settlementStatus;
	}

	public SettlementStatus getSettlementStatusEnum() {
		return SettlementStatus.formValue(settlementStatus);
	}

	public String getRepaymentNo() {
		return repaymentNo;
	}

	public void setRepaymentNo(String repaymentNo) {
		this.repaymentNo = repaymentNo;
	}

	public SettlementOrderQueryModel() {
	}

	public SettlementOrderQueryModel(String financialContractIds, int settlementStatus,
			String settlementOrderNo, String repaymentNo, String appNo) {
		super();
		this.financialContractIds = financialContractIds;
		this.settlementStatus = settlementStatus;
		this.settlementOrderNo = settlementOrderNo;
		this.repaymentNo = repaymentNo;
		this.appNo = appNo;
	}

	public List<String> convertSettlementOrderUuidList() {
		List<String> settlementOrderUuidList = JsonUtils.parseArray(getSettlementOrderUuids(),String.class);
		if(settlementOrderUuidList == null){
			settlementOrderUuidList = Collections.emptyList();
		}
		return settlementOrderUuidList;
	}
}
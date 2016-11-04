package com.zufangbao.wellsfargo.silverpool.cashauditing.entity;

import java.util.ArrayList;
import java.util.List;

import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucher;

public class VoucherOperationInfo {
	private Long operatorId;
	private String ip;
	private String cashFlowUuid;
	
	//操作的对象
	private List<JournalVoucher> changedJournalVoucherList= new ArrayList<JournalVoucher>();

	public List<JournalVoucher> getChangedJournalVoucherList() {
		return changedJournalVoucherList;
	}
	public void setChangedJournalVoucherList(
			List<JournalVoucher> changedJournalVoucherList) {
		this.changedJournalVoucherList = changedJournalVoucherList;
	}
	public Long getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}

	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getCashFlowUuid() {
		return cashFlowUuid;
	}
	public void setCashFlowUuid(String cashFlowUuid) {
		this.cashFlowUuid = cashFlowUuid;
	}
	public VoucherOperationInfo(){
		
	}
	public VoucherOperationInfo(Long operatorId, String ip) {
		super();
		this.operatorId = operatorId;
		this.ip = ip;
	}
	public void addJournalVoucher(JournalVoucher journalVoucher){
		changedJournalVoucherList.add(journalVoucher);
	}
	
	public void addJournalVoucherList(List<JournalVoucher> journalVoucherList){
		changedJournalVoucherList.addAll(journalVoucherList);
	}

}

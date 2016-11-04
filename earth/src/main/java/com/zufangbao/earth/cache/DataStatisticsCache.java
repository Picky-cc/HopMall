package com.zufangbao.earth.cache;

import java.io.Serializable;
import java.util.List;

import com.zufangbao.sun.yunxin.entity.model.vo.welcom.RemittanceDataStatistic;
import com.zufangbao.sun.yunxin.entity.model.vo.welcom.RepaymentDataStatistic;

public class DataStatisticsCache implements Serializable{
	
	private static final long serialVersionUID = 1644576171861062873L;
	private long timeStamp;
	private List<Long> finanialContractIds;
	private RepaymentDataStatistic repaymentData;
	private RemittanceDataStatistic remittanceDataStatistic;
	
	public RepaymentDataStatistic getRepaymentData() {
		return repaymentData;
	}
	public void setRepaymentData(RepaymentDataStatistic repaymentData) {
		this.repaymentData = repaymentData;
	}
	
	public RemittanceDataStatistic getRemittanceDataStatistic() {
		return remittanceDataStatistic;
	}
	public void setRemittanceDataStatistic(
			RemittanceDataStatistic remittanceDataStatistic) {
		this.remittanceDataStatistic = remittanceDataStatistic;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public List<Long> getFinanialContractIds() {
		return finanialContractIds;
	}
	public void setFinanialContractIds(List<Long> finanialContractIds) {
		this.finanialContractIds = finanialContractIds;
	}
	
	public boolean isExpired(long timeout) {
		return (System.currentTimeMillis()-timeStamp)>timeout;
	}
	
	public DataStatisticsCache(){
		
	}

	public DataStatisticsCache(long timeStamp, List<Long> finanialContractIds,
			RepaymentDataStatistic repaymentData,
			RemittanceDataStatistic remittanceDataStatistic) {
		super();
		this.timeStamp = timeStamp;
		this.finanialContractIds = finanialContractIds;
		this.repaymentData = repaymentData;
		this.remittanceDataStatistic = remittanceDataStatistic;
	}
	
}

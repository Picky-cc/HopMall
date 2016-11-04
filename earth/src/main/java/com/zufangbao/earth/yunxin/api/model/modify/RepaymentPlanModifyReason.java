package com.zufangbao.earth.yunxin.api.model.modify;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

public enum RepaymentPlanModifyReason {
	/**
	 * 项目结清
	 */
	REASON_1("enum.repayment-plan-modify-reason.reason-1"),
	/**
	 * 提前部分还款
	 */
	REASON_2("enum.repayment-plan-modify-reason.reason-2"),
	/**
	 * 错误更正
	 */
	REASON_3("enum.repayment-plan-modify-reason.reason-3");
	
	private String key;

	private RepaymentPlanModifyReason(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	
	public int getOrdinal(){
		return this.ordinal();
	}
	
	public String getChineseMessage() {
		return ApplicationMessageUtils.getChineseMessage(this.getKey());
	}
	
	public static RepaymentPlanModifyReason fromKey(String key) {
		for (RepaymentPlanModifyReason reason : RepaymentPlanModifyReason.values()) {
			if(reason.getChineseMessage().equals(key)) {
				return reason;
			}
		}
		return null;
	}
	
}

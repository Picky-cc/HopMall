package com.zufangbao.earth.yunxin.api.model.modify;

/**
 * 变更还款计划类别
 * @author louguanyang
 *
 */
public enum RepaymentPlanModifyType {
	/** 普通变更  **/
	NORMAL("enum.repayment-plan-modify-type.normal"),
	/** 异常变更  **/
	UNUSUAL("enum.repayment-plan-modify-type.unusual");
	private String key;

	private RepaymentPlanModifyType(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	
	public static RepaymentPlanModifyType fromValue(int value) {
		for (RepaymentPlanModifyType item : RepaymentPlanModifyType.values()) {
			if(item.ordinal() == value) {
				return item;
			}
		}
		return null;
	}
}

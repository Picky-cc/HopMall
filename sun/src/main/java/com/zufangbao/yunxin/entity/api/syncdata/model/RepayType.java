package com.zufangbao.yunxin.entity.api.syncdata.model;

public enum RepayType {
	
	BLANK(""),
	NORMAL("enum.repay-type.normal"),
	OVERDUE("enum.repay-type.overdue"),
	GUARANTEE("enum.repay-type.guarantee"),
	GUARANTEE_AND_REPAYMENT("enum.repay-type.guaranteeAndRepayment"),
	REPO("enum.repay-type.repo"),
	PREREPAYMENT_All("enum.repay-type.preRepaymentAll"),
	PREREPAYMENT_PART("enum.repay-type.preRepaymentPart");
	
	private String key;
	
	private RepayType(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public int getOrdinal() {
		return this.ordinal();
	}

	public String getAlias() {
		return this.key.substring(this.key.lastIndexOf(".") + 1);
	}

	

}

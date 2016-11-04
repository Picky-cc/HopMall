package com.zufangbao.sun.entity.order;

import com.zufangbao.sun.entity.account.AuditStatus;

/**
 * 
 * @author zjm
 *
 */
public enum RepaymentAuditStatus {

	/** 待对账 */
	CREATE("enum.repayment-audit-status.create"),

	/** 部分对账 */
	ISSUING("enum.repayment-audit-status.issuing"),

	/** 对账成功 */
	ISSUED("enum.repayment-audit-status.issued"),

	/** 作废 */
	INVALID("enum.repayment-audit-status.invalid");

	private String key;

	private RepaymentAuditStatus(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public int getOrdinal() {
		return ordinal();
	}

	public String getAlias() {
		return this.key.substring(this.key.lastIndexOf(".") + 1);
	}

	public static AuditStatus fromOrdinal(int ordinal) {

		for (AuditStatus item : AuditStatus.values()) {

			if (ordinal == item.getOrdinal()) {

				return item;
			}
		}
		return null;
	}
	public static RepaymentAuditStatus formValue(int ordinal) {
		
		for (RepaymentAuditStatus item : RepaymentAuditStatus.values()) {
			
			if (ordinal == item.getOrdinal()) {
				
				return item;
			}
		}
		return null;
	}
	

	public String getRelatedStatus() {
		if (this.key.equals(new String("enum.repayment-audit-status.create"))) {
			return "未关联";
		}
		if (this.key.equals(new String("enum.repayment-audit-status.issuing"))) {
			return "部分关联";
		}
		if (this.key.equals(new String("enum.repayment-audit-status.issued"))) {
			return "关联成功";
		}
		if (this.key.equals(new String("enum.repayment-audit-status.invalid"))) {
			return "作废";
		} else {
			return "";
		}
	}
}

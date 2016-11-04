package com.zufangbao.sun.entity.order;

import java.math.BigDecimal;


public interface AuditBill extends CommonBill {

	/**
	 * 审计单UUID
	 */
	String getAuditBillId() ;
	void setAuditBillId(String groupId);
	
	/**
	 * 审计状态
	 */
	RepaymentAuditStatus getRepaymentAuditStatus();
	void setRepaymentAuditStatus(RepaymentAuditStatus repaymentAuditStatus);

	public boolean canAudit();
	
	void setPaidRent(BigDecimal paidRent);
	
	void updateStatus(BigDecimal issuedAmount);
}

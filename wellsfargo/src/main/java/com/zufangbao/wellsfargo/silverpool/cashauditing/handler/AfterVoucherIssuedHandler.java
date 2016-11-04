package com.zufangbao.wellsfargo.silverpool.cashauditing.handler;

import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.CashAuditVoucherSet;

public interface AfterVoucherIssuedHandler {
	public void handlerAfterVoucherIssued(CashAuditVoucherSet cashAuditVoucherSet);
}

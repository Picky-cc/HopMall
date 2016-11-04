package com.zufangbao.wellsfargo.silverpool.cashauditing.handler;

import java.math.BigDecimal;

import com.zufangbao.sun.entity.account.DepositeAccountInfo;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.ledgerbook.AssetCategory;
import com.zufangbao.sun.ledgerbook.LedgerBook;
import com.zufangbao.sun.yunxin.entity.AssetSet;

public interface LedgerBookVirtualAccountHandler {

	public void deposit_independent_account_assets(LedgerBook book,DepositeAccountInfo bankAccount, DepositeAccountInfo customerAccount, AssetCategory assetCategory,BigDecimal amount,String jvUuid, String bvUuid, String sdUuid);
	public void inner_transfer_independent_account_assets(LedgerBook book, Order order, BigDecimal amount,String jvUuid, String bvUuid, String sdUuid);
	public void remittance_from_virutal_account_to_receivable_overdue_assets(LedgerBook book, AssetSet AssetSet, BigDecimal amount,String jvUuid, String bvUuid, String sdUuid, boolean ifRecoverMoney);
	public BigDecimal get_balance_of_customer(String ledgerBookNo, String customerUuid);
}

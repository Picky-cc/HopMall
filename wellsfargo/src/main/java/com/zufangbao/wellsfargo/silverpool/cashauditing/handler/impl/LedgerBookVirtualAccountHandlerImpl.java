package com.zufangbao.wellsfargo.silverpool.cashauditing.handler.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.sun.entity.account.DepositeAccountInfo;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.ledgerbook.AccountDispersor;
import com.zufangbao.sun.ledgerbook.AccountSide;
import com.zufangbao.sun.ledgerbook.AssetCategory;
import com.zufangbao.sun.ledgerbook.ChartOfAccount;
import com.zufangbao.sun.ledgerbook.DepositeAccountHandler;
import com.zufangbao.sun.ledgerbook.LedgerBook;
import com.zufangbao.sun.ledgerbook.LedgerBookHandler;
import com.zufangbao.sun.ledgerbook.LedgerItem;
import com.zufangbao.sun.ledgerbook.LedgerItemService;
import com.zufangbao.sun.yunxin.entity.AssetRecoverType;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.LedgerBookVirtualAccountHandler;
import com.zufangbao.wellsfargo.yunxin.handler.JournalVoucherHandler;

@Component("ledgerBookVirtualAccountHandler")
public class LedgerBookVirtualAccountHandlerImpl implements LedgerBookVirtualAccountHandler{
	@Autowired
	private LedgerBookHandler ledgerBookHandler;
	
	@Autowired
	private LedgerItemService ledgerItemService;
	
	@Autowired
	private JournalVoucherHandler journalVoucherHandler;
	
	@Autowired
	private DepositeAccountHandler depositeAccountHandler;
	
	@Override
	public void deposit_independent_account_assets(LedgerBook book,	DepositeAccountInfo bankAccount, DepositeAccountInfo customerAccount,
			AssetCategory assetCategory, BigDecimal amount, String jvUuid, String bvUuid, String sdUuid) {
		AccountDispersor dispersor=new AccountDispersor();
		dispersor.dispers(bankAccount.getIndependentAccountName(), amount, AccountSide.DEBIT, bankAccount.getDeopsite_account_owner_party(), jvUuid, bvUuid, sdUuid);
		dispersor.dispers(customerAccount.getDeposite_account_name(), amount, AccountSide.CREDIT, customerAccount.getDeopsite_account_owner_party(), jvUuid, bvUuid, sdUuid);
		Map<String,LedgerItem> bookedLegerMaps = ledgerItemService.book_M_Debit_M_Credit_ledgers_V2(book, assetCategory,dispersor);
		for(LedgerItem item:bookedLegerMaps.values()) {
			ledgerItemService.save(item);
		}
		
	}
	
	@Override
	public void inner_transfer_independent_account_assets(LedgerBook book, Order order, BigDecimal amount, String jvUuid, String bvUuid, String sdUuid) {
		/*try {
			AssetSet asset=order.getAssetSet();
			if(asset==null) return;
			DepositeAccountInfo counterPartyAccount= depositeAccountHandler.extractCustomerVirtualAccount(asset);
			journalVoucherHandler.recover_loan_asset_or_guarantee(asset, AssetRecoverType.LOAN_ASSET, amount, jvUuid, bvUuid, sdUuid, counterPartyAccount);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	@Override
	public void remittance_from_virutal_account_to_receivable_overdue_assets(LedgerBook book, AssetSet AssetSet, BigDecimal amount,String jvUuid, String bvUuid, String sdUuid, boolean ifRecoverMoney){
		try {
			DepositeAccountInfo counterPartyAccount= depositeAccountHandler.extractVirtualRemittanceAccount(AssetSet);
			journalVoucherHandler.recover_loan_asset_or_guarantee(AssetSet, AssetRecoverType.LOAN_ASSET, amount, jvUuid, bvUuid, sdUuid, counterPartyAccount, ifRecoverMoney);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public BigDecimal get_balance_of_customer(String ledgerBookNo, String customerUuid) {
		BigDecimal amount = ledgerItemService.getBalancedAmount(ledgerBookNo, ChartOfAccount.FST_LIABILITIES_OF_INDEPENDENT_ACCOUNTS, customerUuid, "", null, null);
		if(amount==null){
			return BigDecimal.ZERO;
		}
		return amount.negate();
	}
}

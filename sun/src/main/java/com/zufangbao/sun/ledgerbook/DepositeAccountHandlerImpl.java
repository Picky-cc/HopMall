package com.zufangbao.sun.ledgerbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.sun.entity.account.DepositeAccountInfo;
import com.zufangbao.sun.entity.account.DepositeAccountType;
import com.zufangbao.sun.service.FinancialContractConfigService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
@Component
public class DepositeAccountHandlerImpl implements DepositeAccountHandler {

	
	@Autowired
	private FinancialContractConfigService financialContractConfigService;
	@Autowired
	private LedgerItemService ledgerItemService;
	
	@Override
	public DepositeAccountInfo extractVirtualRemittanceAccount(AssetSet loan_asset) {
		LedgerTradeParty appBalancePayLedgerTradeParty = ledgerItemService.getAppPayBankSavingLedgerTradeParty(loan_asset);
		DepositeAccountInfo counterPartyVirtualAccount=
				new DepositeAccountInfo(ChartOfAccount.SND_LIABILITIES_INDEPENDENT_INTER_ACCOUNT_REMITTANCE,appBalancePayLedgerTradeParty,DepositeAccountType.VIRTUAL_ACCOUNT);
		return counterPartyVirtualAccount;
	}
	@Override
	public DepositeAccountInfo extractCustomerVirtualAccount(AssetSet loan_asset) {
		LedgerTradeParty customerBalancePayLedgerTradeParty = ledgerItemService.getCustomerPayBankSavingLedgerTradeParty(loan_asset);
		DepositeAccountInfo customerVirtualAccount=
				new DepositeAccountInfo(ChartOfAccount.FST_LIABILITIES_OF_INDEPENDENT_ACCOUNTS,customerBalancePayLedgerTradeParty,DepositeAccountType.VIRTUAL_ACCOUNT);
		return customerVirtualAccount;
	}
	@Override
	public DepositeAccountInfo extractDepositReceivedAccount(AssetSet loan_asset) {
		LedgerTradeParty tradeParty = ledgerItemService.getOnlineDeductBanksavingLedgerTradeParty(loan_asset);
		DepositeAccountInfo customerVirtualAccount=
				new DepositeAccountInfo(ChartOfAccount.FST_ACCOUNT_RECEIVED_IN_ADVANCE,tradeParty,DepositeAccountType.UINON_PAY);
		return customerVirtualAccount;
	}
	

}

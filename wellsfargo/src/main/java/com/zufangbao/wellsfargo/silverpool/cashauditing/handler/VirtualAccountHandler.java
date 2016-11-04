package com.zufangbao.wellsfargo.silverpool.cashauditing.handler;

import java.util.List;

import com.zufangbao.sun.entity.account.VirtualAccount;
import com.zufangbao.sun.yunxin.entity.model.VirtualAccountShowModel;
import com.zufangbao.wellsfargo.silverpool.cashauditing.webform.CustomerDepositResult;

public interface VirtualAccountHandler {
	public VirtualAccount refreshVirtualAccountBalance(String ledgerBookNo, String customerUuid, String financialContractUuid);
	public List<VirtualAccount> get_account_with_balance();
	
	public List<VirtualAccountShowModel> getVirtualAccountList(List<VirtualAccount> virtualAccounts);
	
	public CustomerDepositResult buildDeposit(String virtualAccountUuid);
}

package com.zufangbao.sun.ledgerbook;

import com.zufangbao.sun.entity.account.DepositeAccountInfo;
import com.zufangbao.sun.yunxin.entity.AssetSet;

public interface DepositeAccountHandler {
	
	public DepositeAccountInfo extractVirtualRemittanceAccount(AssetSet set);

	public DepositeAccountInfo extractCustomerVirtualAccount(AssetSet loan_asset);
	
	public DepositeAccountInfo extractDepositReceivedAccount(AssetSet loan_asset);
	
	
	

}

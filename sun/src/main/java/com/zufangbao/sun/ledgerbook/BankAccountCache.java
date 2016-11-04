package com.zufangbao.sun.ledgerbook;

import java.util.HashMap;
import java.util.List;

import com.zufangbao.sun.entity.account.DepositeAccountInfo;
import com.zufangbao.sun.entity.financial.FinancialContract;

public interface BankAccountCache {

	public abstract HashMap<String, DepositeAccountInfo> getBankAccountCache(
			FinancialContract contract);

	public HashMap<String, DepositeAccountInfo> getBankAccountCache(
			String financialContractId);

	List<String> getFinancialContractUuidBy(String hostAccountNo);

	
	public DepositeAccountInfo extractFirstBankAccountFrom(FinancialContract financialContract);
	public DepositeAccountInfo extractUnionPayAccountFrom(FinancialContract financialContract, String ... unionPayKey);
	
}
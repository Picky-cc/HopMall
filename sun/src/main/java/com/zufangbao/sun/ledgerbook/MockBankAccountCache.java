package com.zufangbao.sun.ledgerbook;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.sun.entity.account.DepositeAccountInfo;
import com.zufangbao.sun.entity.account.DepositeAccountType;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.service.FinancialContractService;

@Component("mockBankAccountCache")
public class MockBankAccountCache implements BankAccountCache
{

	@Autowired
	private FinancialContractService financialContractService;
	

	@Override
	public HashMap<String, DepositeAccountInfo> getBankAccountCache(
			FinancialContract contract) {
		// TODO Auto-generated method stub
		return financialContractService.extractDepositeAccount(contract);

	}

	@Override
	public HashMap<String, DepositeAccountInfo> getBankAccountCache(
			String financialContractUuid) {
		FinancialContract contract=financialContractService.getFinancialContractBy(financialContractUuid);
		return getBankAccountCache(contract);
		}

	@Override
	public List<String> getFinancialContractUuidBy(String hostAccountNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DepositeAccountInfo extractFirstBankAccountFrom(
			FinancialContract financialContract) {
		HashMap<String, DepositeAccountInfo> accountMap =  financialContractService.extractDepositeAccount(financialContract);
		return extractFirstBankAccount(accountMap, DepositeAccountType.BANK);
	}
	
	public DepositeAccountInfo extractFirstBankAccount(
			HashMap<String, DepositeAccountInfo> accountSet, DepositeAccountType depositAccountType) {
		
		for(DepositeAccountInfo info:accountSet.values())
		{
			if(info!=null&&info.getAccount_type()!=null&&info.getAccount_type().equals(depositAccountType))
				return info;
		}
		return null;
	}

	@Override
	public DepositeAccountInfo extractUnionPayAccountFrom(
			FinancialContract financialContract, String ... unionPayKey) {
		HashMap<String, DepositeAccountInfo> accountMap =  financialContractService.extractDepositeAccount(financialContract);
		return extractFirstBankAccount(accountMap, DepositeAccountType.UINON_PAY);
	}

	
}
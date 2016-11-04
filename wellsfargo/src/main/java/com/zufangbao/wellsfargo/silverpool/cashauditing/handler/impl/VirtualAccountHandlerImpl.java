package com.zufangbao.wellsfargo.silverpool.cashauditing.handler.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.sun.entity.account.VirtualAccount;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.ledgerbook.LedgerItemService;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.VirtualAccountService;
import com.zufangbao.sun.yunxin.entity.model.VirtualAccountShowModel;
import com.zufangbao.sun.yunxin.exception.VirtualAccountNotExsitException;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.LedgerBookVirtualAccountHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.VirtualAccountHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.webform.CustomerDepositResult;

@Component("virtualAccountHandler")
public class VirtualAccountHandlerImpl implements VirtualAccountHandler {

	@Autowired
	private VirtualAccountService virtualAccountService;
	
	@Autowired
	private LedgerBookVirtualAccountHandler ledgerBookVirtualAccountHandler;
	
	@Autowired
	private LedgerItemService ledgerItemService;
	
	@Autowired
	private FinancialContractService financialContractService;
	
	@Autowired
	private ContractService contractService;
	
	@Override
	public VirtualAccount refreshVirtualAccountBalance(String ledgerBookNo, String customerUuid, String financialContractUuid) {
		VirtualAccount virtualAccount = virtualAccountService.getVirtualAccountByCustomerUuid(customerUuid);
		if(virtualAccount==null){
			throw new VirtualAccountNotExsitException();
		}
		
		BigDecimal balance = ledgerBookVirtualAccountHandler.get_balance_of_customer(ledgerBookNo, customerUuid);
		if(balance.compareTo(virtualAccount.getTotalBalance())==0){
			return virtualAccount;
		}
		virtualAccount.updateBalance(balance);
		virtualAccountService.save(virtualAccount);
		return virtualAccount;
	}

	@Override
	public List<VirtualAccount> get_account_with_balance() {
		List<VirtualAccount> virtualAccounts = virtualAccountService.get_virtual_accounts_with_balance_gt0();
		
		
		return virtualAccounts;
	}

	@Override
	public List<VirtualAccountShowModel> getVirtualAccountList(List<VirtualAccount> virtualAccounts) {
		
		if(CollectionUtils.isEmpty(virtualAccounts)) {
			return Collections.emptyList();
		}
		
		List<VirtualAccountShowModel> virtualAccountShowModelList = new ArrayList<VirtualAccountShowModel>();
		
		for (VirtualAccount virtualAccount : virtualAccounts) {
				
				FinancialContract financialContract  = null;
				if(virtualAccount.getFstLevelContractUuid() != null){
					financialContract =  financialContractService.getFinancialContractBy(virtualAccount.getFstLevelContractUuid());
				}
				
				Contract contract = null;
				if(virtualAccount.getSndLevelContractUuid() != null){
					contract = contractService.getContract(virtualAccount.getSndLevelContractUuid());
				}
				
				
				if(virtualAccount != null){
					
					VirtualAccountShowModel virtualAccountShowModel = new VirtualAccountShowModel(virtualAccount, financialContract, contract);
					
					virtualAccountShowModelList.add(virtualAccountShowModel);
				}
		}
		
		return virtualAccountShowModelList;
	}

	@Override
	public CustomerDepositResult buildDeposit(String virtualAccountUuid) {
		VirtualAccount virtualAccount = virtualAccountService.getVirtualAccountByVirtualAccountUuid(virtualAccountUuid);
		if(virtualAccount==null){
			
		}
		FinancialContract financialContract = financialContractService.getFinancialContractBy(virtualAccount.getFstLevelContractUuid());
		Contract contract = contractService.getContract(virtualAccount.getSndLevelContractUuid());
		BigDecimal balance = ledgerBookVirtualAccountHandler.get_balance_of_customer(financialContract.getLedgerBookNo(), virtualAccount.getOwnerUuid());
		CustomerDepositResult customerDepositResult = CustomerDepositResult.buildToDeposit(financialContract, contract, balance, virtualAccount);
		return customerDepositResult;
	}

}

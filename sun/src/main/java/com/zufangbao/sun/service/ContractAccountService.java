/**
 * 
 */
package com.zufangbao.sun.service;

import java.util.List;

import com.demo2do.core.service.GenericService;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;

/**
 * @author zjm
 *
 */
public interface ContractAccountService extends GenericService<ContractAccount> {


	@Deprecated
	List<ContractAccount> getMatchContractAccountListByAccountNo(App app, String accountNo);
	
	@Deprecated
	List<ContractAccount> getMatchContractAccountListByPayerName(App app, String payerName);
	
	@Deprecated
	public List<Contract> getContractListByFilter(String payAcNo, String payerName, Page page, App app);
	
	@Deprecated
	List<Contract> getMatchContractListByAccountNo(App app, String accountNo);
	
	@Deprecated
	List<Contract> getMatchContractListByPayerName(App app, String payerName);
	
	@Deprecated
	public List<ContractAccount> getContractAccountsBy(Contract contract);
	
	@Deprecated
	public ContractAccount getOneContractAccountBy(Contract contract);
	
	//new 
	List<ContractAccount> get_match_contract_account_list_by_payerName_accountNo_bankName(String payerAccountName,
			String payerAccountNo, String bankName, String remark);
	
	public ContractAccount getTheEfficientContractAccountBy(Contract contract);
	
	public List<ContractAccount> getAllContractAccountBy(Contract contract);
	
	public void setContractAccountIsNotVaild(ContractAccount contractAccount);

	public List<ContractAccount> getContractAccountsByMatch(String keyWord);

	List<ContractAccount> getTheEfficientContractAccountListByPayerName(String name);

}

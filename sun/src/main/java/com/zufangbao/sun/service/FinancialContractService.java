/**
 * 
 */
package com.zufangbao.sun.service;

import java.util.HashMap;
import java.util.List;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.entity.account.DepositeAccountInfo;
import com.zufangbao.sun.entity.company.Company;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.PaymentChannel;
import com.zufangbao.sun.entity.financial.PaymentChannelType;

/**
 * @author zjm
 *
 */

public interface FinancialContractService extends GenericService<FinancialContract> {

	public List<FinancialContract> getAvailableFinancialContractForPincipal(Long principalId);
	
	public PaymentChannel getPaymentChannel(String accountNo, String merId, PaymentChannelType PaymentChannelType);
	
	public FinancialContract getFinancialContractById(Long financialContractId);
	
	public FinancialContract getFinancialContractBy(String financialContractUuid);
	
	public FinancialContract getUniqueFinancialContractBy(String contractNo);
	
	public Company getCompanyByAccountNo(String accountNo);
	public List<FinancialContract> getFinancialContractListBy(String accountNo);

	public  List<String> getFinancialContractUuidBy(String hostBankAccount);

	public HashMap<String, DepositeAccountInfo> extractDepositeAccount(
			FinancialContract contract);

	public FinancialContract getFinancialContractByLoanContractUniqueId(
			String contractUniqueId);

	public List<String> getFinancialContractUuidsByIds(List<Long> financialContractIds);
	
	public List<FinancialContract> getFinancialContractsByIds(List<Long> financialContractIds);
	
}

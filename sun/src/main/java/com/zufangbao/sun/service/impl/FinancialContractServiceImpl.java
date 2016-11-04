/**
 * 
 */
package com.zufangbao.sun.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.account.DepositeAccountInfo;
import com.zufangbao.sun.entity.account.DepositeAccountType;
import com.zufangbao.sun.entity.company.Company;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.financial.BusinessType;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.PaymentChannel;
import com.zufangbao.sun.entity.financial.PaymentChannelInformation;
import com.zufangbao.sun.entity.financial.PaymentChannelType;
import com.zufangbao.sun.ledgerbook.ChartOfAccount;
import com.zufangbao.sun.ledgerbook.LedgerTradeParty;
import com.zufangbao.sun.service.AppService;
import com.zufangbao.sun.service.AssetPackageService;
import com.zufangbao.sun.service.CompanyService;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.FinancialContractConfigService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.PaymentChannelInformationService;
import com.zufangbao.sun.yunxin.entity.remittance.AccountSide;

/**
 * @author zjm
 *
 */
@Service("financialContractService")
public class FinancialContractServiceImpl extends
		GenericServiceImpl<FinancialContract> implements
		FinancialContractService {

	@Autowired
	private GenericDaoSupport genericDaoSupport;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private AppService appService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private AssetPackageService assetPackageService;
	@Autowired
	private FinancialContractConfigService financialContractConfigService;
	@Autowired
	private PaymentChannelInformationService paymentChannelInformationService;
	
	public List<FinancialContract> getAvailableFinancialContractForPincipal(Long principalId){
		//TODO get available financial contract from principal;
		return this.list(FinancialContract.class, new Filter());
	}
	
	@Override
	public PaymentChannel getPaymentChannel(String accountNo, String merchantId, PaymentChannelType PaymentChannelType) {
		if(StringUtils.isEmpty(merchantId)){
			return null;
		}
		StringBuffer querySentence = new StringBuffer("SELECT fc.paymentChannel FROM FinancialContract fc "
				+ "where fc.paymentChannel.merchantId=:merchantId "
				+ " AND fc.paymentChannel.paymentChannelType=:paymentChannelType ");
		
		Map<String,Object> params = new HashMap<String,Object>();
		if(!StringUtils.isEmpty(accountNo)){
			params.put("accountNo", accountNo);
			querySentence.append(" AND fc.capitalAccount.accountNo=:accountNo ");
		}
		params.put("merchantId", merchantId);
		params.put("paymentChannelType", PaymentChannelType);
		List<PaymentChannel> paymentChannelList = genericDaoSupport.searchForList(querySentence.toString(),params);
		if(CollectionUtils.isEmpty(paymentChannelList)){
			return null;
		}
		return paymentChannelList.get(0);
	}

	@Override
	public FinancialContract getFinancialContractById(Long financialContractId) {
		return this.genericDaoSupport.get(FinancialContract.class, financialContractId);
	}

	@Override
	public FinancialContract getFinancialContractBy(String financialContractUuid) {
		if(StringUtils.isEmpty(financialContractUuid)){
			return null;
		}
		Filter filter = new Filter();
		filter.addEquals("financialContractUuid", financialContractUuid);
		List<FinancialContract> financialContracts = this.list(FinancialContract.class, filter);
		if(CollectionUtils.isNotEmpty(financialContracts)){
			return financialContracts.get(0);
		}
		return null;
	}
	
	@Override
	public FinancialContract getUniqueFinancialContractBy(String contractNo) {
		if(StringUtils.isEmpty(contractNo)){
			return null;
		}
		Filter filter  = new Filter(); 
		filter.addEquals("contractNo", contractNo);
		List<FinancialContract> financialContracts = this.list(FinancialContract.class, filter);
		if(CollectionUtils.isEmpty(financialContracts)){
			return null;
		}
		return financialContracts.get(0);
	}
	
	@Override
	public Company getCompanyByAccountNo(String accountNo) {
		List<FinancialContract> financialContractList = getFinancialContractListBy(accountNo);
		if(CollectionUtils.isEmpty(financialContractList)){
			return null;
		}
		return financialContractList.get(0).getCompany();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<FinancialContract> getFinancialContractListBy(String accountNo) {
		String sentence = "From FinancialContract where capitalAccount.accountNo=:accountNo";
		return genericDaoSupport.searchForList(sentence, "accountNo", accountNo);
	}
	
	@Override	
	public  List<String> getFinancialContractUuidBy(String hostBankAccount) {
		List<FinancialContract> financialContracts=this.list(FinancialContract.class,new Filter());
		List<String> financialContractUuids=new ArrayList<String>();
		for(FinancialContract contract:financialContracts)
		{
		
			Account account=contract.getCapitalAccount();
			if(account!=null)
			{
				if(StringUtils.isEmpty(account.getAccountNo())==false&&
						account.equals(hostBankAccount)==true)
				{
					financialContractUuids.add(contract.getUuid());
				}
			}
		}
		return financialContractUuids;
	}
	
	private void addDepositeAccountInfo(Map<String,DepositeAccountInfo> accountList, String accountName, LedgerTradeParty party,
			DepositeAccountType depositeAccountType){
		DepositeAccountInfo info=new DepositeAccountInfo(accountName,party,depositeAccountType);
		accountList.put(accountName, info);
	}
	
	@Override		
	public  HashMap<String, DepositeAccountInfo> extractDepositeAccount(FinancialContract contract) {
		HashMap<String,DepositeAccountInfo> accountList=new HashMap<String,DepositeAccountInfo>();
		PaymentChannel paymentChannel=contract.getPaymentChannel();
		LedgerTradeParty party=new LedgerTradeParty();
		party.setFstParty(contract.getCompany().getUuid());
		party.setSndParty("");
		if(paymentChannel!=null){
			//YL NFQ MODE
			if(StringUtils.isEmpty(paymentChannel.getMerchantId())==false){
				addDepositeAccountInfo(accountList, paymentChannel.getMerchantId(), party, DepositeAccountType.UINON_PAY);
			}
		}
			
		String uuid = financialContractConfigService.getPaymentChannelInformationUuids(contract.getFinancialContractUuid(), BusinessType.SELF, AccountSide.DEBIT);
		PaymentChannelInformation paymentChannelInformation =  paymentChannelInformationService.getPaymentChannelInformationBy(uuid);
		if(paymentChannelInformation!=null && !StringUtils.isEmpty(paymentChannelInformation.getOutlierChannelName())){
			//YL New channel mode
			addDepositeAccountInfo(accountList, paymentChannelInformation.getOutlierChannelName(), party, DepositeAccountType.UINON_PAY);
			
			if(!StringUtils.isEmpty(paymentChannelInformation.getMerId_ClearingNo())){
				addDepositeAccountInfo(accountList, paymentChannelInformation.getMerId_ClearingNo(), party, DepositeAccountType.UINON_PAY);
			}
		}
		Account account=contract.getCapitalAccount();
		if(account!=null){
			if(StringUtils.isEmpty(account.getAccountNo())==false){
				//bank ACCOUNT
				addDepositeAccountInfo(accountList, account.getAccountNo(), party, DepositeAccountType.BANK);
			}
				
		}
		addDepositeAccountInfo(accountList, ChartOfAccount.FST_BANK_SAVING, party, DepositeAccountType.BANK);
		
		return accountList;
	}
		
	@Override
	public FinancialContract getFinancialContractByLoanContractUniqueId(
			String  contractUniqueId) {
		Contract contract =  contractService.getContractByUniqueId(contractUniqueId);
		return this.getFinancialContractBy(contract.getFinancialContractUuid());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getFinancialContractUuidsByIds(
			List<Long> financialContractIds) {
		if(CollectionUtils.isEmpty(financialContractIds)) {
			return Collections.emptyList();
		}
		String sql = "SELECT financialContractUuid FROM FinancialContract WHERE id IN (:financialContractIds)";
		return this.genericDaoSupport.searchForList(sql, "financialContractIds", financialContractIds);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FinancialContract> getFinancialContractsByIds(
			List<Long> financialContractIds) {
		if(CollectionUtils.isEmpty(financialContractIds)) {
			return Collections.emptyList();
		}
		String sql = "FROM FinancialContract WHERE id IN (:financialContractIds)";
		return this.genericDaoSupport.searchForList(sql, "financialContractIds", financialContractIds);
	}
		
}

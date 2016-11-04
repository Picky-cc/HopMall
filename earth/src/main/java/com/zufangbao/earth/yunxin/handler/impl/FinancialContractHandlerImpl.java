package com.zufangbao.earth.yunxin.handler.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.persistence.support.Order;
import com.demo2do.core.utils.StringUtils;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.earth.yunxin.handler.FinancialContractHandler;
import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.company.Company;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.FinancialContractType;
import com.zufangbao.sun.ledgerbook.LedgerBook;
import com.zufangbao.sun.service.AccountService;
import com.zufangbao.sun.service.AppService;
import com.zufangbao.sun.service.CompanyService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.LedgerBookService;
import com.zufangbao.sun.service.PaymentChannelInformationService;
import com.zufangbao.sun.yunxin.entity.AssetPackageFormat;
import com.zufangbao.sun.yunxin.entity.model.CreateFinancialContractModel;
import com.zufangbao.sun.yunxin.entity.model.financialcontract.FinancialContractQueryModel;

@Component("FinancialContractHandler")
public class FinancialContractHandlerImpl implements FinancialContractHandler {

	@Autowired
	private AccountService accountService;
	@Autowired
	private FinancialContractService financialContractService;
	
	@Autowired
	private PaymentChannelInformationService paymentChannelInformationService;
	
	@Autowired
	private AppService appService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private LedgerBookService ledgerBookService;
	
	@Override
	public FinancialContract editFinancialContract(
			CreateFinancialContractModel createFinancialContractModel,
			Long financialContractId) {
		return editFinancialContractByData(createFinancialContractModel,
				financialContractId);
	}

	@Override
	public FinancialContract createFinancialContract(
			CreateFinancialContractModel createFinancialContractModel) {
		return createFinancialContractByData(createFinancialContractModel);
	}

	private FinancialContract editFinancialContractByData(
			CreateFinancialContractModel financialContractModel,
			Long financialContractId) {

		FinancialContract financialContract = financialContractService.load(
				FinancialContract.class,financialContractId);
		if(StringUtils.isEmpty(financialContract.getUuid())){
			financialContract.setFinancialContractUuid(UUID.randomUUID().toString());
		}
		
		financialContract.setContractNo(financialContractModel
				.getFinancialContractNo());
		financialContract.setContractName(financialContractModel
				.getFinancialContractName());
		if (financialContractModel.getAppId() != null) {
			financialContract.setApp(appService.load(App.class,
					financialContractModel.getAppId()));
		}
		financialContract.setCompany(companyService.load(Company.class,
				financialContractModel.getCompanyId()));
		financialContract.setFinancialContractType(FinancialContractType
				.formValue(financialContractModel.getFinancialContractType()));
		financialContract.setLoanOverdueStartDay(financialContractModel
				.getLoanOverdueStartDay());
		financialContract.setLoanOverdueEndDay(financialContractModel
				.getLoanOverdueEndDay());
		financialContract.setAdvaRepoTerm(financialContractModel
				.getAdvaRepoTerm());
		financialContract.setAdvaMatuterm(financialContractModel
				.getAdvaMatuterm());
		financialContract.setAdvaStartDate(financialContractModel
				.getAdvaStartDate());
		financialContract.setThruDate(financialContractModel.getThruDate());
		Account capitalAccount = financialContract.getCapitalAccount();
		if (capitalAccount == null) {
			Account newcapitalAccount = new Account(
					financialContractModel.getTrustsBankName(),
					financialContractModel.getTrustsAccountName(),
					financialContractModel.getTrustsAccountNo());
			Serializable accountId = accountService.save(newcapitalAccount);
			newcapitalAccount = accountService.load(Account.class, accountId);
			financialContract.setCapitalAccount(newcapitalAccount);
		} else {
			capitalAccount.setBankName(financialContractModel
					.getTrustsBankName());
			capitalAccount.setAccountName(financialContractModel
					.getTrustsAccountName());
			capitalAccount.setAccountNo(financialContractModel
					.getTrustsAccountNo());
			accountService.update(capitalAccount);
		}
		financialContract.setAssetPackageFormat(AssetPackageFormat
				.formValue(financialContractModel.getAssetPackageFormat()));
		financialContractService.update(financialContract);
		return financialContract;
	}

	/**
	 * @param financialContractModel
	 */
	private FinancialContract createFinancialContractByData(
			CreateFinancialContractModel financialContractModel) {
		FinancialContract financialContract = new FinancialContract();

		financialContract.setFinancialContractUuid(UUID.randomUUID().toString());
		financialContract.setContractNo(financialContractModel
				.getFinancialContractNo());
		financialContract.setContractName(financialContractModel
				.getFinancialContractName());
		if (financialContractModel.getAppId() != null) {
			financialContract.setApp(appService.load(App.class,
					financialContractModel.getAppId()));
		}
		financialContract.setCompany(companyService.load(Company.class,
				financialContractModel.getCompanyId()));
		financialContract.setFinancialContractType(FinancialContractType
				.formValue(financialContractModel.getFinancialContractType()));
		financialContract.setLoanOverdueStartDay(financialContractModel
				.getLoanOverdueStartDay());
		financialContract.setLoanOverdueEndDay(financialContractModel
				.getLoanOverdueEndDay());
		financialContract.setAdvaRepoTerm(financialContractModel
				.getAdvaRepoTerm());
		financialContract.setAdvaMatuterm(financialContractModel
				.getAdvaMatuterm());
		financialContract.setAdvaStartDate(financialContractModel
				.getAdvaStartDate());
		financialContract.setThruDate(financialContractModel.getThruDate());
		Account capitalAccount = new Account(
				financialContractModel.getTrustsBankName(),
				financialContractModel.getTrustsAccountName(),
				financialContractModel.getTrustsAccountNo());
		Serializable accountId = accountService.save(capitalAccount);
		capitalAccount = accountService.load(Account.class, accountId);
		financialContract.setCapitalAccount(capitalAccount);
		financialContract.setAssetPackageFormat(AssetPackageFormat
				.formValue(financialContractModel.getAssetPackageFormat()));
		financialContract.setLedgerBookNo(UUID.randomUUID().toString());
		financialContractService.save(financialContract);
		
		createLedgerBook(financialContract.getLedgerBookNo(), financialContractModel.getCompanyId());
		return financialContract;
	}
	
	private void createLedgerBook(String ledgerBookNo, Long companyId){
		try {
			LedgerBook LedgerBook = new LedgerBook(ledgerBookNo, companyId+"");
			ledgerBookService.save(LedgerBook);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Object> query(FinancialContractQueryModel financialContractQueryModel, Page page) {
		Filter filter = getContractFilter(financialContractQueryModel);
		Order order = new Order("id", "desc");
		List<FinancialContract> totalFinancialContracts = financialContractService.list(FinancialContract.class, filter, order);
		List<FinancialContract> pagedFinancialContracts = financialContractService.list(FinancialContract.class, filter, order, page);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("size", totalFinancialContracts.size());
		result.put("list", pagedFinancialContracts);
		return result;
	}
	
	private Filter getContractFilter(FinancialContractQueryModel financialContractQueryModel) {
		financialContractQueryModel = financialContractQueryModel == null? new FinancialContractQueryModel() : financialContractQueryModel;
		Filter filter = new Filter();
		if (is_where_condition(financialContractQueryModel.getFinancialContractNo())) {
			filter.addLike("contractNo", financialContractQueryModel.getFinancialContractNo());
		}
		if (is_where_condition(financialContractQueryModel.getFinancialContractName())) {
			filter.addLike("contractName", financialContractQueryModel.getFinancialContractName());
		}
		if ((financialContractQueryModel.getAppId() == null) || (financialContractQueryModel.getAppId() != -1) ) {
			filter.addEquals("app.id", financialContractQueryModel.getAppId());
		}
		if (FinancialContractType.formValue(financialContractQueryModel.getFinancialContractType()) != null) {
			filter.addEquals("financialContractType", FinancialContractType.formValue(financialContractQueryModel.getFinancialContractType()));
		}
		if (is_where_condition(financialContractQueryModel.getFinancialAccountNo())) {
			filter.addLike("capitalAccount.accountNo", financialContractQueryModel.getFinancialAccountNo());
		}
		return filter;
	}

	private boolean is_where_condition(String address) {
		return !StringUtils.isEmpty(address);
	}

	@Override
	public List<FinancialContract> getFinancialContractList(Long financialContractId) {
		if(financialContractId == -1) {
			return financialContractService.loadAll(FinancialContract.class);
		}
		FinancialContract financialContract = financialContractService.load(FinancialContract.class, financialContractId);
		if(financialContract != null) {
			List<FinancialContract> financialContractList = new ArrayList<>();
			financialContractList.add(financialContract);
			return financialContractList;
		}
		return Collections.emptyList();
	}

}
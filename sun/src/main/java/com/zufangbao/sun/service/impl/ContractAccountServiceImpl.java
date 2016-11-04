/**
 * 
 */
package com.zufangbao.sun.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.service.ContractAccountService;
import com.zufangbao.sun.utils.DateUtils;

/**
 * @author wk,meikehuan
 *
 */
@Service("contractAccountService")
public class ContractAccountServiceImpl extends
		GenericServiceImpl<ContractAccount> implements ContractAccountService {
	
	
	@Deprecated
	@Override
	public List<Contract> getContractListByFilter(String payAcNo,
			String payerName, Page page, App app) {
		List<Contract> contractList = new ArrayList<Contract>();
		Filter contract_account_filter = getContractAccountFilter(payAcNo,
				payerName, app);
		List<ContractAccount> contractAccountList = list(ContractAccount.class,
				contract_account_filter, page);
		for (ContractAccount ca : contractAccountList) {
			contractList.add(ca.getContract());
		}
		return contractList;
	}

	private boolean isNotEmpty(String message) {
		return !StringUtils.isEmpty(message);
	}

	
	@Deprecated
	@Override
	public List<ContractAccount> getMatchContractAccountListByAccountNo(
			App app, String accountNo) {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("PayAcNo", accountNo);
		parameters.put("app", app);

		return genericDaoSupport
				.searchForList(
						"FROM ContractAccount WHERE trim(PayAcNo)=:PayAcNo AND contract.app=:app",
						parameters);
	}

	@Deprecated
	@Override
	public List<Contract> getMatchContractListByAccountNo(App app,
			String accountNo) {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("PayAcNo", accountNo);
		parameters.put("app", app);

		return (List<Contract>) genericDaoSupport
				.searchForList(
						"SELECT ca.contract FROM ContractAccount ca WHERE trim(ca.PayAcNo)=:PayAcNo AND ca.contract.app=:app",
						parameters);
	}
	@Deprecated
	@Override
	public List<ContractAccount> getMatchContractAccountListByPayerName(
			App app, String payerName) {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("payerName", payerName);
		parameters.put("app", app);

		return genericDaoSupport
				.searchForList(
						"FROM ContractAccount WHERE trim(payerName)=:payerName AND contract.app=:app",
						parameters);

	}
	@Deprecated
	@Override
	public List<Contract> getMatchContractListByPayerName(App app,
			String payerName) {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("payerName", payerName);
		parameters.put("app", app);

		return (List<Contract>) genericDaoSupport
				.searchForList(
						"SELECT ca.contract FROM ContractAccount ca WHERE trim(ca.payerName)=:payerName AND ca.contract.app=:app",
						parameters);

	}
	@Deprecated
	private Filter getContractAccountFilter(String payAcNo, String payerName,
			App app) {
		Filter filter = new Filter();
		filter.addEquals("contract.app", app);
		if (isNotEmpty(payAcNo)) {
			filter.addLike("PayAcNo", payAcNo);
		}
		if (isNotEmpty(payerName)) {
			filter.addLike("payerName", payerName);
		}
		return filter;
	}

	@Deprecated
	@Override
	public List<ContractAccount> getContractAccountsBy(Contract contract) {

		if (null == contract) {

			return Collections.emptyList();
		}
		Filter filter = new Filter();

		filter.addEquals("contract", contract);

		return this.list(ContractAccount.class, filter);
	}
	@Deprecated
	@Override
	public ContractAccount getOneContractAccountBy(Contract contract) {
		List<ContractAccount> accounts = getContractAccountsBy(contract);
		if (CollectionUtils.isEmpty(accounts)) {
			return null;
		}
		return accounts.get(0);
	}

	@Override
	public List<ContractAccount> get_match_contract_account_list_by_payerName_accountNo_bankName(
			String payerAccountName, String payerAccountNo, String bankName, String remark) {
		Filter filter = new Filter();
		String trimedRemark = StringUtils.trim(remark);
		if(StringUtils.isEmpty(payerAccountName) && StringUtils.isEmpty(payerAccountNo)
				&& StringUtils.isEmpty(bankName) && StringUtils.isEmpty(trimedRemark)){
			return Collections.emptyList();
		}
		if(!StringUtils.isEmpty(payerAccountName)){
			filter.addLike("payerName", payerAccountName);
		}
		if(!StringUtils.isEmpty(payerAccountNo)){
			filter.addLike("payAcNo", payerAccountNo);
		}
		if(!StringUtils.isEmpty(bankName)){
			filter.addLike("bank", bankName);
		}
		if(!StringUtils.isEmpty(trimedRemark)){
			filter.addLike("payerName", trimedRemark);
		}
		filter.addEquals("thruDate", DateUtils.MAX_DATE);
		return this.list(ContractAccount.class, filter);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ContractAccount getTheEfficientContractAccountBy(Contract contract) {
		if (contract == null) {
			return null;
		}
		
		String hql = "FROM ContractAccount WHERE contract =:contract AND thruDate =:thruDate ORDER BY id DESC";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("contract", contract);
		params.put("thruDate", DateUtils.MAX_DATE);
		List<ContractAccount> contractAccounts = this.genericDaoSupport.searchForList(hql, params, 0, 1);
		if(CollectionUtils.isEmpty(contractAccounts)) {
			return null;
		}
		return contractAccounts.get(0);
	}

	@Override
	public void setContractAccountIsNotVaild(ContractAccount contractAccount) {
		contractAccount.setThruDate(new Date());
		this.saveOrUpdate(contractAccount);
	}

	@Override
	public List<ContractAccount> getAllContractAccountBy(Contract contract) {
		if (null == contract) {

			return Collections.emptyList();
		}
		Filter filter = new Filter();

		filter.addEquals("contract", contract);

		return this.list(ContractAccount.class, filter);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ContractAccount> getContractAccountsByMatch(String keyWord) {
		//TODO 是否需要匹配出历史的账户
		String querySentece = "From ContractAccount where (contract.contractNo LIKE :contractNo OR payerName LIKE :payerName) "
				+ " AND Date(thruDate) = Date(:thruDate)";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("contractNo", "%"+keyWord+"%");
		params.put("payerName", "%"+keyWord+"%");
		params.put("thruDate",DateUtils.MAX_DATE);
		return genericDaoSupport.searchForList(querySentece,params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContractAccount> getTheEfficientContractAccountListByPayerName(String name) {
		String querySentece = "From ContractAccount where payerName LIKE :payerName AND Date(thruDate) = Date(:thruDate)";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("payerName", "%"+name+"%");
		params.put("thruDate",DateUtils.MAX_DATE);
		return genericDaoSupport.searchForList(querySentece,params);
	}
}

package com.zufangbao.sun.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.account.VirtualAccount;
import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.customer.CustomerType;
import com.zufangbao.sun.service.CustomerService;
import com.zufangbao.sun.service.VirtualAccountService;
import com.zufangbao.sun.yunxin.entity.model.VirtualAccountModel;

@Service("virtualAccountService")
public class VirtualAccountServiceImpl extends GenericServiceImpl<VirtualAccount> implements VirtualAccountService {
	@Autowired
	private CustomerService customerService;
	@Override
	public VirtualAccount getVirtualAccountByCustomerUuid(String customerUuid) {
		Filter filter = new Filter();
		filter.addEquals("ownerUuid", customerUuid);
		List<VirtualAccount> virtualAccountList = list(VirtualAccount.class, filter);
		if(CollectionUtils.isEmpty(virtualAccountList)){
			return null;
		}
		return virtualAccountList.get(0);
	}

	@Override
	public VirtualAccount create_if_not_exist_virtual_account(String customerUuid, String financialContractUuid, String contractUuid) {
		VirtualAccount virtualAccountInDb = getVirtualAccountByCustomerUuid(customerUuid);
		if(virtualAccountInDb!=null){
			return virtualAccountInDb;
		}
		Customer customer = customerService.getCustomer(customerUuid);
		int try_times=10;
		while(try_times-->0)
		{
			VirtualAccount newVirtualAccount = new VirtualAccount(customer.getCustomerUuid(),customer.getName(),customer.getCustomerType()==null?-1:customer.getCustomerType().ordinal(),financialContractUuid,contractUuid,"");
			save(newVirtualAccount);
			VirtualAccount insertedAccount=this.getVirtualAccountByVirtualAccountUuid(newVirtualAccount.getVirtualAccountUuid());
			if(insertedAccount!=null) return newVirtualAccount;
		}
		VirtualAccount newVirtualAccount = new VirtualAccount(customer.getCustomerUuid(),customer.getName(),customer.getCustomerType()==null?-1:customer.getCustomerType().ordinal(),financialContractUuid,contractUuid,"");
		newVirtualAccount.setVirtualAccountNo(newVirtualAccount.getVirtualAccountUuid());
		save(newVirtualAccount);
		return newVirtualAccount;
	}

	@Override
	public List<VirtualAccount> get_virtual_accounts_with_balance_gt0() {
		Filter filter = new Filter();
		filter.addGreaterThan("totalBalance", BigDecimal.ZERO);
		return this.list(VirtualAccount.class, filter);
	}

	@Override
	public VirtualAccount getVirtualAccountByVirtualAccountUuid(String virtualAccountUuid){
		Filter filter = new Filter();
		filter.addEquals("virtualAccountUuid", virtualAccountUuid);
		List<VirtualAccount> virtualAccountList =  this.list(VirtualAccount.class, filter);
		if(CollectionUtils.isEmpty(virtualAccountList)){
			return null;
		}
		return virtualAccountList.get(0);
	}

	@Override
	public List<VirtualAccount> getVirtualAccountList(
			VirtualAccountModel virtualAccountModel, Page page) {
		
		String queryString = "select v from  VirtualAccount v where 1=1";
		Map<String, Object> parameters = new HashMap<>();
		
		if (CustomerType.fromOrdinal(virtualAccountModel.getCustomerType())!= null) {
			queryString += " And v.customerType =:customerType";
			parameters.put("customerType", virtualAccountModel.getCustomerType());
		}
		
		if (is_where_condition(virtualAccountModel.getFstLevelContractUuid())) {
			queryString += " And v.fstLevelContractUuid =:fstLevelContractUuid";
			parameters.put("fstLevelContractUuid", virtualAccountModel.getFstLevelContractUuid());
		}
		
		if (is_where_condition(virtualAccountModel.getKey())) {
			queryString += " And ((v.virtualAccountAlias like :key) or (v.virtualAccountNo like :key)";
			parameters.put("key", "%"+virtualAccountModel.getKey()+"%");
			if(virtualAccountModel.getAmountFromKey()!=null){
				queryString+= "or (totalBalance=:amount)";
				parameters.put("amount", virtualAccountModel.getAmountFromKey());
			}
			queryString +=")";
		}
		queryString +=" order by lastModifiedTime desc";
		if (page == null) {
			return genericDaoSupport.searchForList(queryString, parameters);
		} else {
			return genericDaoSupport.searchForList(queryString, parameters, page.getBeginIndex(), page.getEveryPage());
		}
	}
	
	private boolean is_where_condition(String address) {
		return !StringUtils.isEmpty(address);
	}

	@Override
	public List<VirtualAccount> queryVirtualAccountList(String financialContractUuid, Long customerType, String queryKey, Page page) {
		String querySentence = "From VirtualAccount where fstLevelContractUuid=:financialContractUuid AND"
				+ "customerType=:customerType AND (virtualAccountNo LIKE:queryKey or ownerName LIKE:queryKey )";
		Map<String, Object> params = new HashMap<>();
		params.put("financialContractUuid", financialContractUuid);
		params.put("customerType",customerType);
		params.put("queryKey", queryKey);
		List<VirtualAccount> virtualAccountList = genericDaoSupport.searchForList(querySentence, params,page.getBeginIndex(),page.getEveryPage());
		return virtualAccountList;
	}
	
}

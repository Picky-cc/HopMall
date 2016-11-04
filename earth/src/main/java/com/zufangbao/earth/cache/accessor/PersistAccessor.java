package com.zufangbao.earth.cache.accessor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.yunxin.handler.FinancialContracCacheHandler;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.user.TUser;
import com.zufangbao.wellsfargo.greypool.document.entity.busidocument.dictionary.Currency;

@Component("persistAccessor")
public class PersistAccessor {

	@Autowired
	private GenericDaoSupport genericDaoSupport;
	
	@Autowired
	private FinancialContracCacheHandler financialContracCacheHandler;
	
	public String getRealnameBy(Long principalId){
		Principal principal = genericDaoSupport.load(Principal.class, principalId);
		TUser tUser = principal.gettUser();
		return tUser == null ? "" : tUser.getName();
	}

	public Currency getCurrencyBy(String name){
		return Currency.fromName(name);
	}
	
	@SuppressWarnings("unchecked")
	public List<FinancialContract> getCanAccessFinancialContractList(Long princialId){
		//TODO all financialContracts available.
		return financialContracCacheHandler.getAvailableFinancialContractList(princialId);
	}

}

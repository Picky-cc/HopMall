package com.zufangbao.sun.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.account.VirtualAccount;
import com.zufangbao.sun.entity.account.VirtualAccountFlow;
import com.zufangbao.sun.entity.account.VirtualAccountTransactionType;
import com.zufangbao.sun.service.VirtualAccountFlowService;
import com.zufangbao.sun.yunxin.entity.model.AccountFlowModel;

@Service("virtualAccountFlowService")
public class VirtualAccountFlowServiceImpl extends GenericServiceImpl<VirtualAccountFlow> implements VirtualAccountFlowService {

	@Override
	public List<VirtualAccountFlow> getVirtualAccountFlowList(
			AccountFlowModel accountFlowModel, Page page) {
		Map<String,Object> parameters = new HashMap<String,Object>();
		String querySentence = getVirtualAccountFlowFilter(accountFlowModel,parameters);
		if(page==null){
			return genericDaoSupport.searchForList(querySentence, parameters);
		}
		return genericDaoSupport.searchForList(querySentence, parameters,page.getBeginIndex(),page.getEveryPage());
	}
	

	private String getVirtualAccountFlowFilter(AccountFlowModel accountFlowModel,Map<String,Object> parameters) {
		if(accountFlowModel==null){
			return null;
		}
		StringBuffer queryString = new StringBuffer("from  VirtualAccountFlow where 1=1");
		if (accountFlowModel.getAccountSideEnum()!=null) {
			queryString.append(" AND accountSide=:accountSide");
			parameters.put("accountSide", accountFlowModel.getAccountSideEnum());
		}
		
		if (accountFlowModel.getVirtualAccountTransactionTypeEnum()!=null) {
			queryString.append(" AND transactionType=:transactionType");
			parameters.put("transactionType", accountFlowModel.getVirtualAccountTransactionTypeEnum());
		}
		
		if(!StringUtils.isEmpty(accountFlowModel.getKey())){
			queryString.append(" AND ((businessDocumentNo like :key) or (virtualAccountNo like :key) or (virtualAccountAlias like :key))");
			parameters.put("key", "%"+accountFlowModel.getKey()+"%");
		}
		
		queryString.append(" order by id desc");
		return queryString.toString();

	}

	@Override
	public int count(AccountFlowModel accountFlowModel) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		String querySentence = getVirtualAccountFlowFilter(accountFlowModel, parameters);
		return genericDaoSupport.count(querySentence, parameters);
	}
	
	
	@Override
	public void addAccountFlow(String businessDocumentNo, VirtualAccount virtualAccount, BigDecimal transactionAmount, AccountSide accountSide,VirtualAccountTransactionType transactionType) {
		VirtualAccountFlow virtualAccountFlow = new VirtualAccountFlow(businessDocumentNo,
				accountSide,virtualAccount.getVirtualAccountUuid(), virtualAccount.getVirtualAccountNo(),virtualAccount.getVirtualAccountAlias(), transactionAmount,
				virtualAccount.getTotalBalance(), transactionType);
		this.save(virtualAccountFlow);
	}
	
}

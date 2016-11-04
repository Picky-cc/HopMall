package com.zufangbao.sun.service;

import java.math.BigDecimal;
import java.util.List;

import com.demo2do.core.service.GenericService;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.account.VirtualAccount;
import com.zufangbao.sun.entity.account.VirtualAccountFlow;
import com.zufangbao.sun.entity.account.VirtualAccountTransactionType;
import com.zufangbao.sun.yunxin.entity.model.AccountFlowModel;

public interface VirtualAccountFlowService extends GenericService<VirtualAccountFlow> {
	
	public List<VirtualAccountFlow> getVirtualAccountFlowList(AccountFlowModel AccountFlowModel,Page page);
	public int count(AccountFlowModel accountFlowModel);
	public void addAccountFlow(String businessDocumentNo, VirtualAccount virtualAccount, BigDecimal transactionAmount, AccountSide accountSide,VirtualAccountTransactionType transactionType);
}

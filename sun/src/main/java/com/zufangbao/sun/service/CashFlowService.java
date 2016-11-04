package com.zufangbao.sun.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.demo2do.core.service.GenericService;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.entity.icbc.business.CashFlowChannelType;
import com.zufangbao.sun.entity.icbc.business.CashFlowQueryModel;
import com.zufangbao.sun.ledgerbook.AccountSide;
import com.zufangbao.sun.yunxin.entity.model.BankReconciliationQueryModel;

public interface CashFlowService extends GenericService<CashFlow> {
	
	public boolean isCashFlowAlreadyExist(String hostAccountNo, String bankSequenceNo, BigDecimal transactionAmount);

	public List<CashFlow> searchCashFlowList(CashFlowQueryModel queryModel);

	public List<CashFlow> getCashFlowListBy(String paymentAccountNo, String paymentName, BigDecimal voucherAmount);

	public CashFlow getCashFlowByCashFlowUuid(String cashFlowUuid);
	
	public List<CashFlow> getCashFlowList(BankReconciliationQueryModel bankReconciliationQueryModel, Page page);
	
	public int count(BankReconciliationQueryModel bankReconciliationQueryModel);
	
	public List<CashFlow> getCashFlowsBy(String hostAccountNo, Date begin, Date end, AccountSide accountSide);
	
	public List<CashFlow> listCashFlowBy(String tradeUuid, AccountSide accountSide, CashFlowChannelType cashFlowChannelType);
}

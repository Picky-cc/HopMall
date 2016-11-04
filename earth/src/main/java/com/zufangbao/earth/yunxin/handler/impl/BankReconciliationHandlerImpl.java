package com.zufangbao.earth.yunxin.handler.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.persistence.support.Order;
import com.demo2do.core.utils.StringUtils;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.earth.yunxin.handler.BankReconciliationHandler;
import com.zufangbao.sun.entity.account.AuditStatus;
import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.ledgerbook.AccountSide;
import com.zufangbao.sun.service.BankReconciliationService;
import com.zufangbao.sun.yunxin.entity.model.BankReconciliationQueryModel;

@Component("bankReconciliationHandler")
public class BankReconciliationHandlerImpl implements BankReconciliationHandler{

	@Autowired
	private BankReconciliationService bankReconciliationService;
	
	@Override
	public Map<String, Object> query(
			BankReconciliationQueryModel bankReconciliationQueryModel, Page page) {
		Filter filter = getContractFilter(bankReconciliationQueryModel);
		Order order = new Order("dealTime", "desc");
		List<CashFlow> totalCashFlow = bankReconciliationService.list(CashFlow.class, filter, order);
		List<CashFlow> pagedCashFlow = bankReconciliationService.list(CashFlow.class, filter, order, page);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("size", totalCashFlow.size());
		result.put("list", pagedCashFlow);
		return result;
	}
	
	
	private Filter getContractFilter(BankReconciliationQueryModel bankReconciliationQueryModel) {
		bankReconciliationQueryModel = bankReconciliationQueryModel == null? new BankReconciliationQueryModel() : bankReconciliationQueryModel;
		Filter filter = new Filter();
		if (AccountSide.fromValue(bankReconciliationQueryModel.getAccountSide())!= null) {
			filter.addEquals("drcrf", AccountSide.fromValue(bankReconciliationQueryModel.getAccountSide()));
		}
		if (AuditStatus.fromOrdinal(bankReconciliationQueryModel.getAuditStatus())!= null) {
			filter.addEquals("auditStatus", AuditStatus.fromOrdinal(bankReconciliationQueryModel.getAuditStatus()));
		}
		if (is_where_condition(bankReconciliationQueryModel.getHostAccountNo())) {
			filter.addLike("hostAccountNo", bankReconciliationQueryModel.getHostAccountNo());
		}
		if (is_where_condition(bankReconciliationQueryModel.getKey())) {
			filter.addLike("bankCashFlow", bankReconciliationQueryModel.getKey());
			filter.addLike("hostAccountNo", bankReconciliationQueryModel.getKey());
			filter.addLike("hostAccountName", bankReconciliationQueryModel.getKey());
			filter.addLike("dealAmount", bankReconciliationQueryModel.getKey());
			filter.addLike("summary", bankReconciliationQueryModel.getKey());
		}
		return filter;
	}
	
	private boolean is_where_condition(String address) {
		return !StringUtils.isEmpty(address);
	}

}

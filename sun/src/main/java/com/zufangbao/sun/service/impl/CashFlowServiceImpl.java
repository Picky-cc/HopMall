package com.zufangbao.sun.service.impl;

import java.math.BigDecimal;
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
import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.entity.icbc.business.CashFlowChannelType;
import com.zufangbao.sun.entity.icbc.business.CashFlowQueryModel;
import com.zufangbao.sun.ledgerbook.AccountSide;
import com.zufangbao.sun.service.CashFlowService;
import com.zufangbao.sun.yunxin.entity.model.BankReconciliationQueryModel;

@Service("cashFlowService")
public class CashFlowServiceImpl extends GenericServiceImpl<CashFlow> implements
		CashFlowService {
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean isCashFlowAlreadyExist(String hostAccountNo,
			String bankSequenceNo, BigDecimal transactionAmount) {
		if(StringUtils.isEmpty(hostAccountNo) || StringUtils.isEmpty(bankSequenceNo) || null == transactionAmount) {
			return true;//TODO
		}
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("hostAccountNo", hostAccountNo);
		parms.put("bankSequenceNo", bankSequenceNo);
		parms.put("transactionAmount", transactionAmount);
		List<CashFlow> cashFlowList = genericDaoSupport.searchForList("FROM CashFlow WHERE bankSequenceNo =:bankSequenceNo AND hostAccountNo =:hostAccountNo AND transactionAmount =:transactionAmount", parms);
		
		return ! CollectionUtils.isEmpty(cashFlowList);
	}

	@Override
	public List<CashFlow> searchCashFlowList(CashFlowQueryModel queryModel) {
		if (queryModel == null) {
			return Collections.EMPTY_LIST;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "From CashFlow WHERE 1=1 ";

		if(queryModel.getCashFlowChannelType()!=null){
			sql += " AND cashFlowChannelType =:cashFlowChannelType ";
			params.put("cashFlowChannelType", queryModel.getCashFlowChannelType());
		}
		if (!StringUtils.isEmpty(queryModel.getHostAccountNo())) {
			sql += " AND hostAccountNo =:hostAccountNo ";
			params.put("hostAccountNo", queryModel.getHostAccountNo());
		}

		if (queryModel.getStartDate() != null) {
			sql += " AND (Date(:startDate) <= Date(transactionTime)) ";
			params.put("startDate", queryModel.getStartDate());
		}

		if (queryModel.getEndDate() != null) {
			sql += " AND (Date(transactionTime) <= Date(:endDate)) ";
			params.put("endDate", queryModel.getEndDate());
		}
		if (queryModel.getAccountSide() != null) {
			sql += " AND accountSide =:accountSide ";
			params.put("accountSide", queryModel.getAccountSide());
		}
		if (queryModel.getAuditStatusEnum() != null) {
			sql += " AND auditStatus =:auditStatus ";
			params.put("auditStatus", queryModel.getAuditStatusEnum());
		}

		return genericDaoSupport.searchForList(sql, params);
	}

	@Override
	public List<CashFlow> getCashFlowListBy(String paymentAccountNo,
			String paymentName, BigDecimal voucherAmount) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("counterAccountName", paymentName);
		parameters.put("counterAccountNo", paymentAccountNo);
		parameters.put("transactionAmount", voucherAmount);

		return genericDaoSupport
				.searchForList(
						"FROM CashFlow WHERE counterAccountName = :counterAccountName AND counterAccountNo = :counterAccountNo AND transactionAmount = :transactionAmount ",
						parameters);
	}

	@Override
	public CashFlow getCashFlowByCashFlowUuid(String cashFlowUuid) {
		Filter filter = new Filter();
		filter.addEquals("cashFlowUuid", cashFlowUuid);
		List<CashFlow> cashFlowList = this.list(CashFlow.class, filter);
		if (CollectionUtils.isEmpty(cashFlowList)) {
			return null;
		}
		return cashFlowList.get(0);
	}

	
	private String getContractFilter(BankReconciliationQueryModel bankReconciliationQueryModel,Map<String,Object> parameters) {
		if(bankReconciliationQueryModel==null){
			return null;
		}
		StringBuffer queryString = new StringBuffer("from  CashFlow where cashFlowChannelType=:cashFlowChannelType ");
		parameters.put("cashFlowChannelType",CashFlowChannelType.DirectBank);
		if (bankReconciliationQueryModel.getAccountSideEnum()!=null) {
			queryString.append(" AND accountSide=:accountSide");
			parameters.put("accountSide", bankReconciliationQueryModel.getAccountSideEnum());
		}
		if (bankReconciliationQueryModel.getAuditStatusEnum()!=null) {
			queryString.append(" AND auditStatus=:auditStatus");
			parameters.put("auditStatus", bankReconciliationQueryModel.getAuditStatusEnum());
			
		}
		if (is_where_condition(bankReconciliationQueryModel.getHostAccountNo())) {
			queryString.append(" AND hostAccountNo=:hostAccountNo");
			parameters.put("hostAccountNo", bankReconciliationQueryModel.getHostAccountNo());
		}
		if (is_where_condition(bankReconciliationQueryModel.getKey())) {
			queryString.append(" AND ((bankSequenceNo like :key) or (counterAccountNo like :key) or (counterAccountName like :key) or (remark like :key)");
			parameters.put("key", "%"+bankReconciliationQueryModel.getKey()+"%");
			if(bankReconciliationQueryModel.getAmountFromKey()!=null){
				queryString.append(" or (transactionAmount=:amount) ");
				parameters.put("amount", bankReconciliationQueryModel.getAmountFromKey());
			}
			queryString.append(" ) ");
		}
		if(bankReconciliationQueryModel.parseTradeStartTime()!=null){
			queryString.append(" AND transactionTime > :startTime ");
			parameters.put("startTime", bankReconciliationQueryModel.parseTradeStartTime());
		}
		if(bankReconciliationQueryModel.parseTradeEndTime()!=null){
			queryString.append(" AND transactionTime < :endTime ");
			parameters.put("endTime", bankReconciliationQueryModel.parseTradeEndTime());
		}
		
		queryString.append(" order by transactionTime desc");
		return queryString.toString();
	}
	
	private boolean is_where_condition(String address) {
		return !StringUtils.isEmpty(address);
	}

	
	@Override
	public List<CashFlow> getCashFlowList(BankReconciliationQueryModel bankReconciliationQueryModel, Page page) {
		Map<String,Object> parameters = new HashMap<String,Object>();
		String querySentence = getContractFilter(bankReconciliationQueryModel,parameters);
		if(page==null){
			return genericDaoSupport.searchForList(querySentence, parameters);
		}
		return genericDaoSupport.searchForList(querySentence, parameters,page.getBeginIndex(),page.getEveryPage());
	}

	@Override
	public int count(BankReconciliationQueryModel bankReconciliationQueryModel) {
		Map<String,Object> parameters = new HashMap<String,Object>();
		String querySentence = getContractFilter(bankReconciliationQueryModel,parameters);
		return genericDaoSupport.count(querySentence,parameters);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CashFlow> getCashFlowsBy(String hostAccountNo, Date begin, Date end, AccountSide accountSide) {
		StringBuffer queryString = new StringBuffer("FROM CashFlow WHERE 1=1 ");
		Map<String,Object> parameters = new HashMap<>();
		if(! StringUtils.isEmpty(hostAccountNo)){
			queryString.append(" AND (hostAccountNo = :hostAccountNo)");
			parameters.put("hostAccountNo", hostAccountNo);
		}
		if(begin != null){
			queryString.append(" AND (:startDate <= transactionTime)");
			parameters.put("startDate", begin);
		}
		if(end != null){
			queryString.append(" AND (transactionTime <= :endDate)");
			parameters.put("endDate", end);
		}
		if(accountSide != null){
			queryString.append(" AND (accountSide = :accountSide)");
			parameters.put("accountSide", accountSide);
		}
		return genericDaoSupport.searchForList(queryString.toString(), parameters);
	}

	@Override
	public List<CashFlow> listCashFlowBy(String tradeUuid, AccountSide accountSide, CashFlowChannelType cashFlowChannelType) {
		if(StringUtils.isEmpty(tradeUuid) || accountSide == null){
			return null;
		}
		Filter filter = new Filter();
		filter.addEquals("tradeUuid", tradeUuid);
		filter.addEquals("accountSide", accountSide);
		filter.addEquals("cashFlowChannelType", cashFlowChannelType);
		return this.list(CashFlow.class, filter);
		
	}

}

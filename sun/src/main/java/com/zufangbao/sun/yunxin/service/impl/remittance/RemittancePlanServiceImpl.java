package com.zufangbao.sun.yunxin.service.impl.remittance;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlan;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlanQueryModel;
import com.zufangbao.sun.yunxin.service.remittance.IRemittancePlanService;

@Service("RemittancePlanService")
public class RemittancePlanServiceImpl extends GenericServiceImpl<RemittancePlan> implements IRemittancePlanService{
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RemittancePlan> getRemittancePlanListBy(String remittanceApplicationUuid) {
		if(StringUtils.isEmpty(remittanceApplicationUuid)) {
			return null;
		}
		String hql = "FROM RemittancePlan WHERE remittanceApplicationUuid =:remittanceApplicationUuid";
		return this.genericDaoSupport.searchForList(hql, "remittanceApplicationUuid", remittanceApplicationUuid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RemittancePlan> queryRemittancePlan(RemittancePlanQueryModel queryModel, Page page) {
		if(CollectionUtils.isEmpty(queryModel.getFinancialContractUuidList())){
			return Collections.EMPTY_LIST;
		}
		StringBuffer querySb = new StringBuffer(100);
		Map<String, Object> parameters = new HashMap<>();
		genQuerySentence(querySb, parameters, queryModel);
		if (page == null) {
			return  this.genericDaoSupport.searchForList(querySb.toString(), parameters);
		} else {
			return this.genericDaoSupport.searchForList(querySb.toString(), parameters, page.getBeginIndex(), page.getEveryPage());
		}
	}
	
	private void genQuerySentence(StringBuffer querySb, Map<String, Object> paramters, RemittancePlanQueryModel queryModel){
		querySb.append("FROM RemittancePlan WHERE 1=1");
		
		List<String> financialContractIdList = queryModel.getFinancialContractUuidList();
		if(CollectionUtils.isEmpty(financialContractIdList)){
			financialContractIdList.add("");
		}
		querySb.append(" AND financialContractUuid IN (:financialContractIdList)");
		paramters.put("financialContractIdList", financialContractIdList);
	
		if(StringUtils.isNotBlank(queryModel.getOrderNo())){
			querySb.append(" AND remittancePlanUuid =:orderNo");
			paramters.put("orderNo", queryModel.getOrderNo());
		}
		
		if(StringUtils.isNotBlank(queryModel.getLoanContractNo())){
			querySb.append(" AND contractNo =:loanContractNo");
			paramters.put("loanContractNo", queryModel.getLoanContractNo());
		}
		
		if(queryModel.getStartDateValue() != null ){
			querySb.append(" AND plannedPaymentDate >=:startDate");
			paramters.put("startDate", queryModel.getStartDateValue());
		}
		
		if(queryModel.getEndDateValue() != null){
			querySb.append(" AND plannedPaymentDate <=:endDate");
			paramters.put("endDate", queryModel.getEndDateValue());
		}
		
		if(StringUtils.isNotBlank(queryModel.getPayerAccountHolder())){
			// querySb.append(" AND payerAccountHolder =:payerAccountHolder");
			// paramters.put("payerAccountHolder", queryModel.getPayerAccountHolder());
		}
		
		if(StringUtils.isNotBlank(queryModel.getCpBankAccountHolder())){
			querySb.append(" AND cpBankAccountHolder =:cpBankAccountHolder");
			paramters.put("cpBankAccountHolder", queryModel.getCpBankAccountHolder());
		}
		
		if(queryModel.getExecutionStatusEnum() != null){
			querySb.append(" AND executionStatus =:executionStatus");
			paramters.put("executionStatus", queryModel.getExecutionStatusEnum());
		}
		
		if(queryModel.getRemittanceTypeEnum() != null){
			querySb.append(" AND transactionType =:remittanceType");
			paramters.put("remittanceType", queryModel.getRemittanceTypeEnum());
		}
		
		querySb.append(queryModel.getOrderBySentence());
	}

	@Override
	public int queryRemittancePlanCount(RemittancePlanQueryModel queryModel) {
		if(CollectionUtils.isEmpty(queryModel.getFinancialContractUuidList())){
			return 0;
		}
		StringBuffer querySb = new StringBuffer("SELECT count(id) ");
		Map<String, Object> parameters = new HashMap<>();
		genQuerySentence(querySb, parameters, queryModel);
		return this.genericDaoSupport.searchForInt(querySb.toString(), parameters);
	}

	@SuppressWarnings("unchecked")
	@Override
	public RemittancePlan getUniqueRemittancePlanByUuid(String remittancePlanUuid) {
		if(StringUtils.isEmpty(remittancePlanUuid)) {
			return null;
		}
		String hql = "FROM RemittancePlan WHERE remittancePlanUuid =:remittancePlanUuid";
		List<RemittancePlan> remittancePlanList = this.genericDaoSupport.searchForList(hql, "remittancePlanUuid", remittancePlanUuid);
		if(CollectionUtils.isNotEmpty(remittancePlanList)) {
			return remittancePlanList.get(0);
		}
		return null;
	}
	
}

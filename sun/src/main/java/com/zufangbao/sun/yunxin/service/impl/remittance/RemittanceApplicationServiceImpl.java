package com.zufangbao.sun.yunxin.service.impl.remittance;

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
import com.zufangbao.sun.yunxin.entity.remittance.ExecutionStatus;
import com.zufangbao.sun.yunxin.entity.remittance.RemittanceApplication;
import com.zufangbao.sun.yunxin.entity.remittance.RemittanceApplicationQueryModel;
import com.zufangbao.sun.yunxin.service.remittance.IRemittanceApplicationService;

@Service("RemittanceApplicationService")
public class RemittanceApplicationServiceImpl extends
		GenericServiceImpl<RemittanceApplication> implements IRemittanceApplicationService {

	@Override
	public boolean existsRequestNo(String requestNo) {
		if (StringUtils.isEmpty(requestNo)) {
			return true;
		}
		String hql = "SELECT count(id) FROM RemittanceApplication WHERE requestNo =:requestNo";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("requestNo", requestNo);
		
		int count = this.genericDaoSupport.searchForInt(hql, params);
		return count > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RemittanceApplication> queryRemittanceApplication(RemittanceApplicationQueryModel queryModel, Page page) {
		if(CollectionUtils.isEmpty(queryModel.getExecutionStatusEnumList()) || CollectionUtils.isEmpty(queryModel.getFinancialContractIdList())) {
			return Collections.emptyList();
		}
		
		StringBuffer querySB = new StringBuffer(100);
		Map<String ,Object> paramters = new HashMap<String, Object>();
		
		genQuerySentence(queryModel, querySB, paramters);
		
		if (page == null) {
			return  this.genericDaoSupport.searchForList(querySB.toString(), paramters);
		} else {
			return this.genericDaoSupport.searchForList(querySB.toString(), paramters, page.getBeginIndex(), page.getEveryPage());
		}
	}
	
	private void genQuerySentence(RemittanceApplicationQueryModel queryModel, StringBuffer querySB, Map<String ,Object> paramters){
		
		querySB.append("FROM RemittanceApplication "
				+ " WHERE executionStatus IN (:executionStatusList) "
				+ " AND financialContractId IN (:financialContractIdList) ");
		
		paramters.put("executionStatusList",queryModel.getExecutionStatusEnumList());
		paramters.put("financialContractIdList", queryModel.getFinancialContractIdList());
		
		if(!StringUtils.isEmpty(queryModel.getLoanContractNo())){
			querySB.append(" AND contractNo = :contractNo");
			paramters.put("contractNo", queryModel.getLoanContractNo());
		}
		
		if(!StringUtils.isEmpty(queryModel.getOrderNo())){
			querySB.append(" AND remittanceApplicationUuid = :orderNo");
			paramters.put("orderNo",queryModel.getOrderNo());
		}
		
		if(queryModel.getStartDateValue()!= null){
			querySB.append(" AND createTime >= :startDate");
			paramters.put("startDate",queryModel.getStartDateValue() );
		}
		
		if(queryModel.getEndDateValue()!= null){
			querySB.append(" AND createTime <= :endDate");
			paramters.put("endDate",queryModel.getEndDateValue() );
		}
		
		querySB.append(queryModel.getOrderBySentence());
	}

	@Override
	public int queryRemittanceApplicationCount(RemittanceApplicationQueryModel queryModel) {
		if(CollectionUtils.isEmpty(queryModel.getExecutionStatusEnumList()) || CollectionUtils.isEmpty(queryModel.getFinancialContractIdList())) {
			return 0;
		}
		
		StringBuffer querySB = new StringBuffer("SELECT count(id) ");
		Map<String ,Object> paramters = new HashMap<String, Object>();
		
		genQuerySentence(queryModel, querySB, paramters);
		
		return this.genericDaoSupport.searchForInt(querySB.toString(), paramters);
	}

	@SuppressWarnings("unchecked")
	@Override
	public RemittanceApplication getUniqueRemittanceApplicationByUuid(String remittanceApplicationUuid) {
		if(StringUtils.isEmpty(remittanceApplicationUuid)) {
			return null;
		}
		String hql = "FROM RemittanceApplication WHERE remittanceApplicationUuid =:remittanceApplicationUuid";
		List<RemittanceApplication> remittanceApplicationList = this.genericDaoSupport.searchForList(hql, "remittanceApplicationUuid", remittanceApplicationUuid);
		if(CollectionUtils.isNotEmpty(remittanceApplicationList)) {
			return remittanceApplicationList.get(0);
		}
		return null;
	}

	@Override
	public List<RemittanceApplication> getRemittanceApplicationsBy(String contractUniqueId) {
		if(StringUtils.isBlank(contractUniqueId)){
			return null;
		}
		Filter filter = new Filter();
		filter.addEquals("contractUniqueId", contractUniqueId);
		return this.list(RemittanceApplication.class, filter);
	}

	@Override
	public int countRemittanceApplicationsBy(List<Long> financialContractIds,
			List<ExecutionStatus> executionStatusList, Date calculateDate) {
		if (CollectionUtils.isEmpty(financialContractIds)
				|| CollectionUtils.isEmpty(executionStatusList)
				|| calculateDate == null) {
			return 0;
		}
		String countHql = "SELECT COUNT(id) FROM RemittanceApplication "
				+ " WHERE financialContractId IN(:financialContractIds)"
				+ " AND executionStatus IN(:executionStatusList) "
				+ " AND TO_DAYS(createTime) = TO_DAYS(:calculateDate)";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("executionStatusList", executionStatusList);
		params.put("financialContractIds", financialContractIds);
		params.put("calculateDate", calculateDate);
		return this.genericDaoSupport.searchForInt(countHql, params);
	}
	
	@Override
	public boolean addPlanNotifyNumber(String remittanceApplicationUuid, int number) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("remittanceApplicationUuid", remittanceApplicationUuid);
		params.put("number", number);
		genericDaoSupport.executeSQL(
				"UPDATE t_remittance_application "
					+ "SET plan_notify_number = (plan_notify_number + :number) "
					+ "WHERE remittance_application_uuid =:remittanceApplicationUuid", params);
		return true;
	}

}

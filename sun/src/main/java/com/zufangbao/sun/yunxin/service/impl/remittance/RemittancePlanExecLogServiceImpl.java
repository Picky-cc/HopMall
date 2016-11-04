package com.zufangbao.sun.yunxin.service.impl.remittance;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.persistence.support.Order;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.yunxin.entity.remittance.AccountSide;
import com.zufangbao.sun.yunxin.entity.remittance.ExecutionStatus;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlanExecLog;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlanExecLogQueryModel;
import com.zufangbao.sun.yunxin.entity.remittance.ReverseStatus;
import com.zufangbao.sun.yunxin.service.remittance.IRemittancePlanExecLogService;

@Service("RemittancePlanExecLogService")
public class RemittancePlanExecLogServiceImpl extends GenericServiceImpl<RemittancePlanExecLog> implements IRemittancePlanExecLogService{

	@Override
	public List<RemittancePlanExecLog> getRemittancePlanExecLogListBy(String remittancePlanUuid) {
		if(StringUtils.isBlank(remittancePlanUuid)){
			return null;
		}
		Filter filter = new Filter();
		filter.addEquals("remittancePlanUuid", remittancePlanUuid);
		Order order = new Order("createTime", "DESC");
		return this.list(RemittancePlanExecLog.class, filter, order);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<RemittancePlanExecLog> getRemittacncePlanExecLogsBy(String financialContractUuid, Date beginDate, Date endDate){
		String queryString = "FROM RemittancePlanExecLog WHERE financialContractUuid =:financialContractUuid AND createTime >=:beginDate AND createTime <=:endDate";
		Map<String ,Object> paramters = new HashMap<String, Object>();
		paramters.put("financialContractUuid", financialContractUuid);
		paramters.put("beginDate", beginDate);
		paramters.put("endDate", endDate);
		return this.genericDaoSupport.searchForList(queryString, paramters);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RemittancePlanExecLog> queryRemittancePlanExecLog(RemittancePlanExecLogQueryModel queryModel, Page page) {
		
		if(CollectionUtils.isEmpty(queryModel.getFinancialContractUuidList()) || CollectionUtils.isEmpty(queryModel.getReverseStatusList())){
			return Collections.EMPTY_LIST;
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
	
	private void genQuerySentence(RemittancePlanExecLogQueryModel queryModel, StringBuffer querySB, Map<String ,Object> paramters){
		
		querySB.append("FROM RemittancePlanExecLog WHERE 1=1");
		
		List<String> financialContractIdList = queryModel.getFinancialContractUuidList();
		if(CollectionUtils.isEmpty(financialContractIdList)){
			financialContractIdList.add("");
		}
		querySB.append(" AND financialContractUuid IN (:financialContractIdList) AND reverseStatus IN (:reverseStatusList)");
		paramters.put("financialContractIdList", financialContractIdList);
		paramters.put("reverseStatusList", queryModel.getReverseStatusList());
		
		if(StringUtils.isNotBlank(queryModel.getExecLogUuid())){ // 代付单号
			querySB.append(" AND execReqNo =:execLogUuid");
			paramters.put("execLogUuid", queryModel.getExecLogUuid());
		}
		
		if(StringUtils.isNotBlank(queryModel.getPlanUuid())){ // 放款编号
			querySB.append(" AND remittancePlanUuid=:planUuid");
			paramters.put("planUuid", queryModel.getPlanUuid());
		}
		
		if(StringUtils.isNotBlank(queryModel.getPayerAccountHolder())){ // 付款方账户名
			querySB.append(" AND ( (transactionType=0 AND pgAccountName=:payerAccountHolder) OR (transactionType=1 AND cpBankAccountHolder=:payerAccountHolder) )");
			paramters.put("payerAccountHolder", queryModel.getPayerAccountHolder());
		}
		
		if(StringUtils.isNotBlank(queryModel.getCpBankAccountHolder())){ // 收款方账户名
			querySB.append(" AND ( (transactionType=0 AND cpBankAccountHolder=:cpBankAccountHolder) OR (transactionType=1 AND pgAccountName =:cpBankAccountHolder) )");
			paramters.put("cpBankAccountHolder", queryModel.getCpBankAccountHolder());
		}
		
		if(queryModel.getRemittanceChannelEnum()!=null){ // 放款通道
			querySB.append(" AND paymentGateway=:remittanceChannelEnum");
			paramters.put("remittanceChannelEnum", queryModel.getRemittanceChannelEnum());
		}
		
		if(queryModel.getExecutionStatusEnum()!=null){ // 代付状态
			querySB.append(" AND executionStatus=:executionStatusEnum");
			paramters.put("executionStatusEnum", queryModel.getExecutionStatusEnum());
		}
		
		if(queryModel.getStatusModifyTimeStartValue() != null){ // 状态变更时间起始
			querySB.append(" AND lastModifiedTime >= :lastModifiedTimeStart");
			paramters.put("lastModifiedTimeStart", queryModel.getStatusModifyTimeStartValue());
		}
		
		if(queryModel.getStatusModifyTimeEndValue() != null){ // 状态变更时间结束
			querySB.append(" AND lastModifiedTime <= :lastModifiedTimeEnd");
			paramters.put("lastModifiedTimeEnd", queryModel.getStatusModifyTimeEndValue());
		}
		
		querySB.append(queryModel.getOrderBySentence());  
	}

	@Override
	public int queryRemittacePlanExecLogCount(RemittancePlanExecLogQueryModel queryModel) {
		
		if(CollectionUtils.isEmpty(queryModel.getFinancialContractUuidList())|| CollectionUtils.isEmpty(queryModel.getReverseStatusList())){
			return 0;
		}
		
		StringBuffer querySB = new StringBuffer("SELECT count(id) ");
		Map<String ,Object> paramters = new HashMap<String, Object>();
		
		genQuerySentence(queryModel, querySB, paramters);
		
		return this.genericDaoSupport.searchForInt(querySB.toString(), paramters);
	}

	@Override
	public RemittancePlanExecLog getRemittancePlanExecLogBy(String execReqNo) {
		if(StringUtils.isBlank(execReqNo)){
			return null;
		}
		Filter filter = new Filter();
		filter.addEquals("execReqNo", execReqNo);
		List<RemittancePlanExecLog> execLogs = this.list(RemittancePlanExecLog.class, filter);
		if(CollectionUtils.isNotEmpty(execLogs)){
			return execLogs.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getRemittancePlanExecLogIdsForReverse() {
		String queryString = "SELECT id FROM RemittancePlanExecLog "
				+ " WHERE transactionType =:transactionType "
				+ " AND lastModifiedTime < :timeOff "
				+ " AND actualCreditCashFlowCheckNumber < planCreditCashFlowCheckNumber "
				+ " AND executionStatus=:executionStatus "
				+ " AND reverseStatus=:reverseStatus ";
		
		Calendar nowTime = Calendar.getInstance();
		nowTime.add(Calendar.MINUTE, -5); //5分钟前的时间
		Map<String ,Object> paramters = new HashMap<String, Object>();
		paramters.put("transactionType", AccountSide.CREDIT);
		paramters.put("timeOff", nowTime.getTime());
		paramters.put("executionStatus", ExecutionStatus.FAIL);
		paramters.put("reverseStatus",ReverseStatus.UNOCCUR);
		return this.genericDaoSupport.searchForList(queryString, paramters);
	}

	@Override
	public RemittancePlanExecLog getById(Long id) {
		if(id == null){
			return null;
		}
		return this.genericDaoSupport.get(RemittancePlanExecLog.class, id);
	}

	@Override
	public List<Long> getRemittancePlanExecLogIdsByReverseStatus(ReverseStatus reverseStatus) {
		if(reverseStatus == null){
			return null;
		}
		String queryString = "SELECT id FROM RemittancePlanExecLog WHERE reverseStatus =:reverseStatus";
		Map<String ,Object> paramters = new HashMap<String, Object>();
		paramters.put("reverseStatus", reverseStatus);
		return this.genericDaoSupport.searchForList(queryString, paramters);
	}

	@Override
	public String getLatestRemittancePlanExecLogExecReqNo(String remittancePlanUuid) {
		if(StringUtils.isEmpty(remittancePlanUuid)){
			return null;
		}
		List<String> execReqNos = this.genericDaoSupport
				.queryForSingleColumnList("SELECT exec_req_no "
						+ " FROM t_remittance_plan_exec_log "
						+ " WHERE remittance_plan_uuid =:remittancePlanUuid "
						+ " ORDER BY id DESC LIMIT 0,1", "remittancePlanUuid",
						remittancePlanUuid, String.class);
		if(CollectionUtils.isNotEmpty(execReqNos)) {
			return execReqNos.get(0);
		}
		return null;
	}
	
}

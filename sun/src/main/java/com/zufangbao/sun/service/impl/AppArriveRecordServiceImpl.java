/**
 * 
 */
package com.zufangbao.sun.service.impl;

import java.util.ArrayList;
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
import com.demo2do.core.utils.DateUtils;
import com.zufangbao.sun.Constant;
import com.zufangbao.sun.entity.account.AuditStatus;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.icbc.business.AppArriveRecord;
import com.zufangbao.sun.entity.icbc.business.BankSide;
import com.zufangbao.sun.entity.icbc.business.CashFlowChannelType;
import com.zufangbao.sun.service.AppArriveRecordService;
import com.zufangbao.sun.yunxin.entity.model.CashFlowConditionModel;

/**
 * @author zjm
 *
 */
@Service("appArriveRecordService")
public class AppArriveRecordServiceImpl extends
		GenericServiceImpl<AppArriveRecord> implements AppArriveRecordService {

	@Override
	public List<AppArriveRecord> getArriveRecordBySerialNo(String serialNo) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("serialNo", serialNo);
		
		return genericDaoSupport.searchForList(
				"FROM AppArriveRecord aar WHERE aar.serialNo = :serialNo",
				parameters);
	}

	@Override
	public List<AppArriveRecord> getDebitRecordsFromSometime(App app, Date date) {
		Map<String, Object> parms = new HashMap<String, Object>();
		
		//parms.put("create", RepaymentAuditStatus.CREATE);
		//parms.put("issuing", RepaymentAuditStatus.ISSUING);
		parms.put("credit", AppArriveRecord.CREDIT);
		parms.put("app", app);
		//parms.put("arriveRecordStatus", ArriveRecordStatus.Vetoed);
		
		Date startDate = DateUtils.addDays(date, -Constant.CASH_FLOW_BILL_MATCH_DAYS);
		parms.put("startDate", startDate);
		
		return (List<AppArriveRecord>) genericDaoSupport.searchForList("FROM AppArriveRecord aar WHERE aar.drcrf = :credit AND aar.app=:app AND time >:startDate", parms);
	}

	@Override
	public List<AppArriveRecord> getAlipayAppArriveRecords() {
		
		return getAppArriveRecordsBy(CashFlowChannelType.Alipay, null);
	}
	private List<AppArriveRecord> getAppArriveRecordsBy(CashFlowChannelType cashFlowType,BankSide bankSide){
		
		Filter filter = new Filter();
		
		filter.addEquals("cashFlowChannelType", cashFlowType);
		
		if(bankSide != null){
			
			filter.addEquals("drcrf", bankSide.getValue());
		}
		
		return list(AppArriveRecord.class, filter);
	}

	@Override
	public AppArriveRecord getArriveRecordByCashFlowUuid(String cashFlowUuid) {
		if(StringUtils.isEmpty(cashFlowUuid)){
			return null;
		}
		Filter filter = new Filter();
		filter.addEquals("cashFlowUid", cashFlowUuid);
		List<AppArriveRecord> appArriveRecordList = list(AppArriveRecord.class, filter);
		if(CollectionUtils.isEmpty(appArriveRecordList)){
			return null;
		}
		return appArriveRecordList.get(0);
	}

	@Override
	public List<AppArriveRecord> listAppArriveRecodBy(CashFlowConditionModel cashFlowConditionModel, int begin, int max) {
		List<AppArriveRecord> appArriveRecordList = new ArrayList<AppArriveRecord>();
		if (cashFlowConditionModel ==  null){
			return appArriveRecordList;
		}
		StringBuffer querySentence = new StringBuffer("From AppArriveRecord where 1=1 ");
		Map<String, Object> params = buildCashFlowQuerySentence(cashFlowConditionModel, querySentence);
		querySentence.append(" order by time DESC ");
		if (max == 0){
			return genericDaoSupport.searchForList(querySentence.toString(), params);
		} else {
			return genericDaoSupport.searchForList(querySentence.toString(), params, begin, max);
		}
		
	}

	@Override
	public AppArriveRecord getCashFlowBy(Long appId, String serialNo) {
		if(null == appId || null == serialNo) {
			return null;
		}
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("appId", appId);
		parms.put("serialNo", serialNo);
		List<AppArriveRecord> appArriveRecordList = genericDaoSupport.searchForList("FROM AppArriveRecord WHERE app.id =:appId AND serialNo =:serialNo", parms);
		if(appArriveRecordList.isEmpty()) {
			return null;
		}
		return appArriveRecordList.get(0);
	}
	
	@Override
	public int countAppArriveRecodeBy(CashFlowConditionModel cashFlowConditionModel) {
		
		if (cashFlowConditionModel ==  null){
			return 0;
		}
		StringBuffer querySentence = new StringBuffer("From AppArriveRecord where 1=1 ");
		Map<String, Object> params = buildCashFlowQuerySentence(cashFlowConditionModel, querySentence);
		
		return genericDaoSupport.count(querySentence.toString(), params);
	}

	private Map<String, Object> buildCashFlowQuerySentence(CashFlowConditionModel cashFlowConditionModel,
			StringBuffer querySentence) {
		Map<String,Object> params = new HashMap<String,Object>();
		
		if(!StringUtils.isEmpty(cashFlowConditionModel.getAppId())){
			querySentence.append(" AND app.appId =:appId "); 
			params.put("appId", cashFlowConditionModel.getAppId());
		}
		
		if(!StringUtils.isEmpty(cashFlowConditionModel.getAccountNo())){
			querySentence.append(" AND receiveAcNo =:accountNo ");
			params.put("accountNo", cashFlowConditionModel.getAccountNo());
		}
		
		if(!StringUtils.isEmpty(cashFlowConditionModel.getDrcrf())){
			querySentence.append(" AND drcrf =:drcrf ");
			params.put("drcrf", cashFlowConditionModel.getDrcrf()); 
		}
		
		if(cashFlowConditionModel.getAmount() != null){
			querySentence.append(" AND amount =:amount ");
			params.put("amount", cashFlowConditionModel.getAmount());
		}
		
		if(!StringUtils.isEmpty(cashFlowConditionModel.getQueryKeyWord())){

			querySentence.append(" AND (serialNo LIKE :keyWord OR payAcNo LIKE :keyWord OR payName LIKE :keyWord OR summary LIKE :keyWord) ");
			params.put("keyWord", "%"+cashFlowConditionModel.getQueryKeyWord()+"%");
		}
		
		if(cashFlowConditionModel.getAuditStatusEnum() != null){
			querySentence.append(" AND auditStatus =:auditStatus ");
			params.put("auditStatus", cashFlowConditionModel.getAuditStatusEnum());
		}
		
		if(cashFlowConditionModel.getStartDate() != null){
			querySentence.append(" AND Date(time) >= :startDate ");
			params.put("startDate", cashFlowConditionModel.getStartDate());
		}
		
		if(cashFlowConditionModel.getEndDate() != null){
			querySentence.append(" AND Date(time) <=:endDate ");
			params.put("endDate", cashFlowConditionModel.getEndDate());
		}
		
		if(StringUtils.isEmpty(cashFlowConditionModel.getFinancialAccountName())==false){
			
			querySentence.append(" AND ( first_account_name=:accountName OR  second_account_name=:accountName "
					+ " OR third_account_name=:accountName OR fourth_account_name=:accountName )");
			params.put("accountName", cashFlowConditionModel.getFinancialAccountName());
			
		}
		return params;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AppArriveRecord> getArriveRecordBy(Date tradeDate, BankSide bankSide, AuditStatus auditStatus) {
		if(tradeDate==null){
			return Collections.emptyList();
		}
		StringBuffer querySentence = new StringBuffer("From AppArriveRecord where Date(time)=:tradeDate ");
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("tradeDate", DateUtils.asDay(tradeDate));
		if(bankSide!=null){
			querySentence.append(" AND drcrf=:bankSideValue");
			params.put("bankSideValue", bankSide.getValue());
		}
		if(auditStatus!=null){
			querySentence.append(" AND auditStatus=:auditStatus");
			params.put("auditStatus", auditStatus);
		}
		return genericDaoSupport.searchForList(querySentence.toString(),params);
	}
//	@Override
//	public AppArriveRecord getCashFlowBy(String paymentAccountNo, String paymentName, BigDecimal amount) {
//		if(StringUtils.isEmpty(paymentAccountNo) || StringUtils.isEmpty(paymentName) ){
//			return null;
//		}
//		Filter filter = new Filter();
//		filter.addEquals("payAcNo", paymentAccountNo);
//		filter.addEquals("payName", paymentName);
//		filter.addEquals("amount", amount);
//		List<AppArriveRecord> appArriveRecordList = list(AppArriveRecord.class, filter);
//		if(CollectionUtils.isEmpty(appArriveRecordList)){
//			return null;
//		}
//		return appArriveRecordList.get(0);
//	}
	
}

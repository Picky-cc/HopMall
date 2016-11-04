/**
 * 
 */
package com.zufangbao.sun.service;

import java.util.Date;
import java.util.List;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.entity.account.AuditStatus;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.icbc.business.AppArriveRecord;
import com.zufangbao.sun.entity.icbc.business.BankSide;
import com.zufangbao.sun.yunxin.entity.model.CashFlowConditionModel;

/**
 * @author zjm
 *
 */
public interface AppArriveRecordService extends GenericService<AppArriveRecord> {

	List<AppArriveRecord> getArriveRecordBySerialNo(String serialNo);
	
	AppArriveRecord getCashFlowBy(Long appId, String serialNo);
	
	List<AppArriveRecord> getDebitRecordsFromSometime(App app, Date date);
	
	List<AppArriveRecord> getAlipayAppArriveRecords();
	
	public AppArriveRecord getArriveRecordByCashFlowUuid(String cashFlowUuid);
	
	public List<AppArriveRecord> listAppArriveRecodBy(CashFlowConditionModel cashFlowConditionModel, int begin, int max);
	
	public int countAppArriveRecodeBy(CashFlowConditionModel cashFlowConditionModel);
	
	public List<AppArriveRecord> getArriveRecordBy(Date tradeDate, BankSide bankSide, AuditStatus auditStatus);
}

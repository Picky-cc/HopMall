package com.zufangbao.earth.yunxin.handler.remittance.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo2do.core.web.resolver.Page;
import com.zufangbao.earth.yunxin.handler.remittance.RemittancePlanExecLogHandler;
import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.entity.icbc.business.CashFlowChannelType;
import com.zufangbao.sun.ledgerbook.AccountSide;
import com.zufangbao.sun.service.CashFlowService;
import com.zufangbao.sun.utils.AmountUtil;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlanExecLog;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlanExecLogExportModel;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlanExecLogQueryModel;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlanExecLogShowModel;
import com.zufangbao.sun.yunxin.entity.remittance.RemittanceRefundBill;
import com.zufangbao.sun.yunxin.entity.remittance.ReverseStatus;
import com.zufangbao.sun.yunxin.service.remittance.IRemittancePlanExecLogService;
import com.zufangbao.sun.yunxin.service.remittance.IRemittanceRefundBillService;


@Component("remittancePlanExecLogHandler")
public class RemittancePlanExecLogHandlerImpl implements RemittancePlanExecLogHandler{

	@Autowired
	private IRemittancePlanExecLogService remittancePlanExecLogService;
	
	@Autowired
	private IRemittanceRefundBillService remittanceRefundBillService;
	
	@Autowired
	private CashFlowService cashFlowService;
	
	@Override
	public List<RemittancePlanExecLogShowModel> queryShowModelList(RemittancePlanExecLogQueryModel queryModel, Page page) {
		List<RemittancePlanExecLog> remittancePlanExecLogs = remittancePlanExecLogService.queryRemittancePlanExecLog(queryModel, page);
		List<RemittancePlanExecLogShowModel> showModels = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(remittancePlanExecLogs)){
			showModels = remittancePlanExecLogs.stream().map(a-> new RemittancePlanExecLogShowModel(a)).collect(Collectors.toList());
		}
		return showModels;
	}
	
	@Override
	public List<RemittancePlanExecLogExportModel> extractExecLogExportModel(String financialContractUuid, Date beginDate, Date endDate){
		List<RemittancePlanExecLog> remittancePlanExecLogs = remittancePlanExecLogService.getRemittacncePlanExecLogsBy(financialContractUuid, beginDate, endDate);
		List<RemittancePlanExecLogExportModel> exportModels = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(remittancePlanExecLogs)){
			exportModels = remittancePlanExecLogs.stream().map(a->new RemittancePlanExecLogExportModel(a)).collect(Collectors.toList());
		}
		return exportModels;
	}

	@Override
	public void confirmWhetherExistCreditCashFlow(Long remittancePlanExecLogId) {
		RemittancePlanExecLog planExecLog = remittancePlanExecLogService.getById(remittancePlanExecLogId);
		if(planExecLog == null){
			return;
		}
		List<CashFlow> cashFlows = cashFlowService.listCashFlowBy(planExecLog.getExecRspNo(), AccountSide.CREDIT, CashFlowChannelType.DirectBank);
		if(cashFlows != null && cashFlows.size() == 1){
			CashFlow cf = cashFlows.get(0);
			if (planExecLog.matchInfoWithCreditCashFlow(cf)) {
				planExecLog.setReverseStatus(ReverseStatus.NOTREVERSE);
				planExecLog.setCreditCashFlowUuid(cf.getCashFlowUuid());
			}
		}
		planExecLog.setActualCreditCashFlowCheckNumber(planExecLog.getActualCreditCashFlowCheckNumber() + 1);
		remittancePlanExecLogService.update(planExecLog);
	}
	
	@Override
	public void confirmWhetherExistDebitCashFlow(Long remittancePlanExecLogId) {
		RemittancePlanExecLog planExecLog = remittancePlanExecLogService.getById(remittancePlanExecLogId);
		if(planExecLog == null){
			return;
		}
		List<CashFlow> cashFlows = cashFlowService.listCashFlowBy(planExecLog.getExecRspNo(), AccountSide.DEBIT, CashFlowChannelType.DirectBank);
		if(cashFlows != null && cashFlows.size() == 1){
			CashFlow cf = cashFlows.get(0);
			if (cf != null && AmountUtil.equals(cf.getTransactionAmount(), planExecLog.getPlannedAmount())) {
				planExecLog.setReverseStatus(ReverseStatus.REVERSED);
				planExecLog.setDebitCashFlowUuid(cf.getCashFlowUuid());
				remittancePlanExecLogService.update(planExecLog);
				RemittanceRefundBill refundBill = new RemittanceRefundBill(cf, planExecLog);
				remittanceRefundBillService.save(refundBill);
			}
		}
	}
	
}
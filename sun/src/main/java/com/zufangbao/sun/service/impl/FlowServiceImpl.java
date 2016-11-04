package com.zufangbao.sun.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo2do.core.entity.Result;
import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.sun.Constant;
import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.directbank.USBKey;
import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.entity.icbc.business.CashFlowQueryModel;
import com.zufangbao.sun.entity.icbc.business.FlowRecord;
import com.zufangbao.sun.handler.IDirectBankHandler;
import com.zufangbao.sun.ledgerbook.AccountSide;
import com.zufangbao.sun.service.CashFlowService;
import com.zufangbao.sun.service.FlowService;
import com.zufangbao.sun.service.USBKeyService;
import com.zufangbao.sun.utils.DirectBankHandlerFactory;

@Service("flowService")
public class FlowServiceImpl implements FlowService {

	@Autowired
	private USBKeyService usbKeyService;
	@Autowired
	private CashFlowService cashFlowService;
	
	@Override
	public Result getTodayFlowsByAccount(Account account) {
		
		USBKey usbKey = usbKeyService.getUSBKeyByAccount(account);
		if(usbKey == null){
			return new Result().fail().message("该账号未登记USBKey");
		}
		IDirectBankHandler directBankHandler = DirectBankHandlerFactory.newInstance(usbKey.getBankCode());
		return directBankHandler.queryTodayCashFlow(account, usbKey);
	}

	@Override
	public Result getHistoryFlowsByAccount(Account account,
			String startDate, String endDate) {
		USBKey usbKey = usbKeyService.getUSBKeyByAccount(account);
		if(null == usbKey){
			return new Result().fail().message("该账号未登记USBKey");
		}
		IDirectBankHandler directBankHandler = DirectBankHandlerFactory.newInstance(usbKey.getBankCode());
		return directBankHandler.queryHistoryCashFlow(account, startDate, endDate, usbKey);
	}

	@Override
	public List<FlowRecord> getHistoryFlowsByAccountFromDB(Account account,
			Date startDate, Date endDate) {
		CashFlowQueryModel cashFlowQueryModel = new CashFlowQueryModel(startDate, endDate, account.getAccountNo());
		List<CashFlow> cashFlowList = cashFlowService.searchCashFlowList(cashFlowQueryModel);
		List<FlowRecord> flowRecordList = new ArrayList<FlowRecord>();
		for(CashFlow cashFlow : cashFlowList) {
			Map<String, String> bankInfo = JsonUtils.parse(cashFlow.getCounterBankInfo(), Map.class);
			String bankName = bankInfo == null ? "" : bankInfo.getOrDefault("bankName", StringUtils.EMPTY);
			if(AccountSide.CREDIT.equals(cashFlow.getAccountSide())) {
				FlowRecord flowRecord = new FlowRecord(Constant.DEBIT, cashFlow.getTransactionVoucherNo(), cashFlow.getTransactionAmount(), null, bankName, cashFlow.getCounterAccountNo(), cashFlow.getCounterAccountName(), cashFlow.getRemark(), "", cashFlow.getOtherRemark(), cashFlow.getTransactionTime(), cashFlow.getBalance(), cashFlow.getBankSequenceNo(), cashFlow.getTransactionVoucherNo());
				flowRecordList.add(flowRecord);
			}else {
				FlowRecord flowRecord = new FlowRecord(Constant.CREDIT, cashFlow.getTransactionVoucherNo(), null, cashFlow.getTransactionAmount(), bankName, cashFlow.getCounterAccountNo(), cashFlow.getCounterAccountName(), cashFlow.getRemark(), "", cashFlow.getOtherRemark(), cashFlow.getTransactionTime(), cashFlow.getBalance(), cashFlow.getBankSequenceNo(), cashFlow.getTransactionVoucherNo());
				flowRecordList.add(flowRecord);
			}
		}
		return flowRecordList;
	}
	

	@Override
	public Result getBalanceByAccount(Account account) {
		USBKey usbKey = usbKeyService.getUSBKeyByAccount(account);
		if(usbKey == null){
			return new Result().fail().message("该账号未登记USBKey");
		}
		IDirectBankHandler directBankHandler = DirectBankHandlerFactory.newInstance(usbKey.getBankCode());
		return directBankHandler.queryAccountBalance(account, usbKey);
	}


}

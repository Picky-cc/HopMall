package com.zufangbao.sun.handler.impl;

import org.springframework.stereotype.Component;

import com.demo2do.core.entity.Result;
import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.directbank.USBKey;
import com.zufangbao.sun.handler.IDirectBankHandler;

@Component("DefaultDirectBankHandler")
public class DefaultDirectBankHandlerImpl implements IDirectBankHandler {

	private final static Result FAIL_RESULT = new Result().fail().message("操作失败，不支持的银行！");

	@Override
	public Result queryTodayCashFlow(Account account, USBKey usbKey) {
		return FAIL_RESULT;
	}

	@Override
	public Result queryHistoryCashFlow(Account account, String startDate,
			String endDate, USBKey usbKey) {
		return FAIL_RESULT;
	}

	@Override
	public Result queryAccountBalance(Account account, USBKey usbKey) {
		return FAIL_RESULT;
	}

	@Override
	public void scanBankCashFlow(Account account, App app, USBKey usbKey) {
		return;
	}

	@Override
	public Result recordUnsavedCashFlow(String startDate, String endDate,
			Account account, App app, USBKey usbKey) {
		return FAIL_RESULT;
	}

}

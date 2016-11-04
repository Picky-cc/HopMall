package com.zufangbao.sun.service;

import java.util.List;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.entity.account.Account;

public interface AccountService extends GenericService<Account> {
	public List<Account> listAccountWithScanCashFlowSwitchOn();
	public List<Account> listAccountWithUsbKey();
	public Account getAccountByAccountNo(String accountNo);
	
}

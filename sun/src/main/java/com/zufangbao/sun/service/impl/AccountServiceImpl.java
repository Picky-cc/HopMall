package com.zufangbao.sun.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.service.AccountService;

@Service("accountService")
public class AccountServiceImpl extends GenericServiceImpl<Account> implements AccountService {

	@Override
	public List<Account> listAccountWithScanCashFlowSwitchOn() {
		Filter filter = new Filter();
		filter.addEquals("scanCashFlowSwitch", true);
		return this.list(Account.class, filter);
	}

	@Override
	public List<Account> listAccountWithUsbKey() {
		Filter filter = new Filter();
		filter.addEquals("usbKeyConfigured", true);
		return this.list(Account.class, filter);
	}

	
	@Override
	public Account getAccountByAccountNo(String accountNo) {
		Filter filter = new Filter();
		filter.addEquals("accountNo", accountNo);
		List<Account> accountList = this.list(Account.class, filter);
		if(CollectionUtils.isEmpty(accountList)){
			return null;
		}
		return accountList.get(0);
	}
	
}

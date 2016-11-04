package com.zufangbao.sun.service;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.entity.account.AppAccount;
import com.zufangbao.sun.entity.account.AppAccountActiveStatus;

public interface AppAccountService extends GenericService<AppAccount>{
	public AppAccount getAppAccountByAccountNo(String accountNo,AppAccountActiveStatus activeStatus);
}
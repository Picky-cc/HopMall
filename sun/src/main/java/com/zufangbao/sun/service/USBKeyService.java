package com.zufangbao.sun.service;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.directbank.USBKey;

/**
 * 
 * @author zjm
 *
 */
public interface USBKeyService extends GenericService<USBKey> {

	USBKey getUSBKeyByUUID(String uuid);
	
	USBKey getUSBKeyByAccount(Account account);

	USBKey getUSBKeyByBankCode(String bankCode);
}

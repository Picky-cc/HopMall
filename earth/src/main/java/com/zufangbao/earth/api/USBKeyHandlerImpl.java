package com.zufangbao.earth.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.earth.api.exception.UnknownUkeyException;
import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.directbank.USBKey;
import com.zufangbao.sun.service.AccountService;
import com.zufangbao.sun.service.USBKeyService;

/**
 * 
 * @author zjm
 *
 */
@Component("usbKeyHandler")
public class USBKeyHandlerImpl implements USBKeyHandler {
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private USBKeyAccountRelationService usbKeyAccountRelationService;
	@Autowired
	private USBKeyService usbKeyService;

	@Override
	public USBKey getUSBKeyByPayerAccount(String payerAccountNo, GatewayType gateWayType) throws UnknownUkeyException {
		
		Account payerAccount = accountService.getAccountByAccountNo(payerAccountNo);
		
		if(null == payerAccount) {
			throw new UnknownUkeyException(BankCorpMsgSpec.MSG_PAYERACCOUNT_EMPTY_ERROR);
		}
		
		String usbKeyUuid = usbKeyAccountRelationService.getUSBKeyBy(payerAccount.getUuid(), gateWayType);
		
		return usbKeyService.getUSBKeyByUUID(usbKeyUuid);
	}

	
}

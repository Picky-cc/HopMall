package com.zufangbao.sun.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.directbank.USBKey;
import com.zufangbao.sun.service.USBKeyService;

/**
 * 
 * @author zjm
 *
 */
@Service("USBKeyService")
public class USBKeyServiceImpl extends GenericServiceImpl<USBKey> implements
		USBKeyService {

	@Override
	public USBKey getUSBKeyByUUID(String uuid) {
		List<USBKey> keyList = genericDaoSupport.searchForList(
				"FROM USBKey WHERE uuid =:uuid", "uuid", uuid);

		if (CollectionUtils.isEmpty(keyList)) {
			return null;
		}
		return keyList.get(0);
	}

	@Override
	public USBKey getUSBKeyByBankCode(String bankCode) {
		List<USBKey> keyList = genericDaoSupport.searchForList(
				"FROM USBKey WHERE bankCode =:bankCode", "bankCode", bankCode);

		if (keyList.isEmpty()) {
			return null;
		}
		return keyList.get(0);
	}

	@Override
	public USBKey getUSBKeyByAccount(Account account) {
		if(account==null || !account.isUsbKeyConfigured()){
			return null;
		}
		return getUSBKeyByUUID(account.getUsbUuid());
	}
}

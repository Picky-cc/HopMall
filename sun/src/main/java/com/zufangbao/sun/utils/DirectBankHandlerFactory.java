package com.zufangbao.sun.utils;

import java.util.HashMap;
import java.util.Map;

import com.zufangbao.sun.Constant.BankCorpEps;
import com.zufangbao.sun.handler.IDirectBankHandler;

public class DirectBankHandlerFactory {
	
	private final static Map<String, String> bankCodeDirectBankHandlerBeanNameMapper = new HashMap<String, String>(){{
//		put(BankCorpEps.ICBC_CODE, "ICBCDirectBankHandler");
		put(BankCorpEps.CMB_CODE, "CMBDirectBankHandler");
//		put(BankCorpEps.PAB_CODE, "PABDirectBankHandler");
	}};

	public static <T extends IDirectBankHandler> T newInstance(String bankCode) {
		String beanName = bankCodeDirectBankHandlerBeanNameMapper.getOrDefault(bankCode, "DefaultDirectBankHandler");
		return SpringContextUtil.getBean(beanName);
	}
	
}

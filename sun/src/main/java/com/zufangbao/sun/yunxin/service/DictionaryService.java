package com.zufangbao.sun.yunxin.service;


import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.yunxin.entity.Dictionary;
import com.zufangbao.sun.yunxin.exception.DictionaryNotExsitException;

/**
 * 
 * @author louguanyang
 *
 */
public interface DictionaryService extends GenericService<Dictionary>{

	Dictionary getDictionaryByCode(String code) throws DictionaryNotExsitException;

	boolean getSmsAllowSendFlag();

	void updateAllowedSendStatus(boolean allowedSendFlag);

	String getPlatformPrivateKey();
}

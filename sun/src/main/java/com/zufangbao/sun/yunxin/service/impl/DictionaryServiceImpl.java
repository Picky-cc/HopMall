package com.zufangbao.sun.yunxin.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.yunxin.entity.Dictionary;
import com.zufangbao.sun.yunxin.entity.DictionaryCode;
import com.zufangbao.sun.yunxin.exception.DictionaryNotExsitException;
import com.zufangbao.sun.yunxin.service.DictionaryService;

/**
 * 
 * @author louguanyang
 *
 */
@Service("dictionaryService")
public class DictionaryServiceImpl extends GenericServiceImpl<Dictionary> implements DictionaryService{

	private static final Log logger = LogFactory.getLog(DictionaryServiceImpl.class);
	
	@Override
	public Dictionary getDictionaryByCode(String code) throws DictionaryNotExsitException {
		String queryString = "From Dictionary where code =:code";
		List<Dictionary> result = this.genericDaoSupport.searchForList(queryString, "code", code);
		if(CollectionUtils.isEmpty(result)) {
			throw new DictionaryNotExsitException();
		}
		return result.get(0);
	}

	@Override
	public boolean getSmsAllowSendFlag() {
		try {
			Dictionary allowedSendStatusDictionary = getDictionaryByCode(DictionaryCode.SMS_ALLOW_SEND_FLAG.getCode());
			return new Boolean(allowedSendStatusDictionary.getContent());
		} catch (DictionaryNotExsitException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void updateAllowedSendStatus(boolean allowedSendFlag) {
		try {
			Dictionary allowedSendStatusDictionary = getDictionaryByCode(DictionaryCode.SMS_ALLOW_SEND_FLAG.getCode());
			allowedSendStatusDictionary.setContent((!allowedSendFlag) + "");
			saveOrUpdate(allowedSendStatusDictionary);
		} catch (DictionaryNotExsitException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public String getPlatformPrivateKey() {
		try {
			Dictionary priKeyDictionary = getDictionaryByCode(DictionaryCode.PLATFORM_PRI_KEY.getCode());
			return priKeyDictionary.getContent();
		} catch (DictionaryNotExsitException e) {
			e.printStackTrace();
			logger.error("平台私钥未配置！", e);
			return null;
		}
	}

	
}

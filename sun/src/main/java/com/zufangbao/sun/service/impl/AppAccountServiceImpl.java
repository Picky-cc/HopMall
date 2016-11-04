/**
 * 
 */
package com.zufangbao.sun.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.entity.account.AppAccount;
import com.zufangbao.sun.entity.account.AppAccountActiveStatus;
import com.zufangbao.sun.service.AppAccountService;

/**
 * @author 
 *
 */
@Service("appAccountService")
public class AppAccountServiceImpl extends GenericServiceImpl<AppAccount> implements AppAccountService {

	@Override
	public AppAccount getAppAccountByAccountNo(String accountNo, AppAccountActiveStatus activeStatus) {
		Filter filter = new Filter();
		filter.addEquals("accountNo", accountNo);
		filter.addEquals("appAccountActiveStatus", activeStatus);
		List<AppAccount> appAccountList = this.list(AppAccount.class, filter);
		if(CollectionUtils.isEmpty(appAccountList)){
			return null;
		}
		return appAccountList.get(0);
	}
	
	
}

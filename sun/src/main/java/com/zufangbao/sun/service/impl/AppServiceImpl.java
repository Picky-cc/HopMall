/**
 * 
 */
package com.zufangbao.sun.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.service.AppService;

/**
 * @author lute
 *
 */
@Service("appService")
public class AppServiceImpl extends GenericServiceImpl<App> implements AppService {
	
	/* (non-Javadoc)
	 * @see com.zufangbao.sun.service.AppService#getApp(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public App getApp(String appId) {
		
		List<App> apps = genericDaoSupport.searchForList("FROM App app WHERE app.appId = :appId", "appId", appId);
		
		return CollectionUtils.isNotEmpty(apps) ? apps.get(0) : null;
	}
	
	//通过Companyid 查找唯一对应的 appid
	@Override
	public String getAppidByCompanyid(Long companyid) {
		List<String> appid = genericDaoSupport.queryForSingleColumnList("select app_id FROM App  WHERE company_id = :companyid", "companyid", companyid ,String.class);
		
		return CollectionUtils.isNotEmpty(appid) ? appid.get(0) : StringUtils.EMPTY;
	}

	@Override
	public App getAppByCompanyId(Long companyId) {
		
		List<App> apps = genericDaoSupport.searchForList("FROM App WHERE company.id = :companyId", "companyId", companyId);
		return CollectionUtils.isNotEmpty(apps) ? apps.get(0) : null;
		
	}

	@Override
	public boolean editAddressee(Long appId,String addressee) {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.putIfAbsent("appId", appId);
			parameters.putIfAbsent("addressee", addressee);
			
			genericDaoSupport.executeHQL("update App set addressee =:addressee where id =:appId", parameters);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}

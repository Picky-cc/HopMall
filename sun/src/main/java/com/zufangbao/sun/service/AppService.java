/**
 * 
 */
package com.zufangbao.sun.service;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.entity.company.corp.App;

/**
 * @author lute
 *
 */
	
public interface AppService extends GenericService<App> {
	
	/**
	 * Get app according to appId
	 * 
	 * @param appId
	 * @return
	 */
	public App getApp(String appId);
	
	/**
	 * get Appid By CompanyId
	 * @return
	 */
	public String getAppidByCompanyid(Long companyid);
	public App getAppByCompanyId(Long companyId);

	public boolean editAddressee(Long appId,String addressee);
	
	
}
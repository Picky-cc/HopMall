/**
 * 
 */
package com.zufangbao.sun.service;

import java.util.List;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.entity.company.Company;
import com.zufangbao.sun.entity.company.corp.App;


/**
 * @author lute
 *
 */

public interface CompanyService extends GenericService<Company> {

	public Company getCompanyBy(String appId);
	
	public List<Long> getAllCompanyId();
	
	public List<Long> extractCompanyId(List<Company> companyList);
	
	public String getCompanyAlias(Long companyId);
	
	public Long getCompanyIdBy(String appId);
	
	public Long getCompanyIdBy(App app);
	
	public Long getYunxinCompanyId();
}

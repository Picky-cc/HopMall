/**
 * 
 */
package com.zufangbao.sun.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.Constant;
import com.zufangbao.sun.entity.company.Company;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.service.AppService;
import com.zufangbao.sun.service.CompanyService;
import com.zufangbao.sun.service.FinancialContractService;

/**
 * @author lute
 *
 */

@Service("companyService")
public class CompanyServiceImpl extends GenericServiceImpl<Company> implements CompanyService {

	@Autowired
	private AppService appService;
	
	@Autowired
	private FinancialContractService financialContractService;
	
	@Override
	public Company getCompanyBy(String appId) {
		App app =  appService.getApp(appId);
		if (app == null){
			return null;
		}
		return app.getCompany();
	}
	@Override
	public String getCompanyAlias(Long companyId) {
		if (companyId == null){
			return StringUtils.EMPTY;
		}
		Company company = genericDaoSupport.load(Company.class, companyId);
		if (company==null){
			return StringUtils.EMPTY;
		}
		return company.getShortName();
	}
	@Override
	public Long getCompanyIdBy(String appId) {
		if (StringUtils.isEmpty(appId)){
			return (long)Constant.ErrorData.ERROR_NO_COMPANY;
		}
		Company company = getCompanyBy(appId);
		if (company==null){
			return (long)Constant.ErrorData.ERROR_NO_COMPANY;
		}
		return company.getId();
	}
	@Override
	public Long getCompanyIdBy(App app) {
		if(app == null){
			return (long)Constant.ErrorData.ERROR_NO_APP;
		}
		return getCompanyIdBy(app.getAppId());
	}
	@Override
	public List<Long> getAllCompanyId() {
		List<Company> companyList = this.genericDaoSupport.searchForList("From Company");
		return extractCompanyId(companyList);
	}
	@Override
	public List<Long> extractCompanyId(List<Company> companyList) {
		if(CollectionUtils.isEmpty(companyList)){
			return Collections.emptyList();
		}
		List<Long> companyIdList = new ArrayList<Long>();
		for (Company company : companyList) {
			if(company==null || company.getId()==null){
				continue;
			}
			companyIdList.add(company.getId());
		}
		return companyIdList;
	}
	@Override
	public Long getYunxinCompanyId() {
		List<FinancialContract> financialContractList = financialContractService.list(FinancialContract.class, new Filter());
		if(CollectionUtils.isEmpty(financialContractList)){
			return null;
		}
		FinancialContract financialContract =  financialContractList.get(0);
		if(financialContract==null){
			return null;
		}
		return financialContract.getCompany().getId();
	}

	

}

package com.zufangbao.sun.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.entity.company.Company;
import com.zufangbao.sun.entity.company.finance.FinanceCompany;
import com.zufangbao.sun.service.FinanceCompanyService;

@Service("financeCompanyService")
public class FinanceCompanyServiceImpl extends GenericServiceImpl<FinanceCompany> implements FinanceCompanyService {

	@Override
	public Long getCompanyIdOfOneFinanceCompany() {
		List<FinanceCompany> financeCompanyList = this.list(FinanceCompany.class, new Filter());
		if(CollectionUtils.isEmpty(financeCompanyList)){
			return null;
		}
		Company company = financeCompanyList.get(0).getCompany();
		if(company==null){
			return null;
		}
		return company.getId();
	}

}

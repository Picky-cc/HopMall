package com.zufangbao.sun.service;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.entity.company.finance.FinanceCompany;

public interface FinanceCompanyService extends GenericService<FinanceCompany>{
	public Long getCompanyIdOfOneFinanceCompany();
}

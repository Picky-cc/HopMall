package com.zufangbao.sun.service.impl;

import org.springframework.stereotype.Service;

import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.service.BankReconciliationService;

@Service("bankReconciliationService")
public class BankReconciliationServiceImpl extends
	GenericServiceImpl<CashFlow> implements BankReconciliationService{

	
}

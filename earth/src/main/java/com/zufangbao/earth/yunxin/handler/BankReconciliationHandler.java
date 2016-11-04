package com.zufangbao.earth.yunxin.handler;

import java.util.Map;

import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.yunxin.entity.model.BankReconciliationQueryModel;

public interface BankReconciliationHandler {

	public Map<String, Object> query(BankReconciliationQueryModel bankReconciliationQueryModel, Page page);
}

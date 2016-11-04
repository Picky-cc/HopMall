package com.zufangbao.earth.yunxin.handler.remittance;

import java.util.Date;
import java.util.List;

import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlanExecLogExportModel;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlanExecLogQueryModel;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlanExecLogShowModel;

public interface RemittancePlanExecLogHandler {
	
	public List<RemittancePlanExecLogShowModel> queryShowModelList(RemittancePlanExecLogQueryModel queryModel,Page page);
	
	public List<RemittancePlanExecLogExportModel> extractExecLogExportModel(String financialContractUuid, Date beginDate, Date endDate);
	
	public void confirmWhetherExistCreditCashFlow(Long remittancePlanExecLogId);
	
	public void confirmWhetherExistDebitCashFlow(Long remittancePlanExecLogId);

}
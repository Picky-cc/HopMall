package com.zufangbao.sun.yunxin.service.remittance;

import java.util.List;

import com.demo2do.core.service.GenericService;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlan;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlanQueryModel;

public interface IRemittancePlanService extends GenericService<RemittancePlan> {
	
	public RemittancePlan getUniqueRemittancePlanByUuid(String remittancePlanUuid);
	
	public List<RemittancePlan> getRemittancePlanListBy(String remittanceApplicationUuid);
	
	public List<RemittancePlan> queryRemittancePlan(RemittancePlanQueryModel queryModel, Page page);
	
	public int queryRemittancePlanCount(RemittancePlanQueryModel queryModel);

}

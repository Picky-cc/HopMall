package com.zufangbao.sun.yunxin.service.remittance;

import java.util.Date;
import java.util.List;

import com.demo2do.core.service.GenericService;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlanExecLog;
import com.zufangbao.sun.yunxin.entity.remittance.RemittancePlanExecLogQueryModel;
import com.zufangbao.sun.yunxin.entity.remittance.ReverseStatus;

public interface IRemittancePlanExecLogService extends GenericService<RemittancePlanExecLog> {
	
	public List<RemittancePlanExecLog> getRemittancePlanExecLogListBy(String remittancePlanUuid);
	
	public RemittancePlanExecLog getById(Long id);
	
	public List<RemittancePlanExecLog> queryRemittancePlanExecLog(RemittancePlanExecLogQueryModel RemittancePlanExecLogQueryModel, Page page);

	public int queryRemittacePlanExecLogCount(RemittancePlanExecLogQueryModel queryModel);
	
	public RemittancePlanExecLog getRemittancePlanExecLogBy(String execReqNo);
	
	public List<RemittancePlanExecLog> getRemittacncePlanExecLogsBy(String financialContractUuid, Date beginDate, Date endDate);
	
	public List<Long> getRemittancePlanExecLogIdsForReverse();
	
	public List<Long> getRemittancePlanExecLogIdsByReverseStatus(ReverseStatus reverseStatus);
	
	public String getLatestRemittancePlanExecLogExecReqNo(String remittancePlanUuid);
}

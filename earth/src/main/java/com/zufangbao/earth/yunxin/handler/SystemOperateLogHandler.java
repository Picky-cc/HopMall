package com.zufangbao.earth.yunxin.handler;

import java.util.List;
import java.util.Map;

import com.demo2do.core.web.resolver.Page;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.sun.entity.icbc.business.AppArriveRecord;
import com.zufangbao.sun.yunxin.log.SystemOperateLogRequestParam;
import com.zufangbao.sun.yunxin.log.SystemOperateLogVO;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.VoucherOperationInfo;

public interface SystemOperateLogHandler {

	public void generateSystemOperateLog(SystemOperateLogRequestParam param)
			throws Exception;

	public void generateAssociateSystemLog(Principal principal, String ip,
			String offlineBillUuid, Map<String, Object> map) throws Exception;

	public void generateCashFLowAuditSystemLog(VoucherOperationInfo voucherOperationInfo, AppArriveRecord appArriveRecord)throws Exception;
	
	public List<SystemOperateLogVO> getSystemOperateLogVOListBy(
			String objectUuid, Page page);

	public List<SystemOperateLogVO> getLogVOListByUuid(String uuid);

}

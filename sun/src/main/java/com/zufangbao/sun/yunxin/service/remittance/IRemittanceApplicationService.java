package com.zufangbao.sun.yunxin.service.remittance;

import java.util.Date;
import java.util.List;

import com.demo2do.core.service.GenericService;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.yunxin.entity.remittance.ExecutionStatus;
import com.zufangbao.sun.yunxin.entity.remittance.RemittanceApplication;
import com.zufangbao.sun.yunxin.entity.remittance.RemittanceApplicationQueryModel;

/**
 * 放款申请service接口
 * @author zhanghongbing
 *
 */
public interface IRemittanceApplicationService extends GenericService<RemittanceApplication>{

	/**
	 * 判断请求号是否重复
	 * @param requestNo
	 * @return
	 */
	public boolean existsRequestNo(String requestNo);
	
	public List<RemittanceApplication> queryRemittanceApplication(RemittanceApplicationQueryModel queryModel, Page page);
	
	public int queryRemittanceApplicationCount(RemittanceApplicationQueryModel queryModel);

	public RemittanceApplication getUniqueRemittanceApplicationByUuid(String remittanceApplicationUuid);
	
	public List<RemittanceApplication> getRemittanceApplicationsBy(String contractUniqueId);
	
	public int countRemittanceApplicationsBy(List<Long> financialContractIds, List<ExecutionStatus> executionStatusList, Date calculateDate);

	public boolean addPlanNotifyNumber(String remittanceApplicationUuid, int number);
	
}

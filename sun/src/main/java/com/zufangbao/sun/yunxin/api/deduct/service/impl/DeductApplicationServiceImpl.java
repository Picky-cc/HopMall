package com.zufangbao.sun.yunxin.api.deduct.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.api.model.deduct.DeductApplicationExecutionStatus;
import com.zufangbao.sun.api.model.deduct.RecordStatus;
import com.zufangbao.sun.yunxin.api.deduct.service.DeductApplicationDetailService;
import com.zufangbao.sun.yunxin.api.deduct.service.DeductApplicationService;
import com.zufangbao.sun.yunxin.entity.api.deduct.DeductApplication;
import com.zufangbao.sun.yunxin.entity.deduct.DeductApplicationQeuryModel;

@Service("deductApplicationService")
public class DeductApplicationServiceImpl extends GenericServiceImpl<DeductApplication> implements DeductApplicationService {

	
	@Autowired
	private DeductApplicationDetailService deductApplicationDetailService;
	
	
	@Override
	public DeductApplication getDeductApplicationByDeductRequestNo(String DeductReqeuestNo) {

		Filter filter = new Filter();
		
		filter.addEquals("requestNo", DeductReqeuestNo);
		List<DeductApplication> deductApplications  = this.list(DeductApplication.class, filter);
		if(CollectionUtils.isEmpty(deductApplications)){
			return null;
		}
		return deductApplications.get(0);
	}
	
	@Override
	public DeductApplication getDeductApplicationByDeductId(String deductId) {

		Filter filter = new Filter();
		
		filter.addEquals("deductId", deductId);
		List<DeductApplication> deductApplications  = this.list(DeductApplication.class, filter);
		if(CollectionUtils.isEmpty(deductApplications)){
			return null;
		}
		return deductApplications.get(0);
	}
		@SuppressWarnings("unchecked")
	@Override
	public List<DeductApplication> getDeductApplicationByRepaymentPlanCodeAndInprocessing(String  assetSetUuid){
		
		
		Set<String> deductApplicationUuidSet = deductApplicationDetailService.getDeductApplicationUuidByAssetUuid(assetSetUuid);
		if(CollectionUtils.isEmpty(deductApplicationUuidSet)){
			return Collections.EMPTY_LIST;
		}
	    Map<String, Object> params = new HashMap<String ,Object>();
		params.put("deductApplicationUuidSet", deductApplicationUuidSet );
		params.put("processing", DeductApplicationExecutionStatus.PROCESSING);
		String queryString = "from DeductApplication where deductApplicationUuid IN (:deductApplicationUuidSet) and executionStatus =:processing";
		
		return this.genericDaoSupport.searchForList(queryString, params);
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeductApplication> getDeductApplicationByRepaymentPlanCode(
			String  assetSetUuid) {
		
		Set<String> deductApplicationUuidSet = deductApplicationDetailService.getDeductApplicationUuidByAssetUuid(assetSetUuid);
		if(CollectionUtils.isEmpty(deductApplicationUuidSet)){
			return Collections.EMPTY_LIST;
		}
	    Map<String, Object> params = new HashMap<String ,Object>();
		params.put("deductApplicationUuidSet", deductApplicationUuidSet );
		String queryString = "from DeductApplication where deductApplicationUuid IN (:deductApplicationUuidSet)";
			
		return this.genericDaoSupport.searchForList(queryString, params);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeductApplication> get_processing_or_success_list(String assetSetUuid) {
		
		Set<String> deductApplicationUuidSet = deductApplicationDetailService.getDeductApplicationUuidByAssetUuid(assetSetUuid);
		if(CollectionUtils.isEmpty(deductApplicationUuidSet)){
			return Collections.EMPTY_LIST;
		}
		
		String sql = "from DeductApplication where deductApplicationUuid IN (:deductApplicationUuidSet) and executionStatus IN (:processing , :success)";
		Map<String, Object> params = new HashMap<String ,Object>();
		params.put("deductApplicationUuidSet", deductApplicationUuidSet );
		params.put("processing", DeductApplicationExecutionStatus.PROCESSING);
		params.put("success", DeductApplicationExecutionStatus.SUCCESS);
		return genericDaoSupport.searchForList(sql, params);
	}
	
	@Override
	public List<DeductApplication> get_success_un_write_off_application(String financialContractUuid) {
		String sql = "from DeductApplication where financialContractUuid=:financialContractUuid and executionStatus=:executionStatus"
				+ " and recordStatus!=:writeOff";
		Map<String, Object> params = new HashMap<String ,Object>();
		params.put("financialContractUuid", financialContractUuid);
		params.put("executionStatus", DeductApplicationExecutionStatus.SUCCESS);
		params.put("writeOff", RecordStatus.WRITE_OFF);
		return genericDaoSupport.searchForList(sql, params);
	}
	
	@Override
	public String generateQuerySentence(DeductApplicationQeuryModel deductApplicationQeuryModel, Map<String,Object> params){
		
		StringBuffer querySB =new StringBuffer();
		
		querySB.append("FROM DeductApplication  WHERE financialContractUuid IN (:financialContractIdList) AND repaymentType  IN (:repaymentTypeList)  AND executionStatus IN (:executionStatusList)");
		

		params.put("financialContractIdList", deductApplicationQeuryModel.getFinancialContractIdList());
		params.put("repaymentTypeList", deductApplicationQeuryModel.getRepaymentTypeList());
		params.put("executionStatusList",deductApplicationQeuryModel.getExecutionStatusEnumList());
			
		if(!StringUtils.isEmpty(deductApplicationQeuryModel.getCustomerName())){
				querySB.append(" AND customerName like :customerName");
				params.put("customerName", "%"+deductApplicationQeuryModel.getCustomerName()+"%");
		}
		if(!StringUtils.isEmpty(deductApplicationQeuryModel.getLoanContractNo())){
			querySB.append(" AND contractNo LIKE :loanContractNo");
			params.put("loanContractNo", "%"+deductApplicationQeuryModel.getLoanContractNo()+"%");
		}
		if(deductApplicationQeuryModel.getStartDateValue() != null){
			querySB.append(" AND createTime  >= :startDate");
			params.put("startDate", deductApplicationQeuryModel.getStartDateValue());
		}
		if(deductApplicationQeuryModel.getEndDateValue() != null){
			querySB.append(" AND createTime <= :endDate");
			params.put("endDate", deductApplicationQeuryModel.getEndDateValue());
		}
		querySB.append(" order by createTime DESC");
		
		return querySB.toString();
	}

	@Override
	public DeductApplication getDeductApplicationByDeductApplicationUuid(String deductApplicationUuid) {
		
		Filter filter =  new Filter();
		filter.addEquals("deductApplicationUuid", deductApplicationUuid);
		
		List<DeductApplication> deductApplications = this.list(DeductApplication.class, filter);
		if(CollectionUtils.isEmpty(deductApplications)){
			return null;
		}
		
		return deductApplications.get(0);
	}

}

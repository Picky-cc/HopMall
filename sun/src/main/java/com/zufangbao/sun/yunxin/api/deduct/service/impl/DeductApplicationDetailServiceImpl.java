package com.zufangbao.sun.yunxin.api.deduct.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.api.model.deduct.DeductApplicationRepaymentDetail;
import com.zufangbao.sun.yunxin.api.deduct.service.DeductApplicationDetailService;
import com.zufangbao.sun.yunxin.api.deduct.service.DeductApplicationService;
import com.zufangbao.sun.yunxin.entity.api.deduct.DeductApplication;
import com.zufangbao.sun.yunxin.entity.api.deduct.DeductApplicationDetail;


@Service("deductApplicationDetailService")
public class DeductApplicationDetailServiceImpl  extends GenericServiceImpl<DeductApplicationDetail> implements DeductApplicationDetailService {

	
	@Autowired
	private   DeductApplicationService deductApplicationService;
	@Override
	public List<DeductApplicationDetail> getDeductApplicationDetailByDeductId(String deductId) {
		
		DeductApplication deductApplication = deductApplicationService.getDeductApplicationByDeductId(deductId);
		if(deductApplication == null){
			return Collections.EMPTY_LIST;
		}
		Filter filter = new Filter();
		filter.addEquals("deductApplicationUuid", deductApplication.getDeductApplicationUuid());
		return this.list(DeductApplicationDetail.class, filter);
	}
	
	@Override
	public List<DeductApplicationRepaymentDetail> getRepaymentDetailsBy(String deductApplicationUuid){
	
		
		Map<String, Object> parmas = new HashMap<String, Object>();
		parmas.put("deductApplicationUuid", deductApplicationUuid);
		StringBuffer queryString = new StringBuffer("from DeductApplicationDetail where deductApplicationUuid =:deductApplicationUuid ");
		List<DeductApplicationDetail> deductApplicationDetailAlls =this.genericDaoSupport.searchForList(queryString.toString(),parmas);

		List<DeductApplicationRepaymentDetail> repaymentDetails = new ArrayList<DeductApplicationRepaymentDetail>();
		Map<String,List<DeductApplicationDetail>> DeductApplicationDetailGroup  =deductApplicationDetailAlls.stream().
				collect(Collectors.groupingBy(DeductApplicationDetail::getDeductApplicationDetailUuid));
		
		for(String key:DeductApplicationDetailGroup.keySet())
		{
			List<DeductApplicationDetail> details=DeductApplicationDetailGroup.get(key);
			Map<String , BigDecimal> subjectDetail = new HashMap<String ,BigDecimal>();
			for(DeductApplicationDetail deductApplicationDetail :details ){
				
				    String subjectUuid = deductApplicationDetail.lastAccountName();
					subjectDetail.put(subjectUuid,deductApplicationDetail.getAccountAmount());
			}
			repaymentDetails.add(new DeductApplicationRepaymentDetail(subjectDetail,details.get(0)));
		}
	
		
		return repaymentDetails; 
	}
	
	@Override
	public Set<String> getDeductApplicationUuidByAssetUuid(String assetSetUuid){
		
		String  queryString = "select deduct_application_uuid from t_deduct_application_detail where asset_set_uuid =:assetSetUuid";
		
		Map<String ,Object> params = new HashMap<String,Object>();
		
		params.put("assetSetUuid", assetSetUuid);
		List<String> deductApplicationUuidList = this.genericDaoSupport.queryForSingleColumnList(queryString, params, String.class);
		
		return deductApplicationUuidList.stream().collect(Collectors.toSet());
	}

}

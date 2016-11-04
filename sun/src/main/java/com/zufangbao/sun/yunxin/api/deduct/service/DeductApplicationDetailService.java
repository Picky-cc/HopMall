package com.zufangbao.sun.yunxin.api.deduct.service;

import java.util.List;
import java.util.Set;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.api.model.deduct.DeductApplicationRepaymentDetail;
import com.zufangbao.sun.yunxin.entity.api.deduct.DeductApplicationDetail;

public interface DeductApplicationDetailService  extends  GenericService<DeductApplicationDetail>{

	
	public   List<DeductApplicationDetail>   getDeductApplicationDetailByDeductId(String deductId);

	public   List<DeductApplicationRepaymentDetail> getRepaymentDetailsBy(String deductApplicationUuid);

	public   Set<String> getDeductApplicationUuidByAssetUuid(String assetSetUuid);

} 
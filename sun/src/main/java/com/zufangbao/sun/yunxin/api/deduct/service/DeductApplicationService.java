package com.zufangbao.sun.yunxin.api.deduct.service;

import java.util.List;
import java.util.Map;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.yunxin.entity.api.deduct.DeductApplication;
import com.zufangbao.sun.yunxin.entity.deduct.DeductApplicationQeuryModel;

public interface DeductApplicationService extends GenericService<DeductApplication>{

	public  DeductApplication getDeductApplicationByDeductRequestNo(String DeductReqeuestNo);
	
	public  DeductApplication getDeductApplicationByDeductId(String deductId);
	
	public  List<DeductApplication> getDeductApplicationByRepaymentPlanCodeAndInprocessing(String  assetSetUuid);

	public  List<DeductApplication> getDeductApplicationByRepaymentPlanCode(String assetSetUuid);

	public List<DeductApplication> get_processing_or_success_list(String assetSetUuid);
	
	public List<DeductApplication> get_success_un_write_off_application(String financialContractUuid);


	public String generateQuerySentence(DeductApplicationQeuryModel deductApplicationQeuryModel,Map<String, Object> params);
	
	public DeductApplication getDeductApplicationByDeductApplicationUuid(String deductApplicationUuid);
}

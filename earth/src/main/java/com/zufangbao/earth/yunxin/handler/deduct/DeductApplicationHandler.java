package com.zufangbao.earth.yunxin.handler.deduct;

import java.util.List;

import com.demo2do.core.web.resolver.Page;
import com.zufangbao.earth.yunxin.entity.deduct.model.DeductApplicationDetailShowModel;
import com.zufangbao.earth.yunxin.entity.deduct.model.QueryDeductApplicationShowModel;
import com.zufangbao.sun.yunxin.entity.deduct.DeductApplicationQeuryModel;

public interface DeductApplicationHandler {

	
	public  int countQueryDeductApplicationNumber(DeductApplicationQeuryModel queryModel);
	
	public  List<QueryDeductApplicationShowModel> queryDeductApplicationShowModel(DeductApplicationQeuryModel queryModel, Page page);
	
	public  DeductApplicationDetailShowModel assembleDeductApplicationDetailShowModel(String deductApplicationUuid);
}

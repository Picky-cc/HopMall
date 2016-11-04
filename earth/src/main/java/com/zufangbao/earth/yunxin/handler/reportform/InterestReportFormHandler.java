package com.zufangbao.earth.yunxin.handler.reportform;

import java.util.List;

import com.zufangbao.sun.yunxin.entity.model.reportform.InterestQueryModel;
import com.zufangbao.sun.yunxin.entity.model.reportform.InterestShowModel;

public interface InterestReportFormHandler {

	List<InterestShowModel> query(InterestQueryModel queryModel);
	
}

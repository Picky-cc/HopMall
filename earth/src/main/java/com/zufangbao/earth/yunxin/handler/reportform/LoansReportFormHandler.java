package com.zufangbao.earth.yunxin.handler.reportform;

import java.util.List;

import com.zufangbao.sun.yunxin.entity.model.reportform.LoansQueryModel;
import com.zufangbao.sun.yunxin.entity.model.reportform.LoansShowModel;

public interface LoansReportFormHandler {

	List<LoansShowModel> query(LoansQueryModel queryModel);

}

package com.zufangbao.earth.yunxin.handler;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.demo2do.core.web.resolver.Page;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.sun.entity.financial.LoanBatch;
import com.zufangbao.sun.yunxin.entity.model.loanbatch.LoanBatchQueryModel;
import com.zufangbao.sun.yunxin.entity.model.loanbatch.LoanBatchShowModel;
import com.zufangbao.sun.yunxin.log.LogFunctionType;
import com.zufangbao.sun.yunxin.log.LogOperateType;

public interface LoanBatchHandler {
	public List<HSSFWorkbook> createExcelList(Long loanBatchId);

	public void deleteLoanBatchData(Principal principal, String ip, Long loanBatchId) throws Exception;
	
	public List<LoanBatchShowModel> generateLoanBatchShowModelList(LoanBatchQueryModel loanBatchQueryModel, Page page);
	
	public void activateLoanBatch(Long loanBatchId) ;

	public List<LoanBatch> queryLoanBatchs(LoanBatchQueryModel loanBatchQueryModel);
	
	public void generateLoanBacthSystemLog(Principal principal,
			String ipAddress, LogFunctionType logFunctionType,
			LogOperateType logOperateType, Long loanBatchId) throws Exception;

}

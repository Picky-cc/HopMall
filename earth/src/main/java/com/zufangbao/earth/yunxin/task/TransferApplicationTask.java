package com.zufangbao.earth.yunxin.task;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.earth.yunxin.handler.PrepaymentHandler;
import com.zufangbao.earth.yunxin.handler.UnionPayHandler;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.wellsfargo.yunxin.handler.TransferApplicationHandler;

@Component
public class TransferApplicationTask {

	@Autowired
	private TransferApplicationHandler transferApplicationHandler;
	@Autowired
	private UnionPayHandler unionPayHandler;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private PrepaymentHandler prepaymentHandler;
	
	private static Log logger = LogFactory.getLog(TransferApplicationTask.class);
	
	public void todayRecycleAssetCreateTransferApplicationAndDeduct(){
		logger.info("todayRecycleAssetCreateTransferApplicationAndDeduct begin.");
		long start = System.currentTimeMillis();
		try {
			List<Serializable> ids = transferApplicationHandler.todayRecycleAssetCreateTransferApplications();
			loopStartSingleDeduct(ids);
		} catch (Exception e) {
			logger.error("todayRecycleAssetCreateTransferApplicationAndDeduct error.");
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		logger.info("todayRecycleAssetCreateTransferApplicationAndDeduct end. used ["+(end-start)+"]ms");
	}
	
	public void overDueAssetCreateTransferApplicationAndDeduct(){
		logger.info("overDueAssetCreateTransferApplicationAndDeduct begin.");
		long start = System.currentTimeMillis();
		try {
			List<Serializable> ids = transferApplicationHandler.overDueAssetCreateTransferApplications();
			loopStartSingleDeduct(ids);
		} catch (Exception e) {
			logger.error("overDueAssetCreateTransferApplicationAndDeduct error.");
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		logger.info("overDueAssetCreateTransferApplicationAndDeduct end. used ["+(end-start)+"]ms");
	}
	
	private void loopStartSingleDeduct(List<Serializable> ids) {
		for (Serializable id : ids) {
			unionPayHandler.startSingleDeduct(id);
		}
	}
	
	public void singleQueryUnionpayDeductResult(){
		//查询银联扣款结果
		queryUnionpayDeductResult();
		
		//处理还款成功的提前还款申请
		processingPrepaymentApplicationAfterSuccess();
	}

	private void queryUnionpayDeductResult() {
		logger.info(DateUtils.getCurrentTimeMillis() +" query Union For InProcess TransferApplication begin.");
		long start = System.currentTimeMillis();
		try {
			unionPayHandler.startSingleQueryUnionpayDeductResult();
		} catch (Exception e) {
			logger.error("query Union For InProcess TransferApplication error.");
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		logger.info("query Union For InProcess TransferApplication end. used["+(end-start)+"]ms");
	}
	
	/**
	 * 查询结束后，处理还款成功的提前还款申请
	 */
	private void processingPrepaymentApplicationAfterSuccess(){
		List<String> repaymentPlanUuids = repaymentPlanService.getPrepaymentSuccessRepaymentPlanUuids();
		prepaymentHandler.processingPrepaymentApplicationAfterSuccess(repaymentPlanUuids);
	}
	
}

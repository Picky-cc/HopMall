package com.zufangbao.earth.yunxin.task;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.earth.yunxin.handler.remittance.RemittancePlanExecLogHandler;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.remittance.ReverseStatus;
import com.zufangbao.sun.yunxin.service.remittance.IRemittancePlanExecLogService;


/**
 * 确认放款流水Task
 * @author jx
 */

@Component("remittanceCashFlowCheckTask")
public class RemittanceCashFlowCheckTask {
	
	@Autowired
	private IRemittancePlanExecLogService remittancePlanExecLogService;
	
	@Autowired
	private RemittancePlanExecLogHandler remittancePlanExecLogHandler;

	private static Log logger = LogFactory.getLog(RemittanceCashFlowCheckTask.class);
	
	public void execute() {
		//确认失败的代付单（失败5分钟后且未完成计划检查次数），是否存在贷记流水
		confirmWhetherExistCreditCashFlowForFailedRemittance();
		
		//确认未冲账的代付单，是否有借记流水
		confirmWhetherExistDebitCashFlowForFailedRemittance();
	}
	
	/**
	 * 确认失败的代付单（失败5分钟后且未完成计划检查次数），是否存在贷记流水
	 */
	private void confirmWhetherExistCreditCashFlowForFailedRemittance() {
		logger.info("#confirmWhetherExistCreditCashFlowForFailedRemittance begin.");
		long start = System.currentTimeMillis();
		String currentTime = DateUtils.format(new Date(), DateUtils.LONG_DATE_FORMAT);
		try {
			List<Long> ids = remittancePlanExecLogService.getRemittancePlanExecLogIdsForReverse();
			logger.info(currentTime+"#放款失败贷记流水确认，待确认总计（"+ ids.size() +"）条 !");
			loopConfirmWhetherExistCreditCashFlow(ids);
		} catch (Exception e) {
			logger.error("#confirmWhetherExistCreditCashFlowForFailedRemittance occur error.");
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		logger.info("#confirmWhetherExistCreditCashFlowForFailedRemittance end. used ["+(end-start)+"]ms");
	}
	
	private void loopConfirmWhetherExistCreditCashFlow(List<Long> ids){
		for (Long id : ids) {
			try{
				remittancePlanExecLogHandler.confirmWhetherExistCreditCashFlow(id);
			}catch(Exception e){
				logger.error("#放款失败贷记流水确认，处理发生异常，代付单编号［"+id+"］");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 确认未冲账的代付单，是否有借记流水
	 * 有则，冲账生成代付撤销单
	 */
	private void confirmWhetherExistDebitCashFlowForFailedRemittance() {
		logger.info("#confirmWhetherExistDebitCashFlowForFailedRemittance begin.");
		long start = System.currentTimeMillis();
		String currentTime = DateUtils.format(new Date(), DateUtils.LONG_DATE_FORMAT);
		try {
			List<Long> ids = remittancePlanExecLogService.getRemittancePlanExecLogIdsByReverseStatus(ReverseStatus.NOTREVERSE);
			logger.info(currentTime+"#未冲账代付单，待处理，总计（"+ ids.size() +"）条 !");
			loopConfirmWhetherExistDebitCashFlow(ids);
		} catch (Exception e) {
			logger.error("#confirmWhetherExistDebitCashFlowForFailedRemittance occur error.");
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		logger.info("#confirmWhetherExistDebitCashFlowForFailedRemittance end. used ["+(end-start)+"]ms");
	}
	
	private void loopConfirmWhetherExistDebitCashFlow(List<Long> ids){
		for (Long id : ids) {
			try{
				remittancePlanExecLogHandler.confirmWhetherExistDebitCashFlow(id);
			}catch(Exception e){
				logger.error("未冲账代付单，处理发生异常，代付单编号［"+id+"］");
				e.printStackTrace();
			}
		}
	}
	
}

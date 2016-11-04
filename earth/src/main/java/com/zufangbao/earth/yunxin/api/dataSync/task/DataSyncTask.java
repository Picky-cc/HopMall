package com.zufangbao.earth.yunxin.api.dataSync.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo2do.core.utils.DateUtils;
import com.zufangbao.earth.yunxin.api.handler.DataSyncHandler;
import com.zufangbao.sun.yunxin.entity.api.DataSyncLog;

@Component
public class DataSyncTask {

	private static final int SINGLE_RULE = 1;

	private static final int BATCH_RULE = 0;

	private static final Log logger = LogFactory.getLog(DataSyncTask.class);

	@Autowired
	private DataSyncHandler dataSyncHandler;

	/**
	 *  手动执行同步接口
	 */
	public  void manualOperatecommandDataSync(){
		logger.info("#execCommandDataSync begin.");
		long start = System.currentTimeMillis();
		try {
			List<String> repaymentUuidList = dataSyncHandler.getPendingRepaymentPlanList();
			loopSyncPendingRepaymentPlanList(repaymentUuidList, SINGLE_RULE);

		} catch (Exception e) {
			logger.error("#execCommandDataSync occur error.");
			e.printStackTrace();
		}

		long end = System.currentTimeMillis();
		logger.info("#execCommandDataSync end. used [" + (end - start) + "]ms");
	}
	
	/**
	 * task执行同步数据接口
	 */
	public void commandDataSync() {
		logger.info("#execCommandDataSync begin.");
		long start = System.currentTimeMillis();
		try {
			List<String> repaymentUuidList = dataSyncHandler.getPendingRepaymentPlanList();
			loopSyncPendingRepaymentPlanList(repaymentUuidList, BATCH_RULE);

		} catch (Exception e) {
			logger.error("#execCommandDataSync occur error.");
			e.printStackTrace();
		}

		long end = System.currentTimeMillis();
		logger.info("#execCommandDataSync end. used [" + (end - start) + "]ms");
	}

	private void loopSyncPendingRepaymentPlanList(List<String> repaymentPlanUuidList, int sendRule) {

		try {
			List<DataSyncLog> dataSyncLogList = new ArrayList<DataSyncLog>();
			for (String repaymentPlanUuid : repaymentPlanUuidList) {
				DataSyncLog dataSyncLog = dataSyncHandler.generateSyncLog(repaymentPlanUuid);
				if (dataSyncLog == null) {
					continue;
				}
				dataSyncLogList.add(dataSyncLog);
			}
			dataSyncHandler.execSingleNotifyForDataSync(dataSyncLogList, sendRule);
		} catch (Exception e) {
			logger.error("#数据同步结果，同步失败，同步时间["+DateUtils.format(new Date(),"yyyy-MM-dd HH::mm::SS")+"]");
			e.printStackTrace();
		}
	}

}

package com.zufangbao.earth.yunxin.task;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.earth.yunxin.handler.PrepaymentHandler;
import com.zufangbao.earth.yunxin.handler.UnionPayHandler;
import com.zufangbao.sun.utils.DateUtils;

/**
 * 提前还款日处理（每日13点、16点）
 * @author zhanghongbing
 *
 */
@Component("prepaymentTask")
public class PrepaymentTask {

	@Autowired
	private PrepaymentHandler prepaymentHandler;
	
	@Autowired
	private UnionPayHandler unionPayHandler;
	
	private static Log logger = LogFactory.getLog(PrepaymentTask.class);
	
	/**
	 * 执行提前还款申请
	 */
	public void execPrepaymentApplication() {
		logger.info("#execPrepaymentApplication begin.");
		long start = System.currentTimeMillis();
		String currentTime = DateUtils.format(new Date(), DateUtils.LONG_DATE_FORMAT);
		try {
			List<Serializable> ids = prepaymentHandler.createPrepaymentTransferApplication();
			logger.info(currentTime+"#提前还款，线上扣款执行单总计（"+ ids.size() +"）条 ! －ids－" + ids);
			
			logger.info("#execPrepaymentApplication begin.");
			loopStartSingleDeduct(ids);
		} catch (Exception e) {
			logger.error("#execPrepaymentApplication occur error.");
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		logger.info("#execPrepaymentApplication end. used ["+(end-start)+"]ms");
	}
	
	private void loopStartSingleDeduct(List<Serializable> ids) {
		for (Serializable id : ids) {
			unionPayHandler.startSingleDeduct(id);
		}
	}
	
}

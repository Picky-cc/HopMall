package com.zufangbao.earth.yunxin.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.wellsfargo.yunxin.handler.OrderHandler;
import com.zufangbao.wellsfargo.yunxin.handler.TransferApplicationHandler;

@Component
public class OrderTask {

	@Autowired
	private TransferApplicationHandler transferApplicationHandler;
	
	@Autowired
	private OrderHandler orderHandler;
	
	private static Log logger = LogFactory.getLog(OrderTask.class);
	
	public void updateFailOrderStatus() {
		
		logger.info("begin to update Order executingStatus !");
		try {
			transferApplicationHandler.updateTodayOrderExecuteStatus();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("execute update Order error[" + e.getMessage() + "]");
		}
		logger.info("end to update Order executingStatus !");
	}
	
	public void createGuaranteeOrder() {
		try {
			logger.info("begin to auto create guarantee order!");
			orderHandler.createGuaranteeOrder();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("execute auto create guarantee order error[" + e.getMessage() + "]");
		}
		logger.info("end to auto create guarantee order!");
	}
}

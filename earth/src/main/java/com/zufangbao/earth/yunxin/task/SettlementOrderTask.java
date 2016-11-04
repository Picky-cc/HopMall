package com.zufangbao.earth.yunxin.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.wellsfargo.yunxin.handler.SettlementOrderHandler;

@Component
public class SettlementOrderTask {

	@Autowired
	private SettlementOrderHandler settlementOrderHandler;

	private static Log logger = LogFactory.getLog(SettlementOrderTask.class);

	public void createSettlementOrder() {
		try {
			logger.info("task createAllSettlementOrder start!");
			settlementOrderHandler.createAllSettlementOrder();
			logger.info("task createAllSettlementOrder end!");
		} catch (Exception e) {
			logger.error("task createAllSettlementOrder has Error,[" + e.getMessage() + "]");
			e.printStackTrace();
		}
	}
}

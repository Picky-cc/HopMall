package com.zufangbao.wellsfargo.yunxin.handler;

import com.zufangbao.sun.yunxin.entity.model.SettlementOrderQueryModel;

/**
 * 
 * @author louguanyang
 *
 */
public interface SettlementOrderHandler {
	void createAllSettlementOrder();

	void batchSubmit(SettlementOrderQueryModel settlementOrderQueryModel);

	void batchSettlement(SettlementOrderQueryModel settlementOrderQueryModel);
}

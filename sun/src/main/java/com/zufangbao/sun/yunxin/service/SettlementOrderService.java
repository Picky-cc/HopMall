package com.zufangbao.sun.yunxin.service;

import java.util.List;

import com.demo2do.core.persistence.support.Order;
import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.yunxin.SettlementStatus;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.SettlementOrder;
import com.zufangbao.sun.yunxin.entity.model.SettlementOrderQueryModel;

public interface SettlementOrderService extends GenericService<SettlementOrder> {

	void createSettlementOrder(AssetSet assetSet);

	List<SettlementOrder> getSettlementOrderListBy(AssetSet assetSet);

	List<SettlementOrder> getSettlementOrderListBy(List<String> settlementOrderUuidList);
	
	public int countSettlementOrderList(List<Long> financialContractIds, SettlementStatus settlementStatus);
	
	public List<SettlementOrder> getSettlementOrderListBy(SettlementOrderQueryModel settlementOrderQueryModel,Order order,int begin, int max);
}
package com.zufangbao.wellsfargo.yunxin.handler.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.sun.ledgerbook.LedgerBookHandler;
import com.zufangbao.sun.yunxin.SettlementStatus;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.SettlementOrder;
import com.zufangbao.sun.yunxin.entity.model.SettlementOrderQueryModel;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.sun.yunxin.service.SettlementOrderService;
import com.zufangbao.wellsfargo.yunxin.handler.SettlementOrderHandler;

/**
 * 
 * @author louguanyang
 *
 */
@Component("settlementOrderHandler")
public class SettlementOrderHandlerImpl implements SettlementOrderHandler {

	@Autowired
	private RepaymentPlanService repaymentPlanService;
	
	@Autowired
	private SettlementOrderService settlementOrderService;

	@Override
	public void createAllSettlementOrder() {
		List<AssetSet> allNeedSettlementAssetSetList = repaymentPlanService.loadAllNeedSettlementAssetSetList();
		for (AssetSet assetSet : allNeedSettlementAssetSetList) {
			settlementOrderService.createSettlementOrder(assetSet);
		}
	}

	@Override
	public void batchSubmit(SettlementOrderQueryModel settlementOrderQueryModel) {
		List<String> settlementOrderUuidList = settlementOrderQueryModel.convertSettlementOrderUuidList();
		List<SettlementOrder> settlementOrderList = settlementOrderService.getSettlementOrderListBy(settlementOrderUuidList);
		for (SettlementOrder settlementOrder : settlementOrderList) {
			if (!settlementOrder.isCreateSettlementOrder()) {
				continue;
			}
			updateSettlementOrder(settlementOrder, SettlementStatus.WAITING);
		}
	}

	@Override
	public void batchSettlement(SettlementOrderQueryModel settlementOrderQueryModel) {
		List<String> settlementOrderUuidList = settlementOrderQueryModel.convertSettlementOrderUuidList();
		List<SettlementOrder> settlementOrderList = settlementOrderService.getSettlementOrderListBy(settlementOrderUuidList);
		for (SettlementOrder settlementOrder : settlementOrderList) {
			if (!settlementOrder.isWaitingSettlementOrder()) {
				continue;
			}
			updateSettlementOrder(settlementOrder, SettlementStatus.DONE);
		}
	}
	
	private void updateSettlementOrder(SettlementOrder settlementOrder, SettlementStatus settlementStatus) {
		AssetSet assetSet = settlementOrder.getAssetSet();
		assetSet.setLastModifiedTime(new Date());
		assetSet.setSettlementStatus(settlementStatus);
		repaymentPlanService.save(assetSet);
		settlementOrder.setLastModifyTime(new Date());
		settlementOrder.setAssetSet(assetSet);
		settlementOrderService.save(settlementOrder);
	}

}

package com.zufangbao.sun.yunxin.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.ledgerbook.LedgerBook;
import com.zufangbao.sun.ledgerbook.LedgerBookHandler;
import com.zufangbao.sun.ledgerbook.LedgerItemService;
import com.zufangbao.sun.ledgerbook.LedgerTradeParty;
import com.zufangbao.sun.service.LedgerBookService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.yunxin.SettlementStatus;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.SettlementOrder;
import com.zufangbao.sun.yunxin.entity.model.SettlementOrderQueryModel;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.sun.yunxin.service.SettlementOrderService;

@Service("settlementOrderService")
public class SettlementOrderServiceImpl extends
		GenericServiceImpl<SettlementOrder> implements SettlementOrderService {

	@Autowired
	private GenericDaoSupport genericDaoSupport;
	@Autowired
	private OrderService orderService;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private LedgerBookHandler ledgerBookHandler;
	@Autowired
	private LedgerBookService ledgerBookService;
	@Autowired
	private LedgerItemService ledgerItemService;
	@Override
	public void createSettlementOrder(AssetSet assetSet) {
		if (assetSet == null) {
			return;
		}
		List<SettlementOrder> settlementOrdersInDB = getSettlementOrderListBy(assetSet);
		if (!CollectionUtils.isEmpty(settlementOrdersInDB)) {
			return;
		}
		Order guranteeOrder = orderService.getGuranteeOrder(assetSet);
		SettlementOrder settlementOrder = new SettlementOrder(guranteeOrder);
		this.save(settlementOrder);
		repaymentPlanService.updateSettlementStatusAndSave(assetSet);
		repurchase_to_payable(assetSet);
	}

	private void repurchase_to_payable(AssetSet assetSet) {
		LedgerBook book = ledgerBookService.getBookByAsset(assetSet);
		if(book==null) return;
		LedgerTradeParty payableparty = ledgerItemService.getPayableLedgerTradeParty(assetSet);
		ledgerBookHandler.repurchase_to_payable(book, assetSet, "", "", "", payableparty);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SettlementOrder> getSettlementOrderListBy(AssetSet assetSet) {
		String queryString = "FROM SettlementOrder where assetSet =:assetSet";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("assetSet", assetSet);
		return this.genericDaoSupport.searchForList(queryString, parameters);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SettlementOrder> getSettlementOrderListBy(List<String> settlementOrderUuidList) {
		if(settlementOrderUuidList.isEmpty()) {
			return Collections.emptyList();
		}
		String queryString = "FROM SettlementOrder where settlementOrderUuid IN (:settlementOrderUuidList)";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("settlementOrderUuidList", settlementOrderUuidList);
		return this.genericDaoSupport.searchForList(queryString, parameters);
	}

	@Override
	public int countSettlementOrderList(List<Long> financialContractIds, SettlementStatus settlementStatus) {
		if(CollectionUtils.isEmpty(financialContractIds)){
			return 0;
		}
		String queryString = "FROM SettlementOrder s, AssetPackage a where a.financialContract.id IN(:financialContractIds) "
				+ " AND s.assetSet.contract=a.contract AND s.assetSet.settlementStatus=:settlementStatus";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("financialContractIds", financialContractIds);
		params.put("settlementStatus", settlementStatus);
		return this.genericDaoSupport.count(queryString, params);
	}
	
	@SuppressWarnings("unchecked")
	public List<SettlementOrder> getSettlementOrderListBy(SettlementOrderQueryModel settlementOrderQueryModel,com.demo2do.core.persistence.support.Order order,int begin, int max){
		StringBuffer sentence = new StringBuffer("SELECT settlementOrder From SettlementOrder settlementOrder,AssetPackage ap where settlementOrder.assetSet.contract=ap.contract ");
		Map<String,Object> params = new HashMap<String,Object>();
		if(CollectionUtils.isEmpty(settlementOrderQueryModel.getFinancialContractIdList())){
			return Collections.emptyList();
		} else {
			sentence.append(" AND ap.financialContract.id IN(:financialContractIdList)");
			params.put("financialContractIdList", settlementOrderQueryModel.getFinancialContractIdList());
		}
		if (settlementOrderQueryModel.getSettlementStatusEnum() != null) {
			sentence.append(" AND settlementOrder.assetSet.settlementStatus=:settlementStatus");
			params.put("settlementStatus", settlementOrderQueryModel.getSettlementStatusEnum());
		}
		if (!StringUtils.isEmpty(settlementOrderQueryModel.getSettlementOrderNo())) {
			sentence.append(" AND settlementOrder.settleOrderNo LIKE :settleOrderNo");
			params.put("settleOrderNo", "%"+settlementOrderQueryModel.getSettlementOrderNo()+"%");
		}
		if (!StringUtils.isEmpty(settlementOrderQueryModel.getRepaymentNo())) {
			sentence.append(" AND settlementOrder.assetSet.singleLoanContractNo LIKE :singleLoanContractNo");
			params.put("singleLoanContractNo", "%"+settlementOrderQueryModel.getRepaymentNo()+"%");
		}
		
		if (!StringUtils.isEmpty(settlementOrderQueryModel.getAppNo())) {
			sentence.append(" AND settlementOrder.assetSet.contract.app.appId LIKE :appId");
			params.put("appId", "%"+settlementOrderQueryModel.getAppNo()+"%");
		}
		if(order!=null){
			sentence.append(order.getSentence());
		}
		if(begin==0 && max==0){
			return genericDaoSupport.searchForList(sentence.toString(),params);
		} else{
			return genericDaoSupport.searchForList(sentence.toString(),params,begin,max);
		}
	}

}
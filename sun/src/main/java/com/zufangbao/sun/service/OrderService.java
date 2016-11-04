package com.zufangbao.sun.service;

import java.util.Date;
import java.util.List;

import com.demo2do.core.service.GenericService;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetSetActiveStatus;
import com.zufangbao.sun.yunxin.entity.GuaranteeStatus;
import com.zufangbao.sun.yunxin.entity.OrderClearStatus;
import com.zufangbao.sun.yunxin.entity.OrderType;
import com.zufangbao.sun.yunxin.entity.model.GuaranteeOrderModel;
import com.zufangbao.sun.yunxin.entity.model.OrderMatchQueryModel;
import com.zufangbao.sun.yunxin.entity.model.OrderQueryModel;



/**
 * @author lute
 *
 */

public interface OrderService extends GenericService<Order> {
	
	public Order getOrder(String orderNo);
	
	public Order getOrderById(Long orderId, OrderType orderType);

	public Order getOrderByRepaymentBillId(String repaymentBillId); 
	
	/** new method begin */
	public List<Order> getOrderListByAssetSetId(Long assetSetId);
	
	public List<Order> getOrderListBy(AssetSet assetSet, Date date, List<OrderClearStatus> clearingStatusList);
	
	public List<Order> getTodayOrderListBy(AssetSet assetSet);

	public List<Order> listOrder(OrderQueryModel orderQueryModel, int begin, int max);

	public List<Order> listOrder(List<String> repaymentUuidList, OrderType orderType);

	public Long getCompanyId(Order order);
	
	public List<Order> getGuaranteeOrders(GuaranteeOrderModel orderModel, Page page);
	
	public int countGuranteeOrders(List<Long> financialContractIds, GuaranteeStatus guaranteeStatus);
	
	public Order getGuranteeOrder(AssetSet assetSet);

	public List<Order> getOrderListBy(ContractAccount contractAccount);
	
	public List<Order> getOrderListExceptLapsedGuarantee(OrderMatchQueryModel orderMatchQueryModel);
	
	public boolean exist_not_completed_normal_order(Long assetSetId);
	
	public boolean exist_today_normal_order(Long assetSetId, Date date);

	public List<Order> getUnclearedRepaymentOrder(Long contractId);
	public Order getLastUnclearedRepaymentOrder(AssetSet assetSet);
	
	public List<Order> get_repayment_order_not_in_process_and_asset_not_clear(Contract contract, Date dueDate, AssetSetActiveStatus assetSetActiveStatus);
	
	/**
	 * 获取结算单（仅开启的还款计划）
	 */
	public List<Order> getSettlementStatementInNormalProcessing();
	
	/**
	 * 获取结算单（仅提前还款的还款计划）
	 */
	public List<Order> getSettlementStatementInPrepaymentProcessing();
	
	/**
	 * 获取结算单（含开启、提前还款的还款计划）
	 */
	public List<Order> getAllSettlementStatementInProcessing();
	
	/**
	 * 还款计划下是否存在（成功或者失败）的结算单
	 */
	public boolean existsSuccessOrDoingSettlementStatement(AssetSet repaymentPlan);
	
	public Order getRepaymentOrder(AssetSet assetSet, Date date);

	public int countOrderList(OrderQueryModel orderQueryModel);
	
}


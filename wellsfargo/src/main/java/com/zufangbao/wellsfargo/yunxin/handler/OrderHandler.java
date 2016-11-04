package com.zufangbao.wellsfargo.yunxin.handler;

import java.util.Date;
import java.util.List;

import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.yunxin.entity.model.OrderViewDetail;
import com.zufangbao.wellsfargo.greypool.document.entity.busidocument.bill.BillingPlan;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.OfflineBillPayDetail;

/**
 * 
 * @author zjm
 *
 */
public interface OrderHandler {

	/** new method start */
	public OrderViewDetail convertToOrderDetail(Order order);

	void createGuaranteeOrder();

	public void updateStatusAfterIssueBusinessVoucher(Order order);
	
	/**
	 * 评估资产，并生成结算单
	 * @param assetSetId
	 * @param executeDate
	 * @throws Exception 
	 */
	public void assetValuationAndCreateOrder(Long assetSetId, Date executeDate) throws Exception;

	public List<BillingPlan> convertOrderToBillingPlan(List<Order> orderList);
	
	public List<OfflineBillPayDetail> getOfflineBillList(Order order);
	
}

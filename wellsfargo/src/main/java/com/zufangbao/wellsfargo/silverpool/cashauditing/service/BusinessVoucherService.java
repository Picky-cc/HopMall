/**
 * 
 */
package com.zufangbao.wellsfargo.silverpool.cashauditing.service;

import java.math.BigDecimal;
import java.util.List;

import com.demo2do.core.service.GenericService;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.yunxin.entity.IllegalInputAmountException;
import com.zufangbao.sun.yunxin.entity.model.OrderMatchModel;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.BusinessVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.BusinessVoucherStatus;

/**
 * @author wukai
 *
 */
public interface BusinessVoucherService extends GenericService<BusinessVoucher> {

	public BusinessVoucher getBusinessVoucherByBillingPlanUidAndType(String billingPlanUuid,String businessVoucherTypeUuid);
	public boolean existsInforeBVWithBillingPlanUuid(String billingPlanUuid);
	public BusinessVoucher getInForceBusinessVoucherByBillingPlanUidAndType(String billingPlanUuid,String businessVoucherTypeUuid);
	public BusinessVoucher getBusinessVoucherByBillingPlanUidAndType(String billingPlanUuid,String businessVoucherTypeUuid,List<BusinessVoucherStatus> businessVoucherStatusList);
	
	/** new method begin */
	public void issueBusinessVoucher(BusinessVoucher businessVoucher);

	public OrderMatchModel orderConvertToMatchModel(Order order);

	public void issueBusinessVoucher(BusinessVoucher businessVoucher, BigDecimal bookingAmount) throws IllegalInputAmountException;
	/** new method end */
}

/**
 * 
 */
package com.zufangbao.wellsfargo.silverpool.cashauditing.handler;

import java.math.BigDecimal;
import java.util.Collection;

import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.BusinessVoucher;

/**
 * @author wukai
 *
 */
public interface BusinessVoucherHandler {
	
	public BusinessVoucher createIfNotExistBusinessVoucherForRepaymentBill(String repaymentUuid, Long companyId, BigDecimal receivableAmount, String businessVoucherTypeUuid, String billingPlanTypeUuid);
	public void createIfNotExist(Collection<String> repaymentUuidList,Long companyId);
	public BusinessVoucher issueBusinessVoucher(String repaymentUuid, Long companyId, BigDecimal receivableAmount, String businessVoucherTypeUuid, String billingPlanTypeUuid, BigDecimal bookingAmount);
	public BusinessVoucher issueDefaultBusinessVoucher(Order order, Long companyId, BigDecimal bookingAmount);
	public BusinessVoucher issueAppCompensatoryBusinessVoucher(String repaymentUuid, Long companyId, BigDecimal bookingAmount, BigDecimal receivableAmount);
}

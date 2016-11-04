/**
 * 
 */
package com.zufangbao.wellsfargo.silverpool.cashauditing.handler.impl;


import java.math.BigDecimal;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.service.CompanyService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.yunxin.entity.IllegalInputAmountException;
import com.zufangbao.wellsfargo.greypool.document.entity.leasingdocument.GeneralLeasingDocumentTypeDictionary.BusinessDocumentTypeUuid;
import com.zufangbao.wellsfargo.greypool.document.entity.leasingdocument.GeneralLeasingDocumentTypeDictionary.DefaultTypeUuid;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.BusinessVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.BusinessVoucherStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.BusinessVoucherHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.BusinessVoucherService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.JournalVoucherService;

/**
 * @author wukai
 *
 */
@Component("businessVoucherHandler")
public class BusinessVoucherHandlerImpl implements BusinessVoucherHandler {
	
	@Autowired
	private BusinessVoucherService businessVoucherService;
	
	@Autowired
	private JournalVoucherService journalVoucherService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private OrderService orderService;
	
	private static final Log logger = LogFactory.getLog(BusinessVoucherHandlerImpl.class);

	@Override
	public BusinessVoucher createIfNotExistBusinessVoucherForRepaymentBill(String repaymentUuid, Long companyId, BigDecimal receivableAmount, String businessVoucherTypeUuid, String billingPlanTypeUuid) {
		if(StringUtils.isEmpty(repaymentUuid) ||receivableAmount==null){
			return null;
		}
		
		BusinessVoucher businessVoucher = businessVoucherService.getInForceBusinessVoucherByBillingPlanUidAndType(repaymentUuid, businessVoucherTypeUuid);
		if(businessVoucher!=null){
			return businessVoucher;
		}
		logger.info("begin to create businessVoucher for RepaymentBill["+repaymentUuid+"]");
		BusinessVoucher newBusinessVoucher = new BusinessVoucher(businessVoucherTypeUuid,
				
				repaymentUuid, StringUtils.EMPTY,BusinessVoucherStatus.VOUCHER_ISSUING,
				
				receivableAmount, BigDecimal.ZERO, AccountSide.DEBIT,
				
				companyId, 0L, 

				billingPlanTypeUuid);

		businessVoucherService.save(newBusinessVoucher);
		logger.info("end to create businessVoucher for RepaymentBill["+repaymentUuid+"]");
		return newBusinessVoucher;
	}
	
	@Override
	public void createIfNotExist(Collection<String> repaymentUuidList,Long companyId){
		for (String repaymentUuid : repaymentUuidList) {
			Order order = orderService.getOrderByRepaymentBillId(repaymentUuid);
			createIfNotExistBusinessVoucherForRepaymentBill(repaymentUuid, companyId, order.getTotalRent(), DefaultTypeUuid.DEFAULT_BUSINESS_VOUCHER_TYPE_UUID, DefaultTypeUuid.DEFAULT_BILLING_PLAN_TYPE_UUID);
		}
	}

	@Override
	public BusinessVoucher issueDefaultBusinessVoucher(Order order, Long companyId, BigDecimal bookingAmount) throws IllegalInputAmountException {
		return issueBusinessVoucher(order.getRepaymentBillId(), companyId, order.getTotalRent(), DefaultTypeUuid.DEFAULT_BUSINESS_VOUCHER_TYPE_UUID, DefaultTypeUuid.DEFAULT_BILLING_PLAN_TYPE_UUID, bookingAmount);
	}
	
	@Override
	public BusinessVoucher issueBusinessVoucher(String repaymentUuid, Long companyId, BigDecimal receivableAmount, String businessVoucherTypeUuid, String billingPlanTypeUuid, BigDecimal bookingAmount){
		BusinessVoucher businessVoucher = createIfNotExistBusinessVoucherForRepaymentBill(repaymentUuid,companyId, receivableAmount, businessVoucherTypeUuid, billingPlanTypeUuid);
		businessVoucherService.issueBusinessVoucher(businessVoucher,bookingAmount);
		return businessVoucher;
	}
	
	public BusinessVoucher issueAppCompensatoryBusinessVoucher(String repaymentUuid, Long companyId, BigDecimal bookingAmount, BigDecimal receivableAmount){
		return issueBusinessVoucher(repaymentUuid, companyId, receivableAmount, BusinessDocumentTypeUuid.APP_COMPENSATORY_VOUCHER_BV_TYPE_UUID, BusinessDocumentTypeUuid.APP_COMPENSATORY_VOUCHER_BP_TYPE_UUID, bookingAmount);
	}
	
}

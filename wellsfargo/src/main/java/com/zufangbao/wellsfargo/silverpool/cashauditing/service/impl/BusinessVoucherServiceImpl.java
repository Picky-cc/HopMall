/**
 * 
 */
package com.zufangbao.wellsfargo.silverpool.cashauditing.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.yunxin.entity.IllegalInputAmountException;
import com.zufangbao.sun.yunxin.entity.model.OrderMatchModel;
import com.zufangbao.wellsfargo.greypool.document.entity.leasingdocument.GeneralLeasingDocumentTypeDictionary;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.BusinessVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.BusinessVoucherStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.BusinessVoucherService;

/**
 * @author wukai
 *
 */
@Service("businessVoucherService")
public class BusinessVoucherServiceImpl extends GenericServiceImpl<BusinessVoucher> implements BusinessVoucherService {

	@Override
	public BusinessVoucher getBusinessVoucherByBillingPlanUidAndType(String billingPlanUuid, String businessVoucherTypeUuid) {
		
		if(StringUtils.isEmpty(billingPlanUuid) || StringUtils.isEmpty(businessVoucherTypeUuid)){
			
			return null;
		}
		Filter filter = new Filter();
		filter.addEquals("billingPlanUuid", billingPlanUuid);
		filter.addEquals("businessVoucherTypeUuid", businessVoucherTypeUuid);
		
		List<BusinessVoucher> businessVouchers = this.list(BusinessVoucher.class, filter);
		
		return CollectionUtils.isEmpty(businessVouchers) ? null : businessVouchers.get(0);
	}

	@Override
	public boolean existsInforeBVWithBillingPlanUuid(String billingPlanUuid) {
		if(StringUtils.isEmpty(billingPlanUuid)){
			return false;
		}
		String queryVoucher = "Select Count(*) From BusinessVoucher where billingPlanUuid=:billingPlanUuid AND businessVoucherStatus IN (:statusList)";
		
		List<BusinessVoucherStatus> statusList = new ArrayList<BusinessVoucherStatus>();
		statusList.add(BusinessVoucherStatus.VOUCHER_ISSUED);
		statusList.add(BusinessVoucherStatus.VOUCHER_ISSUING);
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("billingPlanUuid", billingPlanUuid);
		parameters.put("statusList", statusList);
		int size = genericDaoSupport.searchForInt(queryVoucher, parameters);
		if (size<=0){
			return false;
		}
		return true;
	}

	@Override
	public BusinessVoucher getInForceBusinessVoucherByBillingPlanUidAndType(
			String billingPlanUuid, String businessVoucherTypeUuid) {
		List<BusinessVoucherStatus> statusList = new ArrayList<BusinessVoucherStatus>();
		statusList.add(BusinessVoucherStatus.VOUCHER_ISSUED);
		statusList.add(BusinessVoucherStatus.VOUCHER_ISSUING);
		return getBusinessVoucherByBillingPlanUidAndType(billingPlanUuid, businessVoucherTypeUuid, statusList);
	}

	@Override
	public BusinessVoucher getBusinessVoucherByBillingPlanUidAndType(
			String billingPlanUuid, String businessVoucherTypeUuid,
			List<BusinessVoucherStatus> businessVoucherStatusList) {
		
		if(StringUtils.isEmpty(billingPlanUuid) || StringUtils.isEmpty(businessVoucherTypeUuid) || CollectionUtils.isEmpty(businessVoucherStatusList)){
			return null;
		}

		String querySentence = "From BusinessVoucher where billingPlanUuid=:billingPlanUuid AND businessVoucherTypeUuid=:businessVoucherTypeUuid "
				+ " AND businessVoucherStatus IN(:businessVoucherStatusList) ";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("billingPlanUuid", billingPlanUuid);
		params.put("businessVoucherTypeUuid", businessVoucherTypeUuid);
		params.put("businessVoucherStatusList", businessVoucherStatusList);
		List<BusinessVoucher> businessVouchers = genericDaoSupport.searchForList(querySentence, params);
		return CollectionUtils.isEmpty(businessVouchers) ? null : businessVouchers.get(0);
	}
	
	/** new method begin */
	@Override
	public void issueBusinessVoucher(BusinessVoucher businessVoucher) {
		if(businessVoucher==null){
			return;
		}
		businessVoucher.setBusinessVoucherStatus(BusinessVoucherStatus.VOUCHER_ISSUED);
		businessVoucher.setSettlementAmount(businessVoucher.getReceivableAmount());
		this.save(businessVoucher);
	}

	@Override
	public OrderMatchModel orderConvertToMatchModel(Order order) {
		if(null == order) {
			return null;
		}
		BusinessVoucher businessVoucher = getInForceBusinessVoucherByBillingPlanUidAndType(order.getRepaymentBillId(), GeneralLeasingDocumentTypeDictionary.DefaultTypeUuid.DEFAULT_BUSINESS_VOUCHER_TYPE_UUID);
		BigDecimal paidAmount = BigDecimal.ZERO;
		if(businessVoucher != null) {
			paidAmount = businessVoucher.getSettlementAmount();
		}
		return new OrderMatchModel(order, paidAmount);
	}

	@Override
	public void issueBusinessVoucher(BusinessVoucher businessVoucher, BigDecimal bookingAmount) throws IllegalInputAmountException {
		if(businessVoucher == null || bookingAmount == null) {
			return;
		}
		businessVoucher.addSettlementAmountAndBusinessVoucherStatus(bookingAmount);
		this.save(businessVoucher);
	}

}

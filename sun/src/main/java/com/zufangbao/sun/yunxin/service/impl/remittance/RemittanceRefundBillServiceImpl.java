package com.zufangbao.sun.yunxin.service.impl.remittance;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.yunxin.entity.remittance.RemittanceRefundBill;
import com.zufangbao.sun.yunxin.entity.remittance.RemittanceRefundBillQueryModel;
import com.zufangbao.sun.yunxin.service.remittance.IRemittanceRefundBillService;


@Service("RemittanceRefundBillService")
public class RemittanceRefundBillServiceImpl extends GenericServiceImpl<RemittanceRefundBill> implements IRemittanceRefundBillService {

	@SuppressWarnings("unchecked")
	@Override
	public List<RemittanceRefundBill> queryRemittanceRefundBill(RemittanceRefundBillQueryModel queryModel, Page page) {
		
		if (CollectionUtils.isEmpty(queryModel.getFinancialContractUuidList())
				|| CollectionUtils.isEmpty(queryModel.getPaymentInstitutionNameList())) {
			return Collections.emptyList();
		}

		StringBuffer querySB = new StringBuffer(100);
		Map<String ,Object> paramters = new HashMap<String, Object>();
		
		genQuerySentence(queryModel, querySB, paramters);
		
		if (page == null) {
			return  this.genericDaoSupport.searchForList(querySB.toString(), paramters);
		} else {
			return this.genericDaoSupport.searchForList(querySB.toString(), paramters, page.getBeginIndex(), page.getEveryPage());
		}
	}
	
	private void genQuerySentence(RemittanceRefundBillQueryModel queryModel, StringBuffer querySB, Map<String ,Object> paramters){
		
		querySB.append("FROM RemittanceRefundBill WHERE financialContractUuid IN (:financialContractUuids) AND paymentGateway IN (:paymentGateways)");
		paramters.put("financialContractUuids", queryModel.getFinancialContractUuidList());
		paramters.put("paymentGateways", queryModel.getPaymentInstitutionNameList());
		
		if(StringUtils.isNotBlank(queryModel.getChannelCashFlowNo())){
			querySB.append(" AND channelCashFlowNo = :channelCashFlowNo");
			paramters.put("channelCashFlowNo", queryModel.getChannelCashFlowNo());
		}
		
		if(queryModel.getStartDateValue()!= null){
			querySB.append(" AND createTime >= :startDate");
			paramters.put("startDate",queryModel.getStartDateValue() );
		}
		
		if(queryModel.getEndDateValue()!= null){
			querySB.append(" AND createTime <= :endDate");
			paramters.put("endDate",queryModel.getEndDateValue() );
		}
		
		if(StringUtils.isNotBlank(queryModel.getReturnedAccountNo())){
			querySB.append(" AND hostAccountNo = :returnedAccountNo");
			paramters.put("returnedAccountNo", queryModel.getReturnedAccountNo());
		}
		
		querySB.append(queryModel.getOrderBySentence());
	}

	@Override
	public int queryCount(RemittanceRefundBillQueryModel queryModel) {
		
		if (CollectionUtils.isEmpty(queryModel.getFinancialContractUuidList())
				|| CollectionUtils.isEmpty(queryModel.getPaymentInstitutionNameList())) {
			return 0;
		}
		
		StringBuffer querySB = new StringBuffer(100);
		Map<String ,Object> paramters = new HashMap<String, Object>();
		
		genQuerySentence(queryModel, querySB, paramters);
		
		return this.genericDaoSupport.count(querySB.toString(), paramters);
	}

	@Override
	public List<RemittanceRefundBill> listRemittanceRefundBill(String relatedExecReqNo) {
		if(StringUtils.isEmpty(relatedExecReqNo)){
			return Collections.emptyList();
		}
		Filter filter = new Filter();
		filter.addEquals("relatedExecReqNo", relatedExecReqNo);
		return this.list(RemittanceRefundBill.class, filter);
	}

}

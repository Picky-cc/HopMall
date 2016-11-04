package com.zufangbao.sun.yunxin.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.LoanBatch;
import com.zufangbao.sun.yunxin.entity.model.loanbatch.LoanBatchQueryModel;
import com.zufangbao.sun.yunxin.service.LoanBatchService;

@Service("LoanBatchService")
public class LoanBatchServiceImpl extends GenericServiceImpl<LoanBatch>
		implements LoanBatchService {

	@Autowired
	private GenericDaoSupport genericDaoSupport;

	@Override
	public LoanBatch getLoanBatchBycode(String code) {
		List<LoanBatch> loanBatchs = genericDaoSupport.searchForList(
				"from LoanBatch where code = :code", "code", code);
		if (loanBatchs.isEmpty()) {
			return null;
		}
		return loanBatchs.get(0);
	}
 
	@Override
	public void activationLoanBatch(LoanBatch loanBatch) {
		loanBatch.setAvailable(true);
		loanBatch.setLastModifiedTime(new Date());
		loanBatch.setLoanDate(new  Date());
		update(loanBatch);
	}

	@Override
	public LoanBatch createAndSaveLoanBatch(FinancialContract financialContract, boolean activeStatus) {
		String loanBatchCode = LoanBatch.castLoanBatchNo(financialContract);
		LoanBatch loanBatch = new LoanBatch(financialContract, loanBatchCode, activeStatus);
		this.save(loanBatch);
		return loanBatch;
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public List<LoanBatch> queryLoanBatchIdsByQueryModel(LoanBatchQueryModel loanBatchQueryModel, Page page) {
		StringBuffer querySb =  new StringBuffer("FROM LoanBatch WHERE financialContract.financialContractUuid IN (:financialContractIdList)");
		
		querySb = buildQueryStringBy(loanBatchQueryModel, querySb);
		querySb.append(" ORDER BY id DESC ");
		
		Map<String, Object> params = buildParamsBy(loanBatchQueryModel);
		
		if(page == null) {
			return genericDaoSupport.searchForList(querySb.toString(), params);
		}else {
			return genericDaoSupport.searchForList(querySb.toString(), params, page.getBeginIndex(), page.getEveryPage());
		}
	}
	
	@Override
	public int countLoanBatchList(LoanBatchQueryModel loanBatchQueryModel) {
		StringBuffer querySb =  new StringBuffer("FROM LoanBatch WHERE financialContract.financialContractUuid IN (:financialContractIdList)");
		querySb = buildQueryStringBy(loanBatchQueryModel, querySb);
		return genericDaoSupport.count(querySb.toString(), buildParamsBy(loanBatchQueryModel));
	}
	
	private StringBuffer buildQueryStringBy(LoanBatchQueryModel loanBatchQueryModel, StringBuffer querySb) {
		if(loanBatchQueryModel.getStartDateValue()!=null){
			querySb.append(" AND createTime >= :startDate");
		}
		if(loanBatchQueryModel.getEndDateValue()!=null){
			querySb.append(" AND createTime < :endDate");
		}
		if(StringUtils.isNotBlank(loanBatchQueryModel.getLoanBatchCode())) {
			querySb.append(" AND code LIKE:batchCode") ;
		}
		return querySb;
	}
	
	private Map<String, Object> buildParamsBy(LoanBatchQueryModel loanBatchQueryModel) {
		List<String> financialContractIdList = loanBatchQueryModel.getFinancialContractUuidList();
		if(CollectionUtils.isEmpty(financialContractIdList)){
			financialContractIdList.add("");
		}
		
		Map<String, Object> params = new HashMap<>();
		params.put("financialContractIdList", financialContractIdList);

		if(loanBatchQueryModel.getStartDateValue()!=null){
			params.put("startDate", loanBatchQueryModel.getStartDateValue());
		}
		
		if(loanBatchQueryModel.getEndDateValue()!=null){
			params.put("endDate", loanBatchQueryModel.getEndDateValue());
		}
		
		if(StringUtils.isNotBlank(loanBatchQueryModel.getLoanBatchCode())) {
			params.put("batchCode", "%"+loanBatchQueryModel.getLoanBatchCode()+"%");
		}
		return params;
	}

	@Override
	public LoanBatch getLoanBatchByUUid(String Uuid) {
		if(StringUtils.isEmpty(Uuid)){
			return null;
		}
		Filter filter = new Filter();
		filter.addEquals("loanBatchUuid", Uuid);
		List<LoanBatch> list = this.list(LoanBatch.class, filter);
		if(CollectionUtils.isEmpty(list)) {
			return null; 
		}
		return list.get(0);
	}

}

package com.zufangbao.wellsfargo.silverpool.cashauditing.service.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo2do.core.persistence.support.Filter;
import com.demo2do.core.service.impl.GenericServiceImpl;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.order.RepaymentAuditStatus;
import com.zufangbao.sun.ledgerbook.ChartOfAccount;
import com.zufangbao.sun.ledgerbook.LedgerItemService;
import com.zufangbao.sun.ledgerbook.TAccountInfo;
import com.zufangbao.sun.yunxin.entity.OfflineBill;
import com.zufangbao.sun.yunxin.entity.SourceDocumentStatus;
import com.zufangbao.sun.yunxin.entity.model.AccountsPrepaidModel;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentExcuteResult;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentExcuteStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentImporter;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentType;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentService;

@Service("sourceDocumentService")
public class SourceDocumentServiceImpl extends
		GenericServiceImpl<SourceDocument> implements SourceDocumentService {


	@Autowired
	private LedgerItemService ledgerItemService;
	
	private static Log logger = LogFactory.getLog(SourceDocumentServiceImpl.class);
	
	@Override
	public SourceDocument getSourceDocumentBy(String sourceDocumentUuid) {
		if(StringUtils.isEmpty(sourceDocumentUuid)) {
			return null;
		}
		 
		List<SourceDocument> sourceDocumentList = genericDaoSupport.searchForList("FROM SourceDocument WHERE sourceDocumentUuid =:sourceDocumentUuid", "sourceDocumentUuid", sourceDocumentUuid);
		if(CollectionUtils.isEmpty(sourceDocumentList)) {
			return null;
		}
		return sourceDocumentList.get(0);
	}

	
	@Override
	public SourceDocument getIssuedSourceDocumentBy(Long financeCompanyId, Long outlierCompanyId, String outlierDocumentUuid) {
		if (null == financeCompanyId || null == outlierCompanyId || StringUtils.isEmpty(outlierDocumentUuid)) {
			return null;
		}
		
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("outlierDocumentUuid", outlierDocumentUuid);
		parms.put("companyId", financeCompanyId);
		parms.put("outlierCompanyId", outlierCompanyId);
		parms.put("issued", RepaymentAuditStatus.ISSUED);
		parms.put("invalid", RepaymentAuditStatus.INVALID);

		List<SourceDocument> sourceDocumentList = genericDaoSupport.searchForList("FROM SourceDocument WHERE outlierDocumentUuid =:outlierDocumentUuid AND companyId =:companyId AND outlierCompanyId =:outlierCompanyId AND (auditStatus =:issued OR auditStatus =:invalid)", parms);
		
		if(CollectionUtils.isEmpty(sourceDocumentList)) {
			return null;
		}
		return sourceDocumentList.get(0);
	}


	@Override
	public SourceDocument getSourceDocumentBy(Long companyId, String outlierDocumentUuid) {
		
		if (null == companyId || StringUtils.isEmpty(outlierDocumentUuid)) {
			return null;
		}
		
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("outlierDocumentUuid", outlierDocumentUuid);
		parms.put("companyId", companyId);
		
		List<SourceDocument> sourceDocumentList = genericDaoSupport.searchForList("FROM SourceDocument WHERE outlierDocumentUuid =:outlierDocumentUuid AND companyId =:companyId", parms);
		if(CollectionUtils.isEmpty(sourceDocumentList)) {
			return null;
		}
		return sourceDocumentList.get(0);
	}


	@Override
	public SourceDocument getSourceDocumentBy(Long financeCompanyId, Long outlierCompanyId, String outlierDocumentUuid) {
		if (null == financeCompanyId || null == outlierCompanyId || StringUtils.isEmpty(outlierDocumentUuid)) {
			return null;
		}
		
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("outlierDocumentUuid", outlierDocumentUuid);
		parms.put("companyId", financeCompanyId);
		parms.put("outlierCompanyId", outlierCompanyId);
		parms.put("invalid", RepaymentAuditStatus.INVALID);

		List<SourceDocument> sourceDocumentList = genericDaoSupport.searchForList("FROM SourceDocument WHERE outlierDocumentUuid =:outlierDocumentUuid AND companyId =:companyId AND outlierCompanyId =:outlierCompanyId AND auditStatus <>:invalid", parms);
		
		if(CollectionUtils.isEmpty(sourceDocumentList)) {
			return null;
		}
		return sourceDocumentList.get(0);
	}
	
	@Override
	public boolean existsBatchPayRecordUuid(String batchPayRecordUuid, AccountSide sourceAccountSide, Long companyId) {
		Filter filter = new Filter();
		filter.addEquals("outlierDocumentUuid", batchPayRecordUuid);
		filter.addEquals("sourceAccountSide", sourceAccountSide);
		filter.addEquals("companyId", companyId);
		List<SourceDocument> sourceDocumentList = this.list(SourceDocument.class, filter);
		if(CollectionUtils.isEmpty(sourceDocumentList)){
			return false;
		}
		return true;
	}

	@Override
	public List<SourceDocument> getSourceDocuments(SourceDocumentType sourceDocumentType,
			AccountSide sourceAccountSide, Long companyId, SourceDocumentStatus sourceDocumentStatus, String firstOutlier) {
		Filter filter = new Filter();
		filter.addEquals("companyId", companyId);
		filter.addEquals("sourceDocumentType", sourceDocumentType);
		filter.addEquals("sourceAccountSide", sourceAccountSide);
		filter.addEquals("sourceDocumentStatus",sourceDocumentStatus);
		filter.addEquals("firstOutlierDocType",firstOutlier);
		return this.list(SourceDocument.class, filter);
	}
	
	@Override
	public void signSourceDocument(SourceDocument sourceDocument, BigDecimal issuedAmount) {
		
		sourceDocument.setSourceDocumentStatus(SourceDocumentStatus.SIGNED);
		sourceDocument.setIssuedTime(new Date());
		sourceDocument.changeBookingAmountAndAuditStatus(issuedAmount);
		this.update(sourceDocument);
		
	}


	@Override
	public void createSourceDocumentBy(Long companyId, OfflineBill offlineBill) {
		SourceDocument sourceDocument = SourceDocumentImporter.createSourceDocumentFrom(companyId, offlineBill);
		this.save(sourceDocument);
	}

	public List<SourceDocument> getSourceDocumentBy(int repaymentAuditStatus,
			String outlierDocumentUuid) {
		Filter filter  =new Filter();
		filter.addEquals("auditStatus", RepaymentAuditStatus.formValue(repaymentAuditStatus));
		filter.addEquals("outlierDocumentUuid", outlierDocumentUuid);
		return this.list(SourceDocument.class ,filter);
	}

	public List<SourceDocument> getSourceDocumentBy(RepaymentAuditStatus repaymentAuditStatus,
			String outlierDocumentUuid) {
		Filter filter  =new Filter();
		if(repaymentAuditStatus!=null){
			
			filter.addEquals("auditStatus",repaymentAuditStatus);
		}
		filter.addEquals("outlierDocumentUuid", outlierDocumentUuid);
		return this.list(SourceDocument.class ,filter);
	}

	@Override
	public List<SourceDocument> getSourceDocumentByOfflineBillUuid(
			String offlineBillUuid) {
		Filter filter  =new Filter();
		filter.addEquals("outlierDocumentUuid", offlineBillUuid);
		return this.list(SourceDocument.class ,filter);
	}
	
	@Override
	public SourceDocument getOneSourceDocuments(SourceDocumentType sourceDocumentType, AccountSide sourceAccountSide, SourceDocumentStatus sourceDocumentStatus, String firstOutlierType, String outlierDocumentUuid){
		Filter filter = new Filter();
		filter.addEquals("sourceDocumentType", sourceDocumentType);
		filter.addEquals("sourceAccountSide", sourceAccountSide);
		filter.addEquals("sourceDocumentStatus",sourceDocumentStatus);
		filter.addEquals("firstOutlierDocType",firstOutlierType);
		filter.addEquals("outlierDocumentUuid",outlierDocumentUuid);
		List<SourceDocument> sourceDocumentList = this.list(SourceDocument.class, filter);
		if(CollectionUtils.isEmpty(sourceDocumentList)){
			return null;
		}
		return sourceDocumentList.get(0);
	}


	@Override
	public List<SourceDocument> getSourceDocumentListBy(Collection<String> sourceDocumentUuids, SourceDocumentStatus sourceDocumentStatus) {
		if(CollectionUtils.isEmpty(sourceDocumentUuids)){
			return Collections.emptyList();
		}
		String querySentence= "FROM SourceDocument WHERE sourceDocumentUuid IN (:sourceDocumentUuids) AND sourceDocumentStatus =:sourceDocumentStatus";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sourceDocumentUuids", sourceDocumentUuids);
		params.put("sourceDocumentStatus", sourceDocumentStatus);
		return genericDaoSupport.searchForList(querySentence, params);
	}


	@Override
	public Set<String> extractOutlierDocumentUuid(List<SourceDocument> sourceDocumentList, String firstOutlierDocType) {
		Set<String> outlierDocumentUuid = new HashSet<String>();
		for (SourceDocument sourceDocument : sourceDocumentList) {
			if(sourceDocument==null) continue;
			if(StringUtils.equals(sourceDocument.getFirstOutlierDocType(), firstOutlierDocType)){
				outlierDocumentUuid.add(sourceDocument.getOutlierDocumentUuid());
			}
		}
		return outlierDocumentUuid;
	}


	@Override
	public SourceDocument getBusinessPaymentSourceDocument(Long financeCompanyId, Long outlierCompanyId, String outlierSerialGlobalIdentity) {
		if (null == financeCompanyId || null == outlierCompanyId || StringUtils.isEmpty(outlierSerialGlobalIdentity)) {
			return null;
		}
		
		Map<String, Object> parms = new HashMap<String, Object>();
		parms.put("outlierSerialGlobalIdentity", outlierSerialGlobalIdentity);
		parms.put("companyId", financeCompanyId);
		parms.put("outlierCompanyId", outlierCompanyId);
		parms.put("invalid", RepaymentAuditStatus.INVALID);

		List<SourceDocument> sourceDocumentList = genericDaoSupport.searchForList("FROM SourceDocument WHERE outlierSerialGlobalIdentity =:outlierSerialGlobalIdentity AND companyId =:companyId AND outlierCompanyId =:outlierCompanyId AND auditStatus <>:invalid", parms);
		
		if(CollectionUtils.isEmpty(sourceDocumentList)) {
			return null;
		}
		return sourceDocumentList.get(0);
	}

	public List<SourceDocument> getSourceDocumentList(
			AccountsPrepaidModel accountsPrepaidModel, Page page) {
		Map<String,Object> parameters = new HashMap<String,Object>();
		String querySentence = getContractFilter(accountsPrepaidModel,parameters);
		if(page==null){
			return genericDaoSupport.searchForList(querySentence, parameters);
		}
		return genericDaoSupport.searchForList(querySentence, parameters,page.getBeginIndex(),page.getEveryPage());
		
	}
	
	private String getContractFilter(AccountsPrepaidModel acountsPrepaidModel,Map<String,Object> parameters) {
		if(acountsPrepaidModel==null){
			return null;
		}
		
		StringBuffer queryString = new StringBuffer("from SourceDocument where 1=1 ");
		
		if (is_where_condition(acountsPrepaidModel.getFinancialContractUuid())) {
			queryString.append(" AND financialContractUuid=:financialContractUuid");
			parameters.put("financialContractUuid", acountsPrepaidModel.getFinancialContractUuid());
		}
		if (acountsPrepaidModel.getSourceDocumentStatusEnum()!= null) {
			queryString.append(" AND sourceDocumentStatus=:sourceDocumentStatus");
			parameters.put("sourceDocumentStatus", acountsPrepaidModel.getSourceDocumentStatusEnum());
		}
		if (acountsPrepaidModel.getCustomerTypeEnum()!= null) {
			queryString.append(" AND firstPartyType=:customerType");
			parameters.put("customerType", acountsPrepaidModel.getCustomerTypeEnum());
		}
		if (is_where_condition(acountsPrepaidModel.getKey())) {
			queryString.append(" AND ((firstPartyName like :key) or (virtualAccountNo like :key) ");
			parameters.put("key", "%"+acountsPrepaidModel.getKey()+"%");
			if(acountsPrepaidModel.getAmountFromKey()!=null){
				queryString.append(" or (bookingAmount=:amount) ");
				parameters.put("amount", acountsPrepaidModel.getAmountFromKey());
			}
			queryString.append(" ) ");
		}
		
		TAccountInfo unearned_account=ChartOfAccount.EntryBook().get(ChartOfAccount.SND_LIABILITIES_INDEPENDENT_CUSTOMER_DEPOSIT);
		String queryDepositType = build_3lvl_filter(unearned_account);
		if(!StringUtils.isEmpty(queryDepositType)){
			queryString.append(queryDepositType);
		}
		queryString.append(" order by id desc ");
		
		return queryString.toString();
	}
	
	private String build_3lvl_filter(TAccountInfo account){
		String accountFilter= "";
		if(account.getFirstLevelAccount()!=null&&!StringUtils.isEmpty(account.getFirstLevelAccount().getAccountCode())) {
			accountFilter+=" AND firstAccountId='"+account.getFirstLevelAccount().getAccountCode()+"'";
			if(account.getSecondLevelAccount()!=null&&!StringUtils.isEmpty(account.getSecondLevelAccount().getAccountCode()))
			{
				accountFilter+=" AND secondAccountId='"+account.getSecondLevelAccount().getAccountCode()+"'";
				if(account.getThirdLevelAccount()!=null&&!StringUtils.isEmpty(account.getThirdLevelAccount().getAccountCode()))
				{
					accountFilter+=" AND thirdAccountId='"+account.getThirdLevelAccount().getAccountCode()+"'";
				}
			}
		
		}
		return accountFilter;
	}
	
	private boolean is_where_condition(String address) {
		return !StringUtils.isEmpty(address);
	}


	@Override
	public int count(AccountsPrepaidModel accountsPrepaidModel) {
		Map<String,Object> parameters = new HashMap<String,Object>();
		String querySentence = getContractFilter(accountsPrepaidModel,parameters);
		return genericDaoSupport.count(querySentence,parameters);
	}
	
	@Override
	public List<SourceDocument> getDepositReceipt(String cashFlowUuid) {
		StringBuffer querySentence = new StringBuffer("From SourceDocument where outlierDocumentUuid=:cashFlowUuid ");
		TAccountInfo account =ChartOfAccount.EntryBook().get(ChartOfAccount.SND_LIABILITIES_INDEPENDENT_CUSTOMER_DEPOSIT);
		querySentence.append(build_3lvl_filter(account));
		return genericDaoSupport.searchForList(querySentence.toString(), "cashFlowUuid", cashFlowUuid);
	}
	
	public List<Long> get_deposit_source_document_connected_by(SourceDocumentExcuteResult excuteResult,SourceDocumentExcuteStatus excuteStatus){
		StringBuffer queryString = new StringBuffer("from SourceDocument where excuteStatus=:excuteStatus "
				+ " AND excuteResult=:excuteResult ");
		Map<String,Object> params = new HashMap<String,Object>();
		TAccountInfo deposit_receipt_account=ChartOfAccount.EntryBook().get(ChartOfAccount.SND_LIABILITIES_INDEPENDENT_CUSTOMER_DEPOSIT);
		String queryDepositType = build_3lvl_filter(deposit_receipt_account);
		queryString.append(queryDepositType);
		params.put("excuteResult", excuteResult);
		params.put("excuteStatus", excuteStatus);
		List<SourceDocument> sourceDocumentList = genericDaoSupport.searchForList(queryString.toString(),params);
		return sourceDocumentList.stream().map(sour->sour.getId()).collect(Collectors.toList());
	}


	@Override
	public void cancelSourceDocumentDetailAttach(String sourceDocumentUuid, String bankTransactionNo) {
		String hql = "UPDATE SourceDocument SET outlierSerialGlobalIdentity = null Where sourceDocumentUuid = :sourceDocumentUuid AND outlierSerialGlobalIdentity = :outlierSerialGlobalIdentity";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sourceDocumentUuid", sourceDocumentUuid);
		params.put("outlierSerialGlobalIdentity", bankTransactionNo);
		genericDaoSupport.executeHQL(hql, params);
	}
	
	@Override
	public SourceDocument getSourceDocumentBy(Long id) {
		return this.genericDaoSupport.get(SourceDocument.class, id);
	}


	@Override
	public void update_after_inter_account_transfer(Long id, BigDecimal totalIssuedAmount) {
		SourceDocument sourceDocument = getSourceDocumentBy(id);
		if(sourceDocument==null){
			return;
		}
		boolean result = totalIssuedAmount.compareTo(sourceDocument.getBookingAmount())==0;
		sourceDocument.updateAuditStatus(totalIssuedAmount);
		sourceDocument.update_after_inter_account_transfer(result);
		this.saveOrUpdate(sourceDocument);
		
	}

}

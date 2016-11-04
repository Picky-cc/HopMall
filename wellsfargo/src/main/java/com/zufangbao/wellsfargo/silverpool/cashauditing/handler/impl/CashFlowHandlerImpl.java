package com.zufangbao.wellsfargo.silverpool.cashauditing.handler.impl;

import static com.zufangbao.wellsfargo.greypool.document.entity.leasingdocument.GeneralLeasingDocumentTypeDictionary.DefaultTypeUuid.DEFAULT_BUSINESS_VOUCHER_TYPE_UUID;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.account.AuditStatus;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.icbc.business.AppArriveRecord;
import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.service.AppArriveRecordService;
import com.zufangbao.sun.service.CashFlowService;
import com.zufangbao.sun.service.CompanyService;
import com.zufangbao.sun.service.ContractAccountService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.OrderType;
import com.zufangbao.sun.yunxin.entity.remittance.CashFlowExportModel;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.BillMatchResult;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.CashAuditVoucherSet;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.MatchParams;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.VoucherOperationInfo;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.BusinessVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalAccount;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherCheckingLevel;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentDetail;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.AfterVoucherIssuedHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.CashFlowHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.BusinessVoucherService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.JournalVoucherService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentDetailService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentService;
import com.zufangbao.wellsfargo.yunxin.handler.JournalVoucherHandler;

@Component("cashFlowHandler")
public class CashFlowHandlerImpl implements CashFlowHandler {

	@Autowired
	private AppArriveRecordService appArriveRecordService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private JournalVoucherService journalVoucherService;
	
	@Autowired
	private BusinessVoucherService businessVoucherService;
	
	@Autowired
	private ContractAccountService contractAccountService;
	
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	
	@Autowired
	private JournalVoucherHandler journalVoucherHandler;
	
	@Autowired
	private OrderService orderService;

	@Autowired
	private CashFlowService cashFlowService;

	@Autowired
	private SourceDocumentService sourceDocumentService;
	
	@Autowired
	private SourceDocumentDetailService sourceDocumentDetailService;
	
	private static Log logger = LogFactory.getLog(CashFlowHandlerImpl.class);
	
	@Override
	public List<BillMatchResult> getMatchedBillInDbBy(String cashFlowUuid, AccountSide accountSide) {
		List<BillMatchResult> billMatchResultList = new ArrayList<BillMatchResult>();
		MatchParams matchParams = getMatchParams(cashFlowUuid, accountSide);
		if (matchParams == null){
			return billMatchResultList;
		}
		List<BillMatchResult> matchedBillResult = searchMatchedBill(matchParams);
		List<BillMatchResult> billMatchResultInMysql = searchIssuedVoucher(matchParams);
		List<BillMatchResult> mergedBillMatch = merge(matchedBillResult, billMatchResultInMysql, matchParams);
		return mergedBillMatch;
	}

	private MatchParams getMatchParams(String cashFlowUuid, AccountSide accountSide) {
		AppArriveRecord appArriveRecord = appArriveRecordService.getArriveRecordByCashFlowUuid(cashFlowUuid);
		if (appArriveRecord == null){
			return null;
		}
		Long companyId = companyService.getCompanyIdBy(appArriveRecord.getApp());
		return new MatchParams(cashFlowUuid,companyId, appArriveRecord.getPayAcNo(),appArriveRecord.getPayName(),appArriveRecord.getAmount(), appArriveRecord.getTime(), accountSide,appArriveRecord.getRemark());
	}

	@Override
	public List<BillMatchResult> searchMatchedBill(MatchParams matchParams) {
		if (matchParams == null){
			return Collections.emptyList();
		}
		List<ContractAccount> contractAccountList = contractAccountService.get_match_contract_account_list_by_payerName_accountNo_bankName(matchParams.getAccountOwnerName(),matchParams.getAccount(),StringUtils.EMPTY, matchParams.getRemark());
		//暂时先推荐一个
		List<Order> orderList = new ArrayList<Order>();
		
		for (ContractAccount contractAccount: contractAccountList){
			Contract contract = contractAccount.getContract();
			AssetSet assetSet = repaymentPlanService.getFirstUnClearAsset(contract);
			if(assetSet==null){
				continue;
			}
			Order order = orderService.getLastUnclearedRepaymentOrder(assetSet);
			if(order==null){
				continue;
			}
			orderList.add(order);
		}
		
		return convertToBillMatchResultList(matchParams,orderList);
	}

	@Override
	public List<BillMatchResult> convertToBillMatchResultList(MatchParams matchParams, List<Order> orderList) {
		List<BillMatchResult> billMatchResults = new ArrayList<BillMatchResult>();
		if (CollectionUtils.isEmpty(orderList)){
			return billMatchResults;
		}
		for (Order order:orderList){
			if (order == null){
				continue;
			}
			BillMatchResult billMatchResult = generateBillingPlanPartOfDebitBillMatchResultFrom(order, matchParams);
			billMatchResults.add(billMatchResult);
		}
		return billMatchResults;
	}

	@Override
	public BillMatchResult generateBillingPlanPartOfDebitBillMatchResultFrom(Order order, MatchParams matchParams){
		if (order == null || matchParams == null){
			return null;
		}

		BillMatchResult billMatchResult = new BillMatchResult(matchParams.getCashFlowUuid(),matchParams.getAccountSide(),order.getRepaymentBillId());
		fillBillingPlanData(billMatchResult, matchParams.getCompanyId());
		return billMatchResult;
	}

	@Override
	public List<BillMatchResult> searchIssuedVoucher(MatchParams matchParams) {
		if (matchParams == null){
			return Collections.EMPTY_LIST;
		}
		List<JournalVoucher> journalVoucherList = journalVoucherService.getJournalVoucherBy(matchParams.getCashFlowUuid(),matchParams.getAccountSide(),null,matchParams.getCompanyId(), JournalVoucherStatus.VOUCHER_ISSUED);
		
		return fillMatchResultWith(journalVoucherList);
	}

	@Override
	public List<BillMatchResult> fillMatchResultWith(List<JournalVoucher> journalVoucherList) {
		List<BillMatchResult> billMatchResults = new ArrayList<BillMatchResult>();
		if(CollectionUtils.isEmpty(journalVoucherList)){
			return billMatchResults;
		}
		for(JournalVoucher journalVoucher:journalVoucherList){
			if (journalVoucher == null){
				continue;
			}
			BusinessVoucher businessVoucher = businessVoucherService.getInForceBusinessVoucherByBillingPlanUidAndType(journalVoucher.getBillingPlanUuid(),DEFAULT_BUSINESS_VOUCHER_TYPE_UUID);
			if (businessVoucher == null){
				continue;
			}
			BillMatchResult billMatchResult = BillMatchResult.createBillMatchResult( journalVoucher, businessVoucher);
			
			billMatchResults.add(billMatchResult);
		}
		return billMatchResults;
	}

	private BillMatchResult findVoucherAndRemove(List<BillMatchResult> billMatchResultList, String billingPlanUuid){
		for (BillMatchResult billMatchResult : billMatchResultList) {
			if (billMatchResult==null) continue;
			if(StringUtils.equals(billMatchResult.getBillingPlanUuid(), billingPlanUuid)){
				billMatchResultList.remove(billMatchResult);
				return billMatchResult;
			}
		}
		return null;
	}
	
	@Override
	public List<BillMatchResult> merge(List<BillMatchResult> matchedBillingPlan,
					List<BillMatchResult> issuedVouchers, MatchParams matchParams) {
		if (matchParams == null){
			return Collections.EMPTY_LIST;
		}
		
		List<BillMatchResult> allResults=  new ArrayList<BillMatchResult>();
		
		for (BillMatchResult billPlanPart : matchedBillingPlan) {
			fillVoucherPartAndRemoveTheVoucher(issuedVouchers, matchParams, billPlanPart);
		}
		for (BillMatchResult voucherPart : issuedVouchers) {
			if(voucherPart==null) continue;
			fillBillingPlanData(voucherPart, matchParams.getCompanyId());
		}
		allResults.addAll(matchedBillingPlan);
		allResults.addAll(issuedVouchers);
		return allResults;
		
		
		/*HashMap<String,List<BillMatchResult> > mergedAllResults= new HashMap<String,List<BillMatchResult> >();
		allResults.addAll(matchedBillingPlan);
		allResults.addAll(issuedVouchers);
		List<BillMatchResult> mergeBillMatchResult = new ArrayList<BillMatchResult>();
		for(BillMatchResult result:allResults){
			if (result == null) continue;
			List<BillMatchResult> resultOfBillingPlan=mergedAllResults.get(result.getBillingPlanUuid());
			if(resultOfBillingPlan==null ) {
				resultOfBillingPlan=new ArrayList<BillMatchResult>();
			}
			resultOfBillingPlan.add(result);
			mergedAllResults.put(result.getBillingPlanUuid(),resultOfBillingPlan);
		}
		
		for(Entry<String,List<BillMatchResult>> resultOfBillingPlan:mergedAllResults.entrySet()){
			List<BillMatchResult> results=resultOfBillingPlan.getValue();
			BillMatchResult billMatchResult = mergeOneBillMatchResult(results, matchParams.getCompanyId(),matchParams.getAccountSide());
			if (billMatchResult == null) continue;
			mergeBillMatchResult.add(billMatchResult);
		}
		
		return mergeBillMatchResult;*/
	}

	private void fillVoucherPartAndRemoveTheVoucher(
			List<BillMatchResult> issuedVouchers, MatchParams matchParams,
			BillMatchResult matchedBillPart) {
		if(matchedBillPart==null)
			return;
		BillMatchResult issuedVoucher = findVoucherAndRemove(issuedVouchers,matchedBillPart.getBillingPlanUuid());
		if(issuedVoucher==null){
			BigDecimal issuedAmount = journalVoucherService.getBookingAmountSumOfIssueJournalVoucherBy(matchedBillPart.getBillingPlanUuid(), matchParams.getCompanyId(), new JournalAccount(matchParams.getAccountSide())); 
			matchedBillPart.fillVoucherPart(issuedAmount);
		} else {
			matchedBillPart.fillVoucherPart(issuedVoucher);
		}
	}

	private BillMatchResult mergeOneBillMatchResult(List<BillMatchResult> results, Long companyId, AccountSide accountSide) {
		if (CollectionUtils.isEmpty(results)){
			return null;
		}
		BillMatchResult billingPlanPart = null;
		BillMatchResult voucherPart = null;
		for (BillMatchResult billMatchResult:results){
			if (billMatchResult == null) continue;
			
			if (StringUtils.isEmpty(billMatchResult.getJournalVoucherUuid())){
				billingPlanPart = billMatchResult;
				continue;
			}
			voucherPart = billMatchResult;
		}
		if (voucherPart== null && billingPlanPart==null) return null;
		if(voucherPart == null){
			BigDecimal issuedAmount = journalVoucherService.getBookingAmountSumOfIssueJournalVoucherBy(billingPlanPart.getBillingPlanUuid(), companyId, new JournalAccount(accountSide)); 
			billingPlanPart.fillVoucherPart(issuedAmount);
			return billingPlanPart;
		}
		if(billingPlanPart == null){
			fillBillingPlanData(voucherPart, companyId);
			return voucherPart;
		}
		billingPlanPart.fillVoucherPart(voucherPart);
		
		return billingPlanPart;
	}

	/*@Override
	public void fillBillingPlanDataIn(List<BillMatchResult> billMatchResults, Long companyId) {
		if (CollectionUtils.isEmpty(billMatchResults) || companyId == null){
			return;
		}
		for(BillMatchResult billMatchResult: billMatchResults){
			if (billMatchResult == null){
				continue;
			}
			
			fillBillingPlanData(billMatchResult, companyId);
		}
	}*/

	@Override
	public void fillBillingPlanData(BillMatchResult billMatchResult, Long companyId) {
		if (billMatchResult == null || companyId == null){
			return;
		}
		String billingPlanUuid = billMatchResult.getBillingPlanUuid();
		if (StringUtils.isEmpty(billingPlanUuid)){
			return;
		}
		Order order = orderService.getOrderByRepaymentBillId(billingPlanUuid);
		if(order == null){
			return;
		}
		Map<String,String> accountAndNameMaps = new HashMap<String,String>();
		
		if(order.getOrderType()==OrderType.NORMAL){
			List<ContractAccount> contractAccountList = contractAccountService.getContractAccountsBy(order.getAssetSet().getContract());
			accountAndNameMaps = getAccountAndNamesMap(contractAccountList);
		}
		billMatchResult.fillBillingPlanData(order, accountAndNameMaps);
		
	}
	private Map<String,String> getAccountAndNamesMap(List<ContractAccount> contractAccountList){
		Map<String,String> accountAndNameMaps = new HashMap<String,String>();
		for (ContractAccount contractAccount : contractAccountList) {
			if(contractAccount==null) continue;
			accountAndNameMaps.put(contractAccount.getPayAcNo(), contractAccount.getPayerName());
		}
		return accountAndNameMaps;
	}
	

	@Override
	public AuditStatus changeVouchers(List<BillMatchResult> billMatchResultList,Long companyId, String cashFlowUuid, AccountSide accountSide, AfterVoucherIssuedHandler afterVoucherIssuedHandler) throws Exception{
		if(companyId==null || StringUtils.isEmpty(cashFlowUuid) || accountSide==null){
			return null;
		}
		CashAuditVoucherSet cashAuditVoucherSet = new CashAuditVoucherSet(companyId,accountSide,cashFlowUuid);
		
		List<JournalVoucher> vouchersToBeLapse = journalVoucherService.lapse_issued_jvs_of_cash_flow(cashFlowUuid, accountSide);

		cashAuditVoucherSet.collectLapsedVoucher(vouchersToBeLapse);
		Set<String> lapsedBillingPlanUuid = journalVoucherService.getBillingPlanUuidsFrom(vouchersToBeLapse);
		cashAuditVoucherSet.collectBillingPlan(lapsedBillingPlanUuid);
		AppArriveRecord appArriveRecord = appArriveRecordService.getArriveRecordByCashFlowUuid(cashFlowUuid);
		
		String batchUuid = UUID.randomUUID().toString();
		for (BillMatchResult billMatchResult : billMatchResultList) {
			if(billMatchResult==null) continue;
			JournalVoucher issuedJournalVoucher = journalVoucherService.createIssuedJournalVoucherByCashFlow(billMatchResult.getBillingPlanUuid(), appArriveRecord, DEFAULT_BUSINESS_VOUCHER_TYPE_UUID, billMatchResult.getBookingAmount(), billMatchResult.getBusinessVoucherUuid(), JournalVoucherCheckingLevel.DOUBLE_CHECKING);
			issuedJournalVoucher.setBatchUuid(batchUuid);
			journalVoucherService.save(issuedJournalVoucher);
			cashAuditVoucherSet.collectIssuedVoucher(issuedJournalVoucher);
			cashAuditVoucherSet.collectBillingPlan(billMatchResult.getBillingPlanUuid());
		}
		//do after vouchers issued;
		afterVoucherIssuedHandler.handlerAfterVoucherIssued(cashAuditVoucherSet);
		return appArriveRecord.getAuditStatus();
	}
	
	@Override
	public Set<String> checkJVAndBill(List<BillMatchResult> billMatchResultList, Long companyId, String cashFlowUuid) {
		Set<String> dupicatedBillUuid = new HashSet<String>();
		
		if (StringUtils.isEmpty(cashFlowUuid) || companyId==null || CollectionUtils.isEmpty(billMatchResultList)){
			return dupicatedBillUuid;
		}
	
		List<String> duplicatedBillingPlanUuidsInDB = getDuplicatedBillPlanUuidInDB(billMatchResultList, cashFlowUuid);
		List<String> duplicatedBillingPlanUuids = getDuplicatedBillingPlanUuidByItself(billMatchResultList);
		
		dupicatedBillUuid.addAll(duplicatedBillingPlanUuidsInDB);
		dupicatedBillUuid.addAll(duplicatedBillingPlanUuids);
		
		return dupicatedBillUuid;
	}

	@Override
	public List<String> getDuplicatedBillingPlanUuidByItself(List<BillMatchResult> billMatchResultList) {
		List<String> duplicatedBillUuids = new ArrayList<String>();
		if(CollectionUtils.isEmpty(billMatchResultList)){
			return duplicatedBillUuids;
		}
		
		HashMap<String, Integer> agreegatedResults=new HashMap<String,Integer>();
		
		for(BillMatchResult billMatchResult:billMatchResultList){
			if (billMatchResult == null) continue;
			Integer count=agreegatedResults.get(billMatchResult.getBillingPlanUuid());
			if(count==null) count=1;
			else count++;
			agreegatedResults.put(billMatchResult.getBillingPlanUuid(),count);
		}
		
		Set<Entry<String, Integer > > aggreegatedResultSet=agreegatedResults.entrySet();
		for(Entry<String,Integer > result :aggreegatedResultSet)
		{
			if(result.getValue()>1)
				duplicatedBillUuids.add(result.getKey());
		}
		return duplicatedBillUuids;
	}

	@Override
	public List<String> getDuplicatedBillPlanUuidInDB(List<BillMatchResult> billMatchResultList, String cashFlowUuid) {
		Set<String> billUuidToBeCreated = new HashSet<String>();
		
		if (StringUtils.isEmpty(cashFlowUuid) || CollectionUtils.isEmpty(billMatchResultList)){
			return Collections.EMPTY_LIST;
		}
		for(BillMatchResult billMatchResult: billMatchResultList){
			if (billMatchResult == null){
				continue;
			}
			if(StringUtils.isEmpty(billMatchResult.getJournalVoucherUuid())){
				billUuidToBeCreated.add(billMatchResult.getBillingPlanUuid());
			}
		}
		return journalVoucherService.getBillUuidsInDebitIssuedJVListBy(cashFlowUuid, billUuidToBeCreated);
	}

	@Override
	public List<BillMatchResult> generateDebitBillMatchResultWithSettlingAmountFrom(List<Order> orderList, Long companyId, AccountSide accountSide) {
		List<BillMatchResult> billMatchResultList = new ArrayList<BillMatchResult>();
		if (CollectionUtils.isEmpty(orderList) || companyId == null){
			return billMatchResultList;
		}
		MatchParams matchParams = new MatchParams();
		matchParams.setCompanyId(companyId);
		for(Order order: orderList){
			try {
				BillMatchResult billMatchResult = generateBillingPlanPartOfDebitBillMatchResultFrom(order, matchParams);
				if (billMatchResult == null){
					continue;
				}
				BigDecimal issuedAmount = journalVoucherService.getBookingAmountSumOfIssueJournalVoucherBy(order.getRepaymentBillId(), companyId, new JournalAccount(accountSide));
				billMatchResult.fillVoucherPart(issuedAmount);
				billMatchResultList.add(billMatchResult);
			} catch (Exception e){
				logger.error("query by order["+order+"] occur error.");
				e.printStackTrace();
			}
		}
		return billMatchResultList;
	}

	@Override
	public AuditStatus refreshIssuedAmountAndAuditStatus(String cashFlowUuid, boolean isToBeClosed, AccountSide accountSide) {
		if(StringUtils.isEmpty(cashFlowUuid)){
			return null;
		}
		AppArriveRecord appArriveRecord = appArriveRecordService.getArriveRecordByCashFlowUuid(cashFlowUuid);
		if (appArriveRecord == null){
			logger.info("AppArriveRecord is null.");
			return null;
		}
		BigDecimal issuedAmount = journalVoucherService.getIssuedAmountByIssueJV(cashFlowUuid, new JournalAccount(accountSide));
		logger.info("IssuedAmount is "+ issuedAmount);
		AuditStatus auditStatus = refreshIssuedAmountAndAuditStatus(appArriveRecord, issuedAmount, isToBeClosed);
		logger.info("AuditStatus is "+ auditStatus);
		appArriveRecordService.save(appArriveRecord);
		logger.info("Save appArriveRecord.");
		return auditStatus;
	}
	
	private AuditStatus refreshIssuedAmountAndAuditStatus(AppArriveRecord appArriveRecord, BigDecimal issuedAmount, boolean isToBeClose){
		AuditStatus auditStatus = appArriveRecord.changeIssuedAmountAndAuditStatus(issuedAmount);
		logger.info("AuditStatus is "+ auditStatus);
		if(isToBeClose){
			appArriveRecord.setAuditStatus(AuditStatus.CLOSE);
			auditStatus = AuditStatus.CLOSE;
		}
		return auditStatus;
	}
	
	@Override
	public void lapseIssuedAndDebitVouchersAndUpdateStatus(String cashFlowUuid, VoucherOperationInfo operationInfo, AccountSide accountSide){
		if (StringUtils.isEmpty(cashFlowUuid)){
			return;
		}
		AppArriveRecord appArriveRecord = appArriveRecordService.getArriveRecordByCashFlowUuid(cashFlowUuid);
		if (appArriveRecord == null){
			return;
		}
		
		Long companyId = companyService.getCompanyIdBy(appArriveRecord.getApp());
		logger.info("companyId is "+companyId+".");
		List<JournalVoucher> issuedAndDebitJV = journalVoucherService.lapse_issued_jvs_of_cash_flow(cashFlowUuid, accountSide);
		
		operationInfo.addJournalVoucherList(issuedAndDebitJV);
		logger.info("lapse journalVoucherList suc.");
		Set<String> billingPlanUuidSet = journalVoucherService.getBillingPlanUuidsFrom(issuedAndDebitJV);
		
		journalVoucherHandler.update_bv_order_asset_by_jv(billingPlanUuidSet, companyId, accountSide);
		
		refreshIssuedAmountAndAuditStatus(cashFlowUuid, true, accountSide);
		
	}

	@Override
	public List<CashFlow> getUnattachedCashFlowListBy(Long financeCompanyId, String paymentAccountNo, String paymentName, BigDecimal voucherAmount, String bankTransactionNo) {
		if(StringUtils.isEmpty(paymentAccountNo) || StringUtils.isEmpty(paymentName) || BigDecimal.ZERO.compareTo(voucherAmount) == 0) {
			return Collections.emptyList();
		}
		List<CashFlow> cashFlowList = cashFlowService.getCashFlowListBy(paymentAccountNo, paymentName, voucherAmount);
		if(CollectionUtils.isEmpty(cashFlowList)) {
			logger.error("cashFlow List 为空！");
			return Collections.emptyList();
		}
		List<CashFlow> notConnectedCashFlowList = new ArrayList<CashFlow>();
		for (CashFlow cashFlow : cashFlowList) {
			SourceDocument sourceDocument = sourceDocumentService.getSourceDocumentBy(financeCompanyId, cashFlow.getCashFlowUuid());
			if(sourceDocument != null) {
				String outlierSerialGlobalIdentity = sourceDocument.getOutlierSerialGlobalIdentity();
				if(StringUtils.isNotEmpty(outlierSerialGlobalIdentity)) {//有外部唯一标识
					if(StringUtils.equals(outlierSerialGlobalIdentity, bankTransactionNo)) {
						return Collections.emptyList();//流水已关联凭证
					}
					List<SourceDocumentDetail> details = sourceDocumentDetailService.getDetailsBySourceDocumentUuid(sourceDocument.getSourceDocumentUuid(), outlierSerialGlobalIdentity);
					if(CollectionUtils.isNotEmpty(details)) {//存在明细，流水已关联凭证
						continue;
					}
				}
			}
			notConnectedCashFlowList.add(cashFlow);
		}
		return notConnectedCashFlowList;
	}
	
	@Override
	public List<CashFlowExportModel> getCashFlowExportModelsBy(String hostAccountNo, Date begin, Date end, com.zufangbao.sun.ledgerbook.AccountSide accountSide) {
		List<CashFlow> cashFlows = cashFlowService.getCashFlowsBy(hostAccountNo, begin, end, accountSide);
		List<CashFlowExportModel> exportModels = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(cashFlows)){
			exportModels = cashFlows.stream().map(a->new CashFlowExportModel(a)).collect(Collectors.toList());
		}
		return exportModels;
	}

}

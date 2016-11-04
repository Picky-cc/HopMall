package com.zufangbao.wellsfargo.yunxin.handler.impl;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.sun.entity.account.AccountSide;
import com.zufangbao.sun.entity.account.DepositeAccountInfo;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.ledgerbook.AlreadayCarryOverException;
import com.zufangbao.sun.ledgerbook.AssetCategory;
import com.zufangbao.sun.ledgerbook.AssetConvertor;
import com.zufangbao.sun.ledgerbook.BankAccountCache;
import com.zufangbao.sun.ledgerbook.InsufficientBalanceException;
import com.zufangbao.sun.ledgerbook.InsufficientPenaltyException;
import com.zufangbao.sun.ledgerbook.InvalidCarryOverException;
import com.zufangbao.sun.ledgerbook.InvalidLedgerException;
import com.zufangbao.sun.ledgerbook.LackBusinessVoucherException;
import com.zufangbao.sun.ledgerbook.LedgerBook;
import com.zufangbao.sun.ledgerbook.LedgerBookHandler;
import com.zufangbao.sun.ledgerbook.LedgerItemService;
import com.zufangbao.sun.ledgerbook.LedgerTradeParty;
import com.zufangbao.sun.service.BatchPayRecordService;
import com.zufangbao.sun.service.CompanyService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.LedgerBookService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.yunxin.entity.AssetRecoverType;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.ExecutingSettlingStatus;
import com.zufangbao.sun.yunxin.entity.OrderClearStatus;
import com.zufangbao.sun.yunxin.entity.OrderType;
import com.zufangbao.sun.yunxin.entity.SourceDocumentStatus;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.wellsfargo.exception.VoucherException;
import com.zufangbao.wellsfargo.greypool.document.entity.leasingdocument.GeneralLeasingDocumentTypeDictionary;
import com.zufangbao.wellsfargo.greypool.document.entity.leasingdocument.GeneralLeasingDocumentTypeDictionary.DefaultTypeUuid;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.BusinessVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.BusinessVoucherStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalAccount;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucherType;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentType;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.BusinessVoucherHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.BusinessVoucherService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.JournalVoucherService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentService;
import com.zufangbao.wellsfargo.yunxin.handler.JournalVoucherHandler;

@Component("journalVoucherHandler")
public class JournalVoucherHandlerImpl implements JournalVoucherHandler {

	@Autowired
	private SourceDocumentService sourceDocumentService;
	
	@Autowired
	private BatchPayRecordService batchPayRecordService;
	
	@Autowired
	private BusinessVoucherHandler businessVoucherHandler;
	
	@Autowired
	private JournalVoucherService journalVoucherService;
	
	@Autowired
	private BusinessVoucherService businessVoucherService;
	
	@Autowired
	private LedgerBookHandler ledgerBookHandler;
	
	@Autowired
	private LedgerBookService ledgerBookService;
	
	@Autowired
	private LedgerItemService ledgerItemService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private FinancialContractService financialContractService;
	
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	
	private static final Log logger = LogFactory.getLog(JournalVoucherHandlerImpl.class);
	
	@Override
	public void createJVFromSourceDocument(SourceDocument sourceDocument,BankAccountCache cache, JournalVoucherType journalVoucherType) throws VoucherException, LackBusinessVoucherException, AlreadayCarryOverException, InvalidLedgerException, InsufficientPenaltyException {
		
		String batchPayRecordUuid = sourceDocument.getOutlierDocumentUuid();
		Order order = batchPayRecordService.getOrderByBathPayRecordUuid(batchPayRecordUuid);
		String repaymentBillId = order.getRepaymentBillId();
		
		sourceDocumentService.signSourceDocument(sourceDocument,sourceDocument.getOutlierAmount());
		BusinessVoucher businessVoucher = createdIssuedBusinessVoucher(order,sourceDocument.getCompanyId());
		JournalVoucher journalVoucher= journalVoucherService.createIssuedJournalVoucherBySourceDocument(repaymentBillId, sourceDocument, DefaultTypeUuid.DEFAULT_BUSINESS_VOUCHER_TYPE_UUID, order.getTotalRent(), businessVoucher.getBusinessVoucherUuid(), journalVoucherType);
		try {
			logger.info("recover_receivable_loan_asset start");
			
			recover_receivable_loan_asset_from_union_pay(sourceDocument.getSourceDocumentUuid(), order.getAssetSet(), journalVoucher.getJournalVoucherUuid(),
					businessVoucher.getBusinessVoucherUuid(), journalVoucher.getBookingAmount(),cache);
		} catch(Exception e){
			logger.error("recover_receivable_loan_asset eccor error");
			e.printStackTrace();
		}
		logger.info("recover_receivable_loan_asset end");
	}

	private void recover_receivable_loan_asset_from_union_pay(String sourceDocumentUuid,
			AssetSet assetSet, String journalVoucherUuid,
			String businessVoucherUuid, BigDecimal totalAmount,BankAccountCache cache)
			throws LackBusinessVoucherException, AlreadayCarryOverException,
			InvalidLedgerException, InsufficientPenaltyException, InsufficientBalanceException, InvalidCarryOverException {
		
		LedgerBook ledgerBook = ledgerBookService.getBookByAsset(assetSet);
		Contract contract=assetSet.getContract();
		if(contract==null||StringUtils.isEmpty(contract.getFinancialContractUuid()))
			throw new InvalidLedgerException();
		FinancialContract financialContract = financialContractService.getFinancialContractBy(contract.getFinancialContractUuid());
		DepositeAccountInfo accountInfo = cache.extractUnionPayAccountFrom(financialContract);
		if(accountInfo==null) accountInfo=cache.extractFirstBankAccountFrom(financialContract);
		LedgerTradeParty payableParty = ledgerItemService.getPayableLedgerTradeParty(assetSet);
		ledgerBookHandler.recover_receivable_loan_asset(ledgerBook, assetSet, journalVoucherUuid, businessVoucherUuid, sourceDocumentUuid, payableParty, totalAmount,accountInfo, true);
	}
	
	private BusinessVoucher createdIssuedBusinessVoucher(Order order, Long companyId) {
		BusinessVoucher businessVoucher = businessVoucherHandler.createIfNotExistBusinessVoucherForRepaymentBill(order.getRepaymentBillId(),companyId, order.getTotalRent(), DefaultTypeUuid.DEFAULT_BUSINESS_VOUCHER_TYPE_UUID, DefaultTypeUuid.DEFAULT_BILLING_PLAN_TYPE_UUID);
		businessVoucherService.issueBusinessVoucher(businessVoucher);
		return businessVoucher;
	}
	@Override
	public void createJVFromUnSignedOnlineSourceDocuments(Long companyId,BankAccountCache cache) {
		List<SourceDocument> sourceDocumentList = sourceDocumentService.getSourceDocuments(SourceDocumentType.NOTIFY, AccountSide.DEBIT, companyId, SourceDocumentStatus.CREATE, SourceDocument.FIRSTOUTLIER_BATCHPAY_RECORD);
		for (SourceDocument sourceDocument : sourceDocumentList) {
			try {
				createJVFromSourceDocument(sourceDocument,cache, JournalVoucherType.ONLINE_DEDUCT_BACK_ISSUE);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void createJVFromUnSignedOnlineSourceDocuments(BankAccountCache cache) {
		List<Long> companyIdList = companyService.getAllCompanyId();
		for (Long companyId : companyIdList) {
			try{
				createJVFromUnSignedOnlineSourceDocuments(companyId,cache);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void create_JV_and_BV_from_sourceDocument(SourceDocument sourceDocument,List<Order> orderList, JournalVoucherType journalVoucherType){
		try{
			sourceDocumentService.signSourceDocument(sourceDocument,sourceDocument.getOutlierAmount());
			for (Order order : orderList) {
				String repaymentUuid = order.getRepaymentBillId();
				boolean existsJV = journalVoucherService.existsJVWithSourceDocumentUuid(sourceDocument.getSourceDocumentUuid(), sourceDocument.getCompanyId(), AccountSide.DEBIT, repaymentUuid);
				if(existsJV){
					throw new VoucherException();
				}
				BusinessVoucher businessVoucher = createdIssuedBusinessVoucher(order,sourceDocument.getCompanyId());
				journalVoucherService.createIssuedJournalVoucherBySourceDocument(repaymentUuid, sourceDocument, DefaultTypeUuid.DEFAULT_BUSINESS_VOUCHER_TYPE_UUID, order.getTotalRent(), businessVoucher.getBusinessVoucherUuid(), journalVoucherType);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public JournalVoucher issueJournalVoucher(String repaymentBillId, SourceDocument sourceDocument, BigDecimal bookingAmount, String businessVoucherUuid, JournalVoucherType journalVoucherType) {
		return journalVoucherService.createIssuedJournalVoucherBySourceDocument(repaymentBillId, sourceDocument, DefaultTypeUuid.DEFAULT_BUSINESS_VOUCHER_TYPE_UUID, bookingAmount, businessVoucherUuid, journalVoucherType);
	}
	
	@Override
	public void update_bv_order_asset_by_jv(Collection<String> repaymentUuids, Long companyId, AccountSide accountSide){
		for (String repaymentUuid : repaymentUuids) {
			update_bv_order_asset_by_jv(repaymentUuid, companyId, accountSide);
		}
	}
	
	@Override
	public void update_bv_order_asset_by_jv(String repaymentUuid, Long companyId, AccountSide accountSide) {
		BusinessVoucher businessVoucher = businessVoucherService.getInForceBusinessVoucherByBillingPlanUidAndType(repaymentUuid, GeneralLeasingDocumentTypeDictionary.DefaultTypeUuid.DEFAULT_BUSINESS_VOUCHER_TYPE_UUID);
		Order order = orderService.getOrderByRepaymentBillId(repaymentUuid);

		BigDecimal issuedAmount = journalVoucherService.getBookingAmountSumOfIssueJournalVoucherBy(repaymentUuid, companyId, new JournalAccount(accountSide));
		if (order == null){
			logger.info("order is null.");
			return;
		}
		updateBillLifeCycleStatusAndBusinessVoucherStatusAndAmount(issuedAmount, order, businessVoucher, companyId, accountSide);
	}
	
	private void updateBillLifeCycleStatusAndBusinessVoucherStatusAndAmount(BigDecimal issuedAmount, Order order, BusinessVoucher businessVoucher, Long companyId, AccountSide accountSide) {
		if (order==null ||businessVoucher==null){
			logger.info("BillingPlan or businessVoucher is null.");
			return;
		}
		if(issuedAmount==null){
			issuedAmount=BigDecimal.ZERO;
		}
		// 在成功和处理中跳转。
		OrderClearStatus expertedClearingStatus = null;
		ExecutingSettlingStatus expertedExecutingSettlingStatus =null;
		BusinessVoucherStatus expectedBusinessVoucherStatus = null;
		
		
		if(issuedAmount.compareTo(BigDecimal.ZERO)>= 0  && issuedAmount.compareTo(order.getTotalRent()) < 0){
			expertedClearingStatus = OrderClearStatus.UNCLEAR;
			expertedExecutingSettlingStatus = ExecutingSettlingStatus.DOING;
			expectedBusinessVoucherStatus = BusinessVoucherStatus.VOUCHER_ISSUING;
		} else if(issuedAmount.compareTo(order.getTotalRent()) >= 0){
			expertedClearingStatus = OrderClearStatus.CLEAR;
			expertedExecutingSettlingStatus = ExecutingSettlingStatus.SUCCESS;
			expectedBusinessVoucherStatus = BusinessVoucherStatus.VOUCHER_ISSUED;
		}
		//order.updateClearStatus(clearTime, clearResult);
		if(expertedClearingStatus!=null && expertedClearingStatus!=order.getClearingStatus()){
			order.setClearingStatus(expertedClearingStatus);
			order.setModifyTime(new Date());
		}
		if(expertedExecutingSettlingStatus!=null && expertedExecutingSettlingStatus!=order.getExecutingSettlingStatus()){
			order.setExecutingSettlingStatus(expertedExecutingSettlingStatus);
			order.setModifyTime(new Date());
		}
		if(expectedBusinessVoucherStatus!=null && expectedBusinessVoucherStatus!=businessVoucher.getBusinessVoucherStatus()){
			businessVoucher.setBusinessVoucherStatus(expectedBusinessVoucherStatus);
		}
		Date tradeTime = journalVoucherService.getLastPaidTimeByIssuedJournalVoucherAndJournalAccount(order.getRepaymentBillId(), companyId, new JournalAccount(accountSide));
		order.setPayoutTime(tradeTime);
		businessVoucher.setSettlementAmount(issuedAmount);
		
		//TODO  save when modified. or call order.updateClearStatus ?
		orderService.save(order);
		businessVoucherService.save(businessVoucher);
		
		AssetSet assetSet = order.getAssetSet();
		if(order.getOrderType()==OrderType.NORMAL){
			assetSet.updateClearStatus(tradeTime, order.getClearingStatus()==OrderClearStatus.CLEAR);
		}else if(order.getOrderType()==OrderType.GUARANTEE){
			assetSet.updateGuranteeStatus(order.getClearingStatus()==OrderClearStatus.CLEAR);
		}
		repaymentPlanService.save(assetSet);
	}
	
	@Override
	public void write_off_forward_ledgers(List<JournalVoucher> journalVoucherList) {
		try {
			for (JournalVoucher journalVoucher : journalVoucherList) {
				//TODO get by billingPlanUuid AND billingPlanType;
				Order order = orderService.getOrderByRepaymentBillId(journalVoucher.getBillingPlanUuid());
				if(order==null) continue;
				AssetSet asset = order.getAssetSet();
				LedgerBook book = ledgerBookService.getBookByAsset(asset);
				if(book==null) continue;
				AssetCategory assetCategory = AssetConvertor.convertAnMeiTuAssetCategory(asset, "", null);
				ledgerItemService.roll_back_ledgers_by_voucher(book, assetCategory, journalVoucher.getJournalVoucherUuid(), journalVoucher.getBusinessVoucherUuid(), "");
				
			}
		} catch(Exception e){
			
		}
		
	}
	
//	@Override
//	public void recover_loan_asset_or_guarantee_by_jvs(List<JournalVoucher> journalVoucherList){
//		for (JournalVoucher journalVoucher : journalVoucherList) {
//			recover_loan_asset_or_guarantee(journalVoucher);
//		}
//	}
//
//	@Override
//	public void recover_loan_asset_or_guarantee(JournalVoucher journalVoucher) {
//		try {
//			Order order = orderService.getOrderByRepaymentBillId(journalVoucher.getBillingPlanUuid());
//			if(order==null) return;
//			AssetRecoverType assetRecoverType =  AssetRecoverType.convertFromOrderType(order.getOrderType());
//			recover_loan_asset_or_guarantee(order.getAssetSet(), assetRecoverType, journalVoucher.getBookingAmount(), journalVoucher.getJournalVoucherUuid(), journalVoucher.getBusinessVoucherUuid(), "", LedgerBookBankAccount.NFQ_CMB);
//			
//		} catch(Exception e){
//			
//		}
//	}
	
	@Override
	public void recover_loan_asset_or_guarantee(AssetSet assetSet, AssetRecoverType assetRecoverType, BigDecimal bookingAmount,
			String journalVoucherUuid, String businessVoucherUuid, String sourceDocumentUuid, DepositeAccountInfo ledgerBookBankAccount, boolean ifRecoveryMoney) throws LackBusinessVoucherException, AlreadayCarryOverException, InvalidLedgerException, InsufficientPenaltyException, InsufficientBalanceException, InvalidCarryOverException {
		if(assetSet ==null) return;
		LedgerBook book = ledgerBookService.getBookByAsset(assetSet);
		if(book==null) return;
		LedgerTradeParty gurranteeParty = ledgerItemService.getGuranteLedgerTradeParty(assetSet);
		LedgerTradeParty payableparty = ledgerItemService.getPayableLedgerTradeParty(assetSet);
		if(assetRecoverType==AssetRecoverType.LOAN_ASSET){
			logger.info("begin to recover_receivable_asset, asset_uuid["+assetSet.getAssetUuid()+"].");
			ledgerBookHandler.recover_receivable_loan_asset(book, assetSet, journalVoucherUuid, businessVoucherUuid, sourceDocumentUuid, payableparty, bookingAmount,ledgerBookBankAccount, ifRecoveryMoney);
			logger.info("end to recover_receivable_asset, asset_uuid["+assetSet.getAssetUuid()+"].");
		} else if(assetRecoverType==AssetRecoverType.GUARANTEE_ASSET){
			logger.info("begin to recover_receivable_guranttee, asset_uuid["+assetSet.getAssetUuid()+"].");
			ledgerBookHandler.recover_receivable_guranttee(book, assetSet, journalVoucherUuid, businessVoucherUuid, sourceDocumentUuid, gurranteeParty, payableparty, ledgerBookBankAccount, bookingAmount, ifRecoveryMoney);
			logger.info("end to recover_receivable_guranttee, asset_uuid["+assetSet.getAssetUuid()+"].");
		}
	}

}

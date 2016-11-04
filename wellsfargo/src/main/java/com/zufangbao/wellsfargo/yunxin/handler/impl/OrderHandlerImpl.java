package com.zufangbao.wellsfargo.yunxin.handler.impl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.finance.BatchPayRecord;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.ledgerbook.AssetCategory;
import com.zufangbao.sun.ledgerbook.AssetConvertor;
import com.zufangbao.sun.ledgerbook.LedgerBook;
import com.zufangbao.sun.ledgerbook.LedgerBookHandler;
import com.zufangbao.sun.ledgerbook.LedgerItemService;
import com.zufangbao.sun.ledgerbook.LedgerTradeParty;
import com.zufangbao.sun.service.AssetPackageService;
import com.zufangbao.sun.service.BatchPayRecordService;
import com.zufangbao.sun.service.ContractAccountService;
import com.zufangbao.sun.service.LedgerBookService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.service.TransferApplicationService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.GuaranteeStatus;
import com.zufangbao.sun.yunxin.entity.OfflineBill;
import com.zufangbao.sun.yunxin.entity.OfflineBillConnectionState;
import com.zufangbao.sun.yunxin.entity.model.OrderViewDetail;
import com.zufangbao.sun.yunxin.handler.AssetValuationDetailHandler;
import com.zufangbao.sun.yunxin.handler.RepaymentPlanHandler;
import com.zufangbao.sun.yunxin.service.OfflineBillService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.wellsfargo.greypool.document.entity.busidocument.bill.BillingPlan;
import com.zufangbao.wellsfargo.greypool.document.entity.leasingdocument.GeneralLeasingDocumentTypeDictionary.DefaultTypeUuid;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.BusinessVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.OfflineBillPayDetail;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.BusinessVoucherService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.JournalVoucherService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentService;
import com.zufangbao.wellsfargo.yunxin.handler.OrderHandler;
import com.zufangbao.wellsfargo.yunxin.handler.SourceDocumentHandler;
/**
 * 
 * @author zjm
 *
 */
@Component("orderHandler")
public class OrderHandlerImpl implements OrderHandler {
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private BusinessVoucherService businessVoucherService;
	@Autowired
	private ContractAccountService contractAccountService;
	@Autowired
	private TransferApplicationService transferApplicationService;
	
	@Autowired
	private BatchPayRecordService batchPayRecordService;
	
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	
	@Autowired
	private JournalVoucherService journalVoucherService;
	
	@Autowired
	private SourceDocumentService sourceDocumentService;
	
	@Autowired
	private SourceDocumentHandler sourceDocumentHandler;
	
	@Autowired
	private AssetPackageService assetPackageService;

	@Autowired
	private OfflineBillService offlineBillService;
	
	@Autowired
	private LedgerBookHandler ledgerBookHandler;
	@Autowired
	private LedgerBookService ledgerBookService;
	@Autowired
	private LedgerItemService ledgerItemService;
	@Autowired
	private AssetValuationDetailHandler assetValuationDetailHandler;
	
	@Autowired
	private RepaymentPlanHandler repaymentPlanHandler;

	private static Log logger = LogFactory.getLog(OrderHandlerImpl.class);
	
	@Override
	public OrderViewDetail convertToOrderDetail(Order order) {
		if(order==null){
			return null;
		}
		AssetSet assetSet = order.getAssetSet();
		Contract contract = assetSet.getContract();
		Customer customer = contract.getCustomer();
		ContractAccount contractAccount = contractAccountService.getTheEfficientContractAccountBy(contract);
		List<String> paymentNoList = new ArrayList<String>();
		List<String> serialNoList = new ArrayList<String>();
		List<TransferApplication> deductSucTransferApplicationList = transferApplicationService.getDeductSucTransferApplicationListBy(order);
		List<String> deductSucTransferApplicationNoList = transferApplicationService.extractTransferApplicationNoListFrom(deductSucTransferApplicationList);
		List<String> serial_no_of_batch_pay_record = getSerialNoFrom(deductSucTransferApplicationList);
		paymentNoList.addAll(deductSucTransferApplicationNoList);
		serialNoList.addAll(serial_no_of_batch_pay_record);
		OrderViewDetail orderViewDetail = new OrderViewDetail(order, contractAccount,customer, paymentNoList, serialNoList);
		return orderViewDetail;
	}
	private List<String> getSerialNoFrom(List<TransferApplication> deductSucTransferApplicationList) {
		List<String> serial_no_list_from_batch_pay_record = new ArrayList<String>();
		if(CollectionUtils.isEmpty(deductSucTransferApplicationList)){
			return serial_no_list_from_batch_pay_record;
		}
		for (TransferApplication transferApplication : deductSucTransferApplicationList) {
			if(transferApplication==null){
				continue;
			}
			BatchPayRecord batchPayRecord = batchPayRecordService.load(BatchPayRecord.class, transferApplication.getBatchPayRecordId());
			if(batchPayRecord==null){
				continue;
			}
			serial_no_list_from_batch_pay_record.add(batchPayRecord.getSerialNo());
		}
		return serial_no_list_from_batch_pay_record;
	}

	@Override
	public void createGuaranteeOrder() {
		List<AssetSet> needGuaranteeAssetSetList = repaymentPlanService.loadAllNeedGuaranteeAssetSetList();
		for (AssetSet assetSet : needGuaranteeAssetSetList) {
			Contract contract = assetSet.getContract();
			FinancialContract financialContract = assetPackageService.getFinancialContract(contract);
			if(financialContract == null || !financialContract.isSysCreateGuaranteeFlag()) {
				continue;
			}
			Date valuationDate = new Date();
			Date assetRecycleDate = assetSet.getAssetRecycleDate();
			int loanOverdueStartDay = financialContract.getLoanOverdueStartDay();
			if (assetSet.is_in_Interest_free_period(valuationDate, assetRecycleDate, loanOverdueStartDay)) {
				continue;
			}
			Order guaranteeOrder = Order.createGuaranteeOrder(assetSet, contract.getCustomer(), financialContract);
			orderService.save(guaranteeOrder);
			assetSet.updateGuaranteeStatus();
			orderService.update(assetSet);
			book_guarantee_in_ledger_book(financialContract, assetSet);
		}
	}

	private void book_guarantee_in_ledger_book(FinancialContract financialContract, AssetSet assetSet) {
		LedgerBook book = ledgerBookService.getBookByBookNo(financialContract.getLedgerBookNo());
		if(book==null){
			return;
		}
		try {
			LedgerTradeParty guaranteeLedgerTradeParty = ledgerItemService.getGuranteLedgerTradeParty(assetSet);
			AssetCategory assetCategory=AssetConvertor.convertAnMeiTuLoanAssetToAssetCategory(assetSet);
			ledgerBookHandler.book_receivable_load_guarantee_and_assets_sold_for_repurchase(book, assetCategory, guaranteeLedgerTradeParty, assetSet.getAssetInitialValue());
			
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
	@Override
	public void updateStatusAfterIssueBusinessVoucher(Order order) {
		BusinessVoucher businessVoucher = businessVoucherService.getInForceBusinessVoucherByBillingPlanUidAndType(order.getRepaymentBillId(), DefaultTypeUuid.DEFAULT_BUSINESS_VOUCHER_TYPE_UUID);
		if(businessVoucher == null || businessVoucher.isBusinessVoucherNotIssued()) {
			return;
		}
		order.updateClearStatus(new Date(),true);
		orderService.save(order);
		AssetSet assetSet = order.getAssetSet();
		if(order.isNormalOrder()) {
			assetSet.updateClearStatus(new Date(),true);
		}
		if(order.isGuaranteeOrder()) {
			assetSet.setGuaranteeStatus(GuaranteeStatus.HAS_GUARANTEE);
		}
		repaymentPlanService.save(assetSet);
	}
	@Override
	public synchronized void assetValuationAndCreateOrder(Long assetSetId, Date executeDate) throws Exception {
		AssetSet assetSet = repaymentPlanService.load(AssetSet.class, assetSetId);
		//评估单条资产
		try {
			logger.info("评估单条资产，资产id:"+assetSetId);
			assetValuationDetailHandler.assetValuation(assetSet, executeDate);
		} catch (Exception e) {
			logger.error("asset valuation occur error，assetSet id[" + assetSet.getId()+"].");
			throw e;
		}
		//评估单条资产后生成结算单
		try {
			repaymentPlanHandler.createOrderByAssetSet(assetSet, executeDate);
		} catch (Exception e) {
			logger.error("create normal order by assetSet occur error，assetSet id["+assetSet.getId()+"].");
			throw e;
		}
	}
	@Override
	public List<BillingPlan> convertOrderToBillingPlan(List<Order> orderList) {
		List<BillingPlan> billingPlanList = new ArrayList<BillingPlan>();
		for (Order order : orderList) {
			if(order==null) continue;
			BillingPlan bill = new BillingPlan(order);
			billingPlanList.add(bill);
		}
		return billingPlanList;
	}
	@Override
	public List<OfflineBillPayDetail> getOfflineBillList(Order order) {
		List<JournalVoucher> journalVoucherList = journalVoucherService.getVouchersByBillingPlanUidAndIssued(order.getRepaymentBillId(), order.getFinancialContract().getCompany().getId());
		List<OfflineBillPayDetail> offlineBillPayDetails = new ArrayList<OfflineBillPayDetail>();
		journalVoucherList.stream().forEach(jv -> {
			SourceDocument sourceDocument = sourceDocumentService.getSourceDocumentBy(jv.getSourceDocumentUuid());
			OfflineBill offlineBill = offlineBillService.getOfflineBillBy(sourceDocument.getOutlierDocumentUuid());
			if(offlineBill != null) {
				OfflineBillConnectionState offlineBillConnectionState = sourceDocumentHandler.getOfflineBillConnectionState(offlineBill);
				offlineBill.setOfflineBillConnectionState(offlineBillConnectionState);
				OfflineBillPayDetail payDetail = new OfflineBillPayDetail(jv, offlineBill);
				offlineBillPayDetails.add(payDetail);
			}
		});
		return offlineBillPayDetails;
	}
	
}
package com.zufangbao.earth.yunxin.api.handler.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo2do.core.utils.DateUtils;
import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.exception.ApiResponseCode;
import com.zufangbao.earth.yunxin.api.handler.RepaymentListApitHandler;
import com.zufangbao.earth.yunxin.api.model.RepaymentListDetail;
import com.zufangbao.earth.yunxin.api.model.query.RepaymentListQueryModel;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.finance.BatchPayRecord;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.service.BatchPayRecordService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.OrderService;
import com.zufangbao.sun.service.TransferApplicationService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.OfflineBill;
import com.zufangbao.sun.yunxin.service.OfflineBillService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.JournalVoucher;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.JournalVoucherService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentService;

@Component("RepaymentListApiHandler")
public class RepaymentListApiHandlerImpl implements RepaymentListApitHandler {

	@Autowired
	private FinancialContractService financialContractService;

	@Autowired
	private TransferApplicationService transferApplicationService;

	@Autowired
	private OfflineBillService offlineBillService;

	@Autowired
	private SourceDocumentService sourceDocumentService;

	@Autowired
	private JournalVoucherService journalVoucherService;

	@Autowired
	private OrderService orderService;
	@Autowired
	private BatchPayRecordService batchPayRecordService;

	@Override
	public List<RepaymentListDetail> queryRepaymentList(
			RepaymentListQueryModel queryModel) {
		String financialContractNo = queryModel.getFinancialContractNo();
		FinancialContract financialContract = queryFinancialContract(financialContractNo);

		List<RepaymentListDetail> repaymentListDetails = new ArrayList<RepaymentListDetail>();
		
		List<RepaymentListDetail> onLinePaymentListDetails = generateOnlineRepaymentListDetails(queryModel, financialContract);

		List<RepaymentListDetail> offlineRepaymentListDetails = generateOfflineRepaymentListDetail(queryModel, financialContract);

		repaymentListDetails.addAll(onLinePaymentListDetails);
		repaymentListDetails.addAll(offlineRepaymentListDetails);
		
		sortRepaymentListDetailByDeductDate(repaymentListDetails);
		
		return repaymentListDetails;
	}

	private void sortRepaymentListDetailByDeductDate(
			List<RepaymentListDetail> repaymentListDetails) {
		Collections.sort(repaymentListDetails, new Comparator<RepaymentListDetail>() {
	        @Override
	        public int compare(RepaymentListDetail repaymentList1,RepaymentListDetail repaymentList2) {
	        	return  DateUtils.asDay(repaymentList1.getDeductDate()).compareTo(DateUtils.asDay(repaymentList2.getDeductDate()));
	        }
		
		});
	}

	private FinancialContract queryFinancialContract(String financialContractNo) {
		FinancialContract financialContract = financialContractService.getUniqueFinancialContractBy(financialContractNo);
		if (financialContract == null) {
			throw new ApiException(
					ApiResponseCode.FINANCIAL_CONTRACT_NOT_EXIST);
		}
		return financialContract;
	}

	private List<RepaymentListDetail> generateOnlineRepaymentListDetails(
			RepaymentListQueryModel queryModel,
			FinancialContract financialContract) {
		List<RepaymentListDetail> onLinePaymentListDetails = new ArrayList<RepaymentListDetail>();
		List<TransferApplication> transferAspplications = transferApplicationService
				.queryTransferApplciationListBy(financialContract,
						queryModel.getQueryStartDate(),
						queryModel.getQueryEndDate());
		for (TransferApplication transferApplication : transferAspplications) {
			BatchPayRecord batchPayRecord = batchPayRecordService.load(
					BatchPayRecord.class,
					transferApplication.getBatchPayRecordId());
			RepaymentListDetail offlineRepaymentListDetail = new RepaymentListDetail(
					transferApplication, batchPayRecord);
			onLinePaymentListDetails.add(offlineRepaymentListDetail);
		}
		return onLinePaymentListDetails;
	}

	private List<RepaymentListDetail> generateOfflineRepaymentListDetail(
			RepaymentListQueryModel queryModel,
			FinancialContract financialContract) {
		List<RepaymentListDetail> offlineRepaymentListDetails = new ArrayList<RepaymentListDetail>();
		List<OfflineBill> offlineBillListsDuringDate = offlineBillService
				.queryOfflineBillListBy(queryModel.getQueryStartDate(),
						queryModel.getQueryEndDate());

		for (OfflineBill offlineBill : offlineBillListsDuringDate) {

			SourceDocument sourceDocument = sourceDocumentService
					.getSourceDocumentByOfflineBillUuid(
							offlineBill.getOfflineBillUuid()).get(0);
			List<JournalVoucher> jvs = journalVoucherService
					.getJournalVoucherBySourceDocumentUuid(sourceDocument
							.getSourceDocumentUuid());
			if (jvs.isEmpty()) {
				continue;
			}
			Set<AssetSet> offlineBillRelateAssetSets = new HashSet<AssetSet>();
			Set<Contract> offlineBillRelateContracts = new HashSet<Contract>();
			for (JournalVoucher jvForRelate : jvs) {
				Order order = orderService
						.getOrderByRepaymentBillId(jvForRelate
								.getBillingPlanUuid());
				if (order == null)
					continue;
				if (!order.getFinancialContract().getId()
						.equals(financialContract.getId()))
					continue;
				offlineBillRelateAssetSets.add(order.getAssetSet());
				offlineBillRelateContracts.add(order.getAssetSet()
						.getContract());
			}
			RepaymentListDetail offlineRepaymentListDetail = new RepaymentListDetail(
					offlineBill, offlineBillRelateAssetSets,
					offlineBillRelateContracts);
			offlineRepaymentListDetails.add(offlineRepaymentListDetail);
		}
		return offlineRepaymentListDetails;
	}

}

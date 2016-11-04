package com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher;

import java.math.BigDecimal;

import com.zufangbao.sun.api.model.deduct.DeductApplicationRepaymentDetail;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.yunxin.entity.api.VoucherPayer;
import com.zufangbao.sun.yunxin.entity.api.VoucherSource;
import com.zufangbao.sun.yunxin.entity.api.VoucherType;

public class SourceDocumentDetailImporter {

	public static SourceDocumentDetail createSourceDocumentDetail(String sourceDocumentUuid, DeductApplicationRepaymentDetail deductApplicationRepaymentDetail,String requestNo, ContractAccount contractAccount, String capitalAccountNo){
		return new SourceDocumentDetail(sourceDocumentUuid, deductApplicationRepaymentDetail.getContractUniqueId(), deductApplicationRepaymentDetail.getRepaymentPlanCode(), deductApplicationRepaymentDetail.getTotalAmount(), SourceDocumentDetailStatus.UNSUCCESS,
				VoucherSource.THIRD_PARTY_DEDUCTION_VOUCHER.getKey(), deductApplicationRepaymentDetail.getDeductApplicationUuid(), VoucherType.THIRD_PARTY_DEDUCTION_VOUCHER.getKey(), deductApplicationRepaymentDetail.getDeductApplicationDetailUuid(), VoucherPayer.LOANER,capitalAccountNo, contractAccount.getPayAcNo(), contractAccount.getPayerName(), contractAccount.getBank());
	
	}

	public static SourceDocumentDetail createDetailBy(String sourceDocumentUuid, String contractUniqueId, String repaymentPlanNo,
			BigDecimal amount, String firstNo, String secondType, VoucherPayer payer, String receivableAccountNo, String paymentAccountNo,
			String paymentName, String paymentBank) {
		SourceDocumentDetailStatus status = SourceDocumentDetailStatus.UNSUCCESS;
		String firstType = VoucherSource.BUSINESS_PAYMENT_VOUCHER.getKey();
		return new SourceDocumentDetail(sourceDocumentUuid, contractUniqueId, repaymentPlanNo, amount, status, firstType, firstNo, secondType, null, payer, receivableAccountNo, paymentAccountNo, paymentName, paymentBank);
	}
	
	public static SourceDocumentDetail createActivePaymentVoucherDetail(String sourceDocumentUuid, String contractUniqueId, String repaymentPlanNo,
			BigDecimal amount, String firstNo, String secondType, VoucherPayer payer, String receivableAccountNo, String paymentAccountNo,
			String paymentName, String paymentBank) {
		SourceDocumentDetailStatus status = SourceDocumentDetailStatus.UNSUCCESS;
		String firstType = VoucherSource.ACTIVE_PAYMENT_VOUCHER.getKey();
		return new SourceDocumentDetail(sourceDocumentUuid, contractUniqueId, repaymentPlanNo, amount, status, firstType, firstNo, secondType, null, payer, receivableAccountNo, paymentAccountNo, paymentName, paymentBank);
	}
}

package com.zufangbao.wellsfargo.silverpool.cashauditing.handler.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.ledgerbook.stat.LedgerBookStatHandler;
import com.zufangbao.sun.service.CashFlowService;
import com.zufangbao.sun.service.ContractAccountService;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.utils.StringUtils;
import com.zufangbao.sun.yunxin.api.ActivePaymentVoucherLogService;
import com.zufangbao.sun.yunxin.api.BusinessPaymentVoucherLogService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.api.ActivePaymentVoucherLog;
import com.zufangbao.sun.yunxin.entity.api.BusinessPaymentVoucherLog;
import com.zufangbao.sun.yunxin.entity.api.VoucherSource;
import com.zufangbao.sun.yunxin.entity.api.VoucherType;
import com.zufangbao.sun.yunxin.entity.model.voucher.VoucherDetailBusinessModel;
import com.zufangbao.sun.yunxin.entity.model.voucher.VoucherDetailModel;
import com.zufangbao.sun.yunxin.entity.model.voucher.VoucherQueryModel;
import com.zufangbao.sun.yunxin.entity.model.voucher.VoucherShowModel;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentDetail;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentDetailStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.SourceDocumentDetailHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentDetailService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentService;

@Component("sourceDocumentDetailHandler")
public class SourceDocumentDetailHandlerImpl implements SourceDocumentDetailHandler {

	@Autowired
	private SourceDocumentDetailService sourceDocumentDetailService;
	@Autowired
	private SourceDocumentService sourceDocumentService;
	@Autowired
	private BusinessPaymentVoucherLogService businessPaymentVoucherLogService;
	@Autowired
	private CashFlowService cashFlowService;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private FinancialContractService financialContractService;
	@Autowired
	private LedgerBookStatHandler ledgerBookStatHandler;
	@Autowired
	private ContractService contractService;
	@Autowired
	private ContractAccountService contractAccountService;
	@Autowired
	private ActivePaymentVoucherLogService activePaymentVoucherLogService;
	
	@Override
	public List<VoucherShowModel> getBusinessVoucherShowModels(VoucherQueryModel voucherQueryModel, Page page) {
		String firstType = VoucherSource.BUSINESS_PAYMENT_VOUCHER.getKey();
		List<Long> detailIds = sourceDocumentDetailService.get_voucher_ids_by_first_type(voucherQueryModel, firstType, page);
		return convertVoucherShowModel(detailIds);
	}

	private List<VoucherShowModel> convertVoucherShowModel(List<Long> detailIds) {
		List<VoucherShowModel> showList = new ArrayList<>();
		for (Long id : detailIds) {
			SourceDocumentDetail detail = sourceDocumentDetailService.getSourceDocumentDetailBy(id);
			VoucherShowModel showModel = createVoucherShowModel(detail);
			showList.add(showModel);
		}
		return showList;
	}

	private VoucherShowModel createVoucherShowModel(SourceDocumentDetail detail) {
		String sourceDocumentNo = "";
		BigDecimal amount = BigDecimal.ZERO;
		if(StringUtils.isEmpty(detail.getSourceDocumentUuid())) {
			amount = businessPaymentVoucherLogService.getLogAmountByRequestNo(detail.getFirstNo());
		}else {
			SourceDocument sourceDocument = sourceDocumentService.getSourceDocumentBy(detail.getSourceDocumentUuid());
			if(sourceDocument != null) {
				sourceDocumentNo = sourceDocument.getSourceDocumentNo();
				amount = sourceDocument.getOutlierAmount();
			}
		}
		String voucherStatus = getVoucherStatus(detail);
		VoucherShowModel showModel = createVoucherShowModel(detail, sourceDocumentNo, voucherStatus, amount);
		return showModel;
	}

	private String getVoucherStatus(SourceDocumentDetail detail) {
		String voucherStatus = SourceDocumentDetailStatus.UNSUCCESS.getChineseName();
		switch (detail.getStatus()) {
		case INVALID:
			voucherStatus = SourceDocumentDetailStatus.INVALID.getChineseName();
			break;
		case SUCCESS:
			List<SourceDocumentDetail> detailList = sourceDocumentDetailService.getSameVersionDetails(detail, null);
			if(detailList.stream().filter(d -> d.getStatus() == SourceDocumentDetailStatus.UNSUCCESS).count() == 0)
				voucherStatus = SourceDocumentDetailStatus.SUCCESS.getChineseName();
			break;
		default:
			break;
		}
		return voucherStatus;
	}

	private VoucherShowModel createVoucherShowModel(SourceDocumentDetail detail, String sourceDocumentNo,
			String voucherStatus, BigDecimal amount) {
		String receivableAccountNo = detail.getReceivableAccountNo();
		String paymentAccountNo = detail.getPaymentAccountNo();
		String paymentName = detail.getPaymentName();
		String paymentBank = detail.getPaymentBank();
		String voucherType = VoucherType.fromKey(detail.getSecondType())== null ? "" : VoucherType.fromKey(detail.getSecondType()).getChineseMessage();
		String voucherSource = VoucherSource.fromKey(detail.getFirstType()) == null ? "" : VoucherSource.fromKey(detail.getFirstType()).getChineseMessage();
		Long detailId = detail.getId();
		VoucherShowModel showModel = new VoucherShowModel(detailId , sourceDocumentNo, receivableAccountNo, paymentAccountNo, paymentName, paymentBank, amount, voucherType, voucherSource, voucherStatus);
		return showModel;
	}

	@Override
	public VoucherDetailModel getBusinessVoucherDetailModel(Long detailId) {
		SourceDocumentDetail detail = sourceDocumentDetailService.load(SourceDocumentDetail.class, detailId);
		if(detail == null) {
			return null;
		}
		if(detail.is_business_payment_voucher() == false) {
			return null;
		}
		BusinessPaymentVoucherLog log = businessPaymentVoucherLogService.getLog(detail.getFirstNo());
		Date createTime = log == null ? null : log.getCreateTime();
		String requestNo = log == null ? "" : log.getRequestNo();
		return getVoucherDetailModel(detail, createTime, requestNo);
	}

	private VoucherDetailModel getVoucherDetailModel(SourceDocumentDetail detail, Date createTime, String requestNo) {
		SourceDocument sourceDocument = sourceDocumentService.getSourceDocumentBy(detail.getSourceDocumentUuid());
		CashFlow cashFlow = null; 
		if(sourceDocument != null) {
			cashFlow = cashFlowService.getCashFlowByCashFlowUuid(sourceDocument.getOutlierDocumentUuid());
		}
		boolean checkFails = sourceDocumentDetailService.isSourceDocumentDetailsCheckFails(detail);
		VoucherDetailModel model = createVoucherDetailModel(detail, sourceDocument, cashFlow, createTime, requestNo);
		checkFails = checkFails && org.apache.commons.lang.StringUtils.equals(model.getVoucherStatus(), SourceDocumentDetailStatus.UNSUCCESS.getChineseName());
		model.setHasFails(checkFails);
		return model;
	}

	private VoucherDetailModel createVoucherDetailModel(SourceDocumentDetail detail, SourceDocument sourceDocument, CashFlow cashFlow, Date createTime, String requestNo) {
		String sourceDocumentNo = "";
		BigDecimal amount = BigDecimal.ZERO;
		String comment = "";
		if(sourceDocument != null) {
			sourceDocumentNo = sourceDocument.getSourceDocumentNo();
			amount = sourceDocument.getOutlierAmount();
			comment = sourceDocument.getOutlierBreif();
		}
		String receivableAccountNo = detail.getReceivableAccountNo();
		String paymentAccountNo = detail.getPaymentAccountNo();
		String paymentName = detail.getPaymentName();
		String paymentBank = detail.getPaymentBank();
		String voucherType = VoucherType.fromKey(detail.getSecondType())== null ? "" : VoucherType.fromKey(detail.getSecondType()).getChineseMessage();
		String voucherSource = VoucherSource.fromKey(detail.getFirstType()) == null ? "" : VoucherSource.fromKey(detail.getFirstType()).getChineseMessage();
		String voucherStatus = getVoucherStatus(detail);
		return new VoucherDetailModel(sourceDocumentNo, voucherSource, voucherType, amount, receivableAccountNo, paymentAccountNo, paymentName, paymentBank, createTime, voucherStatus, requestNo, cashFlow, comment);
	}

	@Override
	public List<VoucherDetailBusinessModel> queryVoucherDetails(SourceDocumentDetail detail, Page page) {
		List<SourceDocumentDetail> sameVersionDetails = sourceDocumentDetailService.getSameVersionDetails(detail, page);
		List<VoucherDetailBusinessModel> showList = new ArrayList<>();
		sameVersionDetails.stream().forEach(d -> {
			VoucherDetailBusinessModel model = createBusinessModel(d);
			showList.add(model);
		});
		return showList;
	}

	private VoucherDetailBusinessModel createBusinessModel(SourceDocumentDetail d) {
		AssetSet repaymentPlan = repaymentPlanService.getRepaymentPlanByRepaymentPlanNo(d.getRepaymentPlanNo());
		BigDecimal receivable_amount = BigDecimal.ZERO;
		String financialContractName = "";
		if(repaymentPlan != null) {
			Contract contract = repaymentPlan.getContract();
			FinancialContract financialContract = financialContractService.getFinancialContractBy(contract.getFinancialContractUuid());
			String ledgerBookNo = financialContract.getLedgerBookNo();
			String customerUuid = contract.getCustomer().getCustomerUuid();
			receivable_amount = ledgerBookStatHandler.get_receivable_amount(ledgerBookNo , repaymentPlan.getAssetUuid() , customerUuid);
			financialContractName = financialContract.getContractName();
		}
		BigDecimal detailAmount = d.getAmount();
		String detailStatus = d.getStatus().getChineseName();
		Long sourceDocumentDetailId = d.getId();
		String verifyStatus = d.getCheckState().getChineseName();
		String comment = d.getComment();
		VoucherDetailBusinessModel model = new VoucherDetailBusinessModel(sourceDocumentDetailId, repaymentPlan, receivable_amount, financialContractName, detailAmount, detailStatus, verifyStatus, comment);
		d.getSecondType();
		String secondType = d.getSecondType();
		VoucherType voucherType = VoucherType.fromKey(secondType);
		if(voucherType != null) {
			model.setVoucherType(voucherType.getChineseMessage());
		}
		return model;
	}

	@Override
	public List<VoucherShowModel> getActiveVoucherShowModels(VoucherQueryModel voucherQueryModel, Page page) {
		String firstType = VoucherSource.ACTIVE_PAYMENT_VOUCHER.getKey();
		List<Long> detailIds = sourceDocumentDetailService.get_voucher_ids_by_first_type(voucherQueryModel, firstType, page);
		return convertVoucherShowModel(detailIds);
	}

	@Override
	public VoucherDetailModel getActiveVoucherDetailModel(Long detailId) {
		SourceDocumentDetail detail = sourceDocumentDetailService.load(SourceDocumentDetail.class, detailId);
		if(detail == null) {
			return null;
		}
		if(detail.is_active_payment_voucher() == false) {
			return null;
		}
		ActivePaymentVoucherLog log = activePaymentVoucherLogService.getLog(detail.getFirstNo());
		Date createTime = log == null ? null : log.getCreateTime();
		String requestNo = log == null ? "" : log.getRequestNo();
		VoucherDetailModel voucherDetailModel = getVoucherDetailModel(detail, createTime, requestNo);
		
		Contract contract = contractService.getContractByUniqueId(detail.getContractUniqueId());
		ContractAccount contractAccount = contractAccountService.getTheEfficientContractAccountBy(contract);
		voucherDetailModel.update(contract, contractAccount);
		return voucherDetailModel;
	}

}

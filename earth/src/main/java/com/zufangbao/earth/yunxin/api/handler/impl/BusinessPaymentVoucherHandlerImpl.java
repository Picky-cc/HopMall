package com.zufangbao.earth.yunxin.api.handler.impl;

import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.FINANCIAL_CONTRACT_NOT_EXIST;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.NO_SUCH_CASH_FLOW;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.NO_SUCH_VOUCHER;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.NO_SUCH_VOUCHER_TYPE;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.REPEAT_REQUEST_NO;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.SYSTEM_BUSY;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.VOUCHER_CAN_NOT_CANCEL;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.handler.BusinessPaymentVoucherHandler;
import com.zufangbao.earth.yunxin.api.model.command.BusinessPaymentVoucherCommandModel;
import com.zufangbao.earth.yunxin.api.model.command.BusinessPaymentVoucherDetail;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.yunxin.api.BusinessPaymentVoucherLogService;
import com.zufangbao.sun.yunxin.entity.api.BusinessPaymentVoucherLog;
import com.zufangbao.sun.yunxin.entity.api.VoucherPayer;
import com.zufangbao.sun.yunxin.entity.api.VoucherType;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentDetail;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentDetailImporter;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentDetailStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.CashFlowHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentDetailService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentService;
import com.zufangbao.wellsfargo.yunxin.handler.vouchertask.impl.BusinessPaymentVoucherTaskHandlerImpl;

/**
 * 商户付款凭证接口
 * 
 * @author louguanyang
 *
 */
@Component("businessPaymentVoucherHandler")
public class BusinessPaymentVoucherHandlerImpl extends BusinessPaymentVoucherTaskHandlerImpl implements BusinessPaymentVoucherHandler {

	private static final Log logger = LogFactory.getLog(BusinessPaymentVoucherHandlerImpl.class);
	
	@Autowired
	private BusinessPaymentVoucherLogService logService;
	
	@Autowired
	private CashFlowHandler cashFlowHandler;
	
	@Autowired
	private ContractService contractService;
	@Autowired
	private FinancialContractService financialContractService;
	@Autowired
	private SourceDocumentService sourceDocumentService;

	@Autowired
	private SourceDocumentDetailService sourceDocumentDetailService;
	
	@Override
	public List<CashFlow> businessPaymentVoucher(BusinessPaymentVoucherCommandModel model, String ip) {
		checkByRequestNo(model.getRequestNo());
		saveLog(model, ip);
		return submitBusinessPaymentVoucher(model);
	}

	private void checkByRequestNo(String requestNo) throws ApiException {
		List<BusinessPaymentVoucherLog> result = logService.getLogByRequestNo(requestNo);
		if(CollectionUtils.isNotEmpty(result)) {
			throw new ApiException(REPEAT_REQUEST_NO);
		}
	}
	
	/**
	 * 撤销商户付款凭证接口
	 * @param model
	 */
	private void undoBusinessPaymentVoucher(BusinessPaymentVoucherCommandModel model) {
		FinancialContract financialContract = financialContractService.getUniqueFinancialContractBy(model.getFinancialContractNo());
		if(financialContract == null) {
			throw new ApiException(FINANCIAL_CONTRACT_NOT_EXIST, "找不到信托计划，信托计划信托计划产品代码：" + model.getFinancialContractNo());
		}
		Long financeCompanyId = financialContract.getCompany().getId();
		Long outlierCompanyId = financialContract.getApp().getCompany().getId();
		String bankTransactionNo = model.getBankTransactionNo();
		SourceDocument sourceDocument = sourceDocumentService.getBusinessPaymentSourceDocument(financeCompanyId, outlierCompanyId, bankTransactionNo);
		if(sourceDocument == null) {
			throw new ApiException(NO_SUCH_VOUCHER);
		}
		String sourceDocumentUuid = sourceDocument.getSourceDocumentUuid();
		List<SourceDocumentDetail> details = sourceDocumentDetailService.getDetailsBySourceDocumentUuid(sourceDocumentUuid, bankTransactionNo);
		if(CollectionUtils.isEmpty(details)) {
			throw new ApiException(NO_SUCH_VOUCHER);
		}
		if(details.stream().filter(detail -> detail.getStatus() == SourceDocumentDetailStatus.SUCCESS).count() > 0) {
			throw new ApiException(VOUCHER_CAN_NOT_CANCEL);
		}
		String requestNo = details.get(0).getFirstNo();
		cancelSourceDocumentDetails(sourceDocumentUuid, requestNo, bankTransactionNo);
	}

	/**
	 * 提交商户付款凭证接口
	 * @param model
	 */
	private List<CashFlow> submitBusinessPaymentVoucher(BusinessPaymentVoucherCommandModel model) {
		FinancialContract financialContract = financialContractService.getUniqueFinancialContractBy(model.getFinancialContractNo());
		
		Long financeCompanyId = financialContract.getCompany().getId();
		String paymentAccountNo = model.getPaymentAccountNo();//付款银行账号
		String paymentName = model.getPaymentName();//付款机构名称
		String paymentBank = model.getPaymentBank();//付款银行名称
		BigDecimal voucherAmount = model.getVoucherAmount();//凭证金额
		String bankTransactionNo = model.getBankTransactionNo();//打款流水号

		List<CashFlow> cashFlowList = cashFlowHandler.getUnattachedCashFlowListBy(financeCompanyId, paymentAccountNo, paymentName, voucherAmount, bankTransactionNo);
		if(CollectionUtils.isEmpty(cashFlowList)) {
			throw new ApiException(NO_SUCH_CASH_FLOW);
		}
		String sourceDocumentUuid = attachedSourceDocumentReturnUuid(financeCompanyId, bankTransactionNo, cashFlowList);
		createSourceDocumentDetails(model, paymentAccountNo, paymentName, paymentBank, bankTransactionNo, sourceDocumentUuid);
		return cashFlowList;
	}

	private void createSourceDocumentDetails(BusinessPaymentVoucherCommandModel model, String paymentAccountNo,
			String paymentName, String paymentBank, String bankTransactionNo, String sourceDocumentUuid) {
		VoucherType voucherType = model.getVoucherTypeEnum();
		if(voucherType == null) {
			throw new ApiException(NO_SUCH_VOUCHER_TYPE);
		}
		String receivableAccountNo = model.getReceivableAccountNo();
		List<BusinessPaymentVoucherDetail> detailModel = model.getDetailModel();
		for (BusinessPaymentVoucherDetail voucherDetail : detailModel) {
			String contractUniqueId = voucherDetail.getUniqueId();
			String repaymentPlanNo = voucherDetail.getRepaymentPlanNo();
			BigDecimal amount = voucherDetail.getAmount();
			VoucherPayer payer = VoucherPayer.fromValue(voucherDetail.getPayer());
			payer = payer == null? VoucherPayer.LOANER : payer;
			SourceDocumentDetail sourceDocumentDetail = SourceDocumentDetailImporter.createDetailBy(sourceDocumentUuid, contractUniqueId, repaymentPlanNo, amount, model.getRequestNo(), voucherType.getKey(), payer, receivableAccountNo, paymentAccountNo, paymentName, paymentBank);
			sourceDocumentDetail.setSecondNo(bankTransactionNo);//一级编号requestNo，二级编号打款流水号bankTransactionNo
			sourceDocumentDetailService.saveOrUpdate(sourceDocumentDetail);
			logger.info("生成凭证明细, sourceDocumentDetailUUID:" + sourceDocumentDetail.getUuid());
		}
	}

	private String attachedSourceDocumentReturnUuid(Long financeCompanyId, String bankTransactionNo, List<CashFlow> cashFlowList) {
		if(cashFlowList.size() == 1) {
			SourceDocument sourceDocument = sourceDocumentService.getSourceDocumentBy(financeCompanyId, cashFlowList.get(0).getCashFlowUuid());
			if(sourceDocument == null) {
				logger.error("流水对应凭证不存在");
				throw new ApiException(SYSTEM_BUSY, "流水对应凭证不存在");
			}
			sourceDocument.setOutlierSerialGlobalIdentity(bankTransactionNo);
			sourceDocumentService.saveOrUpdate(sourceDocument);
			return sourceDocument.getSourceDocumentUuid();
		}else{
			return null;
		}
	}

	private void saveLog(BusinessPaymentVoucherCommandModel model, String ip) {
		String requestNo = model.getRequestNo();
		int transactionType = model.getTransactionType();
		int voucherType = model.getVoucherType();
		BigDecimal voucherAmount = model.getVoucherAmount();
		String financialContractNo = model.getFinancialContractNo();
		String receivableAccountNo = model.getReceivableAccountNo();
		String paymentAccountNo = model.getPaymentAccountNo();
		String paymentName = model.getPaymentAccountNo();
		String paymentBank = model.getPaymentBank();
		String bankTransactionNo = model.getBankTransactionNo();
		BusinessPaymentVoucherLog log = new BusinessPaymentVoucherLog(requestNo, transactionType, voucherType, voucherAmount, financialContractNo, receivableAccountNo, paymentAccountNo, paymentName, paymentBank, bankTransactionNo, ip);
		logService.saveOrUpdate(log);		
	}
	@Override
	public void undoBusinessPaymentVoucher(BusinessPaymentVoucherCommandModel model, String ip) {
		checkByRequestNo(model.getRequestNo());
		saveLog(model, ip);
		undoBusinessPaymentVoucher(model);
	}
	@Override
	public void invalidSourceDocument(Long detailId) {
		SourceDocumentDetail detail = sourceDocumentDetailService.load(SourceDocumentDetail.class, detailId);
		boolean checkFails = sourceDocumentDetailService.isSourceDocumentDetailsCheckFails(detail);
		if(checkFails) {
			String sourceDocumentUuid = detail.getSourceDocumentUuid();
			String firstNo = detail.getFirstNo();
			String secondNo = detail.getSecondNo();
			cancelSourceDocumentDetails(sourceDocumentUuid, firstNo, secondNo);
		}
	}

	private void cancelSourceDocumentDetails(String sourceDocumentUuid, String firstNo, String secondNo) {
		sourceDocumentDetailService.cancelDetails(sourceDocumentUuid, firstNo, secondNo);
		sourceDocumentService.cancelSourceDocumentDetailAttach(sourceDocumentUuid, secondNo);
	}
	
	@Override
	public List<CashFlow> matchCashflow(Long detailId) {
		try {
			SourceDocumentDetail sourceDocumentDetail = sourceDocumentDetailService.load(SourceDocumentDetail.class, detailId);
			Contract contract = contractService.getContractByUniqueId(sourceDocumentDetail.getContractUniqueId());
			FinancialContract financialContract = financialContractService.getFinancialContractBy(contract.getFinancialContractUuid());
			Long financeCompanyId = financialContract.getCompany().getId();
			String paymentAccountNo = sourceDocumentDetail.getPaymentAccountNo();
			String paymentName = sourceDocumentDetail.getPaymentName();
			String firstNo = sourceDocumentDetail.getFirstNo();
			String bankTransactionNo = sourceDocumentDetail.getSecondNo();
			BigDecimal voucherAmount = sourceDocumentDetailService.getVoucherAmountOfSourceDocument(firstNo, bankTransactionNo);
			
			if(BigDecimal.ZERO.compareTo(voucherAmount) == 0) {
				return Collections.emptyList();
			}
			List<CashFlow> cashFlowList = cashFlowHandler.getUnattachedCashFlowListBy(financeCompanyId, paymentAccountNo, paymentName, voucherAmount, bankTransactionNo);
			return cashFlowList;
		} catch (Exception e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	@Override
	public void connectionCashFlow(Long detailId, String cashFlowUuid) {
		List<SourceDocument> sourceDocuments = sourceDocumentService.getDepositReceipt(cashFlowUuid);
		if(CollectionUtils.isEmpty(sourceDocuments)) {
			return;
		}
		SourceDocument sourceDocument = sourceDocuments.get(0);
		String sourceDocumentUuid = sourceDocument.getSourceDocumentUuid();
		SourceDocumentDetail sourceDocumentDetail = sourceDocumentDetailService.load(SourceDocumentDetail.class, detailId);
		String firstNo = sourceDocumentDetail.getFirstNo();
		String secondNo = sourceDocumentDetail.getSecondNo();
		sourceDocumentDetailService.appendSourceDocumentUuid(sourceDocumentUuid, firstNo, secondNo);
	}
}

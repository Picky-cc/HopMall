package com.zufangbao.earth.yunxin.api.handler.impl;

import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.FINANCIAL_CONTRACT_NOT_EXIST;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.NO_SUCH_CASH_FLOW;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.NO_SUCH_RECEIVABLE_ACCOUNT_NO;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.NO_SUCH_VOUCHER;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.NO_SUCH_VOUCHER_TYPE;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.REPEAT_REQUEST_NO;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.UNSUPPORTED_FILE_TYPE;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.VOUCHER_AMOUNT_ERROR;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.VOUCHER_CAN_NOT_CANCEL;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.exception.ApiResponseCode;
import com.zufangbao.earth.yunxin.api.handler.ActivePaymentVoucherHandler;
import com.zufangbao.earth.yunxin.api.model.command.ActivePaymentVoucherCommandModel;
import com.zufangbao.sun.entity.account.VirtualAccount;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.customer.Customer;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.icbc.business.CashFlow;
import com.zufangbao.sun.entity.icbc.business.ContractAccount;
import com.zufangbao.sun.service.ContractAccountService;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.VirtualAccountService;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.utils.FileUtils;
import com.zufangbao.sun.utils.FilenameUtils;
import com.zufangbao.sun.yunxin.api.ActivePaymentVoucherLogService;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.api.ActivePaymentVoucherLog;
import com.zufangbao.sun.yunxin.entity.api.VoucherPayer;
import com.zufangbao.sun.yunxin.entity.api.VoucherType;
import com.zufangbao.sun.yunxin.entity.model.voucher.VoucherCreateBaseModel;
import com.zufangbao.sun.yunxin.entity.model.voucher.VoucherCreateModel;
import com.zufangbao.sun.yunxin.log.LogFunctionType;
import com.zufangbao.sun.yunxin.log.LogOperateType;
import com.zufangbao.sun.yunxin.log.SystemOperateLog;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;
import com.zufangbao.sun.yunxin.service.SystemOperateLogService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocument;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentDetail;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentDetailCheckState;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentDetailImporter;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentDetailStatus;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentImporter;
import com.zufangbao.wellsfargo.silverpool.cashauditing.entity.voucher.SourceDocumentResource;
import com.zufangbao.wellsfargo.silverpool.cashauditing.handler.CashFlowHandler;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentDetailService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentResourceService;
import com.zufangbao.wellsfargo.silverpool.cashauditing.service.SourceDocumentService;

/**
 * 主动付款凭证接口
 * @author louguanyang
 *
 */
@Component("activePaymentVoucherHandler")
public class ActivePaymentVoucherHandlerImpl implements ActivePaymentVoucherHandler {

	private static final Log logger = LogFactory.getLog(ActivePaymentVoucherHandlerImpl.class);
	
	@Autowired
	private ActivePaymentVoucherLogService activePaymentVoucherLogService;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private FinancialContractService financialContractService;
	@Autowired
	private CashFlowHandler cashFlowHandler;
	@Autowired
	private SourceDocumentService sourceDocumentService;
	@Autowired
	private SourceDocumentDetailService sourceDocumentDetailService;
	@Autowired
	private VirtualAccountService virtualAccountService;
	@Autowired
	private SourceDocumentResourceService sourceDocumentResourceService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private ContractAccountService contractAccountService;
	@Autowired
	private SystemOperateLogService systemOperateLogService;
	
	@Override
	public void save_file_to_service(MultipartHttpServletRequest fileRequest, String sourceDocumentUuid, String requestNo) throws IOException {
		Iterator<String> fileNames = fileRequest.getFileNames();
		while (fileNames.hasNext()) {
			String fileName = (String) fileNames.next();
			List<MultipartFile> files = fileRequest.getFiles(fileName);
			process_files_and_save_path(files, sourceDocumentUuid, requestNo);
		}
	}

	@Value("#{config['voucherPath']}")
	private String voucherPath = "";
	
	private String[] extensions = {FilenameUtils.SUFFIX_PNG, FilenameUtils.SUFFIX_JPG, FilenameUtils.SUFFIX_PDF, FilenameUtils.SUFFIX_PNG_UPCASE, FilenameUtils.SUFFIX_JPG_UPCASE, FilenameUtils.SUFFIX_PDF_UPCASE};

	private void process_files_and_save_path(List<MultipartFile> files, String sourceDocumentUuid, String requestNo) throws IOException {
		for (MultipartFile multipartFile : files) {
			String tempFilePath = saveFileToServiceReturnPath(multipartFile);
			saveFilePath(sourceDocumentUuid, requestNo, tempFilePath);
			logger.info("save file to service success, file path:" + tempFilePath);
		}
	}

	private String saveFileToServiceReturnPath(MultipartFile multipartFile) throws IOException {
		String originalFilename = multipartFile.getOriginalFilename();
		if(!FilenameUtils.isExtension(originalFilename, extensions)) {
			throw new ApiException(UNSUPPORTED_FILE_TYPE);
		}
		String filePath = voucherPath + File.separator + DateUtils.today() + File.separator;
		String tempFilename = UUID.randomUUID().toString() + FilenameUtils.EXTENSION_SEPARATOR + FilenameUtils.getExtension(originalFilename);
		File temp = new File(filePath, tempFilename);
		FileUtils.writeByteArrayToFile(temp, multipartFile.getBytes());
		String tempFilePath = temp.getPath();
		return tempFilePath;
	}

	private void saveFilePath(String sourceDocumentUuid, String requestNo, String tempFilePath) {
		SourceDocumentResource resource = new SourceDocumentResource(sourceDocumentUuid, requestNo, tempFilePath);
		sourceDocumentResourceService.save(resource);
	}

	@Override
	public void submitActivePaymentVoucher(ActivePaymentVoucherCommandModel model, Contract contract, MultipartHttpServletRequest fileRequest) throws IOException {
		List<String> repaymentPlanNoArray = model.getRepaymentPlanNoArray();
		String receivableAccountNo = model.getReceivableAccountNo();
		BigDecimal voucherAmount = model.getVoucherAmount();
		String paymentAccountNo = model.getPaymentAccountNo();//付款银行账号
		String paymentName = model.getPaymentName();//付款机构名称
		String paymentBank = model.getPaymentBank();//付款银行名称
		String bankTransactionNo = model.getBankTransactionNo();//打款流水号
		VoucherType voucherType = model.getVoucherTypeEnum();
		String requestNo = model.getRequestNo();

		String sourceDocumentUuid = submit_active_payment_voucher_return_sourceDocumentUuid(contract, repaymentPlanNoArray, receivableAccountNo, voucherAmount, paymentAccountNo, paymentName, paymentBank, bankTransactionNo, voucherType, requestNo, StringUtils.EMPTY);
		save_file_to_service(fileRequest, sourceDocumentUuid, requestNo);
	}

	private String submit_active_payment_voucher_return_sourceDocumentUuid(Contract contract, List<String> repaymentPlanNoArray,
			String receivableAccountNo, BigDecimal voucherAmount, String paymentAccountNo, String paymentName,
			String paymentBank, String bankTransactionNo, VoucherType voucherType, String requestNo, String comment) {
		checkRepaymentPlanNoArray(repaymentPlanNoArray, contract);
		FinancialContract financialContract = financialContractService.getFinancialContractBy(contract.getFinancialContractUuid());
		checkReceivableAccountNo(receivableAccountNo, financialContract);
		checkVoucherAmount(repaymentPlanNoArray, voucherAmount);
		Long financeCompanyId = financialContract.getCompany().getId();
		List<CashFlow> cashFlowList = getUnattachedCashFlow(financeCompanyId, paymentAccountNo, paymentName, voucherAmount, bankTransactionNo);
		String sourceDocumentUuid = attachedSourceDocumentReturnUuid(financialContract, contract, cashFlowList, bankTransactionNo, comment);
		createSourceDocumentDetails(contract, sourceDocumentUuid, voucherType, repaymentPlanNoArray, receivableAccountNo, paymentAccountNo, paymentName, paymentBank, bankTransactionNo, requestNo);
		return sourceDocumentUuid;
	}

	private List<CashFlow> getUnattachedCashFlow(Long financeCompanyId, String paymentAccountNo, String paymentName, BigDecimal voucherAmount, String bankTransactionNo) {
		List<CashFlow> cashFlowList = cashFlowHandler.getUnattachedCashFlowListBy(financeCompanyId, paymentAccountNo, paymentName, voucherAmount, bankTransactionNo);
		if(CollectionUtils.isEmpty(cashFlowList)) {
			throw new ApiException(NO_SUCH_CASH_FLOW);
		}
		return cashFlowList;
	}

	private void checkVoucherAmount(List<String> repaymentPlanNoArray, BigDecimal voucherAmount) {
		BigDecimal sum = BigDecimal.ZERO;
		for (String repaymentPlanNo : repaymentPlanNoArray) {
			AssetSet assetSet = repaymentPlanService.getRepaymentPlanByRepaymentPlanNo(repaymentPlanNo);
			sum = sum.add(assetSet.getAmount());
		}
		if(voucherAmount.compareTo(sum) != BigDecimal.ZERO.intValue()) {
			throw new ApiException(VOUCHER_AMOUNT_ERROR);
		}
	}

	private void createSourceDocumentDetails(Contract contract, String sourceDocumentUuid, VoucherType voucherType, List<String> repaymentPlanNoArray, String receivableAccountNo, String paymentAccountNo, String paymentName, String paymentBank, String bankTransactionNo, String requestNo) {
		if(voucherType == null) {
			throw new ApiException(NO_SUCH_VOUCHER_TYPE);
		}
		String contractUniqueId = contract.getUniqueId();
		for (String repaymentPlanNo : repaymentPlanNoArray) {
			AssetSet assetSet = repaymentPlanService.getRepaymentPlanByRepaymentPlanNo(repaymentPlanNo);
			BigDecimal amount = assetSet.getAmount();
			VoucherPayer payer = VoucherPayer.LOANER;
			SourceDocumentDetail sourceDocumentDetail = SourceDocumentDetailImporter.createActivePaymentVoucherDetail(sourceDocumentUuid, contractUniqueId, repaymentPlanNo, amount, requestNo, voucherType.getKey(), payer, receivableAccountNo, paymentAccountNo, paymentName, paymentBank);
			sourceDocumentDetail.setSecondNo(bankTransactionNo);//一级编号requestNo，二级编号打款流水号bankTransactionNo
			sourceDocumentDetail.setCheckState(SourceDocumentDetailCheckState.CHECK_SUCCESS);
			sourceDocumentDetailService.saveOrUpdate(sourceDocumentDetail);
			logger.info("生成主动付款凭证明细, sourceDocumentDetailUUID:" + sourceDocumentDetail.getUuid());
		}
	}
	
	private String attachedSourceDocumentReturnUuid(FinancialContract financialContract, Contract contract, List<CashFlow> cashFlowList, String bankTransactionNo, String comment) {
		if(CollectionUtils.isEmpty(cashFlowList) || cashFlowList.size() > 1) {
			return null;
		}
		
		CashFlow cashFlow = cashFlowList.get(0);
		Long financeCompanyId = financialContract.getCompany().getId();
		SourceDocument sourceDocument = sourceDocumentService.getSourceDocumentBy(financeCompanyId, cashFlow.getCashFlowUuid());
		if(sourceDocument == null) {
			String financialContractUuid = financialContract.getUuid();
			Customer customer = contract.getCustomer();
			String customerUuid = customer.getCustomerUuid();
			String contractUuid = contract.getUuid();
			VirtualAccount virtualAccount = virtualAccountService.create_if_not_exist_virtual_account(customerUuid, financialContractUuid, contractUuid);
			sourceDocument = SourceDocumentImporter.createActivePaymentVoucherSourceDocument(financialContract, contract, cashFlow, virtualAccount);
			sourceDocumentService.save(sourceDocument);
		}
		sourceDocument.setOutlierSerialGlobalIdentity(bankTransactionNo);
		sourceDocument.setOutlierBreif(comment);
		sourceDocumentService.saveOrUpdate(sourceDocument);
		return sourceDocument.getSourceDocumentUuid();
	}
	
	private void checkRepaymentPlanNoArray(List<String> repaymentPlanNoArray, Contract contract) {
		boolean flag = repaymentPlanService.is_repaymentPlan_belong_contract(repaymentPlanNoArray, contract);
		if(flag == false) {
			throw new ApiException(ApiResponseCode.REPAYMENT_CODE_NOT_IN_CONTRACT);
		}
	}

	/***
	 * 校验收款账户
	 * @param receivableAccountNo
	 * @param contract
	 */
	private void checkReceivableAccountNo(String receivableAccountNo, FinancialContract financialContract) {
		try {
			if(StringUtils.isEmpty(receivableAccountNo)) {
				return;
			}
			if(null == financialContract) {
				throw new ApiException(FINANCIAL_CONTRACT_NOT_EXIST);
			}
			String accountNo = financialContract.getCapitalAccount().getAccountNo();
			if(StringUtils.equals(receivableAccountNo, accountNo) == false) {
				throw new ApiException(NO_SUCH_RECEIVABLE_ACCOUNT_NO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApiException(NO_SUCH_RECEIVABLE_ACCOUNT_NO, e.getMessage());
		}
	}

	@Override
	public void checkRequestNoAndSaveLog(ActivePaymentVoucherCommandModel model, String ip) {
		List<ActivePaymentVoucherLog> logs = activePaymentVoucherLogService.getLogByRequestNo(model.getRequestNo());
		if(CollectionUtils.isNotEmpty(logs)) {
			throw new ApiException(REPEAT_REQUEST_NO);
		}
		String requestNo = model.getRequestNo();
		Integer transactionType = model.getTransactionType();
		Integer voucherType = model.getVoucherType();
		String uniqueId = model.getUniqueId();
		String contractNo = model.getContractNo();
		String repaymentPlanNo = model.getRepaymentPlanNo();
		String receivableAccountNo = model.getReceivableAccountNo();
		String paymentBank = model.getPaymentBank();
		String bankTransactionNo = model.getBankTransactionNo();
		BigDecimal voucherAmount = model.getVoucherAmount();
		String paymentName = model.getPaymentName();
		String paymentAccountNo = model.getPaymentAccountNo();
		ActivePaymentVoucherLog log = new ActivePaymentVoucherLog(requestNo, transactionType, voucherType, uniqueId, contractNo, repaymentPlanNo, receivableAccountNo, paymentBank, bankTransactionNo, voucherAmount, paymentName, paymentAccountNo, ip);
		activePaymentVoucherLogService.save(log);
	}

	/**
	 * 撤销主动付款凭证接口
	 * @param contract
	 * @param bankTransactionNo
	 */
	@Override
	public void undoActivePaymentVoucher(Contract contract, String bankTransactionNo) {
		FinancialContract financialContract = financialContractService.getFinancialContractBy(contract.getFinancialContractUuid());
		if(financialContract == null) {
			throw new ApiException(FINANCIAL_CONTRACT_NOT_EXIST);
		}
		Long financeCompanyId = financialContract.getCompany().getId();
		Long outlierCompanyId = financialContract.getApp().getCompany().getId();
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
	
	private void cancelSourceDocumentDetails(String sourceDocumentUuid, String firstNo, String secondNo) {
		sourceDocumentDetailService.cancelDetails(sourceDocumentUuid, firstNo, secondNo);
		sourceDocumentService.cancelSourceDocumentDetailAttach(sourceDocumentUuid, secondNo);
		List<SourceDocumentResource> resource = sourceDocumentResourceService.get(sourceDocumentUuid, firstNo);
		for (SourceDocumentResource res : resource) {
			File file = new File(res.getPath());
			FileUtils.deleteQuietly(file);
			sourceDocumentResourceService.delete(res);
		}
	}

	@Override
	public VoucherCreateBaseModel searchAccountInfoByContractNo(String contractNo) {
		Contract contract = contractService.getContractByContractNo(contractNo);
		ContractAccount contractAccount = contractAccountService.getTheEfficientContractAccountBy(contract);
		List<AssetSet> asset_set_list = repaymentPlanService.get_all_unclear_and_open_asset_set_list(contract);
		if(null == contract || null == contractAccount || CollectionUtils.isEmpty(asset_set_list)) {
			return null;
		}
		return new VoucherCreateBaseModel(contract, contractAccount, asset_set_list);
	}

	@Override
	public List<ContractAccount> searchAccountInfoByName(String name) {
		return contractAccountService.getTheEfficientContractAccountListByPayerName(name);
	}

	@Override
	public void saveActiveVoucher(VoucherCreateModel model, Principal principal, String ip) throws IOException {
		Contract contract = contractService.getContractByContractNo(model.getContractNo());
		if(null == contract) {
			throw new RuntimeException("贷款合同编号错误！");
		}
		String sourceDocumentUuid = this.submitActivePaymentVoucher(model, contract);
		String requestNo = UUID.randomUUID().toString();
		List<String> resourceUuids = model.getResourceUuidArray();
		if(resourceUuids != null) {
			// 更新资源文件关联关系
			sourceDocumentResourceService.updateResourceConnectionRelation(resourceUuids, sourceDocumentUuid, requestNo);
		}
		
		saveCreateLog(principal, sourceDocumentUuid, ip);
	}

	private void saveCreateLog(Principal principal, String sourceDocumentUuid, String ipAddress) {
		String recordContent = "新增主动付款凭证，【操作人】" + principal.getUsername() + "，【操作时间】" + DateUtils.getNowFullDateTime() + ",【IP】" + ipAddress;
		Long userId = principal.getId();
		SystemOperateLog log = SystemOperateLog.createLog(userId, recordContent, ipAddress, LogFunctionType.ACTIVE_PAYMENT_VOUCHER_CREATE, LogOperateType.ADD, sourceDocumentUuid, sourceDocumentUuid);
		systemOperateLogService.saveOrUpdate(log);
	}

	private String submitActivePaymentVoucher(VoucherCreateModel model, Contract contract) {
		List<String> repaymentPlanNoArray = model.getRepaymentPlanNoArray();
		String receivableAccountNo = model.getReceivableAccountNo();
		BigDecimal voucherAmount = model.getVoucherAmount();
		String paymentAccountNo = model.getPaymentAccountNo();//付款银行账号
		String paymentName = model.getPaymentName();//付款机构名称
		String paymentBank = model.getPaymentBank();//付款银行名称
		String bankTransactionNo = model.getBankTransactionNo();//打款流水号
		VoucherType voucherType = model.getVoucherTypeEnum();
		String requestNo = UUID.randomUUID().toString();
		String comment = model.getComment();
		
		return submit_active_payment_voucher_return_sourceDocumentUuid(contract, repaymentPlanNoArray, receivableAccountNo, voucherAmount, paymentAccountNo, paymentName, paymentBank, bankTransactionNo, voucherType, requestNo, comment);
	}

	@Override
	public void updateActiveVoucherComment(Long detailId, String comment) {
		SourceDocumentDetail sourceDocumentDetail = sourceDocumentDetailService.load(SourceDocumentDetail.class, detailId);
		if(null == sourceDocumentDetail) {
			throw new ApiException(NO_SUCH_VOUCHER);
		}
		if(sourceDocumentDetail.isInvalid()) {
			throw new ApiException("当前凭证已作废");
		}
		SourceDocument sourceDocument = sourceDocumentService.getSourceDocumentBy(sourceDocumentDetail.getSourceDocumentUuid());
		sourceDocument.setOutlierBreif(comment);
		sourceDocumentService.update(sourceDocument);
	}

	@Override
	public List<String> getActiveVoucherResource(Long detailId) {
		SourceDocumentDetail sourceDocumentDetail = sourceDocumentDetailService.load(SourceDocumentDetail.class, detailId);
		if(null == sourceDocumentDetail) {
			return Collections.emptyList();
		}
		List<SourceDocumentResource> list = sourceDocumentResourceService.get(sourceDocumentDetail.getSourceDocumentUuid(), sourceDocumentDetail.getFirstNo());
		return list.stream().map(resource -> resource.getPath()).collect(Collectors.toList());
	}

	@Override
	public String uploadSingleFileReturnUUID(MultipartFile file) throws IOException {
		String tempFilePath = saveFileToServiceReturnPath(file);
		SourceDocumentResource sourceDocumentResource = new SourceDocumentResource(tempFilePath);
		sourceDocumentResourceService.save(sourceDocumentResource);
		return sourceDocumentResource.getUuid();
	}

}

package com.zufangbao.earth.yunxin.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.demo2do.core.utils.DateUtils;
import com.zufangbao.earth.api.exception.TransactionDetailApiException;
import com.zufangbao.earth.entity.security.Principal;
import com.zufangbao.earth.yunxin.handler.SystemOperateLogHandler;
import com.zufangbao.earth.yunxin.handler.UnionPayHandler;
import com.zufangbao.earth.yunxin.unionpay.component.impl.GZUnionPayApiComponentImpl;
import com.zufangbao.earth.yunxin.unionpay.model.TransactionDetailNode;
import com.zufangbao.earth.yunxin.unionpay.model.TransactionDetailQueryInfoModel;
import com.zufangbao.earth.yunxin.unionpay.model.TransactionDetailQueryResult;
import com.zufangbao.earth.yunxin.unionpay.model.basic.IGZUnionPayApiParams;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.entity.financial.PaymentChannel;
import com.zufangbao.sun.entity.financial.TransferApplication;
import com.zufangbao.sun.service.AssetPackageService;
import com.zufangbao.sun.service.ContractAccountService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.service.TransferApplicationService;
import com.zufangbao.sun.utils.excel.ExcelUtil;
import com.zufangbao.sun.yunxin.entity.DailyPaymentCheckFlowExcel;
import com.zufangbao.sun.yunxin.entity.DailyPaymentGDUnionPayFlowExcel;
import com.zufangbao.sun.yunxin.entity.DailyReturnListExcel;
import com.zufangbao.sun.yunxin.log.LogFunctionType;
import com.zufangbao.sun.yunxin.log.LogOperateType;
import com.zufangbao.sun.yunxin.log.SystemOperateLogRequestParam;

@Component("dailyYunxinPaymentFlowCheckExcel")
public class DailyYunxinPaymentFlowCheckExcel {

	private static final String QUERY_GZ_UNION_PAY_DEFAULT = "";
	private static final String QUERY_GZ_UNION_PAY_FIRST_PAGE = "1";

	@Autowired
	private TransferApplicationService transferApplicationService;
	@Autowired
	private UnionPayHandler unionPayHandlerl;
	@Autowired
	private GZUnionPayApiComponentImpl gZUnionPayHandlerImpl;
	@Autowired
	private FinancialContractService financialContractService;
	@Autowired
	private AssetPackageService assetPackageService;
	@Autowired
	private ContractAccountService contractAccountService;
	@Autowired
	private SystemOperateLogHandler systemOperateLogHandler;

	public HSSFWorkbook gzUnionpayExcel(Principal principal, String ip,
			String queryDate, Long financialContractId) {
		HSSFWorkbook workbook = create_payment_detail_excel(principal, ip,
				queryDate, financialContractId);
		TransactionDetailQueryInfoModel transactionDetailQueryInfoModel = createRequestQueryModel(
				queryDate, financialContractId);
		TransactionDetailQueryResult result = new TransactionDetailQueryResult();

		try {
			result = gZUnionPayHandlerImpl
					.execTransactionDetailQuery(transactionDetailQueryInfoModel);
		} catch (TransactionDetailApiException e) {
			e.printStackTrace();
			workbook.createSheet("广东银联流水");
			return workbook;
		}

		List<DailyPaymentGDUnionPayFlowExcel> dailyPaymentGDUnionPayFlowExcels = new ArrayList<DailyPaymentGDUnionPayFlowExcel>();
		List<TransactionDetailNode> nodes = result.getDetailNodes();
		if (result.getDetailNodes() == null
				|| result.getDetailNodes().size() == 0) {
			workbook.createSheet("广东银联流水");
			return workbook;
		}
		for (TransactionDetailNode node : nodes) {
			DailyPaymentGDUnionPayFlowExcel dailyPaymentGDUnionPayFlowExcel = new DailyPaymentGDUnionPayFlowExcel();
			dailyPaymentGDUnionPayFlowExcel.setReqNo(node.getReqNo());
			dailyPaymentGDUnionPayFlowExcel.setSn(node.getSn());
			dailyPaymentGDUnionPayFlowExcel.setSfType(node.getSfType());
			dailyPaymentGDUnionPayFlowExcel.setSettDate(node.getSettDate());
			dailyPaymentGDUnionPayFlowExcel.setReckonAccount(node
					.getReckonAccount());
			dailyPaymentGDUnionPayFlowExcel.setMsg(node.getErrMsg());
			dailyPaymentGDUnionPayFlowExcel.setCompleteTime(node
					.getCompleteTime());
			dailyPaymentGDUnionPayFlowExcel.setAmount(node.getAmount()
					.toString());
			dailyPaymentGDUnionPayFlowExcel.setAccountName(node
					.getAccountName());
			dailyPaymentGDUnionPayFlowExcel.setAccount(node.getAccount());
			dailyPaymentGDUnionPayFlowExcels
					.add(dailyPaymentGDUnionPayFlowExcel);
		}
		ExcelUtil<DailyPaymentGDUnionPayFlowExcel> excelUtil = new ExcelUtil<DailyPaymentGDUnionPayFlowExcel>(
				DailyPaymentGDUnionPayFlowExcel.class);
		return excelUtil.exportDataToHSSFWork(workbook,
				dailyPaymentGDUnionPayFlowExcels, "广东银联流水");

	}

	public HSSFWorkbook dailyRetuenListExcel(Principal principal, String ip,
			String queryDate, Long financialContractId) {
		List<TransferApplication> transferApplications = transferApplicationService
				.queryTheDateTransferApplicationOrderByStatus(
						financialContractId, queryDate);

		List<DailyReturnListExcel> dailyReturnListExcels = new ArrayList<DailyReturnListExcel>();
		List<String> transferApplicationUuids = new ArrayList<String>();
		for (TransferApplication transferApplication : transferApplications) {
			DailyReturnListExcel dailyReturnListExcel = new DailyReturnListExcel(
					transferApplication);
			dailyReturnListExcels.add(dailyReturnListExcel);
			transferApplicationUuids.add(transferApplication
					.getTransferApplicationUuid());
		}
		if(CollectionUtils.isEmpty(transferApplications)) {
			dailyReturnListExcels.add(new DailyReturnListExcel());
		}
		
		ExcelUtil<DailyReturnListExcel> excelUtil = new ExcelUtil<DailyReturnListExcel>(
				DailyReturnListExcel.class);
		HSSFWorkbook workbook = excelUtil.exportDataToHSSFWork(
				dailyReturnListExcels, "每日还款清单");

		if (CollectionUtils.isEmpty(dailyReturnListExcels)) {
			workbook.createSheet("每日还款清单");
			return workbook;
		}
		SystemOperateLogRequestParam param = getSystemOperateLogRequestParam(
				principal, ip, transferApplicationUuids);
		try {
			systemOperateLogHandler.generateSystemOperateLog(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;

	}

	private SystemOperateLogRequestParam getSystemOperateLogRequestParam(
			Principal principal, String ip,
			List<String> transferApplicationUuids) {
		SystemOperateLogRequestParam param = new SystemOperateLogRequestParam(
				principal.getId(), ip, null,
				LogFunctionType.ONLINEBILLEXPORTDAILYRETURNLIST,
				LogOperateType.EXPORT, null, null, null,
				transferApplicationUuids);
		return param;
	}

	/**
	 * @param queryDate
	 * @param financialContract
	 * @return
	 */
	private TransactionDetailQueryInfoModel createRequestQueryModel(
			String queryDate, Long financialContractId) {
		TransactionDetailQueryInfoModel model = new TransactionDetailQueryInfoModel();
		String queryForMatDate = DateUtils.format(
				DateUtils.parseDate(queryDate, "yyyy-MM-dd"), "yyyyMMdd");
		model.setBeginDate(queryForMatDate);
		model.setEndDate(queryForMatDate);

		FinancialContract financialContract = financialContractService.load(
				FinancialContract.class, financialContractId);
		PaymentChannel paymentChannel = financialContract.getPaymentChannel();
		model.setUserName(paymentChannel.getUserName());
		model.setUserPwd(paymentChannel.getUserPassword());
		model.setMerchantId(paymentChannel.getMerchantId());
		model.setApiUrl(paymentChannel.getApiUrl());
		model.setReqNo(UUID.randomUUID().toString());
		model.setPageNum(QUERY_GZ_UNION_PAY_FIRST_PAGE);
		model.setPageSize(QUERY_GZ_UNION_PAY_DEFAULT);
		setGZUnionPayParams(model, paymentChannel);
		return model;
	}

	private void setGZUnionPayParams(IGZUnionPayApiParams apiParams,
			PaymentChannel paymentChannel) {
		apiParams.setApiUrl(paymentChannel.getApiUrl());
		apiParams.setCerFilePath(paymentChannel.getCerFilePath());
		apiParams.setPfxFilePath(paymentChannel.getPfxFilePath());
		apiParams.setPfxFileKey(paymentChannel.getPfxFileKey());
	}

	private HSSFWorkbook create_payment_detail_excel(Principal principal,
			String ip, String queryDate, Long financialContractId) {
		List<TransferApplication> transferApplications = transferApplicationService
				.queryTheDateTransferApplicationOrderByStatus(
						financialContractId, queryDate);

		List<DailyPaymentCheckFlowExcel> dailyPaymentCheckFlowExcels = new ArrayList<DailyPaymentCheckFlowExcel>();
		List<String> transferApplicationUuids = new ArrayList<String>();
		for (TransferApplication transferApplication : transferApplications) {
			DailyPaymentCheckFlowExcel dailyPaymentCheckFlowExcel = new DailyPaymentCheckFlowExcel(
					transferApplication);
			dailyPaymentCheckFlowExcels.add(dailyPaymentCheckFlowExcel);
			transferApplicationUuids.add(transferApplication
					.getTransferApplicationUuid());
		}

		ExcelUtil<DailyPaymentCheckFlowExcel> excelUtil = new ExcelUtil<DailyPaymentCheckFlowExcel>(
				DailyPaymentCheckFlowExcel.class);
		if (dailyPaymentCheckFlowExcels.size() == 0) {
			HSSFWorkbook workbook = new HSSFWorkbook();// 产生工作薄对象
			workbook.createSheet("支付单详情");
			return workbook;
		}
		HSSFWorkbook workbook = excelUtil.exportDataToHSSFWork(
				dailyPaymentCheckFlowExcels, "支付单详情");
		SystemOperateLogRequestParam param = getSystemOperateLogRequestNo(
				principal, ip, transferApplicationUuids);

		try {
			systemOperateLogHandler.generateSystemOperateLog(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	private SystemOperateLogRequestParam getSystemOperateLogRequestNo(
			Principal principal, String ip,
			List<String> transferApplicationUuids) {
		SystemOperateLogRequestParam param = new SystemOperateLogRequestParam(
				principal.getId(), ip, null,
				LogFunctionType.ONLINEBILLEXPORTCHECKING,
				LogOperateType.EXPORT, null, null, null,
				transferApplicationUuids);
		return param;
	}

}

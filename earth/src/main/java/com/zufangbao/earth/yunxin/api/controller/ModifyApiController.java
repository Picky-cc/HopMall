package com.zufangbao.earth.yunxin.api.controller;

import static com.zufangbao.earth.yunxin.api.constant.ApiConstant.PARAMS_FN_KEY_WITH_COMBINATORS;
import static com.zufangbao.earth.yunxin.api.constant.ModifyOpsFunctionCodes.IMPORT_ASSET_PACKAGE;
import static com.zufangbao.earth.yunxin.api.constant.ModifyOpsFunctionCodes.MODIFY_OVERDUE_FEE;
import static com.zufangbao.earth.yunxin.api.constant.ModifyOpsFunctionCodes.MODIFY_REPAYMENT_INFORMATION;
import static com.zufangbao.earth.yunxin.api.constant.ModifyOpsFunctionCodes.MODIFY_REPAYMENT_PLAN;
import static com.zufangbao.earth.yunxin.api.constant.ModifyOpsFunctionCodes.PREPAYMENT;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo2do.core.utils.JsonUtils;
import com.zufangbao.earth.util.IpUtil;
import com.zufangbao.earth.yunxin.api.BaseApiController;
import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.exception.ApiResponseCode;
import com.zufangbao.earth.yunxin.api.handler.AssetSetApiHandler;
import com.zufangbao.earth.yunxin.api.handler.ContractApiHandler;
import com.zufangbao.earth.yunxin.api.handler.ImportAssetPackageApiHandler;
import com.zufangbao.earth.yunxin.api.handler.RepaymentInformationApiHandler;
import com.zufangbao.earth.yunxin.api.model.modify.ImportAssetPackageContent;
import com.zufangbao.earth.yunxin.api.model.modify.ImportAssetPackageRequestModel;
import com.zufangbao.earth.yunxin.api.model.modify.ModifyOverDueFeeRequestModel;
import com.zufangbao.earth.yunxin.api.model.modify.RepaymentInformationModifyModel;
import com.zufangbao.earth.yunxin.api.model.modify.RepaymentPlanModifyModel;
import com.zufangbao.earth.yunxin.api.model.response.ImportAssetPackageResponseModel;
import com.zufangbao.earth.yunxin.api.service.ImportAssetPackageLogService;
import com.zufangbao.earth.yunxin.api.service.PrepaymentApplicationService;
import com.zufangbao.earth.yunxin.api.service.UpdateRepaymentInformationLogService;
import com.zufangbao.earth.yunxin.handler.modifyOverDueFee.ModifyOverDueFeeHandler;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.ledgerbook.LedgerBookHandler;
import com.zufangbao.sun.ledgerbook.LedgerTradeParty;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.utils.CheckFormatUtils;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.RepaymentWay;
import com.zufangbao.sun.yunxin.entity.model.api.ContractDetail;
import com.zufangbao.sun.yunxin.entity.model.api.ImportRepaymentPlanDetail;
import com.zufangbao.sun.yunxin.entity.model.api.PrepaymentModifyModel;
import com.zufangbao.wellsfargo.yunxin.handler.OrderHandler;

@Controller
@RequestMapping("/api/modify")
public class ModifyApiController extends BaseApiController {

	private static final Log logger = LogFactory.getLog(ModifyApiController.class);

	@Autowired
	private ContractApiHandler contractApiHandler;

	@Autowired
	private AssetSetApiHandler assetSetApiHandler;

	@Autowired
	private PrepaymentApplicationService prepaymentApplicationService;
	
	@Autowired
	private RepaymentInformationApiHandler repaymentInformationApiHandler;

	@Autowired
	private UpdateRepaymentInformationLogService updateRepaymentInformationLogService;
	
	@Autowired
	private ImportAssetPackageLogService importAssetPackageLogService;
	
	@Autowired
	private FinancialContractService financialContractService;
	
	@Autowired
	private LedgerBookHandler ledgerBookHandler;
	
	@Autowired
	private OrderHandler orderHandler;
	
	@Autowired
	private ModifyOverDueFeeHandler modifyOverDueFeeHandler;
	
	@Autowired
	private ImportAssetPackageApiHandler importAssetPackageHandler;
	
	@Autowired
	private ContractService contractService;

	@RequestMapping(value = "", params = { PARAMS_FN_KEY_WITH_COMBINATORS + MODIFY_REPAYMENT_PLAN}, method = RequestMethod.POST)
	public @ResponseBody String modifyRepaymentPlan(@ModelAttribute RepaymentPlanModifyModel modifyModel, HttpServletRequest request, HttpServletResponse response) {
		try {
			logger.info("开始调用变更还款计划接口, 时间:" + DateUtils.getCurrentTimeMillis() + "请求参数：" + modifyModel.getRequestParamsInfo(request));
			if(modifyModel.paramsHasError()) {
				logger.info("开始调用异常变更还款计划接口错误！");
				return signErrorResult(response, ApiResponseCode.INVALID_PARAMS, modifyModel.getCheckFailedMsg());
			}
			Contract contract = contractApiHandler.getContractBy(modifyModel.getUniqueId(), modifyModel.getContractNo());
			Integer oldActiveVersionNo = contractService.getActiveVersionNo(contract.getUuid());
			List<AssetSet> newCreatedAssets = assetSetApiHandler.modify_repaymentPlan(contract, modifyModel, IpUtil.getIpAddress(request), oldActiveVersionNo);
			FinancialContract financialContract = financialContractService.getFinancialContractBy(contract.getFinancialContractUuid());
			record_ledger_book_and_value_asset_and_create_order(financialContract, newCreatedAssets,contract);
			return signSucResult(response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("#modifyRepaymentPlan occur error [requestNo : "+ modifyModel.getRequestNo() +" ]!", e);
			return signErrorResult(response, e);
		}
	}
	
	private void record_ledger_book_and_value_asset_and_create_order(FinancialContract financialContract, List<AssetSet> assets,Contract contract) throws Exception{
		if(financialContract==null || CollectionUtils.isEmpty(assets)){
			return;
		}
		try{
			String companyUuid = financialContract.getCompany().getUuid();
			String customerUuid = contract.getCustomer()==null?"":contract.getCustomer().getCustomerUuid();
			LedgerTradeParty party = new LedgerTradeParty(companyUuid,customerUuid);
			ledgerBookHandler.book_loan_assets(financialContract.getLedgerBookNo(), assets, party);
		} catch(Exception e){
			logger.error(" book loan asset occur error. ");
			throw e;
		}
		
		//value assetSet and create order
		for (AssetSet assetSet : assets) {
			if(assetSet!=null && assetSet.isAssetRecycleDate(new Date())){
				orderHandler.assetValuationAndCreateOrder(assetSet.getId(), new Date());
			}
		}
		
	}

	@RequestMapping(value = "", params = { PARAMS_FN_KEY_WITH_COMBINATORS
			+ MODIFY_REPAYMENT_INFORMATION }, method = RequestMethod.POST)
	public @ResponseBody String modifyRepaymentInformation(
			@ModelAttribute RepaymentInformationModifyModel modifyModel,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("开始调用变更还款信息接口，时间:" + DateUtils.getCurrentTimeMillis()
				+ "请求参数：[ uniqueId: " + modifyModel.getUniqueId() + ", contractNo: "
				+ modifyModel.getContractNo() + ", requestNo: " + modifyModel.getRequestNo()
				+ IpUtil.getIpAddress(request) + "]");
		try {
			if(!modifyModel.isValid()) {
				return signErrorResult(response, ApiResponseCode.INVALID_PARAMS, modifyModel.getCheckFailedMsg());
			}
			Contract contract = contractApiHandler.getContractBy(modifyModel.getUniqueId(), modifyModel.getContractNo());
			
			checkRequestNoAndSaveLog(modifyModel, request, contract);
			repaymentInformationApiHandler.modifyRepaymentInformation(modifyModel, request, contract);
			return signSucResult(response); 
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("#modifyRepaymentInformation occur error [requestNo : "+ modifyModel.getRequestNo() +" ]!");
			return signErrorResult(response, e);
		}

	}

	private void checkRequestNoAndSaveLog(
			RepaymentInformationModifyModel modifyModel,
			HttpServletRequest request, Contract contract) {
		updateRepaymentInformationLogService.checkRequestNo(modifyModel.getRequestNo());
		updateRepaymentInformationLogService.generateAndSaveUpdateRepaymentInformationLog(modifyModel, request, contract);
	}

	@RequestMapping(value = "", params = { PARAMS_FN_KEY_WITH_COMBINATORS + PREPAYMENT }, method = RequestMethod.POST)
	public @ResponseBody String prepayment(
			@ModelAttribute PrepaymentModifyModel model,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			String ipAddress = IpUtil.getIpAddress(request);
			logger.info("开始调用提前还款接口, 时间:" + DateUtils.getCurrentTimeMillis() + ", 请求 ip:" + ipAddress + "]");
			if (!model.isValid()) {
				return signErrorResult(response, ApiResponseCode.INVALID_PARAMS, model.getCheckFailedMsg());
			}
			prepaymentApplicationService.checkByRequestNo(model.getRequestNo());
			Contract contract = contractApiHandler.getContractBy(model.getUniqueId(), model.getContractNo());
			List<AssetSet> newCreatedAssets = assetSetApiHandler.prepayment(contract, model, ipAddress);
			FinancialContract financialContract = financialContractService.getFinancialContractBy(contract.getFinancialContractUuid());
			record_ledger_book_and_value_asset_and_create_order(financialContract, newCreatedAssets, contract);
			return signSucResult(response);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("#prepayment occur error [requestNo : "+ model.getRequestNo() +" ]!");
			return signErrorResult(response, e);
		}
	}

	
	@RequestMapping(value = "", params = { PARAMS_FN_KEY_WITH_COMBINATORS + IMPORT_ASSET_PACKAGE }, method = RequestMethod.POST)
	public @ResponseBody String importAssetPackage(
			@ModelAttribute ImportAssetPackageRequestModel model,
			HttpServletRequest request, HttpServletResponse response) {
		
		try {
			String ipAddress = IpUtil.getIpAddress(request);
			logger.info("开始调用资产包导入, 时间:" + DateUtils.getCurrentTimeMillis() + ", 请求 ip:" + ipAddress + "]");
			if (!model.isValid()) {
				return signErrorResult(response, ApiResponseCode.INVALID_PARAMS, model.getCheckFailedMsg());
			}
			importAssetPackageLogService.checkByRequestNo(model.getRequestNo());
			importAssetPackageLogService.generateImportAssetPackageLog(model, request);
			validateRequestDataFormat(model);
			ImportAssetPackageResponseModel responseModel = importAssetPackageHandler.importAssetPackage(model);
			System.out.println(JsonUtils.toJsonString(responseModel));
			return signSucResult(response,"batchResponse",responseModel);
		} catch (ApiException e) {
			e.printStackTrace();
			logger.error("#import asset package occur error [requestNo : "+ model.getRequestNo() +" ]!");
			return signErrorResult(response, e);
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("#import asset package occur error [requestNo : "+ model.getRequestNo() +" ]!");
			return signErrorResult(response, e);
		} catch(Exception e){
			e.printStackTrace();
			logger.error("#import asset package occur error [requestNo : "+ model.getRequestNo() +" ]!");
			return signErrorResult(response, e);
		}
	}
	
	@RequestMapping(value = "", params = { PARAMS_FN_KEY_WITH_COMBINATORS + MODIFY_OVERDUE_FEE }, method = RequestMethod.POST)
	public @ResponseBody String modifyOverDueFee(
			@ModelAttribute ModifyOverDueFeeRequestModel model,
			HttpServletRequest request, HttpServletResponse response) {
		
		try {
			String ipAddress = IpUtil.getIpAddress(request);
			logger.info("开始调用逾期费用修改接口, 时间:" + DateUtils.getCurrentTimeMillis() + ", 请求 ip:" + ipAddress + "]");
			if (!model.isValid()) {
				return signErrorResult(response, ApiResponseCode.INVALID_PARAMS, model.getCheckFailedMsg());
			}
			
			modifyOverDueFeeHandler.modifyOverDueFeeAndCheckData(model, request);
			return signSucResult(response);
		} catch (ApiException e) {
			e.printStackTrace();
			logger.error("#modify over due fee error [requestNo : "+ model.getRequestNo() +" ]!");
			return signErrorResult(response, e);
	}
	}
	private void validateRequestDataFormat(ImportAssetPackageRequestModel model) {
		
		ImportAssetPackageContent requestContent = model.getRequestContentObject();
		
		validateTotalData(requestContent);
		List<ContractDetail> contractDetails = requestContent.getContractDetails();
		for(ContractDetail contractDetail :contractDetails ){
			validateContractData(contractDetail);
			List<ImportRepaymentPlanDetail>  repaymentPlans = contractDetail.getRepaymentPlanDetails();
			for(ImportRepaymentPlanDetail repaymentPlanDetail : repaymentPlans){
				validateRepaymentPlanData(repaymentPlanDetail);
			}
			checkRepaymentDetailsRepaymentDate(repaymentPlans);
		}
		
	}

	private void checkRepaymentDetailsRepaymentDate(
			List<ImportRepaymentPlanDetail> repaymentPlans) {
		Date maxDate =DateUtils.asDay(repaymentPlans.get(0).getRepaymentDate());
		for(int index=0 ;index <repaymentPlans.size();index++){
			if(maxDate.after(DateUtils.asDay(repaymentPlans.get(index).getRepaymentDate()))){
				throw new ApiException(ApiResponseCode.REPAYMENT_PLAN_REPAYMENT_DATE_ERROR);
			}
			maxDate = DateUtils.asDay(repaymentPlans.get(index).getRepaymentDate());
		}
	}

	private void validateTotalData(ImportAssetPackageContent requestContent) {
		if(requestContent.getThisBatchContractsTotalNumber() <=0){
			processError(ApiResponseCode.TOTAL_NUMBER_ERROR);
		}
		if(StringUtils.isEmpty(requestContent.getThisBatchContractsTotalAmount()) || !CheckFormatUtils.checkRMBCurrency(requestContent.getThisBatchContractsTotalAmount())){
			processError(ApiResponseCode.TOTAL_AMOUNT_ERROR);
		}
		if(CollectionUtils.isEmpty(requestContent.getContractDetails())){
			processError(ApiResponseCode.TOTAL_CONTRACTS_NUMBER_NOT_MATCH);
		}
		if(StringUtils.isEmpty(requestContent.getFinancialProductCode()) ){
			processError(ApiResponseCode.FINANCIAL_PRODUCT_CODE_ERROR);
		}
		for(ContractDetail contractDetail :requestContent.getContractDetails() ){
			if(CollectionUtils.isEmpty(contractDetail.getRepaymentPlanDetails())){
				processError(ApiResponseCode.REPAYMENT_PLAN_TOTAL_PERIODS_ERROR);
			}
		}
	}

	private void validateContractData(ContractDetail contractDetail) {
		if(StringUtils.isEmpty(contractDetail.getLoanCustomerName())){
			processError(ApiResponseCode.LOAN_CUSTOMER_NAME_ERROR);
		}
		if(StringUtils.isEmpty(contractDetail.getLoanCustomerNo()) ){
			processError(ApiResponseCode.LOAN_CUSTOMER_NO_ERROR);
		}
		if(StringUtils.isEmpty(contractDetail.getIDCardNo()) || !CheckFormatUtils.checkIDCard(contractDetail.getIDCardNo())){
			processError(ApiResponseCode.ID_CARD_ERROR);
		}
		if(StringUtils.isEmpty(contractDetail.getLoanTotalAmount()) || !CheckFormatUtils.checkRMBCurrency(contractDetail.getLoanTotalAmount())){
			processError(ApiResponseCode.LOAN_TOTAL_AMOUNT_ERROR);
		}
		if(contractDetail.getLoanPeriods() <= 0 ){
			processError(ApiResponseCode.LOAN_PERIODS_ERROR);
		}
		if(StringUtils.isEmpty(contractDetail.getLoanContractNo()) || StringUtils.isEmpty(contractDetail.getUniqueId()) ){
			processError(ApiResponseCode.LOAN_CONTRACT_NO_OR_UNIQUEID_IS_EMPTY);
		}
		if(StringUtils.isEmpty(contractDetail.getBankCode()) ){
			processError(ApiResponseCode.NO_MATCH_BANK);
		}
		if(StringUtils.isEmpty(contractDetail.getBankOfTheProvince()) ){
			processError(ApiResponseCode.NO_MATCH_PROVINCE);
		}
		if(StringUtils.isEmpty(contractDetail.getBankOfTheCity()) ){
			processError(ApiResponseCode.NO_MATCH_CITY);
		}
		if(StringUtils.isEmpty(contractDetail.getRepaymentAccountNo()) ){
			processError(ApiResponseCode.REPAYMENT_ACCOUNT_ERROR);
		}
		if(StringUtils.isEmpty(contractDetail.getEffectDate()) || DateUtils.asDay(contractDetail.getEffectDate()) == null){
			processError(ApiResponseCode.EFFECT_DATE_ERROR);
		}
		if(StringUtils.isEmpty(contractDetail.getExpiryDate()) && DateUtils.asDay(contractDetail.getExpiryDate()) == null){
			processError(ApiResponseCode.EXPIRE_DATE_ERROR);
		}
		if(StringUtils.isEmpty(contractDetail.getLoanRates()) || !CheckFormatUtils.checkRMBCurrency(contractDetail.getLoanRates())){
			processError(ApiResponseCode.LOAN_RATES_ERROR);
		}
		if(contractDetail.getPenalty() != null && !CheckFormatUtils.checkRMBCurrency(contractDetail.getPenalty())){
			processError(ApiResponseCode.PENALTY_ERROR);
		}
		if(RepaymentWay.fromValue(contractDetail.getRepaymentWay()) == null){
			processError(ApiResponseCode.NO_MATCH_REPAYMENT_WAY);
		}
	}

	private void validateRepaymentPlanData(
			ImportRepaymentPlanDetail repaymentPlanDetail) {
		if(StringUtils.isEmpty(repaymentPlanDetail.getRepaymentDate()) || DateUtils.asDay(repaymentPlanDetail.getRepaymentDate()) == null){
			processError(ApiResponseCode.REPAYMENT_PLAN_REPAYMENT_DATE_ERROR);
		}
		if(!CheckFormatUtils.checkRMBCurrency(repaymentPlanDetail.getRepaymentPrincipal())){
			processError(ApiResponseCode.REPAYMENT_PRINCIPAL_ERROR);
		}
		if(!CheckFormatUtils.checkRMBCurrency(repaymentPlanDetail.getRepaymentInterest())){
			processError(ApiResponseCode.REPAYMENT_INTEREST_ERROR);
		}
		if(!CheckFormatUtils.checkRMBCurrency(repaymentPlanDetail.getTechMaintenanceFee())){
			processError(ApiResponseCode.TECH_MAINTENANCE_FEE_ERROR);
		}
		if(!CheckFormatUtils.checkRMBCurrency(repaymentPlanDetail.getLoanServiceFee())){
			processError(ApiResponseCode.LOAN_SERVICE_FEE_ERROR);
		}
		if(!CheckFormatUtils.checkRMBCurrency(repaymentPlanDetail.getOtheFee())){
			processError(ApiResponseCode.OTHER_FEE_ERROR);
		}
	}
	
	private void processError(int code) {
		throw new ApiException(code);
	}
}
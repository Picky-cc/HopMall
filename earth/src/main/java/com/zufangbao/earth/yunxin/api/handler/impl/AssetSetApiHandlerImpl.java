package com.zufangbao.earth.yunxin.api.handler.impl;

import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.ASSET_RECYCLE_DATE_TOO_EARLY;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.ASSET_RECYCLE_DATE_TOO_LATE;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.EXPIRE_UNCLEAR_ASSETSET_EXISTED;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.EXSIT_PROCESSING_OR_SUCCESS_REPAYMENT_PLAN;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.FAIL_TO_MODIFY;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.FINANCIAL_CONTRACT_NOT_EXIST;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.INVALID_PRINCIPAL_AMOUNT;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.NO_AVAILABLE_ASSET_SET;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.PREPAYMENT_AMOUNT_INVALID;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.PREPAYMENT_ASSETSET_EXSITED;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.REQUEST_FREQUENT;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.SYSTEM_BUSY;
import static com.zufangbao.earth.yunxin.api.exception.ApiResponseCode.WRONG_PREPAYMENT_DATE;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zufangbao.earth.yunxin.api.exception.ApiException;
import com.zufangbao.earth.yunxin.api.exception.ApiResponseCode;
import com.zufangbao.earth.yunxin.api.handler.AssetSetApiHandler;
import com.zufangbao.earth.yunxin.api.model.modify.RepaymentPlanModifyModel;
import com.zufangbao.earth.yunxin.api.model.modify.RepaymentPlanModifyRequestDataModel;
import com.zufangbao.earth.yunxin.api.model.modify.UpdateAssetLog;
import com.zufangbao.earth.yunxin.api.service.PrepaymentApplicationService;
import com.zufangbao.earth.yunxin.api.service.UpdateAssetLogService;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.contract.RepaymentPlanOperateLog;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.ledgerbook.ChartOfAccount;
import com.zufangbao.sun.service.ContractService;
import com.zufangbao.sun.service.FinancialContractService;
import com.zufangbao.sun.utils.DateUtils;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetSetActiveStatus;
import com.zufangbao.sun.yunxin.entity.RepaymentPlanType;
import com.zufangbao.sun.yunxin.entity.api.PrepaymentApplication;
import com.zufangbao.sun.yunxin.entity.model.api.PrepaymentModifyModel;
import com.zufangbao.sun.yunxin.handler.ContractHandler;
import com.zufangbao.sun.yunxin.handler.RepaymentPlanHandler;
import com.zufangbao.sun.yunxin.handler.impl.RepaymentPlanHandlerImpl;
import com.zufangbao.sun.yunxin.service.RepaymentPlanExtraChargeService;
import com.zufangbao.sun.yunxin.service.RepaymentPlanService;

@Component("assetSetApiHandler")
public class AssetSetApiHandlerImpl implements AssetSetApiHandler {

	@Autowired
	private ContractService contractService;
	@Autowired
	private ContractHandler contractHandler;
	@Autowired
	private RepaymentPlanService repaymentPlanService;
	@Autowired
	private UpdateAssetLogService updateAssetLogService;
	@Autowired
	private PrepaymentApplicationService prepaymentApplicationService;
	@Autowired
	private RepaymentPlanHandler repaymentPlanHandler;
	@Autowired
	private FinancialContractService financialContractService;
	@Autowired
	private RepaymentPlanExtraChargeService  repaymentPlanExtraChargeService;
	
	private static final Log logger = LogFactory.getLog(AssetSetApiHandlerImpl.class);
	
	private List<AssetSet> modify_repaymentPlan(Contract contract, RepaymentPlanModifyModel modifyModel, String ip, List<AssetSet> canUpdateAssetList, boolean unusualModifyFlag, Integer oldActiveVersionNo) {
		updateAssetLogService.checkByRequestNo(modifyModel.getRequestNo());
		
		isCanUpdateAssetListEmpty(canUpdateAssetList);//判空
		
		modifyModelValidator(contract, modifyModel, unusualModifyFlag);//校验变更结束日，剩余未还本金
		
		int newVersion=-1;
		try {
			newVersion=repaymentPlanHandler.updateContractActiveVersionNo(contract, oldActiveVersionNo);//更新贷款最新合同版本号
			contract.setActiveVersionNo(newVersion);
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof RuntimeException && RepaymentPlanHandlerImpl.REQUEST_FREQUENT_ERROR.equals(e.getMessage())) {
				throw new ApiException(REQUEST_FREQUENT);
			}
		}
		
		int currentPeriod = getCurrentPeriod(canUpdateAssetList);
		List<AssetSet> open = saveRequestAssetList(modifyModel, contract, currentPeriod,newVersion);//新增
		
		repaymentPlanService.invalidateAssets(canUpdateAssetList);//作废
		
		//添加log
		contractHandler.addRepaymentPlanOperateLog(contract, RepaymentPlanOperateLog.MODIFY_REPAYMENT_PLAN, open, canUpdateAssetList, null, ip, null);
		saveUpdateAssetLog(contract, modifyModel, "", ip);
		
		return open;
	}

	/**
	 * 获取还款计划最新的开始期数
	 * @param canUpdateAssetList
	 * @return
	 */
	private int getCurrentPeriod(List<AssetSet> canUpdateAssetList) {
		int currentPeriod = canUpdateAssetList.get(0).getCurrentPeriod();
		return currentPeriod;
	}

	private void isCanUpdateAssetListEmpty(List<AssetSet> canUpdateAssetList) {
		if (CollectionUtils.isEmpty(canUpdateAssetList)) {
			throw new ApiException(FAIL_TO_MODIFY);
		}
	}

	private void modifyModelValidator(Contract contract, RepaymentPlanModifyModel modifyModel, boolean unusualModifyFlag) {
		checkPlanBeginDateEndDate(contract, modifyModel);
		checkPrincipalAndDate(contract, modifyModel, unusualModifyFlag);
	}

	/**
	 * 校验剩余本金及时间
	 * @param contract
	 * @param modifyModel
	 * @param unusualModifyFlag
	 */
	private void checkPrincipalAndDate(Contract contract, RepaymentPlanModifyModel modifyModel, boolean unusualModifyFlag) {
		BigDecimal planPrincipalAmount = modifyModel.getPlanPrincipalAmount();
		Date modifyStartDate = DateUtils.addDays(DateUtils.getToday(), BigDecimal.ONE.intValue());
		if(unusualModifyFlag) {//“变更当日”，权限已开通
			if(modifyModel.hasModifyTodayPlan()) {//是否存在需要变更当日的还款计划
				modifyStartDate = DateUtils.getToday();
			}
		}
		BigDecimal principal_amount_of_outstanding = repaymentPlanService.get_the_outstanding_principal_amount_of_contract(contract, modifyStartDate);
		if (planPrincipalAmount.compareTo(principal_amount_of_outstanding) != 0) {
			throw new ApiException(INVALID_PRINCIPAL_AMOUNT);
		}
		checkRequsetDate(modifyModel, modifyStartDate);
	}

	private void checkRequsetDate(RepaymentPlanModifyModel modifyModel, Date modifyStartDate) {
		Date startDate = modifyStartDate;
		for (RepaymentPlanModifyRequestDataModel assetSetUpdateModel : modifyModel.getRequestDataModel()) {
			Date assetRecycleDate = assetSetUpdateModel.getDate();
			if (startDate.after(assetRecycleDate)) {
				throw new ApiException(ApiResponseCode.WRONG_ASSET_RECYCLE_DATE);
			}
			startDate = assetRecycleDate;
		}
	}

	/**
	 * 校验变更结束日期
	 * @param contract
	 * @param modifyModel
	 */
	private void checkPlanBeginDateEndDate(Contract contract, RepaymentPlanModifyModel modifyModel) {
		Date contractBeginDate = contract.getBeginDate();
		Date planBeginDate = modifyModel.getPlanBeginDate();
		if(planBeginDate.before(contractBeginDate)) {
			throw new ApiException(ASSET_RECYCLE_DATE_TOO_EARLY);
		}
		Date contractEndDate = contract.getEndDate();
		Date planEndDate = modifyModel.getPlanEndDate();
		if(planEndDate.after(contractEndDate)) {
			throw new ApiException(ASSET_RECYCLE_DATE_TOO_LATE);
		}
	}

	
	private void saveUpdateAssetLog(Contract contract, RepaymentPlanModifyModel modifyModel, String originData, String ip) {
		UpdateAssetLog updateAssetLog = new UpdateAssetLog(contract, modifyModel, originData, ip);
		updateAssetLogService.save(updateAssetLog);
	}

	private List<AssetSet> saveRequestAssetList(RepaymentPlanModifyModel modifyModel, Contract contract, int startPeriod,int newVersion) {
		List<RepaymentPlanModifyRequestDataModel> requestDataList = modifyModel.getRequestDataModel();
		int period = startPeriod;
		List<AssetSet> assetSets = new ArrayList<AssetSet>();
		
		for (RepaymentPlanModifyRequestDataModel model : requestDataList) {
			Date assetRecycleDate = model.getDate();
			BigDecimal assetPrincipalValue = model.getAssetPrincipal();
			BigDecimal assetInterestValue = model.getAssetInterest();
			BigDecimal theAdditionalFee  = model.getTheAdditionalFee();
			AssetSet assetSet = new AssetSet(contract, period, assetRecycleDate, assetPrincipalValue, assetInterestValue,theAdditionalFee);
			if(model.getAssetType() !=null && model.getAssetType() == 0 ) {
				assetSet.setRepaymentPlanType(RepaymentPlanType.PREPAYMENT);// 提前还款显示标志。
			}
			assetSet.setVersionNo(newVersion);
			repaymentPlanService.save(assetSet);
			saveRepaymentPlanExtraCharge(model, assetSet);
			assetSets.add(assetSet);
			period++;
		}
		int waitModifyListSize = requestDataList.size();
		updateContractPeriod(contract, startPeriod, waitModifyListSize);
		return assetSets;
	}

	/**
	 * 新增 服务费,技术维护费，其它费用
	 * @param model
	 * @param assetSet
	 */
	private void saveRepaymentPlanExtraCharge(RepaymentPlanModifyRequestDataModel model, AssetSet assetSet) {
		repaymentPlanExtraChargeService.createAssetSetExtraCharge(assetSet,model.getOtherCharge(),ChartOfAccount.SND_UNEARNED_LOAN_ASSET_OTHER_FEE);
		repaymentPlanExtraChargeService.createAssetSetExtraCharge(assetSet,model.getServiceCharge(),ChartOfAccount.SND_UNEARNED_LOAN_ASSET_LOAN_SERVICE_FEE);
		repaymentPlanExtraChargeService.createAssetSetExtraCharge(assetSet,model.getMaintenanceCharge(),ChartOfAccount.SND_UNEARNED_LOAN_ASSET_TECH_FEE);
	}

	private void updateContractPeriod(Contract contract, int startPeriod, int waitModifyListSize) {
		int contractPeriod = startPeriod + waitModifyListSize - 1;
		contract.setPeriods(contractPeriod);
		contractService.update(contract);
	}

	@Override
	public List<AssetSet> prepayment(Contract contract, PrepaymentModifyModel model, String ipAddress) {
		//TODO 暂不支持部分提前还款
		//获取当前有效还款计划
		PrepaymentApplication prepaymentApplication = new PrepaymentApplication(contract.getId(), model, ipAddress);
		List<AssetSet> assetList = getAvailableAssetSetList(contract, model);
		AssetSet assetSet = savePrepaymentPlan(model, contract, assetList, prepaymentApplication, ipAddress);
		return Arrays.asList(assetSet);
	}

	private AssetSet savePrepaymentPlan(PrepaymentModifyModel model, Contract contract, List<AssetSet> assetList, PrepaymentApplication prepaymentApplication, String ipAddress) {
		int currentPeriod = getCurrentPeriod(assetList);
		BigDecimal interest = assetList.get(0).getAssetInterestValue();
		Date assetRecycleDate = DateUtils.asDay(model.getAssetRecycleDate());
		BigDecimal principal = get_remaining_asset_principal_amount(assetList);
		
		//生成提前还款计划
		AssetSet assetSet = new AssetSet(contract, currentPeriod, assetRecycleDate, principal, interest,BigDecimal.ZERO);
		assetSet.setRepaymentPlanType(RepaymentPlanType.PREPAYMENT);
		assetSet.setActiveStatus(AssetSetActiveStatus.PREPAYMENT_WAIT_FOR_PROCESSING);
		Long assetSetId = (Long) repaymentPlanService.save(assetSet);
		
		//提前还款申请单中，记录还款计划编号
		prepaymentApplication.setAssetSetId(assetSetId, assetSet.getAssetUuid());
		prepaymentApplicationService.saveOrUpdate(prepaymentApplication);
		
		//全部提前还款，添加还款计划操作日志
		List<AssetSet> prepaymentList = new ArrayList<AssetSet>();
		prepaymentList.add(assetSet);
		contractHandler.addRepaymentPlanOperateLog(contract, RepaymentPlanOperateLog.APPLY_PREPAYMENT_ALL, null, null, prepaymentList, ipAddress, null);

		//如果需传assetSetList，则传list
		return assetSet;
	}

	private List<AssetSet> getAvailableAssetSetList(Contract contract, PrepaymentModifyModel model) {
		validate_not_prepayment_asset_list(contract);
		List<AssetSet> assetList = repaymentPlanService.get_all_unclear_and_open_asset_set_list(contract);
		
		if(CollectionUtils.isEmpty(assetList)) {
			throw new ApiException(NO_AVAILABLE_ASSET_SET);
		}
		validate_expire_unclear_asset_existed(assetList);
		validate_prepayment_amount(model.getAssetInitialValueBD(), assetList);
		validate_prepayment_date(assetList, model.getAssetRecycleDateValue());
		return assetList;
	}

	/**
	 * 检查是否存在到期未还还款计划
	 * @param assetList
	 */
	private void validate_expire_unclear_asset_existed(List<AssetSet> assetList) {
		Date today = DateUtils.getToday();
		for (AssetSet assetSet : assetList) {
			Date assetRecycleDate = assetSet.getAssetRecycleDate();
			if(today.compareTo(assetRecycleDate) >= 0) {
				throw new ApiException(EXPIRE_UNCLEAR_ASSETSET_EXISTED);
			}
		}
	}

	/**
	 * 校验提前还款日期（提前还款日期要早于最近一次还款日期）
	 * @param assetList
	 * @param assetRecycleDateValue
	 */
	private void validate_prepayment_date(List<AssetSet> assetList, Date assetRecycleDateValue) {
		Date recentRecycleDate = assetList.get(0).getAssetRecycleDate();
		if(!assetRecycleDateValue.before(recentRecycleDate)) {
			throw new ApiException(WRONG_PREPAYMENT_DATE);
		}
	}

	/**
	 * 提前还款金额校验
	 * @param prepaymentAmount
	 * @param assetList
	 */
	private void validate_prepayment_amount(BigDecimal prepaymentAmount, List<AssetSet> assetList) {
		BigDecimal interest = assetList.get(0).getAssetInterestValue();
		BigDecimal principalAmount = get_remaining_asset_principal_amount(assetList);
		BigDecimal amount = interest.add(principalAmount);
		if(amount.compareTo(prepaymentAmount) != 0) {
			throw new ApiException(PREPAYMENT_AMOUNT_INVALID);
		}
	}

	private BigDecimal get_remaining_asset_principal_amount(List<AssetSet> assetList) {
		BigDecimal principalAmount = BigDecimal.ZERO;
		for (AssetSet assetSet : assetList) {
			BigDecimal principal = assetSet.getAssetPrincipalValue();
			principalAmount = principalAmount.add(principal);
		}
		return principalAmount;
	}

	/**
	 * 检查是否存在未执行的提前还款
	 * @param assetList
	 */
	private void validate_not_prepayment_asset_list(Contract contract) {
		List<AssetSet> assetSets = repaymentPlanHandler.getPrepaymentWaitForProcessingRepaymentPlansByContract(contract);
		if(CollectionUtils.isNotEmpty(assetSets)) {
			throw new ApiException(PREPAYMENT_ASSETSET_EXSITED);
		}
	}

	@Override
	public List<AssetSet> modify_repaymentPlan(Contract contract, RepaymentPlanModifyModel modifyModel, String ip, Integer oldActiveVersionNo) throws ApiException {
		FinancialContract financialContract = financialContractService.getFinancialContractBy(contract.getFinancialContractUuid());
		if(financialContract == null){
			throw new ApiException(FINANCIAL_CONTRACT_NOT_EXIST);
		}
		
		boolean unusualModifyFlag = financialContract.isUnusualModifyFlag();
		boolean isIncludeToday = unusualModifyFlag && modifyModel.hasModifyTodayPlan();//允许变更当日，且存在变更当日的。
		List<AssetSet> canUpdateAssetList = repaymentPlanService.get_unclear_asset_set_list(contract, isIncludeToday);
		
		try {
			boolean exsit_today_processing_or_success_plan = repaymentPlanHandler.is_exsit_processing_or_success_RepaymentPlan(canUpdateAssetList);
			if(exsit_today_processing_or_success_plan) {
				throw new ApiException(EXSIT_PROCESSING_OR_SUCCESS_REPAYMENT_PLAN);
			}
		} catch (IOException e) {
			logger.error("请求BridgeWater查询还款计划是否提交扣款请求接口异常，错误消息：" + e.getMessage(), e);
			throw new ApiException(SYSTEM_BUSY);
		}
		return modify_repaymentPlan(contract, modifyModel, ip, canUpdateAssetList, unusualModifyFlag, oldActiveVersionNo);
	}


}

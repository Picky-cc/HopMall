package com.zufangbao.sun.yunxin.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.demo2do.core.service.GenericService;
import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.entity.financial.FinancialContract;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetSetActiveStatus;
import com.zufangbao.sun.yunxin.entity.AuditOverdueStatus;
import com.zufangbao.sun.yunxin.entity.PaymentStatus;
import com.zufangbao.sun.yunxin.entity.model.assetset.AssetSetQueryModel;
import com.zufangbao.yunxin.entity.api.syncdata.model.DataSyncStatus;

/**
 * 还款计划表(AssetSet)，CRUD
 * @author zhanghongbing
 *
 */
public interface RepaymentPlanService extends GenericService<AssetSet>{

	/**
	 * 通过还款计划有效状态，查询还款计划列表
	 * @param contract 贷款合同
	 * @param activeStatus 还款计划有效状态
	 * @return 还款计划列表
	 */
	public List<AssetSet> getRepaymentPlansByContractAndActiveStatus(Contract contract, AssetSetActiveStatus activeStatus);
	
	public Date getMaxAssetRecycleDateInActiveAsset(Contract contract);
	
	public List<Long> get_all_receivable_unclear_asset_set_list(Date date);
	
	public List<AssetSet> loadAllNeedGuaranteeAssetSetList();
	
	public List<AssetSet> loadAllNeedSettlementAssetSetList();
	
	public Integer getMaxVersionNo(Contract contract);
	
	public void invalidateAssets(List<AssetSet> assetSetList);

	//已修正activeStatus
	public List<AssetSet> queryAssetSetIdsByQueryModel(AssetSetQueryModel assetSetQueryModel, Page page);

	public void updateSettlementStatusAndSave(AssetSet assetSet);

	public List<AssetSet> get_all_needs_remind_assetSet_list(int remindDay);

	/**
	 * 获取超过还款宽限日的未还还款计划
	 */
	public List<AssetSet> getOverGracePeriodNoRepayAssetSets(Contract contract, FinancialContract financialContract, Date currentDate);

	/**
	 * 贷款规模管理-计算 期初已还本金
	 * @param financialContract 信托计划
	 * @param startDate 期初时间（不包含）
	 * @return 期初已还本金
	 */
	public BigDecimal calculateBeginningPaid(FinancialContract financialContract, Date startDate);
	
	/**
	 * 贷款规模管理-计算 本期减少本金总额
	 * @param financialContract	信托计划
	 * @param startDate 查询日首日（包含）
	 * @param endDate	查询日尾日（不包含）
	 * @return 本期减少本金总额
	 */
	public BigDecimal calculateReduceLoansPrincipal(FinancialContract financialContract, Date startDate, Date endDate);

	/**
	 * 应收利息管理-计算 期初对应的 利息总额
	 * @param financialContract
	 * @param startDate
	 * @return
	 */
	public BigDecimal calculateBeginningAmountInterest(FinancialContract financialContract, Date startDate);

	/**
	 * 应收利息管理-计算 期初对应的 已还利息金额
	 * 
	 * @param financialContract 信托合同
	 * @param startDate 期初时间（不包含）
	 * @return
	 */
	public BigDecimal calculateBeginningPaidInterest(FinancialContract financialContract, Date startDate);

	/**
	 * 应收利息管理-计算 期初应收利息
	 * @param financialContract 信托合同
	 * @param startDate 期初时间（不包含）
	 * @return 期初利息总额 - 期初已还利息总额
	 */
	public BigDecimal calculateBeginningInterest(FinancialContract financialContract, Date startDate);

	/**
	 * 应收利息管理-计算 新增 应收利息
	 * @param financialContract
	 * @param startDate 查询日首日（包含）
	 * @param endDate	查询日尾日（不包含）
	 * @return
	 */
	public BigDecimal calculateNewInterest(FinancialContract financialContract, Date startDate, Date endDate);

	/**
	 * 应收利息管理-计算 本期已还利息总额
	 * @param financialContract	信托计划
	 * @param startDate 查询日首日（包含）
	 * @param endDate	查询日尾日（不包含）
	 * @return 本期已还利息总额
	 */
	public BigDecimal calculateReduceInterest(FinancialContract financialContract, Date startDate, Date endDate);

	public int countAssets(List<String> financialContractUuids,AssetSetActiveStatus activeStatus,List<PaymentStatus> paymentStatusList, AuditOverdueStatus auditOverdueStatus);
	
	public List<AssetSet> get_all_unclear_and_open_asset_set_list(Contract contract);
	
	/**
	 * 提前还款成功的还款计划uuid
	 * @return uuids
	 */
	public List<String> getPrepaymentSuccessRepaymentPlanUuids();
	
	/**
	 * 通过uuid获取唯一的还款计划
	 * @param repaymentPlanUuid 还款计划uuid
	 * @return 还款计划
	 */
	public AssetSet getUniqueRepaymentPlanByUuid(String repaymentPlanUuid);
	
	/**
	 * 获取提前还款还款计划（超过提前还款日&&未结清）
	 * @return
	 */
	public List<AssetSet> getExpiredUnclearPrepaymentRepaymentPlanList();

	public AssetSet getRepaymentPlanByRepaymentCode(String repaymentPlanCode);
	public AssetSet getRepaymentPlanByRepaymentPlanNo(String repaymentPlanNo);
	
	public AssetSet getFirstUnClearAsset(Contract contract);

	/**
	 * 查询某一日期及以后的剩余未还本金总额
	 * @param contract
	 * @param calculateStartDate 含
	 * @return
	 */
	public BigDecimal get_the_outstanding_principal_amount_of_contract(Contract contract, Date calculateStartDate);

	/**
	 * 获取所以截至当日未还（含当日）的还款计划
	 * @param contract
	 * @param isIncludeToday TODO
	 * @return
	 */
	public List<AssetSet> get_unclear_asset_set_list(Contract contract, boolean isIncludeToday);

	public boolean is_repaymentPlan_belong_contract(List<String> repaymentPlanNoArray, Contract contract);

	public  AssetSet getRepaymentPlanByRepaymentCodeNotInVaild(String repaymentPlanCode);

	public  List<String> getNotPaidOffAndHasGuaranteeRepaymentPlans();

	public  List<String> getPaidOffAndNoSyncRepaymentPlans();

	int countAssetSetByQueryModel(AssetSetQueryModel assetSetQueryModel);

	public  AssetSet getLastPeriodInTheContract(Contract contract);

	public void updateAssetSetSyncStatusWhenIsPaidOff(String assetUuid, DataSyncStatus syncStatus);

	public BigDecimal get_principal_interest_and_extra_amount(AssetSet repaymentPlan);
	
	
}

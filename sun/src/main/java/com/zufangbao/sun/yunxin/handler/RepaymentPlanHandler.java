package com.zufangbao.sun.yunxin.handler;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.demo2do.core.web.resolver.Page;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AuditOverdueStatus;
import com.zufangbao.sun.yunxin.entity.excel.OverDueRepaymentDetailExcelVO;
import com.zufangbao.sun.yunxin.entity.excel.RepaymentPlanDetailExcelVO;
import com.zufangbao.sun.yunxin.entity.model.assetset.AssetSetQueryModel;
import com.zufangbao.sun.yunxin.entity.model.assetset.AssetSetShowModel;

/**
 * 还款计划处理程序
 * @author zhanghongbing
 *
 */
public interface RepaymentPlanHandler {

	/**
	 * 获取有效的还款计划列表
	 * @param contract 贷款合同
	 * @return 还款计划列表
	 */
	public List<AssetSet> getEffectiveRepaymentPlansByContract(Contract contract);
	
	/**
	 * 获取提前还款待处理的还款计划列表
	 * @param contract 贷款合同
	 * @return 还款计划列表
	 */
	public List<AssetSet> getPrepaymentWaitForProcessingRepaymentPlansByContract(Contract contract);
	
	public int updateContractActiveVersionNo(Contract contract, Integer oldActiveVersionNo);
	
	/**
	 * 根据资产，创建结算单
	 * @param assetSet 资产
	 * @param executeDate 执行日期
	 */
	public void createOrderByAssetSet(AssetSet assetSet,
			Date executeDate);

	/**
	 * 获取可以生成结算单的还款计划 条件: 1、应收，未清资产 2、当日无结算单 3、最近1日的结算单不存在处理中的扣款单
	 * @param date 评估日
	 * @return 符合条件的还款计划列表
	 */
	public List<Long> get_all_allow_valuation_and_create_normal_order_asset_set_list(
			Date date);

	public List<Serializable> get_all_needs_remind_assetSet_id_list(
			int remindDay);

	public  List<RepaymentPlanDetailExcelVO> get_repayment_plan_Detail_excel(AssetSetQueryModel assetSetQueryModel);

	public List<OverDueRepaymentDetailExcelVO> get_overdue_than_over_due_start_day_repayment_detail_excel(AssetSetQueryModel assetSetQueryModel);
		
	public void updateOverdueStatusAndSaveLog(Long assetId, Long userId, AuditOverdueStatus status, String reason, String overdueDate, String ip);

	public void updateCommentAndSaveLog(Long assetId, Long userId, String comment, String ip);

	public List<AssetSet> queryAssetSetIds(AssetSetQueryModel assetSetQueryModel, Page page);

	public List<AssetSetShowModel> generateAssetSetModelList(AssetSetQueryModel assetSetQueryModel, Page page);
	
	/**
	 * 获取提前还款失败的还款计划uuids
	 * @return uuids
	 */
	public List<String> getPrepaymentFailRepaymentPlanUuids();

	/**
	 * 是否存在当日处理中或者已完成的还款计划
	 * @param assetList TODO
	 * @return
	 * @throws IOException 
	 */
	public boolean is_exsit_processing_or_success_RepaymentPlan(List<AssetSet> assetList) throws IOException;

	int countAssetSetIds(AssetSetQueryModel assetSetQueryModel);
	
}

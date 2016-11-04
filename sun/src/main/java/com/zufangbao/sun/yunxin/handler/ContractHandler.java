package com.zufangbao.sun.yunxin.handler;

import java.util.List;

import com.zufangbao.sun.VO.ProjectInformationSQLReturnData;
import com.zufangbao.sun.entity.contract.Contract;
import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.excel.LoanContractDetailCheckExcelVO;
import com.zufangbao.sun.yunxin.entity.excel.ProjectInformationExeclVo;
import com.zufangbao.sun.yunxin.entity.model.ContractQueryModel;

public interface ContractHandler {
	
	public List<ProjectInformationExeclVo> castProjectInformationShowVOs(List<ProjectInformationSQLReturnData> datas);
	
	public List<LoanContractDetailCheckExcelVO> getLoanContractExcelVO(ContractQueryModel queryModel);
	
	/**
	 * 增加还款计划操作日志
	 * @param contract 贷款合同
	 * @param triggerEvent 触发事件
	 * @param open 开启还款计划列表
	 * @param invalid 作废还款计划列表
	 * @param prepayment 提前还款待处理列表
	 * @param ipAddress ip地址
	 * @param operatorName 操作者名
	 */
	public void addRepaymentPlanOperateLog(Contract contract,
			Integer triggerEvent, List<AssetSet> open, List<AssetSet> invalid,
			List<AssetSet> prepayment, String ipAddress, String operatorName);

	public void invalidateContract(Contract contract, long userId, String operatorName, String comment, String ipAddress);

	public boolean isContainRepaymentPlanInTheContract(String repaymentPlanNo, Contract contract);
}

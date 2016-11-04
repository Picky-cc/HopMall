package com.zufangbao.sun.entity.contract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.zufangbao.sun.yunxin.entity.AssetSet;
import com.zufangbao.sun.yunxin.entity.AssetSetActiveStatus;

/**
 * 还款计划操作日志
 * @author zhanghongbing
 *
 */
public class RepaymentPlanOperateLog {
	
	/** 触发事件 **/
	public static final Integer CONTRACT_IMPORT = 0;
	public static final Integer MODIFY_REPAYMENT_PLAN = 1;
	public static final Integer APPLY_PREPAYMENT_ALL = 2;
	public static final Integer APPLY_PREPAYMENT_PART = 3;
	public static final Integer PREPAYMENT_SUCCESS = 4;
	public static final Integer PREPAYMENT_FAIL = 5;
	public static final Integer INVALIDATE_CONTRACT = PREPAYMENT_FAIL + 1;
	
	public static final String OPERATOR_SYSTEM = "系统";
	
	/**
	 * 触发事件 0:合同导入，1:变更还款计划，2:申请全部提前还款，3:申请部分提前还款，4:提前还款成功，5提前还款失败，6 贷款合同中止
	 */
	private Integer triggerEvent;

	/**
	 * 发生时间
	 */
	private String occurTime;
	
	/**
	 * 操作内容
	 */
	private Map<Integer, String> content = new HashMap<Integer, String>();
	
	/**
	 * 操作者名
	 */
	private String operatorName;
	
	/**
	 * ip地址
	 */
	private String ipAddress;

	public Integer getTriggerEvent() {
		return triggerEvent;
	}

	public void setTriggerEvent(Integer triggerEvent) {
		this.triggerEvent = triggerEvent;
	}

	public String getOccurTime() {
		return occurTime;
	}

	public void setOccurTime(String occurTime) {
		this.occurTime = occurTime;
	}

	public Map<Integer, String> getContent() {
		return content;
	}

	public void setContent(Map<Integer, String> content) {
		this.content = content;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	public RepaymentPlanOperateLog() {
		super();
	}

	public RepaymentPlanOperateLog(Integer triggerEvent, String occurTime,
			List<AssetSet> open, List<AssetSet> invalid,
			List<AssetSet> prepayment, String operatorName, String ipAddress) {
		super();
		this.triggerEvent = triggerEvent;
		this.occurTime = occurTime;
		Map<Integer, String> contentMap = new HashMap<Integer, String>();
		contentMap.put(AssetSetActiveStatus.OPEN.getOrdinal(), this.streamMapAssetSetNos(open));
		contentMap.put(AssetSetActiveStatus.INVALID.getOrdinal(), this.streamMapAssetSetNos(invalid));
		contentMap.put(AssetSetActiveStatus.PREPAYMENT_WAIT_FOR_PROCESSING.getOrdinal(), this.streamMapAssetSetNos(prepayment));
		this.content = contentMap;
		this.operatorName = operatorName;
		this.ipAddress = ipAddress;
	}
	
	private String streamMapAssetSetNos(List<AssetSet> assetSets) {
		if(CollectionUtils.isEmpty(assetSets)){
			return StringUtils.EMPTY;
		}else {
			List<String> assetSetNos = assetSets.stream()
					.map(assetSet -> assetSet.getSingleLoanContractNo())
					.collect(Collectors.toList());
			return StringUtils.join(assetSetNos, ",");
		}
	}
	
}

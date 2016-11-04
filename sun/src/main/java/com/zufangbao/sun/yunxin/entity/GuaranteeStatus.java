package com.zufangbao.sun.yunxin.entity;

import java.util.ArrayList;
import java.util.List;

import com.zufangbao.sun.utils.ApplicationMessageUtils;

/**
 * 差额补足状态（担保状态）
 * 
 * @author louguanyang
 *
 */
public enum GuaranteeStatus {
	/** 未发生 */
	NOT_OCCURRED("enum.guarantee-status.not-occurred"),
	/** 待补足 */
	WAITING_GUARANTEE("enum.guarantee-status.waiting-guarantee"),
	/** 已补足 */
	HAS_GUARANTEE("enum.guarantee-status.has-guarantee"),
	/** 担保作废 */
	LAPSE_GUARANTEE("enum.guarantee-status.lapse-guarantee");
	
	private String key;

	private GuaranteeStatus(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	
	public int getOrdinal(){
		return this.ordinal();
	}
	
	public static GuaranteeStatus fromValue(int value) {
		for (GuaranteeStatus item : GuaranteeStatus.values()) {
			if(item.ordinal() == value) {
				return item;
			}
		}
		return null;
	}
	
	public String getChineseMessage() {
		return ApplicationMessageUtils.getChineseMessage(this.getKey());
	}
	
	public static List<GuaranteeStatus> getGuaranteeStatusForGuaranteeOrder(){
		List<GuaranteeStatus> guaranteeStatusForGurateeOrder = new ArrayList<GuaranteeStatus>();
		guaranteeStatusForGurateeOrder.add(GuaranteeStatus.WAITING_GUARANTEE);
		guaranteeStatusForGurateeOrder.add(GuaranteeStatus.HAS_GUARANTEE);
		guaranteeStatusForGurateeOrder.add(GuaranteeStatus.LAPSE_GUARANTEE);
		return guaranteeStatusForGurateeOrder;
	}
	
}

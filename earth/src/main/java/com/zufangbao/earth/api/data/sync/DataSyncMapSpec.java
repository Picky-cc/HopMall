package com.zufangbao.earth.api.data.sync;

import java.util.HashMap;
import java.util.Map;

import com.zufangbao.yunxin.entity.api.syncdata.model.RepayType;

public class DataSyncMapSpec {

	/**
	 * 低到高位组合判定还款类型
	 * 个位：1：已还款 0：未还款
	 * 十位：1：已逾期 0: 未逾期
	 * 百位：1：已担保 0: 未担保
	 * 千位：1：提前全部 2：提前部分 0：非提前还款
	 */
	public final static Map<String, RepayType> RepaymentTypeMap =new HashMap<String, RepayType>(){
		private static final long serialVersionUID = 1998932252096520683L;
		{
			put("0001",RepayType.NORMAL);
			put("0011",RepayType.OVERDUE);
			put("0110",RepayType.GUARANTEE);
			put("0111",RepayType.GUARANTEE_AND_REPAYMENT);
			put("1001",RepayType.PREREPAYMENT_All);
			put("2001",RepayType.PREREPAYMENT_PART);
		}
	};
	
	
}

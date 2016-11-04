package com.zufangbao.earth.yunxin.api.model.command;

import java.util.HashMap;
import java.util.Map;

public class RemittanceConstants {
	
	public static final Map<String, String> REMITTANCE_STRATEGY_MAPPER = new HashMap<String, String>(){
		private static final long serialVersionUID = -1567623245897904650L;
		{
			put("0", "多目标放款策略");
//			put("1", "先放后扣放款策略");
		}
	};
	
}

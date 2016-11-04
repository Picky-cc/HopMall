package com.zufangbao.sun.yunxin.handler;

import java.util.List;

import com.zufangbao.sun.yunxin.entity.model.vo.welcom.RemittanceDataStatistic;
import com.zufangbao.sun.yunxin.entity.model.vo.welcom.RepaymentDataStatistic;

public interface DataStatHandler {
	
	public RepaymentDataStatistic countRepaymentData(List<Long> financialContractIds);
	
	public RemittanceDataStatistic countRemittanceData(List<Long> financialContractIds);
	
}

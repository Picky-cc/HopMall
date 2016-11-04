package com.zufangbao.earth.yunxin.handler.impl;

import java.util.HashMap;
import java.util.Map;

import com.zufangbao.sun.entity.order.Order;
import com.zufangbao.sun.yunxin.log.FinancialContractLog;
import com.zufangbao.sun.yunxin.log.LogFunctionType;

public class LogMapRecordContentSpec {

	public static final Map<LogFunctionType, String> logFunctionTypeMatchRecordContentHeadName = new HashMap<LogFunctionType, String>() {
		private static final long serialVersionUID = 1550498008776487957L;
		{
			put(LogFunctionType.EDITFINANCIALCONTRACT, "编辑信托合同");
			put(LogFunctionType.EDITORDER, "编辑结算单");
			put(LogFunctionType.ADDOFFLINEBILL, "新增线下支付单");
			put(LogFunctionType.ASSETPACKAGEIMPORT, "导入资产包批次");
			put(LogFunctionType.EXPORTREPAYEMNTPLAN, "导出还款计划");
			put(LogFunctionType.DELETELOANBATCH, "删除放宽批次");
			put(LogFunctionType.ACTIVELOANBATCH, "激活放款批次");
			put(LogFunctionType.ADDFINANCIALCONTRACT, "新增信托合约");
			put(LogFunctionType.ONLINEBILLEXPORTCHECKING, "对账单导出支付单");
			put(LogFunctionType.ONLINEBILLEXPORTDAILYRETURNLIST, "每日还款清单导出支付单");
			put(LogFunctionType.GUARANTEEEXPORT, "导出担保单");
			put(LogFunctionType.ADDOFFLINEBILL, "新增线下支付单");
			put(LogFunctionType.GUARANTEELAPSE, "作废担保单");
			put(LogFunctionType.GUARANTEEACTIVE, "激活担保单");
			put(LogFunctionType.JOURNAL_VOUCHER_AUDIT,"对账");
		}

	};
	public static final Map<String, HashMap<String, String>> recordContentHeadNameMatchRecordContent = new HashMap<String, HashMap<String, String>>() {
		private static final long serialVersionUID = 2734018021978827240L;

		{
			put("编辑信托合同",
					(HashMap<String, String>) FinancialContractLog.filedNameCorrespondStringName);
			put("编辑结算单",
					(HashMap<String, String>) Order.filedNameCorrespondStringName);
		}

	};
}

package com.zufangbao.sun.service;

import java.util.Date;
import java.util.List;

import com.demo2do.core.entity.Result;
import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.icbc.business.FlowRecord;

public interface FlowService {

	/**
	 * 查询当日明细
	 * 
	 * @param bankAccount
	 * @return
	 */
	Result getTodayFlowsByAccount(Account account);

	/**
	 * 查询历史明细
	 * 
	 * @param bankAccount
	 * @return
	 */
	Result getHistoryFlowsByAccount(Account account, String startDate,
			String endDate);
	
	List<FlowRecord> getHistoryFlowsByAccountFromDB(Account account, Date startDate,
			Date endDate);
	
	Result getBalanceByAccount(Account account);
}

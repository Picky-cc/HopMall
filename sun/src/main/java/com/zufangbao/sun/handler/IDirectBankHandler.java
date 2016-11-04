package com.zufangbao.sun.handler;

import com.demo2do.core.entity.Result;
import com.zufangbao.sun.entity.account.Account;
import com.zufangbao.sun.entity.company.corp.App;
import com.zufangbao.sun.entity.directbank.USBKey;

public interface IDirectBankHandler {
	
	/**
	 * 扫描银行流水
	 * @param account 扫描账户
	 * @param app 应用
	 * @param usbKey 银企直连配置
	 */
	public void scanBankCashFlow(Account account, App app, USBKey usbKey);
	
	/**
	 * 补刷流水
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @param account 账户
	 * @param app 应用
	 * @return result保存 总记录数：'totalCount'，添加记录数：'recordCount'
	 */
	public Result recordUnsavedCashFlow(String startDate, String endDate, Account account, App app, USBKey usbKey);

	/**
	 * 查询当日银行流水
	 * @param account 查询账户
	 * @param usbKey 银企直连配置
	 * @return result保存 银行流水信息：'flowList'
	 */
	public Result queryTodayCashFlow(Account account, USBKey usbKey);
	
	/**
	 * 查询指定时段银行流水
	 * @param account 查询账户
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @param usbKey 银企直连配置
	 * @return result保存 银行流水信息：'flowList'
	 */
	public Result queryHistoryCashFlow(Account account, String startDate, String endDate, USBKey usbKey);
	
	/**
	 * 查询账户余额
	 * @param account 查询账户
	 * @param usbKey 银企直连配置
	 * @return result保存 账户余额：'balance'
	 */
	public Result queryAccountBalance(Account account, USBKey usbKey);
}

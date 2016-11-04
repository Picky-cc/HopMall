package com.zufangbao.earth.official.account.handler;

import java.math.BigDecimal;

import com.zufangbao.official.account.entity.IOUSummary;

public interface BreakUpReceiptHandler {

	
	public   IOUSummary  GenerateTheIOU( BigDecimal singleBusinessLendingQuotas, BigDecimal singleDayDebitLimit ,BigDecimal totalAmount);	
}

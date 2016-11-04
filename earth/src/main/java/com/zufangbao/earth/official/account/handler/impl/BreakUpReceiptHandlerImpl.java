package com.zufangbao.earth.official.account.handler.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.zufangbao.earth.official.account.handler.BreakUpReceiptHandler;
import com.zufangbao.official.account.entity.IOU;
import com.zufangbao.official.account.entity.IOUSummary;
import com.zufangbao.official.account.entity.IOUSummaryOfOneDay;



@Component("BreakUpReceiptHandler")
public class BreakUpReceiptHandlerImpl  implements BreakUpReceiptHandler {

	@Override
	public IOUSummary GenerateTheIOU(BigDecimal singleBusinessLendingQuotas, BigDecimal singleDayDebitLimit,
			BigDecimal totalAmount) {
		
		IOUSummary iOUSummary = new IOUSummary();
		List<IOU> IOUList = new ArrayList<IOU>();
		if(totalAmount.compareTo(singleDayDebitLimit) == -1){
			BigDecimal remain = totalAmount;
			generateSingleIOU(singleBusinessLendingQuotas, remain);
		}
		
		if(totalAmount.compareTo(singleDayDebitLimit) == 1){
			BigDecimal remain = totalAmount;
			do{
				IOUSummaryOfOneDay iOUSummaryOfOneDay= new IOUSummaryOfOneDay();
				BigDecimal singleDebitAmount = BigDecimal.ZERO;
				if(remain.compareTo(singleDayDebitLimit) ==-1){
					singleDebitAmount = remain;
				}
				singleDebitAmount = singleDayDebitLimit;
				iOUSummaryOfOneDay.setTodayLoanAmount(singleDebitAmount);
				iOUSummaryOfOneDay.setIOUList(generateSingleIOU(singleBusinessLendingQuotas, singleDebitAmount));
			}
			while(remain.compareTo(BigDecimal.ZERO) == 1);
			generateSingleIOU(singleBusinessLendingQuotas, remain);
		}
		
		
		return null;
	}

	private List<IOU> generateSingleIOU(BigDecimal singleBusinessLendingQuotas, BigDecimal remain) {
		List<IOU> IOUList = new ArrayList<IOU>();
		do{
			IOU iou = new IOU();
			BigDecimal singleDebitAmount = BigDecimal.ZERO;
			singleDebitAmount = singleBusinessLendingQuotas;
			if(remain.compareTo(singleBusinessLendingQuotas) ==-1){
				singleDebitAmount = remain;
			}
			iou.setSingleAmount(singleBusinessLendingQuotas);
			IOUList.add(iou);
			remain = remain.subtract(singleDebitAmount);
		}
		while(remain.compareTo(BigDecimal.ZERO) == 1);
		
		return IOUList;
	}
	
	

}

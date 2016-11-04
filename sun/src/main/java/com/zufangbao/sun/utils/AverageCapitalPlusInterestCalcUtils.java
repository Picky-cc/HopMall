package com.zufangbao.sun.utils;

import java.math.BigDecimal;

public class AverageCapitalPlusInterestCalcUtils {

	
	
	public static BigDecimal calcAverageCapitalPlusMonthFee( BigDecimal interestRate,int totalPeriods,BigDecimal totalAmount){
		
		BigDecimal calc =interestRate.add(BigDecimal.ONE)
				.pow(totalPeriods);
		BigDecimal dividend = calc.multiply(interestRate)
				.multiply(totalAmount);
		BigDecimal divisor = calc.subtract(BigDecimal.ONE);
		return dividend.divide(divisor, 2, BigDecimal.ROUND_HALF_UP);
	}
	
	public  static BigDecimal calAssetPrincipalVaule( BigDecimal interestRate,BigDecimal totalAmount,int totalPeriods, int currentPeriod) {
		BigDecimal calc = interestRate.add(BigDecimal.ONE)
				.pow(currentPeriod - 1);
		BigDecimal dividend = totalAmount
				.multiply(interestRate).multiply(calc);
		BigDecimal divisor = interestRate.add(BigDecimal.ONE)
				.pow(totalPeriods	).subtract(BigDecimal.ONE);
		return dividend.divide(divisor, 2, BigDecimal.ROUND_HALF_UP);
	}
}

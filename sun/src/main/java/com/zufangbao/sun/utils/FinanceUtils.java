package com.zufangbao.sun.utils;

import java.math.BigDecimal;

public class FinanceUtils {

  /**交易返回码*/
  public static final String SUCCESS_RETURN_CODE = "0";
  
  public static final BigDecimal DEFAULT_ZERO = new BigDecimal("0.00");
  
  /***
   * 将元转化成分
   * 
   * @param yuan_num
   * @return
   */
  public static String convert_yuan_to_cent(BigDecimal yuan_num) {
    if (yuan_num==null){
      return "0";
    }
  return  yuan_num.movePointRight(2).toString();
  }


  public static BigDecimal convert_cent_to_yuan(BigDecimal amount) {
    if (amount==null){
      return new BigDecimal("0.00");
    }
    return amount.movePointLeft(2);
  }
  public static BigDecimal convertToBigDecimal(Double value){
	  if(value==null) return  BigDecimal.ZERO; 
	  return new BigDecimal(value);
  }
  public static Double convertToDouble(BigDecimal value){
	  if(value==null) return 0D;
	  return value.doubleValue();
  }
  public static BigDecimal divide(BigDecimal dividend ,BigDecimal divisor){
	  
	  if(null == dividend || null == divisor){
		  
		  return DEFAULT_ZERO;
	  }
	  return dividend.divide(divisor, 2,BigDecimal.ROUND_HALF_UP);
  }
  public static BigDecimal divide(BigDecimal dividend ,int divisor){
	  return divide(dividend, new BigDecimal(divisor));
  }
  public static BigDecimal divide(int dividend ,int divisor){
	  return divide(new BigDecimal(dividend), new BigDecimal(divisor));
  }
  public static BigDecimal divide(BigDecimal dividend ,Double divisor){
	  return divide(dividend, new BigDecimal(divisor));
  }
}

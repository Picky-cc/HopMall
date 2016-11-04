package com.zufangbao.sun.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.demo2do.core.utils.DateUtils;
import com.demo2do.core.utils.StringUtils;

/**
 * 校验类
 * @author lixiaofei
 *
 */
public class ValidatorUtil {
	/**
	 * 必输项
	 */
	public static final String Require = ".+";
	/**
	 * 空内容
	 */
	public static final String Empty = "^$";
	/**
	 * 电子邮件地址
	 */
	public static final String Email = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
	/**
	 * 电话号码
	 */
	public static final String Phone = "^0\\d{2,3}-?[1-9]\\d{5,7}$";
	/**
	 * 电话号码或分机号
	 */
	public static final String PhoneExt = "^(0[0-9]{2,3}-)?([2-9][0-9]{6,7})([*#][0-9]{1,4})?$";
	
	/**
	 * 手机号码
	 */
	public static final String Mobile = "^((13)|(14)|(15)|(17)|(18))\\d{9}$";
	/**
	 * 手机或电话号码
	 */
	public static final String MPhone = "^(((13)|(14)|(15)|(17)|(18))\\d{9})|(0\\d{2,3}-?[1-9]\\d{5,7})$";
	/**
	 * 中国移动
	 */
	public static final String ChinaMobile = "^1((3[4-9])||(47)||(5[0-2])||(5[7-9])||(8[2-4])||(8[7-8]))\\d{8}$";
	/**
	 * 中国联通
	 */
	public static final String ChinaUnicom = "^1((3[0-2])||(45)||(5[5-6])||(8[5-6]))\\d{8}$";
	/**
	 * 中国电信
	 */
	public static final String ChinaTelecom = "^1((33)||(53)||(8[0-1])||(89))\\d{8}$";
	/**
	 * 支付宝账号
	 */
	public static final String AlipayAccount = "(^((13)|(14)|(15)|(17)|(18))\\d{9}$)|(^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$)";
	
	/**
	 * URL地址
	 */
	public static final String Url = "(https?|ftp|mms)://([A-Za-z0-9]+[_-]?[A-z0-9]*\\.)+([A-Za-z0-9]+)(:[0-9]+)?/?.*"; /*'*/
	/**
	 * 金额
	 */
	public static final String Currency = "^\\d+(\\.\\d+)?$";
	/**
	 * 数字 非负数串
	 */
	public static final String Number = "^\\d+$";
	/**
	 * 年
	 */
	public static final String Year = "^\\d{4}$";
	
	/**
	 * 年月
	 */
	public static final String YearMonth = "^([1-9]{1}[0-9]{3})[-/](0?[1-9]|1[0-2])";
	/**
	 * 百分率
	 */
	public static final String Rate = "^[-\\+]?\\d+(\\.\\d+)?(%)?$";
	/**
	 * 邮编
	 */
	public static final String Zip = "^[1-9]\\d{5}$";
	/**
	 * QQ号
	 */
	public static final String QQ = "^[1-9]\\d{4,20}$";
	/**
	 * 整数
	 */
	public static final String Integer = "^[-\\+]?\\d+$";
	/**
	 * 小数
	 */
	public static final String Double = "^[-\\+]?\\d+(\\.\\d+)?$";
	/**
	 * 英文
	 */
	public static final String English = "^[A-Za-z]+$";
	/**
	 * 中文
	 */
	public static final String Chinese = "^[\\u0391-\\uFFE5]+$";
	/**
	 * 公司名称 4~20个字符(私有)
	 */
	public static final String CompanyName = "^([\\u0391-\\uFFE5]|[a-zA-Z0-9]){4,50}$";
	/**
	 * 通用 一般词校验 如:单位名称 部门 职务等
	 */
	public static final String General = "^([\\u0391-\\uFFE5]|\\w)+$"; //一般词校验 如:单位名称 部门 职务等
	/**
	 * 表示自定义的号码 如中介公司的合同号
	 */
	public static final String CustomNo = "^[a-zA-Z0-9._\\-]{3,30}$";
	/**
	 * 通用注册帐号
	 */
	public static final String Account = "^(?i)([a-z]\\w{3,15})$";
	/**
	 * 银行卡号
	 */
	public static final String CardNO = "^[1-9]\\d{8,22}$";
	/**
	 * 手机号或者邮箱
	 */
	public static final String MAccount = "^(\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)|(((13)|(14)|(15)|(17)|(18))\\d{9})$";
	/**
	 * 不做标准校验的帐号，用于中介帐号 并且能用邮箱登录
	 *//*
	public static final String OAccount = "^[0-9a-zA-Z_\\-\\+\\.@]{3,30}$";*/
	/**
	 * 登录的账号  用户名/手机号码/电子邮箱
	 */
	public static final String LoginAccount = "^((?i)([a-z]\\w{3,15}))|(\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)|(((13)|(14)|(15)|(17)|(18))\\d{9})$";
	/**
	 * 姓名
	 */
	public static final String UserName = "^[\\u0391-\\uFFE5]{2,10}$";
	/**
	 * 密码
	 */
	public static final String Password = "^.{6,16}$";
	/**
	 * Rsa加密后的Hex字串
	 */
	public static final String RsaHexString = "^[0-9a-f]{4,2048}$";
	/**
	 * 校验码
	 */
	public static final String ValidCode = "^[0-9a-zA-Z]{4,6}$";
	/**
	 * 不安全项
	 */
	public static final String UnSafe = "^(([A-Z]*|[a-z]*|\\d*|[-_\\~!@#\\$%\\^&\\*\\.\\(\\)\\[\\]\\{\\}<>\\?\\\\\\/\\\'\\\"]*)|.{0,5})$|\\s";  /*'*/
	/**
	 * 信用卡号
	 */
	public static final String CreditCard = "^[1-9]\\d{8,22}$"; //未找到通用规则
	/**
	 * 身份证 正则校验， 如果需要校验其正确性，请用isIdCard()方法校验
	 */
	public static final String IdCard = "^(\\d{15})|(\\d{17}(\\d|a|A|x|X))$";
	
	public static final String IdCard18 = "^(\\d{17}(\\d|x|X))$";
	/**
	 * 判断指定的字符串是否与正则相匹配
	 * @param regex 正则串
	 * @param input 需要判断的字串
	 * @return 如果能匹配，则返回true 否则返回false
	 */
	public static boolean matches(String regex, String input){
		return input != null && Pattern.matches(regex, input);
	}
	/**
	 * 指定串是否是安全串
	 * @param input 输入项
	 * @return
	 */
	public static boolean isSafe(String input){
		return input != null && !matches(UnSafe, input);
	}
	/**
	 * 身份证区域号
	 */
	private static Map<String, String> mapAreaIdCard = null;
	/**
	 * 身份证区域号
	 * @return
	 */
	private static Map<String, String> getMapAreaIdCard(){
		if (mapAreaIdCard == null){
			Map<String, String> mapArea = new HashMap<String, String>();
			mapArea.put("11", "北京"); mapArea.put("12", "天津"); mapArea.put("13", "河北"); mapArea.put("14", "山西"); mapArea.put("15", "内蒙古"); 
	        mapArea.put("21", "辽宁"); mapArea.put("22", "吉林"); mapArea.put("23", "黑龙江"); mapArea.put("31", "上海"); mapArea.put("32", "江苏"); 
	        mapArea.put("33", "浙江"); mapArea.put("34", "安徽"); mapArea.put("35", "福建"); mapArea.put("36", "江西"); mapArea.put("37", "山东"); 
	        mapArea.put("41", "河南"); mapArea.put("42", "湖北"); mapArea.put("43", "湖南"); mapArea.put("44", "广东"); mapArea.put("45", "广西"); 
	        mapArea.put("46", "海南"); mapArea.put("50", "重庆"); mapArea.put("51", "四川"); mapArea.put("52", "贵州"); mapArea.put("53", "云南"); 
	        mapArea.put("54", "西藏"); mapArea.put("61", "陕西"); mapArea.put("62", "甘肃"); mapArea.put("63", "青海"); mapArea.put("64", "宁夏"); 
	        mapArea.put("65", "新疆");
	        mapAreaIdCard = mapArea;
		}
		return mapAreaIdCard;
	}
	/**
	 * 指定串是否是身份证号
	 * @param input 身份证号码
	 * @return
	 */
	public static boolean isIdCard(String input){
		if (input == null || (input.length() != 15 && input.length() != 18))
			return false;
		input = input.toUpperCase(); // 对身份证号码做处理
		Map<String, String> mapArea = getMapAreaIdCard();
		if (!mapArea.containsKey(input.substring(0, 2)))
			return false;
		String strTestReg;
		if(input.length() == 18){
			//出生日期的合法性检查
            //闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
            //平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
			if (!Pattern.matches("^\\d{17}(\\d|X|A)$", input)){
				return false;
			}
            if (new Integer(input.substring(6, 9)) % 4 == 0 || (new Integer(input.substring(6, 9)) % 100 == 0 && new Integer(input.substring(6, 9)) % 4 == 0)) {
            	strTestReg = "^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9XxAa]$"; //闰年出生日期的合法性正则表达式
            } else {
            	strTestReg = "^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9XxAa]$"; //平年出生日期的合法性正则表达式
            }
            if (!Pattern.matches(strTestReg, input)){//测试出生日期的合法性
            	return false;
            }
            //计算校验位
            int totalCheck = (new Integer(String.valueOf(input.charAt(0))) + new Integer(String.valueOf(input.charAt(10)))) * 7
					+ (new Integer(String.valueOf(input.charAt(1))) + new Integer(String.valueOf(input.charAt(11)))) * 9
					+ (new Integer(String.valueOf(input.charAt(2))) + new Integer(String.valueOf(input.charAt(12)))) * 10
					+ (new Integer(String.valueOf(input.charAt(3))) + new Integer(String.valueOf(input.charAt(13)))) * 5
					+ (new Integer(String.valueOf(input.charAt(4))) + new Integer(String.valueOf(input.charAt(14)))) * 8
					+ (new Integer(String.valueOf(input.charAt(5))) + new Integer(String.valueOf(input.charAt(15)))) * 4
					+ (new Integer(String.valueOf(input.charAt(6))) + new Integer(String.valueOf(input.charAt(16)))) * 2
					+ new Integer(String.valueOf(input.charAt(7))) * 1
					+ new Integer(String.valueOf(input.charAt(8))) * 6
					+ new Integer(String.valueOf(input.charAt(9))) * 3;
            int indexCheck= totalCheck % 11;
            final String strCheckCodes = "10X98765432";
            char charCheck = strCheckCodes.charAt(indexCheck); /*判断校验位*/
            if (charCheck == input.charAt(17)) {  /*检测ID的校验位false;*/
            	return true;
            } 
            else if (input.charAt(17) == 'A') {//A结尾不校验规则
                return true; /*检测ID的校验位false;*/
            }
            else {
            	return false;
            }
		}
		return false;
	}
	/**
	 * 输入的数据是否是日期
	 * @param input
	 * @return
	 */
	public static boolean isDate(String input, String pattern){
		if (StringUtils.isEmpty(input))
			return false;
		Date dateValue = DateUtils.parseDate(input, pattern);
		return dateValue != null;
	}
	
	/**
	 * 输入的字符串是否符合类似公司名称这样的验证
	 * @param input
	 * @return
	 */
	public static boolean isCompanyName(String input){
		if (StringUtils.isEmpty(input))
			return false;
		return matches(ValidatorUtil.CompanyName,  input) && matches(".*[\\u0391-\\uFFE5].*",  input);
	}
		
}

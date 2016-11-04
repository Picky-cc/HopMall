/**
 * 
 */
package com.zufangbao.sun.utils;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

/**
 * @author wukai
 *
 */
public class GeneratorUtils {

	private final static int DEFAULT_LENGTH = 16;
	private final static int DEFAULT_PART_1_LENGTH = 5;
	private final static int DEFAULT_PART_2_LENGTH = 7;
//	private final static int DEFAULT_PART_3_LENGTH = 4;
	private final static int MAX_VALUE = 65536;
	private static final String PAD_CHAR = "0";

	/**
	 * 生成16进制的16位订单号。
	 * 
	 * 共三部分 1、yyMMdd 2、HHmmssSSS 3、XXXX 四位随机数
	 * 
	 * @return
	 */
	private synchronized static String generateNo() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		StringBuffer buffer = new StringBuffer("");
		int yyMMdd = Integer.valueOf(DateUtils.format(new Date(), "yyMMdd"));
		String part1 = Integer.toHexString(yyMMdd);
		buffer.append(StringUtils.leftPad(part1, DEFAULT_PART_1_LENGTH, PAD_CHAR));

		int HHmmssSSS = Integer.valueOf(DateUtils.format(new Date(), "HHmmssSSS"));
		String part2 = Integer.toHexString(HHmmssSSS);
		buffer.append(StringUtils.leftPad(part2, DEFAULT_PART_2_LENGTH, PAD_CHAR));

		String part3 = Integer.toHexString((int) (Math.random() * MAX_VALUE));
		final int part3Length = DEFAULT_LENGTH - DEFAULT_PART_1_LENGTH - DEFAULT_PART_2_LENGTH;
		buffer.append(StringUtils.leftPad(part3, part3Length, PAD_CHAR));
		return buffer.toString().toUpperCase();
	}
	
	/**
	 * 银联代扣单号
	 * @return
	 */
	public synchronized static String generateUnionPayNo() {
		return generateNo();
	}
	/**
	 * 导入批次号
	 * @return
	 */
	
	public synchronized static String generateLoanBatchNo(){
		return generateNo();
	}
	/**
	 * 线下支付单号
	 * @return
	 */
	public synchronized static String generateOfflineBillNo() {
		return "XX" + generateNo();
	}

	/**
	 * 担保单号
	 * @return
	 */
	public synchronized static String generateGuaranteeOrderNo() {
		return "DB" + generateNo();
	}
	
	/**
	 * 结算单号
	 * @return
	 */
	public synchronized static String generateJSOrderNo() {
		return "JS" + generateNo();
	}
	
	/**
	 * 资产编号
	 * @return
	 */
	public synchronized static String generateAssetSetNo() {
		return "ZC" + generateNo();
	}
	
	/**
	 * 清算单编号
	 * @return
	 */
	public synchronized static String generateSettlementOrderNo() {
		return "QS" + generateNo();
	}
	
	/**
	 * 接口扣款单编号（interfaceDeductBill）
	 * @return
	 */
	public synchronized static String generateInterfaceDeductBillNo() {
		return "IDB" + generateNo();
	}
	
	/**
	 * 余额账户编号
	 * @return
	 */
	public synchronized static String generateVirtualAccountNo() {
		return "VACC" + generateNo();
	}
	
	/**
	 * 余额账户流水编号
	 * @return
	 */
	public synchronized static String generateVirtualAccountFlowNo() {
		return "LS" + generateNo();
	}
	
	/**
	 * 充值单编号
	 * @return
	 */
	public synchronized static String generateDepositNo() {
		return "CZ" + generateNo();
	}
	
	/**
	 * 扣款单编号
	 * @return
	 */
	public synchronized static String generateDudectPlanVoucherNo() {
		return "KK" + generateNo();
	}
	/**
	 * 支付单编号
	 * @return
	 */
	public synchronized static String generatePaymentNo() {
		return "ZF" + generateNo();
	}
	
	public synchronized static String generateID(){
		
		try {
			
			Thread.currentThread().sleep(1);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return DateUtils.getCurrentTimeMillis();
	}

	public synchronized static String generateUUID() {

		return UUID.randomUUID().toString();
	}

}

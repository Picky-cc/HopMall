package com.zufangbao.sun.yunxin.entity.model.vo.welcom;

import java.util.Date;

public class RepaymentDataStatistic {
	
	private Date countDate;
	/** 还款状态（处理中）逾期状态（正常）的还款单数目 */
	private int processing_payment_status_and_not_overdue_assets_nums;
	/** 还款状态（异常）逾期状态（正常）的还款单数目 */
	private int unusual_payment_status_and_not_overdue_assets_nums;
	
	/** 逾期状态为待确认的资产数目 */
	private int unconfirmed_overdue_assets_nums;
	/** 逾期状态(逾期)还款状态(处理中or异常)的资产数目 */
	private int overdue_and_processing_unusual_payment_status_assets_nums;
	
	/** 待补足的担保单数目 */
	private int waiting_guarantee_orders_nums;
	/** 申请清算的结清单数目 */
	private int settlement_status_create_settlement_orders_nums;
	
	public int getProcessing_payment_status_and_not_overdue_assets_nums() {
		return processing_payment_status_and_not_overdue_assets_nums;
	}
	public void setProcessing_payment_status_and_not_overdue_assets_nums(
			int processing_payment_status_and_not_overdue_assets_nums) {
		this.processing_payment_status_and_not_overdue_assets_nums = processing_payment_status_and_not_overdue_assets_nums;
	}
	public int getUnusual_payment_status_and_not_overdue_assets_nums() {
		return unusual_payment_status_and_not_overdue_assets_nums;
	}
	public void setUnusual_payment_status_and_not_overdue_assets_nums(
			int unusual_payment_status_and_not_overdue_assets_nums) {
		this.unusual_payment_status_and_not_overdue_assets_nums = unusual_payment_status_and_not_overdue_assets_nums;
	}
	public int getUnconfirmed_overdue_assets_nums() {
		return unconfirmed_overdue_assets_nums;
	}
	public void setUnconfirmed_overdue_assets_nums(
			int unconfirmed_overdue_assets_nums) {
		this.unconfirmed_overdue_assets_nums = unconfirmed_overdue_assets_nums;
	}
	public int getOverdue_and_processing_unusual_payment_status_assets_nums() {
		return overdue_and_processing_unusual_payment_status_assets_nums;
	}
	public void setOverdue_and_processing_unusual_payment_status_assets_nums(
			int overdue_and_processing_unusual_payment_status_assets_nums) {
		this.overdue_and_processing_unusual_payment_status_assets_nums = overdue_and_processing_unusual_payment_status_assets_nums;
	}
	public int getWaiting_guarantee_orders_nums() {
		return waiting_guarantee_orders_nums;
	}
	public void setWaiting_guarantee_orders_nums(int waiting_guarantee_orders_nums) {
		this.waiting_guarantee_orders_nums = waiting_guarantee_orders_nums;
	}
	public int getSettlement_status_create_settlement_orders_nums() {
		return settlement_status_create_settlement_orders_nums;
	}
	public void setSettlement_status_create_settlement_orders_nums(
			int settlement_status_create_settlement_orders_nums) {
		this.settlement_status_create_settlement_orders_nums = settlement_status_create_settlement_orders_nums;
	}
	
	public int getNotOverDueAssetsNum(){
		return processing_payment_status_and_not_overdue_assets_nums + unusual_payment_status_and_not_overdue_assets_nums;
	}
	public int getOverDueAssetsNum(){
		return unconfirmed_overdue_assets_nums + overdue_and_processing_unusual_payment_status_assets_nums;
	}
	public int getGuranteeNum(){
		return waiting_guarantee_orders_nums + settlement_status_create_settlement_orders_nums;
	}
	public Date getCountDate() {
		return countDate;
	}
	public void setCountDate(Date countDate) {
		this.countDate = countDate;
	}
	
	public RepaymentDataStatistic(
			int processing_payment_status_and_not_overdue_assets_nums,
			int unusual_payment_status_and_not_overdue_assets_nums,
			int unconfirmed_overdue_assets_nums,
			int overdue_and_processing_unusual_payment_status_assets_nums,
			int waiting_guarantee_orders_nums,
			int settlement_status_create_settlement_orders_nums) {
		super();
		this.processing_payment_status_and_not_overdue_assets_nums = processing_payment_status_and_not_overdue_assets_nums;
		this.unusual_payment_status_and_not_overdue_assets_nums = unusual_payment_status_and_not_overdue_assets_nums;
		this.unconfirmed_overdue_assets_nums = unconfirmed_overdue_assets_nums;
		this.overdue_and_processing_unusual_payment_status_assets_nums = overdue_and_processing_unusual_payment_status_assets_nums;
		this.waiting_guarantee_orders_nums = waiting_guarantee_orders_nums;
		this.settlement_status_create_settlement_orders_nums = settlement_status_create_settlement_orders_nums;
		
		this.countDate = new Date();
	}
	public RepaymentDataStatistic(){
		super();
		this.countDate = new Date();
	}
	
	
}

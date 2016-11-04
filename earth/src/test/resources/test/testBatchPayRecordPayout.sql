SET FOREIGN_KEY_CHECKS=0;

delete from batch_pay_record;
delete from batch_pay_detail;
delete from rent_order;
delete from finance_payment_record;
delete from charge;
delete from factoring_contract;
delete from asset_package;
delete from contract;

insert into `batch_pay_record`(`id`,`amount`,`apply_time`,`creator_id`,`modify_time`,`operator_id`,`pay_status`,`pay_time`,`remark`,`request_no`,`serial_no`,`payer_account_id`,`payment_institution_id`,`receive_account_id`) values
(5,2600.00,'2015-07-09 14:53:56',1,'2015-07-09 14:53:56',NULL,0,NULL,'测试',NULL,NULL,7,2,6);

INSERT INTO `batch_pay_detail` (`id`, `create_time`, `creator_id`, `pay_money`, `pay_time`, `batch_pay_record_id`, `order_id`) VALUES
	(53,'2015-07-09 14:53:56',1,1400.00,NULL,5,1618),
	(54,'2015-07-09 14:53:56',1,1200.00,NULL,5,2453);
	
	
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`,`collection_bill_capital_status`)
VALUES
	(1618,'2015-07-04',NULL,NULL,'CC-61055-3',2,1400.00,NULL,00000000,NULL,1400.00,205,266,1,NULL,NULL,0,NULL,NULL,'2'),
	(2453,'2015-07-10',NULL,NULL,'CC-61158-3',2,1200.00,NULL,00000000,NULL,1200.00,303,363,1,NULL,NULL,0,NULL,NULL,'2');

INSERT INTO `factoring_contract` (`id`, `adva_matuterm`, `adva_pct`, `adva_start_date`, `adva_term`, `ar_amt`, `ar_cost_rate`, `ar_deposit_amt`, `ar_deposit_amt_aval`, `ar_deposit_amt_input_days`, `ar_deposit_amt_input_type`, `ar_ldgamt`, `borrower_name`, `contract_no`, `cost_deduct_type`, `deposit_deduct_type`, `int_accuretype`, `int_rate`, `latest_settle_date`, `lender_ac_bk_name`, `lender_ac_no`, `lender_name`, `otsd_amt`, `otsd_amt_aval`, `settle_pct`, `app_id`, `company_id`, `nominal_rate`, `payment_agreement_id`)
VALUES
	(61,7,0.70,'2015-06-02 21:47:05',0,0.00,0.00,0.00,0.00,0,1,0.00,'寓见','DCF-XY-FZR905A',2,2,2,0.1966003200,NULL,NULL,NULL,'鼎程（上海）商业保理有限公司',0.00,0.00,1.00,1,2,0.0850000000,2);
	
INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`, `transfer_status`)
VALUES
	(205,'2015-05-04','CC-61055',0,2800.00,'2016-05-03',NULL,'',1400.00,00000000,0,NULL,NULL,1,266,208,0),
	(303,'2015-05-10','CC-61158',0,2400.00,'2016-05-09',NULL,'',1200.00,00000001,0,NULL,NULL,1,363,305,0);
	
	
INSERT INTO `asset_package` (`id`, `adva_start_date`, `is_available`, `bank_account`, `bank_name`, `create_time`, `latest_settle_date`, `otsd_amt`, `otsd_amt_aval`, `rec_transfee_total_amt`, `remove_time`, `contract_id`, `factoring_contract_id`)
VALUES
	(170,NULL,00000000,NULL,NULL,'2015-07-01 20:06:29',NULL,9800.00,9800.00,14000.00,NULL,205,61),
	(265,NULL,00000000,NULL,NULL,'2015-07-03 19:19:01',NULL,8400.00,8400.00,12000.00,NULL,303,61);


SET FOREIGN_KEY_CHECKS=1;

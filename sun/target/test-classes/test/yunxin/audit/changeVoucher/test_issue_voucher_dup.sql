SET FOREIGN_KEY_CHECKS=0;

delete from `contract_account`;
delete from `asset_package`;
delete from `rent_order`;
delete from `asset_set`;
delete from `financial_contract`;
delete from `contract`;
delete from `app`;
delete from `customer`;
delete from `contract_account`;
delete from `app_arrive_record`;
delete from `journal_voucher`;
delete from `business_voucher`;

INSERT INTO `contract_account` (`id`, `pay_ac_no`, `contract_id`, `payer_name`, `bank`, `id_card_num`, `bank_code`,  `from_date`, `thru_date`) VALUES 
('1', 'account_1', '1', 'account_name_1', '中国邮政储蓄银行', 'ID_card1', '403', '2016-04-17 00:00:00', '2900-01-01 00:00:00'),
('2', 'account_2', '2', 'account_name_2', '中国邮政储蓄银行', 'ID_card12', '403', '2016-04-17 00:00:00', '2900-01-01 00:00:00');


INSERT INTO `asset_set` (`id`, `guarantee_status`, `asset_fair_value`,`asset_initial_value`, `asset_recycle_date`, `asset_status`, `on_account_status`,  `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `current_period`,`active_status`,`version_no`)
VALUES
	(1, 0, 2984.65,1000, '2016-05-01', 0, 1, '2016-04-06 16:43:47','0d712307-a747-4a57-9fa5-792cf76c1a4c', '2016-04-06 16:43:47', '2016-04-07 21:49:04', 'test-contract-1-1', 1, NULL, 1,0,0),
	(2, 0, 2983.16,1000, '2016-05-01', 0, 1, '2016-04-06 16:43:47','1676780d-15bf-4fe3-968c-62be2b02c3aa', '2016-04-06 16:43:47', NULL, 'test-contract-1-2', 1, NULL, 2,0,0);

INSERT INTO `asset_package` (`id`, `create_time`, `contract_id`, `asset_package_no`, `financial_contract_id`) VALUES 
('1', '2015-10-19 13:34:35', '1','no1','1'),
('2', '2015-10-19 13:34:35', '2','no2','2');

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `addressee`) VALUES 
('1', 'anmeitu', '11111db75ebb24fa0993f4fa25775023', '\0', 'http://e.zufangbao.cn', '安美途', '1', NULL),
('2', 'test4Zufangbao', '2e85ae999845f25faf8ea7b514ee0aca', '\0', 'http://e.zufangbao.cn', '租房宝测试账号', '2', NULL);

INSERT INTO `customer` (`id`, `account`, `mobile`, `name`, `source`, `app_id`) VALUES 
('1', '01091081XXXXX', '13777846666', '王二', 'D001', '1'),
('2', '01091082XXXXX', '13777846666', '王二', 'D002', '2');

INSERT INTO `financial_contract` (`id`, `adva_matuterm`, `contract_no`, `app_id`, `company_id`,   `adva_repo_term`, `loan_overdue_start_day`, `loan_overdue_end_day`) VALUES 
('1', 3, 'YX_AMT_001', 1, 1, 30, 5, 90),
('2', 3, 'YX_AMT_002', 2, 1, 30, 5, 90);

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`,`end_date`,`app_id`, `customer_id`, `house_id`,`actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`,`month_fee`,`active_version_no`) VALUES 
(1, '2015-10-19', 'DKHD-001', '2016-10-19', 1, 1, 1, NULL, '2015-10-19 13:34:35', 0.001, 1, 1, 12, 0, 10000, 0.0001,1000,0),
(2, '2015-10-20', 'DKHD-002', '2016-10-19', 2, 1, 1, NULL, '2015-10-19 13:34:35', 0.001, 1, 1, 12, 0, 10000, 0.0001,1000,0);

INSERT INTO `rent_order` (`id`, `order_type`, `due_date`, `order_no`, `payout_time`, `total_rent`, `customer_id`, `user_upload_param`, `modify_time`, `financial_contract_id`, `repayment_bill_id`, `asset_set_id`, `create_time`, `clearing_status`, `executing_settling_status`, `comment`) VALUES 
('1', '0', '2016-05-16', 'JS27306907162EDEE2', NULL, '1001.00', '1', '[1,2]', '2016-05-18 15:15:28', '2', 'repayment_uuid_1', '1', '2016-05-18 15:14:59', '0', '3', NULL),
('2', '0', '2016-05-17', 'JS273069071637E162', NULL, '1002.00', '2', '[3,4]', '2016-05-18 15:15:28', '2', 'repayment_uuid_2', '1', '2016-05-18 15:14:59', '0', '3', NULL),
('3', '0', '2016-05-18', 'JS273069071637E333', NULL, '1003.00', '2', '[3,4]', '2016-05-18 15:15:28', '2', 'repayment_uuid_3', '1', '2016-05-18 15:14:59', '0', '3', NULL);

INSERT INTO `app_arrive_record` (`id`, `cash_flow_uid`,`amount`, `arrive_record_status`, `pay_ac_no`, `pay_name`,`receive_ac_no`, `remark`, `serial_no`, `time`, `app_id`, `drcrf`,  `summary`, `vouh_no`, `detail_data`, `cash_flow_channel_type`, `transaction_type`, `partner_id`) VALUES 
('1', 'cash_flow_uuid_1','10.00', '0', 'account_1','account_name_1', 'receive_ac_no_1', NULL, 'serial_no_1', '2015-10-10 09:26:56', '1', '2',  '汇宁3号905房租', 'vouh_no_1', NULL, '0', '0', 'partner_id');



INSERT into `journal_voucher` (
	`id`,
	`booking_amount`,
	`account_side`,
	`journal_voucher_uuid`,
	`completeness`,
	`status`,
	`checking_level`,
	
	`cash_flow_uuid`,
	`trade_time`,
	`cash_flow_serial_no`,
	`notification_memo`,
	`notification_identity`,
	`bank_identity`,
	`cash_flow_amount`,
	
	`notification_record_uuid`,
	`notified_date`,
	`source_document_identity`,
	`source_document_cash_flow_serial_no`,
	`source_document_amount`,
	`company_id`,
	
	`business_voucher_uuid`,
	`billing_plan_uuid`,
	`cash_flow_channel_type`) values

	
(1,'1.00', 1, 'journal_voucher_uuid_1',3,1,0,'cash_flow_uuid_1','2015-10-10 11:11:11','cash_flow_serial_no_1',NULL,'notification_identity_1','bank_identity_2018','1.00','notification_record_uuid_1','2015-10-10 11:12:11','notification_identity','cash_flow_serial_no_1','1.00',1,'business_voucher_uuid_1','repayment_uuid_3',1),
(2,'1.00', 1, 'journal_voucher_uuid_2',3,1,0,'cash_flow_uuid_1','2015-10-10 11:11:11','cash_flow_serial_no_2',NULL,'notification_identity_2','bank_identity_2018','1.00','notification_record_uuid_2','2015-10-10 11:12:11','notification_identity','cash_flow_serial_no_2','1.00',1,'business_voucher_uuid_2','repayment_uuid_2',1);


INSERT INTO `business_voucher` (`id`, `account_id`, `account_side`, `billing_plan_breif`, `billing_plan_type_uuid`, `billing_plan_uuid`, `business_voucher_status`, `business_voucher_type_uuid`, `business_voucher_uuid`, `company_id`, `receivable_amount`, `settlement_amount`, `tax_invoice_uuid`) VALUES
('1', 1, 1, 'billing_plan_breif_1', 'billing_plan_type_uuid_1', 'repayment_uuid_3', 1, 'ef8d1fbd-3bcc-42a1-b021-912133d76744', 'business_voucher_uuid_1', 1, '1003.00','1.00' , NULL),
('2', 1, 1, 'billing_plan_breif_2', 'billing_plan_type_uuid_2', 'repayment_uuid_2', 1, 'ef8d1fbd-3bcc-42a1-b021-912133d76744', 'business_voucher_uuid_2', 1, '1002.00','2.00' , NULL);



SET FOREIGN_KEY_CHECKS=1;


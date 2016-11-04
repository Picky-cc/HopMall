delete from `offline_bill`;
delete from `source_document`;
delete from `finance_company`;
delete from `contract_account`;
delete from `customer`;
delete from `contract`;
delete from `app`;
delete from `house`;
delete from `financial_contract`;
delete from `rent_order`;
delete from `asset_set`;
delete from `payment_channel`;
delete from `ledger_book`;
delete from `ledger_book_shelf`;
delete from `company`;
delete from `asset_package`;

INSERT INTO `offline_bill` (`id`, `amount`, `bank_show_name`, `comment`, `create_time`, `trade_time`, `is_delete`, `status_modified_time`, `last_modified_time`, `offline_bill_no`, `offline_bill_status`, `offline_bill_uuid`, `serial_no`, `payer_account_name`, `payer_account_no`)
VALUES
	(1, 298315.6, '中国银行滨江支行', '王二线下打款:29831.56 元', '2016-05-04 15:25:08', '2016-05-04 15:24:00', 0, '2016-05-04 15:25:08', '2016-05-04 15:25:08', 'XX272F8917169A7B1B', 1, '8a855c1514b7480dba6ffba6450221cf', '2016050415243801', '王二', '0109108221231233');

INSERT INTO `source_document` (`id`, `company_id`, `source_document_uuid`, `source_document_type`, `create_time`, `issued_time`, `source_document_status`, `source_account_side`, `booking_amount`, `outlier_document_uuid`, `outlier_trade_time`, `outlier_counter_party_account`, `outlier_counter_party_name`, `outlier_account`, `outlie_account_name`, `outlier_account_id`, `outlier_company_id`, `outlier_serial_global_identity`, `outlier_memo`, `outlier_amount`, `outlier_settlement_modes`, `outlier_breif`, `outlier_account_side`, `appendix`, `first_outlier_doc_type`, `second_outlier_doc_type`, `third_outlier_doc_type`, `currency_type`, `audit_status`)
VALUES
	(1, 1, 'bcb9a20b7c0347098db4e480169e6983', 1, '2016-05-04 15:25:08', '2016-05-04 17:15:24', 0, 1, 0.00, '8a855c1514b7480dba6ffba6450221cf', '2016-05-05 15:24:00', '0109108221231233', '王二', '', '', NULL, 1, '2016050415243801', '', 298315.6, 3, '', 1, '', 'offline_bill', '', '', NULL, 0);

INSERT INTO `contract_account` (`id`, `pay_ac_no`, `bankcard_type`, `contract_id`, `payer_name`, `bank`, `bind_id`)
VALUES
	(1, '0109108221231233', 0, 1, '王二', '中国银行滨江支行', '8344e4d5d9539851a965a6727d21c401');

INSERT INTO `customer` (`id`, `account`, `app_id`,`customer_type`)
VALUES
	(1, '0109108221231233', 1,0),
	(2, '', 1,1);

INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`, `uuid`) VALUES 
('1', '上海', '测试金融公司', '测试金融', 'a02c02b9-6f98-11e6-bf08-00163e002839'),
('2', '南京', '测试商务公司', '测试分期', 'a02c078d-6f98-11e6-bf08-00163e002839'),
('3', '杭州', '杭州随地付有限公司', '随地付', 'a02c0830-6f98-11e6-bf08-00163e002839');

INSERT INTO `asset_package` (`id`,`contract_id`,  `financial_contract_id`, `loan_batch_id`) VALUES 
('1', '1', '2', '1');

	
INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `month_fee`, `app_id`, `customer_id`, `house_id`, `actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`,`active_version_no`,`financial_contract_uuid`)
VALUES
	(1, '2016-04-04', 'CSHT-1', 29831.56, 1, 1, 1, NULL, '2016-05-04 15:13:01', 0.1500000000, 0, 1, 5, 0, 100000.00, 0.0005000000,1,'financial_contract_uuid_1');

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `addressee`)
VALUES
	(1, 'anmeitu', '11111db75ebb24fa0993f4fa25775023', 0, 'http://e.zufangbao.cn', '安美途', 1, NULL);

INSERT INTO `house` (`id`, `address`, `app_id`)
VALUES
	(1, 'ZC-1', 1);

INSERT INTO `financial_contract` (`id`, `adva_matuterm`, `contract_no`, `app_id`, `company_id`, `adva_repo_term`,`payment_channel_id`,`financial_contract_uuid`,`ledger_book_no`)
VALUES
	(2, 3, 'YX-AMT-1', 1, 1, 0.0850000000,1,'financial_contract_uuid_1','ledger_book_no');

INSERT INTO `ledger_book` (`id`, `ledger_book_no`, `ledger_book_orgnization_id`, `book_master_id`, `party_concerned_ids`) VALUES 
(1, 'ledger_book_no', 1, 1, NULL);

INSERT INTO `payment_channel` (`id`, `channel_name`, `user_name`, `user_password`, `merchant_id`, `cer_file_path`, `pfx_file_path`, `pfx_file_key`, `payment_channel_type`, `api_url`, `from_date`, `thru_date`) 
 VALUES 
 ('1', '广东银联', 'user1', '123456', '123', NULL, NULL, NULL, '0', NULL, NULL, NULL);
 
	
INSERT INTO `finance_company` (`id`, `company_id`)
VALUES
	(1, 1);
	
INSERT INTO `rent_order` (`id`, `order_type`, `due_date`, `order_no`, `total_rent`, `customer_id`, `user_upload_param`, `modify_time`, `repayment_bill_id`, `asset_set_id`, `create_time`, `clearing_status`, `executing_settling_status`)
VALUES
	(1, 0, NOW(), 'JS272F890AC51F8E71', 29831.56, 1, '[1]', '2016-05-04 17:15:24', '854f7d819114434d8a019f7fe7964a14', 1, '2016-05-04 15:17:01', 0, 0);

INSERT INTO `asset_set` (`id`, `guarantee_status`, `settlement_status`, `asset_fair_value`, `asset_principal_value`, `asset_initial_value`, `asset_recycle_date`, `asset_status`, `on_account_status`, `repayment_plan_type`, `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `current_period`,`version_no`)
VALUES
	(1, 0, 0, 29831.56, 14831.56, 29831.56, '2016-05-05', 0, 1, 0, '2016-05-04 15:17:00', 'asset_uuid_1', '2016-05-04 15:13:01', '2016-05-04 17:15:24', 'ZC272F8904AC73B66F1', 1, '2016-05-04 17:15:24', 1,1);
	
INSERT INTO `ledger_book_shelf` (`id`, `ledger_uuid`, `debit_balance`, `credit_balance`, `first_account_name`, `first_account_uuid`, `account_side`, `second_account_name`, `second_account_uuid`, `third_account_name`, `third_account_uuid`, `first_party_id`, `second_party_id`, `third_party_id`, `forward_ledger_uuid`, `backward_ledger_uuid`, `batch_serial_uuid`, `amortized_date`, `book_in_date`, `business_voucher_uuid`, `carried_over_date`, `contract_id`, `contract_uuid`, `default_date`, `journal_voucher_uuid`, `ledger_book_no`, `ledger_book_owner_id`, `life_cycle`, `related_lv_1_asset_outer_idenity`, `related_lv_1_asset_uuid`, `related_lv_2_asset_outer_idenity`, `related_lv_2_asset_uuid`, `related_lv_3_asset_outer_idenity`, `related_lv_3_asset_uuid`, `source_document_uuid`) VALUES
	('1', 'ledger_uuid_1', '29831.56', '0.00', 'FST_RECIEVABLE_ASSET', '20000', '1', 'SND_RECIEVABLE_LOAN_ASSET', '20000.01', NULL, NULL, '1', NULL, NULL, NULL, 'ledger_uuid_3', NULL, '2016-05-01', '2016-05-16 17:24:47', '', NULL, '7', NULL, '2016-05-01 00:00:00', '', 'ledger_book_no', '1', '1', 'asset_single_no', 'asset_uuid_1', NULL, NULL, NULL, NULL, '');
DELETE FROM `contract`;
DELETE FROM `asset_set`;
DELETE FROM `asset_valuation_detail`;
DELETE FROM `rent_order`;
DELETE FROM `financial_contract`;
DELETE FROM `payment_channel`;
DELETE FROM `asset_package`;
DELETE FROM `company`;
delete from `ledger_book_shelf`;
delete from `ledger_book`;

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `end_date`, `month_fee`, `app_id`, `customer_id`, `house_id`, `actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`,`active_version_no`,`financial_contract_uuid`)
VALUES
	(1, '2016-03-01', 'DKHD-001', '2017-03-15', 10000.00, 1, 1, 1, NULL, '2016-03-15 13:34:35', 0.0010000000, 1, 1, 10, 0, 100000, 0.0005000000,1,'uuid1');

INSERT INTO `asset_package` (`id`, `is_available`,  `contract_id`, `asset_package_no`, `financial_contract_id`) VALUES 
('1', 1, 1, 'no1', '1');

INSERT INTO `ledger_book` (`id`, `ledger_book_no`, `ledger_book_orgnization_id`, `book_master_id`, `party_concerned_ids`) VALUES 
(1, 'ledger_book_no', 1, 1, NULL);
	
INSERT INTO `financial_contract` (`id`, `adva_matuterm`, `contract_no`, `app_id`, `company_id`,   `adva_repo_term`, `payment_channel_id`, `loan_overdue_start_day`, `loan_overdue_end_day`,`ledger_book_no`,`sys_create_penalty_flag`,`financial_contract_uuid`) VALUES 
('1', 3, 'YX_AMT_001', 1, 1, 30,1,5,90,'ledger_book_no',1,'uuid1');

INSERT INTO `payment_channel` (`id`, `channel_name`, `user_name`, `user_password`, `merchant_id`, `cer_file_path`, `pfx_file_path`, `pfx_file_key`, `payment_channel_type`, `api_url`, `from_date`, `thru_date`) VALUES 
(1, 'channel_name', 'user_name', 'user_password', 'merchant_id', 'cer_file_path', 'pfx_file_path', 'pfx_file_key', 0, 'api_url', NULL, NULL);


INSERT INTO `asset_set` (`id`, `asset_fair_value`,`asset_initial_value`,`asset_principal_value`,`asset_interest_value`, `asset_recycle_date`, `asset_status`, `asset_uuid`, `create_time`, `last_modified_time`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `version_no`,`active_status`)
VALUES
	(1, 10000.00,10000.00,10000.00,0, '2016-05-29', 0, 'asset_uuid_1', '2016-03-14 13:34:35', '2016-03-14 13:34:35', 'DKHD-001-01', 1, NULL,1,0);

INSERT INTO `ledger_book_shelf` (`id`, `ledger_uuid`, `debit_balance`, `credit_balance`, `first_account_name`, `first_account_uuid`, `account_side`, `second_account_name`, `second_account_uuid`, `third_account_name`, `third_account_uuid`, `first_party_id`, `second_party_id`, `third_party_id`, `forward_ledger_uuid`, `backward_ledger_uuid`, `batch_serial_uuid`, `amortized_date`, `book_in_date`, `business_voucher_uuid`, `carried_over_date`, `contract_id`, `contract_uuid`, `default_date`, `journal_voucher_uuid`, `ledger_book_no`, `ledger_book_owner_id`, `life_cycle`, `related_lv_1_asset_outer_idenity`, `related_lv_1_asset_uuid`, `related_lv_2_asset_outer_idenity`, `related_lv_2_asset_uuid`, `related_lv_3_asset_outer_idenity`, `related_lv_3_asset_uuid`, `source_document_uuid`) VALUES
	('1', 'ledger_uuid_1', '10000.00', '0.00', 'FST_UNEARNED_LOAN_ASSET', '10000', '1', '', '', NULL, NULL, '1', NULL, NULL, NULL, 'ledger_uuid_3', NULL, '2016-05-01', '2016-05-16 17:24:47', '', NULL, '7', NULL, '2016-05-01 00:00:00', '', 'ledger_book_no', '1', '1', 'ZC27304880AB312E3A', 'asset_uuid_1', NULL, NULL, NULL, NULL, '');
	
INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`)
VALUES
	(1, '上海', '云南国际信托有限公司', '云南信托');


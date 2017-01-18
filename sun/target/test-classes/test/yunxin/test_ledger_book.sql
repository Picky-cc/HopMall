SET FOREIGN_KEY_CHECKS=0;

delete from `asset_set`;
delete from `financial_contract`;
delete from `contract`;
delete from `asset_valuation_detail`;
delete from  ledger_book_shelf;
delete from  payment_channel;
delete from  customer;
delete from  app;
delete from  company;
delete from `asset_set_extra_charge`;

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `addressee` )
VALUES 
('1', 'anmeitu', '11111db75ebb24fa0993f4fa25775023', '\0', 'http://e.zufangbao.cn', '安美途', '14', NULL);


INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`) 
VALUES 
('14', '上海', '云南国际信托有限公司', '云南信托');

INSERT INTO `customer` (`id`, `account`, `mobile`, `name`, `source`, `app_id`,`customer_uuid`,`customer_type`) VALUES 
(1, NULL, NULL, NULL, NULL, 1,'11111db75ebb24fa0993f4fa25775023',1),
(2, NULL, NULL, NULL, NULL, 1,'2222db75ebb24fa0993f4fa25775023',0);


INSERT INTO `financial_contract` (`id`, `adva_matuterm`, `contract_no`, `contract_name`, `app_id`, `company_id`,   `adva_repo_term`,`loan_overdue_end_day`,`loan_overdue_start_day`,`payment_channel_id`,`ledger_book_no`,`asset_package_format`,`financial_contract_type`) VALUES 
('1', 3, 'DCF-NFQ-LR903A', '云南信托', 1, 14, 30,90,1,1,'yunxin_ledger_book',1,0);

INSERT INTO `payment_channel` (`id`, `channel_name`, `user_name`, `user_password`, `merchant_id`, `cer_file_path`, `pfx_file_path`, `pfx_file_key`, `payment_channel_type`, `api_url`, `from_date`, `thru_date`) 
 VALUES 
 ('1', '广东银联', 'user1', '123456', '123', NULL, NULL, NULL, '0', NULL, NULL, NULL);

INSERT INTO `asset_set` (`id`, `asset_initial_value`,`asset_fair_value`, `asset_recycle_date`, `asset_status`, `asset_uuid`, `create_time`, `last_modified_time`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`,`asset_interest_value`,`asset_principal_value`, `version_no`) VALUES 
('1',1000, 1010, '2015-10-19', 0, 'asset_uuid_1', '2015-10-19 13:34:35', '2015-10-19 13:34:35', 'DKHD-001-01', 1, '2015-10-19 13:34:35',100.00,900,1),
('2',1000, 1000, '2015-10-20', 0, 'asset_uuid_2', '2015-10-20 13:34:35', '2015-10-20 13:34:35', 'DKHD-001-02', 1, '2015-10-19 13:34:35',100.00,900,1);

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`,`end_date`,`app_id`, `customer_id`, `house_id`,`actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`,`active_version_no`) VALUES 
(1, '2015-10-19', 'DKHD-001', '2016-10-19', 1, 1, 1, NULL, '2015-10-19 13:34:35', 0.001, 1, 1, 12, 0, 1000, 0.0001,1);

INSERT INTO `asset_valuation_detail` (`id`, `amount`, `asset_value_date`, `created_date`, `subject`, `asset_set_id`) VALUES 
('1', '1000.00', '2016-03-14 19:46:38', '2016-03-14', '0', '1'),
('2', '5.00', '2016-03-15 19:47:16', '2016-03-15', '1', '1'),
('3', '15.00', '2016-03-16 19:47:16', '2016-03-16', '1', '1');

INSERT INTO `ledger_book_shelf` (`account_side`,`id`, `ledger_book_owner_id`, `ledger_book_no`, `ledger_uuid`, `related_lv_1_asset_uuid`, `debit_balance`, `credit_balance`, `first_account_name`, `first_account_uuid`, `second_account_name`, `second_account_uuid`, `third_account_name`, `third_account_uuid`, `first_party_id`, `second_party_id`, `third_party_id`, `contract_id`, `journal_voucher_uuid`, `amortized_date`, `business_voucher_uuid`, `source_document_uuid`, `life_cycle`, `backward_ledger_uuid`, `forward_ledger_uuid`, `batch_serial_uuid`, `book_in_date`, `carried_over_date`)
VALUES
	(0,2, 14, 'YUNXIN_AMEI_ledger_book', 'test_ledger_1', 'sss',  0, 1, 'FST_RECIEVABLE_ASSET', '20000', '', '', '', '', '', '', '', NULL, '', NULL, '', NULL, NULL, '', '', '', NULL, NULL),
	(0,4, 14, 'YUNXIN_AMEI_ledger_book', 'test_ledger_2', 'sss',  0, 1, 'FST_RECIEVABLE_ASSET', '20000', 'SND_RECIEVABLE_COLLECTION', '20000.05', '', '', '', '', '', NULL, '', NULL, '', NULL, NULL, '', '', '', NULL, NULL),
	(0,5, 14, 'YUNXIN_AMEI_ledger_book', 'test_ledger_3', 'sss',  0, 1, 'FST_RECIEVABLE_ASSET', '20000', 'SND_RECIEVABLE_COLLECTION', '20000.05', 'TRD_RECIEVABLE_COLLECTION_OVERDUE_LOAN', '20000.05.01', '', '', '', NULL, '', NULL, '', NULL, NULL, '', '', '', NULL, NULL);


SET FOREIGN_KEY_CHECKS=1;
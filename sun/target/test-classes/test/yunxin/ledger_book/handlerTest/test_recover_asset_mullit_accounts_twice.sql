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
delete from  `ledger_book`;
delete from `asset_set_extra_charge`;

INSERT INTO `ledger_book` (`id`, `ledger_book_no`, `ledger_book_orgnization_id`, `book_master_id`, `party_concerned_ids`) VALUES 
('1', 'yunxin_ledger_book', '1', NULL, NULL);


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
('1',1000, 1103, '2015-10-19', 0, 'asset_uuid_1', '2015-10-19 13:34:35', '2015-10-19 13:34:35', 'DKHD-001-01', 1, '2015-10-19 13:34:35',100.00,900,1);

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`,`end_date`,`app_id`, `customer_id`, `house_id`,`actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`,`active_version_no`) VALUES 
(1, '2015-10-19', 'DKHD-001', '2016-10-19', 1, 1, 1, NULL, '2015-10-19 13:34:35', 0.001, 1, 1, 12, 0, 1000, 0.0001,1);

INSERT INTO `asset_valuation_detail` (`id`, `amount`, `asset_value_date`, `created_date`, `subject`, `asset_set_id`) VALUES 
('1', '1000.00', '2016-03-14 19:46:38', '2016-03-14', '0', '1'),
('2', '5.00', '2016-03-15 19:47:16', '2016-03-15', '1', '1'),
('3', '15.00', '2016-03-16 19:47:16', '2016-03-16', '1', '1');

INSERT INTO `ledger_book_shelf` (`id`, `ledger_uuid`, `debit_balance`, `credit_balance`, `first_account_name`, `first_account_uuid`, `account_side`, `second_account_name`, `second_account_uuid`, `third_account_name`, `third_account_uuid`, `first_party_id`, `second_party_id`, `third_party_id`, `forward_ledger_uuid`, `backward_ledger_uuid`, `batch_serial_uuid`, `amortized_date`, `book_in_date`, `business_voucher_uuid`, `carried_over_date`, `contract_id`, `contract_uuid`, `default_date`, `journal_voucher_uuid`, `ledger_book_no`, `ledger_book_owner_id`, `life_cycle`, `related_lv_1_asset_outer_idenity`, `related_lv_1_asset_uuid`, `related_lv_2_asset_outer_idenity`, `related_lv_2_asset_uuid`, `related_lv_3_asset_outer_idenity`, `related_lv_3_asset_uuid`, `source_document_uuid`) VALUES 
('1', '86a7cda6-d7dc-458e-901c-e91804ac5ed2', '1.00', '0.00', 'FST_RECIEVABLE_ASSET', '20000', '1', 'SND_RECIEVABLE_OVERDUE_FEE', '20000.06', 'TRD_RECIEVABLE_OVERDUE_FEE_OBLIGATION', '20000.06.01', 'a02c02b9-6f98-11e6-bf08-00163e002839', 'c4fbe016-476c-4f12-b7fe-0d78116e87cf', NULL, NULL, NULL, '0d237f16-d1c9-4639-940c-c68cfa20a7eb', '2016-09-20', '2016-09-20 21:03:12', '', NULL, '7993', '45db59fa-1cdf-450c-bc0e-419002cb8afb', '2016-09-20 00:00:00', '', 'yunxin_ledger_book', '1', '1', 'ZC27498C84886D1108', 'asset_uuid_1', NULL, NULL, NULL, NULL, ''),
('2', '1b422ff0-7c68-4c05-b514-7bc41432daab', '100.00', '0.00', 'FST_RECIEVABLE_ASSET', '20000', '1', 'SND_RECIEVABLE_OVERDUE_FEE', '20000.06', 'TRD_RECIEVABLE_OVERDUE_FEE_SERVICE_FEE', '20000.06.02', 'a02c02b9-6f98-11e6-bf08-00163e002839', 'c4fbe016-476c-4f12-b7fe-0d78116e87cf', NULL, NULL, NULL, '0d237f16-d1c9-4639-940c-c68cfa20a7eb', '2016-09-20', '2016-09-20 21:03:12', '', NULL, '7993', '45db59fa-1cdf-450c-bc0e-419002cb8afb', '2016-09-20 00:00:00', '', 'yunxin_ledger_book', '1', '1', 'ZC27498C84886D1108', 'asset_uuid_1', NULL, NULL, NULL, NULL, ''),
('3', '01df722d-000a-41dd-86b3-c4b87f096976', '1.00', '0.00', 'FST_RECIEVABLE_ASSET', '20000', '1', 'SND_RECIEVABLE_LOAN_PENALTY', '20000.03', NULL, NULL, 'a02c02b9-6f98-11e6-bf08-00163e002839', 'c4fbe016-476c-4f12-b7fe-0d78116e87cf', NULL, NULL, NULL, '0d237f16-d1c9-4639-940c-c68cfa20a7eb', '2016-09-20', '2016-09-20 21:03:12', '', NULL, '7993', '45db59fa-1cdf-450c-bc0e-419002cb8afb', '2016-09-20 00:00:00', '', 'yunxin_ledger_book', '1', '1', 'ZC27498C84886D1108', 'asset_uuid_1', NULL, NULL, NULL, NULL, ''),
('4', 'eeb21d1d-e9b3-4702-bcab-2d18af9cbd0c', '1.00', '0.00', 'FST_RECIEVABLE_ASSET', '20000', '1', 'SND_RECIEVABLE_OVERDUE_FEE', '20000.06', 'TRD_RECIEVABLE_OVERDUE_FEE_OTHER_FEE', '20000.06.03', 'a02c02b9-6f98-11e6-bf08-00163e002839', 'c4fbe016-476c-4f12-b7fe-0d78116e87cf', NULL, NULL, NULL, '0d237f16-d1c9-4639-940c-c68cfa20a7eb', '2016-09-20', '2016-09-20 21:03:12', '', NULL, '7993', '45db59fa-1cdf-450c-bc0e-419002cb8afb', '2016-09-20 00:00:00', '', 'yunxin_ledger_book', '1', '1', 'ZC27498C84886D1108', 'asset_uuid_1', NULL, NULL, NULL, NULL, ''),
('5', '1330a4b5-a0ac-462a-85e2-7269f6cd021f', '1000.00', '0.00', 'FST_RECIEVABLE_ASSET', '20000', '1', 'SND_RECIEVABLE_OVERDUE_LOAN_ASSET', '20000.05', 'TRD_RECIEVABLE_OVERDUE_LOAN_ASSET_PRINCIPLE', '20000.05.01', 'a02c02b9-6f98-11e6-bf08-00163e002839', 'c4fbe016-476c-4f12-b7fe-0d78116e87cf', NULL, NULL, 'a24c8654-7c6a-4e20-85b3-7022f733aa1f', 'dbbf643f-242c-488d-aafe-c0101dbaa073', '2016-09-20', '2016-09-20 21:03:12', NULL, NULL, '7993', '45db59fa-1cdf-450c-bc0e-419002cb8afb', '2016-09-20 00:00:00', NULL, 'yunxin_ledger_book', '1', '1', 'ZC27498C84886D1108', 'asset_uuid_1', NULL, NULL, NULL, NULL, NULL);


SET FOREIGN_KEY_CHECKS=1;
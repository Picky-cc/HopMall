SET FOREIGN_KEY_CHECKS=0;

delete from `rent_order`;
delete from `asset_set`;
delete from `financial_contract`;
delete from `contract`;
delete from `asset_valuation_detail`;
truncate table `asset_valuation_detail`;
delete from `asset_package`;
delete from `transfer_application`;
delete from `ledger_book`;
delete from `ledger_book_shelf`;

INSERT INTO `ledger_book` (`id`, `ledger_book_no`, `ledger_book_orgnization_id`, `book_master_id`, `party_concerned_ids`) VALUES 
(1, 'ledger_book_no', 1, 1, NULL);

INSERT INTO `financial_contract` (`id`, `adva_matuterm`, `contract_no`, `app_id`, `company_id`,   `adva_repo_term`,`ledger_book_no`,`sys_create_penalty_flag`,`financial_contract_uuid`,`sys_create_statement_flag`) VALUES 
('1', 3, 'YX_AMT_001', 1, 1, 30,'ledger_book_no',1,'uuid',1);

-- assetSet：1存在处理中的结算单;结算单4已有结算单，不生成新的结算单;结算单5状态为已结转，不生成新的结算单;asset6已作废
-- 只有结算单2生成新的 结算单;
INSERT INTO `asset_set` (`id`, `asset_fair_value`,`asset_initial_value`, `asset_recycle_date`, `asset_status`, `asset_uuid`, `create_time`, `last_modified_time`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `version_no`,`active_status`) VALUES 
('1', 1010,1000, DATE_ADD(NOW(),INTERVAL -1 DAY), 0, 'asset_uuid_1', '2015-10-19 13:34:35', '2015-10-19 13:34:35', 'DKHD-001-01', 1, '2015-10-19 13:34:35',1,0),
('2', 1010,1000, Date(NOW()), 0, 'asset_uuid_2', '2015-10-20 13:34:35', '2015-10-20 13:34:35', 'DKHD-001-02', 1, '2015-10-19 13:34:35',1,0),
('3', 1000,1000, DATE_ADD(NOW(),INTERVAL +1 DAY), 0, 'asset_uuid_3', '2015-10-20 13:34:35', '2015-10-20 13:34:35', 'DKHD-001-03', 1, '2015-10-19 13:34:35',1,0),
('4', 1000,1000, Date(NOW()), 0, 'asset_uuid_4', '2015-10-20 13:34:35', '2015-10-20 13:34:35', 'DKHD-001-04', 1, '2015-10-19 13:34:35',1,0),
('5', 1000,1000, Date(NOW()), 1, 'asset_uuid_5', '2015-10-20 13:34:35', '2015-10-20 13:34:35', 'DKHD-001-05', 1, '2015-10-19 13:34:35',1,0),
('6', 1000,1000, Date(NOW()), 1, 'asset_uuid_6', '2015-10-20 13:34:35', '2015-10-20 13:34:35', 'DKHD-001-06', 1, '2015-10-19 13:34:35',1,1);

INSERT INTO `ledger_book_shelf` (`id`, `ledger_uuid`, `debit_balance`, `credit_balance`, `first_account_name`, `first_account_uuid`, `account_side`, `second_account_name`, `second_account_uuid`, `third_account_name`, `third_account_uuid`, `first_party_id`, `second_party_id`, `third_party_id`, `forward_ledger_uuid`, `backward_ledger_uuid`, `batch_serial_uuid`, `amortized_date`, `book_in_date`, `business_voucher_uuid`, `carried_over_date`, `contract_id`, `contract_uuid`, `default_date`, `journal_voucher_uuid`, `ledger_book_no`, `ledger_book_owner_id`, `life_cycle`, `related_lv_1_asset_outer_idenity`, `related_lv_1_asset_uuid`, `related_lv_2_asset_outer_idenity`, `related_lv_2_asset_uuid`, `related_lv_3_asset_outer_idenity`, `related_lv_3_asset_uuid`, `source_document_uuid`) VALUES
	('1', 'ledger_uuid_1', '10000.00', '0.00', 'FST_UNEARNED_LOAN_ASSET', '10000', '1', '', '', NULL, NULL, '1', NULL, NULL, NULL, '', NULL, '2016-05-01', '2016-05-16 17:24:47', '', NULL, '7', NULL, '2016-05-01 00:00:00', '', 'ledger_book_no', '1', '1', 'ZC27304880AB312E3A', 'asset_uuid_1', NULL, NULL, NULL, NULL, ''),
	('2', 'ledger_uuid_2', '10000.00', '0.00', 'FST_UNEARNED_LOAN_ASSET', '10000', '1', '', '', NULL, NULL, '1', NULL, NULL, NULL, '', NULL, '2016-05-01', '2016-05-16 17:24:47', '', NULL, '7', NULL, '2016-05-01 00:00:00', '', 'ledger_book_no', '1', '1', 'ZC27304880AB312E3A', 'asset_uuid_2', NULL, NULL, NULL, NULL, ''),
	('3', 'ledger_uuid_3', '10000.00', '0.00', 'FST_UNEARNED_LOAN_ASSET', '10000', '1', '', '', NULL, NULL, '1', NULL, NULL, NULL, '', NULL, '2016-05-01', '2016-05-16 17:24:47', '', NULL, '7', NULL, '2016-05-01 00:00:00', '', 'ledger_book_no', '1', '1', 'ZC27304880AB312E3A', 'asset_uuid_3', NULL, NULL, NULL, NULL, ''),
	('4', 'ledger_uuid_4', '10000.00', '0.00', 'FST_UNEARNED_LOAN_ASSET', '10000', '1', '', '', NULL, NULL, '1', NULL, NULL, NULL, '', NULL, '2016-05-01', '2016-05-16 17:24:47', '', NULL, '7', NULL, '2016-05-01 00:00:00', '', 'ledger_book_no', '1', '1', 'ZC27304880AB312E3A', 'asset_uuid_4', NULL, NULL, NULL, NULL, ''),
	('5', 'ledger_uuid_5', '10000.00', '0.00', 'FST_UNEARNED_LOAN_ASSET', '10000', '1', '', '', NULL, NULL, '1', NULL, NULL, NULL, '', NULL, '2016-05-01', '2016-05-16 17:24:47', '', NULL, '7', NULL, '2016-05-01 00:00:00', '', 'ledger_book_no', '1', '1', 'ZC27304880AB312E3A', 'asset_uuid_5', NULL, NULL, NULL, NULL, ''),
	('6', 'ledger_uuid_6', '10000.00', '0.00', 'FST_UNEARNED_LOAN_ASSET', '10000', '1', '', '', NULL, NULL, '1', NULL, NULL, NULL, '', NULL, '2016-05-01', '2016-05-16 17:24:47', '', NULL, '7', NULL, '2016-05-01 00:00:00', '', 'ledger_book_no', '1', '1', 'ZC27304880AB312E3A', 'asset_uuid_6', NULL, NULL, NULL, NULL, '');

INSERT INTO `asset_package` (`id`, `create_time`, `contract_id`, `asset_package_no`, `financial_contract_id`) VALUES 
('1', '2015-10-19 13:34:35', '1','no1','1');

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`,`end_date`,`app_id`, `customer_id`, `house_id`,`actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`,`active_version_no`,`financial_contract_uuid`) VALUES 
(1, '2015-10-19', 'DKHD-001', '2016-10-19', 1, 1, 1, NULL, '2015-10-19 13:34:35', 0.001, 1, 1, 12, 0, 1000, 0.0001,1,'uuid');


INSERT INTO `rent_order` (`id`, `due_date`, `order_no`, `payout_time`, `total_rent`, `customer_id`, `user_upload_param`, `modify_time`, `asset_set_id`, `create_time`, `clearing_status`, `executing_settling_status`) VALUES
(1, '2016-01-19', 'DKHD-001-01-20160307', '2016-10-19 13:34:35',1000, '1','', '2016-10-19', 1, '2015-10-19',0,3),
(2, '2016-01-20', 'DKHD-001-01-20160308', '2016-10-19 13:34:35',1000, '1','', '2016-10-19', 1, '2015-10-19',0,1),
(3, '2016-01-21', 'DKHD-001-02-20160409', '2016-10-19 13:34:35',1000, '1','', '2016-10-19', 2, '2015-10-19',0,3),
(4, Date(NOW()), 'DKHD-001-04-20160409', '2016-10-19 13:34:35',1000, '1','', '2016-10-19', 4, '2015-10-19',0,0);

INSERT INTO `asset_valuation_detail` (`id`, `amount`, `asset_value_date`, `created_date`, `subject`, `asset_set_id`) VALUES 
('1', '1000.00', '2016-03-14 19:46:38', '2016-03-14', '0', '2'),
('2', '10.00', '2016-03-15 19:47:16', '2016-03-15', '1', '2');

INSERT INTO `transfer_application` (`id`, `amount`, `comment`, `create_time`, `deduct_status`, `payment_way`, `executing_deduct_status`, `transfer_application_no`, `order_id`, `deduct_time`, `last_modified_time`, `contract_account_id`, `union_pay_order_no`)
VALUES
	(1, 1000.00, '2007 : ', '2016-06-02 19:49:18', 0, 0, 1, '2735AB9E354D1327', 2, NULL, '2016-06-02 19:56:27', 1, '2735AB9E354D1327');


SET FOREIGN_KEY_CHECKS=1;

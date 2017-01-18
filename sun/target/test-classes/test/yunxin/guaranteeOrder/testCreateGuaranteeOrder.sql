SET FOREIGN_KEY_CHECKS=0;

delete from `asset_package`;
delete from `rent_order`;
delete from `asset_set`;
delete from `financial_contract`;
delete from `contract`;
delete from `app`;
delete from `customer`;
delete from `contract_account`;

-- asset5已作废
INSERT INTO `asset_set` (`id`, `guarantee_status`, `asset_fair_value`,`asset_initial_value`, `asset_recycle_date`, `asset_status`, `on_account_status`, `repayment_plan_type`, `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `current_period`, `version_no`,`active_status`,`overdue_status`)
VALUES
	(1, 0, 2984.65,1000, DATE_ADD(NOW(),INTERVAL -5 DAY), 0, 1, 0, '2016-04-07 21:49:04', '0d712307-a747-4a57-9fa5-792cf76c1a4c', '2016-04-06 16:43:47', '2016-04-07 21:49:04', 'test-contract-1-1', 1, NULL, 1,1,0,2),
	(2, 0, 2983.16,1000, DATE_ADD(NOW(),INTERVAL -1 DAY), 0, 1, 0, NULL, '1676780d-15bf-4fe3-968c-62be2b02c3aa', '2016-04-06 16:43:47', NULL, 'test-contract-1-2', 1, NULL, 2,1,0,0),
	(3, 0, 2984.65,1000, DATE_ADD(NOW(),INTERVAL -1 DAY), 1, 1, 0, '2016-04-07 21:49:04', '25804e12-b6ad-4ca4-86d5-6e6cb7616847', '2016-04-06 16:43:47', '2016-04-07 21:49:04', 'test-contract-2-1', 2, DATE_ADD(NOW(),INTERVAL -1 DAY), 1,1,0,0),
	(4, 0, 2983.16,1000, DATE(NOW()), 0, 1, 0, NULL, '1676780d-15bf-4fe3-968c-62be2b02c3aa', '2016-04-06 16:43:47', NULL, 'test-contract-2-2', 1, NULL, 2,1,0,0),
	(5, 0, 2984.65,1000, DATE_ADD(NOW(),INTERVAL -5 DAY), 0, 1, 0, '2016-04-07 21:49:04', 'uuid_5', '2016-04-06 16:43:47', '2016-04-07 21:49:04', 'test-contract-3-1', 1, NULL, 1,1,1,0);

INSERT INTO `asset_package` (`id`, `create_time`, `contract_id`, `asset_package_no`, `financial_contract_id`) VALUES 
('1', '2015-10-19 13:34:35', '1','no1','1'),
('2', '2015-10-19 13:34:35', '2','no2','2');

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `addressee`) VALUES 
('1', 'anmeitu', '11111db75ebb24fa0993f4fa25775023', '\0', 'http://e.zufangbao.cn', '安美途', '1', NULL),
('2', 'test4Zufangbao', '2e85ae999845f25faf8ea7b514ee0aca', '\0', 'http://e.zufangbao.cn', '租房宝测试账号', '2', NULL);

INSERT INTO `customer` (`id`, `account`, `mobile`, `name`, `source`, `app_id`) VALUES 
('1', '01091081XXXXX', '13777846666', '王二', 'D001', '1'),
('2', '01091082XXXXX', '13777846666', '王二', 'D002', '2');

INSERT INTO `financial_contract` (`id`, `adva_matuterm`, `contract_no`, `app_id`, `company_id`,   `adva_repo_term`, `loan_overdue_start_day`, `loan_overdue_end_day`,`sys_create_penalty_flag`,`sys_create_guarantee_flag`) VALUES 
('1', 3, 'YX_AMT_001', 1, 1, 30, 5, 90,1,1),
('2', 3, 'YX_AMT_002', 2, 1, 30, 5, 90,1,1);

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`,`end_date`,`app_id`, `customer_id`, `house_id`,`actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`,`month_fee`,`active_version_no`) VALUES 
(1, '2015-10-19', 'DKHD-001', '2016-10-19', 1, 1, 1, NULL, '2015-10-19 13:34:35', 0.001, 1, 1, 12, 0, 10000, 0.0001,1000,1),
(2, '2015-10-20', 'DKHD-002', '2016-10-19', 2, 1, 1, NULL, '2015-10-19 13:34:35', 0.001, 1, 1, 12, 0, 10000, 0.0001,1000,1);


SET FOREIGN_KEY_CHECKS=1;


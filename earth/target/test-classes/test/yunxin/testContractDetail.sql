SET FOREIGN_KEY_CHECKS = 0;

delete from `contract`;
delete from `app`;
delete from `company`;
delete from `house`;
delete from `contract`;
delete from `customer`;
delete from `principal`;
delete from `asset_set`;
delete from `contract_account`;
delete from `payment_channel`;
delete from `loan_batch`;
delete from `asset_package`;
delete from `contract_account`;
delete from `financial_contract`;
delete from `asset_package`;
	
INSERT INTO `contract` (`id`, `asset_type`, `begin_date`, `contract_no`, `month_fee`, `app_id`, `customer_id`, `house_id`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`,`active_version_no`) 
VALUES 
('1687', '0', '2016-03-08', 'DKHD-001', '15976.15', '1', '1514', '1440', '2016-03-28 11:20:28', '0.1500000000', '0', '1', '20', '0', '100000.00', '0.0005000000',1);

INSERT INTO `contract_account` (`id`, `pay_ac_no`, `bankcard_type`, `contract_id`, `payer_name`, `bank`, `bind_id`, `id_card_num`, `bank_code`, `province`, `city`, `from_date`, `thru_date`)
VALUES 
('1', '6217000000000003006', NULL, '1687', '测试用户1', '中国邮政储蓄银行', NULL, 'ID_card1', '403', '安徽省', '亳州', '2016-04-17 00:00:00', '2900-01-01 00:00:00');
INSERT INTO `financial_contract` (`id`, `adva_matuterm`, `contract_no`, `contract_name`, `app_id`, `company_id`,   `adva_repo_term`,`loan_overdue_end_day`,`loan_overdue_start_day`,`payment_channel_id`,`ledger_book_no`,`asset_package_format`,`financial_contract_type`) VALUES 
('1', 3, 'DCF-NFQ-LR903A', '云南信托', 1, 1, 30,90,1,1,'yunxin_ledger_book',1,0);

INSERT INTO `payment_channel` (`id`, `channel_name`, `user_name`, `user_password`, `merchant_id`, `cer_file_path`, `pfx_file_path`, `pfx_file_key`, `payment_channel_type`, `api_url`, `from_date`, `thru_date`) 
 VALUES 
 ('1', '广东银联', 'user1', '123456', '123', NULL, NULL, NULL, '0', NULL, NULL, NULL);
INSERT INTO `loan_batch` (`id`, `is_available`, `code`, `create_time`, `financial_contract_id`, `last_modified_time`, `loan_date`) 
VALUES 
('1', '', 'DCF-NFQ-LR903A 20160518 15:14:100', '2016-05-18 15:14:48', '1', '2016-05-19 18:43:20', '2016-05-19 18:43:20');

INSERT INTO `asset_package` (`id`, `is_available`, `create_time`, `contract_id`, `asset_package_no`, `financial_contract_id`, `loan_batch_id`) 
VALUES 
('1', NULL, NULL, '1687', NULL, '1', '1');

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `addressee`) 
VALUES 
('1', 'anmeitu', '11111db75ebb24fa0993f4fa25775023', '\0', 'http://e.zufangbao.cn', '安美途', '1', NULL);


INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`) 
VALUES 
('1', '上海', '云南国际信托有限公司', '云南信托');


INSERT INTO `house` (`id`, `address`, `app_id`)
VALUES 
('1440', 'V001', '1');


INSERT INTO `customer` (`id`, `account`, `app_id`) 
VALUES 
('1514', '01091081XXXXX', '1');
INSERT INTO `principal` (`id`, `authority`, `name`, `password`, `start_date`, `thru_date`) 
VALUES 
('1', 'ROLE_SUPER_USER', 'root', 'befd2450f81f88ecc5fbcc4c1f97f0b4', NULL, '2015-12-01 15:53:30');


INSERT INTO `asset_set` (`id`, `guarantee_status`, `settlement_status`,`asset_principal_value`,`asset_interest_value`, `asset_fair_value`, `asset_initial_value`, `asset_recycle_date`, `asset_status`, `on_account_status`, `repayment_plan_type`, `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `current_period`,`version_no`)
VALUES 
('7101', '2', '0',0,0, '15.98', '0.00', '2016-04-08', '0', '1', 0, '2016-04-13 16:45:00', '6b40848f-ff52-4047-897f-2ca2135e9fd6', '2016-03-31 19:49:37', '2016-04-18 18:35:00', 'DKHD-001-01', '1687', NULL, '1',1),
('7102', '0', '0',0,0, '15976.15', '0.00', DATE_ADD(now(),INTERVAL +1 DAY), '0', '1', 0, NULL, '0a0a76b7-edf6-4682-a6d6-39ef6f55b8ba', '2016-03-31 19:49:37', NULL, 'DKHD-001-02', '1687', NULL, '2',1);

SET FOREIGN_KEY_CHECKS = 1;
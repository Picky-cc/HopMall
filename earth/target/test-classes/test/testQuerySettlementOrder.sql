
delete from settlement_order;
delete from asset_set;
delete from contract;
delete from app;
delete from principal;
delete from asset_package;
delete from company;
delete from customer;
delete from financial_contract;
delete from house;


INSERT INTO `asset_package` (`id`, `create_time`, `contract_id`, `asset_package_no`, `financial_contract_id`) VALUES 
('1', '2015-10-19 13:34:35', '1','no1','1');

INSERT INTO `company` (`id`, `short_name`) VALUES 
(1, 'anmeitu'),
(2, '租房宝');

INSERT INTO `customer` (`id`, `account`, `mobile`, `name`, `source`, `app_id`, `customer_uuid`) VALUES 
('1', NULL, NULL, '测试用户1', 'C74211', '1', 'd6e8090c-a7a2-40c1-917e-3e292a0b707a');

INSERT INTO `house` (`id`, `address`, `app_id`) VALUES 
('1', NULL, '1');

INSERT INTO `financial_contract` (`id`, `adva_matuterm`, `contract_no`, `app_id`, `company_id`,   `adva_repo_term`, `payment_channel_id`) VALUES 
('1', 3, 'YX_AMT_001', 1, 1, 30,1);

INSERT INTO `asset_set` (`id`, `guarantee_status`, `settlement_status`, `asset_initial_value`,`asset_fair_value`, `asset_recycle_date`, `asset_status`, `on_account_status`, `repayment_plan_type`, `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `current_period`,`version_no`)
VALUES 
('7101', '0', '0', '10.00','15.98', '2016-04-08', '0', '1', 0, '2016-04-13 16:45:00', '6b40848f-ff52-4047-897f-2ca2135e9fd6', '2016-03-31 19:49:37', '2016-04-13 16:45:00', 'DKHD-001-01', '1', NULL, '1',1);


INSERT INTO `contract` (`id`, `asset_type`, `begin_date`, `contract_no`, `end_date`, `month_fee`, `app_id`, `customer_id`, `house_id`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`,`active_version_no`) 
VALUES ('1', '0', '2016-03-08', 'DKHD-001', NULL, '15976.15', '1', '1', '1', '2016-03-31 19:49:34', '0.1500000000', '0', '1', '20', '0', '100000.00', '0.0005000000',1);

INSERT INTO `settlement_order` (`id`, `asset_set_id`, `settle_order_no`, `guarantee_order_no`, `due_date`, `overdue_days`, `overdue_penalty`, `create_time`, `settlement_amount`,`comment`)
VALUES ('1', '7101', 'qs123456', 'bc123456', '2016-04-18', '0', '0.00', NULL, '0.00',  NULL);

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `addressee`)
VALUES 
('1', 'anmeitu', '11111db75ebb24fa0993f4fa25775023', '\0', 'http://e.zufangbao.cn', '安美途', '1', NULL);

INSERT INTO `principal` (`id`, `authority`, `name`, `password`) VALUES
	(1, 'ROLE_SUPER_USER', 'root', 'e10adc3949ba59abbe56e057f20f883e');

SET FOREIGN_KEY_CHECKS=1;
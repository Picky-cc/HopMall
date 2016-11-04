SET FOREIGN_KEY_CHECKS = 0;

delete from `contract`;
delete from `app`;
delete from `company`;
delete from `house`;
delete from `contract`;
delete from `customer`;
delete from `principal`;
delete from `asset_package`;
delete from `financial_contract`;

INSERT INTO `financial_contract` (`id`, `asset_package_format`, `adva_matuterm`, `adva_start_date`, `contract_no`, `contract_name`, `app_id`, `company_id`, `adva_repo_term`, `thru_date`, `capital_account_id`, `financial_contract_type`, `loan_overdue_start_day`, `loan_overdue_end_day`, `payment_channel_id`, `ledger_book_no`, `financial_contract_uuid`) 
VALUES 
('5', '1', '7', '2016-06-08 00:00:00', 'DCF-NFQ-LR903', 'sasad', '1', '1', '3', '2016-09-05 00:00:00', '2', '1', '0', '3', '0', 'yunxin_nfq_ledger_book', 'd7b3b325-719a-42af-a129-0ac861f18ebe');


INSERT INTO `asset_package` (`id`, `is_available`, `create_time`, `contract_id`, `asset_package_no`, `financial_contract_id`, `loan_batch_id`)
VALUES
	(1, NULL, NULL, 1687, NULL, 5, 1);
INSERT INTO `contract` (`id`, `asset_type`, `begin_date`, `contract_no`, `month_fee`, `app_id`, `customer_id`, `house_id`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`,`active_version_no`,`financial_contract_uuid`) 
VALUES 
('1687', '0', '2016-03-08', 'DKHD-001', '15976.15', '1', '1514', '1440', '2016-03-28 11:20:28', '0.1500000000', '0', '1', '20', '0', '100000.00', '0.0005000000',1,'d7b3b325-719a-42af-a129-0ac861f18ebe');


INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `addressee`) 
VALUES 
('1', 'anmeitu', '11111db75ebb24fa0993f4fa25775023', '\0', 'http://e.zufangbao.cn', '安美途', '1', NULL);


INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`) 
VALUES 
('1', '上海', '云南国际信托有限公司', '云南信托');


INSERT INTO `house` (`id`, `address`, `app_id`)
VALUES 
('1440', 'V001', '1');


INSERT INTO `customer` (`id`, `account`, `app_id`,`name`) 
VALUES 
('1514', '01091081XXXXX', '1','name_1');
INSERT INTO `principal` (`id`, `authority`, `name`, `password`, `start_date`, `thru_date`) 
VALUES 
('1', 'ROLE_SUPER_USER', 'root', 'befd2450f81f88ecc5fbcc4c1f97f0b4', NULL, '2015-12-01 15:53:30');
SET FOREIGN_KEY_CHECKS = 1;
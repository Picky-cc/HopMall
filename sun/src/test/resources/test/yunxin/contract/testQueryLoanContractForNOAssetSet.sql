SET FOREIGN_KEY_CHECKS=0;

delete from contract;
delete from app;
delete from customer;
delete from house;
delete from financial_contract;
delete from asset_package;
delete from contract_account;
delete from account;
delete from asset_set;
delete from company;


INSERT INTO  `contract` (`id`, `unique_id`, `begin_date`, `contract_no`, `end_date`, `asset_type`, `month_fee`, `app_id`, `customer_id`, `house_id`, `actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`,`active_version_no`,`financial_contract_uuid`) 
VALUES 
('325', NULL, '2016-04-17', '云信信2016-78-DK(ZQ2016042522502)', NULL, '1', '0.00', '1', '339', '339', NULL, '2016-06-15 22:04:59', '0.1560000000', '0', '0', '0', '2', '0.00', '0.0005000000','1','d7b3b325-719a-42af-a129-0ac861f18ebe');


INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `addressee`)
VALUES 
('1', 'nongfenqi', '', '', '', '农分期', '2', NULL);


INSERT INTO `customer` (`id`, `account`, `mobile`, `name`, `source`, `app_id`, `customer_uuid`) 
VALUES 
('339', NULL, NULL, '测试用户3', 'C30433', '1', '76000e99-c92f-4a45-a58a-39857326cd42');


INSERT INTO `house` (`id`, `address`, `app_id`) VALUES ('339', NULL, '1');

INSERT INTO `financial_contract` (`id`, `asset_package_format`, `adva_matuterm`, `adva_start_date`, `contract_no`, `contract_name`, `app_id`, `company_id`, `adva_repo_term`, `thru_date`, `capital_account_id`, `financial_contract_type`, `loan_overdue_start_day`, `loan_overdue_end_day`, `payment_channel_id`, `ledger_book_no`, `financial_contract_uuid`) 
VALUES 
('1', '1', '7', '2016-06-08 00:00:00', 'DCF-NFQ-LR903', 'sasad', '1', '1', '3', '2016-09-05 00:00:00', '2', '1', '0', '3', '0', 'yunxin_nfq_ledger_book', 'd7b3b325-719a-42af-a129-0ac861f18ebe');


INSERT INTO `asset_package` (`id`, `is_available`, `create_time`, `contract_id`, `asset_package_no`, `financial_contract_id`, `loan_batch_id`) 
VALUES 
('325', NULL, NULL, '325', NULL, '1', '32');


INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`) 
VALUES 
('1', '上海', '云南国际信托有限公司', '云南信托');

INSERT INTO `contract_account` (`id`, `pay_ac_no`, `bankcard_type`, `contract_id`, `payer_name`, `bank`, `bind_id`, `id_card_num`, `bank_code`, `province`, `city`,`thru_date`) 
VALUES 
('325', '6217000000000003015', NULL, '325', '测试用户3', '中国建设银行', NULL, NULL, '105', '安徽省', '亳州','2900-01-01 00:00:00');

INSERT INTO `account` (`id`, `account_name`, `account_no`, `bank_name`, `alias`, `attr`, `scan_cash_flow_switch`, `usb_key_configured`) VALUES ('2', 'name', 'sadas', 'name', NULL, NULL, '\0', '\0');



SET FOREIGN_KEY_CHECKS=1;
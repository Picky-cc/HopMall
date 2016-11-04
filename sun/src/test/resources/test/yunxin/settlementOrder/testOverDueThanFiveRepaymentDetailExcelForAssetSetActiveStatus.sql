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
('325', NULL, '2016-04-17', '云信信2016-78-DK(ZQ2016042522502)', NULL, '1', '0.00', '1', '339', '339', NULL, '2016-06-15 22:04:59', '0.1560000000', '0', '0', '2', '2', '4500.00', '0.0005000000',1,'d7b3b325-719a-42af-a129-0ac861f18ebe');


INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `addressee`)
VALUES 
('1', 'nongfenqi', '', '', '', '农分期', '2', NULL);


INSERT INTO `customer` (`id`, `account`, `mobile`, `name`, `source`, `app_id`, `customer_uuid`) 
VALUES 
('339', NULL, NULL, '测试用户3', 'C30433', '1', '76000e99-c92f-4a45-a58a-39857326cd42');


INSERT INTO `house` (`id`, `address`, `app_id`) VALUES ('339', NULL, '1');

INSERT INTO `financial_contract` (`id`, `asset_package_format`, `adva_matuterm`, `adva_start_date`, `contract_no`, `contract_name`, `app_id`, `company_id`, `adva_repo_term`, `thru_date`, `capital_account_id`, `financial_contract_type`, `loan_overdue_start_day`, `loan_overdue_end_day`, `payment_channel_id`, `ledger_book_no`, `financial_contract_uuid`) 
VALUES 
('1', '1', '7', '2016-06-08 00:00:00', 'DCF-NFQ-LR903', 'sasad', '1', '1', '3', '2016-09-05 00:00:00', '2', '1', '5', '90', '0', 'yunxin_nfq_ledger_book', 'd7b3b325-719a-42af-a129-0ac861f18ebe');


INSERT INTO `asset_package` (`id`, `is_available`, `create_time`, `contract_id`, `asset_package_no`, `financial_contract_id`, `loan_batch_id`) 
VALUES 
('325', NULL, NULL, '325', NULL, '1', '32');


INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`) 
VALUES 
('1', '上海', '云南国际信托有限公司', '云南信托');

INSERT INTO `contract_account` (`id`, `pay_ac_no`, `bankcard_type`, `contract_id`, `payer_name`, `bank`, `bind_id`, `id_card_num`, `bank_code`, `province`, `city`, `from_date`, `thru_date`) 
VALUES 
('325', '6217000000000003015', NULL, '325', '测试用户3', '中国建设银行', NULL, NULL, '105', '安徽省', '亳州', '2016-06-15 22:04:59', '2900-01-01 00:00:00');

INSERT INTO `account` (`id`, `account_name`, `account_no`, `bank_name`, `alias`, `attr`, `scan_cash_flow_switch`, `usb_key_configured`) VALUES ('2', 'name', 'sadas', 'name', NULL, NULL, '\0', '\0');


INSERT INTO `asset_set` (`id`, `guarantee_status`, `settlement_status`, `asset_fair_value`, `asset_principal_value`, `asset_interest_value`, `asset_initial_value`, `asset_recycle_date`, `confirm_recycle_date`, `refund_amount`, `asset_status`, `on_account_status`, `repayment_plan_type`, `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `comment`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `current_period`,`version_no`,`active_status`) 
VALUES 
('5', '1', '0', '2434.80', '0.00', '2400.00', '2400.00', '2016-05-17', NULL, '0.00', '0', '1', 0, '2016-06-15 22:13:47', '5df794c5-5362-497f-adcb-65b03f74e425', '2016-06-15 22:04:59', '2016-06-15 23:24:31', NULL, 'ZC27367D23EFEE063B', '325', NULL, '1',1,0),
 ('6', '1', '0', '2130.45', '0.00', '2100.00', '2100.00', '2016-05-17', NULL, '0.00', '0', '1', 0, '2016-06-15 22:13:48', 'efd85258-ce01-4adb-ab4d-168e42d1e51b', '2016-06-15 22:04:59', '2016-06-15 23:24:31', NULL, 'ZC27367D23F0180329', '325', NULL, '2',1,0),
 ('7', '1', '0', '2100.00', '0.00', '2100.00', '2100.00', '2016-05-17', NULL, '0.00', '0', '1', 0, '2016-06-15 22:13:48', 'efd85258-ce01-4adb-ab4d-168e42d1e51b', '2016-06-15 22:04:59', '2016-06-15 23:24:31', NULL, 'ZC27367D23F0180330', '325', NULL, '2',1,1);

SET FOREIGN_KEY_CHECKS=1;
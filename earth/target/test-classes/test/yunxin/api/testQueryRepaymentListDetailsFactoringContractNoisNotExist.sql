SET FOREIGN_KEY_CHECKS=0;
delete from financial_contract;
delete from asset_set;
delete from contract;
delete from app;
delete from customer;
delete from house;
delete from company;


INSERT INTO `financial_contract` (`id`, `asset_package_format`, `adva_matuterm`, `adva_start_date`, `contract_no`, `contract_name`, `app_id`, `company_id`, `adva_repo_term`, `thru_date`, `capital_account_id`, `financial_contract_type`, `loan_overdue_start_day`, `loan_overdue_end_day`, `payment_channel_id`, `ledger_book_no`, `financial_contract_uuid`) 
VALUES 
('1', '1', '7', '2016-06-08 00:00:00', 'DCF-NFQ-LR903', 'sasad', '1', '1', '3', '2016-09-05 00:00:00', '2', '1', '5', '90', '0', 'yunxin_nfq_ledger_book', 'd7b3b325-719a-42af-a129-0ac861f18ebe');



INSERT INTO `asset_set` (`id`, `guarantee_status`, `settlement_status`, `asset_fair_value`, `asset_principal_value`, `asset_interest_value`, `asset_initial_value`, `asset_recycle_date`, `confirm_recycle_date`, `refund_amount`, `asset_status`, `on_account_status`, `repayment_plan_type`, `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `comment`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `current_period`) 
VALUES 
('323', '1', '0', '1217.40', '0.00', '1200.00', '1200.00', '2016-05-17', NULL, '0.00', '0', '1', 0, '2016-06-15 22:13:45', 'e86cba4c-b447-4e05-89c2-4fb339b4f888', '2016-06-15 22:04:59', '2016-06-15 23:24:31', NULL, 'ZC27367D23EF4A36F4', '323', NULL, '1');



INSERT INTO `contract` (`id`, `unique_id`, `begin_date`, `contract_no`, `end_date`, `asset_type`, `month_fee`, `app_id`, `customer_id`, `house_id`, `actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`) 
VALUES 
('323', '1234567', '2016-04-17', '云信信2016-78-DK(ZQ2016042522479)', NULL, '1', '0.00', '1', '337', '337', NULL, '2016-06-15 22:04:59', '0.1560000000', '0', '0', '1', '2', '1200.00', '0.0005000000');

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `addressee`) 
VALUES
('1', 'nongfenqi', '', '', '', '农分期', '2', NULL);

INSERT INTO `customer` (`id`, `account`, `mobile`, `name`, `source`, `app_id`, `customer_uuid`) 
VALUES 
('337', NULL, NULL, '测试用户1', 'C74211', '1', '6120dce9-6214-433f-a7bd-acf9f89e2b7c');

INSERT INTO `house` (`id`, `address`, `app_id`)
VALUES 
('337', NULL, '1');

INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`) 
VALUES 
('2', '南京', '南京农纷期电子商务有限公司', '农分期');

SET FOREIGN_KEY_CHECKS=1;
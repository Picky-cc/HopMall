SET FOREIGN_KEY_CHECKS=0;

DELETE  from `loan_batch`;
DELETE  from `contract`;
DELETE  from `asset_package`;
DELETE  from `financial_contract`;
DELETE  from `asset_set`;


INSERT INTO `loan_batch` (`id`, `is_available`, `code`, `create_time`, `financial_contract_id`, `last_modified_time`, `loan_date`) 
VALUES 
('1', '', 'DCF-NFQ-LR903A 20160518 15:14:100', '2016-05-18 15:14:48', '1', '2016-05-19 18:43:20', '2016-05-19 18:43:20');

INSERT INTO `asset_package` (`id`, `is_available`, `create_time`, `contract_id`, `asset_package_no`, `financial_contract_id`, `loan_batch_id`) 
VALUES 
('1', NULL, NULL, '1', NULL, '2', '1');

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `end_date`, `asset_type`, `month_fee`, `app_id`, `customer_id`, `house_id`, `actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`,`active_version_no`) 
VALUES 
('1', '2016-04-17', '云信信2016-78-DK(ZQ2016042522479)', NULL, '1', '0.00', '2', '1', '1', NULL, '2016-05-18 15:14:48', '15.6000000000', '0', '0', '1', '2', '1200.00', '0.0005000000',1);

INSERT INTO `financial_contract` (`id`, `asset_package_format`, `adva_matuterm`, `adva_start_date`, `contract_no`, `contract_name`, `app_id`, `company_id`, `adva_repo_term`, `thru_date`, `capital_account_id`, `financial_contract_type`, `loan_overdue_start_day`, `loan_overdue_end_day`, `payment_channel_id`, `ledger_book_no`, `financial_contract_uuid`) 
VALUES 
('1', '0', '7', '2015-04-02 00:00:00', 'DCF-AMT-LR903A', '安美途', '1', '1', '0', '9999-01-01 00:00:00', '1', '0', '1', '10', '1', 'yunxin_anmeitu_ledger_book', '1');

INSERT INTO `asset_set` (`id`, `guarantee_status`, `settlement_status`, `asset_fair_value`, `asset_principal_value`, `asset_interest_value`, `asset_initial_value`, `asset_recycle_date`, `asset_status`, `on_account_status`, `repayment_plan_type`, `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `current_period`,`version_no`) 
VALUES 
('1', '1', '0', '1200.60', '0.00', '1200.00', '1200.00', '2016-05-17', '0', '1', 0, '2016-05-18 15:14:59', 'd4c2ccdf-c657-4df1-8a3d-2fdfa731f9ef', '2016-05-18 15:14:48', '2016-05-19 17:13:00', 'ZC27306906EAF2BB19', '1', NULL, '1',1);

SET FOREIGN_KEY_CHECKS=1;
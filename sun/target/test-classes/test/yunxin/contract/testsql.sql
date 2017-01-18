SET FOREIGN_KEY_CHECKS=0;


delete from asset_package;

delete from contract;

delete from financial_contract;

INSERT INTO `asset_package` (`id`, `is_available`, `create_time`, `contract_id`, `asset_package_no`, `financial_contract_id`, `loan_batch_id`) VALUES ('1', NULL, '2016-08-17 18:04:26', '1', NULL, '1', '2');

INSERT INTO `contract` (`id`, `unique_id`, `begin_date`, `contract_no`, `end_date`, `asset_type`, `month_fee`, `app_id`, `customer_id`, `house_id`, `actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`, `active_version_no`, `repayment_plan_operate_logs`) VALUES ('1', NULL, '2015-10-19', 'DKHD-001', '2016-10-19', NULL, NULL, '1', '1', '1', NULL, '2015-10-19 13:34:35', '0.0010000000', '1', '1', '12', '0', '1000.00', '0.0001000000', '1', NULL);

INSERT INTO `financial_contract` (`id`, `asset_package_format`, `adva_matuterm`, `adva_start_date`, `contract_no`, `contract_name`, `app_id`, `company_id`, `adva_repo_term`, `thru_date`, `capital_account_id`, `financial_contract_type`, `loan_overdue_start_day`, `loan_overdue_end_day`, `payment_channel_id`, `ledger_book_no`, `financial_contract_uuid`) VALUES ('1', NULL, '3', NULL, 'YX_AMT_001', NULL, '1', '1', '30', NULL, NULL, '0', '0', '0', '0', NULL, NULL);


SET FOREIGN_KEY_CHECKS=1;
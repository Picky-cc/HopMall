DELETE FROM `contract`;
INSERT INTO `contract` (`id`, `uuid`, `unique_id`, `begin_date`, `contract_no`, `end_date`, `asset_type`, `month_fee`, `app_id`, `customer_id`, `house_id`, `actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`, `active_version_no`, `repayment_plan_operate_logs`, `state`, `financial_contract_uuid`)
VALUES
	(36, '5b765309-3714-49c0-a092-c0c826053d9e', '26055d9e-8798-47dd-9b55-2c3f2e56223d', '2016-08-01', 'contract20160830', '2099-01-01', NULL, 0.00, 1, 38, 36, NULL, '2016-09-01 01:06:56', 0.1560000000, 0, 0, 1, 2, 10000.00, 0.0005000000, 1, NULL, 2, '69f8b0a1-001a-4182-ae06-1730ce297fd6');

DELETE FROM `asset_set`;
INSERT INTO `asset_set` (`id`, `guarantee_status`, `settlement_status`, `asset_fair_value`, `asset_principal_value`, `asset_interest_value`, `asset_initial_value`, `asset_recycle_date`, `confirm_recycle_date`, `refund_amount`, `asset_status`, `on_account_status`, `repayment_plan_type`, `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `comment`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `current_period`, `overdue_status`, `overdue_date`, `version_no`, `active_status`)
VALUES
	(48, 0, 0, 11000.00, 10000.00, 1000.00, 11000.00, DATE_ADD(now(),INTERVAL +1 DAY), NULL, 0.00, 0, 1, 0, now(), '93e3136d-e371-472b-bd14-9441b3b207d0', '2016-09-01 01:06:57', '2016-09-02 01:22:30', NULL, 'ZC274850A29B26503A', 36, '2016-09-02 01:22:30', 1, 0, NULL, 1, 0);


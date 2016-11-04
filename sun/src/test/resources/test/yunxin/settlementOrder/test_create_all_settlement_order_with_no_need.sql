delete From `asset_set`;
delete From `financial_contract`;
delete From `contract`;
delete from `settlement_order`;


INSERT INTO `asset_set` (`id`, `guarantee_status`, `asset_fair_value`, `asset_recycle_date`, `asset_status`, `on_account_status`, `repayment_plan_type`, `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `current_period`,`version_no`,`active_status`)
VALUES
	(1, 2, 2984.65, DATE_ADD(NOW(),INTERVAL -1 DAY), 1, 1, 0, '2016-04-07 21:49:04', '0d712307-a747-4a57-9fa5-792cf76c1a4c', '2016-04-06 16:43:47', '2016-04-07 21:49:04', 'test-contract-1-1', 1, NOW(), 1,1,1);

	
INSERT INTO `financial_contract` (`id`, `adva_matuterm`, `contract_no`, `app_id`, `company_id`, `adva_repo_term`) VALUES 
('1', 3, 'YX_AMT_001', 1, 1, 30);


INSERT INTO `contract` (`id`, `begin_date`, `contract_no`,`end_date`,`app_id`, `customer_id`, `house_id`,`actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`) VALUES 
(1, '2015-10-19', 'DKHD-001', '2016-10-19', 1, 1, 1, NULL, '2015-10-19 13:34:35', 0.001, 1, 1, 12, 0, 1000, 0.0001);

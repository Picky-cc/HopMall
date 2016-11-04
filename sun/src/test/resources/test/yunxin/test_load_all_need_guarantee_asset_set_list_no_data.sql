delete from `asset_set`;
INSERT INTO `asset_set` (`id`, `guarantee_status`, `asset_fair_value`,`asset_initial_value`, `asset_recycle_date`, `asset_status`, `on_account_status`, `repayment_plan_type`, `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `current_period`, `version_no`,`active_status`)
VALUES
	(1, 1, 2984.65,2983.16, DATE_ADD(NOW(),INTERVAL -1 DAY), 0, 1, 0, '2016-04-07 21:49:04', '0d712307-a747-4a57-9fa5-792cf76c1a4c', '2016-04-06 16:43:47', '2016-04-07 21:49:04', 'test-contract-1-1', 1, NULL, 1, 1,0),
	(2, 0, 2983.16,2983.16, DATE(NOW()), 0, 1, 0, NULL, '1676780d-15bf-4fe3-968c-62be2b02c3aa', '2016-04-06 16:43:47', NULL, 'test-contract-1-2', 1, NULL, 2, 1,0);

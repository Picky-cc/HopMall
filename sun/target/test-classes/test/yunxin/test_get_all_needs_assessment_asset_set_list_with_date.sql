delete from `asset_set`;
delete from `rent_order`;
INSERT INTO `asset_set` (`id`, `asset_fair_value`,`asset_initial_value`, `asset_recycle_date`, `asset_status`, `on_account_status`, `repayment_plan_type`, `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `version_no`,`active_status`)
VALUES
	(1, 1000.00,1000.00, '2015-10-19', 0, 1, 0, NULL, 'asset_uuid_0', '2015-10-19 13:34:35', '2015-10-19 13:34:35', 'DKHD-001-01', 1, NULL, 1,0),
	(2, 1000.00,1000.00, '2015-10-19', 0, 1, 0, NULL, 'asset_uuid_1', '2015-10-19 13:34:35', '2015-10-19 13:34:35', 'DKHD-002-01', 1, NULL, 1,0),
	(3, 1000.00,1000.00, '2015-10-19', 1, 1, 0, NULL, 'asset_uuid_2', '2015-10-19 13:34:35', '2015-10-19 13:34:35', 'DKHD-003-01', 2, NULL, 1,0);

INSERT INTO `rent_order` (`id`, `order_type`, `due_date`, `order_no`, `total_rent`, `customer_id`, `user_upload_param`, `modify_time`, `financial_contract_id`, `asset_set_id`, `create_time`, `clearing_status`, `executing_settling_status`)
VALUES
	(1, 0, '2016-06-03', 'order_no_1', 1000.00, 1, '', '2016-06-03 10:29:47', -1, 2, '2015-06-03 00:00:00', 0, 0),
	(2, 1, '2016-06-03', 'order_no_2_guarantee', 1000.00, 1, '', '2016-06-03 10:29:47', -1, 1, '2015-06-03 00:00:00', 0, 0);

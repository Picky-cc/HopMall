delete from `asset_set`;
delete from `rent_order`;
INSERT INTO `asset_set` (`id`, `asset_fair_value`,`asset_initial_value`, `asset_recycle_date`, `asset_status`, `on_account_status`, `repayment_plan_type`, `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `version_no`,`active_status`)
VALUES
	(1, 1000.00,1000.00, '2115-10-19', 0, 1, 0, NULL, 'asset_uuid_1', '2015-10-19 13:34:35', '2015-10-19 13:34:35', 'DKHD-001-01', 1, NULL, 1,0),
	(2, 1000.00,1000.00, '2115-10-19', 0, 1, 0, NULL, 'asset_uuid_2', '2015-10-19 13:34:35', '2015-10-19 13:34:35', 'DKHD-001-02', 2, NULL, 1,0);

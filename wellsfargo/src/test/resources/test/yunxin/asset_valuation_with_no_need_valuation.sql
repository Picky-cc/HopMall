delete from `contract`;
delete from `asset_set`;
delete from `asset_valuation_detail`;

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `end_date`, `month_fee`, `app_id`, `customer_id`, `house_id`, `actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`)
VALUES
	(1, '2115-10-19', 'DKHD-001', '2116-10-19', 12000.00, 1, 1, 1, NULL, '2115-10-19 13:34:35', 0.0010000000, 1, 1, 10, 0, 120000, 0.0005000000),
	(2, '2115-10-19', 'DKHD-002', '2116-10-19', 12000.00, 1, 1, 1, NULL, '2115-10-19 13:34:36', 0.0010000000, 1, 1, 10, 0, 120000, 0.0005000000);


INSERT INTO `asset_set` (`id`, `asset_fair_value`,`asset_initial_value`, `asset_recycle_date`, `asset_status`, `asset_uuid`, `create_time`, `last_modified_time`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `version_no`,`active_status`)
VALUES
	(1, 12000.00,12000.00, '2115-10-19 00:00:00', 0, 'asset_uuid_1', '2015-10-19 13:34:35', '2015-10-19 13:34:35', 'DKHD-001-01', 1, NULL,1,0),
	(2, 12000.00,12000.00, '2115-10-19 00:00:00', 0, 'asset_uuid_2', '2015-10-19 13:34:35', '2015-10-19 13:34:35', 'DKHD-002-01', 2, NULL,1,0);

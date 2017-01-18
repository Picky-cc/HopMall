delete from `asset_set`;
delete from `asset_valuation_detail`;

INSERT INTO `asset_set` (`id`, `asset_fair_value`,`asset_initial_value`, `asset_recycle_date`, `asset_status`, `asset_uuid`, `create_time`, `last_modified_time`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`)
VALUES
	(1, 1000.00,1000.00, '2015-10-19 13:34:35', 0, 'asset_uuid_0', '2015-10-19 13:34:35', '2015-10-19 13:34:35', 'DKHD-001-01', 1, NULL);

INSERT INTO `asset_valuation_detail` (`id`, `amount`, `asset_value_date`, `created_date`, `subject`, `asset_set_id`)
VALUES
	(1, 10000.00, '2016-03-15 00:00:00', '2016-03-15', 0, 1);
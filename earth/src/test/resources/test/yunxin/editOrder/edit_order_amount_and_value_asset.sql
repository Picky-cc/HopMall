DELETE FROM `contract`;
DELETE FROM `asset_set`;
DELETE FROM `asset_valuation_detail`;
DELETE FROM `rent_order`;
DELETE FROM `financial_contract`;
DELETE FROM `payment_channel`;
DELETE FROM `asset_package`;
delete from `customer`;

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `end_date`, `month_fee`, `app_id`, `customer_id`, `house_id`, `actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`,asset_type)
VALUES
	(1, '2016-03-01', 'DKHD-001', '2017-03-15', 10000.00, 1, 1, 1, NULL, '2016-03-15 13:34:35', 0.0010000000, 1, 1, 10, 0, 100000, 0.0005000000,1);

INSERT INTO `asset_package` (`id`, `is_available`,  `contract_id`, `asset_package_no`, `financial_contract_id`) VALUES 
('1', 1, 1, 'no1', '1');

INSERT INTO `customer` (`id`, `account`, `mobile`, `name`, `source`, `app_id`, `customer_uuid`) VALUES 
('1', NULL, NULL, '测试用户1', 'C74211', '2', 'customer_uuid_1');


INSERT INTO `financial_contract` (`id`, `adva_matuterm`, `contract_no`, `app_id`, `company_id`,   `adva_repo_term`, `payment_channel_id`) VALUES 
('1', 3, 'YX_AMT_001', 1, 1, 30,1),
('2', 3, 'YX_AMT_002', 2, 1, 30,1);

INSERT INTO `payment_channel` (`id`, `channel_name`, `user_name`, `user_password`, `merchant_id`, `cer_file_path`, `pfx_file_path`, `pfx_file_key`, `payment_channel_type`, `api_url`, `from_date`, `thru_date`) VALUES 
(1, 'channel_name', 'user_name', 'user_password', 'merchant_id', 'cer_file_path', 'pfx_file_path', 'pfx_file_key', 0, 'api_url', NULL, NULL);


INSERT INTO `asset_set` (`id`, `asset_fair_value`,`asset_initial_value`, `asset_recycle_date`, `asset_status`, `asset_uuid`, `create_time`, `last_modified_time`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`,`version_no`)
VALUES
	(1, 10010.00,10000.00,'2016-05-16', 0, 'asset_uuid_1', '2016-05-16 13:34:35', '2016-05-16 13:34:35', 'DKHD-001-01', 1, NULL,1),
	(2, 10000.00,10000.00,'2016-05-17', 0, 'asset_uuid_2', '2016-05-17 13:34:35', '2016-05-17 13:34:35', 'DKHD-002-01', 2, NULL,1);

INSERT INTO `asset_valuation_detail` (`id`, `amount`, `asset_value_date`, `created_date`, `subject`, `asset_set_id`)
VALUES
	(1, 10000.00, '2016-05-16', '2016-05-16', 0, 1),
	(2, 10000.00, '2016-05-17', '2016-05-17', 0, 2),
	(3, 5.00, '2016-05-17', '2016-05-17', 1, 1),
	(4, 5.00, '2016-05-18', '2016-05-18', 1, 1),
	(5, 5.00, '2016-05-18', '2016-05-18', 1, 2);

INSERT INTO `rent_order` (`id`, `order_type`, `due_date`, `order_no`, `payout_time`, `total_rent`, `customer_id`, `user_upload_param`, `modify_time`, `financial_contract_id`, `repayment_bill_id`, `asset_set_id`, `create_time`, `clearing_status`, `executing_settling_status`) VALUES 
('1', '0', Date_add(now(),INTERVAL -2 DAY), 'order_no_1', NULL, '10000.00', '1', '[1]', Date_add(now(),INTERVAL -2 DAY), '2', 'repayment_bill_id_1', '1', Date_add(now(),INTERVAL -2 DAY), '1', '2'),
('2', '0', Date_add(now(),INTERVAL -1 DAY), 'order_no_2', NULL, '10000.00', '1', '[1,3]', Date_add(now(),INTERVAL -1 DAY), '2', 'repayment_bill_id_2', '1', Date_add(now(),INTERVAL -1 DAY), '0', '3'),
('3', '0', now(), 'order_no_3', NULL, '10010.00', '1', '[1,3,4]',now(), '2', 'repayment_bill_id_3', '1', now(), '0', '0');



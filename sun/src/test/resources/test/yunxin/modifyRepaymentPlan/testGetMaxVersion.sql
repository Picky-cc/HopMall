SET FOREIGN_KEY_CHECKS=0;

delete from `asset_set`;
delete from `financial_contract`;
delete from `contract`;
delete from `asset_valuation_detail`;


INSERT INTO `financial_contract` (`id`, `adva_matuterm`, `contract_no`, `app_id`, `company_id`, `adva_repo_term`) VALUES 
('1', 3, 'YX_AMT_001', 1, 1, 30);

-- asset3作废
INSERT INTO `asset_set` (`id`, `asset_fair_value`,`asset_initial_value`, `asset_recycle_date`, `asset_status`, `asset_uuid`, `create_time`, `last_modified_time`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `version_no`,`active_status`) VALUES 
('1', 1010,1000, '2015-10-19', 0, 'asset_uuid_1', '2015-10-19 13:34:35', '2015-10-19 13:34:35', 'DKHD-001-01', 1, '2015-10-19 13:34:35', 1,'0'),
('2', 1000,1000, '2015-10-20', 0, 'asset_uuid_2', '2015-10-20 13:34:35', '2015-10-20 13:34:35', 'DKHD-001-02', 1, '2015-10-19 13:34:35', 2,'0'),
('3', 1010,1000, '2015-10-19', 0, 'asset_uuid_3', '2015-10-19 13:34:35', '2015-10-19 13:34:35', 'DKHD-001-01', 1, '2015-10-19 13:34:35', 3,'1');

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`,`end_date`,`app_id`, `customer_id`, `house_id`,`actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`,`active_version_no`) VALUES 
(1, '2015-10-19', 'DKHD-001', '2016-10-19', 1, 1, 1, NULL, '2015-10-19 13:34:35', 0.001, 1, 1, 12, 0, 1000, 0.0001,2);

INSERT INTO `asset_valuation_detail` (`id`, `amount`, `asset_value_date`, `created_date`, `subject`, `asset_set_id`) VALUES 
('1', '1000.00', '2016-03-14 19:46:38', '2016-03-14', '0', '1'),
('2', '10.00', '2016-03-15 19:47:16', '2016-03-15', '1', '1');


SET FOREIGN_KEY_CHECKS=1;

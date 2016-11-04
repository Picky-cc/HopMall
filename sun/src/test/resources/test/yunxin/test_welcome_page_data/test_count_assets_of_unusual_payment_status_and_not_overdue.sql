SET FOREIGN_KEY_CHECKS=0;

delete from `asset_package`;
delete from `rent_order`;
delete from `asset_set`;
delete from `financial_contract`;
delete from `contract`;
delete from `app`;
delete from `customer`;
delete from `asset_valuation_detail`;
delete from `house`;
delete from `company`;
delete from `payment_channel`;
delete from `principal`;

INSERT INTO `asset_package` (`id`, `create_time`, `contract_id`, `asset_package_no`, `financial_contract_id`) VALUES 
('1', '2015-10-19 13:34:35', '1','no1','1'),
('2', '2015-10-19 13:34:35', '2','no2','2');

INSERT INTO `company` (`id`, `short_name`) VALUES 
(1, 'anmeitu'),
(2, '租房宝');

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `addressee`) VALUES 
('1', 'anmeitu', '11111db75ebb24fa0993f4fa25775023', '\0', 'http://e.zufangbao.cn', '安美途', '1', NULL),
('2', 'test4Zufangbao', '2e85ae999845f25faf8ea7b514ee0aca', '\0', 'http://e.zufangbao.cn', '租房宝测试账号', '2', NULL);

INSERT INTO `customer` (`id`, `account`, `mobile`, `name`, `source`, `app_id`) VALUES 
('1', '01091081XXXXX', '13777846666', '王二', 'D001', '1'),
('2', '01091082XXXXX', '13777846666', '王二', 'D002', '1');

INSERT INTO `house` (`id`, `address`, `app_id`) VALUES 
('1', 'V001-test', '1');



INSERT INTO `financial_contract` (`id`, `adva_matuterm`, `contract_no`, `app_id`, `company_id`,   `adva_repo_term`, `payment_channel_id`,`financial_contract_uuid`) VALUES 
('1', 3, 'YX_AMT_001', 1, 1, 30,1,'uuid1'),
('2', 3, 'YX_AMT_002', 2, 1, 30,1,'uuid2');

INSERT INTO `payment_channel` (`id`, `channel_name`, `user_name`, `user_password`, `merchant_id`, `cer_file_path`, `pfx_file_path`, `pfx_file_key`, `payment_channel_type`, `api_url`, `from_date`, `thru_date`) VALUES 
(1, 'channel_name', 'user_name', 'user_password', 'merchant_id', 'cer_file_path', 'pfx_file_path', 'pfx_file_key', 0, 'api_url', NULL, NULL);

-- asset1已收， asset3为处理中，asset2为异常

INSERT INTO `asset_set` (`id`, `asset_fair_value`,`asset_initial_value`, `asset_recycle_date`, `asset_status`, `asset_uuid`, `create_time`, `last_modified_time`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`,`version_no`,`active_status`,`guarantee_status`,`overdue_status`) VALUES 
('1', 1010,1000, DATE_ADD(NOW(),INTERVAL -1 DAY), 0, 'asset_uuid_1', '2015-10-19 13:34:35', '2015-10-19 13:34:35', 'DKHD-001-01', 1, NOW(),1,0,0,0),
('2', 1000,1000, DATE_ADD(NOW(),INTERVAL -5 DAY), 0, 'asset_uuid_2', '2015-10-20 13:34:35', '2015-10-20 13:34:35', 'DKHD-001-02', 1, NULL,1,0,0,0),
('3', 1000,1000, now(), 0, 'asset_uuid_3', '2015-10-20 13:34:35', '2015-10-20 13:34:35', 'DKHD-002-01', 2, NULL,1,0,0,0),
('4', 1000,1000, DATE_ADD(NOW(),INTERVAL 5 DAY), 0, 'asset_uuid_4', '2015-10-20 13:34:35', '2015-10-20 13:34:35', 'DKHD-002-02', 2, NOW(),1,0,0,0);

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`,`end_date`,`app_id`, `customer_id`, `house_id`,`actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`,`month_fee`,`active_version_no`,`asset_type`,`financial_contract_uuid`) VALUES 
(1, '2015-10-19', 'DKHD-001', '2016-10-19', 1, 1, 1, NULL, '2015-10-19 13:34:35', 0.001, 1, 1, 12, 0, 10000, 0.0001,1000,1,1,'uuid1'),
(2, '2015-10-20', 'DKHD-002', '2016-10-19', 2, 1, 1, NULL, '2015-10-19 13:34:35', 0.001, 1, 1, 12, 0, 10000, 0.0001,1000,1,1,'uuid2');


INSERT INTO `rent_order` (`id`, `due_date`, `order_no`, `payout_time`, `total_rent`, `customer_id`, `user_upload_param`, `modify_time`, `asset_set_id`, `create_time`,`clearing_status`,`executing_settling_status`,`financial_contract_id`) VALUES
(1, '2016-10-09', 'DKHD-001-01-20160307', '2016-10-19 13:34:35',1000, '1','', '2016-01-19', 1, '2015-10-19', 0,0,1),
(2, '2016-10-11', 'DKHD-001-01-20160308', '2016-10-19 13:34:35',1000, '1','', '2016-02-19', 1, '2015-10-19',0,1,1);


INSERT INTO `asset_valuation_detail` (`id`, `amount`, `asset_value_date`, `created_date`, `subject`, `asset_set_id`) VALUES 
('1', '1000.00', '2016-03-14 19:46:38', '2016-03-14', '0', '1'),
('2', '10.00', '2016-03-15 19:47:16', '2016-03-15', '1', '1'),
('3', '1000.00', '2016-03-14 19:46:38', '2016-03-14', '0', '2');

INSERT INTO `principal` (`id`, `authority`, `name`, `password`, `start_date`, `thru_date`) VALUES 
('1', 'ROLE_SUPER_USER', 'zhushiyun', 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL);



SET FOREIGN_KEY_CHECKS=1;

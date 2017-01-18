SET FOREIGN_KEY_CHECKS=0;

delete from contract_account;
delete from transfer_application;
delete from rent_order;
delete from asset_set;
delete from principal;

INSERT INTO `contract_account` (`id`, `pay_ac_no`, `bankcard_type`, `contract_id`, `payer_name`,`bank`, `bind_id`) VALUES 
(1, 'pay_ac_no_1', 0, 1, 'payer_name_1', '中国银行滨江支行','band_id_1');

INSERT INTO `transfer_application` (`id`, `amount`, `batch_pay_record_id`, `comment`, `create_time`, `creator_id`, `deduct_status`, `payment_way`, `executing_deduct_status`, `transfer_application_no`, `deduct_time`, `last_modified_time`, `contract_account_id`, `order_id`, `union_pay_order_no`) 
VALUES ('1', '1.00', '1', '', '2016-03-31 19:10:46', NULL, '0', '0', '1', 'zxcvbnm', NULL, '2016-10-10 19:10:46', '1', '2', NULL);

INSERT INTO `rent_order` (`id`, `due_date`, `order_no`, `payout_time`, `total_rent`, `customer_id`, `user_upload_param`, `modify_time`, `asset_set_id`, `create_time`,`clearing_status`,`executing_settling_status`) VALUES
('2', Date(NOW()), 'DKHD-001-01-20160308', '2016-10-19 13:34:35',1000, '1','', '2016-10-19', 1, '2015-10-19',0,1);


INSERT INTO `asset_set` (`id`, `asset_fair_value`, `asset_recycle_date`, `asset_status`, `asset_uuid`, `create_time`, `last_modified_time`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`,`version_no`) VALUES 
('1', 1010, '2015-10-19 13:34:35', 0, 'asset_uuid_1', '2015-10-19 13:34:35', '2015-10-19 13:34:35', 'DKHD-1-01', 1, '2015-10-19 13:34:35',1);

INSERT INTO `principal` (`id`, `authority`, `name`, `password`, `start_date`, `thru_date`) VALUES ('1', 'ROLE_SUPER_USER', 'root', 'befd2450f81f88ecc5fbcc4c1f97f0b4', NULL, '2015-12-01 15:53:30');

SET FOREIGN_KEY_CHECKS=1;
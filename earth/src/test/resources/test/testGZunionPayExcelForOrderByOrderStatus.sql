SET FOREIGN_KEY_CHECKS=0;

delete from contract_account;
delete from transfer_application;
delete from rent_order;
delete from asset_set;
delete from principal;
delete from batch_pay_record;

INSERT INTO `contract_account` (`id`, `pay_ac_no`, `bankcard_type`, `contract_id`, `payer_name`, `bank`, `bind_id`) 
VALUES 
('795', '01091081XXXXX', '0', '1682', '王二', '中国银行滨江支行', '123456789x');

INSERT INTO `transfer_application` (`id`, `amount`,  `batch_pay_record_id`, `comment`, `create_time`, `creator_id`, `deduct_status`, `payment_way`, `executing_deduct_status`, `transfer_application_no`, `deduct_time`, `execute_type`, `funding_src_type`, `last_modified_time`, `src_bill_id`, `status`, `transfer_usage_type`, `trigger_type`, `contract_account_id`, `order_id`, `factoring_contract_id`, `payer_account_id`, `payment_institution_id`, `receive_account_id`, `union_pay_order_no`)
VALUES 
('1', '1.00', '1', '', '2016-03-31 19:10:46', NULL, '0', '0', '0', 'DKHD-001-01-20160308-1910', '2016-03-31 19:10:46', NULL, NULL, '2016-03-31 19:10:46', NULL, NULL, NULL, NULL, '795', '2', NULL, NULL, NULL, NULL, NULL);

INSERT INTO `batch_pay_record` (`id`, `amount`, `apply_time`, `creator_id`, `modify_time`, `operator_id`, `pay_status`, `pay_time`, `remark`, `request_no`, `serial_no`, `payer_account_id`, `payment_institution_id`, `receive_account_id`, `confirm_time`,  `request_data`, `response_data`, `bank_corporate_account_id`)
VALUES 
('1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '123456789', NULL, NULL, '12', NULL,  NULL, NULL, NULL);


INSERT INTO `rent_order` (`id`, `due_date`, `order_no`, `payout_time`, `total_rent`, `contract_id`, `customer_id`, `user_upload_param`, `modify_time`, `unique_bill_id`, `asset_set_id`, `create_time`,`clearing_status`,`executing_settling_status`) VALUES
('2', Date(NOW()), 'DKHD-001-01-20160308', '2016-10-19 13:34:35',1000, 1, '1','', '2016-10-19', 'unique_bill_id_2', 1, '2015-10-19',0,1);


INSERT INTO `asset_set` (`id`, `asset_fair_value`, `asset_recycle_date`, `asset_status`, `asset_uuid`, `create_time`, `last_modified_time`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`) VALUES 
('1', 1010, '2015-10-19 13:34:35', 0, 'asset_uuid_1', '2015-10-19 13:34:35', '2015-10-19 13:34:35', 'DKHD-1-01', 1, '2015-10-19 13:34:35');

INSERT INTO `principal` (`id`, `authority`, `name`, `password`, `start_date`, `thru_date`) VALUES ('1', 'ROLE_SUPER_USER', 'root', 'befd2450f81f88ecc5fbcc4c1f97f0b4', NULL, '2015-12-01 15:53:30');


INSERT INTO `galaxy_yunxin`.`payment_channel` (`id`, `channel_name`, `user_name`, `user_password`, `merchant_id`, `cer_file_path`, `pfx_file_path`, `pfx_file_key`, `payment_channel_type`, `api_url`, `from_date`, `thru_date`) VALUES ('1', '广东银联安美途', 'operator', 'operator', '001053110000001', 'src/main/resources/certificate/gzdsf.cer', 'src/main/resources/certificate/ORA@TEST1.pfx', '123456', '0', 'http://59.41.103.98:333/gzdsf/ProcessServlet', NULL, NULL);

SET FOREIGN_KEY_CHECKS=1;
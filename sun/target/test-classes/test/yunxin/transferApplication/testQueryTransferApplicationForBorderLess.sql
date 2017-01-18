SET FOREIGN_KEY_CHECKS=0;

delete from contract_account;
delete from transfer_application;
delete from rent_order;
delete from asset_set;
delete from principal;
delete from batch_pay_record;
delete from financial_contract;

INSERT INTO `contract_account` (`id`, `pay_ac_no`, `bankcard_type`, `contract_id`, `payer_name`, `bank`, `bind_id`) 
VALUES 
('795', '01091081XXXXX', '0', '1682', '王二', '中国银行滨江支行', '123456789x');

INSERT INTO `transfer_application` (`id`, `amount`,  `batch_pay_record_id`, `comment`, `create_time`, `creator_id`, `deduct_status`, `payment_way`, `executing_deduct_status`, `transfer_application_no`, `deduct_time`, `last_modified_time`, `contract_account_id`, `order_id`, `union_pay_order_no`)
VALUES 
('1', '1.00', '1', '', '2016-03-31 19:10:46', NULL, '0', '0', '0', 'DKHD-001-01-20160308-1910', '2016-03-31 19:10:46', '1999-04-01 00:00:00', '795', '2', NULL);

INSERT INTO `batch_pay_record` (`id`, `amount`)
VALUES 
('1', '123456789');

INSERT INTO `financial_contract` (`id`, `adva_matuterm`, `contract_no`, `app_id`, `company_id`,`adva_repo_term`) VALUES 
('1', 3, 'YX_AMT_001', 1, 1, 30);

INSERT INTO `rent_order` (`id`, `due_date`, `order_no`, `payout_time`, `total_rent`, `customer_id`, `user_upload_param`, `modify_time`, `asset_set_id`, `create_time`,`clearing_status`,`executing_settling_status`,`financial_contract_id`) VALUES
('2', Date(NOW()), 'DKHD-001-01-20160308', '2016-10-19 13:34:35',1000, '1','', '2016-10-19', 1, '2015-10-19',0,1,1);


INSERT INTO `asset_set` (`id`, `asset_fair_value`,`asset_initial_value`, `asset_recycle_date`, `asset_status`, `asset_uuid`, `create_time`, `last_modified_time`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`) VALUES 
('1', 1010,1000, '2015-10-19 13:34:35', 0, 'asset_uuid_1', '2015-10-19 13:34:35', '2015-10-19 13:34:35', 'DKHD-1-01', 1, '2015-10-19 13:34:35');

INSERT INTO `principal` (`id`, `authority`, `name`, `password`, `start_date`, `thru_date`) VALUES ('1', 'ROLE_SUPER_USER', 'root', 'befd2450f81f88ecc5fbcc4c1f97f0b4', NULL, '2015-12-01 15:53:30');

SET FOREIGN_KEY_CHECKS=1;
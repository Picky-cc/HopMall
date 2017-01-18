SET FOREIGN_KEY_CHECKS=0;

delete from contract_account;
delete from transfer_application;
delete from rent_order;
delete from asset_set;
delete from contract;
delete from principal;
delete from batch_pay_record;
delete from account;
delete from asset_package;
delete from financial_contract;
delete from loan_batch;

INSERT INTO `asset_package` (`id`, `is_available`, `create_time`, `contract_id`, `asset_package_no`, `financial_contract_id`, `loan_batch_id`) VALUES ('2', NULL, NULL, '2', NULL, '1', '1');

INSERT INTO `contract_account` (`id`, `pay_ac_no`, `bankcard_type`, `contract_id`, `payer_name`, `bank`, `bind_id`) 
VALUES 
('2', '01091081XXXXX', '0', '2', '王二', '中国银行滨江支行', '123456789x');

INSERT INTO `account` (`id`, `account_name`, `account_no`, `bank_name`, `alias`, `attr`) VALUES
('2', 'name', '123', 'name', NULL, NULL);

INSERT INTO `transfer_application` (`id`, `transfer_application_uuid`, `amount`, `batch_pay_record_id`, `comment`, `create_time`, `creator_id`, `deduct_status`, `payment_way`, `executing_deduct_status`, `transfer_application_no`, `order_id`, `deduct_time`, `last_modified_time`, `contract_account_id`, `union_pay_order_no`) VALUES 
('1', 'd046021fa9604c3a90908a11bf234362', '1210.20', '1', '签名工具类异常！', '2016-06-03 11:39:30', NULL, '0', '0', '0', '2735B6CA6FCADF73', '2', NULL, '2016-06-03 11:39:30', '2', '2735B6CA6FCADF73');

INSERT INTO `financial_contract` (`id`, `asset_package_format`, `adva_matuterm`, `adva_start_date`, `contract_no`, `contract_name`, `app_id`, `company_id`, `adva_repo_term`, `thru_date`, `capital_account_id`, `financial_contract_type`, `loan_overdue_start_day`, `loan_overdue_end_day`, `payment_channel_id`, `ledger_book_no`) VALUES ('1', '1', '7', '2015-04-02 00:00:00', 'DCF-NFQ-LR903A', '农分期', '1', '1', '0', '9999-01-01 00:00:00', '2', '1', '0', '0', '2', 'yunxin_nfq_ledger_book');

INSERT INTO `rent_order` (`id`, `order_type`, `due_date`, `order_no`, `payout_time`, `total_rent`, `customer_id`, `user_upload_param`, `modify_time`, `financial_contract_id`, `repayment_bill_id`, `asset_set_id`, `create_time`, `clearing_status`, `executing_settling_status`, `comment`) VALUES
('2', '0', '2016-06-03', 'JS2735B6BE27ED5EA8', NULL, '1210.20', '2', '[19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36]', '2016-06-03 11:39:30', '1', 'c4e6c762937140d580161534e96b883c', '2', '2016-06-03 11:31:25', '0', '1', NULL);

INSERT INTO `asset_set` (`id`, `guarantee_status`, `settlement_status`, `asset_fair_value`, `asset_principal_value`, `asset_interest_value`, `asset_initial_value`, `asset_recycle_date`, `confirm_recycle_date`, `refund_amount`, `asset_status`, `on_account_status`, `repayment_plan_type`, `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `comment`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `current_period`) VALUES 
('2', '1', '0', '1210.20', '0.00', '1200.00', '1200.00', '2016-05-17', NULL, '0.00', '0', '1', 0, '2016-06-03 11:31:18', '27146cc6-050b-43d9-b151-ca9bdc380781', '2016-06-02 18:04:51', '2016-06-03 14:15:00', NULL, 'ZC2735AAC17896B3D3', '2', NULL, '1');

INSERT INTO `principal` (`id`, `authority`, `name`, `password`, `start_date`, `thru_date`) VALUES ('1', 'ROLE_SUPER_USER', 'root', 'befd2450f81f88ecc5fbcc4c1f97f0b4', NULL, '2015-12-01 15:53:30');

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `end_date`, `asset_type`, `month_fee`, `app_id`, `customer_id`, `house_id`, `actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`) VALUES 
('2', '2016-04-17', '云信信2016-78-DK(ZQ2016042522479)', NULL, '1', '0.00', '1', '1', '1', NULL, '2016-06-02 18:04:51', '0.1560000000', '0', '0', '1', '2', '1200.00', '0.0005000000');

INSERT INTO `payment_channel` (`id`, `channel_name`, `user_name`, `user_password`, `merchant_id`, `cer_file_path`, `pfx_file_path`, `pfx_file_key`, `payment_channel_type`, `api_url`, `from_date`, `thru_date`) VALUES ('2', '农分期-广银联(代收)', 'operator', 'operator', '001053110000001', '/data/webapps/tomcat-src/main/resources/certificate/gzdsf.cer', '/data/webapps/tomcat-src/main/resources/certificate/ORA@TEST1.pfx', '123456', '0', 'http://59.41.103.98:333/gzdsf/ProcessServlet', NULL, NULL);

INSERT INTO `loan_batch` (`id`, `is_available`, `code`, `create_time`, `financial_contract_id`, `last_modified_time`, `loan_date`) VALUES ('1', '', 'DCF-NFQ-LR903A 20160602 18:04:156', '2016-06-02 18:04:51', '1', '2016-06-13 15:50:03', '2016-06-13 15:50:03');

SET FOREIGN_KEY_CHECKS=1;
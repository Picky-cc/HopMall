SET FOREIGN_KEY_CHECKS=0;

delete from `principal`;
delete from offline_bill;
delete from source_document;
delete from journal_voucher;
delete from rent_order;
delete from business_voucher;
delete from contract;
delete from asset_package;
delete from customer;
delete from financial_contract;
delete from payment_channel;
delete from asset_set;


INSERT INTO `principal` (`id`, `authority`, `name`, `password`, `start_date`, `thru_date`) VALUES 
('1', 'ROLE_SUPER_USER', 'test', 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL);

INSERT INTO `offline_bill` (`id`, `amount`, `bank_show_name`, `comment`, `create_time`, `is_delete`, `last_modified_time`, `offline_bill_no`, `offline_bill_status`, `offline_bill_uuid`, `serial_no`, `payer_account_name`, `payer_account_no`)
VALUES 
('1', '1000.00', '中国银行', '备注', '2016-04-01 14:22:58', '\0', '2016-04-01 14:23:27', 'BC00001', '1', 'offline_bill', 'serial_no', 'payer_account_name', 'account_no');


INSERT INTO `source_document` (`id`, `company_id`, `source_document_uuid`, `source_document_type`, `create_time`, `issued_time`, `source_document_status`, `source_account_side`, `booking_amount`, `outlier_document_uuid`, `outlier_trade_time`, `outlier_counter_party_account`, `outlier_counter_party_name`, `outlier_account`, `outlie_account_name`, `outlier_account_id`, `outlier_company_id`, `outlier_serial_global_identity`, `outlier_memo`, `outlier_amount`, `outlier_settlement_modes`, `outlier_breif`, `outlier_account_side`, `appendix`, `first_outlier_doc_type`, `second_outlier_doc_type`, `third_outlier_doc_type`, `currency_type`, `audit_status`) 
VALUES 
('1', '1', 'source_document_uuid_1', '1', '2016-04-21 17:19:23', '2016-04-15 17:19:26', '1', '1', '0.00', 'offline_bill', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'offline_bill', NULL, NULL, NULL, '1');

INSERT INTO `journal_voucher` (`id`, `account_side`, `bank_identity`, `billing_plan_uuid`, `booking_amount`, `business_voucher_type_uuid`, `business_voucher_uuid`, `cash_flow_amount`, `cash_flow_breif`, `cash_flow_channel_type`, `cash_flow_serial_no`, `cash_flow_uuid`, `checking_level`, `company_id`, `completeness`, `counter_party_account`, `counter_party_name`, `journal_voucher_uuid`, `notification_identity`, `notification_memo`, `notification_record_uuid`, `notified_date`, `settlement_modes`, `source_document_amount`, `source_document_breif`, `source_document_cash_flow_serial_no`, `source_document_counter_party_uuid`, `source_document_identity`, `source_document_uuid`, `status`, `trade_time`, `batch_uuid`, `created_date`, `source_document_counter_party_account`, `source_document_counter_party_name`, `issued_time`) VALUES 
('1', '1', NULL, 'repayment_bill_id_1', '1000.00', 'ef8d1fbd-3bcc-42a1-b021-912133d76744', 'f709b5ae-000c-4b69-90ac-63dc3eb933c0', NULL, NULL, NULL, NULL, NULL, '1', '1', '0', NULL, NULL, '09d50a0f-4dfb-442d-920f-9287b74c246d', NULL, NULL, NULL, '2015-03-30 16:11:50', '3', '10.00', '', 'serial_no_1', NULL, 'batch_pay_record_uuid_2', 'source_document_uuid_1', '1', '2015-03-30 16:11:50', NULL, '2016-05-18 14:44:10', 'pay_ac_no_1', 'payer_name_1', NULL),
('2', '1', NULL, 'repayment_bill_id_2', '1200.00', 'ef8d1fbd-3bcc-42a1-b021-912133d76744', '4ca195f7-1a09-41e1-a536-84bd65425449', NULL, NULL, NULL, NULL, NULL, '1', '1', '0', NULL, NULL, '52604110-7b2d-43c8-bc35-f8629ef22d81', NULL, NULL, NULL, '2016-05-20 23:01:00', '3', '1200.00', '', '54', NULL, '9dea7e6de7b6454fafa4b6b0a705b359', 'source_document_uuid_1', '1', '2016-05-20 23:01:00', NULL, '2016-05-20 23:02:05', '44', '12', NULL);

INSERT INTO `rent_order` (`id`, `order_type`, `due_date`, `order_no`, `payout_time`, `total_rent`, `customer_id`, `user_upload_param`, `modify_time`, `financial_contract_id`, `repayment_bill_id`, `asset_set_id`, `create_time`, `clearing_status`, `executing_settling_status`, `comment`) VALUES 
('1', '0', '2015-03-18', 'DKHD-001-02-20160308', '2016-10-19 13:34:35', '10.00', '1', '', '2016-10-19 00:00:00', '1', 'repayment_bill_id_1', '2', '2015-10-19 00:00:00', '0', '0', NULL),
('2', '0', '2016-05-18', 'JS27306907163CE4A7', NULL, '2401.20', '3', '[5,6]', '2016-05-18 15:15:28', '2', 'repayment_bill_id_2', '3', '2016-05-18 15:14:59', '0', '3', NULL);

INSERT INTO `business_voucher` (`id`, `account_id`, `account_side`, `billing_plan_breif`, `billing_plan_type_uuid`, `billing_plan_uuid`, `business_voucher_status`, `business_voucher_type_uuid`, `business_voucher_uuid`, `company_id`, `receivable_amount`, `settlement_amount`, `tax_invoice_uuid`) VALUES 
('1', '0', '1', '', '789e2d98-1ddb-42ce-b0b8-44fe3d1b84c3', 'repayment_bill_id_1', '1', 'ef8d1fbd-3bcc-42a1-b021-912133d76744', '4fd7e8cb-d8bc-46a7-bf9c-894b29138863', '1', '1200.00', '122.00', NULL),
('2', '0', '1', '', '789e2d98-1ddb-42ce-b0b8-44fe3d1b84c3', 'repayment_bill_id_2', '2', 'ef8d1fbd-3bcc-42a1-b021-912133d76744', '4ca195f7-1a09-41e1-a536-84bd65425449', '1', '1200.00', '1200.00', NULL);

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `end_date`, `month_fee`, `app_id`, `customer_id`, `house_id`, `actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`)
VALUES
	(1, '2016-03-01', 'DKHD-001', '2017-03-15', 10000.00, 1, 1, 1, NULL, '2016-03-15 13:34:35', 0.0010000000, 1, 1, 10, 0, 100000, 0.0005000000);

INSERT INTO `asset_package` (`id`, `is_available`,  `contract_id`, `asset_package_no`, `financial_contract_id`) VALUES 
('1', 1, 1, 'no1', '1');

INSERT INTO `customer` (`id`, `account`, `mobile`, `name`, `source`, `app_id`, `customer_uuid`) VALUES 
('1', NULL, NULL, '测试用户1', 'C74211', '2', 'customer_uuid_1');


INSERT INTO `financial_contract` (`id`, `adva_matuterm`, `contract_no`, `app_id`, `company_id`,   `adva_repo_term`, `payment_channel_id`) VALUES 
('1', 3, 'YX_AMT_001', 1, 1, 30,1),
('2', 3, 'YX_AMT_002', 2, 1, 30,1);

INSERT INTO `payment_channel` (`id`, `channel_name`, `user_name`, `user_password`, `merchant_id`, `cer_file_path`, `pfx_file_path`, `pfx_file_key`, `payment_channel_type`, `api_url`, `from_date`, `thru_date`) VALUES 
(1, 'channel_name', 'user_name', 'user_password', 'merchant_id', 'cer_file_path', 'pfx_file_path', 'pfx_file_key', 0, 'api_url', NULL, NULL);


INSERT INTO `asset_set` (`id`, `asset_fair_value`,`asset_initial_value`, `asset_recycle_date`, `asset_status`, `asset_uuid`, `create_time`, `last_modified_time`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`)
VALUES
	(1, 10010.00,10000.00,'2016-05-16', 0, 'asset_uuid_1', '2016-05-16 13:34:35', '2016-05-16 13:34:35', 'DKHD-001-01', 1, NULL);



SET FOREIGN_KEY_CHECKS=1;
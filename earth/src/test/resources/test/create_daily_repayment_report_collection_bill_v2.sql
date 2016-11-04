SET FOREIGN_KEY_CHECKS = 0;

delete from rent_order;
delete from contract;
delete from house;
delete from customer;
delete from transaction_record;
delete from transaction_record_log;
delete from finance_payment_record;
delete from app;
delete from receive_order_map;
delete from app_arrive_record;
delete from business_voucher;
delete from journal_voucher;
delete from asset_package;

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `payee_account_name`, `payee_account_no`, `payee_bank_name`) VALUES
(1, '小寓', '70991db75ebb24fa0993f4fa25775022', b'0', 'http://beta.demo2do.com/jupiter/', '寓见', 5, NULL, NULL, NULL),
(2, '优帕克', '优帕克ID', b'0', 'http://beta.demo2do.com/jupiter/', '优帕克', 5, NULL, NULL, NULL);

INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`) VALUES
(1, NULL, NULL, NULL, '优帕克承租方', NULL, '优帕克', 2),
(2, NULL, NULL, NULL, '小寓预付款承租方', NULL, '寓见', 1);


INSERT INTO `house` (`id`, `address`, `area`, `bulid_area`, `city`, `community`, `community_detail`, `hall`, `house_status`, `house_type`, `province`, `room`, `app_id`) VALUES
(1, '杭州市西湖区', NULL, 0, NULL, 'test-rom', NULL, 0, 1, 2, NULL, 0, 8);

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`, `transfer_status`) VALUES
(1, '2015-04-01', 'KXHY1#1803', 0, '33000.00', '2016-03-31', NULL, '', '16500.00', b'0', 1, NULL, NULL, 1, 1, 1, 0);

INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`, `contract_payment_amount`, `contract_payment_type`, `doubt_record`, `virtual_account_unique_id`, `bill_life_cycle`, `collection_bill_life_cycle`, `collection_bill_capital_status`, `asset_package_id`, `collection_bill_service_notice_status`, `collection_bill_frozen_status`, `factoring_contract_id`, `audit_bill_id`, `repayment_audit_status`, `repayment_status`, `repayment_bill_id`) VALUES
 (627, '2016-02-24', '2016-03-23', NULL, 'KXHY1#1803#ORDER#1', 0, 0.00, NULL, b'0', '2016-02-24', 9500.00, 1, 1, 0, NULL, 'unique_particle_id-12cdaa660e05', 1, NULL, 'unique_bill_id-11_627', 9500.00, 1, NULL, 'virtual_account-04824b9daa1a', 1, 1, 4, 13, 1, 0, 58, 'audit_bill_id-359614b4c721', 2, 0, 'repayment_bill-fb74e18e0830');

 INSERT INTO `asset_package` (`id`, `adva_start_date`, `is_available`, `bank_account`, `bank_name`, `create_time`, `latest_settle_date`, `otsd_amt`, `otsd_amt_aval`, `rec_transfee_total_amt`, `remove_time`, `contract_id`, `factoring_contract_id`, `thru_date`) VALUES 
('13', '2015-04-03 00:00:00', '', NULL, NULL, '2015-04-02 00:00:00', '2015-04-03 00:00:00', '138600.00', '138600.00', '198000.00', NULL, '13', '58', NULL);

INSERT INTO `business_voucher` (`id`, `account_id`, `account_side`, `billing_plan_breif`, `billing_plan_type_uuid`, `billing_plan_uuid`, `business_voucher_status`, `business_voucher_type_uuid`, `business_voucher_uuid`, `company_id`, `receivable_amount`, `settlement_amount`, `tax_invoice_uuid`) VALUES
                               (3024,   0,            1,              '',                 'AUDIT_BILL_BILLING_PLAN', 'audit_bill_id-359614b4c721', 2,                'AUDIT_BILL_BUSINESS_VOUCHER', 'business_voucher-359614b4c721', 4, 9500.00, 9500.00, NULL);

INSERT INTO `journal_voucher` (`id`, `account_side`, `bank_identity`, `billing_plan_uuid`,         `booking_amount`, `business_voucher_type_uuid`, `business_voucher_uuid`, `cash_flow_amount`, `cash_flow_breif`, `cash_flow_channel_type`, `cash_flow_serial_no`, `cash_flow_uuid`,                     `checking_level`, `company_id`, `completeness`, `counter_party_account`, `counter_party_name`, `journal_voucher_uuid`, `notification_identity`, `notification_memo`, `notification_record_uuid`, `notified_date`, `settlement_modes`, `source_document_amount`, `source_document_breif`, `source_document_cash_flow_serial_no`, `source_document_counter_party_uuid`, `source_document_identity`, `source_document_uuid`, `status`, `trade_time`, `virtual_account_uuid`, `created_date`) VALUES
                              (6371,     1,            '',            'unique_bill_id-11_627', 9500.00,         'AUDIT_BILL_BILLING_PLAN',     'business_voucher-359614b4c721', 9500.00,            '',                0,                        '0',                   '558fc1f8-b05c-440e-a246-8dcfc1375b98', 1,               1,            1,             NULL,                    NULL,                 'ecd50a7e-abf4-40e6-873b-dd51b8256254', '2016-02-29-15.35.00.477789', '房租', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, '2016-02-29 15:35:00', NULL, '2016-03-01 13:23:09');

INSERT INTO `finance_payment_record` (`id`, `creator_id`, `pay_money`, `payment_no`, `payment_time`, `order_id`, `batch_pay_record_id`, `bank_name`, `card_no`, `payee_name`) VALUES
                                     (1185,           9, '1200.00', '1509092169848140001', '2015-09-21 17:29:06', 627, 157, NULL, NULL, NULL);

INSERT INTO `app_arrive_record` (`id`, `amount`, `arrive_record_status`, `pay_ac_no`, `receive_ac_no`, `remark`, `serial_no`, `time`, `app_id`, `drcrf`, `pay_name`, `summary`, `vouh_no`, `operate_remark`) VALUES
(1, '52500.00', 2, '6222081001006853570', '1001300219000027827', '永业公寓28号604', '2015-03-30-22.39.56.930280', '2015-03-30 22:39:56', 2, '2', '徐月', '网转', '0', NULL);

INSERT INTO `receive_order_map` (`id`, `amount`, `association_type`, `create_time`, `is_deducted`, `pay_time`, `app_arrive_record_id`, `order_id`, `receive_order_map_status`) VALUES
(1, '16500.00', 1, '2015-04-03 12:00:00', b'1', '2015-04-03 12:00:00', 1, 1, 0);


SET FOREIGN_KEY_CHECKS = 1;
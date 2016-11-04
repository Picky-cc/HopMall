SET FOREIGN_KEY_CHECKS=0;
delete from financial_contract;
delete from asset_set;
delete from contract;
delete from app;
delete from customer;
delete from house;
delete from transfer_application;
delete from rent_order;
delete from batch_pay_record;


INSERT INTO `financial_contract` (`id`, `asset_package_format`, `adva_matuterm`, `adva_start_date`, `contract_no`, `contract_name`, `app_id`, `company_id`, `adva_repo_term`, `thru_date`, `capital_account_id`, `financial_contract_type`, `loan_overdue_start_day`, `loan_overdue_end_day`, `payment_channel_id`, `ledger_book_no`, `financial_contract_uuid`) 
VALUES 
('1', '1', '7', '2016-06-08 00:00:00', 'DCF-NFQ-LR903', 'sasad', '1', '1', '3', '2016-09-05 00:00:00', '2', '1', '5', '90', '0', 'yunxin_nfq_ledger_book', 'd7b3b325-719a-42af-a129-0ac861f18ebe');

INSERT INTO `contract_account` (`id`, `pay_ac_no`, `bankcard_type`, `contract_id`, `payer_name`, `bank`, `bind_id`, `id_card_num`, `bank_code`, `province`, `city`) 
VALUES 
('323', '6217000000000003006', NULL, '323', '测试用户1', '中国邮政储蓄银行', NULL, NULL, '403', '安徽省', '亳州');


INSERT INTO `asset_set` (`id`, `guarantee_status`, `settlement_status`, `asset_fair_value`, `asset_principal_value`, `asset_interest_value`, `asset_initial_value`, `asset_recycle_date`, `confirm_recycle_date`, `refund_amount`, `asset_status`, `on_account_status`, `repayment_plan_type`, `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `comment`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `current_period`, `overdue_status`, `overdue_date`) 
VALUES 
('323', '1', '0', '1217.40', '0.00', '1200.00', '1200.00', '2016-05-01', NULL, '0.00', '1', '2', 0, '2016-06-15 22:13:45', 'e86cba4c-b447-4e05-89c2-4fb339b4f888', '2016-06-15 22:04:59', '2016-06-15 23:24:31', NULL, 'ZC27367D23EF4A36F4', '323', NULL, '1', '0', NULL),
 ('324', '1', '0', '1217.40', '1200.00', '0.00', '1200.00', '2016-06-17', NULL, '0.00', '0', '1', 0, '2016-06-15 22:13:46', '1b31d474-595d-4e8e-b716-01c91b18fe5c', '2016-06-15 22:04:59', '2016-06-15 23:24:31', NULL, 'ZC27367D23EF795E49', '323', NULL, '2', '0', NULL);

INSERT INTO `transfer_application` (`id`, `transfer_application_uuid`, `amount`, `batch_pay_record_id`, `comment`, `create_time`, `creator_id`, `deduct_status`, `payment_way`, `executing_deduct_status`, `transfer_application_no`, `order_id`, `deduct_time`, `last_modified_time`, `contract_account_id`, `union_pay_order_no`)
VALUES 
('56', 'ce90202623a14fd5a7a6ce9f863c8cd4', '1217.40', '1', '签名工具类异常', '2016-06-15 23:03:36', NULL, '0', '0', '1', '27367DBAA552A85B', '189', NULL, '2016-06-15 23:03:36', '323', '27367DBAA552A85B'),
('57', '0ce94f46cb654c4d9654561c0fd2dec1', '1217.40', '2', '签名工具类异常', '2016-06-15 23:03:36', NULL, '0', '0', '1', '27367DBAA55EDDBB', '190', NULL, '2016-06-15 23:03:36', '323', '27367DBAA55EDDBB');


INSERT INTO `rent_order` (`id`, `order_type`, `due_date`, `order_no`, `payout_time`, `total_rent`, `customer_id`, `user_upload_param`, `modify_time`, `financial_contract_id`, `repayment_bill_id`, `asset_set_id`, `create_time`, `clearing_status`, `executing_settling_status`, `comment`) 
VALUES 
('189', '0', '2016-06-15', 'JS27367D31764C5F2C', NULL, '1217.40', '337', '[1994,1995,1996,1997,1998,1999,2000,2001,2002,2003,2004,2005,2006,2007,2008,2009,2010,2011,2012,2013,2014,2015,2016,2017,2018,2019,2020,2021,2022,2023]', '2016-06-15 23:03:36', '1', '90d1993676754549ac4637f4c0c81262', '323', '2016-06-15 22:13:45', '0', '1', NULL),
 ('190', '0', '2016-06-15', 'JS27367D3179D2EB1E', NULL, '1217.40', '337', '[2024,2025,2026,2027,2028,2029,2030,2031,2032,2033,2034,2035,2036,2037,2038,2039,2040,2041,2042,2043,2044,2045,2046,2047,2048,2049,2050,2051,2052,2053]', '2016-06-15 23:03:36', '1', '439a3e74f6b0466a9390e05d8411adb1', '324', '2016-06-15 22:13:46', '0', '1', NULL);


INSERT INTO `batch_pay_record` (`id`, `batch_pay_record_uuid`, `amount`, `modify_time`, `create_time`, `pay_time`, `request_no`, `serial_no`, `request_data`, `response_data`, `query_response_data`, `trans_date_time`) 
VALUES
('1', 'dc97d9540f7640448b9d14b750d99b42', '1210.20', '2016-07-07 17:07:40', '2016-06-03 11:39:30', NULL, '2735B6CA6FCADF73', NULL, NULL, NULL, '签名工具类异常', NULL),
 ('2', '0203c4a741af4a8491847b14350ce74c', '1210.20', '2016-07-07 17:07:40', '2016-06-03 11:39:31', NULL, '2735B6CA6FEF8326', NULL, NULL, NULL, '签名工具类异常', NULL);

 
 
INSERT INTO `contract` (`id`, `unique_id`, `begin_date`, `contract_no`, `end_date`, `asset_type`, `month_fee`, `app_id`, `customer_id`, `house_id`, `actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`) 
VALUES 
('323', '1234567', '2016-04-17', '云信信2016-78-DK(ZQ2016042522479)', NULL, '1', '0.00', '1', '337', '337', NULL, '2016-06-15 22:04:59', '0.1560000000', '0', '0', '1', '2', '1200.00', '0.0005000000');

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `addressee`) 
VALUES
('1', 'nongfenqi', '', '', '', '农分期', '2', NULL);

INSERT INTO `customer` (`id`, `account`, `mobile`, `name`, `source`, `app_id`, `customer_uuid`) 
VALUES 
('337', NULL, NULL, '测试用户1', 'C74211', '1', '6120dce9-6214-433f-a7bd-acf9f89e2b7c');

INSERT INTO `house` (`id`, `address`, `app_id`)
VALUES 
('337', NULL, '1');

INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`) 
VALUES 
('2', '南京', '南京农纷期电子商务有限公司', '农分期');

SET FOREIGN_KEY_CHECKS=1;
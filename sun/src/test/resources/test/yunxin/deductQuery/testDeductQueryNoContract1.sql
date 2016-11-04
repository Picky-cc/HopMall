
SET FOREIGN_KEY_CHECKS=0;

delete from  contract;
delete from  app;
delete from  house;
delete from  asset_set;
delete from  company;
delete from  customer;
delete from  t_deduct_application;


INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `end_date`, `asset_type`, `month_fee`, `app_id`, `customer_id`, `house_id`, `actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`)
VALUES 
('323', '2016-04-17', '云信信2016-78--DK(ZQ2016042522479)', NULL, '1', '0.00', '1', '337', '337', NULL, '2016-06-15 22:04:59', '0.1560000000', '0', '0', '1', '2', '1200.00', '0.0005000000');

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `addressee`) 
VALUES
('1', 'nongfenqi', '', '', '', '农分期', '2', NULL);

INSERT INTO `customer` (`id`, `account`, `mobile`, `name`, `source`, `app_id`, `customer_uuid`) 
VALUES 
('337', NULL, NULL, '测试用户1', 'C74211', '1', '6120dce9-6214-433f-a7bd-acf9f89e2b7c');

INSERT INTO `house` (`id`, `address`, `app_id`)
VALUES 
('337', NULL, '1');

INSERT INTO `asset_set` (`id`, `guarantee_status`, `settlement_status`, `asset_fair_value`, `asset_principal_value`, `asset_interest_value`, `asset_initial_value`, `asset_recycle_date`, `confirm_recycle_date`, `refund_amount`, `asset_status`, `on_account_status`, `repayment_plan_type`, `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `comment`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `current_period`) 
VALUES 
('323', '1', '0', '1217.40', '0.00', '1200.00', '1200.00', '2016-05-17', NULL, '0.00', '0', '1', 0, '2016-06-15 22:13:45', 'e86cba4c-b447-4e05-89c2-4fb339b4f888', '2016-06-15 22:04:59', '2016-06-15 23:24:31', NULL, 'ZC27367D23EF4A36F4', '323', NULL, '1');

INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`) 
VALUES 
('2', '南京', '南京农纷期电子商务有限公司', '农分期');
INSERT INTO `t_deduct_application` (`id`, `deduct_application_uuid`, `deduct_id`, `request_no`, `financial_contract_uuid`, `financial_product_code`, `contract_unique_id`, `repayment_plan_code_list`, `contract_no`, `planned_deduct_total_amount`, `actual_deduct_total_amount`, `notify_url`, `transcation_type`, `repayment_type`, `execution_status`, `execution_remark`, `create_time`, `creator_name`, `ip`, `last_modified_time`, `record_status`, `is_available`, `api_called_time`, `transaction_recipient`)
VALUES
	(31, '1602bc9b-8a48-4ac0-874e-b32cdd8a3d9c', '1087e301-bd27-45b4-8368-3137b9e6e12c', '4eecd64a-6f81-44b7-94dd-77f1fb189ed5', '984149f1-cb43-410c-a789-d8f4bba123b6', 'G00001', NULL, '[\"ZC27375ACFF4234805\"]', '629测试(ZQ2016002000001)', 100.00, 0.00, '', 1, 0, 1, '', '2016-08-25 17:14:31', 't_test_zfb', '127.0.0.1', '2016-08-26 21:23:54', 1, 0, '2016-05-04 00:00:00', 1);


SET FOREIGN_KEY_CHECKS=1;
SET FOREIGN_KEY_CHECKS=0;

delete from `rent_order`;
delete from `asset_set`;
delete from `financial_contract`;
delete from `account`;
delete from `contract`;
delete from `asset_valuation_detail`;
delete from `contract_account`;
delete from `asset_package`;
delete from `transfer_application`;



INSERT INTO `asset_set` (`id`, `guarantee_status`, `settlement_status`, `asset_fair_value`, `asset_principal_value`, `asset_interest_value`, `asset_initial_value`, `asset_recycle_date`, `confirm_recycle_date`, `refund_amount`, `asset_status`, `on_account_status`, `repayment_plan_type`, `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `comment`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `current_period`) 
VALUES 
('1', '0', '0', '1210.20', '0.00', '1200.00', '1200.00', '2016-05-17', NULL, '0.00', '0', '1', 0, '2016-06-03 11:31:18', '43e29089-af4d-4d47-9b76-3d8cd4e1ec8f', '2016-06-02 18:04:51', '2016-06-03 11:31:18', NULL, 'ZC2735AAC1787050D8', '1', NULL, '1');

INSERT INTO `financial_contract` (`id`, `asset_package_format`, `adva_matuterm`, `adva_start_date`, `contract_no`, `contract_name`, `app_id`, `company_id`, `adva_repo_term`, `thru_date`, `capital_account_id`, `financial_contract_type`, `loan_overdue_start_day`, `loan_overdue_end_day`, `payment_channel_id`, `ledger_book_no`) 
VALUES 
       ('1', '1', '7', '2015-04-02 00:00:00', 'DCF-NFQ-LR903A', '农分期', '1', '1', '0', '9999-01-01 00:00:00', '2', '1', '0', '0', '2', 'yunxin_nfq_ledger_book');

INSERT INTO `customer` (`id`, `account`, `mobile`, `name`, `source`, `app_id`, `customer_uuid`)
VALUES
('1', NULL, NULL, '测试用户1', 'C74211', '1', 'a0fed70c-7b8d-41ef-b953-df58ce9b32af');

INSERT INTO `rent_order` (`id`, `order_type`, `due_date`, `order_no`, `payout_time`, `total_rent`, `customer_id`, `user_upload_param`, `modify_time`, `financial_contract_id`, `repayment_bill_id`, `asset_set_id`, `create_time`, `clearing_status`, `executing_settling_status`, `comment`)
VALUES 
       ('1', '0', '2016-06-03', 'JS2735B6BE27E1EB0D', NULL, '1210.20', '1', '[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18]', '2016-06-03 11:39:30', '1', '02350064170a45fca3e382f477ecf507', '1', '2016-06-03 11:31:25', '0', '1', NULL),

        ('2', '0', '2016-06-03', 'JS2735B6BE27ED5EA8', NULL, '1210.20', '2', '[19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36]', '2016-06-03 11:39:30', '1', 'c4e6c762937140d580161534e96b883c', '2', '2016-06-03 11:31:25', '0', '1', NULL),
        ('3', '0', '2016-06-03', 'JS2735B6BE27F880AD', NULL, '2420.40', '3', '[37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54]', '2016-06-03 11:39:30', '1', '0d20a79d3e1742878222fb70f294e48b', '3', '2016-06-03 11:31:25', '0', '1', NULL),
        ('4', '0', '2016-06-03', 'JS2735B6BE280371D7', NULL, '2117.85', '4', '[55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72]', '2016-06-03 14:14:00', '1', '21913d9d45424a10b1984ffad919888a', '4', '2016-06-03 11:31:25', '0', '3', NULL),
        ('5', '0', '2016-06-03', 'JS2735B6BE280FA79D', NULL, '2017.00', '5', '[73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90]', '2016-06-03 14:14:00', '1', '63ab439c9f914ae98d93ce50a403b4ce', '5', '2016-06-03 11:31:25', '0', '3', NULL);


INSERT INTO `transfer_application` (`id`, `transfer_application_uuid`, `amount`, `batch_pay_record_id`, `comment`, `create_time`, `creator_id`, `deduct_status`, `payment_way`, `executing_deduct_status`, `transfer_application_no`, `order_id`, `deduct_time`, `last_modified_time`, `contract_account_id`, `union_pay_order_no`) 
VALUES 
       ('1', 'd046021fa9604c3a90908a11bf234362', '1210.20', '1', '签名工具类异常！', '2016-06-03 11:39:30', NULL, '0', '0', '0', '2735B6CA6FCADF73', '1', NULL, '2016-06-03 11:39:31', '1', '2735B6CA6FCADF73'),
('2', 'c27d14eb91924da2b56a06adb22723a3', '1210.20', '2', '签名工具类异常！', '2016-06-03 11:39:30', NULL, '0', '0', '1', '2735B6CA6FEF8326', '2', NULL, '2016-06-03 11:39:31', '2', '2735B6CA6FEF8326'),
('3', '7198305a04714ff9a8dc8e001108da1e', '2420.40', '3', '签名工具类异常！', '2016-06-03 11:39:30', NULL, '0', '0', '2', '2735B6CA6FF6B989', '3', NULL, '2016-06-03 11:39:31', '3', '2735B6CA6FF6B989'),
('4', 'fc589b24c2a24e75879e9caf4c3642c6', '2117.85', '4', '签名工具类异常！', '2016-06-03 11:39:30', NULL, '0', '0', '3', '2735B6CA6FFDB667', '4', NULL, '2016-06-03 11:39:31', '4', '2735B6CA6FFDB667'),
('5', 'c8d61409024048deb31bfe9407bde9d5', '2017.00', '5', '签名工具类异常！', '2016-06-03 11:39:30', NULL, '0', '0', '4', '2735B6CA7003DDBB', '5', NULL, '2016-06-03 11:39:31', '5', '2735B6CA7003DDBB');


SET FOREIGN_KEY_CHECKS=1;

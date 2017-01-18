SET FOREIGN_KEY_CHECKS=0;

delete from factoring_contract;
delete from app;
delete from company;
delete from contract;
delete from asset_package;
delete from customer;
delete from contract_account;
INSERT INTO `factoring_contract` (`id`, `adva_matuterm`, `adva_pct`, `adva_start_date`, `adva_term`, `ar_amt`, `asset_package_format`, `ar_cost_rate`, `ar_deposit_amt`, `ar_deposit_amt_aval`, `ar_deposit_amt_input_days`, `ar_deposit_amt_input_type`, `ar_ldgamt`, `borrower_name`, `contract_no`, `cost_deduct_type`, `deposit_deduct_type`, `int_accuretype`, `int_rate`, `latest_settle_date`, `lender_ac_bk_name`, `lender_ac_no`, `lender_name`, `otsd_amt`, `otsd_amt_aval`, `settle_pct`, `app_id`, `company_id`, `nominal_rate`, `payment_agreement_id`, `adva_repo_term`, `deposit_rate`, `financing_verification_ways`, `is_buffer_repayment`, `thru_date`, `payback_account_id`, `payment_channel_id`, `trusts_contract_type`, `loan_overdue_start_day`, `loan_overdue_end_day`, `loan_repo_day`, `loan_guarantee_day`, `trusts_account_name`, `trusts_account_no`, `trusts_bank_name`) 
VALUES 
('1', '7', '0.70', '2015-04-02 00:00:00', '0', '11211970.00', '0', '0.00', '0.00', '0.00', '15', '1', '11211970.00', '安美途', 'DCF-AMT-LR903A', '2', '1', '2', '0.1971432000', NULL, NULL, NULL, '安美途', '7848379.00', '7848379.00', '1.00', '1', '1', '0.0850000000', '1', '0', NULL, NULL, NULL, NULL, '0', '0', '0', '0', '0', '0', '0', NULL, NULL, NULL);


INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `addressee`, `payee_account_name`, `payee_account_no`, `payee_bank_name`) 
VALUES 
('1', 'youpark', '123456', '\0', '', '优帕克', '4', NULL, NULL, NULL, NULL);



INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`, `bank_name`, `card_no`, `name`) 
VALUES
('1', '杭州万塘路8号', '杭州随地付网络技术有限公司', '租房宝', NULL, NULL, NULL),
('4', '上海', '上海优帕克投资管理有限公司', '优帕克', NULL, NULL, NULL),
('5', '上海', '小寓科技', '小寓', NULL, NULL, NULL),
 ('13', '上海', '杭州驻客网络技术有限公司', '驻客', NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS=1;
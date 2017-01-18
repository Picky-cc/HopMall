SET FOREIGN_KEY_CHECKS=0;

delete from `app`;
delete from `customer`;
delete from `company`;
delete from `rent_order`;
delete from `asset_set`;
delete from `account`;
delete from `contract`;
delete from `asset_valuation_detail`;
delete from `contract_account`;
delete from `asset_package`;
delete from `transfer_application`;
delete from `batch_pay_record`;
delete from `source_document`;
delete from `journal_voucher`;
delete from `business_voucher`;
delete from `ledger_book`;
delete from `asset_package`;
delete from `loan_batch`;
delete from `ledger_book_shelf`;
delete from `financial_contract`;
delete from `payment_channel`;
delete from `rent_order`;
delete from `financial_contract_config`;
delete from `payment_channel_information`;


INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `addressee` )
VALUES 
('1', 'anmeitu', '', '\0', '', '安美途', '1', NULL),
('2', 'nfq', '', '\0', '', '农分期', '1', NULL);

INSERT INTO `ledger_book` (`id`, `ledger_book_no`, `ledger_book_orgnization_id`, `book_master_id`, `party_concerned_ids`) VALUES 
('1', 'yunxin_ledger_book_1', '1', '1', ''),
('2', 'yunxin_ledger_book_2', '1', '1', '');

INSERT INTO `financial_contract` (`id`, `adva_matuterm`, `contract_no`, `contract_name`, `app_id`, `company_id`,   `adva_repo_term`,`loan_overdue_end_day`,`loan_overdue_start_day`,`payment_channel_id`,`ledger_book_no`,`asset_package_format`,`financial_contract_type`,`financial_contract_uuid`,`capital_account_id`) VALUES 
('1', 3, 'DCF-NFQ-LR903A', '云南信托', 1, 1, 30,90,1,1,'yunxin_ledger_book_1',1,0,'financial_contract_uuid_1',1),
('2', 3, 'DCF-NFQ-22', '云南信托', 2, 1, 30,90,1,NULL,'yunxin_ledger_book_2',1,0,'financial_contract_uuid_2',2);


INSERT INTO `financial_contract_config` (`id`, `financial_contract_uuid`, `business_type`, `payment_channel_router_for_debit`) VALUES 
('1', 'financial_contract_uuid_2', '0', 'debit_payment_channel_uuid_1');


INSERT INTO `payment_channel_information` (`id`, `related_financial_contract_uuid`, `payment_channel_uuid`,  `outlier_channel_name`) VALUES 
('1',  'financial_contract_uuid_2','debit_payment_channel_uuid_1','mer_id_1');


INSERT INTO `account` (`id`, `account_name`, `account_no`, `alias`, `attr`) VALUES 
(1, 'account_name_1', '955103657777777', NULL, NULL),
(2, 'account_name_2', '07895462', NULL, NULL);

INSERT INTO `payment_channel` (`id`, `channel_name`, `user_name`, `user_password`, `merchant_id`, `cer_file_path`, `pfx_file_path`, `pfx_file_key`, `payment_channel_type`, `api_url`, `from_date`, `thru_date`)
VALUES
	(1, '广东银联', 'user1', '123456', '000191400205800', NULL, NULL, NULL, 0, NULL, NULL, NULL);


INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`,`uuid`) 
VALUES 
('1', '上海', '云南国际信托有限公司', '云南信托','2333ssss');


INSERT INTO `customer` (`id`, `account`, `mobile`, `name`, `source`, `app_id`,`customer_uuid`) VALUES 
(1, NULL, NULL, NULL, NULL, 1,'customerUuid1');

SET FOREIGN_KEY_CHECKS=1;

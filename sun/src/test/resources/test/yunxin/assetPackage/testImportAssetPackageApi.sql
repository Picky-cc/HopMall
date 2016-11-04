SET FOREIGN_KEY_CHECKS=0;

delete from financial_contract;
delete from app;
delete from company;
delete from contract;
delete from asset_package;
delete from customer;
delete from contract_account;
delete from asset_set;
delete from house;
INSERT INTO `financial_contract` (`id`, `asset_package_format`, `adva_matuterm`, `adva_start_date`, `contract_no`, `contract_name`, `app_id`, `company_id`, `adva_repo_term`, `thru_date`, `capital_account_id`, `financial_contract_type`, `loan_overdue_start_day`, `loan_overdue_end_day`, `payment_channel_id`, `ledger_book_no`, `financial_contract_uuid`) 
VALUES
('1', NULL, '3', NULL, 'G0000000', NULL, '1', '1', '30', NULL, '1', '0', '0', '0', '0', NULL, 'uuid');

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `addressee`)
VALUES
	(1, 'nongfenqi', '11111db75ebb24fa0993f4fa25775023', 1, 'http://e.zufangbao.cn', '农分期', 5, NULL);

INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`)
VALUES
	(1, '上海', '云南国际信托有限公司', '云南信托'),
	(5, '杭州', '农分期', '农分期');

SET FOREIGN_KEY_CHECKS=1;
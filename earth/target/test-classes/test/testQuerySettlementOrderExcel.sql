SET FOREIGN_KEY_CHECKS=0;

delete from settlement_order;
delete from asset_set;
delete from contract;
delete from app;
delete from financial_contract;

INSERT INTO `financial_contract` (`id`, `asset_package_format`, `adva_matuterm`, `adva_start_date`, `contract_no`, `contract_name`, `app_id`, `company_id`, `adva_repo_term`, `thru_date`, `capital_account_id`, `financial_contract_type`, `loan_overdue_start_day`, `loan_overdue_end_day`, `payment_channel_id`, `ledger_book_no`, `financial_contract_uuid`) VALUES ('1', NULL, '3', NULL, 'YX_AMT_001', NULL, '1', '1', '30', NULL, NULL, '0', '0', '0', '0', NULL, NULL);

INSERT INTO `asset_set` (`id`, `guarantee_status`, `settlement_status`, `asset_fair_value`, `asset_recycle_date`, `asset_status`, `on_account_status`, `repayment_plan_type`, `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `current_period`)
VALUES 
('7101', '0', '0', '15.98', '2016-04-08', '0', '1', 0, '2016-04-13 16:45:00', '6b40848f-ff52-4047-897f-2ca2135e9fd6', '2016-03-31 19:49:37', '2016-04-13 16:45:00', 'DKHD-001-01', '1687', NULL, '1');


INSERT INTO `contract` (`id`, `asset_type`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`, `shroff_account_id`, `transfer_status`, `contract_life_cycle`, `contract_type`, `actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`) 
VALUES ('1687', '0', '2016-03-08', 'DKHD-001', '0', NULL, NULL, NULL, NULL, '15976.15', NULL, NULL, NULL, NULL, '1', '1749', '1675', NULL, '0', NULL, NULL, NULL, '2016-03-31 19:49:34', '0.1500000000', '0', '1', '20', '0', '100000.00', '0.0005000000');

INSERT INTO `settlement_order` (`id`, `asset_set_id`, `settle_order_no`, `guarantee_order_no`, `due_date`, `overdue_days`, `overdue_penalty`, `create_time`, `modify_time`, `settlement_amount`,`comment`)
VALUES ('1', '7101', 'qs123456', 'bc123456', '2016-04-18', '0', '0.00', NULL, '2016-04-13 16:45:00', '0.00',  NULL);

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `addressee`, `payee_account_name`, `payee_account_no`, `payee_bank_name`)
VALUES 
('1', 'anmeitu', '11111db75ebb24fa0993f4fa25775023', '\0', 'http://e.zufangbao.cn', '安美途', '1', NULL, NULL, NULL, NULL);


SET FOREIGN_KEY_CHECKS=1;
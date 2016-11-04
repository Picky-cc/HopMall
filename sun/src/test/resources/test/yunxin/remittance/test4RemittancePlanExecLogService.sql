SET FOREIGN_KEY_CHECKS=0;

delete from `t_remittance_plan_exec_log`;
delete from `financial_contract`;

INSERT INTO `t_remittance_plan_exec_log` (`id`, `remittance_application_uuid`, `remittance_plan_uuid`, `financial_contract_uuid`, `financial_contract_id`, `payment_gateway`, `payment_channel_uuid`, `payment_channel_name`, `pg_account_name`, `pg_account_no`, `pg_clearing_account`, `planned_amount`, `actual_total_amount`, `cp_bank_code`, `cp_bank_card_no`, `cp_bank_account_holder`, `cp_id_type`, `cp_id_number`, `cp_bank_province`, `cp_bank_city`, `cp_bank_name`, `transaction_type`, `transaction_remark`, `exec_req_no`, `exec_rsp_no`, `execution_status`, `execution_remark`, `transaction_serial_no`, `complete_payment_date`, `create_time`, `last_modified_time`, `plan_credit_cash_flow_check_number`, `actual_credit_cash_flow_check_number`, `reverse_status`, `credit_cash_flow_uuid`, `debit_cash_flow_uuid`)
VALUES 
 ('1', 'remittance_application_uuid1', 'remittance_plan_uuid1', 'financial_contract_uuid1', '1', '1', 'payment_channel_uuid1', 'payment_channel_name1', 'pg_account_name1', 'pg_account_no1', 'pg_clearing_account1', '0.03', '0.00', 'cp_bank_code1', 'cp_bank_card_no1', 'cp_bank_account_holder1', '0', 'cp_id_number1', 'cp_bank_province1', 'cp_bank_city1', 'cp_bank_name1', '0', NULL, 'exec_req_no1', 'exec_rsp_no1', '3', NULL, 'transaction_serial_no1', '2016-09-22 15:32:23', '2016-09-22 15:32:23', '2016-09-22 15:32:58', '55', '0', '0', 'credit_cash_flow_uuid1', 'debit_cash_flow_uuid1'),
('2', 'remittance_application_uuid2', 'remittance_plan_uuid2', 'financial_contract_uuid2', '2', '1', 'payment_channel_uuid2', 'payment_channel_name2', 'pg_account_name2', 'pg_account_no2', 'pg_clearing_account2', '0.04', '0.00', 'cp_bank_code2', 'cp_bank_card_no2', 'cp_bank_account_holder2', '0', 'cp_id_number2', 'cp_bank_province2', 'cp_bank_city2', 'cp_bank_name2', '0', NULL, 'exec_req_no2', 'exec_rsp_no2', '3', NULL, 'transaction_serial_no2', '2016-09-22 15:32:23', '2016-09-22 15:32:23', '2016-09-22 15:32:58', '0', '0', '0', 'credit_cash_flow_uuid2', 'debit_cash_flow_uuid2'),
('3', 'remittance_application_uuid3', 'remittance_plan_uuid3', 'financial_contract_uuid3', '3', '1', 'payment_channel_uuid3', 'payment_channel_name3', 'pg_account_name3', 'pg_account_no3', 'pg_clearing_account3', '0.05', '0.00', 'cp_bank_code3', 'cp_bank_card_no3', 'cp_bank_account_holder3', '0', 'cp_id_number3', 'cp_bank_province3', 'cp_bank_city3', 'cp_bank_name3', '0', NULL, 'exec_req_no3', 'exec_rsp_no3', '3', NULL, 'transaction_serial_no3', '2016-09-22 15:32:23', '2016-09-22 15:32:23', '2016-09-22 15:32:58', '55', '0', '0', 'credit_cash_flow_uuid3', 'debit_cash_flow_uuid3');



INSERT INTO `financial_contract` (`id`, `adva_matuterm`, `contract_no`, `app_id`, `company_id`, `adva_repo_term`) VALUES 
('1', 3, 'financial_contract_no1', 1, 1, 30);
	
SET FOREIGN_KEY_CHECKS=1;



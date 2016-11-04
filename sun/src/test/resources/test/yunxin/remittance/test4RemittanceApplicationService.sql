SET FOREIGN_KEY_CHECKS=0;

delete from `t_remittance_application`;
delete from `financial_contract`;

INSERT INTO `t_remittance_application` (`id`, `remittance_application_uuid`, `request_no`, `financial_contract_uuid`, `financial_contract_id`, `financial_product_code`, `contract_unique_id`, `contract_no`, `planned_total_amount`, `actual_total_amount`, `auditor_name`, `audit_time`, `notify_url`, `plan_notify_number`, `actual_notify_number`, `remittance_strategy`, `remark`, `transaction_recipient`, `execution_status`, `execution_remark`, `create_time`, `creator_name`, `ip`, `last_modified_time`)
VALUES
	(1, 'remittance_application_uuid1', 'request_no1', 'financial_contract_uuid1', 1, 'G00003', 'contract_unique_id1', 'contract_no1', 0.03, 0.00, '', NULL, NULL, 0, 0, 0, '20160912测试', 1, 3, NULL, '2016-09-18 17:32:23', 't_test', '127.0.0.1', '2016-09-19 17:32:58'),
	(2, 'remittance_application_uuid1', 'request_no2', 'financial_contract_uuid2', 1, 'G00003', 'contract_unique_id2', 'contract_no2', 0.04, 0.00, '', NULL, NULL, 0, 0, 0, '20160913测试', 1, 2, NULL, '2016-09-19 17:32:23', 't_test', '127.0.0.1', '2016-09-19 17:32:58'),
	(3, 'remittance_application_uuid3', 'request_no3', 'financial_contract_uuid3', 1, 'G00003', 'contract_unique_id3', 'contract_no3', 0.05, 0.00, '', NULL, NULL, 0, 0, 0, '20160913测试', 1, 2, NULL, '2016-09-19 17:32:23', 't_test', '127.0.0.1', '2016-09-19 17:32:58');

INSERT INTO `financial_contract` (`id`, `adva_matuterm`, `contract_no`, `app_id`, `company_id`, `adva_repo_term`) VALUES 
('1', 3, 'financial_contract_no1', 1, 1, 30);
	
SET FOREIGN_KEY_CHECKS=1;


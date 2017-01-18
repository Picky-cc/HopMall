SET FOREIGN_KEY_CHECKS=0;

delete from  offline_bill;

delete from  source_document;

INSERT INTO `offline_bill` (`id`, `amount`, `bank_show_name`, `comment`, `create_time`, `trade_time`, `customer_no_list`, `is_delete`, `status_modified_time`, `last_modified_time`, `offline_bill_no`, `offline_bill_status`, `offline_bill_uuid`, `order_no_list`, `serial_no`, `payer_account_name`, `payer_account_no`, `single_loan_contract_no_list`, `guarantee_order_list_cache`) VALUES 
('1', '1000.00', '中国银行guabnf', '备注', '2016-04-01 14:22:58', NULL, '[\"D001\"]', '\0', NULL, '2016-04-01 14:23:27', 'BC00001', '1', 'uuid', '[\"DKHD-001-01-20160307\",\"DKHD-001-01-20160308\",\"DKHD-001-01-20160309\"]', 'serial_no', 'payer_account_name', 'account_no', '[\"test-contract-1-1\",\"test-contract-1-2\",\"test-contract-2-1\"]', NULL);

INSERT INTO `source_document` (`id`, `company_id`, `source_document_uuid`, `source_document_type`, `create_time`, `issued_time`, `source_document_status`, `source_account_side`, `booking_amount`, `outlier_document_uuid`, `outlier_trade_time`, `outlier_counter_party_account`, `outlier_counter_party_name`, `outlier_account`, `outlie_account_name`, `outlier_account_id`, `outlier_company_id`, `outlier_serial_global_identity`, `outlier_memo`, `outlier_amount`, `outlier_settlement_modes`, `outlier_breif`, `outlier_account_side`, `appendix`, `first_outlier_doc_type`, `second_outlier_doc_type`, `third_outlier_doc_type`, `currency_type`, `audit_status`) 
VALUES 
('1', '2', 'uuid', '1', '2016-04-21 17:19:23', '2016-04-15 17:19:26', '0', NULL, '0.00', 'uuid', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'offline_bill', NULL, NULL, NULL, '1');

SET FOREIGN_KEY_CHECKS=1; 
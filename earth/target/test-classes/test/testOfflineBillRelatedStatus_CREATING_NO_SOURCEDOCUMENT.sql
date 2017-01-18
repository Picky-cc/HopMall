SET FOREIGN_KEY_CHECKS=0;

delete from  offline_bill;
delete from source_document;

INSERT INTO `offline_bill` (`id`, `amount`, `bank_show_name`, `comment`, `create_time`, `customer_no_list`, `is_delete`, `last_modified_time`, `offline_bill_no`, `offline_bill_status`, `offline_bill_uuid`, `order_no_list`, `serial_no`, `payer_account_name`, `payer_account_no`, `single_loan_contract_no_list`)
VALUES 
('1', '1000.00', '中国银行', '备注', '2016-04-01 14:22:58', '[\"D001\"]', '\0', '2016-04-01 14:23:27', 'BC00001', '1', 'uuid', '[\"DKHD-001-01-20160307\",\"DKHD-001-01-20160308\",\"DKHD-001-01-20160309\"]', 'serial_no', 'payer_account_name', 'account_no', '[\"test-contract-1-1\",\"test-contract-1-2\",\"test-contract-2-1\"]'),
('2', '1000.00', '中国银行', '备注', '2016-04-01 14:22:58', '[\"D001\"]', '\0', '2016-04-01 14:23:27', 'BC0000112312', '1', 'uuid12423', '[\"DKHD-001-01-20160307\",\"DKHD-001-01-20160308\",\"DKHD-001-01-20160309\"]', 'serial_no', 'payer_account_name', 'account_no', '[\"test-contract-1-1\",\"test-contract-1-2\",\"test-contract-2-1\"]');


SET FOREIGN_KEY_CHECKS=1;
SET FOREIGN_KEY_CHECKS=0;

delete from `payment_channel`;
delete from `financial_contract`;
delete from `account`;

INSERT INTO `payment_channel` (`id`, `channel_name`, `user_name`, `user_password`, `merchant_id`, `cer_file_path`, `pfx_file_path`, `pfx_file_key`, `payment_channel_type`, `api_url`, `from_date`, `thru_date`) VALUES
('1', '安美途', 'operator', 'operator', '001053110000001', 'src/main/resources/certificate/gzdsf.cer', 'src/main/resources/certificate/ORA@TEST1.pfx', '123456', '0', 'http://59.41.103.98:333/gzdsf/ProcessServlet', NULL, NULL);

INSERT INTO `financial_contract` (`id`, `asset_package_format`, `adva_matuterm`, `adva_start_date`, `contract_no`, `contract_name`, `app_id`, `company_id`, `adva_repo_term`, `thru_date`, `capital_account_id`, `financial_contract_type`, `loan_overdue_start_day`, `loan_overdue_end_day`, `payment_channel_id`, `ledger_book_no`) VALUES 
('1', '0', '7', '2015-04-02 00:00:00', 'DCF-AMT-LR903A', '安美途', '1', '1', '0', '9999-01-01 00:00:00', '1', '0', '0', '0', '1', NULL);

INSERT INTO `account` (`id`, `account_name`, `account_no`, `bank_name`, `alias`, `attr`, `scan_cash_flow_switch`, `usb_key_configured`) VALUES
('1', 'account_name', 'account_no', '安美途bank_name', NULL,'',0,0),
('2', '银企直连专用账户9', '591902896710201', '银企直连专用账户9', NULL, '{\"bankBranchNo\":\"59\",\"usbUuid\":\"b173f24a-3c27-4243-85b7-e93ef6a128ac\"}', 1, 1);


INSERT INTO `usb_key` (`id`, `bank_code`, `config`, `key_type`, `uuid`) VALUES 
('1', 'CMB', '{\"LGNNAM\":\"银企直连专用集团1\",\"URL\":\"http://192.168.1.154:8080",\"GetTransInfo_Code\":\"GetTransInfo\",\"GetAccInfo_Code\":\"GetAccInfo\",\"GetPaymentInfo_Code\":\"GetPaymentInfo\",\"DCPAYMNT_Code\":\"DCPAYMNT\",\"BUSCOD\":\"N02031\",\"BUSMOD\":\"00001\",\"PAYMENTLOCKED\":false}', '0', 'b173f24a-3c27-4243-85b7-e93ef6a128ac');

SET FOREIGN_KEY_CHECKS=1;
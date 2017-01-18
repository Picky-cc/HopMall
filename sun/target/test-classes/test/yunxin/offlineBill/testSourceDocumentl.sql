SET FOREIGN_KEY_CHECKS=0;

delete from `source_document`;
delete from `offline_bill`;

INSERT INTO `source_document` (`id`, `company_id`, `source_document_uuid`, `source_document_type`, `create_time`, `issued_time`, `source_document_status`, `source_account_side`, `booking_amount`, `outlier_document_uuid`, `outlier_trade_time`, `outlier_counter_party_account`, `outlier_counter_party_name`, `outlier_account`, `outlie_account_name`, `outlier_account_id`, `outlier_company_id`, `outlier_serial_global_identity`, `outlier_memo`, `outlier_amount`, `outlier_settlement_modes`, `outlier_breif`, `outlier_account_side`, `appendix`, `first_outlier_doc_type`, `second_outlier_doc_type`, `third_outlier_doc_type`, `currency_type`, `audit_status`) VALUES 
('1', '1', 'source_document_uuid_1', '1', '2016-05-27 18:32:35', '2016-05-27 18:32:36', '0', '1', '2406.00', 'ed0cd216d03b4a889d023ac20a46880e', '2016-05-27 18:29:50', '6217000000000000000', '测试用户18', 'account_no', 'account_name', '5', '1', '2730FAE730E7B3C6', '', '2406.00', '3', '', '1', '', 'batch_pay_record', '', '', NULL, '2'),
('2', '1', 'source_document_uuid_2', '1', '2016-05-27 18:32:36', '2016-05-27 18:32:36', '1', '1', '1804.50', '883992639dc04cb8b596f26a187d52a3', '2016-05-27 18:29:53', '6217000000000000000', '测试用户19', 'account_no', 'account_name', '5', '1', '2730FAE730EC7B52', '', '1804.50', '3', '', '1', '', 'batch_pay_record', '', '', NULL, '2');



SET FOREIGN_KEY_CHECKS=1;


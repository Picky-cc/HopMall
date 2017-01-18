SET FOREIGN_KEY_CHECKS=0;

delete from  t_deduct_application;


INSERT INTO `t_deduct_application` (`id`, `deduct_application_uuid`, `deduct_id`, `request_no`, `financial_contract_uuid`, `financial_product_code`, `contract_unique_id`, `contract_no`, `planned_deduct_total_amount`, `actual_deduct_total_amount`, `notify_url`, `transcation_type`, `repayment_type`, `execution_status`, `execution_remark`, `create_time`, `creator_name`, `ip`, `last_modified_time`, `record_status`, `is_available`, `api_called_time`,`transaction_recipient`) VALUES ('31', '1602bc9b-8a48-4ac0-874e-b32cdd8a3d9c', '1087e301-bd27-45b4-8368-3137b9e6e12c', '4eecd64a-6f81-44b7-94dd-77f1fb189ed5', '984149f1-cb43-410c-a789-d8f4bba123b6', 'G00001', NULL, '629测试(ZQ2016002000001)', '100.00', '0.00', '', '1', '0', '1', '', '2016-08-25 17:14:31', 't_test_zfb', '127.0.0.1', '2016-08-25 17:14:31', '1', '0', '2016-05-04 00:00:00','0');






SET FOREIGN_KEY_CHECKS=1;
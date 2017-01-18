DELETE FROM `financial_contract`;
DELETE FROM `payment_channel`;
DELETE FROM `principal`;

INSERT INTO `principal` (`id`, `authority`, `name`, `password`, `start_date`, `thru_date`) VALUES 
('1', 'ROLE_SUPER_USER', 'zhushiyun', 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL);


INSERT INTO `financial_contract` (`id`, `adva_matuterm`, `contract_no`, `app_id`, `company_id`,   `adva_repo_term`, `payment_channel_id`) VALUES 
('1', 3, 'YX_AMT_001', 1, 1, 30,1),
('2', 3, 'YX_AMT_002', 2, 1, 30,1);

INSERT INTO `payment_channel` (`id`, `channel_name`, `user_name`, `user_password`, `merchant_id`, `cer_file_path`, `pfx_file_path`, `pfx_file_key`, `payment_channel_type`, `api_url`, `from_date`, `thru_date`) VALUES 
(1, 'channel_name', 'operator', 'operator', '001053110000001', 'src/main/resources/certificate/gzdsf.cer', 'src/main/resources/certificate/ORA@TEST1.pfx', '123456', 0, 'http://59.41.103.98:333/gzdsf/ProcessServlet', NULL, NULL);




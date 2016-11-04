SET FOREIGN_KEY_CHECKS=0;

delete from `t_deduct_application_detail`;

INSERT INTO `t_deduct_application_detail` (`id`, `deduct_application_detail_uuid`, `deduct_application_uuid`, `financial_contract_uuid`, `contract_unique_id`, `repayment_plan_code`, `request_no`, `repayment_type`, `transaction_type`, `create_time`, `execution_remark`, `creator_name`, `last_modify_time`, `first_account_name`, `first_account_uuid`, `second_account_name`, `second_account_uuid`, `third_account_name`, `third_account_uuid`, `account_amount`, `is_total`)
VALUES 
('36', '45c930ba-1a0a-4df7-aa47-f8714af4fdf1', '9d61fa8b-76b4-4ade-b402-b725bc92391a', '6512e636-5231-4082-a0b5-0ce7e71aeae4', NULL, 'ZC27375ACFF4234805', '1', '0', '1', '2016-08-24 02:15:42', '', NULL, '2016-08-24 02:15:42', 'FST_RECIEVABLE_ASSET', '20000', 'SND_RECIEVABLE_LOAN_ASSET', '20000.01', 'TRD_RECIEVABLE_LOAN_ASSET_PRINCIPLE', '20000.01.01', '0.00', '1'),
('37', '45c930ba-1a0a-4df7-aa47-f8714af4fdf1', '9d61fa8b-76b4-4ade-b402-b725bc92391a', '6512e636-5231-4082-a0b5-0ce7e71aeae4', NULL, 'ZC27375ACFF4234805', '1', '0', '1', '2016-08-24 02:15:42', '', NULL, '2016-08-24 02:15:42', 'FST_RECIEVABLE_ASSET', '20000', 'SND_RECIEVABLE_LOAN_ASSET', '20000.01', 'TRD_RECIEVABLE_LOAN_ASSET_INTEREST', '20000.01.02', '100.00', '1'),
('38', '45c930ba-1a0a-4df7-aa47-f8714af4fdf1', '9d61fa8b-76b4-4ade-b402-b725bc92391a', '6512e636-5231-4082-a0b5-0ce7e71aeae4', NULL, 'ZC27375ACFF4234805', '1', '0', '1', '2016-08-24 02:15:42', '', NULL, '2016-08-24 02:15:42', 'FST_RECIEVABLE_ASSET', '20000', 'SND_RECIEVABLE_LOAN_ASSET', '20000.01', 'TRD_RECIEVABLE_LOAN_ASSET_TECH_FEE', '20000.01.04', '0.00', '1'),
('39', '45c930ba-1a0a-4df7-aa47-f8714af4fdf1', '9d61fa8b-76b4-4ade-b402-b725bc92391a', '6512e636-5231-4082-a0b5-0ce7e71aeae4', NULL, 'ZC27375ACFF4234805', '1', '0', '1', '2016-08-24 02:15:42', '', NULL, '2016-08-24 02:15:42', 'FST_RECIEVABLE_ASSET', '20000', 'SND_RECIEVABLE_LOAN_ASSET', '20000.01', 'TRD_RECIEVABLE_LOAN_ASSET_LOAN_SERVICE_FEE', '20000.01.03', '0.00', '1'),
('40', '45c930ba-1a0a-4df7-aa47-f8714af4fdf1', '9d61fa8b-76b4-4ade-b402-b725bc92391a', '6512e636-5231-4082-a0b5-0ce7e71aeae4', NULL, 'ZC27375ACFF4234805', '1', '0', '1', '2016-08-24 02:15:42', '', NULL, '2016-08-24 02:15:42', 'FST_RECIEVABLE_ASSET', '20000', 'SND_RECIEVABLE_LOAN_ASSET', '20000.01', 'TRD_RECIEVABLE_LOAN_ASSET_OTHER_FEE', '20000.01.05', '0.00', '1'),
('41', '45c930ba-1a0a-4df7-aa47-f8714af4fdf1', '9d61fa8b-76b4-4ade-b402-b725bc92391a', '6512e636-5231-4082-a0b5-0ce7e71aeae4', NULL, 'ZC27375ACFF4234805', '1', '0', '1', '2016-08-24 02:15:42', '', NULL, '2016-08-24 02:15:42', 'FST_RECIEVABLE_ASSET', '20000', 'SND_RECIEVABLE_LOAN_PENALTY', '20000.03', NULL, NULL, '0.00', '1'),
('42', '45c930ba-1a0a-4df7-aa47-f8714af4fdf1', '9d61fa8b-76b4-4ade-b402-b725bc92391a', '6512e636-5231-4082-a0b5-0ce7e71aeae4', NULL, 'ZC27375ACFF4234805', '1', '0', NULL, '2016-08-24 02:15:42', '', NULL, '2016-08-24 02:15:42', NULL, NULL, NULL, NULL, NULL, NULL, '100.00', '0');



SET FOREIGN_KEY_CHECKS=1;
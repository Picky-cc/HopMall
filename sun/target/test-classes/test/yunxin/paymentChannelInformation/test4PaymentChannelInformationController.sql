SET FOREIGN_KEY_CHECKS=0;

delete from `bank_transaction_limit_sheet`;

INSERT INTO `bank_transaction_limit_sheet` (`id`, `bank_transaction_limit_sheet_uuid`, `payment_institution_name`, `outlier_channel_name`, `payment_channel_information_uuid`, `payment_channel_information_service_uuid`, `account_side`, `bank_code`, `bank_name`, `transaction_limit_per_transcation`, `transcation_limit_per_day`, `transaction_limit_per_month`, `working_mode`, `creat_time`, `last_modified_time`, `invalid_time`) 
VALUES ('1', 'f76fcd2d-4bdd-4ffe-aeef-e0d6747bb2db', '2', '19014526016005', NULL, NULL, '0', 'C10828', '平安银行', '1000.10', '10000.02', '1000000.03', NULL, '2016-10-14 17:53:29', '2016-10-14 17:53:29', '2016-10-14 17:55:47'),
       ('2', '59d16a1e-bf82-4c87-bb8e-b051b4ea15ba', '2', '19014526016005', NULL, NULL, '0', 'C10828', '平安银行', '1000.10', '10000.02', '1000000.03', NULL, '2016-10-14 17:55:48', '2016-10-14 17:55:48', '2016-10-14 17:56:01'),
       ('3', 'bd3eb3d7-9166-4f33-88e4-ae5587f639c7', '1', '19014526016005', NULL, NULL, '0', 'C10828', '平安银行', '1000.10', '10000.02', '1000000.03', NULL, '2016-10-14 17:56:02', '2016-10-14 17:56:02', NULL),
       ('4', 'bd3eb3d7-9166-4f33-88e4-ae5587f639c8', '2', '19014526016005', NULL, NULL, '0', 'C10828', '平安银行', '1000.10', '10000.02', '1000000.03', NULL, '2016-10-14 17:56:02', '2016-10-14 17:56:02', NULL),
       ('5', 'bd3eb3d7-9166-4f33-88e4-ae5587f639c9', '2', '19014526016004', NULL, NULL, '0', 'C10828', '平安银行', '1000.10', '10000.02', '1000000.03', NULL, '2016-10-14 17:56:02', '2016-10-14 17:56:02', NULL),
       ('6', 'bd3eb3d7-9166-4f33-88e4-ae5587f639ca', '2', '19014526016004', NULL, NULL, '1', 'C10828', '平安银行', '1000.10', '10000.02', '1000000.03', NULL, '2016-10-14 17:56:02', '2016-10-14 17:56:02', NULL),
       ('7', 'bd3eb3d7-9166-4f33-88e4-ae5587f639cb', '2', '19014526016004', NULL, NULL, '1', 'C10308', '招商银行', '1000.10', '10000.02', '1000000.03', NULL, '2016-10-14 17:56:02', '2016-10-14 17:56:02', NULL);
SET FOREIGN_KEY_CHECKS=1;


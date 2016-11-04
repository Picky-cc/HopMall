DELETE FROM `financial_contract`;
DELETE FROM `asset_package`;
DELETE FROM `contract`;
DELETE FROM `asset_set`;

INSERT INTO `financial_contract` (`id`, `asset_package_format`, `adva_matuterm`, `adva_start_date`, `contract_no`, `contract_name`, `app_id`, `company_id`, `adva_repo_term`, `thru_date`, `capital_account_id`, `financial_contract_type`, `loan_overdue_start_day`, `loan_overdue_end_day`, `payment_channel_id`, `ledger_book_no`, `financial_contract_uuid`)
VALUES
	(1, 1, 3, '2016-04-01 00:00:00', 'nfqtest001', '测试合同', 1, 1, 91, '2016-07-01 00:00:00', 5, 0, 5, 90, 2, '74a9ce4d-cafc-407d-b013-987077541bdc', '2d380fe1-7157-490d-9474-12c5a9901e29');

INSERT INTO `asset_set` (`id`, `guarantee_status`, `settlement_status`, `asset_fair_value`, `asset_principal_value`, `asset_interest_value`, `asset_initial_value`, `asset_recycle_date`, `confirm_recycle_date`, `refund_amount`, `asset_status`, `on_account_status`, `repayment_plan_type`, `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `comment`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `current_period`, `overdue_status`, `overdue_date`)
VALUES
	(1, 2, 0, 1236.00, 400.00, 800.00, 1200.00, '2016-05-17', NULL, 0.00, 1, 1, 0, '2016-07-20 03:00:08', '18b302ac-281d-4b39-b906-cded34b42b58', '2016-05-27 18:27:16', '2016-07-20 03:00:08', NULL, 'ZC2730FAE4092E0A6E', 1, '2016-05-17 00:00:00', 1, 1, NULL),
	(2, 2, 0, 1236.00, 600.00, 600.00, 1200.00, '2016-05-17', NULL, 0.00, 1, 1, 0, '2016-07-20 03:00:08', 'b2453ef0-853a-47a7-a41e-de6fe2bad389', '2016-05-27 18:27:16', '2016-07-20 03:00:08', NULL, 'ZC2730FAE4095260A1', 2, '2016-05-17 00:00:00', 1, 1, NULL),
	(3, 2, 0, 2472.00, 1200.00, 1200.00, 2400.00, '2016-06-17', NULL, 0.00, 0, 1, 0, '2016-07-20 03:00:09', '57d007bc-d590-492c-9622-3689cc552539', '2016-05-27 18:27:16', '2016-07-20 03:00:09', NULL, 'ZC2730FAE409733A89', 3, NULL, 1, 1, NULL),
	(4, 2, 0, 2163.00, 1500.00, 600.00, 2100.00, '2016-07-17', NULL, 0.00, 0, 1, 0, '2016-07-20 03:00:09', '61f5a455-93ab-4c14-9cf6-45ab32da456a', '2016-05-27 18:27:16', '2016-07-20 03:00:09', NULL, 'ZC2730FAE409912CBB', 4, NULL, 1, 1, NULL);

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `end_date`, `asset_type`, `month_fee`, `app_id`, `customer_id`, `house_id`, `actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`,`financial_contract_uuid`)
VALUES
	(1, '2016-04-17', '2016-78-DK(ZQ2016042522479)', NULL, 1, 0.00, 1, 1, 162, NULL, '2016-05-27 18:27:16', 0.1560000000, 0, 0, 1, 2, 1200.00, 0.0005000000,'2d380fe1-7157-490d-9474-12c5a9901e29'),
	(2, '2016-04-17', '2016-78-DK(ZQ2016042422395)', NULL, 1, 0.00, 1, 2, 163, NULL, '2016-05-27 18:27:16', 0.1560000000, 0, 0, 1, 2, 1200.00, 0.0005000000,'2d380fe1-7157-490d-9474-12c5a9901e29'),
	(3, '2016-04-17', '2016-78-DK(ZQ2016042522502)', NULL, 1, 0.00, 1, 3, 164, NULL, '2016-05-27 18:27:16', 0.1560000000, 0, 0, 1, 2, 2400.00, 0.0005000000,'2d380fe1-7157-490d-9474-12c5a9901e29'),
	(4, '2016-07-17', '2016-78-DK(ZQ2016042622566)', NULL, 1, 0.00, 1, 4, 165, NULL, '2016-05-27 18:27:16', 0.1560000000, 0, 0, 1, 2, 2100.00, 0.0005000000,'2d380fe1-7157-490d-9474-12c5a9901e29');

INSERT INTO `asset_package` (`id`, `is_available`, `create_time`, `contract_id`, `asset_package_no`, `financial_contract_id`, `loan_batch_id`)
VALUES
	(1, NULL, NULL, 1, NULL, 1, 1),
	(2, NULL, NULL, 2, NULL, 1, 1),
	(3, NULL, NULL, 3, NULL, 1, 1),
	(4, NULL, NULL, 4, NULL,1, 1);

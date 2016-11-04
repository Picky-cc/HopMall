

SET FOREIGN_KEY_CHECKS=0;

DELETE FROM `asset_set`;
DELETE FROM `t_interface_data_sync_log`;
DELETE FROM `contract`;
DELETE FROM `asset_package`;
DELETE FROM `financial_contract`;

INSERT INTO `contract` (`id`, `uuid`, `unique_id`, `begin_date`, `contract_no`, `end_date`, `asset_type`, `month_fee`, `app_id`, `customer_id`, `house_id`, `actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`, `active_version_no`, `repayment_plan_operate_logs`, `state`, `financial_contract_uuid`)
VALUES
	(24, 'a0afda94-5fa8-11e6-b2c2-00163e002839', 'a0fda94-5fa8-11e6-b2c2-00163e002839', '2016-04-19', '2016-78-DK(ZQ2016042522537)', NULL, 1, 0.00, 1, 9, 170, NULL, '2016-05-27 18:27:16', 0.1560000000, 0, 0, 1, 2, 1800.00, 0.0005000000, 1, NULL, 2, NULL);



INSERT INTO `asset_package` (`id`, `is_available`, `create_time`, `contract_id`, `asset_package_no`, `financial_contract_id`, `loan_batch_id`)
VALUES
	(24, NULL, NULL, 24, NULL, 5, 1);

INSERT INTO `financial_contract` (`id`, `asset_package_format`, `adva_matuterm`, `adva_start_date`, `contract_no`, `contract_name`, `app_id`, `company_id`, `adva_repo_term`, `thru_date`, `capital_account_id`, `financial_contract_type`, `loan_overdue_start_day`, `loan_overdue_end_day`, `payment_channel_id`, `ledger_book_no`, `financial_contract_uuid`, `sys_normal_deduct_flag`, `sys_overdue_deduct_flag`, `sys_create_penalty_flag`, `sys_create_guarantee_flag`, `unusual_modify_flag`)
VALUES
	(5, 1, 3, '2016-04-01 00:00:00', 'nfqtest001', '测试合同', 1, 1, 91, '2016-07-01 00:00:00', 5, 0, 5, 90, 2, '74a9ce4d-cafc-407d-b013-987077541bdc', '2d380fe1-7157-490d-9474-12c5a9901e29', 0, 0, 0, 0, 0);


INSERT INTO `asset_set` (`id`, `guarantee_status`, `settlement_status`, `asset_fair_value`, `asset_principal_value`, `asset_interest_value`, `asset_initial_value`, `asset_recycle_date`, `confirm_recycle_date`, `refund_amount`, `asset_status`, `on_account_status`, `repayment_plan_type`, `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `comment`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `current_period`, `overdue_status`, `overdue_date`, `version_no`, `active_status`, `extra_charge`)
VALUES
	(18, 0, 0, 2406.00, 0.00, 2400.00, 2400.00, '2016-05-18', '2016-05-27', 0.00, 1, 2, 0, '2016-05-27 18:28:01', '90d22404-99ea-4b5a-872c-5ffc6c7909fd', '2016-05-27 18:27:17', '2016-05-27 18:30:23', NULL, 'ZC2730FAE40B0F2478', 24, '2016-05-27 18:21:04', 1, 0, NULL, 1, 0, NULL);


INSERT INTO `t_interface_data_sync_log` (`id`,`data_sync_log_uuid`, `product_id`, `contract_no`, `contract_end_date`, `contract_unique_id`, `repayment_plan_no`, `asset_set_uuid`, `contract_flag`, `repay_type`, `plan_repay_date`, `actual_repay_date`, `data_sync_big_decimal_details`, `create_time`, `last_modify_time`, `is_success`,`return_message`,`request_message`)
VALUES
('1', '1c5bba-6514-4657-9f3f-2f197e894191',NULL, NULL, NULL, NULL, NULL, 'assetSetUuid1', NULL, '0', NULL, NULL, NULL, NULL, NULL, '1','asdsa','dfghjk');
SET FOREIGN_KEY_CHECKS=1;

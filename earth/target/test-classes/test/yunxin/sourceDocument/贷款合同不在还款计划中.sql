-- 明细金额大于未还金额 错误
delete from `source_document_detail`;
INSERT INTO `source_document_detail` (`id`, `uuid`, `source_document_uuid`, `contract_unique_id`, `repayment_plan_no`, `amount`, `status`, `first_type`, `first_no`, `second_type`, `second_no`, `payer`, `receivable_account_no`, `payment_account_no`, `payment_name`, `payment_bank`, `check_state`, `comment`)
VALUES
	(1, 'e25b3d36-d011-4fc1-9caf-70505bda6531', '8d6e74ae-a04d-4505-8d89-7d0d545a255b', '26055d9e-8798-47dd-9b55-2c3f2e56223d', 'ZC274850A29B26503A', 20000, 0, 'enum.voucher-source.business-payment-voucher', '66ec97e2-58d6-400b-b7fe-72baceb42f65', 'enum.voucher-type.pay', 'bank_transaction_no_10000', 0, 'yunxin_account_no', '10001', 'counter_name', 'account_account_name', 0, NULL);

DELETE FROM `financial_contract`;
INSERT INTO `financial_contract` (`id`, `asset_package_format`, `adva_matuterm`, `adva_start_date`, `contract_no`, `contract_name`, `app_id`, `company_id`, `adva_repo_term`, `thru_date`, `capital_account_id`, `financial_contract_type`, `loan_overdue_start_day`, `loan_overdue_end_day`, `payment_channel_id`, `ledger_book_no`, `financial_contract_uuid`, `sys_normal_deduct_flag`, `sys_overdue_deduct_flag`, `sys_create_penalty_flag`, `sys_create_guarantee_flag`)
VALUES
	(5, 0, 5, '2016-08-02 00:00:00', 'test_cache2', '测试缓存2', 1, 1, 5, '2016-08-02 00:00:00', 6, 0, 3, 1, 1, 'f8ef10a7-71a7-4960-ae29-e61c69a4b85b', '69f8b0a1-001a-4182-ae06-1730ce297fd6', 0, 0, 0, 0);

DELETE FROM `app`;
INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `addressee`, `pub_key_path`, `pub_key`)
VALUES
	(1, 'anmeitu', '11111db75ebb24fa0993f4fa25775021', 1, 'http://e.zufangbao.cn', '安美途', 3, NULL, NULL, NULL),
	(2, 'nongfenqi', '11111db75ebb24fa0993f4fa25775022', 0, 'http://e.zufangbao.cn', '农分期', 4, NULL, NULL, NULL),
	(3, 'maidanxia', '11111db75ebb24fa0993f4fa25775023', 1, 'http://e.zufangbao.cn', '买单侠', 5, NULL, NULL, NULL),
	(4, 'yunxin', NULL, 0, NULL, '云信', 1, NULL, NULL, NULL);
	
DELETE FROM `company`;
INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`, `uuid`)
VALUES
	(1, '上海', '云南国际信托有限公司', '云南信托', 'c58a824d-6f6e-11e6-8c6a-b083fe8d3abe'),
	(2, '杭州', '杭州随地付有限公司', '随地付', 'c58a9371-6f6e-11e6-8c6a-b083fe8d3abe'),
	(3, '杭州', '安美途', '安美途', 'c58a93ee-6f6e-11e6-8c6a-b083fe8d3abe'),
	(4, '杭州', '农分期', '农分期', 'c58aa562-6f6e-11e6-8c6a-b083fe8d3abe'),
	(5, '杭州', '买单侠', '买单侠', 'c58aa5d4-6f6e-11e6-8c6a-b083fe8d3abe');

DELETE FROM `account`;
INSERT INTO `account` (`id`, `account_name`, `account_no`, `bank_name`, `alias`, `attr`, `scan_cash_flow_switch`, `usb_key_configured`, `uuid`, `bank_code`)
VALUES
	(6, '云南信托', 'yunxin_account_no', '中国银行', NULL, NULL, 0, 0, '9de211f4-eae5-4d0d-bc04-0d8d6870e547', NULL);

DELETE FROM `customer`;
INSERT INTO `customer` (`id`, `account`, `mobile`, `name`, `source`, `app_id`, `customer_uuid`, `customer_type`)
VALUES
	(37, NULL, NULL, NULL, NULL, 1, 'compay_customerUuid2', 1);

DELETE FROM `contract`;
INSERT INTO `contract` (`id`, `uuid`, `unique_id`, `begin_date`, `contract_no`, `end_date`, `asset_type`, `month_fee`, `app_id`, `customer_id`, `house_id`, `actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`, `active_version_no`, `repayment_plan_operate_logs`, `state`, `financial_contract_uuid`)
VALUES
	(35, '51cce865-1313-4f36-97ea-ebfca2e653ab', NULL, '2016-08-01', 'contractNo3', NULL, NULL, 0.00, 2, 35, 35, NULL, '2016-08-15 20:16:28', 0.1560000000, 0, 0, 2, 2, 4000.00, 0.0005000000, 2, '[{\"content\":{0:\"ZC2742FC0CADC152AB,ZC2742FC0CADD090BB\",1:\"ZC2742FC04978D10A5,ZC2742FC049790FB5A\",2:\"\"},\"ipAddress\":\"127.0.0.1\",\"occurTime\":\"2016-08-15 20:21:57\",\"triggerEvent\":1},{\"content\":{0:\"\",1:\"\",2:\"ZC2742FC1580315690\"},\"ipAddress\":\"127.0.0.1\",\"occurTime\":\"2016-08-15 20:27:35\",\"triggerEvent\":2},{\"content\":{0:\"\",1:\"ZC2742FC1580315690\",2:\"\"},\"occurTime\":\"2016-09-02 01:12:05\",\"operatorName\":\"系统\",\"triggerEvent\":5}]', 2, NULL),
	(36, '5b765309-3714-49c0-a092-c0c826053d9e', '26055d9e-8798-47dd-9b55-2c3f2e56223d', '2016-08-01', 'contract20160830', '2099-01-01', NULL, 0.00, 1, 38, 36, NULL, '2016-09-01 01:06:56', 0.1560000000, 0, 0, 1, 2, 10000.00, 0.0005000000, 1, NULL, 2, '69f8b0a1-001a-4182-ae06-1730ce297fd6');

DELETE FROM `asset_package`;
INSERT INTO `asset_package` (`id`, `is_available`, `create_time`, `contract_id`, `asset_package_no`, `financial_contract_id`, `loan_batch_id`)
VALUES
	(32, NULL, NULL, 35, NULL, 4, 17),
	(33, NULL, NULL, 36, NULL, 5, 18);

DELETE FROM `loan_batch`;
INSERT INTO `loan_batch` (`id`, `is_available`, `code`, `create_time`, `financial_contract_id`, `last_modified_time`, `loan_date`, `loan_batch_uuid`)
VALUES
	(18, 1, 'test_cache2 20160901 01:06:68', '2016-09-01 01:06:56', 5, '2016-09-01 01:06:56', '2016-09-01 01:06:56', 'baf6e056-d96b-48a7-9d8e-d5480cf952d6');

DELETE FROM `ledger_book`;
INSERT INTO `ledger_book` (`id`, `ledger_book_no`, `ledger_book_orgnization_id`, `book_master_id`, `party_concerned_ids`)
VALUES
	(4, 'f8ef10a7-71a7-4960-ae29-e61c69a4b85b', '1', NULL, NULL);

DELETE FROM `contract`;
INSERT INTO `contract` (`id`, `uuid`, `unique_id`, `begin_date`, `contract_no`, `end_date`, `asset_type`, `month_fee`, `app_id`, `customer_id`, `house_id`, `actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`, `active_version_no`, `repayment_plan_operate_logs`, `state`, `financial_contract_uuid`)
VALUES
	(35, '51cce865-1313-4f36-97ea-ebfca2e653ab', 'contract_unique_id', '2016-08-01', 'contractNo3', NULL, NULL, 0.00, 2, 35, 35, NULL, '2016-08-15 20:16:28', 0.1560000000, 0, 0, 2, 2, 4000.00, 0.0005000000, 2, '[{\"content\":{0:\"ZC2742FC0CADC152AB,ZC2742FC0CADD090BB\",1:\"ZC2742FC04978D10A5,ZC2742FC049790FB5A\",2:\"\"},\"ipAddress\":\"127.0.0.1\",\"occurTime\":\"2016-08-15 20:21:57\",\"triggerEvent\":1},{\"content\":{0:\"\",1:\"\",2:\"ZC2742FC1580315690\"},\"ipAddress\":\"127.0.0.1\",\"occurTime\":\"2016-08-15 20:27:35\",\"triggerEvent\":2},{\"content\":{0:\"\",1:\"ZC2742FC1580315690\",2:\"\"},\"occurTime\":\"2016-09-02 01:12:05\",\"operatorName\":\"系统\",\"triggerEvent\":5}]', 2, NULL),
	(36, '5b765309-3714-49c0-a092-c0c826053d9e', '26055d9e-8798-47dd-9b55-2c3f2e56223d', '2016-08-01', 'contract20160830', '2099-01-01', NULL, 0.00, 1, 38, 36, NULL, '2016-09-01 01:06:56', 0.1560000000, 0, 0, 1, 2, 10000.00, 0.0005000000, 1, NULL, 2, '69f8b0a1-001a-4182-ae06-1730ce297fd6');

DELETE FROM `asset_set`;
INSERT INTO `asset_set` (`id`, `guarantee_status`, `settlement_status`, `asset_fair_value`, `asset_principal_value`, `asset_interest_value`, `asset_initial_value`, `asset_recycle_date`, `confirm_recycle_date`, `refund_amount`, `asset_status`, `on_account_status`, `repayment_plan_type`, `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `comment`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `current_period`, `overdue_status`, `overdue_date`, `version_no`, `active_status`, `extra_charge`)
VALUES
	(43, 0, 0, 100.00, 0.00, 100.00, 0.00, '2016-09-04', NULL, 0.00, 0, 1, 0, NULL, '533975ce-8e79-4a42-9c20-6550935f3fac', '2016-08-15 20:16:28', '2016-08-15 20:16:28', NULL, 'ZC2742FC04978D10A5', 35, NULL, 1, 0, NULL, 1, 1, '{\"loanServiceFee\":\"0.00\",\"otheFee\":\"0.00\",\"techMaintenanceFee\":\"0.00\"}'),
	(44, 0, 0, 50.00, 0.00, 50.00, 0.00, '2016-10-04', NULL, 0.00, 0, 1, 0, NULL, '06c6790d-4b3c-4e0a-9e9d-7a7130491caf', '2016-08-15 20:16:28', '2016-08-15 20:16:28', NULL, 'ZC2742FC049790FB5A', 35, NULL, 2, 0, NULL, 1, 1, '{\"loanServiceFee\":\"0.00\",\"otheFee\":\"0.00\",\"techMaintenanceFee\":\"0.00\"}'),
	(45, 0, 0, 150.00, 150.00, 150.00, 150.00, '2018-02-02', NULL, 0.00, 0, 1, 0, NULL, 'e8f1c2a6-1de1-466c-9f14-ba7b924bf9c6', '2016-08-15 20:21:58', '2016-08-15 20:21:58', NULL, 'ZC2742FC0CADC152AB', 35, NULL, 1, 0, NULL, 2, 0, NULL),
	(46, 0, 0, 450.00, 300.00, 50.00, 50.00, '2019-02-02', NULL, 0.00, 0, 1, 0, NULL, '4092a940-af73-412d-b3cd-673be6ea0292', '2016-08-15 20:21:58', '2016-08-15 20:21:58', NULL, 'ZC2742FC0CADD090BB', 35, NULL, 2, 0, NULL, 2, 0, NULL),
	(47, 0, 0, 600.00, 450.00, 150.00, 600.00, '2016-08-16', NULL, 0.00, 0, 1, 1, NULL, '7277243d-6ca3-420b-849c-9e40b07eb118', '2016-08-15 20:27:36', '2016-08-15 20:27:36', NULL, 'ZC2742FC1580315690', 35, NULL, 1, 0, NULL, 2, 1, NULL),
	(48, 0, 0, 11000.00, 10000.00, 1000.00, 11000.00, '2016-09-02', '2016-09-02', 0.00, 1, 2, 0, '2016-09-02 01:12:08', '93e3136d-e371-472b-bd14-9441b3b207d0', '2016-09-01 01:06:57', '2016-09-02 01:22:30', NULL, 'ZC274850A29B26503A', 36, '2016-09-02 01:22:30', 1, 0, NULL, 1, 0, '{\"loanServiceFee\":\"0.00\",\"otheFee\":\"0.00\",\"techMaintenanceFee\":\"0.00\",\"totalOverduFee\":\"0.00\"}');

DELETE FROM `ledger_book_shelf`;
INSERT INTO `ledger_book_shelf` (`id`, `ledger_uuid`, `debit_balance`, `credit_balance`, `first_account_name`, `first_account_uuid`, `account_side`, `second_account_name`, `second_account_uuid`, `third_account_name`, `third_account_uuid`, `first_party_id`, `second_party_id`, `third_party_id`, `forward_ledger_uuid`, `backward_ledger_uuid`, `batch_serial_uuid`, `amortized_date`, `book_in_date`, `business_voucher_uuid`, `carried_over_date`, `contract_id`, `contract_uuid`, `default_date`, `journal_voucher_uuid`, `ledger_book_no`, `ledger_book_owner_id`, `life_cycle`, `related_lv_1_asset_outer_idenity`, `related_lv_1_asset_uuid`, `related_lv_2_asset_outer_idenity`, `related_lv_2_asset_uuid`, `related_lv_3_asset_outer_idenity`, `related_lv_3_asset_uuid`, `source_document_uuid`)
VALUES
	(415, 'a8556606-ef16-4709-897e-5488746be2f9', 10000.00, 0.00, 'FST_RECIEVABLE_ASSET', '20000', 1, 'SND_RECIEVABLE_LOAN_ASSET', '20000.01', 'TRD_RECIEVABLE_LOAN_ASSET_PRINCIPLE', '20000.01.01', 'c58a824d-6f6e-11e6-8c6a-b083fe8d3abe', 'b4cc5b05-f4ea-4010-b0e6-45d9cc9c6001', NULL, NULL, '50f1f745-c827-4981-b6b3-f75c8d19b5fb', '40ce246c-4646-4fcd-a09d-f2d1a491821e', '2016-09-02', '2016-09-02 01:12:08', NULL, NULL, 36, '5b765309-3714-49c0-a092-c0c826053d9e', '2016-09-02 00:00:00', NULL, 'f8ef10a7-71a7-4960-ae29-e61c69a4b85b', '1', 1, 'ZC274850A29B26503A', '93e3136d-e371-472b-bd14-9441b3b207d0', NULL, NULL, NULL, NULL, NULL),
	(417, 'f4e5cdff-4208-4e5f-b14b-6dc0e925d876', 1000.00, 0.00, 'FST_RECIEVABLE_ASSET', '20000', 1, 'SND_RECIEVABLE_LOAN_ASSET', '20000.01', 'TRD_RECIEVABLE_LOAN_ASSET_INTEREST', '20000.01.02', 'c58a824d-6f6e-11e6-8c6a-b083fe8d3abe', 'b4cc5b05-f4ea-4010-b0e6-45d9cc9c6001', NULL, NULL, 'b90cb355-5577-4c1a-86ae-91f797a52851', 'fa60eb34-2d58-4542-b17e-97f9f97f61e4', '2016-09-02', '2016-09-02 01:12:08', NULL, NULL, 36, '5b765309-3714-49c0-a092-c0c826053d9e', '2016-09-02 00:00:00', NULL, 'f8ef10a7-71a7-4960-ae29-e61c69a4b85b', '1', 1, 'ZC274850A29B26503A', '93e3136d-e371-472b-bd14-9441b3b207d0', NULL, NULL, NULL, NULL, NULL);

DELETE FROM `source_document`;
INSERT INTO `source_document` (`id`, `company_id`, `source_document_uuid`, `source_document_type`, `create_time`, `issued_time`, `source_document_status`, `source_account_side`, `booking_amount`, `outlier_document_uuid`, `outlier_trade_time`, `outlier_counter_party_account`, `outlier_counter_party_name`, `outlier_account`, `outlie_account_name`, `outlier_account_id`, `outlier_company_id`, `outlier_serial_global_identity`, `outlier_memo`, `outlier_amount`, `outlier_settlement_modes`, `outlier_breif`, `outlier_account_side`, `appendix`, `first_outlier_doc_type`, `second_outlier_doc_type`, `third_outlier_doc_type`, `currency_type`, `audit_status`, `first_party_id`, `second_party_id`, `virtual_account_uuid`, `first_account_id`, `second_account_id`, `third_account_id`, `excute_status`, `excute_result`, `related_contract_uuid`, `financial_contract_uuid`, `source_document_no`, `first_party_type`, `first_party_name`, `virtual_account_no`)
VALUES
	(24, 1, '8d6e74ae-a04d-4505-8d89-7d0d545a255b', 1, '2016-09-01 00:57:43', NULL, 1, 1, 0.00, 'cash_flow_uuid_12', '2016-09-01 00:02:02', '10001', 'counter_name', 'yunxin_account_no', NULL, NULL, 1, NULL, NULL, 20000.00, 3, NULL, 1, NULL, 'FST_LIABILITIES_OF_INDEPENDENT_ACCOUNTS', 'SND_LIABILITIES_INDEPENDENT_CUSTOMER_DEPOSIT', '', NULL, 1, 'compay_customerUuid2', '1', 'cfa28906-adb0-4b81-a6c2-d84fe4033947', '50000', '50000.01', '', 2, 1, NULL, '69f8b0a1-001a-4182-ae06-1730ce297fd6', 'CZ27485057A2238D68', '1', NULL, 'VACC27438B136F445B5F');

DELETE FROM `cash_flow`;
INSERT INTO `cash_flow` (`id`, `cash_flow_uuid`, `cash_flow_channel_type`, `company_uuid`, `host_account_uuid`, `host_account_no`, `host_account_name`, `counter_account_no`, `counter_account_name`, `counter_account_appendix`, `counter_bank_info`, `account_side`, `transaction_time`, `transaction_amount`, `balance`, `transaction_voucher_no`, `bank_sequence_no`, `remark`, `other_remark`, `strike_balance_status`, `trade_uuid`, `issued_amount`, `audit_status`, `date_field_one`, `date_field_two`, `date_field_three`, `long_field_one`, `long_field_two`, `long_field_three`, `string_field_one`, `string_field_two`, `string_field_three`, `decimal_field_one`, `decimal_field_two`, `decimal_field_three`)
VALUES
	(1, 'cash_flow_uuid_12', 0, 'company_uuid_1', 'd0503298-e890-425a-b5b4-12', 'yunxin_account_no', '测试专户开户行', '10001', 'counter_name', NULL, NULL, 1, '2016-09-01 00:02:02', 20000.00, 20000.00, NULL, 'cash_flow_no_12', NULL, NULL, NULL, NULL, 20000.00, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

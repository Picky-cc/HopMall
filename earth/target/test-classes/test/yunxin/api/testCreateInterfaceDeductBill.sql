SET FOREIGN_KEY_CHECKS=0;
delete from `app`;
delete from `contract`;
delete from `customer`;
delete from `house`;
delete from `company`;
delete from `asset_package`;
delete from `asset_set`;
delete from `contract_account`;
delete from t_deduct_application;



INSERT INTO `contract` (`id`, `unique_id`, `begin_date`, `contract_no`, `end_date`, `asset_type`, `month_fee`, `app_id`, `customer_id`, `house_id`, `actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`,`active_version_no`)
VALUES
	(24, NULL, '2016-04-17', '629测试(ZQ2016002000001)', NULL, 1, 0.00, 2, 116, 116, NULL, '2016-06-29 18:14:01', 0.1560000000, 0, 0, 1, 2, 100.00, 0.0005000000,1),
	(25, NULL, '2016-04-17', '629测试(ZQ2016002000002)', NULL, 1, 0.00, 2, 116, 116, NULL, '2016-06-29 18:14:01', 0.1560000000, 0, 0, 1, 2, 100.00, 0.0005000000,1);

INSERT INTO `asset_set` (`id`, `guarantee_status`, `settlement_status`, `asset_fair_value`, `asset_principal_value`, `asset_interest_value`, `asset_initial_value`, `asset_recycle_date`, `confirm_recycle_date`, `refund_amount`, `asset_status`, `on_account_status`, `repayment_plan_type`, `last_valuation_time`, `asset_uuid`, `create_time`, `last_modified_time`, `comment`, `single_loan_contract_no`, `contract_id`, `actual_recycle_date`, `current_period`, `overdue_status`, `overdue_date`,`version_no`, `active_status`)
VALUES
	(25, 1, 0, 200.20, 0.00, 200.00, 200.00, '2016-06-01', NULL, 0.00, 0, 1, 0, '2016-06-30 14:03:03', '88450378-e6fd-4857-9562-22971b05b932', '2016-06-29 18:14:01', '2016-06-30 14:03:03', NULL, 'ZC27375ACFF4234805', 25, NULL, 1, 1, NULL,1, 0),
	(24, 1, 0, 200.20, 0.00, 200.00, 200.00, '2016-06-01', NULL, 0.00, 0, 1, 0, '2016-06-30 14:03:03', '88450378-e6fd-4857-9562-22971b05b932', '2016-06-29 18:14:01', '2016-06-30 14:03:03', NULL, 'ZC27375ACFF4234805', 24, NULL, 1, 1, NULL,1, 1);

	
INSERT INTO `asset_package` (`id`, `is_available`, `create_time`, `contract_id`, `asset_package_no`, `financial_contract_id`, `loan_batch_id`)
VALUES
	(47, NULL, NULL, 24, NULL, 14, 14),
	(48, NULL, NULL, 25, NULL, 14, 14);
	
INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `addressee`)
VALUES
	(2, 'nongfenqi', '11111db75ebb24fa0993f4fa25775022', 00000000, 'http://e.zufangbao.cn', '农分期', 4, NULL);

INSERT INTO `customer` (`id`, `account`, `mobile`, `name`, `source`, `app_id`, `customer_uuid`)
VALUES
	(116, NULL, NULL, '测试用户00101', 'C74211', 2, '4faef2f9-1155-4ca8-bd22-bf6230bbc72c');

INSERT INTO `house` (`id`, `address`, `app_id`)
VALUES
	(116, NULL, 2);
	
INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`)
VALUES
	(4, '杭州', '南京农纷期电子商务有限公司', '农分期');
	
	INSERT INTO `contract_account` (`id`, `pay_ac_no`, `bankcard_type`, `contract_id`, `payer_name`, `bank`, `bind_id`, `id_card_num`, `bank_code`, `province`, `city`, `from_date`, `thru_date`)
VALUES 
	(24, '6217000000000003006', NULL, 24, '测试用户00101', '中国邮政储蓄银行', NULL, NULL, '403', '安徽省', '亳州市', '2016-04-17 00:00:00', '2900-01-01 00:00:00'),
	(25, '6217000000000003007', NULL, 25, '测试用户00102', '中国邮政储蓄银行', NULL, NULL, '403', '江苏省', '淮安市', '2016-04-17 00:00:00', '2900-01-01 00:00:00');

SET FOREIGN_KEY_CHECKS=1;

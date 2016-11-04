SET FOREIGN_KEY_CHECKS=0;

delete from `principal`;
delete from `contract`;
delete from `asset_package`;
delete from `factoring_contract`;
delete from `app`;
delete from `company`;
delete from `rent_order`;
delete from `payment_institution`;
delete from `transaction_record`;
delete from `finance_payment_record`;
delete from `payment_agreement`;
delete from `account`;
delete from `batch_pay_record`;
delete from `charge`;
delete from`customer`;

INSERT INTO `principal` (`id`, `authority`, `name`, `password`, `start_date`, `thru_date`)
VALUES
	(9, 'ROLE_SUPER_USER', NULL, 'zhangjianming', '14ebdd77cd348da2ee411e118d125b53', NULL, NULL);


INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`, `shroff_account_id`, `transfer_status`, `contract_life_cycle`, `contract_type`)
VALUES
	(1272, '2015-10-13', 'CC-63805', 0, 0.00, '2016-10-12', NULL, '', 700.00, 00000000, 2, NULL, NULL, 1, 1333, 1262, NULL, 0, NULL, 0),
	(261, '2015-03-18', 'CC-60272', 0, 2400.00, '2016-03-17', NULL, '', 1200.00, 00000001, 1, NULL, NULL, 1, 321, 263, NULL, 0, 3, 0);

INSERT INTO `asset_package` (`id`, `adva_start_date`, `is_available`, `bank_account`, `bank_name`, `create_time`, `latest_settle_date`, `otsd_amt`, `otsd_amt_aval`, `rec_transfee_total_amt`, `remove_time`, `contract_id`, `factoring_contract_id`, `thru_date`)
VALUES
	(827, '2015-11-18 00:00:00', 00000001, NULL, NULL, '2015-11-18 22:33:05', '2015-11-18 00:00:00', 4900.00, 4900.00, 7000.00, NULL, 1272, 61, NULL),
	(223, '2015-07-21 00:00:00', 00000001, NULL, NULL, '2015-07-03 19:18:56', '2015-07-21 00:00:00', 6720.00, 6720.00, 9600.00, NULL, 261, 61, NULL);

	
INSERT INTO `factoring_contract` (`id`, `adva_matuterm`, `adva_pct`, `adva_start_date`, `adva_term`, `ar_amt`, `ar_cost_rate`, `ar_deposit_amt`, `ar_deposit_amt_aval`, `ar_deposit_amt_input_days`, `ar_deposit_amt_input_type`, `ar_ldgamt`, `borrower_name`, `contract_no`, `cost_deduct_type`, `deposit_deduct_type`, `int_accuretype`, `int_rate`, `latest_settle_date`, `lender_ac_bk_name`, `lender_ac_no`, `lender_name`, `otsd_amt`, `otsd_amt_aval`, `settle_pct`, `app_id`, `company_id`, `nominal_rate`, `payment_agreement_id`, `deposit_rate`)
VALUES
	(61, 7, 0.70, '2015-06-02 21:47:05', 0, 0.00, 0.00, 0.00, 0.00, 0, 1, 0.00, '寓见', 'DCF-XY-FZR905A', 2, 2, 2, 0.1966003200, NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', 0.00, 0.00, 1.00, 1, 2, 0.0850000000, 2, NULL);

	
INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `addressee`, `payee_account_name`, `payee_account_no`, `payee_bank_name`)
VALUES
	(1, 'xiaoyu', '70991db75ebb24fa0993f4fa25775022', 00000000, 'http://beta.demo2do.com/jupiter/', '寓见', 5, 'zhangjianming@zufangbao.com;8520874@qq.com', NULL, NULL, NULL);

INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`, `bank_name`, `card_no`, `name`)
VALUES
	(2, '上海陆家嘴', '鼎程（上海）商业保理有限公司', '鼎程', NULL, NULL, NULL);

	
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`, `contract_payment_amount`, `contract_payment_type`, `doubt_record`, `virtual_account_unique_id`, `bill_life_cycle`, `collection_bill_life_cycle`, `collection_bill_capital_status`, `asset_package_id`, `collection_bill_service_notice_status`, `collection_bill_frozen_status`, `factoring_contract_id`, `audit_bill_id`)
VALUES
	(8410, '2015-12-13', NULL, NULL, 'CC-63805-2', 0, NULL, NULL, 00000000, NULL, 1400.00, 1272, 1333, 0, NULL, NULL, 0, NULL, 'xiaoyu_CC-63805-2_8410', 1400.00, 1, NULL, NULL, 1, 1, 4, 827, 1, 0, 61, '3fdabebf-4a0d-45e1-a028-f1313952a5b4'),
	(8411, '2016-02-13', NULL, NULL, 'CC-63805-3', 0, NULL, NULL, 00000000, NULL, 1400.00, 1272, 1333, 0, NULL, NULL, 0, NULL, 'xiaoyu_CC-63805-3_8411', 1400.00, 1, NULL, NULL, 1, 1, 4, 827, 1, 0, 61, '767e5378-b7e6-47b6-9d13-344fad27e557'),
	(8412, '2016-04-13', NULL, NULL, 'CC-63805-4', 0, NULL, NULL, 00000000, NULL, 1400.00, 1272, 1333, 0, NULL, NULL, 0, NULL, 'xiaoyu_CC-63805-4_8412', 1400.00, 1, NULL, NULL, 1, 1, 4, 827, 1, 0, 61, '5b5ee735-5a54-4f92-82c9-2b8ca165f0c7'),
	(8413, '2016-06-13', NULL, NULL, 'CC-63805-5', 0, NULL, NULL, 00000000, NULL, 1400.00, 1272, 1333, 0, NULL, NULL, 0, NULL, 'xiaoyu_CC-63805-5_8413', 1400.00, 1, NULL, NULL, 1, 1, 4, 827, 1, 0, 61, '092f6be4-2b16-486e-ba8b-db886566f62f'),
	(8414, '2016-08-13', NULL, NULL, 'CC-63805-6', 0, NULL, NULL, 00000000, NULL, 1400.00, 1272, 1333, 0, NULL, NULL, 0, NULL, 'xiaoyu_CC-63805-6_8414', 1400.00, 1, NULL, NULL, 1, 1, 4, 827, 1, 0, 61, '793bb9d2-7cfd-4e3a-8b4e-330330b6d6fd');

INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`, `contract_payment_amount`, `contract_payment_type`, `doubt_record`, `virtual_account_unique_id`, `bill_life_cycle`, `collection_bill_life_cycle`, `collection_bill_capital_status`, `asset_package_id`, `collection_bill_service_notice_status`, `collection_bill_frozen_status`, `factoring_contract_id`, `audit_bill_id`)
VALUES
	(2105, '2015-07-18', NULL, NULL, 'CC-60272-5', 2, 1200.00, '2015-07-21 15:03:24', 00000000, NULL, 1200.00, 261, 321, 1, NULL, '9dfca839-5d8e-419d-8e9c-50fad3683b9e', 1, NULL, 'xiaoyu_CC-60272-5_2105', NULL, NULL, NULL, 'ed8cb46d-525b-47e4-abae-419bae28dc61', 3, 1, 3, 223, 1, 0, 61, NULL),
	(2106, '2015-08-18', NULL, NULL, 'CC-60272-6', 2, 1200.00, '2015-08-12 17:47:45', 00000000, NULL, 1200.00, 261, 321, 0, NULL, '9dfca839-5d8e-419d-8e9c-50fad3683b9e', 1, NULL, 'xiaoyu_CC-60272-6_2106', NULL, NULL, NULL, 'ed8cb46d-525b-47e4-abae-419bae28dc61', 3, 1, 3, 223, 1, 0, 61, NULL),
	(2107, '2015-09-18', NULL, NULL, 'CC-60272-7', 2, 1200.00, '2015-09-15 17:48:53', 00000000, NULL, 1200.00, 261, 321, 0, NULL, '9dfca839-5d8e-419d-8e9c-50fad3683b9e', 1, NULL, 'xiaoyu_CC-60272-7_2107', NULL, NULL, NULL, 'ed8cb46d-525b-47e4-abae-419bae28dc61', 3, 1, 3, 223, 1, 0, 61, NULL),
	(2108, '2015-10-18', NULL, NULL, 'CC-60272-8', 2, 1200.00, '2015-10-15 17:08:14', 00000000, NULL, 1200.00, 261, 321, 0, NULL, '9dfca839-5d8e-419d-8e9c-50fad3683b9e', 1, NULL, 'xiaoyu_CC-60272-8_2108', NULL, NULL, NULL, 'ed8cb46d-525b-47e4-abae-419bae28dc61', 3, 1, 3, 223, 1, 0, 61, NULL),
	(2109, '2015-11-18', NULL, NULL, 'CC-60272-9', 2, 1200.00, '2015-11-19 17:10:44', 00000000, NULL, 1200.00, 261, 321, 0, NULL, '9dfca839-5d8e-419d-8e9c-50fad3683b9e', 1, NULL, 'xiaoyu_CC-60272-9_2109', NULL, NULL, NULL, 'ed8cb46d-525b-47e4-abae-419bae28dc61', 2, 3, 3, 223, 1, 0, 61, NULL),
	(2110, '2015-12-18', NULL, NULL, 'CC-60272-10', 0, NULL, NULL, 00000000, NULL, 1200.00, 261, 321, 0, NULL, '9dfca839-5d8e-419d-8e9c-50fad3683b9e', 1, NULL, 'xiaoyu_CC-60272-10_2110', NULL, NULL, NULL, 'ed8cb46d-525b-47e4-abae-419bae28dc61', 1, 1, 4, 223, 1, 0, 61, NULL),
	(2111, '2016-01-18', NULL, NULL, 'CC-60272-11', 0, NULL, NULL, 00000000, NULL, 1200.00, 261, 321, 0, NULL, '9dfca839-5d8e-419d-8e9c-50fad3683b9e', 1, NULL, 'xiaoyu_CC-60272-11_2111', NULL, NULL, NULL, 'ed8cb46d-525b-47e4-abae-419bae28dc61', 1, 1, 4, 223, 1, 0, 61, NULL),
	(2112, '2016-02-18', NULL, NULL, 'CC-60272-12', 0, NULL, NULL, 00000000, NULL, 1200.00, 261, 321, 0, NULL, '9dfca839-5d8e-419d-8e9c-50fad3683b9e', 1, NULL, 'xiaoyu_CC-60272-12_2112', NULL, NULL, NULL, 'ed8cb46d-525b-47e4-abae-419bae28dc61', 1, 1, 4, 223, 1, 0, 61, NULL);

	
INSERT INTO `payment_institution` (`id`, `alias`, `code`, `day_upper_limit`, `name`, `once_upper_limit`)
VALUES
	(1, '', 'record', NULL, '记录支付', NULL);

	
INSERT INTO `payment_agreement` (`id`, `collection_accounts`, `repayment_account_id`, `risk_reserve_pay_account_id`, `risk_reserve_query_account_id`, `bank_corporate_account_id`, `usb_key_id`)
VALUES
	(2, NULL, 1, 6, 6, 7, NULL);
	
	
INSERT INTO `account` (`id`, `account_name`, `account_no`, `bank_id`, `alias`, `attr`) VALUES
(1, '鼎程（上海）商业保理有限公司', '11014671112002', 2, '鼎程回款户', NULL),
(6, '鼎程 (上海) 商业保理有限公司', '1001184219000050139', 5, '鼎程－寓见风险准备金户', NULL),
(7, '杭州随地付网络技术有限公司', 'zfbdg@zufangbao.com', 3, '随地付', NULL);


INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`)
VALUES
	(1333, NULL, NULL, NULL, '佘泽仟', NULL, '寓见', 1),
	(321, NULL, NULL, NULL, '马永秀', NULL, '寓见', 1);


SET FOREIGN_KEY_CHECKS=1;

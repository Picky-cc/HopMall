SET FOREIGN_KEY_CHECKS = 0;

delete from batch_pay_record;
delete from batch_pay_detail;
delete from transaction_record;
delete from transaction_record_log;
delete from rent_order;
delete from app;
delete from contract;
delete from `bank`;
delete from `account`;
delete from `asset_package`;
delete from `factoring_contract`;
delete from `payment_agreement`;
delete from `customer`;

INSERT INTO `batch_pay_record` (`id`, `amount`, `apply_time`, `creator_id`, `modify_time`, `operator_id`, `pay_status`, `pay_time`, `remark`, `request_no`, `serial_no`, `payer_account_id`, `payment_institution_id`, `receive_account_id`, `confirm_time`) VALUES
(1, '43250.00', '2015-06-24 15:25:00', 9, '2015-06-24 15:30:49', 1, 1, '2015-06-24 15:30:49', '23套房源风险准备金0624PM', NULL, '20150624110070101500000002423900', 7, 2, 6, NULL),
(2, '26800.00', '2015-06-26 16:30:00', 1, '2015-06-26 16:32:33', 1, 1, '2015-06-26 16:32:33', '16套房源风险准备金0626PM', NULL, '1548062644620819003', 7, 2, 6, NULL),
(3, '18150.00', '2015-07-03 11:10:15', 8, '2015-07-03 14:10:39', 1, 1, '2015-07-03 14:10:39', '11套房源风险准备金0703AM ', NULL, '1554070327971079005', 7, 2, 6, NULL);

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `payee_account_name`, `payee_account_no`, `payee_bank_name`) VALUES
(1, 'xiaoyu', '70991db75ebb24fa0993f4fa25775022', b'0', 'http://beta.demo2do.com/jupiter/', '寓见', 5, NULL, NULL, NULL),
(2, 'youpark', '123456', b'0', '', '优帕克', 4, NULL, NULL, NULL),
(3, 'test4Zufangbao', '2e85ae999845f25faf8ea7b514ee0aca', b'0', 'http://e.zufangbao.cn', '租房宝测试账号', 3, NULL, NULL, NULL);

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`, `transfer_status`) VALUES
(1, '2015-09-01', 'HQWB8#701-2015-09-08', 0, '10000.00', '2016-08-31', NULL, '', '10000.00', b'0', 3, NULL, NULL, 1, 971, 913, 0),
(2, '2015-09-08', '0000250', 0, '1300.00', '2016-09-07', NULL, NULL, '2300.00', b'0', 1, NULL, NULL, 1, 972, 914, 0),
(3, '2015-09-09', 'ct-0020', 0, '1200.00', '2016-09-08', NULL, NULL, '720.00', b'0', 1, NULL, NULL, 1, 973, 915, 0),
(4, '2015-06-09', '0000309', 0, '1300.00', '2016-06-08', NULL, NULL, '2320.00', b'0', 1, NULL, NULL, 1, 974, 916, 0),
(975, '2015-04-07', 'C33', 0, 44000.00, '2017-04-06', NULL, '', 22000.00, 00000000, 1, NULL, NULL, 11, 1035, 971, 0);

INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`)
VALUES
	(971,NULL,NULL,NULL,'林佩如',NULL,'优帕克',2),
	(972,NULL,NULL,'18328419335','敖燕',NULL,'美家公寓',9),
	(973,NULL,NULL,'15828429694','叶茹',NULL,'美家公寓',9),
	(974,NULL,NULL,'13458583216','蒋雨涵',NULL,'美家公寓',9),
	(1035,NULL,NULL,NULL,'雅冠企业管理咨询（上海）有限公司',NULL,'源涞国际',11);


INSERT INTO `asset_package` (`id`, `adva_start_date`, `is_available`, `bank_account`, `bank_name`, `create_time`, `latest_settle_date`, `otsd_amt`, `otsd_amt_aval`, `rec_transfee_total_amt`, `remove_time`, `contract_id`, `factoring_contract_id`)
VALUES
	(555, '2015-09-29 00:00:00', 00000001, NULL, NULL, '2015-09-29 10:30:10', '2015-09-29 00:00:00', 184800.00, 184800.00, 264000.00, NULL, 975, 66);

	
INSERT INTO `factoring_contract` (`id`, `adva_matuterm`, `adva_pct`, `adva_start_date`, `adva_term`, `ar_amt`, `ar_cost_rate`, `ar_deposit_amt`, `ar_deposit_amt_aval`, `ar_deposit_amt_input_days`, `ar_deposit_amt_input_type`, `ar_ldgamt`, `borrower_name`, `contract_no`, `cost_deduct_type`, `deposit_deduct_type`, `int_accuretype`, `int_rate`, `latest_settle_date`, `lender_ac_bk_name`, `lender_ac_no`, `lender_name`, `otsd_amt`, `otsd_amt_aval`, `settle_pct`, `app_id`, `company_id`, `nominal_rate`, `payment_agreement_id`)
VALUES
	(66, 7, 0.70, '2015-09-07 14:30:16', 0, 0.00, 0.00, 0.00, 0.00, 15, 1, 0.00, '源涞国际', 'DCF-YL-FR907A', 2, 1, 2, 0.1966003200, NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', 0.00, 0.00, 1.00, 11, 2, 0.0850000000, 4);

INSERT INTO `payment_agreement` (`id`, `collection_accounts`, `repayment_account_id`, `risk_reserve_pay_account_id`, `risk_reserve_query_account_id`, `bank_corporate_account_id`, `usb_key_id`)
VALUES
	(4, '[{\"accountName\":\"上海源涞实业有限公司\",\"accountNo\":\"121913751710201\",\"alias\":\"源涞国际回款户\",\"bank\":{\"areaCode\":\"21\",\"bankType\":\"TraditionalBank\",\"branchNo\":\"21\",\"cityCode\":\"SHSH\",\"code\":\"CMB\",\"deleted\":false,\"fullName\":\"招商银行上海天钥桥支行\",\"id\":11,\"shortName\":\"招商银行\"},\"bankAccount\":true,\"id\":18}]', 17, 16, 16, 15, '44bcf65b-c138-48f4-afd5-929bbdd979ee');


INSERT INTO `batch_pay_detail` (`id`, `create_time`, `creator_id`, `pay_money`, `pay_time`, `batch_pay_record_id`, `order_id`) VALUES
(682, '2015-09-15 16:22:45', 9, '1700.00', '2015-09-15 17:48:53', 1, 1),
(683, '2015-09-15 16:22:45', 9, '2400.00', '2015-09-15 17:48:53', 1, 2);

INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`, `contract_payment_amount`, `contract_payment_type`, `doubt_record`, `virtual_account_unique_id`) VALUES
(1, '2016-08-07', NULL, NULL, 'C33-20150928-17', 0, NULL, NULL, b'0', NULL, '22000.00', 975, 1035, 0, NULL, NULL, 0, NULL, 'yuanlai_C33-20150928-17_6782', '22000.00', 1, NULL, NULL),
(2, '2016-09-07', NULL, NULL, 'C33-20150928-18', 0, NULL, NULL, b'0', NULL, '22000.00', 975, 1035, 0, NULL, NULL, 0, NULL, 'yuanlai_C33-20150928-18_6783', '22000.00', 1, NULL, NULL);

INSERT INTO `transaction_record_log` (`id`, `modified_time`, `operator_id`, `transaction_record_operate_status`, `transaction_record_id`) VALUES
(5, '2015-04-13 19:27:02', 130, 0, 22),
(6, '2015-04-13 20:16:04', 126, 0, 23),
(7, '2015-04-13 20:17:11', 6, 1, 23),
(8, '2015-04-13 20:19:06', 126, 0, 24),
(9, '2015-04-13 20:19:45', 8, 1, 24);

INSERT INTO `transaction_record` (`id`, `card_no`, `channel`, `last_modified_time`, `merchant_payment_no`, `order_no`, `pay_money`, `request_no`, `trade_no`, `transaction_record_status`, `app_id`, `payment_institution_id`) VALUES
(23, NULL, 1, '2015-09-10 16:15:26', '641804989666624512', 'C33-20150928-17', '3900.00', '20150910104649641804989666624512', '2015091000001000850066807154', 2, 1, 2),
(24, NULL, 1, '2015-09-10 12:22:58', '641829168600253440', 'C33-20150928-18', '1200.00', '20150910122253641829168600253440', NULL, 2, 1, 2);


INSERT INTO `account` (`id`, `account_name`, `account_no`, `bank_id`, `alias`) VALUES
	(6, '鼎程 (上海) 商业保理有限公司', '1001184219000050139', 5, '鼎程－寓见风险准备金户'),
	(7, '杭州随地付网络技术有限公司', 'zfbdg@zufangbao.com', 3, '随地付'),
	(13, '鼎程（上海）商业保理有限公司', '11014671112002', 2, '鼎程回款户'),
	(14, '上海锐诩酒店管理有限公司', '121912306510801', 9, '安心公寓回款户'),
	(15, '杭州随地付网络技术有限公司', '571907757810703', 10, '随地付'),
	(16, '鼎程（上海）商业保理有限公司', '121916729010301', 10, '鼎程保证金户'),
	(17, '鼎程（上海）商业保理有限公司', '121916729010102', 10, '鼎程回款户'),
	(18, '上海源涞实业有限公司', '121913751710201', 11, '源涞国际回款户'),
	(19, '上海柯罗芭服饰有限公司', '1001215509300557671', 12, '柯罗芭回款户'),
	(20, '寓见资产管理（上海）有限公司', '31001613402050023693', 13, NULL),
	(21, '北京汉维百利威仓储服务有限公司', '121917799510201', 10, '北京汉维回款户'),
	(22, '鼎程（上海）商业保理有限公司', '121916729010301000003', 10, '汉维保证金户');


INSERT INTO `bank` (`id`, `code`, `full_name`, `is_deleted`, `short_name`, `branch_no`, `area_code`, `city_code`, `bank_type`)
VALUES
	(2, '', '平安银行上海分行营业部', 00000000, '平安银行', NULL, NULL, NULL, 0),
	(3, '', '支付宝企业版', 00000000, '支付宝', NULL, NULL, NULL, 1),
	(5, 'ICBC', '工商银行上海浦东证券大厦支行', 00000000, '工商银行', NULL, NULL, NULL, 0),
	(1, 'ICBC', '中国工商银行', 00000000, '工商银行', NULL, NULL, NULL, 0),
	(4, '', '中国工商银行杭州景江苑支行', 00000000, '中国工商银行杭州景江苑支行', NULL, NULL, NULL, 0),
	(6, NULL, '招商银行成都顺城街支行', 00000000, '招商银行', NULL, NULL, NULL, 0),
	(7, 'ICBC', '工行杭州开元支行', 00000000, '工行杭州开元支行', NULL, NULL, NULL, 0),
	(8, NULL, '中国农业银行杭州解放路支行', 00000000, '农业银行', NULL, NULL, NULL, 0),
	(9, 'CMB', '招商银行淮海支行', 00000000, '招商银行', '21', '21', 'SHSH', 0),
	(10, 'CMB', '招商银行上海分行联洋支行', 00000000, '招商银行', '21', '21', 'SHSH', 0),
	(11, 'CMB', '招商银行上海天钥桥支行', 00000000, '招商银行', '21', '21', 'SHSH', 0),
	(12, 'ICBC', '工行上海天目东路支行', 00000000, '工商银行', NULL, NULL, NULL, 0),
	(13, 'CCB', '建行徐汇支行漕河泾支行', 00000000, '建设银行', NULL, NULL, NULL, 0);


SET FOREIGN_KEY_CHECKS = 1;
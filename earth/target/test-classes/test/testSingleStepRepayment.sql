SET FOREIGN_KEY_CHECKS=0;

delete from `asset_package`;
delete from `contract`;
delete from `house`;
delete from `customer`;
delete from `rent_order`;
delete from `escrow_agreement`;
delete from `escrow_contract`;
delete from `payment_agreement`;
delete from `account`;
delete from `app`;
delete from `payment_institution`;
delete from `factoring_contract`;
delete from `bank`;
delete from `principal`;
delete from `batch_pay_record`;
delete from `batch_pay_detail`;
delete from `app_payment_config`;

INSERT INTO `asset_package` (`id`, `adva_start_date`, `is_available`, `bank_account`, `bank_name`, `create_time`, `latest_settle_date`, `otsd_amt`, `otsd_amt_aval`, `rec_transfee_total_amt`, `remove_time`, `contract_id`, `factoring_contract_id`)
VALUES
	(723, '2015-10-30 00:00:00', 00000001, NULL, NULL, '2015-10-30 14:57:52', '2015-10-30 00:00:00', 2520000.00, 2520000.00, 3600000.00, NULL, 1165, 67);

	
INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`, `shroff_account_id`, `transfer_status`, `contract_life_cycle`, `contract_type`)
VALUES
	(1165, NULL, 'LST', 0, 0.00, '2016-12-31', NULL, '', 300000.00, 00000000, 1, NULL, NULL, 12, 1226, 1155, 21, 0, NULL, 0);

	
INSERT INTO `house` (`id`, `address`, `area`, `bulid_area`, `city`, `community`, `community_detail`, `hall`, `house_status`, `house_type`, `province`, `room`, `app_id`)
VALUES
	(1155, '', NULL, 0, NULL, '', NULL, 0, 1, 2, NULL, 0, 12);

	
INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`)
VALUES
	(1226, NULL, NULL, NULL, '北京隆盛泰商贸有限责任公司', NULL, '汉维仓储', 12);

	
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`, `contract_payment_amount`, `contract_payment_type`, `doubt_record`, `virtual_account_unique_id`, `bill_life_cycle`, `collection_bill_life_cycle`, `collection_bill_capital_status`, `asset_package_id`, `collection_bill_service_notice_status`, `collection_bill_frozen_status`, `factoring_contract_id`, `audit_bill_id`)
VALUES
	(7820, '2015-11-20', NULL, NULL, 'LST-20151029-2', 2, 300000.00, NULL, 00000000, NULL, 300000.00, 1165, 1226, 0, NULL, NULL, 0, NULL, 'hanwei_LST-20151029-2_7820', 300000.00, 1, NULL, NULL, 1, 1, 1, 723, 1, 0, 67, NULL);
	

INSERT INTO `escrow_agreement` (`id`, `escrow_account_id`, `repayment_account_id`)
VALUES
	(2,11,10);

	
INSERT INTO `escrow_contract` (`id`, `arrival_days`, `single_service_fee`, `app_id`, `escrow_agreement_id`)
VALUES
	(2,NULL,NULL,9,2);
	
	
	
INSERT INTO `payment_agreement` (`id`, `collection_accounts`, `repayment_account_id`, `risk_reserve_pay_account_id`, `risk_reserve_query_account_id`, `bank_corporate_account_id`, `usb_key_id`)
VALUES
	(1, '[{\"accountName\":\"上海优帕克投资管理有限公司\",\"accountNo\":\"1001300219000027827\",\"alias\":\"优帕克回款户\",\"bank\":{\"bankType\":\"TraditionalBank\",\"code\":\"ICBC\",\"deleted\":false,\"fullName\":\"中国工商银行\",\"id\":1,\"shortName\":\"工商银行\"},\"bankAccount\":true,\"id\":3}]', 1, 2, 2, 5, '0baa43b3-6f77-46ac-81ab-2eb051cd7e76'),
	(2, NULL, 1, 6, 6, 7, NULL),
	(3, '[{\"accountName\":\"上海锐诩酒店管理有限公司\",\"accountNo\":\"121912306510801\",\"alias\":\"安心公寓回款户\",\"bank\":{\"areaCode\":\"21\",\"bankType\":\"TraditionalBank\",\"branchNo\":\"21\",\"cityCode\":\"SHSH\",\"code\":\"CMB\",\"deleted\":false,\"fullName\":\"招商银行淮海支行\",\"id\":9,\"shortName\":\"招商银行\"},\"bankAccount\":true,\"id\":14}]', 17, 16, 16, 15, '44bcf65b-c138-48f4-afd5-929bbdd979ee'),
	(4, '[{\"accountName\":\"上海源涞实业有限公司\",\"accountNo\":\"121913751710201\",\"alias\":\"源涞国际回款户\",\"bank\":{\"areaCode\":\"21\",\"bankType\":\"TraditionalBank\",\"branchNo\":\"21\",\"cityCode\":\"SHSH\",\"code\":\"CMB\",\"deleted\":false,\"fullName\":\"招商银行上海天钥桥支行\",\"id\":11,\"shortName\":\"招商银行\"},\"bankAccount\":true,\"id\":18}]', 17, 16, 16, 15, '44bcf65b-c138-48f4-afd5-929bbdd979ee'),
	(5, '', 1, 2, 2, 5, '0baa43b3-6f77-46ac-81ab-2eb051cd7e76'),
	(6, '[{\"accountName\":\"北京汉维百利威仓储服务有限公司\",\"accountNo\":\"121917799510201\",\"alias\":\"北京汉维回款户\",\"bank\":{\"areaCode\":\"21\",\"bankType\":\"TraditionalBank\",\"branchNo\":\"21\",\"cityCode\":\"SHSH\",\"code\":\"CMB\",\"deleted\":false,\"fullName\":\"招商银行上海分行联洋支行\",\"id\":10,\"shortName\":\"招商银行\"},\"bankAccount\":true,\"id\":21}]', 17, 16, 16, 17, '23988f31-f583-4afa-b866-a46abeafd8b0');


	
INSERT INTO `account` (`id`, `account_name`, `account_no`, `bank_id`, `alias`, `attr`)
VALUES
	(1, '鼎程（上海）商业保理有限公司', '11014671112002', 2, '鼎程回款户', NULL),
	(2, '鼎程（上海）商业保理有限公司', '1001262119204647489', 1, '鼎程－优帕克风险准备金户', NULL),
	(3, '上海优帕克投资管理有限公司', '1001300219000027827', 1, '优帕克回款户', NULL),
	(4, '小寓科技', '', 1, NULL, NULL),
	(5, '杭州随地付网络技术有限公司', '1001262119204646188', 1, '随地付', NULL),
	(6, '鼎程 (上海) 商业保理有限公司', '1001184219000050139', 5, '鼎程－寓见风险准备金户', NULL),
	(7, '杭州随地付网络技术有限公司', 'zfbdg@zufangbao.com', 3, '随地付', NULL),
	(8, '罗仙林', '6222081202010524989', 4, NULL, NULL),
	(10, '王晓娣', '6214830285912142', 6, NULL, NULL),
	(11, '杭州随地付网络技术有限公司', '1202021519900680156', 7, '随地付', NULL),
	(12, '杭州蜗居网络技术有限公司', '19025101040024439', 8, NULL, NULL),
	(13, '寓见资产管理（上海）有限公司', 'slin@yuapt.com', 3, NULL, NULL),
	(14, '上海锐诩酒店管理有限公司', '121912306510801', 9, '安心公寓回款户', NULL),
	(15, '杭州随地付网络技术有限公司', '571907757810703', 10, '随地付', NULL),
	(16, '鼎程（上海）商业保理有限公司', '121916729010301', 10, '鼎程保证金户', NULL),
	(17, '鼎程（上海）商业保理有限公司', '121916729010102', 10, '鼎程回款户', '{\"CRTSQN\":\"CMB0000005\",\"CRTBNK\":\"招商银行上海联洋支行\",\"CRTPVC\":\"上海市\",\"CRTCTY\":\"浦东新区\"}'),
	(18, '上海源涞实业有限公司', '121913751710201', 11, '源涞国际回款户', NULL),
	(19, '上海柯罗芭服饰有限公司', '1001215509300557671', 12, '柯罗芭回款户', NULL),
	(20, '寓见资产管理（上海）有限公司', '31001613402050023693', 13, NULL, NULL),
	(21, '北京汉维百利威仓储服务有限公司', '121917799510201', 10, '北京汉维回款户', NULL),
	(22, '鼎程（上海）商业保理有限公司', '121916729010301000003', 10, '汉维保证金户', NULL);

	
INSERT INTO `bank` (`id`, `code`, `full_name`, `is_deleted`, `short_name`, `branch_no`, `area_code`, `city_code`, `bank_type`)
VALUES
	(1, 'ICBC', '中国工商银行', 00000000, '工商银行', NULL, NULL, NULL, 0),
	(2, '', '平安银行上海分行营业部', 00000000, '平安银行', NULL, NULL, NULL, 0),
	(3, '', '支付宝企业版', 00000000, '支付宝', NULL, NULL, NULL, 1),
	(4, '', '中国工商银行杭州景江苑支行', 00000000, '中国工商银行杭州景江苑支行', NULL, NULL, NULL, 0),
	(5, 'ICBC', '工商银行上海浦东证券大厦支行', 00000000, '工商银行', NULL, NULL, NULL, 0),
	(6, NULL, '招商银行成都顺城街支行', 00000000, '招商银行', NULL, NULL, NULL, 0),
	(7, 'ICBC', '工行杭州开元支行', 00000000, '工行杭州开元支行', NULL, NULL, NULL, 0),
	(8, NULL, '中国农业银行杭州解放路支行', 00000000, '农业银行', NULL, NULL, NULL, 0),
	(9, 'CMB', '招商银行淮海支行', 00000000, '招商银行', '21', '21', 'SHSH', 0),
	(10, 'CMB', '招商银行上海分行联洋支行', 00000000, '招商银行', '21', '21', 'SHSH', 0),
	(11, 'CMB', '招商银行上海天钥桥支行', 00000000, '招商银行', '21', '21', 'SHSH', 0),
	(12, 'ICBC', '工行上海天目东路支行', 00000000, '工商银行', NULL, NULL, NULL, 0),
	(13, 'CCB', '建行徐汇支行漕河泾支行', 00000000, '建设银行', NULL, NULL, NULL, 0);

	

INSERT INTO `factoring_contract` (`id`, `adva_matuterm`, `adva_pct`, `adva_start_date`, `adva_term`, `ar_amt`, `ar_cost_rate`, `ar_deposit_amt`, `ar_deposit_amt_aval`, `ar_deposit_amt_input_days`, `ar_deposit_amt_input_type`, `ar_ldgamt`, `borrower_name`, `contract_no`, `cost_deduct_type`, `deposit_deduct_type`, `int_accuretype`, `int_rate`, `latest_settle_date`, `lender_ac_bk_name`, `lender_ac_no`, `lender_name`, `otsd_amt`, `otsd_amt_aval`, `settle_pct`, `app_id`, `company_id`, `nominal_rate`, `payment_agreement_id`)
VALUES
	(67, 10, 0.70, '2015-10-30 14:40:20', 0, 0.00, 0.00, 0.00, 0.00, 0, 1, 0.00, '汉维仓储', 'DCF-HW-FR908A', 2, 1, 2, 0.1966003200, NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', 0.00, 0.00, 1.00, 12, 2, 0.0850000000, 6);

	
INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `payee_account_name`, `payee_account_no`, `payee_bank_name`)
VALUES
	(12, 'hanwei', NULL, 00000000, NULL, '汉维仓储', 15, NULL, NULL, NULL);

INSERT INTO `payment_institution` (`id`, `alias`, `code`, `day_upper_limit`, `name`, `once_upper_limit`)
VALUES
	(1, '', 'record', NULL, '记录支付', NULL),
	(2, 'alipay', 'alipay', NULL, '支付宝', NULL),
	(3, 'directbank', 'ICBC', NULL, '工行银企互联', NULL),
	(4, 'unionpay', 'unionpay', NULL, '银联', NULL),
	(5, 'directbank', 'CMB', NULL, '招行银企直联', NULL);

INSERT INTO `principal` (`id`, `authority`, `name`, `password`)
VALUES
	(9, 'ROLE_SUPER_USER', NULL, 'zhangjianming', '14ebdd77cd348da2ee411e118d125b53');

INSERT INTO `app_payment_config` (`id`, `channel`, `app_id`, `payment_institution_id`)
VALUES
	(1, 1, 1, 2),
	(2, 2, 2, 3),
	(3, 2, 6, 3),
	(4, 1, 9, 2),
	(5, 1, 4, 4),
	(6, 1, 8, 4),
	(7, 2, 10, 5),
	(8, 2, 11, 5),
	(10, 2, 12, 5);

SET FOREIGN_KEY_CHECKS=1;
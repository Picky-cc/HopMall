SET FOREIGN_KEY_CHECKS=0;

delete from `factoring_contract`;
INSERT INTO `factoring_contract` (`id`, `adva_matuterm`, `adva_pct`, `adva_start_date`, `adva_term`, `ar_amt`, `ar_cost_rate`, `ar_deposit_amt`, `ar_deposit_amt_aval`, `ar_deposit_amt_input_days`, `ar_deposit_amt_input_type`, `ar_ldgamt`, `borrower_name`, `contract_no`, `cost_deduct_type`, `deposit_deduct_type`, `int_accuretype`, `int_rate`, `latest_settle_date`, `lender_ac_bk_name`, `lender_ac_no`, `lender_name`, `otsd_amt`, `otsd_amt_aval`, `settle_pct`, `app_id`, `company_id`, `nominal_rate`, `payment_agreement_id`)
VALUES
	(58, 7, 0.70, '2015-04-02 00:00:00', 0, 11211970.00, 0.00, 0.00, 0.00, 15, 1, 11211970.00, '优帕克', 'DCF-YPK-LR903A', 2, 1, 2, 0.1971432000, NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', 7848379.00, 7848379.00, 1.00, 2, 2, 0.0850000000, 1),
	(61, 7, 0.70, '2015-06-02 21:47:05', 0, 0.00, 0.00, 0.00, 0.00, 0, 1, 0.00, '寓见', 'DCF-XY-FZR905A', 2, 2, 2, 0.1966003200, NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', 0.00, 0.00, 1.00, 1, 2, 0.0850000000, 2),
	(67, 10, 0.70, '2015-10-30 14:40:20', 0, 0.00, 0.00, 0.00, 0.00, 0, 1, 0.00, '汉维仓储', 'DCF-HW-FR908A', 2, 1, 2, 0.1966003200, NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', 0.00, 0.00, 1.00, 12, 2, 0.0850000000, 6),
	(62, 10, 0.70, '2015-06-11 17:32:16', 0, 10289433.60, 0.00, 0.00, 0.00, 0, 1, 10289433.60, '柯罗芭', 'DCF-KLB-JMR904A', 2, 1, 2, 0.1853200000, NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', 7313040.00, 7313040.00, 1.00, 6, 2, 0.0850000000, 5);


delete from `principal`;
INSERT INTO `principal` (`id`, `authority`, `name`, `password`)
VALUES
	(9,'ROLE_SUPER_USER',NULL,'zhangjianming','14ebdd77cd348da2ee411e118d125b53'),
	(5,'ROLE_INSTITUTION',2,'DCF001','3cf5eaa7d33e0d023e811c90cd6f2f73'),
	(4,'ROLE_BANK_APP',2,'yopark','086246bffb2de7288946151fc900db59');

	
delete from `payment_agreement`;
INSERT INTO `payment_agreement` (`id`, `collection_accounts`, `repayment_account_id`, `risk_reserve_pay_account_id`, `risk_reserve_query_account_id`, `bank_corporate_account_id`, `usb_key_id`)
VALUES
	(1, '[{\"accountName\":\"上海优帕克投资管理有限公司\",\"accountNo\":\"1001300219000027827\",\"alias\":\"优帕克回款户\",\"bank\":{\"bankType\":\"TraditionalBank\",\"code\":\"ICBC\",\"deleted\":false,\"fullName\":\"中国工商银行\",\"id\":1,\"shortName\":\"工商银行\"},\"bankAccount\":true,\"id\":3}]', 1, 2, 2, 5, '0baa43b3-6f77-46ac-81ab-2eb051cd7e76'),
	(2, NULL, 1, 6, 6, 7, NULL),
	(5, '', 1, 2, 2, 5, '0baa43b3-6f77-46ac-81ab-2eb051cd7e76'),
	(6, '[{\"accountName\":\"北京汉维百利威仓储服务有限公司\",\"accountNo\":\"121917799510201\",\"alias\":\"北京汉维回款户\",\"bank\":{\"areaCode\":\"21\",\"bankType\":\"TraditionalBank\",\"branchNo\":\"21\",\"cityCode\":\"SHSH\",\"code\":\"CMB\",\"deleted\":false,\"fullName\":\"招商银行上海分行联洋支行\",\"id\":10,\"shortName\":\"招商银行\"},\"bankAccount\":true,\"id\":21}]', 17, 16, 16, 17, '23988f31-f583-4afa-b866-a46abeafd8b0');

	
delete from `account`;
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


delete from `bank`;
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


SET FOREIGN_KEY_CHECKS=1;

SET FOREIGN_KEY_CHECKS=0;
delete from app;
delete from payment_institution;
delete from app_account;
delete from app_payment_config;
delete from app_service_config;
delete from payment_institution_param;
delete from landlord;
delete from house;
delete from customer;
delete from contract;
delete from rent_order;
delete from company;
delete from factoring_contract;
delete from asset_package;
delete from transaction_record;
delete from finance_payment_record;
delete from settle_record;
delete from settle_item;
delete from contract_account;
delete from partical_model;
delete from partical;
delete from principal;
delete from contract_partical;
delete from app_particles;

delete from payment_agreement;
delete from account;

insert into `account`(`id`,`account_name`,`account_no`,`bank_id`) values
('1','鼎程（上海）商业保理有限公司','11014671112002','2'),
('2','鼎程（上海）商业保理有限公司','1001262119204647489','1'),
('3','上海优帕克投资管理有限公司','1001300219000027827','1'),
('4','小寓科技','','1'),
('5','杭州随地付网络技术有限公司','1001262119204646188','1');

INSERT INTO `payment_agreement` (`id`, `collection_accounts`, `repayment_account_id`, `risk_reserve_pay_account_id`, `risk_reserve_query_account_id`, `bank_corporate_account_id`, `usb_key_id`)
VALUES
	(1, '[{\"accountName\":\"上海优帕克投资管理有限公司\",\"accountNo\":\"1001300219000027827\",\"alias\":\"优帕克回款户\",\"bank\":{\"bankType\":\"TraditionalBank\",\"code\":\"ICBC\",\"deleted\":false,\"fullName\":\"中国工商银行\",\"id\":1,\"shortName\":\"工商银行\"},\"bankAccount\":true,\"id\":3}]', 1, 2, 2, 5, '0baa43b3-6f77-46ac-81ab-2eb051cd7e76');


INSERT INTO `contract_account` (`id`, `pay_ac_no`, `bankcard_type`, `contract_id`) VALUES 
(1, '111111', 0, 48),
(2, '222222', 0, 49),
(3, '333333', 0, 50);

-- 接入商家的信息 
INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`)
VALUES
	(1,'xiaoyu','70991db75ebb24fa0993f4fa25775022',00000000,'http://beta.demo2do.com/jupiter/','寓见',5),
	(2,'youpark','123456',00000000,'','优帕克',4);
  
  
-- 支付通道的信息  
INSERT INTO `payment_institution` (`id`, `alias`, `code`, `day_upper_limit`, `name`, `once_upper_limit`) VALUES
  (1,'umpay','umpay',NULL,'联动优势',NULL),
  (2,'alipay','alipay',NULL,'支付宝',NULL),
  (3,'directbank.icbc','directbank.icbc',NULL,'工行银企互联',NULL);
  
-- 接入商家的个性化显示信息
INSERT INTO `app_account` (`id`, `channel`, `header`, `logo`, `name`, `app_id`)
VALUES
	(1,1,'1-logo.png','banner-logo-xiaoyu.png','小寓科技',1),
	(2,2,NULL,NULL,'优帕克',2);
 -- (2, 2, NULL, 1, '优帕克', 2, '斑裸协厉矿凜扛芦低审它霞','1001262119306922285');
  
  
-- 商家在不同访问通道可用的支付通道 , CHANNEL 0是PC端,1是移动端
INSERT INTO `app_payment_config` (`id`, `channel`, `app_id`, `payment_institution_id`) VALUES
  (1,1,1,1),
  (2,2,2,3);
  
-- 房屋信息  
INSERT INTO `house` (`id` ,`address` ,`area`,`bulid_area`,`city`,`community`,`community_detail` ,`hall`,`house_status`,`house_type`,`province`,`room`,`app_id`) VALUES
 ('51', '荣华东道119弄4号1001室', null, '0', null, '巴黎花园4#1001', null, '0', '1', '1', null, '0','2'),
 ('52', '长宁区娄山关路999弄77号2805室', null, '0', null, '春天花园77#2805', null, '0', '1', '1', null, '0','2'),
 ('53', '虹桥路168弄4号803室', null, '0', null, '东方曼哈顿4#803', null, '0', '1', '1', null, '0','2');
  
-- 租客信息
INSERT INTO `customer` (`id` ,`account`,`city`,`mobile`,`name`,`province`,`source`,`app_id`) VALUES
 ('62', null, null, null, '清水 亘', null, '优帕克', '2'),
 ('63', null, null, null, '徐静江', null, '优帕克', '2'),
 ('64', null, null, null, 'L.O.S', null, '优帕克', '2');
 
-- 租约合同 
INSERT INTO `contract` (`id` ,`begin_date`,`contract_no`,`contract_status`,`deposit` ,`end_date`,`interrupt_date`,`interrupt_reason` ,
  `month_fee` ,`non_deposit_guarantee_way`,`payment_instrument` ,`renewal` ,`url` ,`customer_id` ,`house_id` ,`app_id`) VALUES
('48', '2014-04-12', 'YPK-CZ-0001', '0', '32000.00', '2016-04-11', null, '', '16.00', b'0', '0', null, null, '62', '51', '2'),
('49', '2014-04-27', 'YPK-CZ-0002', '0', '13400.00', '2015-10-26', null, '', '6.80', b'0', '0', null, null, '63', '52', '2'),
('50', '2014-07-01', 'YPK-CZ-0003', '0', '20000.00', '2015-12-31', null, '', '10.00', b'0', '0', null, null, '64', '53', '2'),
('51', '2014-07-01', 'YPK-CZ-0004', '0', '20000.00', '2015-12-31', null, '', '10.00', b'0', '0', null, null, '64', '53', '2');

-- 应付房租  
INSERT INTO `rent_order` (
  `id` ,
  `due_date` ,
  `end_date` ,
  `late_fee` ,
  `order_no` ,
  `order_status` ,
  `paid_rent` ,
  `payout_time` ,
  `is_settled` ,
  `start_date` ,
  `total_rent` ,
  `contract_id`,
  `customer_id`) VALUES
('96', '2015-04-12', null, null, 'YPK-CZ-0001-13', 0, null, null, '', null, '16.00', '48', '62'),
('97', '2015-05-12', null, null, 'YPK-CZ-0001-14', 0, null, null, '', null, '16.00', '48', '62'),
('98', '2015-06-12', null, null, 'YPK-CZ-0001-15', 0, null, null, '', null, '16.00', '48', '62'),
('108', '2015-04-27', null, null, 'YPK-CZ-0002-13', 0, null, null, '', null, '6.80', '49', '63'),
('109', '2015-05-27', null, null, 'YPK-CZ-0002-14', 0, null, null, '', null, '6.80', '49', '63'),
('110', '2015-06-27', null, null, 'YPK-CZ-0002-15', 0, null, null, '', null, '6.80', '49', '63'),
('114', '2015-04-01', null, null, 'YPK-CZ-0003-10', 0, null, null, '', null, '10.00', '50', '64'),
('115', '2015-05-01', null, null, 'YPK-CZ-0003-11', 0, null, null, '', null, '10.00', '50', '64'),
('116', '2015-06-01', null, null, 'YPK-CZ-0003-12', 0, null, null, '', null, '10.00', '50', '64'),
('117', '2015-06-01', null, null, 'YPK-CZ-0003-13', 0, null, null, '', null, '10.00', '51', '64');

-- 金融公司表	
INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`)
VALUES
	(2,'上海陆家嘴','鼎程（上海）商业保理有限公司','鼎程'),
	(3,'杭州万塘路8号','杭州随地付网络技术有限公司','租房宝'),
	(4,'上海','上海优帕克投资管理有限公司','优帕克'),
	(5,'上海','小寓科技','小寓');


-- 保理合同表
INSERT INTO `factoring_contract` (
  `id` ,
  `payment_agreement_id` ,
  `adva_matuterm` ,   -- 融资宽限期
  `adva_pct` ,        -- 融资比例  
  `adva_start_date` , -- 融资起始日期
  `adva_term` ,       -- 融资期限(日）
  `ar_amt` ,          -- 应收账款总额  
  `ar_cost_rate` ,    -- 手续费率
  `ar_deposit_amt` ,  -- 风险准备金
  `ar_deposit_amt_aval` ,  -- 风险准备金余额
  `ar_deposit_amt_input_days` , -- 风险准备金收取天数
  `ar_deposit_amt_input_type` , -- 风险准备金补充方式
  `ar_ldgamt` ,       -- 应收账款余额  
  `borrower_name` ,   -- 融资方名称
  
  `contract_no` ,     -- 保理合同编号
  `cost_deduct_type` ,-- 手续费收取方式
  `deposit_deduct_type` , -- 风险准备金释放方式
  `int_accuretype` ,    -- 扣息方式
  `int_rate` ,          -- 融资利率
  `latest_settle_date` , -- 最后核销日期
  `lender_ac_bk_name` ,  -- 融资方银行名称
  `lender_ac_no` ,       -- 融资方银行账号
  `lender_name` ,        -- 融出方名称   
  `otsd_amt` ,           -- 融资本金 
  `otsd_amt_aval` ,      -- 融资余额 
  `settle_pct` ,         -- 核销比例
  `app_id` ,             -- 对接租房公司
  `company_id`)          -- 对接金融公司 
   VALUES
  ('54',1, '7', '0.70', '2015-04-01 00:00:00', '180', '98.00', '0.00', '0.00', '0.00', '30', '1',  '98.00', '优帕克',
   'DCF-YPK-0001', '2', '0', '2', '0.198', null, '工商银行', '100001', '鼎程', '80.00', '80.00', '1.00', '2', '2');

-- 资产包
INSERT INTO `asset_package` (
  `id` ,
  `adva_start_date` ,
  `is_available` ,
  `bank_account` ,
  `bank_name` ,
  `create_time` ,
  `latest_settle_date` ,
  `otsd_amt`  ,
  `otsd_amt_aval` ,
  `remove_time` ,
  `contract_id` ,
  `factoring_contract_id` ) VALUES
('47', '2015-04-01 00:00:00', b'1', null, null, '2015-04-01 00:00:00', '2015-04-01 00:00:00', '48.00', '48.00', null, '48', '54'),
('48', '2015-04-01 00:00:00', b'1', null, null, '2015-04-01 00:00:00', '2015-04-01 00:00:00', '20.40', '20.40', null, '49', '54'),
('49', '2015-04-01 00:00:00', b'1', null, null, '2015-04-01 00:00:00', '2015-04-01 00:00:00', '30.00', '30.00', null, '50', '54');

INSERT INTO `partical` (`id`, `entry_url`, `partical_name`, `partical_status`, `partical_type`, `unique_partical_id`)
VALUES
	(1, 'http://127.0.0.1:17523/testquark', 'test_quark', 0, 0, '963eb1401db6fc3cfce2a7cd5e9f916d'),
	(2, 'http://test2.url', 'test2_quark', 1, 1, 'e13855b7f2037a9fed12432720d156b5');


INSERT INTO `partical_model` (`id`, `value`, `name`, `unique_partical_id`)
VALUES
	(1, '/revoke', 'revokeBills', '963eb1401db6fc3cfce2a7cd5e9f916d'),
	(2, '/app-config/sync', 'syncAppConfig', '963eb1401db6fc3cfce2a7cd5e9f916d'),
	(3, '/assgin', 'assignBills', '963eb1401db6fc3cfce2a7cd5e9f916d'),
	(4, 'testvalue21', 'testname1', 'e13855b7f2037a9fed12432720d156b5'),
	(5, 'testvalue22', 'testname2', 'e13855b7f2037a9fed12432720d156b5'),
	(6, 'testvalue23', 'testname3', 'e13855b7f2037a9fed12432720d156b5');

INSERT INTO `app_particles` (`app_id`,`is_delete`,`modified_time`,`unique_partical_id`) VALUES ('youpark',b'0','2015-04-27 15:00:58','963eb1401db6fc3cfce2a7cd5e9f916d');
	

INSERT INTO `principal` (`id`, `authority`, `name`, `password`) VALUES
	(1, 'ROLE_SUPER_USER', 'root', NULL, 'e10adc3949ba59abbe56e057f20f883e'),
	(2, 'ROLE_INSTITUTION', 'dingcheng', 2, 'e10adc3949ba59abbe56e057f20f883e'),
	(3, 'ROLE_APP', 'xiaoyu', 1, 'e10adc3949ba59abbe56e057f20f883e'),
	(4, 'ROLE_APP','youpark',2,'e10adc3949ba59abbe56e057f20f883e');
	
INSERT INTO `contract_partical`
(`channel_assgin`,`contract_id`,`modified_time`,`partical_unique_id`,`is_delete`)
VALUES (2,48,'2015-04-30 14:00:00','963eb1401db6fc3cfce2a7cd5e9f916d',b'0'),(2,49,'2015-04-30 14:00:00','963eb1401db6fc3cfce2a7cd5e9f916d',b'0'),(1,50,'2015-04-30 14:00:00','e13855b7f2037a9fed12432720d156b5',b'0');
   
SET FOREIGN_KEY_CHECKS=1;

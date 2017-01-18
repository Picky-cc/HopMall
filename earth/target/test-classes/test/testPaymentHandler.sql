SET FOREIGN_KEY_CHECKS=0;

delete from `asset_package`;
INSERT INTO `asset_package` (`id`, `adva_start_date`, `is_available`, `bank_account`, `bank_name`, `create_time`, `latest_settle_date`, `otsd_amt`, `otsd_amt_aval`, `rec_transfee_total_amt`, `remove_time`, `contract_id`, `factoring_contract_id`)
VALUES
	(85,'2015-06-02 00:00:00',00000000,NULL,NULL,'2015-07-01 20:06:27','2015-06-02 00:00:00',8750.00,8750.00,12500.00,NULL,120,61),
	(363,NULL,00000000,NULL,NULL,'2015-07-28 21:48:45','2015-07-28 00:00:00',6720.00,6720.00,9600.00,NULL,531,61),
	(385,NULL,00000000,NULL,NULL,'2015-07-28 21:48:48','2015-07-28 00:00:00',33600.00,33600.00,48000.00,NULL,553,61);

delete from `contract`;
INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`, `transfer_status`)
VALUES
	(531,'2015-04-04','CC-60498',0,2400.00,'2016-04-03',NULL,'',1200.00,00000000,0,NULL,NULL,1,591,533,0),
	(553,'2015-06-05','CC-61444',0,9600.00,'2016-06-04',NULL,'',4800.00,00000000,0,NULL,NULL,1,613,555,0),
	(120,'2015-04-05','CC-60485',0,2500.00,'2016-04-04',NULL,'',1250.00,00000000,0,NULL,NULL,1,181,123,0),
	(404,'2015-05-03','ct-0230',0,2400.00,'2016-05-02',NULL,NULL,3960.00,00000000,NULL,NULL,NULL,9,464,406,2),
	(412,'2015-07-19','ct-0280',0,3200.00,'2016-07-18',NULL,NULL,5160.00,00000000,0,NULL,NULL,9,472,414,2);
	
delete from `house`;
INSERT INTO `house` (`id`, `address`, `area`, `bulid_area`, `city`, `community`, `community_detail`, `hall`, `house_status`, `house_type`, `province`, `room`, `app_id`)
VALUES
	(533,'闵行区浦江浦瑞路369弄31号202室D房',NULL,0,NULL,'SH-600101-D',NULL,0,1,2,NULL,0,1),
	(555,'徐汇区钦川南路531号18号301室B房',NULL,0,NULL,'SH-362872-301B',NULL,0,1,2,NULL,0,1),
	(123,'闵行区华漕申滨路宁虹路69号702室C房',NULL,0,NULL,'SH-600199-C',NULL,0,1,2,NULL,0,1);
	
delete from `customer`;
INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`)
VALUES
	(591,NULL,NULL,NULL,'余良',NULL,'寓见',1),
	(613,NULL,NULL,NULL,'吕钦文',NULL,'寓见',1),
	(181,NULL,NULL,NULL,'刘张保',NULL,'寓见',1);
	
delete from `rent_order`;
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`,`collection_bill_capital_status`,`asset_package_id`,`factoring_contract_id`)
VALUES
	(3997,'2015-08-04',NULL,NULL,'CC-60498-5',2,1200.01,NULL,00000000,NULL,1200.00,531,591,1,NULL,NULL,0,NULL,NULL,'1','363','61'),
	(3998,'2015-09-04',NULL,NULL,'CC-60498-6',0,NULL,NULL,00000000,NULL,1200.00,531,591,0,NULL,NULL,0,NULL,NULL,'0','363','61'),
	(3999,'2015-10-04',NULL,NULL,'CC-60498-7',0,NULL,NULL,00000000,NULL,1200.00,531,591,0,NULL,NULL,0,NULL,NULL,'0','363','61'),
	(4197,'2015-08-05',NULL,NULL,'CC-61444-3',2,4800.00,NULL,00000000,NULL,4800.00,553,613,1,NULL,NULL,0,NULL,NULL,'1','385','61'),
	(4198,'2015-09-05',NULL,NULL,'CC-61444-4',0,NULL,NULL,00000000,NULL,4800.00,553,613,0,NULL,NULL,0,NULL,NULL,'0','385','61'),
	(4199,'2015-10-05',NULL,NULL,'CC-61444-5',0,NULL,NULL,00000000,NULL,4800.00,553,613,0,NULL,NULL,0,NULL,NULL,'0','385','61'),
	(767,'2015-06-05',NULL,NULL,'CC-60485-3',0,0.00,NULL,00000000,NULL,1250.00,120,181,1,NULL,NULL,0,NULL,NULL,'0','85','61'),
	(768,'2015-07-05',NULL,NULL,'CC-60485-4',0,0.00,NULL,00000000,NULL,1250.00,120,181,0,NULL,NULL,0,NULL,NULL,'0','85','61'),
	(769,'2015-08-05',NULL,NULL,'CC-60485-5',2,1250.00,NULL,00000000,NULL,1250.00,120,181,0,NULL,NULL,0,NULL,NULL,'1','85','61'),
	(3027,'2015-07-28','2015-11-02',NULL,'ct-0230-2',2,3960.00,NULL,00000000,'2015-08-03',3960.00,404,464,NULL,'','953fb78f2d6f6ea9cb10a82d399e4464',2,NULL,'meijia_ct-0230-2_3027','1','-1','-1'),
	(3060,'2015-07-18','2015-10-18',NULL,'ct-0280-1',2,8360.00,NULL,00000000,'2015-07-19',8360.00,412,472,NULL,NULL,'953fb78f2d6f6ea9cb10a82d399e4464',2,NULL,'meijia_ct-0280-1_3060','1','-1','-1'),
	(3061,'2015-10-13','2016-01-18',NULL,'ct-0280-2',0,0.00,NULL,00000000,'2015-10-19',5160.00,412,472,NULL,NULL,'953fb78f2d6f6ea9cb10a82d399e4464',2,NULL,'meijia_ct-0280-2_3061','0','-1','-1'),
	(3062,'2016-01-13','2016-04-18',NULL,'ct-0280-3',0,0.00,NULL,00000000,'2016-01-19',5160.00,412,472,NULL,NULL,'953fb78f2d6f6ea9cb10a82d399e4464',2,NULL,'meijia_ct-0280-3_3062','0','-1','-1'),
	(3063,'2016-04-13','2016-07-18',NULL,'ct-0280-4',0,0.00,NULL,00000000,'2016-04-19',5160.00,412,472,NULL,NULL,'953fb78f2d6f6ea9cb10a82d399e4464',2,NULL,'meijia_ct-0280-4_3063','0','-1','-1');


delete from `escrow_agreement`;
INSERT INTO `escrow_agreement` (`id`, `escrow_account_id`, `repayment_account_id`)
VALUES
	(2,11,10);

	
delete from `escrow_contract`;
INSERT INTO `escrow_contract` (`id`, `arrival_days`, `single_service_fee`, `app_id`, `escrow_agreement_id`)
VALUES
	(2,NULL,NULL,9,2);
	
	
	
delete from `payment_agreement`;
INSERT INTO `payment_agreement` (`id`, `collection_accounts`, `repayment_account_id`, `risk_reserve_pay_account_id`, `risk_reserve_query_account_id`, `bank_corporate_account_id`, `usb_key_id`)
VALUES
	(2, NULL, 1, 6, 6, 7, NULL);

delete from `account`;
INSERT INTO `account` (`id`, `account_name`, `account_no`, `bank_id`)
VALUES
	(1,'鼎程（上海）商业保理有限公司','11014671112002',2),
	(6,'鼎程 (上海) 商业保理有限公司','1001184219000050139',5),
	(7,'杭州随地付网络技术有限公司','zfbdg@zufangbao.com',3),
	(10,'王晓娣','6214830285912142',6),
	(11,'杭州随地付网络技术有限公司','1202021519900680156',7);
	
delete from `bank`;
INSERT INTO `bank` (`id`, `code`, `full_name`, `is_deleted`, `short_name`, `branch_no`, `area_code`, `city_code`, `bank_type`)
VALUES
	(2, '', '平安银行上海分行营业部', 00000000, '平安银行', NULL, NULL, NULL, 0),
	(3, '', '支付宝企业版', 00000000, '支付宝', NULL, NULL, NULL, 1),
	(5, 'ICBC', '工商银行上海浦东证券大厦支行', 00000000, '工商银行', NULL, NULL, NULL, 0),
	(6, NULL, '招商银行成都顺城街支行', 00000000, '招商银行', NULL, NULL, NULL, 0),
	(7, 'ICBC', '工行杭州开元支行', 00000000, '工行杭州开元支行', NULL, NULL, NULL, 0);


delete from `factoring_contract`;
INSERT INTO `factoring_contract` (`id`, `adva_matuterm`, `adva_pct`, `adva_start_date`, `adva_term`, `ar_amt`, `ar_cost_rate`, `ar_deposit_amt`, `ar_deposit_amt_aval`, `ar_deposit_amt_input_days`, `ar_deposit_amt_input_type`, `ar_ldgamt`, `borrower_name`, `contract_no`, `cost_deduct_type`, `deposit_deduct_type`, `int_accuretype`, `int_rate`, `latest_settle_date`, `lender_ac_bk_name`, `lender_ac_no`, `lender_name`, `otsd_amt`, `otsd_amt_aval`, `settle_pct`, `app_id`, `company_id`, `nominal_rate`, `payment_agreement_id`)
VALUES
	(61,7,0.70,'2015-06-02 21:47:05',0,0.00,0.00,0.00,0.00,0,1,0.00,'寓见','DCF-XY-FZR905A',2,2,2,0.1966003200,NULL,NULL,NULL,'鼎程（上海）商业保理有限公司',0.00,0.00,1.00,1,2,0.0850000000,2);

	
delete from `app`;
INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `payee_account_name`, `payee_account_no`, `payee_bank_name`)
VALUES
	(9,'meijia','1bf40057e15fd462773c13e0a85e9676',00000000,'http://99196.hotel.cthy.com/','美家公寓',12,NULL,NULL,NULL);


delete from `payment_institution`;
INSERT INTO `payment_institution` (`id`, `alias`, `code`, `day_upper_limit`, `name`, `once_upper_limit`)
VALUES
	(1, '', 'record', NULL, '记录支付', NULL),
	(2, 'alipay', 'alipay', NULL, '支付宝', NULL),
	(3, 'directbank', 'ICBC', NULL, '工行银企互联', NULL),
	(4, 'unionpay', 'unionpay', NULL, '银联', NULL),
	(5, 'directbank', 'CMB', NULL, '招行银企直联', NULL);


	
SET FOREIGN_KEY_CHECKS=1;
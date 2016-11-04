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
delete from charge;
delete from order_payment;
delete from receive_order_map;
delete from contract;
delete from app_account;
delete from transaction_record;
delete from app_arrive_record;

delete from `app_sms_config`;

INSERT INTO `app_arrive_record` (`id`, `amount`, `arrive_record_status`, `pay_ac_no`, `receive_ac_no`, `remark`, `serial_no`, `time`, `app_id`, `drcrf`, `pay_name`, `summary`, `vouh_no`) values ('4', '52500.00', '2', '6222081001006853570', '1001300219000027827', '永业公寓28号604', '2015-03-30-22.39.56.930280', '2015-03-30 22:39:56', '2', '2', '徐月', '网转', '0');
								
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`) values (352, '2015-8-7', '2015-9-6', NULL, 'YXXH1#2307-20150414-5', 2,'1500', NULL,  '\0', '2015-8-7', 14500.00, 76, 137, 0);
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`) values (508, '2015-9-7', '2016-10-6', NULL, 'YXXH1#2307-20150414-6', 1,'1600', NULL,  '\0', '2015-9-7', 14500.00, 93, 137, 0);
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`) values (741, '2015-11-30', '2015-12-29', NULL, 'YCHT7#1201-20150508-12', 2, '1700', NULL, '\0', '2015-11-30', 16400.00, 13, 177, 0);
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`) values (742, '2015-11-30', '2015-12-29', NULL, 'YCHT7#1201-20150508-13', 0,  '1800',NULL, '\0', '2015-11-30', 16400.00, 177, 177, 0);
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`) values (2919, '2015-06-29', '2015-12-29', NULL, 'order-paid-1', 0,  NULL,NULL, '\0', '2015-11-30', 16400.00, 91, 412, 0);
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`) values (509, '2015-9-7', '2016-10-6', NULL, 'YXXH1#2307-20150414-7', 0,'1600', NULL,  '\0', '2015-9-7', 14500.00, 94, 137, 0);
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`) values (510, '2015-7-21', '2016-10-6', NULL, 'YXXH1#2307-20150414-8', 0,'1600', NULL,  '\0', '2015-9-7', 14500.00, 95, 107, 0);
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`) values (493, '2015-9-7', '2016-10-6', NULL, 'YCHT7#1201-20150508-14', 0,'1600', NULL,  '\0', '2015-9-7', 14500.00, 96, 177, 0);
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`) values (495, '2015-9-7', '2016-10-6', NULL, 'YCHT7#1201-20150508-15', 0,'1600', NULL,  '\0', '2015-9-7', 14500.00, 96, 177, 0);
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`) values (496, '2015-9-7', '2016-10-6', NULL, 'YCHT7#1201-20150508-16', 0,'1600', NULL,  '\0', '2015-9-7', 14500.00, 96, 177, 0);
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`) values (497, '2015-9-7', '2016-10-6', NULL, 'YCHT7#1201-20150508-17', 0,'1600', NULL,  '\0', '2015-9-7', 14500.00, 96, 177, 0);

INSERT INTO `order_payment` VALUES ('1', '0.01', '2015-04-23 10:19:00', '2015042300001000350050409308', '494');
INSERT INTO `order_payment` VALUES ('2', '0.01', '2015-04-23 10:19:00', '2015051100001000130051125017', '493');
										
INSERT INTO `receive_order_map` (`id`, `amount`, `association_type`, `create_time`, `is_deducted`, `pay_time`, `app_arrive_record_id`, `order_id`, `receive_order_map_status`) values ('1', '16500.00', '1', '2015-04-03 12:00:00', '\0', '2015-04-03 12:00:00', '4', '742', '0');
INSERT INTO `receive_order_map` (`id`, `amount`, `association_type`, `create_time`, `is_deducted`, `pay_time`, `app_arrive_record_id`, `order_id`, `receive_order_map_status`) values ('2', '16500.00', '1', '2015-04-03 12:00:00', '\0', '2015-04-03 12:00:00', '21', '153', '0');
INSERT INTO `receive_order_map` (`id`, `amount`, `association_type`, `create_time`, `is_deducted`, `pay_time`, `app_arrive_record_id`, `order_id`, `receive_order_map_status`) values ('3', '13500.00', '1', '2015-04-03 12:00:00', '', '2015-04-03 12:00:00', '19', '134', '0');
INSERT INTO `receive_order_map` (`id`, `amount`, `association_type`, `create_time`, `is_deducted`, `pay_time`, `app_arrive_record_id`, `order_id`, `receive_order_map_status`) values ('4', '12600.00', '1', '2015-04-03 12:00:00', '', '2015-04-03 12:00:00', '23', '119', '0');

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`) values ('13', '2015-04-01', 'KXHY1#1803', '0', '33000.00', '2016-03-31', null, '', '16500.00', '\0', '0', null, null, '2', '106', '13');
INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`) values ('93', '2014-11-01', 'HNHY3#602', '0', '25200.00', '2015-10-31', null, '', '12600.00', '\0', '0', null, null, '2', '107', '14');
INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`) values ('15', '2014-06-01', 'HQHY1#33B', '0', '34000.00', '2015-11-30', null, '', '17000.00', '\0', '0', null, null, '2', '108', '15');
INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`) values ('16', '2014-05-01', 'JAGJ1804', '0', '27000.00', '2016-04-30', null, '', '13500.00', '\0', '0', null, null, '2', '109', '16');
INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`) values ('91', '2014-05-01', 'TEST-ZFB', '0', '27000.00', '2016-04-30', null, '', '13500.00', '\0', '0', null, null, '3', '110', '17');
INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`) values ('94', '2014-11-01', 'HNHY3#6002', '0', '25200.00', '2015-10-31', null, '', '12600.00', '\0', '0', null, null, '3', '111', '18');
INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`) values ('95', '2014-11-01', 'HNHY3#60002', '0', '25200.00', '2015-10-31', null, '', '12600.00', '\0', '0', null, null, '4', '112', '19');
INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`) values ('96', '2014-11-01', 'YCHT7#1201-20150508-17', '0', '25200.00', '2015-10-31', null, '', '12600.00', '\0', '0', null, null, '4', '113', '20');

INSERT INTO `app_account` (`id`, `channel`, `header`, `logo`, `name`, `app_id`)
VALUES
	(1,1,'1-logo.png','banner-logo-xiaoyu.png','小寓科技',1),
	(2,2,NULL,NULL,'优帕克',2),
	(3,1,NULL,'banner-logo.png','租房宝测试账号',3);

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`)
VALUES
	(1,'xiaoyu','70991db75ebb24fa0993f4fa25775022',00000000,'http://beta.demo2do.com/jupiter/','寓见',5),
	(2,'youpark','123456',00000000,'','优帕克',4),
	(4,'test4Zufangbao','2e85ae999845f25faf8ea7b514ee0aca',00000000,'http://e.zufangbao.cn','租房宝测试账号',9);

INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`) values ('412', null, null, '13732255076', '上海化耀国际贸易有限公司', null, '优帕克', '4');
INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`) values ('107', null, null, null, '吴林飞', null, '优帕克', '2');
INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`) values ('108', null, null, null, 'R.B', null, '优帕克', '2');
INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`) values ('109', null, null, null, 'OWENBOYDALEXANDER', null, '优帕克', '2');
INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`) values ('137', null, null, null, '伊朗马汉航空公司上海代表处', null, '优帕克', '2');

--INSERT INTO `asset_package` (`id`, `adva_start_date`, `is_available`, `bank_account`, `bank_name`, `create_time`, `latest_settle_date`, `otsd_amt`, `otsd_amt_aval`, `rec_transfee_total_amt`, `remove_time`, `contract_id`, `factoring_contract_id`) values ('13', '2015-04-03 00:00:00', '', null, null, '2015-04-02 00:00:00', '2015-04-03 00:00:00', '138600.00', '138600.00', '198000.00', null, '13', '58');
--INSERT INTO `asset_package` (`id`, `adva_start_date`, `is_available`, `bank_account`, `bank_name`, `create_time`, `latest_settle_date`, `otsd_amt`, `otsd_amt_aval`, `rec_transfee_total_amt`, `remove_time`, `contract_id`, `factoring_contract_id`) values ('14', '2015-04-03 00:00:00', '', null, null, '2015-04-02 00:00:00', '2015-04-03 00:00:00', '61740.00', '61740.00', '88200.00', null, '14', '58');
--INSERT INTO `asset_package` (`id`, `adva_start_date`, `is_available`, `bank_account`, `bank_name`, `create_time`, `latest_settle_date`, `otsd_amt`, `otsd_amt_aval`, `rec_transfee_total_amt`, `remove_time`, `contract_id`, `factoring_contract_id`) values ('15', '2015-04-03 00:00:00', '', null, null, '2015-04-02 00:00:00', '2015-04-03 00:00:00', '95200.00', '95200.00', '136000.00', null, '15', '58');
--INSERT INTO `asset_package` (`id`, `adva_start_date`, `is_available`, `bank_account`, `bank_name`, `create_time`, `latest_settle_date`, `otsd_amt`, `otsd_amt_aval`, `rec_transfee_total_amt`, `remove_time`, `contract_id`, `factoring_contract_id`) values ('16', '2015-04-03 00:00:00', '', null, null, '2015-04-02 00:00:00', '2015-04-03 00:00:00', '95200.00', '95200.00', '136000.00', null, '177', '58');
--
--
--INSERT INTO `factoring_contract` (`id`, `adva_matuterm`, `adva_pct`, `adva_start_date`, `adva_term`, `ar_amt`, `ar_cost_rate`, `ar_deposit_amt`, `ar_deposit_amt_aval`, `ar_deposit_amt_input_days`, `ar_deposit_amt_input_type`, `ar_ldgamt`, `borrower_name`, `contract_no`, `cost_deduct_type`, `deposit_deduct_type`, `int_accuretype`, `int_rate`, `latest_settle_date`, `lender_ac_bk_name`, `lender_ac_no`, `lender_name`, `otsd_amt`, `otsd_amt_aval`, `settle_pct`, `app_id`, `company_id`) values ('58', '7', '0.70', '2015-04-02 00:00:00', '0', '8090410.00', '0.00', '0.00', '0.00', '15', '1', '8090410.00', '优帕克', 'DCF-YPK-LR903A', '2', '1', '2', '0.197143', null, null, null, '鼎程（上海）商业保理有限公司', '5663287.00', '5663287.00', '1.00', '2', '2');
--INSERT INTO `factoring_contract` (`id`, `adva_matuterm`, `adva_pct`, `adva_start_date`, `adva_term`, `ar_amt`, `ar_cost_rate`, `ar_deposit_amt`, `ar_deposit_amt_aval`, `ar_deposit_amt_input_days`, `ar_deposit_amt_input_type`, `ar_ldgamt`, `borrower_name`, `contract_no`, `cost_deduct_type`, `deposit_deduct_type`, `int_accuretype`, `int_rate`, `latest_settle_date`, `lender_ac_bk_name`, `lender_ac_no`, `lender_name`, `otsd_amt`, `otsd_amt_aval`, `settle_pct`, `app_id`, `company_id`) values ('59', '8', '0.70', '2015-04-13 17:27:38', '0', '55043.20', '0.00', '0.00', '0.00', '0', '0', '55043.20', '寓见', 'DCF-YJ-LR903A', '2', '0', '0', '0.197143', null, null, null, '鼎程（上海）商业保理有限公司', '0.80', '0.80', '1.00', '1', '2');


INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`)
VALUES
	(2,'上海陆家嘴','鼎程（上海）商业保理有限公司','鼎程'),
	(3,'杭州万塘路8号','杭州随地付网络技术有限公司','租房宝'),
	(4,'上海','上海优帕克投资管理有限公司','优帕克'),
	(5,'上海','小寓科技','小寓');

INSERT INTO `transaction_record` (`id`, `card_no`, `channel`, `last_modified_time`, `merchant_payment_no`, `order_no`, `pay_money`, `request_no`, `trade_no`, `transaction_record_status`, `app_id`, `payment_institution_id`) values ('23', null, '1', '2015-04-13 23:31:14', '587589379743482880', 'CC-60446-2', '0.01', '20150413081554587589379743482880', '2015041300001000450050496693', '2', '1', '2');
INSERT INTO `transaction_record` (`id`, `card_no`, `channel`, `last_modified_time`, `merchant_payment_no`, `order_no`, `pay_money`, `request_no`, `trade_no`, `transaction_record_status`, `app_id`, `payment_institution_id`) values ('24', null, '1', '2015-04-13 20:19:45', '587590022008865792', 'CC-60446-2', '0.01', '20150413081826587590022008865792', '2015041300001000330052443978', '1', '1', '2');
INSERT INTO `transaction_record` (`id`, `card_no`, `channel`, `last_modified_time`, `merchant_payment_no`, `order_no`, `pay_money`, `request_no`, `trade_no`, `transaction_record_status`, `app_id`, `payment_institution_id`) values ('25', null, '1', '2015-04-13 20:24:44', '587591431290815488', 'CC-60446-2', '0.01', '20150413082402587591431290815488', '2015041300001000330052444444', '1', '1', '2');
INSERT INTO `transaction_record` (`id`, `card_no`, `channel`, `last_modified_time`, `merchant_payment_no`, `order_no`, `pay_money`, `request_no`, `trade_no`, `transaction_record_status`, `app_id`, `payment_institution_id`) values ('26', null, '1', '2015-04-13 20:29:31', '587592712440972288', 'CC-60446-2', '0.01', '20150413082908587592712440972288', '2015041300001000450050497773', '1', '1', '2');
INSERT INTO `transaction_record` (`id`, `card_no`, `channel`, `last_modified_time`, `merchant_payment_no`, `order_no`, `pay_money`, `request_no`, `trade_no`, `transaction_record_status`, `app_id`, `payment_institution_id`) values ('27', null, '1', '2015-04-13 20:33:02', '587592810998727680', 'CC-60446-2', '0.01', '20150413082931587592810998727680', '2015041300001000330052445086', '1', '1', '2');
INSERT INTO `transaction_record` (`id`, `card_no`, `channel`, `last_modified_time`, `merchant_payment_no`, `order_no`, `pay_money`, `request_no`, `trade_no`, `transaction_record_status`, `app_id`, `payment_institution_id`) values ('28', null, '1', '2015-04-13 20:36:11', '587594235258536960', 'CC-60446-2', '0.01', '20150413083511587594235258536960', '2015041300001000450050498312', '1', '1', '2');
INSERT INTO `transaction_record` (`id`, `card_no`, `channel`, `last_modified_time`, `merchant_payment_no`, `order_no`, `pay_money`, `request_no`, `trade_no`, `transaction_record_status`, `app_id`, `payment_institution_id`) values ('29', null, '1', '2015-04-13 20:53:52', '587598844224013312', 'CC-60446-2', '0.01', '20150413085329587598844224013312', '2015041300001000450050500061', '1', '1', '2');

INSERT INTO `app_sms_config` (`id`, `is_deleted`, `sms_rule_suit`, `app_id`, `is_need_link`, `default_sms_rule`)
VALUES
	(8, 00000000, '[{\"contentTemplate\":\"(##appName##)您当期房租##amount##元已到支付日期，请及时完成支付！请点击链接[##link##]【随地付】\",\"ruleScript\":\"function execute(order){return (order.getDistanceFromTodayToDueDay() == 0);}\",\"ruleUniqueId\":\"c99c94d7-74d9-4f68-a845-d1f7e7ea48f6\"}]', 4, 00000001, '{\"contentTemplate\":\"(##appName##)您当期房租##amount##元已到支付日期，请及时完成支付！请点击链接[##link##]【随地付】\",\"ruleScript\":\"function execute(order){return true;}\",\"ruleUniqueId\":\"936c9600-d26f-45fa-b42e-cab4fd492497\"}');


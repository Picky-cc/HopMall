SET FOREIGN_KEY_CHECKS=0;
delete from principal;
delete from sms_log;
delete from `app`;
delete from `company`;
delete from `contract`;
delete from `customer`;
delete from `house`;
delete from `contract_account`;
delete from `rent_order`;

INSERT INTO `contract` (`id`,`contract_no`, `contract_status`, `payment_instrument`, `app_id`, `customer_id`, `house_id`,  `contract_type`,`non_deposit_guarantee_way`) VALUES 
(1,'HNHY3#602', 0, 2, 2, 1, 1, 0, 0);

INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`,`virtual_account_unique_id`) values
(1, '2015-8-7', '2015-9-6', NULL, 'YXXH1#2307-20150414-5', 2,'1500', NULL,  '\0', '2015-8-7', 14500.00, 1, 1, 0,'953fb78f2d6f6ea9cb10a82d399e4464');

INSERT INTO `contract_account` (`id`, `pay_ac_no`, `bankcard_type`, `contract_id`, `payer_name`) VALUES
(1, 'pay_ac_no_1', 0, 1, 'payer_name_1'),
(2, 'pay_ac_no_2', 0, 1, 'payer_name_2');


INSERT INTO `customer` (`id`,`account`, `city`, `mobile`, `name`, `app_id`) VALUES 
(1, 'account', 'city', 'mobile_1', '吴林飞', 2);


INSERT INTO `house` (`id`, `address`, `community`, `app_id`,`bulid_area`,`hall`,`room`) VALUES 
(1, '徐汇区淮海中路183弄3号602室', '汇宁花园3#602', 2,100,50,50);


INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `payee_account_name`, `payee_account_no`, `payee_bank_name`)
VALUES
	(1, 'xiaoyu', '70991db75ebb24fa0993f4fa25775022', 00000000, 'http://beta.demo2do.com/jupiter/', '寓见', 5, NULL, NULL, NULL),
	(2, 'youpark', '123456', 00000000, '', '优帕克', 4, NULL, NULL, NULL),
	(3, 'test4Zufangbao', '2e85ae999845f25faf8ea7b514ee0aca', 00000000, 'http://e.zufangbao.cn', '租房宝测试账号', 3, NULL, NULL, NULL),
	(4, 'zhuke', 'cb742d55634a532060ac7387caa8d242', 00000000, 'http://zkroom.com/', '杭州驻客网络技术有限公司', 6, NULL, NULL, NULL),
	(5, 'laoA', '744a38b1672b728dc35a68f7a10df082', 00000000, 'http://www.13980.com', '上海元轼信息咨询有限公司', 7, NULL, NULL, NULL),
	(6, 'keluoba', '30f4d225438a7fd1541fe1a055420dfd', 00000000, 'http://keluoba.com', '柯罗芭', 8, NULL, NULL, NULL),
	(7, 'testForXiaoyu', '2138ed4b66cebbded5753f3c59a064ae', 00000000, 'http://xxx.com', '小寓测试帐号', 10, NULL, NULL, NULL),
	(8, 'woju', '495d49ae55fd794036576aa8f71dbb43', 00000000, 'http://www.woju.com', '杭州蜗居', 11, NULL, NULL, NULL),
	(9, 'meijia', '1bf40057e15fd462773c13e0a85e9676', 00000000, 'http://99196.hotel.cthy.com/', '美家公寓', 12, NULL, NULL, NULL),
	(10, 'anxin', 'd1cd2618432723c478fab102bdfa2e11', 00000000, 'http://anxin.com', '安心公寓', 13, NULL, NULL, NULL),
	(11, 'yuanlai', 'eaaf6bfe5c98e042822b60cae955a276', 00000000, 'http://yuanlai.com', '源涞国际', 14, NULL, NULL, NULL);

INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`, `bank_name`, `card_no`, `name`)
VALUES
	(2, '上海陆家嘴', '鼎程（上海）商业保理有限公司', '鼎程', NULL, NULL, NULL),
	(3, '杭州万塘路8号', '杭州随地付网络技术有限公司', '租房宝', NULL, NULL, NULL),
	(4, '上海', '上海优帕克投资管理有限公司', '优帕克', NULL, NULL, NULL),
	(5, '上海', '小寓科技', '小寓', NULL, NULL, NULL),
	(6, '上海', '杭州驻客网络技术有限公司', '驻客', NULL, NULL, NULL),
	(7, '上海', '上海元轼信息咨询有限公司', '老A', NULL, NULL, NULL),
	(8, '上海', '柯罗芭', '柯罗芭', NULL, NULL, NULL),
	(9, '杭州', '租房宝测试', '租房宝测试', NULL, NULL, NULL),
	(10, NULL, '小寓测试帐号', '小寓测试帐号', NULL, NULL, NULL),
	(11, NULL, '杭州蜗居', '杭州蜗居', NULL, NULL, NULL),
	(12, NULL, '美家公寓', '美家公寓', NULL, NULL, NULL),
	(13, NULL, '安心公寓', '安心公寓', NULL, NULL, NULL),
	(14, NULL, '源涞国际', '源涞国际', NULL, NULL, NULL);

INSERT INTO `house` ( `address`, `area`, `bulid_area`, `city`, `community`, `community_detail`, `hall`, `house_status`, `house_type`, `province`, `room`, `app_id`)
VALUES
	( '汇川路88号1#1803', NULL, 0, NULL, '凯欣豪苑1#1803', NULL, 0, 1, 1, NULL, 0, 2),
	( '汇川路88号1#1803', NULL, 0, NULL, '凯欣豪苑1#1803', NULL, 0, 1, 1, NULL, 0, 2),
	( '汇川路88号1#1803', NULL, 0, NULL, '凯欣豪苑1#1803', NULL, 0, 1, 1, NULL, 0, 2),
	( '汇川路88号1#1803', NULL, 0, NULL, '凯欣豪苑1#1803', NULL, 0, 1, 1, NULL, 0, 2),
	( '汇川路88号1#1803', NULL, 0, NULL, '凯欣豪苑1#1803', NULL, 0, 1, 1, NULL, 0, 2),
	( '汇川路88号1#1803', NULL, 0, NULL, '凯欣豪苑1#1803', NULL, 0, 1, 1, NULL, 0, 2),
	( '汇川路88号1#1803', NULL, 0, NULL, '凯欣豪苑1#1803', NULL, 0, 1, 1, NULL, 0, 2),
	( '汇川路88号1#1803', NULL, 0, NULL, '凯欣豪苑1#1803', NULL, 0, 1, 1, NULL, 0, 2),
	( '汇川路88号1#1803', NULL, 0, NULL, '凯欣豪苑1#1803', NULL, 0, 1, 1, NULL, 0, 2),
	( '汇川路88号1#1803', NULL, 0, NULL, '凯欣豪苑1#1803', NULL, 0, 1, 1, NULL, 0, 2),
	( '汇川路88号1#1803', NULL, 0, NULL, '凯欣豪苑1#1803', NULL, 0, 1, 1, NULL, 0, 2)
	;
INSERT INTO `house` ( `id`,`address`, `area`, `bulid_area`, `city`, `community`, `community_detail`, `hall`, `house_status`, `house_type`, `province`, `room`, `app_id`)
VALUES
	(364, '益州大道北段388号城市春天1-1703', NULL, 0, NULL, '凯欣豪苑1#1803', NULL, 0, 1, 1, NULL, 0, 2);

INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`)
VALUES
	(106, NULL, NULL, '13732255076', '朱师云', NULL, '优帕克', 2),
	(107, NULL, NULL, NULL, '吴林飞', NULL, '优帕克', 2),
	(108, NULL, NULL, NULL, 'R.B', NULL, '优帕克', 2),
	(109, NULL, NULL, NULL, 'OWENBOYDALEXANDER', NULL, '优帕克', 2),
	(110, NULL, NULL, NULL, 'QM', NULL, '优帕克', 2),
	(111, NULL, NULL, NULL, 'eileen', NULL, '优帕克', 2),
	(118, NULL, NULL, NULL, '目黑克彦', NULL, '优帕克', 2),
	(119, NULL, NULL, NULL, '冼的坤', NULL, '优帕克', 2),
	(120, NULL, NULL, NULL, 'Javier Diaz', NULL, '优帕克', 2),
	(121, NULL, NULL, NULL, 'Michael Charles Madely', NULL, '优帕克', 2),
	(122, NULL, NULL, NULL, 'Missart Reynoso Agustina', NULL, '优帕克', 2),
	(124, NULL, NULL, NULL, '参环国际货运代理（上海）有限公司', NULL, '优帕克', 2),
	(125, NULL, NULL, NULL, '李宇阳', NULL, '寓见', 1),
	(126, NULL, NULL, NULL, '杨林奎', NULL, '寓见', 1),
	(127, NULL, NULL, NULL, '陈超', NULL, '寓见', 1),
	(128, NULL, NULL, NULL, '陈超', NULL, '寓见', 1);

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`, `transfer_status`, `contract_life_cycle`, `contract_type`)
VALUES
	(13, '2015-04-01', 'test-meijia-20150710', 0, 33000.00, '2016-03-31', NULL, '', 16500.00, 00000000, 1, NULL, NULL, 9, 106, 13, 0, 1, 0);
	
INSERT INTO `contract` (`begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`, `transfer_status`, `contract_life_cycle`, `contract_type`)
VALUES
	( '2015-04-01', 'test-meijia-20150710', 0, 33000.00, '2016-03-31', NULL, '', 16500.00, 00000000, 1, NULL, NULL, 9, 107, 13, 0, 1, 0),
	( '2015-04-01', 'test-meijia-20150710', 0, 33000.00, '2016-03-31', NULL, '', 16500.00, 00000000, 1, NULL, NULL, 9, 107, 13, 0, 1, 0),
		( '2015-04-01', 'test-meijia-20150710', 0, 33000.00, '2016-03-31', NULL, '', 16500.00, 00000000, 1, NULL, NULL, 9, 107, 13, 0, 1, 0),
	( '2015-04-01', 'test-meijia-20150710', 0, 33000.00, '2016-03-31', NULL, '', 16500.00, 00000000, 1, NULL, NULL, 9, 107, 13, 0, 1, 0),
	( '2015-04-01', 'test-meijia-20150710', 0, 33000.00, '2016-03-31', NULL, '', 16500.00, 00000000, 1, NULL, NULL, 9, 107, 13, 0, 1, 0),
	( '2015-04-01', 'test-meijia-20150710', 0, 33000.00, '2016-03-31', NULL, '', 16500.00, 00000000, 1, NULL, NULL, 9, 107, 13, 0, 1, 0),
		( '2015-04-01', 'test-meijia-20150710', 0, 33000.00, '2016-03-31', NULL, '', 16500.00, 00000000, 1, NULL, NULL, 9, 107, 13, 0, 1, 0),
	( '2015-04-01', 'test-meijia-20150710', 0, 33000.00, '2016-03-31', NULL, '', 16500.00, 00000000, 1, NULL, NULL, 9, 107, 13, 0, 1, 0),
	( '2015-04-01', 'test-meijia-20150710', 0, 33000.00, '2016-03-31', NULL, '', 16500.00, 00000000, 1, NULL, NULL, 9, 107, 13, 0, 1, 0),
		( '2015-04-01', 'test-meijia-20150710', 0, 33000.00, '2016-03-31', NULL, '', 16500.00, 00000000, 1, NULL, NULL, 9, 107, 13, 0, 1, 0),
	( '2015-04-01', 'test-meijia-20150710', 0, 33000.00, '2016-03-31', NULL, '', 16500.00, 00000000, 1, NULL, NULL, 9, 107, 13, 0, 1, 0)
	;

INSERT INTO `principal` (`id`, `authority`, `name`, `password`) VALUES ('1', 'ROLE_SUPER_USER', 'root', 'befd2450f81f88ecc5fbcc4c1f97f0b4'),
('2', 'ROLE_INSTITUTION', '2', 'dingcheng', 'df5b59f050d316b72b17f73714473f8b'),
('3', 'ROLE_APP', '1', 'xiaoyu', 'e10adc3949ba59abbe56e057f20f883e'),
('4', 'ROLE_LEASING_ASSET_MANAGER', '2', 'youpark', '086246bffb2de7288946151fc900db59'),
('5', 'ROLE_INSTITUTION', '2', 'DCF001', '3cf5eaa7d33e0d023e811c90cd6f2f73'),
('6', 'ROLE_INSTITUTION', '2', 'DCF002', 'adb29c63523254a343864e97c84e6360'),
 ('7', 'ROLE_APP', '5', 'laoA', 'e10adc3949ba59abbe56e057f20f883e'),
('8', 'ROLE_SUPER_USER', NULL, 'guanzhishi', 'befd2450f81f88ecc5fbcc4c1f97f0b4'),
('9', 'ROLE_SUPER_USER', NULL, 'zhangjianming', '14ebdd77cd348da2ee411e118d125b53'),
('10', 'ROLE_SUPER_USER', NULL, 'dongjigong', 'cb8d07590edc38e54bb40e3719acc189'),
 ('11', 'ROLE_SUPER_USER', NULL, 'lishuzhen', 'a82a92061f9ad7a549a843658107141b'),
 ('12', 'ROLE_SUPER_USER', NULL, 'chenjie', '64b2f4c902086b2220afd5b05ad25fb9'),
('13', 'ROLE_APP', '3', 'test4Zufangbao', 'e10adc3949ba59abbe56e057f20f883e'),
 ('14', 'ROLE_SUPER_USER', 'zhushiyun', '2ba9a0c7b7bf6b07846c7468c32552d1'),
 ('15', 'ROLE_SUPER_USER', NULL, 'lixu', 'a82a92061f9ad7a549a843658107141b'),
 ('16', 'ROLE_SUPER_USER', NULL, 'jinyin', '9c74066927e18620a8bc89580f23e8ed'),
 ('17', 'ROLE_APP', '8', 'woju', 'e10adc3949ba59abbe56e057f20f883e'),
 ('18', 'ROLE_SUPER_USER', NULL, 'wukai', 'c4de644efae81ff323fa45b50c31296b'),
('19', 'ROLE_APP', '9', 'meijia', 'e10adc3949ba59abbe56e057f20f883e'),
('20', 'ROLE_BANK_APP', '2', 'YoparkTest', 'befd2450f81f88ecc5fbcc4c1f97f0b4'),
('21', 'ROLE_MERCURY_APP', '1', 'XY001', 'e10adc3949ba59abbe56e057f20f883e'),
('22', 'ROLE_SUPER_USER', NULL, 'meikehuan', 'e10adc3949ba59abbe56e057f20f883e')
,('23', 'ROLE_LEASING_ASSET_MANAGER', '2', 'fishmei', 'e10adc3949ba59abbe56e057f20f883e');


TRUNCATE TABLE `sms_log`;
insert into `sms_log`(`id`,`app_id`,`send_result`,`send_time`,`sms_content`,`sms_log_type`,`mobile`) values
('1','test4Meijia','-1','2015-07-22 21:22:13','(成都美家)您当期房租1.00元已到支付日期，请及时完成支付！【随地付】','0',null),
('2','test4Meijia','-1','2015-07-22 21:24:44','(成都美家)您当期房租1.00元已到支付日期，请及时完成支付！【随地付】','0',null),
('3','test4Meijia','0','2015-07-22 21:26:37','(成都美家)您当期房租1.00元已到支付日期，请及时完成支付！【随地付】','0',null),
('4','test4Meijia','0','2015-07-23 11:45:50','(成都美家)您当期房租1.00元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLicmJc]【随地付】','0',null),
('5','test4Meijia','0','2015-07-23 12:02:36','(成都美家)您当期房租0.10元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLiFRu4]【随地付】','0',null),
('6','test4Meijia','0','2015-07-23 12:15:18','(成都美家)您当期房租0.10元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLisOkO]【随地付】','0',null),
('7','test4Meijia','0','2015-07-23 14:19:24','(成都美家)您当期房租0.10元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLisOkO]【随地付】','0',null),
('8','testForwoju','0','2015-07-23 14:59:14','(杭州蜗居)您当期房租0.10元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RL6yhqI]【随地付】','0',null),
('9','woju','0','2015-07-23 15:18:19','(杭州蜗居2)您当期房租0.10元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RL6UJEf]【随地付】','0',null),
('10','woju','0','2015-07-24 15:30:45','(杭州蜗居2)您当期房租0.10元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RL6UJEf]【随地付】','0',null),
('11','test4Meijia','0','2015-07-25 12:00:02','(成都美家)您当期房租1.00元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLaiMz1]【随地付】','1',null),
('12','testForZhuKe','0','2015-07-27 15:50:32','(驻客测试)您当期房租0.02元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLon4MA]【随地付】','0',null),
('13','testForZhuKe','0','2015-07-27 15:50:42','(驻客测试)您当期房租0.04元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLon4sG]【随地付】','0',null),
('14','testForZhuKe','0','2015-07-27 15:50:50','(驻客测试)您当期房租0.04元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLonbNP]【随地付】','0',null),
('15','testForZhuKe','0','2015-07-27 15:51:06','(驻客测试)您当期房租0.04元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLonbNP]【随地付】','0',null),
('16','testForZhuKe','0','2015-07-27 15:51:12','(驻客测试)您当期房租0.04元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLonGFb]【随地付】','0',null),
('17','testForZhuKe','0','2015-07-27 15:51:43','(驻客测试)您当期房租0.04元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLonGFb]【随地付】','0',null),
('18','testForZhuKe','0','2015-07-27 15:51:55','(驻客测试)您当期房租0.02元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLoncVD]【随地付】','0',null),
('19','testForZhuKe','0','2015-07-27 15:52:03','(驻客测试)您当期房租0.04元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLoncBZ]【随地付】','0',null),
('20','testForZhuKe','0','2015-07-27 15:52:10','(驻客测试)您当期房租0.04元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLonVxI]【随地付】','0',null),
('21','testForZhuKe','0','2015-07-27 15:52:20','(驻客测试)您当期房租0.06元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLonfPT]【随地付】','0',null),
('22','testForZhuKe','0','2015-07-27 15:52:28','(驻客测试)您当期房租0.02元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLonfNS]【随地付】','0',null),
('23','testForZhuKe','0','2015-07-27 15:52:37','(驻客测试)您当期房租0.04元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLonI2g]【随地付】','0',null),
('24','testForZhuKe','0','2015-07-27 15:52:48','(驻客测试)您当期房租0.04元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLonI1N]【随地付】','0',null),
('25','testForZhuKe','0','2015-07-27 15:52:56','(驻客测试)您当期房租0.06元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLonMiY]【随地付】','0',null),
('26','testForZhuKe','0','2015-07-27 15:53:02','(驻客测试)您当期房租0.03元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLonM1r]【随地付】','0',null),
('27','woju','0','2015-07-27 15:53:13','(杭州蜗居2)您当期房租0.05元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLonxNc]【随地付】','0',null),
('28','woju','0','2015-07-27 15:53:22','(杭州蜗居2)您当期房租0.05元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLonJyh]【随地付】','0',null),
('29','woju','0','2015-07-27 15:53:30','(杭州蜗居2)您当期房租0.03元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLonJEz]【随地付】','0',null),
('30','woju','0','2015-07-27 18:29:03','(杭州蜗居2)您当期房租0.10元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RL6UJEf]【随地付】','0',null),
('31','woju','0','2015-07-28 10:53:20','(杭州蜗居2)您当期房租0.10元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLKWMia]【随地付】','0',null),
('32','testForZhuKe','-1','2015-07-28 12:00:01','(驻客测试)您当期房租10.00元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLKTkhV]【随地付】','1',null),
('33','woju','0','2015-07-28 12:00:02','(杭州蜗居2)您当期房租0.10元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLKWMia]【随地付】','1',null),
('34','woju','0','2015-07-28 20:35:11','(杭州蜗居2)您当期房租0.03元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLonJEz]【随地付】','0',null),
('35','woju','0','2015-07-28 20:40:58','(杭州蜗居2)您当期房租0.05元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RLonJyh]【随地付】','0',null),
('36','woju','0','2015-07-29 12:00:02','(杭州蜗居2)提醒您：您当期房租账单金额12.00元，支付日期2015-07-30，请及时支付！请点击链接[http://t.cn/RL9gtz9]【随地付】','1',null),
('37','woju','0','2015-07-29 12:00:02','(杭州蜗居2)提醒您：您当期房租账单金额12.00元，支付日期2015-07-30，请及时支付！请点击链接[http://t.cn/RL9gtZg]【随地付】','1',null),
('38','woju','0','2015-07-29 14:07:11','(杭州蜗居2)您当期房租12.00元已到支付日期，请及时完成支付！请点击链接[http://t.cn/RL9gtZg]【随地付】','0','13732255076');

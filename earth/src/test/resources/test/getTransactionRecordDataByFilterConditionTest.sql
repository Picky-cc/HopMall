SET FOREIGN_KEY_CHECKS = 0;

delete from transaction_record;
delete from transaction_record_log;
delete from principal;
delete from app;
delete from `company`;

insert into `app`(`id`,`app_id`,`app_secret`,`is_disabled`,`host`,`name`,`company_id`) values
('1','xiaoyu','70991db75ebb24fa0993f4fa25775022',b'0','http://beta.demo2do.com/jupiter/','寓见','5'),
('2','youpark','123456',b'0','','优帕克','4'),
('3','test4Zufangbao','2e85ae999845f25faf8ea7b514ee0aca',b'0','http://e.zufangbao.cn','租房宝测试账号','9'),
('4','zhuke','cb742d55634a532060ac7387caa8d242',b'0','http://zkroom.com/','杭州驻客网络技术有限公司','6'),
('5','laoA','744a38b1672b728dc35a68f7a10df082',b'0','http://www.13980.com','上海元轼信息咨询有限公司','7'),
('6','keluoba','30f4d225438a7fd1541fe1a055420dfd',b'0','http://keluoba.com','柯罗芭','8'),
('7','testForXiaoyu','2138ed4b66cebbded5753f3c59a064ae',b'0','http://xxx.com','小寓测试帐号','10'),
('8','woju','495d49ae55fd794036576aa8f71dbb43',b'0','http://www.woju.com','杭州蜗居','11'),
('9','meijia','1bf40057e15fd462773c13e0a85e9676',b'0','http://99196.hotel.cthy.com/','美家公寓','12');

insert into `principal`(`id`,`authority`,`key_id`,`name`,`password`) values
('1','ROLE_SUPER_USER',null,'root','befd2450f81f88ecc5fbcc4c1f97f0b4'),
('2','ROLE_INSTITUTION','2','dingcheng','df5b59f050d316b72b17f73714473f8b'),
('3','ROLE_APP','1','xiaoyu','e10adc3949ba59abbe56e057f20f883e'),
('4','ROLE_BANK_APP','2','yopark','086246bffb2de7288946151fc900db59');

insert into `transaction_record`(`id`,`card_no`,`channel`,`last_modified_time`,`merchant_payment_no`,`order_no`,`pay_money`,`request_no`,`trade_no`,`transaction_record_status`,`app_id`,`payment_institution_id`) values
('7',null,'2','2015-04-23 12:01:00',null,'KXHY1#1803-20150402-1',16500.00,null,'0','2','2','3'),
('8',null,'2','2015-04-23 12:02:00',null,'JAGJ1804-20150402-12',13500.00,null,'0','2','2','3'),
('9',null,'2','2015-04-23 12:03:00',null,'HNHY3#602-20150402-6',12600.00,null,'0','2','2','3'),
('10',null,'2','2015-04-23 12:04:00',null,'HQHY1#33B-20150402-11',17000.00,null,'0','2','2','3');

insert into `transaction_record_log`(`id`,`modified_time`,`operator_id`,`transaction_record_operate_status`,`transaction_record_id`) values
('5','2015-04-13 19:27:02','130','1','7'),
('6','2015-04-13 20:16:04','126','1','8'),
('7','2015-04-13 19:27:02','130','3','7'),
('8','2015-04-13 20:19:06','126','1','10');


INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`, `bank_name`, `card_no`, `name`)
VALUES
	(9, '杭州', '租房宝测试', '租房宝测试', NULL, NULL, NULL),
	(5, '上海', '小寓科技', '小寓', NULL, NULL, NULL),
	(4, '上海', '上海优帕克投资管理有限公司', '优帕克', NULL, NULL, NULL),
	(6, '上海', '杭州驻客网络技术有限公司', '驻客', NULL, NULL, NULL),
	(7, '上海', '上海元轼信息咨询有限公司', '老A', NULL, NULL, NULL),
	(8, '上海', '柯罗芭', '柯罗芭', NULL, NULL, NULL),
	(10, NULL, '小寓测试帐号', '小寓测试帐号', NULL, NULL, NULL),
	(11, NULL, '杭州蜗居', '杭州蜗居', NULL, NULL, NULL),
	(12, NULL, '美家公寓', '美家公寓', NULL, NULL, NULL),
	(13, NULL, '安心公寓', '安心公寓', NULL, NULL, NULL),
	(14, NULL, '源涞国际', '源涞国际', NULL, NULL, NULL);


SET FOREIGN_KEY_CHECKS = 1;
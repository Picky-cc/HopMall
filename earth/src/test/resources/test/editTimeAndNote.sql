SET FOREIGN_KEY_CHECKS = 0;

delete from `system_log`;

delete from finance_payment_record;
insert into `finance_payment_record`(`id`,`creator_id`,`pay_money`,`payment_no`,`payment_time`,`order_id`,`batch_pay_record_id`,`bank_name`,`card_no`,`payee_name`,`uuid`,`interest_time`,`interest_adjust_note`) values
('1034','9','1900.00','74867953','2015-11-12 16:50:44','8265','381',null,null,null,null,null,null),
('1035','9','2100.00','1545091189097420014','2015-09-11 15:16:47','8266','381',null,null,null,null,null,null),
('1036','9','1124.64','20151112165101','2015-09-11 15:16:47','1696','382',null,null,null,null,null,null),
('1037','9','0242.24','1545091189097420014','2015-11-11 14:10:00','3034','382',null,null,null,null,null,null),
('1038','9','1900.00','20151112165101','2015-09-11 15:16:47','1869','385',null,null,null,null,null,null),
('1039','9','1900.00','74867953','2015-09-11 15:16:47','6950','382',null,null,null,null,null,null),
('1040','9','1900.00','1545091189097420014','2015-11-11 14:10:00','8073','383',null,null,null,null,null,null),
('1041','9','1900.00','74867953','2015-09-11 15:16:47','6553','384',null,null,null,null,null,null),
('1042','9','1900.00','1545091189097420014','2015-09-11 15:16:47','962','385',null,null,null,null,null,null),
('1043','9','1900.00','1545091189097420014','2015-11-11 14:10:00','1052','385',null,null,null,null,null,null),
('1044','9','1900.00','74867953','2015-09-11 15:16:47','1172','385',null,null,null,null,null,null);

delete from `rent_order`;
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`, `contract_payment_amount`, `contract_payment_type`, `doubt_record`, `virtual_account_unique_id`, `bill_life_cycle`, `collection_bill_life_cycle`, `collection_bill_capital_status`, `asset_package_id`, `collection_bill_service_notice_status`, `collection_bill_frozen_status`, `factoring_contract_id`, `group_id`)
VALUES
	(8265, '2015-11-11', '2015-11-11', NULL, 'CC-62649-2-repo', 2, 10980.36, '2015-11-11 14:10:00', 00000000, '2015-11-11', 10980.36, 1103, 1163, 0, NULL, NULL, 0, NULL, 'xiaoyu_CC-62649-2_7441', 10980.36, 1, NULL, NULL, 3, 3, 3, 680, 1, 0, 61, NULL);


delete from app;
insert into `app`(`id`,`app_id`,`app_secret`,`is_disabled`,`host`,`name`,`company_id`,`payee_account_name`,`payee_account_no`,`payee_bank_name`) values
('1','xiaoyu','70991db75ebb24fa0993f4fa25775022',b'0','http://beta.demo2do.com/jupiter/','寓见','5',null,null,null),
('2','youpark','123456',b'0','','优帕克','4',null,null,null),
('3','test4Zufangbao','2e85ae999845f25faf8ea7b514ee0aca',b'0','http://e.zufangbao.cn','租房宝测试账号','9',null,null,null),
('4','zhuke','cb742d55634a532060ac7387caa8d242',b'0','http://zkroom.com/','杭州驻客网络技术有限公司','6',null,null,null),
('5','laoA','744a38b1672b728dc35a68f7a10df082',b'0','http://www.13980.com','上海元轼信息咨询有限公司','7',null,null,null),
('6','keluoba','30f4d225438a7fd1541fe1a055420dfd',b'0','http://keluoba.com','柯罗芭','8',null,null,null),
('7','testForXiaoyu','2138ed4b66cebbded5753f3c59a064ae',b'0','http://xxx.com','小寓测试帐号','10',null,null,null),
('8','woju','495d49ae55fd794036576aa8f71dbb43',b'0','http://www.woju.com','杭州蜗居','11',null,null,null),
('9','meijia','1bf40057e15fd462773c13e0a85e9676',b'0','http://99196.hotel.cthy.com/','美家公寓','12',null,null,null);

delete from principal;
insert into `principal`(`id`,`authority`,`key_id`,`name`,`password`,`start_date`,`thru_date`) values
('9','ROLE_SUPER_USER',null,'zhangjianming','14ebdd77cd348da2ee411e118d125b53',null,null);

delete from company;
INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`, `bank_name`, `card_no`, `name`) VALUES 
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


SET FOREIGN_KEY_CHECKS = 1;
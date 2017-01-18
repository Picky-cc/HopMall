SET FOREIGN_KEY_CHECKS = 0;

delete from principal;
delete from company;

insert into `principal`(`id`,`authority`,`key_id`,`name`,`password`,`start_date`,`thru_date`) values
('1','ROLE_SUPER_USER',null,'root','a82a92061f9ad7a549a843658107141b',null,null),
('2','ROLE_INSTITUTION','2','dingcheng','df5b59f050d316b72b17f73714473f8b',null,null),
('3','ROLE_APP','1','xiaoyu','e10adc3949ba59abbe56e057f20f883e',null,null),
('4','ROLE_BANK_APP','2','yopark','086246bffb2de7288946151fc900db59',null,null),
('5','ROLE_INSTITUTION','2','DCF001','3cf5eaa7d33e0d023e811c90cd6f2f73',null,null),
('6','ROLE_INSTITUTION','2','DCF002','adb29c63523254a343864e97c84e6360',null,null),
('7','ROLE_APP','5','laoA','e10adc3949ba59abbe56e057f20f883e',null,null),
('8','ROLE_SUPER_USER',null,'guanzhishi','befd2450f81f88ecc5fbcc4c1f97f0b4',null,null),
('9','ROLE_SUPER_USER',null,'zhangjianming','14ebdd77cd348da2ee411e118d125b53',null,null),
('10','ROLE_SUPER_USER',null,'dongjigong','cb8d07590edc38e54bb40e3719acc189',null,null),
('11','ROLE_SUPER_USER',null,'lishuzhen','a82a92061f9ad7a549a843658107141b',null,null),
('12','ROLE_SUPER_USER',null,'chenjie','64b2f4c902086b2220afd5b05ad25fb9',null,null),
('13','ROLE_APP','3','test4Zufangbao','e10adc3949ba59abbe56e057f20f883e',null,null);


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
(14, NULL, '源涞国际', '源涞国际', NULL, NULL, NULL),
(15, NULL, '汉维仓储', '汉维仓储', NULL, NULL, NULL);


SET FOREIGN_KEY_CHECKS = 1;
SET FOREIGN_KEY_CHECKS = 0;

delete from principal;

insert into `principal`(`id`,`authority`,`key_id`,`name`,`password`) values
('1','ROLE_SUPER_USER',null,'root','a82a92061f9ad7a549a843658107141b'),
('2','ROLE_INSTITUTION','2','dingcheng','df5b59f050d316b72b17f73714473f8b'),
('3','ROLE_APP','1','xiaoyu','e10adc3949ba59abbe56e057f20f883e'),
('4','ROLE_BANK_APP','2','yopark','086246bffb2de7288946151fc900db59'),
('5','ROLE_INSTITUTION','2','DCF001','3cf5eaa7d33e0d023e811c90cd6f2f73'),
('6','ROLE_INSTITUTION','2','DCF002','adb29c63523254a343864e97c84e6360'),
('7','ROLE_APP','5','laoA','e10adc3949ba59abbe56e057f20f883e'),
('8','ROLE_SUPER_USER',null,'guanzhishi','befd2450f81f88ecc5fbcc4c1f97f0b4'),
('9','ROLE_SUPER_USER',null,'zhangjianming','14ebdd77cd348da2ee411e118d125b53'),
('10','ROLE_SUPER_USER',null,'dongjigong','cb8d07590edc38e54bb40e3719acc189'),
('11','ROLE_SUPER_USER',null,'lishuzhen','a82a92061f9ad7a549a843658107141b'),
('12','ROLE_SUPER_USER',null,'chenjie','64b2f4c902086b2220afd5b05ad25fb9'),
('13','ROLE_APP','3','test4Zufangbao','e10adc3949ba59abbe56e057f20f883e');

delete from company;
insert into `company`(`id`,`address`,`full_name`,`short_name`,`bank_name`,`card_no`,`name`) values
('2','上海陆家嘴','鼎程（上海）商业保理有限公司','鼎程',null,null,null),
('3','杭州万塘路8号','杭州随地付网络技术有限公司','租房宝',null,null,null),
('4','上海','上海优帕克投资管理有限公司','优帕克',null,null,null),
('5','上海','小寓科技','小寓',null,null,null),
('6','上海','杭州驻客网络技术有限公司','驻客',null,null,null),
('7','上海','上海元轼信息咨询有限公司','老A',null,null,null),
('8','上海','柯罗芭','柯罗芭',null,null,null),
('9','杭州','租房宝测试','租房宝测试',null,null,null),
('10',null,'小寓测试帐号','小寓测试帐号',null,null,null),
('11',null,'杭州蜗居','杭州蜗居',null,null,null),
('12',null,'美家公寓','美家公寓',null,null,null);

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

SET FOREIGN_KEY_CHECKS = 1;
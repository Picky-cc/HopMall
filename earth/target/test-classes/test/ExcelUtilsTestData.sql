SET FOREIGN_KEY_CHECKS=0;

delete from app;
delete from company;

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

SET FOREIGN_KEY_CHECKS=1;
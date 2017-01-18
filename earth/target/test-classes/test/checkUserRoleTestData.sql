SET FOREIGN_KEY_CHECKS=0;

delete from principal;
delete from app;
delete from company;
insert into `principal`(`id`,`authority`,`key_id`,`name`,`password`) values
('1','ROLE_SUPER_USER',null,'root','a82a92061f9ad7a549a843658107141b'),
('2','ROLE_INSTITUTION','2','dingcheng','df5b59f050d316b72b17f73714473f8b'),
('3','ROLE_APP','1','xiaoyu','e10adc3949ba59abbe56e057f20f883e'),
('4','ROLE_APP','2','yopark','086246bffb2de7288946151fc900db59'),
('5','ROLE_INSTITUTION','2','DCF001','3cf5eaa7d33e0d023e811c90cd6f2f73'),
('6','ROLE_INSTITUTION','2','DCF002','adb29c63523254a343864e97c84e6360'),
('7','ROLE_APP','5','laoA','e10adc3949ba59abbe56e057f20f883e');

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`)
VALUES
	(1,'xiaoyu','70991db75ebb24fa0993f4fa25775022',00000000,'http://beta.demo2do.com/jupiter/','寓见',5),
	(2,'youpark','123456',00000000,'','优帕克',4),
	(3,'test4Zufangbao','2e85ae999845f25faf8ea7b514ee0aca',00000000,'http://e.zufangbao.cn','租房宝测试账号',9),
	(4,'zhuke','cb742d55634a532060ac7387caa8d242',b'0','http://zkroom.com/','杭州驻客网络技术有限公司',6),
	(5,'laoA','cb742d55634a532060ac7387caa8d242',b'0','http://www.13980.com','上海元轼信息咨询有限公司',7);
	
INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`)
VALUES
	(2,'上海陆家嘴','鼎程（上海）商业保理有限公司','鼎程'),
	(3,'杭州万塘路8号','杭州随地付网络技术有限公司','租房宝'),
	(4,'上海','上海优帕克投资管理有限公司','优帕克'),
	(5,'上海','小寓科技','小寓'),
	(6,'上海','杭州驻客网络技术有限公司','驻客'),
	(7,'上海','上海元轼信息咨询有限公司','老A');

SET FOREIGN_KEY_CHECKS=0;
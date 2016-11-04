SET FOREIGN_KEY_CHECKS=0;

delete from rent_order;
delete from app;
delete from customer;
delete from contract;
delete from house;
delete from company;
delete from partical;
delete from partical_model;
delete from app_particles;

-- id为200的order没有其他合同信息的order; 236的order为测试paymentUrl的数据

insert into `rent_order`(`id`,`due_date`,`end_date`,`late_fee`,`order_no`,`order_status`,`paid_rent`,`payout_time`,`is_settled`,`start_date`,`total_rent`,`contract_id`,`customer_id`,`repayment_type`,`unique_bill_id`) values

('151','2015-05-01','2099-05-01',null,'test-wuju20150617-1','0',0.00,'2015-04-30 12:20:13',b'0','2001-01-01',1.00,'15','94','0',''),
('107','2015-04-01','2015-04-01',null,'KXHY1#1803-20150402-1','2',16500.00,'2015-04-03 16:45:24',b'0','2015-03-01',16500.00,'13','106','1','youpark_KXHY1#1803-20150402-1_107'),
('108','2015-05-01',null,null,'KXHY1#1803-20150402-2','2',0.00,'2015-05-13 15:17:34',b'0',null,16500.00,'13','106','0',''),
('109','2015-06-01',null,null,'KXHY1#1803-20150402-3','2',0.00,'2015-05-27 16:45:49',b'0',null,16500.00,'13','106','0',''),
('110','2015-07-01',null,null,'KXHY1#1803-20150402-4','2',0.00,null,b'0',null,16500.00,'13','106','0',''),
('111','2015-08-01',null,null,'KXHY1#1803-20150402-5','2',0.00,null,b'0',null,16500.00,'13','106','0',''),
('112','2099-09-01','2099-09-01',null,'KXHY1#1803-20150402-6','0',0.00,null,b'0','2001-01-01 16:45:49',16500.00,'13','106','0',''),
('113','2099-10-01','2099-10-01',null,'KXHY1#1803-20150402-7','0',null,null,b'0','2001-01-01 16:45:49',16500.00,'13','106','0',''),
('114','2099-11-01','2099-11-01',null,'KXHY1#1803-20150402-8','1',0.00,null,b'0','2001-01-01 16:45:49',16500.00,'13','106','0',''),
('115','2015-12-01',null,null,'KXHY1#1803-20150402-9','2',0.00,null,b'0',null,16500.00,'13','106','0',''),
('116','2016-01-01',null,null,'KXHY1#1803-20150402-10','2',0.00,null,b'0',null,16500.00,'13','106','0',''),
('117','2016-02-01',null,null,'KXHY1#1803-20150402-11','2',0.00,null,b'0',null,16500.00,'13','106','0',''),
('118','2016-03-01',null,null,'KXHY1#1803-20150402-12','0',0.00,null,b'0',null,16500.00,'13','106','0',''),
('119','2015-04-01',null,null,'HNHY3#602-20150402-6','2',12600.00,'2015-04-03 16:45:24',b'0',null,12600.00,'14','107','1',''),
('120','2015-05-01',null,null,'HNHY3#602-20150402-7','2',0.00,'2015-04-30 14:15:36',b'0',null,12600.00,'14','107','0',''),
('121','2015-06-01',null,null,'HNHY3#602-20150402-8','0',0.00,null,b'0',null,12600.00,'14','107','0',''),
('122','2015-07-01',null,null,'HNHY3#602-20150402-9','1',0.00,null,b'0',null,12600.00,'14','107','0',''),
('123','2015-08-01',null,null,'HNHY3#602-20150402-10','1',0.00,null,b'0',null,12600.00,'14','107','0',''),
('124','2015-09-01',null,null,'HNHY3#602-20150402-11','1',0.00,null,b'0',null,12600.00,'14','107','0',''),
('125','2015-10-01',null,null,'HNHY3#602-20150402-12','1',0.00,null,b'0',null,12600.00,'14','107','0',''),
('126','2015-04-01',null,null,'HQHY1#33B-20150402-11','2',17000.00,'2015-04-03 16:45:24',b'0',null,17000.00,'15','108','1',''),
('127','2015-05-01',null,null,'HQHY1#33B-20150402-12','0',0.00,'2015-04-30 12:20:13',b'0',null,17000.00,'15','108','0',''),
('128','2015-06-01',null,null,'HQHY1#33B-20150402-13','0',0.00,null,b'0',null,17000.00,'15','108','0',''),
('129','2015-07-01',null,null,'HQHY1#33B-20150402-14','0',0.00,null,b'0',null,17000.00,'15','108','0',''),
('130','2015-08-01',null,null,'HQHY1#33B-20150402-15','0',0.00,null,b'0',null,17000.00,'15','108','0',''),
('200','2015-08-01',null,null,'HQHY1#33B-20150402-200','0',0.00,null,b'0',null,17000.00,'200','200','0','');

insert into `rent_order`(`id`,`due_date`,`end_date`,`late_fee`,`order_no`,`order_status`,`paid_rent`,`payout_time`,`is_settled`,`start_date`,`total_rent`,`contract_id`,`customer_id`,`repayment_type`,`unique_bill_id`,`unique_particle_id`) values

('236','2015-04-01','2015-04-01',null,'21','0',0.00,'2015-04-03 16:45:24',b'0','2015-03-01',16500.00,'13','110','0','zufangbao_21_236','483b89b9ac532bb271a7ba407304fd22');

insert into `app`(`id`,`app_id`,`app_secret`,`is_disabled`,`host`,`name`, `company_id`) values
('1','xiaoyu','70991db75ebb24fa0993f4fa25775022',b'0','http://beta.demo2do.com/jupiter/','寓见',5),
('2','youpark','123456',b'0','','优帕克',4),
('3','test4Zufangbao','2e85ae999845f25faf8ea7b514ee0aca',b'0','http://e.zufangbao.cn','租房宝测试账号',9),
('4','zhuke','cb742d55634a532060ac7387caa8d242',b'0','http://zkroom.com/','杭州驻客网络技术有限公司',6),
('5','laoA','744a38b1672b728dc35a68f7a10df082',b'0','http://www.13980.com','上海元轼信息咨询有限公司',7),
('6','keluoba','30f4d225438a7fd1541fe1a055420dfd',b'0','http://keluoba.com','柯罗芭',7),
('7','testForwoju','40a8d6c31e4eaf1122177d67cd0e82e9',b'0','','优帕克',9),
('8','zufangbao','123456',b'0','','zufangbao',9);

insert into `customer`(`id`,`account`,`city`,`mobile`,`name`,`province`,`source`,`app_id`) values
('106',null,null,'13051364779','上海化耀国际贸易有限公司',null,'优帕克','2'),
('107',null,null,null,'吴林飞',null,'优帕克','2'),
('108',null,null,null,'R.B',null,'优帕克','2'),
('109',null,null,null,'OWENBOYDALEXANDER',null,'优帕克','2'),
('110',null,null,null,'QM',null,'优帕克','8'),
('111',null,null,null,'eileen',null,'优帕克','2'),
('118',null,null,null,'目黑克彦',null,'优帕克','2'),
('119',null,null,null,'冼的坤',null,'优帕克','2'),
('120',null,null,null,'Javier Diaz',null,'优帕克','2'),
('121',null,null,null,'Michael Charles Madely',null,'优帕克','2'),
('122',null,null,null,'Missart Reynoso Agustina',null,'优帕克','2'),
('124',null,null,null,'参环国际货运代理（上海）有限公司',null,'优帕克','2'),
('125',null,null,null,'李宇阳',null,'寓见','1'),
('126',null,null,null,'杨林奎',null,'寓见','1'),
('127',null,null,null,'陈超',null,'寓见','1'),
('128',null,null,null,'陈超',null,'寓见','1'),
('129',null,null,null,'李宇阳',null,'寓见','1'),
('130',null,null,null,'陈超',null,'寓见','1');

insert into `contract`(`id`,`begin_date`,`contract_no`,`contract_status`,`deposit`,`end_date`,`interrupt_date`,`interrupt_reason`,`month_fee`,`non_deposit_guarantee_way`,`payment_instrument`,`renewal`,`url`,`app_id`,`customer_id`,`house_id`) values
('13','2015-04-01','KXHY1#1803','0',33000.00,'2016-03-31',null,'',16500.00,b'0','0',null,null,'2','106','13'),
('14','2014-11-01','HNHY3#602','0',25200.00,'2015-10-31',null,'',12600.00,b'0','0',null,null,'2','107','14'),
('15','2014-06-01','HQHY1#33B','0',34000.00,'2015-11-30',null,'',17000.00,b'0','0',null,null,'2','108','15'),
('16','2014-05-01','JAGJ1804','0',27000.00,'2016-04-30',null,'',13500.00,b'0','0',null,null,'2','109','16'),
('17','2014-05-14','JAFJ4#26A','0',35600.00,'2015-11-13',null,'',17800.00,b'0','0',null,null,'2','110','17'),
('18','2014-05-08','JAHJ4#1203','0',33000.00,'2015-11-07',null,'',16500.00,b'0','0',null,null,'2','111','18'),
('57','2014-04-13','KXHY9#2601','0',38000.00,'2015-10-12',null,'',19500.00,b'0','0',null,null,'2','118','60'),
('58','2014-07-01','JAHJ3#2203','0',41600.00,'2015-12-30',null,'',20800.00,b'0','0',null,null,'2','119','61'),
('59','2014-03-12','SM3#15B','0',0.00,'2016-03-11',null,null,29000.00,b'0','0',null,null,'2','120','62'),
('60','2014-04-17','SHIMAO1#35D','0',0.00,'2015-10-16',null,null,28500.00,b'0','0',null,null,'2','121','63'),
('61','2015-03-23','SHIMAO1#45H','0',0.00,'2016-03-22',null,null,18900.00,b'0','0',null,null,'2','122','64'),
('63','2015-04-01','ZSGY16#1502','0',0.00,'2016-03-31',null,null,10400.00,b'0','0',null,null,'2','124','66'),
('64','2015-04-13','CC-60445','0',100.00,'2016-04-12',null,null,0.10,b'0','0',null,null,'1','125','67'),
('65','2015-05-13','CC-60446','0',100.00,'2016-05-12',null,null,0.10,b'0','0',null,null,'1','126','68'),
('66','2015-01-01','CC-60447','0',100.00,'2015-12-31',null,null,0.10,b'0','0',null,null,'1','127','69'),
('67','2015-04-13','CC-60448','0',100.00,'2016-04-12',null,null,0.10,b'0','0',null,null,'1','128','70'),
('68','2015-04-13','CC-60449','0',100.00,'2016-04-12',null,null,0.10,b'0','0',null,null,'1','129','71'),
('69','2015-04-13','CC-60450','0',100.00,'2016-04-12',null,null,0.10,b'0','0',null,null,'1','130','72');

insert into `house`(`id`,`address`,`area`,`bulid_area`,`city`,`community`,`community_detail`,`hall`,`house_status`,`house_type`,`province`,`room`,`app_id`) values
('13','汇川路88号1#1803',null,'0',null,'凯欣豪苑1#1803',null,'0','1','1',null,'0','2'),
('14','徐汇区淮海中路183弄3号602室',null,'0',null,'汇宁花园3#602',null,'0','1','1',null,'0','2'),
('15','紫云路118弄1号33B室',null,'0',null,'虹桥豪苑1#33B',null,'0','1','1',null,'0','2'),
('16','新闸路1528弄1号1804室',null,'0',null,'静安国际1804',null,'0','1','1',null,'0','2'),
('17','常德路500弄4号26A室',null,'0',null,'静安枫景4#26A',null,'0','1','1',null,'0','2'),
('18','西康路501号4号1203室',null,'0',null,'静安豪景4#1203',null,'0','1','1',null,'0','2'),
('60','汇川路88号9#2601室',null,'0',null,'凯欣豪苑9#2601',null,'0','1','1',null,'0','2'),
('61','西康路501弄3号2203室',null,'0',null,'静安豪景3#2203',null,'0','1','1',null,'0','2'),
('62','潍坊西路2弄1号1502室',null,'0',null,'世茂3#15B',null,'0','1','2',null,'0','2'),
('63','潍坊西路1弄7号3502室',null,'0',null,'世茂1#35D',null,'0','1','2',null,'0','2'),
('64','潍坊西路1弄5号4502室',null,'0',null,'世茂1#45H',null,'0','1','2',null,'0','2'),
('66','长宁区长宁路1277弄16号1502室',null,'0',null,'中山公寓16#1502',null,'0','1','2',null,'0','2'),
('67','SH-600041-C',null,'0',null,'SH-600041-C',null,'0','1','2',null,'0','2'),
('68','SH-600041-B',null,'0',null,'SH-600041-B',null,'0','1','2',null,'0','2'),
('69','SH-362885-304A',null,'0',null,'SH-362885-304A',null,'0','1','2',null,'0','2'),
('70','SH-362888-201A',null,'0',null,'SH-362888-201A',null,'0','1','2',null,'0','2'),
('71','SH-362892-1101A',null,'0',null,'SH-362892-1101A',null,'0','1','2',null,'0','2'),
('72','SH-362894-601A',null,'0',null,'SH-362894-601A',null,'0','1','2',null,'0','2');


INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`)
VALUES
	(2,'上海陆家嘴','鼎程（上海）商业保理有限公司','鼎程'),
	(3,'杭州万塘路8号','杭州随地付网络技术有限公司','租房宝'),
	(4,'上海','上海优帕克投资管理有限公司','优帕克'),
	(5,'上海','小寓科技','小寓'),
	(6,'上海','杭州驻客网络技术有限公司','驻客'),
	(7,'上海','上海元轼信息咨询有限公司','老A'),
	(8,'上海','柯罗芭','柯罗芭'),
	(9,'杭州','租房宝测试','租房宝测试');

INSERT INTO `partical` (`id`, `entry_url`, `partical_name`, `partical_status`, `partical_type`, `unique_partical_id`)
VALUES
	(1, 'http://127.0.0.1:17523/testquark', 'test_quark', 0, 0, '963eb1401db6fc3cfce2a7cd5e9f916d'),
	(2, 'http://test2.url', 'test2_quark', 1, 1, 'e13855b7f2037a9fed12432720d156b5'),
	(3, 'http://e.zufangbao.cn', 'test_quark', 0, 0, '483b89b9ac532bb271a7ba407304fd22');


INSERT INTO `partical_model` (`id`, `value`, `name`, `unique_partical_id`)
VALUES
	(1, '/revoke', 'revokeBills', '963eb1401db6fc3cfce2a7cd5e9f916d'),
	(2, '/app-config/sync', 'syncAppConfig', '963eb1401db6fc3cfce2a7cd5e9f916d'),
	(3, '/assgin', 'assignBills', '963eb1401db6fc3cfce2a7cd5e9f916d'),
	(4, 'testvalue21', 'testname1', 'e13855b7f2037a9fed12432720d156b5'),
	(5, 'testvalue22', 'testname2', 'e13855b7f2037a9fed12432720d156b5'),
	(6, 'testvalue23', 'testname3', 'e13855b7f2037a9fed12432720d156b5'),
	(7, '/cooling-bills', 'coolingHotBills', '963eb1401db6fc3cfce2a7cd5e9f916d'),
	(8, '/hot-snapshot/all', 'snapShotHotBills', '963eb1401db6fc3cfce2a7cd5e9f916d'),
	(9, '/outstanding-bills', 'getOutstandingBills', '483b89b9ac532bb271a7ba407304fd22'),
	(10, '/today-paid-bills', 'getTodayPaidBills', '483b89b9ac532bb271a7ba407304fd22'),
	(11, '/transactions/from-bills', 'getTrasactionRecords', '483b89b9ac532bb271a7ba407304fd22'),
	(12, '/transactions/from-bills', 'getTrasactionRecords', '963eb1401db6fc3cfce2a7cd5e9f916d'),
	(13, '/transactions/confirm', 'confirmTrasactionRecords', '483b89b9ac532bb271a7ba407304fd22'),
	(14, '/not-reach-payment-date-bills', 'getNotReachPaymentDateBills', '483b89b9ac532bb271a7ba407304fd22'),
	(15, '/over-payment-date-bills', 'getOverPaymentDateBills', '483b89b9ac532bb271a7ba407304fd22'),
	(16, '/today-unpaid-oustanding-bills', 'getTodayUnpaidOutstandingBills', '483b89b9ac532bb271a7ba407304fd22'),
	(17, '/bill-deposite/from-billSketch', 'getAuthorisedBillSketches', '483b89b9ac532bb271a7ba407304fd22');

INSERT INTO `app_particles` (`app_id`,`is_delete`,`modified_time`,`unique_partical_id`) 
VALUES ('zufangbao',b'0','2015-04-27 15:00:58','483b89b9ac532bb271a7ba407304fd22'),
('yopark',b'0','2015-04-27 15:00:58','963eb1401db6fc3cfce2a7cd5e9f916d'),
('test4QuarkNoParticle',b'0','2015-04-27 15:00:58','551eb1401db6fc3cfce2a7cd5e9f116d');
	
SET FOREIGN_KEY_CHECKS=1;
SET FOREIGN_KEY_CHECKS = 0;

delete from app;
delete from transaction_record;
delete from rent_order;
delete from transaction_record_log;
delete from contract;
delete from customer;
delete from payment_institution;

insert into `app`(`id`,`app_id`,`app_secret`,`is_disabled`,`host`,`name`,`company_id`) values
('1','xiaoyu','70991db75ebb24fa0993f4fa25775022',b'0','http://beta.demo2do.com/jupiter/','寓见','5'),
('2','youpark','123456',b'0','','优帕克','4');

insert into `payment_institution`(`id`,`alias`,`code`,`day_upper_limit`,`name`,`once_upper_limit`) values
('1','umpay','umpay',null,'联动优势',null),
('2','alipay','alipay',null,'支付宝',null),
('3','directbank.icbc','directbank.icbc',null,'工行银企互联',null);

insert into `transaction_record`(`id`,`card_no`,`channel`,`last_modified_time`,`merchant_payment_no`,`order_no`,`pay_money`,`request_no`,`trade_no`,`transaction_record_status`,`app_id`,`payment_institution_id`) values
('11',null,'2','2015-04-03 12:01:00',null,'KXHY1#1803-20150402-1',16500.00,null,'0','2','1','3'),
('12',null,'2','2015-04-03 12:02:00',null,'JAGJ1804-20150402-12',13500.00,null,'0','2','1','3'),
('24',null,'2','2015-04-03 12:03:00',null,'HNHY3#602-20150402-6',12600.00,null,'0','2','1','3'),
('25',null,'2','2015-04-03 12:04:00',null,'HQHY1#33B-20150402-11',17000.00,null,'0','2','1','3'),
('23',null,'2','2015-04-03 12:05:00',null,'JAHJ3#2203-20150402-10',20800.00,null,'0','2','1','3'),
('26',null,'2','2015-04-01 12:06:00',null,'JAHJ3#2203-20150402-10',20800.00,null,'0','2','1','3');

insert into `rent_order`(`id`,`due_date`,`end_date`,`late_fee`,`order_no`,`order_status`,`paid_rent`,`payout_time`,`is_settled`,`start_date`,`total_rent`,`contract_id`,`customer_id`,`repayment_type`,`user_upload_param`,`unique_particle_id`,`transfer_status`,`modify_time`,`unique_bill_id`) values
('107','2015-04-01',null,null,'KXHY1#1803-20150402-1','3',16500.00,'2015-04-03 16:45:24',b'0',null,16500.00,'13','106','1',null,null,'0',null,null),
('108','2015-05-01',null,null,'JAGJ1804-20150402-12','3',16500.00,'2015-05-13 15:17:34',b'0',null,16500.00,'13','106','0',null,null,'0',null,null),
('109','2015-06-01',null,null,'HNHY3#602-20150402-6','3',16500.00,'2015-05-27 16:45:49',b'0',null,16500.00,'13','106','0',null,null,'0',null,null),
('110','2015-07-01',null,null,'HQHY1#33B-20150402-11','3',16500.00,'2015-06-23 17:04:30',b'0',null,16500.00,'13','106','0',null,null,'0',null,null),
('111','2015-08-01',null,null,'JAHJ3#2203-20150402-10','0',0.00,null,b'0',null,16500.00,'13','106','0',null,null,'0',null,null),
('112','2015-09-01',null,null,'KXHY1#1803-20150402-6','0',0.00,null,b'0',null,16500.00,'13','106','0',null,null,'0',null,null),
('113','2015-10-01',null,null,'KXHY1#1803-20150402-7','0',0.00,null,b'0',null,16500.00,'13','106','0',null,null,'0',null,null),
('114','2015-11-01',null,null,'KXHY1#1803-20150402-8','0',0.00,null,b'0',null,16500.00,'13','106','0',null,null,'0',null,null),
('115','2015-12-01',null,null,'KXHY1#1803-20150402-9','0',0.00,null,b'0',null,16500.00,'13','106','0',null,null,'0',null,null),
('116','2016-01-01',null,null,'KXHY1#1803-20150402-10','0',0.00,null,b'0',null,16500.00,'13','106','0',null,null,'0',null,null),
('117','2016-02-01',null,null,'KXHY1#1803-20150402-11','0',0.00,null,b'0',null,16500.00,'13','106','0',null,null,'0',null,null);

insert into `transaction_record_log`(`id`,`modified_time`,`operator_id`,`transaction_record_operate_status`,`transaction_record_id`) values
('5','2015-04-03 19:27:02','130','0','12'),
('6','2015-04-01 20:16:04','126','1','11'),
('7','2015-04-13 20:17:11','6','1','23'),
('8','2015-04-13 20:19:06','126','0','24'),
('9','2015-04-13 20:19:45','8','1','24'),
('10','2015-04-13 20:24:07','126','0','25'),
('11','2015-04-13 20:24:44','10','1','25'),
('12','2015-04-13 20:29:12','126','0','26'),
('13','2015-04-13 20:29:31','12','1','26'),
('14','2015-04-13 20:32:29','126','0','27');

insert into `contract`(`id`,`begin_date`,`contract_no`,`contract_status`,`deposit`,`end_date`,`interrupt_date`,`interrupt_reason`,`month_fee`,`non_deposit_guarantee_way`,`payment_instrument`,`renewal`,`url`,`app_id`,`customer_id`,`house_id`,`transfer_status`) values
('13','2015-04-01','KXHY1#1803','0',33000.00,'2016-03-31',null,'',16500.00,b'0','0',null,null,'2','106','13','0'),
('14','2014-11-01','HNHY3#602','0',25200.00,'2015-10-31',null,'',12600.00,b'0','0',null,null,'2','107','14','0'),
('15','2014-06-01','HQHY1#33B','0',34000.00,'2015-11-30',null,'',17000.00,b'0','0',null,null,'2','108','15','0'),
('16','2014-05-01','JAGJ1804','0',27000.00,'2016-04-30',null,'',13500.00,b'0','0',null,null,'2','109','16','0'),
('17','2014-05-14','JAFJ4#26A','0',35600.00,'2015-11-13',null,'',17800.00,b'0','0',null,null,'2','110','17','0'),
('18','2014-05-08','JAHJ4#1203','0',33000.00,'2015-11-07',null,'',16500.00,b'0','0',null,null,'2','111','18','0'),
('57','2014-04-13','KXHY9#2601','0',38000.00,'2015-10-12',null,'',19500.00,b'0','0',null,null,'2','118','60','0'),
('58','2014-07-01','JAHJ3#2203','0',41600.00,'2015-12-30',null,'',20800.00,b'0','0',null,null,'2','119','61','0');

insert into `customer`(`id`,`account`,`city`,`mobile`,`name`,`province`,`source`,`app_id`) values
('-1',null,null,null,'小寓预付款承租方',null,'寓见','1'),
('106',null,null,null,'上海化耀国际贸易有限公司',null,'优帕克','2'),
('107',null,null,null,'吴林飞',null,'优帕克','2'),
('108',null,null,null,'R.B',null,'优帕克','2'),
('109',null,null,null,'OWENBOYDALEXANDER',null,'优帕克','2'),
('110',null,null,null,'QM',null,'优帕克','2'),
('111',null,null,null,'eileen',null,'优帕克','2'),
('118',null,null,null,'目黑克彦',null,'优帕克','2'),
('119',null,null,null,'冼的坤',null,'优帕克','2'),
('120',null,null,null,'Javier Diaz',null,'优帕克','2'),
('121',null,null,null,'Michael Charles Madely',null,'优帕克','2'),
('122',null,null,null,'Missart Reynoso Agustina',null,'优帕克','2'),
('124',null,null,null,'参环国际货运代理（上海）有限公司',null,'优帕克','2');

SET FOREIGN_KEY_CHECKS = 1;
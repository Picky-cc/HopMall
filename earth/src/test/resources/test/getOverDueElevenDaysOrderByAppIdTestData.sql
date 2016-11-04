SET FOREIGN_KEY_CHECKS = 0;

delete from app;
delete from contract;
delete from rent_order;
delete from app_arrive_record;
delete from receive_order_map;
delete from asset_package;



insert into `rent_order`(`id`,`due_date`,`end_date`,`late_fee`,`order_no`,`order_status`,`paid_rent`,`payout_time`,`is_settled`,`start_date`,`total_rent`,`contract_id`,`customer_id`,`repayment_type`,`user_upload_param`,`unique_particle_id`,`transfer_status`,`modify_time`,`unique_bill_id`) values
('107','2015-07-04',null,null,'KXHY1#1803-20150402-1','0',16500.00,'2015-04-03 16:45:24',b'0',null,16500.00,'13','106','1',null,null,'0',null,null),
('108','2015-07-04',null,null,'KXHY1#1803-20150402-2','0',16500.00,'2015-05-13 15:17:34',b'0',null,16500.00,'13','106','0',null,null,'0',null,null),
('109','2015-07-04',null,null,'ABCD#1803-20150402-3','0',16500.00,'2015-05-27 16:45:49',b'0',null,16500.00,'14','106','0',null,null,'0',null,null),
('110','2015-07-04',null,null,'KXHY1#1803-20150402-4','0',16500.00,'2015-06-23 17:04:30',b'0',null,16500.00,'13','106','0',null,null,'0',null,null);

insert into `app`(`id`,`app_id`,`app_secret`,`is_disabled`,`host`,`name`,`company_id`) values
('1','xiaoyu','70991db75ebb24fa0993f4fa25775022',b'0','http://beta.demo2do.com/jupiter/','寓见','5'),
('2','youpark','123456',b'0','','优帕克','4');

insert into `contract`(`id`,`begin_date`,`contract_no`,`contract_status`,`deposit`,`end_date`,`interrupt_date`,`interrupt_reason`,`month_fee`,`non_deposit_guarantee_way`,`payment_instrument`,`renewal`,`url`,`app_id`,`customer_id`,`house_id`,`transfer_status`) values
('13','2015-04-01','KXHY1#1803','0',33000.00,'2016-03-31',null,'',16500.00,b'0','0',null,null,'2','106','13','0'),
('14','2014-11-01','ABCD#1803','0',25200.00,'2015-10-31',null,'',12600.00,b'0','0',null,null,'2','107','14','0'),
('15','2014-06-01','HQHY1#33B','0',34000.00,'2015-11-30',null,'',17000.00,b'0','0',null,null,'2','108','15','0'),
('16','2014-05-01','JAGJ1804','0',27000.00,'2016-04-30',null,'',13500.00,b'0','0',null,null,'2','109','16','0'),
('17','2014-05-14','JAFJ4#26A','0',35600.00,'2015-11-13',null,'',17800.00,b'0','0',null,null,'2','110','17','0');

INSERT INTO `asset_package` (`id`, `adva_start_date`, `is_available`, `bank_account`, `bank_name`, `create_time`, `latest_settle_date`, `otsd_amt`, `otsd_amt_aval`, `rec_transfee_total_amt`, `remove_time`, `contract_id`, `factoring_contract_id`, `thru_date`)
VALUES
	(13, '2015-04-03 00:00:00', 00000001, NULL, NULL, '2015-04-02 00:00:00', '2015-04-03 00:00:00', 138600.00, 138600.00, 198000.00, NULL, 13, 58, NULL),
	(14, '2015-04-03 00:00:00', 00000001, NULL, NULL, '2015-04-02 00:00:00', '2015-04-03 00:00:00', 61740.00, 61740.00, 88200.00, NULL, 14, 58, '2015-11-24 20:25:04');


insert into `app_arrive_record`(`id`,`amount`,`arrive_record_status`,`pay_ac_no`,`receive_ac_no`,`remark`,`serial_no`,`time`,`app_id`,`drcrf`,`pay_name`,`summary`,`vouh_no`) values
('1',52500.00,'2','6222081001006853570','1001300219000027827','永业公寓28号604','2015-03-30-22.39.56.930280','2015-03-30 22:39:56','2','2','徐月','网转','0'),
('4',58000.00,'2','00000440367604092','1001300219000027827','','2015-03-30-17.57.10.628144','2015-01-01 00:00:00','2','2','上海博雅方略资产管理有限公司','房屋租赁费','0'),
('5',37510.00,'2','10012906012157280','1001300219000027827','','2015-03-30-16.36.16.033357','2015-03-30 16:36:16','2','2','陆雪芸','汇款','0'),
('6',49500.00,'2','00000452059224352','1001300219000027827','','2015-03-30-16.11.50.920468','2015-03-30 16:11:50','2','2','上海化耀国际贸易有限公司','汇川路88号1号楼1803?','0'),
('7',43500.00,'2','6228480038019787175','1001300219000027827','','2015-03-30-16.06.17.968790','2015-03-30 16:06:17','2','2','林娟','租金','0');

insert into `receive_order_map`(`id`,`amount`,`association_type`,`create_time`,`is_deducted`,`pay_time`,`app_arrive_record_id`,`order_id`,`receive_order_map_status`) values
('1',16500.00,'1','2015-04-03 12:00:00',b'1','2015-04-03 12:00:00','4','107','0'),
('2',16500.00,'1','2015-04-03 12:00:00',b'1','2015-04-03 12:00:00','5','108','0'),
('3',13500.00,'1','2015-04-03 12:00:00',b'1','2015-04-03 12:00:00','6','109','0'),
('4',12600.00,'1','2015-04-03 12:00:00',b'1','2015-04-03 12:00:00','7','110','0'),
('5',17000.00,'1','2015-04-03 12:00:00',b'1','2015-04-03 12:00:00','15','126','0'),
('6',20800.00,'1','2015-04-03 12:00:00',b'1','2015-04-03 12:00:00','25','196','0'),
('7',17800.00,'1','2015-04-03 12:00:00',b'1','2015-04-03 12:00:00','17','146','0'),
('8',19500.00,'1','2015-04-03 12:00:00',b'1','2015-04-03 12:00:00','24','190','0');

SET FOREIGN_KEY_CHECKS = 1;
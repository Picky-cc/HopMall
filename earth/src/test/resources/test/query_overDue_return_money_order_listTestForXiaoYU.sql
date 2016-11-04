SET FOREIGN_KEY_CHECKS = 0;

delete from contract;
delete from app;
delete from rent_order;
delete from asset_package;

insert into `app`(`id`,`app_id`,`app_secret`,`is_disabled`,`host`,`name`,`company_id`) values
('1','xiaoyu','70991db75ebb24fa0993f4fa25775022',b'0','http://beta.demo2do.com/jupiter/','寓见','5'),
('2','youpark','123456',b'0','','优帕克','4');

insert into `asset_package`(`id`,`adva_start_date`,`is_available`,`bank_account`,`bank_name`,`create_time`,`latest_settle_date`,`otsd_amt`,`otsd_amt_aval`,`rec_transfee_total_amt`,`remove_time`,`contract_id`,`factoring_contract_id`) values
('13','2015-04-03 00:00:00',b'1',null,null,'2015-04-02 00:00:00','2015-04-03 00:00:00',138600.00,138600.00,198000.00,null,'13','58'),
('14','2015-04-03 00:00:00',b'1',null,null,'2015-04-02 00:00:00','2015-04-03 00:00:00',61740.00,61740.00,88200.00,null,'14','58'),
('15','2015-04-03 00:00:00',b'1',null,null,'2015-04-02 00:00:00','2015-04-03 00:00:00',95200.00,95200.00,136000.00,null,'15','58'),
('16','2015-04-03 00:00:00',b'1',null,null,'2015-04-02 00:00:00','2015-04-03 00:00:00',113400.00,113400.00,162000.00,null,'16','58'),
('17','2015-04-03 00:00:00',b'1',null,null,'2015-04-02 00:00:00','2015-04-03 00:00:00',87220.00,87220.00,124600.00,null,'17','58');

insert into `contract`(`id`,`begin_date`,`contract_no`,`contract_status`,`deposit`,`end_date`,`interrupt_date`,`interrupt_reason`,`month_fee`,`non_deposit_guarantee_way`,`payment_instrument`,`renewal`,`url`,`app_id`,`customer_id`,`house_id`,`transfer_status`) values
('13','2015-04-01','KXHY1#1803','0',33000.00,'2016-03-31',null,'',16500.00,b'0','0',null,null,'1','106','13','0'),
('14','2014-11-01','HNHY3#602','0',25200.00,'2015-10-31',null,'',12600.00,b'0','0',null,null,'1','107','14','0'),
('15','2014-06-01','HQHY1#33B','0',34000.00,'2015-11-30',null,'',17000.00,b'0','0',null,null,'1','108','15','0'),
('16','2014-05-01','JAGJ1804','0',27000.00,'2016-04-30',null,'',13500.00,b'0','0',null,null,'1','109','16','0'),
('17','2014-05-14','JAFJ4#26A','0',35600.00,'2015-11-13',null,'',17800.00,b'0','0',null,null,'1','110','17','0');

insert into `rent_order`(`id`,`due_date`,`end_date`,`late_fee`,`order_no`,`order_status`,`paid_rent`,`payout_time`,`is_settled`,`start_date`,`total_rent`,`contract_id`,`customer_id`,`repayment_type`,`user_upload_param`,`unique_particle_id`,`transfer_status`,`modify_time`,`unique_bill_id`,`asset_package_id`) values
('107','2015-04-01',null,null,'KXHY1#1803-20150402-1','4',16500.00,'2015-04-03 16:45:24',b'0',null,16500.00,'13','106','1',null,null,'0',null,null,13),
('108','2015-05-01',null,null,'KXHY1#1803-20150402-2','4',16500.00,'2015-05-13 15:17:34',b'0',null,16500.00,'13','106','0',null,null,'0',null,null,13),
('109','2015-06-01',null,null,'KXHY1#1803-20150402-3','4',16500.00,'2015-05-27 16:45:49',b'0',null,16500.00,'13','106','0',null,null,'0',null,null,13),
('110','2015-07-01',null,null,'KXHY1#1803-20150402-4','4',16500.00,'2015-06-23 17:04:30',b'0',null,16500.00,'13','106','0',null,null,'0',null,null,13),
('111','2015-02-01',null,null,'KXHY1#1803-20150402-5','0',0.00,null,b'0',null,16500.00,'13','106','0',null,null,'0',null,null,13),
('112','2015-03-01',null,null,'KXHY1#1803-20150402-6','0',0.00,null,b'0',null,16500.00,'13','106','0',null,null,'0',null,null,13),
('113','2015-01-01',null,null,'KXHY1#1803-20150402-7','1',0.00,null,b'0',null,16500.00,'13','106','0',null,null,'0',null,null,13),
('119','2015-04-01',null,null,'HNHY3#602-20150402-6','4',12600.00,'2015-04-03 16:45:24',b'0',null,12600.00,'14','107','1',null,null,'0',null,null,14),
('120','2015-05-01',null,null,'HNHY3#602-20150402-7','4',12600.00,'2015-04-30 14:15:36',b'0',null,12600.00,'14','107','0',null,null,'0',null,null,14),
('121','2015-06-01',null,null,'HNHY3#602-20150402-8','4',12600.00,'2015-06-02 16:41:56',b'0',null,12600.00,'14','107','0',null,null,'0',null,null,14),
('122','2015-07-01',null,null,'HNHY3#602-20150402-9','4',12600.00,'2015-06-30 17:41:20',b'0',null,12600.00,'14','107','0',null,null,'0',null,null,14),
('123','2015-06-01',null,null,'HNHY3#602-20150402-10','0',0.00,null,b'0',null,12600.00,'14','107','0',null,null,'0',null,null,14);

SET FOREIGN_KEY_CHECKS = 1;
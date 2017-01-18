SET FOREIGN_KEY_CHECKS=0;

delete from rent_order;
delete from customer;
delete from app;

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`)
VALUES
	(1,'xiaoyu','70991db75ebb24fa0993f4fa25775022',00000000,'http://beta.demo2do.com/jupiter/','寓见',5),
	(2,'youpark','123456',00000000,'','优帕克',4);


insert into `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`) values('106',NULL,NULL,NULL,'上海化耀国际贸易有限公司',NULL,'优帕克','2');

INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`) VALUES ('107', '2015-03-06', '2015-04-01', null, 'KXHY1#1803-20150402-4', '0', '16500.00','2015-05-13 15:17:34', '\0', '2015-04-03 16:45:24', '16500.00', '13', '106', '0');
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`) VALUES ('119', '2015-03-11', '2015-11-01', null, 'KXHY1#1803-20150402-5', '0', '16500.00', '2015-05-13 15:17:34', '\0', '2015-04-03 16:45:24', '16500.00', '13', '106', '0');
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`) VALUES ('126', '2015-03-01', '2015-11-01', null, 'KXHY1#1803-20150402-6', '0', '16500.00', '2015-05-13 15:17:34', '\0', '2015-04-03 16:45:24', '16500.00', '13', '106', '0');
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`) VALUES ('134', '2015-03-01', '2015-11-01', null, 'KXHY1#1803-20150402-7', '0', '16500.00', '2015-05-13 15:17:34', '\0', '2015-04-03 16:45:24', '16500.00', '13', '106', '0');
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`) VALUES ('146', '2015-03-01', '2015-11-01', null, 'KXHY1#1803-20150402-8', '0', '16500.00','2015-05-13 15:17:34', '\0', '2015-04-03 16:45:24', '16500.00', '13', '106', '0');

SET FOREIGN_KEY_CHECKS=1;
SET FOREIGN_KEY_CHECKS=0;
delete from app;
delete from principal;
delete from payment_institution;
delete from app_account;
delete from app_payment_config;
delete from app_service_config;
delete from payment_institution_param;
delete from landlord;
delete from house;
delete from customer;
delete from contract;
delete from rent_order;
delete from company;
delete from factoring_contract;
delete from asset_package;
delete from transaction_record;
delete from finance_payment_record;
delete from settle_record;
delete from settle_item;
delete from charge;
delete from order_payment;
delete from receive_order_map;
delete from contract;
delete from app_account;
delete from transaction_record;
delete from app_arrive_record;
delete from payment_agreement;
delete from account;

								
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`,`virtual_account_unique_id`) values (493, '2015-9-7', '2016-10-6', NULL, 'YCHT7#1201-20150508-14', 0,'1600', NULL,  '\0', '2015-9-7', 14500.00, 96, 177, 0,'953fb78f2d6f6ea9cb10a82d399e4464');
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`,`virtual_account_unique_id`,`unique_bill_id`) values (495, '2015-9-7', '2016-10-6', NULL, 'YCHT7#1201-20150508-15', 1,'14500.00', NULL,  '\0', '2015-9-7', 14500.00, 96, 177, 0,'953fb78f2d6f6ea9cb10a82d399e4464','495');
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`,`virtual_account_unique_id`) values (496, '2015-9-7', '2016-10-6', NULL, 'YCHT7#1201-20150508-16', 2,'14500.00', NULL,  '\0', '2015-9-7', 14500.00, 96, 177, 0,'953fb78f2d6f6ea9cb10a82d399e4464');
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`,`virtual_account_unique_id`) values (497, '2015-9-7', '2016-10-6', NULL, 'YCHT7#1201-20150508-17', 3,'1600', NULL,  '\0', '2015-9-7', 14500.00, 96, 177, 0,'953fb78f2d6f6ea9cb10a82d399e4464');

INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`,`virtual_account_unique_id`) values (498, '2015-9-7', '2016-10-6', NULL, 'YCHT7#1201-20150508-18', 2,'14501.00', NULL,  '\0', '2015-9-7', 14500.00, 96, 177, 0,'953fb78f2d6f6ea9cb10a82d399e4464');
-- 未关闭的租约下的订单
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`,`virtual_account_unique_id`,`bill_life_cycle`,`collection_bill_life_cycle`,`unique_bill_id`) values (499, '2015-9-7', '2016-10-6', NULL, 'YCHT7#1201-20150508-19', 2,'14490.00', NULL,  '\0', '2015-9-7', 14500.00, 97, 177, 0,'953fb78f2d6f6ea9cb10a82d399e4464',3,3,'499');
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`,`virtual_account_unique_id`,`bill_life_cycle`,`collection_bill_life_cycle`,`unique_bill_id`) values (500, '2015-9-7', '2016-10-6', NULL, 'YCHT7#1201-20150508-20', 3,'14490.00', NULL,  '\0', '2015-9-7', 14500.00, 97, 177, 0,'953fb78f2d6f6ea9cb10a82d399e4464',2,3,'500');
-- 关闭的租约下的订单
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`,`virtual_account_unique_id`,`bill_life_cycle`,`unique_bill_id`) values (501, '2015-9-7', '2016-10-6', NULL, 'YCHT7#1201-20150508-21', 2,'14490.00', NULL,  '\0', '2015-9-7', 14500.00, 98, 177, 0,'953fb78f2d6f6ea9cb10a82d399e4464',3,'501');
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`,`virtual_account_unique_id`,`bill_life_cycle`,`unique_bill_id`) values (502, '2015-9-7', '2016-10-6', NULL, 'YCHT7#1201-20150508-22', 0,'14490.00', NULL,  '\0', '2015-9-7', 14500.00, 98, 177, 0,'953fb78f2d6f6ea9cb10a82d399e4464',3,'502');
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`,`virtual_account_unique_id`,`bill_life_cycle`,`collection_bill_life_cycle`) values (503, '2015-9-7', '2016-10-6', NULL, 'YCHT7#1201-20150508-2333', 3,'14490.00', NULL,  '\0', '2015-9-7', 14500.00, 99, 177, 0,'953fb78f2d6f6ea9cb10a82d399e4464',3,3);

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`,`contract_life_cycle`) values ('96', '2014-11-01', 'YCHT7#1201-20150508-16', '0', '25200.00', '2015-10-31', null, '', '12600.00', '\0', '0', null, null, '4', '113', '20',2);

-- 未关闭的租约
INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`) values ('99', '2014-11-01', 'YCHT7#1201-20150508-17', '0', '25200.00', '2015-10-31', null, '', '12600.00', '\0', '0', null, null, '4', '113', '20');
-- 关闭的租约
INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`,`contract_life_cycle`) values ('98', '2014-11-01', 'YCHT7#1201-20150508-18', '0', '25200.00', '2015-10-31', null, '', '12600.00', '\0', '0', null, null, '4', '113', '20',6);

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`)
VALUES
	(1,'xiaoyu','70991db75ebb24fa0993f4fa25775022',00000000,'http://beta.demo2do.com/jupiter/','寓见',5),
	(2,'youpark','123456',00000000,'','优帕克',4),
	(3,'test4Zufangbao','2e85ae999845f25faf8ea7b514ee0aca',00000000,'http://e.zufangbao.cn','租房宝测试账号',9),
	(4,'zhuke','cb742d55634a532060ac7387caa8d242',b'0','http://zkroom.com/','杭州驻客网络技术有限公司',6);

INSERT INTO `principal` (`id`, `authority`, `name`, `password`) VALUES ('1', 'ROLE_SUPER_USER', 'root', 'befd2450f81f88ecc5fbcc4c1f97f0b4'),
('2', 'ROLE_INSTITUTION', '2', 'dingcheng', 'df5b59f050d316b72b17f73714473f8b'),
('3', 'ROLE_APP', '1', 'xiaoyu', 'e10adc3949ba59abbe56e057f20f883e'),
('4', 'ROLE_LEASING_ASSET_MANAGER', '2', 'youpark', '086246bffb2de7288946151fc900db59'),
('5', 'ROLE_INSTITUTION', '2', 'DCF001', '3cf5eaa7d33e0d023e811c90cd6f2f73'),
('6', 'ROLE_INSTITUTION', '2', 'DCF002', 'adb29c63523254a343864e97c84e6360'),
 ('7', 'ROLE_APP', '5', 'laoA', 'e10adc3949ba59abbe56e057f20f883e'),
('8', 'ROLE_SUPER_USER', NULL, 'guanzhishi', 'befd2450f81f88ecc5fbcc4c1f97f0b4'),
('9', 'ROLE_SUPER_USER', NULL, 'zhangjianming', '14ebdd77cd348da2ee411e118d125b53'),
('10', 'ROLE_SUPER_USER', NULL, 'dongjigong', 'cb8d07590edc38e54bb40e3719acc189'),
 ('11', 'ROLE_SUPER_USER', NULL, 'lishuzhen', 'a82a92061f9ad7a549a843658107141b'),
 ('12', 'ROLE_SUPER_USER', NULL, 'chenjie', '64b2f4c902086b2220afd5b05ad25fb9'),
('13', 'ROLE_APP', '3', 'test4Zufangbao', 'e10adc3949ba59abbe56e057f20f883e'),
 ('14', 'ROLE_SUPER_USER', 'zhushiyun', '2ba9a0c7b7bf6b07846c7468c32552d1'),
 ('15', 'ROLE_SUPER_USER', NULL, 'lixu', 'a82a92061f9ad7a549a843658107141b'),
 ('16', 'ROLE_SUPER_USER', NULL, 'jinyin', '9c74066927e18620a8bc89580f23e8ed'),
 ('17', 'ROLE_APP', '8', 'woju', 'e10adc3949ba59abbe56e057f20f883e'),
 ('18', 'ROLE_SUPER_USER', NULL, 'wukai', 'c4de644efae81ff323fa45b50c31296b'),
('19', 'ROLE_APP', '9', 'meijia', 'e10adc3949ba59abbe56e057f20f883e'),
('20', 'ROLE_BANK_APP', '2', 'YoparkTest', 'befd2450f81f88ecc5fbcc4c1f97f0b4'),
('21', 'ROLE_MERCURY_APP', '1', 'XY001', 'e10adc3949ba59abbe56e057f20f883e'),
('22', 'ROLE_SUPER_USER', NULL, 'meikehuan', 'e10adc3949ba59abbe56e057f20f883e')
,('23', 'ROLE_LEASING_ASSET_MANAGER', '2', 'fishmei', 'e10adc3949ba59abbe56e057f20f883e');
	
INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`) values ('152', null, null, null, '上海化耀国际贸易有限公司', null, '优帕克', '3');
INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`) values ('107', null, null, null, '吴林飞', null, '优帕克', '2');
INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`) values ('108', null, null, null, 'R.B', null, '优帕克', '2');
INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`) values ('109', null, null, null, 'OWENBOYDALEXANDER', null, '优帕克', '2');
INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`) values ('137', null, null, null, '伊朗马汉航空公司上海代表处', null, '优帕克', '2');

SET FOREIGN_KEY_CHECKS=1;
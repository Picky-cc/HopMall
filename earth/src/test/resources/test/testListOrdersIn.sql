SET FOREIGN_KEY_CHECKS = 0;

delete from app;
delete from principal;
delete from contract;
delete from rent_order;
DELETE FROM `order_virtual_account_relation`;
delete from company;

-- 接入商家的信息 
INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`)
VALUES
	(1,'xiaoyu','70991db75ebb24fa0993f4fa25775022',00000000,'http://beta.demo2do.com/jupiter/','寓见',5),
	(2,'youpark','123456',00000000,'','优帕克',4);
INSERT INTO `principal` (`id`, `authority`, `name`, `password`) VALUES
	(1, 'ROLE_SUPER_USER', 'root', NULL, 'e10adc3949ba59abbe56e057f20f883e'),
	(2, 'ROLE_INSTITUTION', 'dingcheng', 2, 'e10adc3949ba59abbe56e057f20f883e'),
	(3, 'ROLE_MERCURY_APP', 'xiaoyu', 1, 'e10adc3949ba59abbe56e057f20f883e'),
	(4, 'ROLE_APP','youpark',2,'e10adc3949ba59abbe56e057f20f883e');
	
insert into `company`(`id`,`address`,`full_name`,`short_name`) values
('2','上海陆家嘴','鼎程（上海）商业保理有限公司','鼎程'),
('3','杭州万塘路8号','杭州随地付网络技术有限公司','租房宝'),
('4','上海','上海优帕克投资管理有限公司','优帕克'),
('5','上海','小寓科技','小寓'),
('6','上海','杭州驻客网络技术有限公司','驻客'),
('7','上海','上海元轼信息咨询有限公司','老A'),
('8','上海','柯罗芭','柯罗芭'),
('9','杭州','租房宝测试','租房宝测试'),
('10',null,'小寓测试帐号','小寓测试帐号'),
('11',null,'杭州蜗居','杭州蜗居'),
('12',null,'美家公寓','美家公寓');

-- 租约合同 
INSERT INTO `contract` (`id` ,`begin_date`,`contract_no`,`contract_status`,`deposit` ,`end_date`,`interrupt_date`,`interrupt_reason` ,
  `month_fee` ,`non_deposit_guarantee_way`,`payment_instrument` ,`renewal` ,`url` ,`customer_id` ,`house_id` ,`app_id`) VALUES
('48', '2014-04-12', 'YPK-CZ-0001', '0', '32000.00', '2016-04-11', null, '', '16.00', b'0', '0', null, null, '62', '51', '2'),
('49', '2014-04-27', 'YPK-CZ-0002', '0', '13400.00', '2015-10-26', null, '', '6.80', b'0', '0', null, null, '63', '52', '2'),
('50', '2014-07-01', 'YPK-CZ-0003', '0', '20000.00', '2015-12-31', null, '', '10.00', b'0', '0', null, null, '64', '53', '2');

-- 应付房租  
INSERT INTO `rent_order` (
  `id` ,
  `due_date` ,
  `end_date` ,
  `late_fee` ,
  `order_no` ,
  `order_status` ,
  `paid_rent` ,
  `payout_time` ,
  `is_settled` ,
  `start_date` ,
  `total_rent` ,
  `contract_id`,
  `customer_id`,
  `transfer_status`,
  `virtual_account_unique_id`) VALUES
('828', '2015-04-12', null, null, 'YPK-CZ-0001-13', 0, null, null, '', null, '16.00', '48', '62','0',''),
('829', '2015-04-12', null, null, 'YPK-CZ-0001-12', 0, null, null, '', null, '16.00', '48', '62','0','5a9155df-4e63-44cb-870b-e548858f0d69'),
('2961', '2015-04-13', null, null, 'YPK-CZ-0001-10', 0, null, null, '', null, '16.00', '48', '62','1','c2ac9722-9eed-4d9f-8062-65b8803b0664'),
('2962', '2015-04-12', null, null, 'YPK-CZ-0001-11', 0, null, null, '', null, '16.00', '48', '62','1','c2ac9722-9eed-4d9f-8062-65b8803b0664');
	

INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`, `contract_payment_amount`, `contract_payment_type`, `doubt_record`, `virtual_account_unique_id`, `bill_life_cycle`, `collection_bill_life_cycle`, `collection_bill_capital_status`, `asset_package_id`, `collection_bill_service_notice_status`, `collection_bill_frozen_status`, `factoring_contract_id`, `audit_bill_id`)
VALUES
	(256, '2015-04-13', '2015-05-12', NULL, 'CC-60445-3', 2, 0.10, NULL, 00000000, '2015-04-13', 0.10, 64, 125, 0, NULL, '9dfca839-5d8e-419d-8e9c-50fad3683b9e', 0, NULL, 'testForXiaoyu_CC-60445-3_256', NULL, NULL, NULL, '3f16b3e8-98ac-47ae-89a7-61de75f61967', 3, 3, 3, -1, 0, 0, -1, NULL);


INSERT INTO `order_virtual_account_relation` (`id`, `account_unique_id`, `is_delete`, `modified_time`, `partical_unique_id`, `transfer_status`, `contract_id`, `order_id`)
VALUES
	(32021, '5a9155df-4e63-44cb-870b-e548858f0d69', 00000000, '2015-07-10 00:00:00', '953fb78f2d6f6ea9cb10a82d399e4464', 1, 359, 2948),
	(32022, '5a9155df-4e63-44cb-870b-e548858f0d69', 00000000, '2015-07-10 00:00:00', '953fb78f2d6f6ea9cb10a82d399e4464', 1, 359, 2949),
	(32023, '5a9155df-4e63-44cb-870b-e548858f0d69', 00000000, '2015-07-10 00:00:00', '953fb78f2d6f6ea9cb10a82d399e4464', 1, 360, 2950),
	(32024, '5a9155df-4e63-44cb-870b-e548858f0d69', 00000000, '2015-07-10 00:00:00', '953fb78f2d6f6ea9cb10a82d399e4464', 1, 360, 2951),
	(32025, '5a9155df-4e63-44cb-870b-e548858f0d69', 00000000, '2015-07-10 00:00:00', '953fb78f2d6f6ea9cb10a82d399e4464', 1, 364, 2956),
	(32026, '5a9155df-4e63-44cb-870b-e548858f0d69', 00000000, '2015-07-10 00:00:00', '953fb78f2d6f6ea9cb10a82d399e4464', 1, 364, 2957),
	(32027, '5a9155df-4e63-44cb-870b-e548858f0d69', 00000000, '2015-07-10 00:00:00', '953fb78f2d6f6ea9cb10a82d399e4464', 1, 365, 2958),
	(32028, '5a9155df-4e63-44cb-870b-e548858f0d69', 00000000, '2015-07-10 00:00:00', '953fb78f2d6f6ea9cb10a82d399e4464', 1, 365, 2959),
	(32029, '5a9155df-4e63-44cb-870b-e548858f0d69', 00000000, '2015-07-10 00:00:00', '953fb78f2d6f6ea9cb10a82d399e4464', 1, 366, 2960),
	(32030, '5a9155df-4e63-44cb-870b-e548858f0d69', 00000000, '2015-07-10 00:00:00', '953fb78f2d6f6ea9cb10a82d399e4464', 1, 366, 2961),
	(32031, '5a9155df-4e63-44cb-870b-e548858f0d69', 00000000, '2015-07-10 00:00:00', '953fb78f2d6f6ea9cb10a82d399e4464', 1, 367, 2962);
	
INSERT INTO `order_virtual_account_relation` (`id`, `account_unique_id`, `is_delete`, `modified_time`, `partical_unique_id`, `transfer_status`, `contract_id`, `order_id`)
VALUES
	(33011, 'c2ac9722-9eed-4d9f-8062-65b8803b0664', 00000000, '2015-08-10 19:06:22', '09b1cfbb-09d8-4440-85b2-67840fb59a77', 1, 126, 828);
	
INSERT INTO `order_virtual_account_relation` (`id`, `account_unique_id`, `is_delete`, `modified_time`, `partical_unique_id`, `transfer_status`, `contract_id`, `order_id`)
VALUES
	(33012, 'c2ac9722-9eed-4d9f-8062-65b8803b0664', 00000000, '2015-08-10 19:06:22', '09b1cfbb-09d8-4440-85b2-67840fb59a77', 1, 126, 829);

SET FOREIGN_KEY_CHECKS = 1;
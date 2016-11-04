SET FOREIGN_KEY_CHECKS = 0;

delete from app;
delete from contract;
delete from rent_order;
DELETE FROM `order_virtual_account_relation`;
delete from principal;
delete from company;

-- 接入商家的信息 
INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`)
VALUES
	(1,'xiaoyu','70991db75ebb24fa0993f4fa25775022',00000000,'http://beta.demo2do.com/jupiter/','寓见',5),
	(2,'youpark','123456',00000000,'','优帕克',4);
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

-- 租约合同 
INSERT INTO `contract` (`id` ,`begin_date`,`contract_no`,`contract_status`,`deposit` ,`end_date`,`interrupt_date`,`interrupt_reason` ,
  `month_fee` ,`non_deposit_guarantee_way`,`payment_instrument` ,`renewal` ,`url` ,`customer_id` ,`house_id` ,`app_id`) VALUES
('48', '2014-04-12', 'YPK-CZ-0001', '0', '32000.00', '2016-04-11', null, '', '16.00', b'0', '0', null, null, '62', '51', '2'),
('49', '2014-04-27', 'YPK-CZ-0002', '0', '13400.00', '2015-10-26', null, '', '6.80', b'0', '0', null, null, '63', '52', '2'),
('50', '2014-07-01', 'YPK-CZ-0003', '0', '20000.00', '2015-12-31', null, '', '10.00', b'0', '0', null, null, '64', '53', '2');

INSERT INTO `principal` (`id`, `authority`, `name`, `password`) VALUES
	(1, 'ROLE_SUPER_USER', 'root', NULL, 'e10adc3949ba59abbe56e057f20f883e'),
	(2, 'ROLE_INSTITUTION', 'dingcheng', 2, 'e10adc3949ba59abbe56e057f20f883e'),
	(3, 'ROLE_APP', 'xiaoyu', 1, 'e10adc3949ba59abbe56e057f20f883e'),
	(4, 'ROLE_APP','youpark',2,'e10adc3949ba59abbe56e057f20f883e');

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
('96', '2015-04-12', null, null, 'YPK-CZ-0001-13', 0, null, null, '', null, '16.00', '48', '62','0',''),
('97', '2015-04-12', null, null, 'YPK-CZ-0001-12', 0, null, null, '', null, '16.00', '48', '62','0',''),
('98', '2015-04-12', null, null, 'YPK-CZ-0001-11', 0, null, null, '', null, '16.00', '48', '62','1','5a9155df-4e63-44cb-870b-e548858f0d69');
	
SET FOREIGN_KEY_CHECKS = 1;
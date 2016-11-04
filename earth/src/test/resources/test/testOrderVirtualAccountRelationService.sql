SET FOREIGN_KEY_CHECKS = 0;
delete from contract;
delete from rent_order;
DELETE FROM `order_virtual_account_relation`;

INSERT INTO `contract` (`id` ,`begin_date`,`contract_no`,`contract_status`,`deposit` ,`end_date`,`interrupt_date`,`interrupt_reason` ,
  `month_fee` ,`non_deposit_guarantee_way`,`payment_instrument` ,`renewal` ,`url` ,`customer_id` ,`house_id` ,`app_id`) VALUES
('48', '2014-04-12', 'YPK-CZ-0001', '0', '32000.00', '2016-04-11', null, '', '16.00', b'0', '0', null, null, '62', '51', '2'),
('49', '2014-04-27', 'YPK-CZ-0002', '0', '13400.00', '2015-10-26', null, '', '6.80', b'0', '0', null, null, '63', '52', '2'),
('367', '2014-07-01', 'YPK-CZ-0003', '0', '20000.00', '2015-12-31', null, '', '10.00', b'0', '0', null, null, '64', '53', '2');

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
  `customer_id`) VALUES
('96', '2015-04-12', null, null, 'YPK-CZ-0001-13', 0, null, null, '', null, '16.00', '48', '62'),
('97', '2015-05-12', null, null, 'YPK-CZ-0001-14', 0, null, null, '', null, '16.00', '48', '62'),
('98', '2015-06-12', null, null, 'YPK-CZ-0001-15', 0, null, null, '', null, '16.00', '48', '62'),
('108', '2015-04-27', null, null, 'YPK-CZ-0002-13', 0, null, null, '', null, '6.80', '49', '63'),
('109', '2015-05-27', null, null, 'YPK-CZ-0002-14', 0, null, null, '', null, '6.80', '49', '63'),
('110', '2015-06-27', null, null, 'YPK-CZ-0002-15', 0, null, null, '', null, '6.80', '49', '63'),
('114', '2015-04-01', null, null, 'YPK-CZ-0003-10', 0, null, null, '', null, '10.00', '50', '64'),
('115', '2015-05-01', null, null, 'YPK-CZ-0003-11', 0, null, null, '', null, '10.00', '50', '64'),
('2962', '2015-06-01', null, null, 'YPK-CZ-0003-12', 0, null, null, '', null, '10.00', '367', '64');

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
	(33011, 'c2ac9722-9eed-4d9f-8062-65b8803b0664', 00000000, '2015-08-10 19:06:22', '09b1cfbb-09d8-4440-85b2-67840fb59a77', 0, 126, 828);
	
INSERT INTO `order_virtual_account_relation` (`id`, `account_unique_id`, `is_delete`, `modified_time`, `partical_unique_id`, `transfer_status`, `contract_id`, `order_id`)
VALUES
	(33012, 'c2ac9722-9eed-4d9f-8062-65b8803b0664', 00000000, '2015-08-10 19:06:22', '09b1cfbb-09d8-4440-85b2-67840fb59a77', 1, 126, 829);



	
SET FOREIGN_KEY_CHECKS = 1;
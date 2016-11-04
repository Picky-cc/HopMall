SET FOREIGN_KEY_CHECKS = 0;

delete from rent_order;
delete from contract;
delete from house;
delete from customer;
delete from transaction_record;
delete from transaction_record_log;
delete from finance_payment_record;
delete from app;
delete from receive_order_map;
delete from app_arrive_record;
delete from asset_package;

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `payee_account_name`, `payee_account_no`, `payee_bank_name`) VALUES
(2, 'xiaoyu', '70991db75ebb24fa0993f4fa25775022', b'0', 'http://beta.demo2do.com/jupiter/', '寓见', 5, NULL, NULL, NULL);

INSERT INTO `receive_order_map` (`id`, `amount`, `association_type`, `create_time`, `is_deducted`, `pay_time`, `app_arrive_record_id`, `order_id`, `receive_order_map_status`) VALUES
(1, '16500.00', 1, '2015-04-03 12:00:00', b'1', '2015-04-03 12:00:00', 1, 1, 0),
(2, '16500.00', 1, '2015-04-03 12:00:00', b'1', '2015-04-03 12:00:00', 2, 2, 0),
(3, '13500.00', 1, '2015-04-03 12:00:00', b'1', '2015-04-03 12:00:00', 3, 3, 0),
(4, '12600.00', 1, '2015-04-03 12:00:00', b'1', '2015-04-03 12:00:00', 4, 4, 0);

INSERT INTO `app_arrive_record` (`id`, `amount`, `arrive_record_status`, `pay_ac_no`, `receive_ac_no`, `remark`, `serial_no`, `time`, `app_id`, `drcrf`, `pay_name`, `summary`, `vouh_no`, `operate_remark`) VALUES
(1, '52500.00', 2, '6222081001006853570', '1001300219000027827', '永业公寓28号604', '2015-03-30-22.39.56.930280', '2015-03-30 22:39:56', 2, '2', '徐月', '网转', '0', NULL),
(2, '58000.00', 2, '00000440367604092', '1001300219000027827', '', '2015-03-30-17.57.10.628144', '2015-03-30 17:57:10', 2, '2', '上海博雅方略资产管理有限公司', '房屋租赁费', '0', NULL),
(3, '37510.00', 2, '10012906012157280', '1001300219000027827', '', '2015-03-30-16.36.16.033357', '2015-03-30 16:36:16', 2, '2', '陆雪芸', '汇款', '0', NULL),
(4, '49500.00', 2, '00000452059224352', '1001300219000027827', '', '2015-03-30-16.11.50.920468', '2015-03-30 16:11:50', 2, '2', '上海化耀国际贸易有限公司', '汇川路88号1号楼1803?', '0', NULL);

INSERT INTO `finance_payment_record` (`id`, `creator_id`, `pay_money`, `payment_no`, `payment_time`, `order_id`, `batch_pay_record_id`, `bank_name`, `card_no`, `payee_name`) VALUES
(1185, 9, '1200.00', '1509092169848140001', '2015-09-21 17:29:06', 1, 157, NULL, NULL, NULL),
(1186, 9, '1600.00', '1509092169848140001', '2015-09-21 17:29:07', 2, 157, NULL, NULL, NULL),
(1187, 9, '1200.00', '1509092169848140001', '2015-09-21 17:29:08', 3, 157, NULL, NULL, NULL),
(1188, 9, '900.00', '1509092169848140001', '2015-09-21 17:29:09', 4, 157, NULL, NULL, NULL);

INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`, `contract_payment_amount`, `contract_payment_type`, `doubt_record`, `virtual_account_unique_id`,`asset_package_id`) VALUES
(1, '2015-12-07', NULL, NULL, 'C33-20150928-9', 0, NULL, NULL, b'0', NULL, '22000.00', 1, 1, 0, NULL, NULL, 0, NULL, 'yuanlai_C33-20150928-9_6774', '22000.00', 1, NULL, NULL,13),
(2, '2016-01-07', NULL, NULL, 'C33-20150928-10', 0, NULL, NULL, b'0', NULL, '22000.00', 1, 1, 0, NULL, NULL, 0, NULL, 'yuanlai_C33-20150928-10_6775', '22000.00', 1, NULL, NULL,13),
(3, '2016-02-07', NULL, NULL, 'C33-20150928-11', 0, NULL, NULL, b'0', NULL, '22000.00', 1, 1, 0, NULL, NULL, 0, NULL, 'yuanlai_C33-20150928-11_6776', '22000.00', 1, NULL, NULL,13),
(4, '2016-03-07', NULL, NULL, 'C33-20150928-12', 0, NULL, NULL, b'0', NULL, '22000.00', 1, 1, 0, NULL, NULL, 0, NULL, 'yuanlai_C33-20150928-12_6777', '22000.00', 1, NULL, NULL,13);

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`, `transfer_status`) VALUES
(1, '2015-04-01', 'KXHY1#1803', 0, '33000.00', '2016-03-31', NULL, '', '16500.00', b'0', 1, NULL, NULL, 2, 1, 1, 0);

INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`) VALUES
(1, NULL, NULL, NULL, '小寓预付款承租方', NULL, '寓见', 1);

INSERT INTO `house` (`id`, `address`, `area`, `bulid_area`, `city`, `community`, `community_detail`, `hall`, `house_status`, `house_type`, `province`, `room`, `app_id`) VALUES
(1, '杭州市西湖区', NULL, 0, NULL, 'test-rom', NULL, 0, 1, 2, NULL, 0, 8);

INSERT INTO `transaction_record` (`id`, `card_no`, `channel`, `last_modified_time`, `merchant_payment_no`, `order_no`, `pay_money`, `request_no`, `trade_no`, `transaction_record_status`, `app_id`, `payment_institution_id`) VALUES
(1, '00000457261761640', 2, '2015-06-23 17:04:05', NULL, 'C33-20150928-9', '13500.00', NULL, NULL, 2, 2, 3),
(2, '00000452059224352', 2, '2015-06-23 17:04:26', NULL, 'C33-20150928-10', '16500.00', NULL, NULL, 2, 2, 3),
(3, ' ', 2, '2015-06-23 17:20:27', NULL, 'C33-20150928-11', '17000.00', NULL, NULL, 2, 2, 3),
(4, ' ', 2, '2015-06-23 17:21:25', NULL, 'C33-20150928-12', '18000.00', NULL, NULL, 2, 2, 3);

INSERT INTO `transaction_record_log` (`id`, `modified_time`, `operator_id`, `transaction_record_operate_status`, `transaction_record_id`) VALUES
(1244, '2015-07-21 16:13:00', 334, 1, 1),
(1245, '2015-07-21 16:13:24', 334, 1, 2),
(1246, '2015-07-21 16:13:49', 334, 1, 3),
(1247, '2015-07-21 16:33:02', 9, 2, 4);
INSERT INTO `asset_package` (`id`, `adva_start_date`, `is_available`, `bank_account`, `bank_name`, `create_time`, `latest_settle_date`, `otsd_amt`, `otsd_amt_aval`, `rec_transfee_total_amt`, `remove_time`, `contract_id`, `factoring_contract_id`, `thru_date`) VALUES ('13', '2015-04-03 00:00:00', '', NULL, NULL, '2015-04-02 00:00:00', '2015-04-03 00:00:00', '138600.00', '138600.00', '198000.00', NULL, '13', '58', NULL);


SET FOREIGN_KEY_CHECKS = 1;
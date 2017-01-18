set foreign_key_checks = 0;

delete from rent_order;
delete from contract;
delete from app;
delete from asset_package;
delete from house;
delete from customer;

INSERT INTO `house` (`id`, `address`, `area`, `bulid_area`, `city`, `community`, `community_detail`, `hall`, `house_status`, `house_type`, `province`, `room`, `app_id`) VALUES
(13, '汇川路88号1#1803', NULL, 0, NULL, '凯欣豪苑1#1803', NULL, 0, 1, 1, NULL, 0, 2),
(14, '徐汇区淮海中路183弄3号602室', NULL, 0, NULL, '汇宁花园3#602', NULL, 0, 1, 1, NULL, 0, 2),
(15, '紫云路118弄1号33B室', NULL, 0, NULL, '虹桥豪苑1#33B', NULL, 0, 1, 1, NULL, 0, 2);

INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`) VALUES
(971, NULL, NULL, NULL, '小寓预付款承租方', NULL, '寓见', 1),
(106, NULL, NULL, NULL, '上海化耀国际贸易有限公司', NULL, '优帕克', 2),
(107, NULL, NULL, NULL, '吴林飞', NULL, '优帕克', 2),
(108, NULL, NULL, NULL, 'R.B', NULL, '优帕克', 2),
(109, NULL, NULL, NULL, 'OWENBOYDALEXANDER', NULL, '优帕克', 2),
(110, NULL, NULL, NULL, 'QM', NULL, '优帕克', 2);

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `payee_account_name`, `payee_account_no`, `payee_bank_name`) VALUES
(1, 'xiaoyu', '70991db75ebb24fa0993f4fa25775022', b'0', 'http://beta.demo2do.com/jupiter/', '寓见', 5, NULL, NULL, NULL),
(2, 'youpark', '123456', b'0', '', '优帕克', 4, NULL, NULL, NULL);

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`, `transfer_status`) VALUES
(1, '2015-04-01', 'KXHY1#1803', 0, '33000.00', '2016-03-31', NULL, '', '16500.00', b'0', 1, NULL, NULL, 2, 106, 13, 0),
(2, '2014-11-01', 'HNHY3#602', 0, '25200.00', '2015-10-31', NULL, '', '12600.00', b'0', 1, NULL, NULL, 2, 107, 14, 0),
(3, '2014-11-01', 'XXXXX#603', 0, '25200.00', '2015-10-31', NULL, '', '12600.00', b'0', 1, NULL, NULL, 2, 107, 14, 0);

INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`, `contract_payment_amount`, `contract_payment_type`, `doubt_record`, `virtual_account_unique_id`,`asset_package_id`) VALUES
(1, '2015-04-01', NULL, NULL, 'KXHY1#1803-20150402-1', 1, '500.00', '2015-04-03 16:45:24', b'0', NULL, '10000.00', 1, 106, 1, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, NULL, NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a','-1'),
(2, '2015-05-01', NULL, NULL, 'KXHY1#1803-20150402-2', 1, '500.00', '2015-05-13 15:17:34', b'0', NULL, '10000.00', 1, 106, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, NULL, NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a','1'),
(3, '2015-06-01', NULL, NULL, 'KXHY1#1803-20150402-3', 0, '16500.00', '2015-05-27 16:45:49', b'0', NULL, '10000.00', 1, 106, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, NULL, NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a','1'),
(4, '2015-07-01', NULL, NULL, 'HNHY3#602-4', 0, '16500.00', '2015-06-23 17:04:30', b'0', NULL, '6500.00', 2, 106, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, NULL, NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a','-1'),
(5, '2015-08-01', NULL, NULL, 'HNHY3#602-5', 0, '16500.00', '2015-06-23 17:04:30', b'0', NULL, '6500.00', 2, 106, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, NULL, NULL, 0, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a','2'),
(6, '2015-05-01', NULL, NULL, 'XXXXX#603-5', 0, '16000.00', '2015-06-23 17:04:30', b'0', NULL, '6500.00', 2, 106, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, NULL, NULL, 0, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a','3');

INSERT INTO `asset_package` (`id`, `adva_start_date`, `is_available`, `bank_account`, `bank_name`, `create_time`, `latest_settle_date`, `otsd_amt`, `otsd_amt_aval`, `rec_transfee_total_amt`, `remove_time`, `contract_id`, `factoring_contract_id`,`thru_date`) VALUES
(1, '2015-04-03 00:00:00', b'1', NULL, NULL, '2015-04-02 00:00:00', '2015-04-03 00:00:00', '138600.00', '138600.00', '198000.00', NULL, 1, 58, NULL),
(2, '2015-04-03 00:00:00', b'1', NULL, NULL, '2015-04-02 00:00:00', '2015-04-03 00:00:00', '61740.00', '61740.00', '88200.00', NULL, 2, 58, '2015-12-01 00:00:00'),
(3, '2015-04-03 00:00:00', b'0', NULL, NULL, '2015-04-02 00:00:00', '2015-04-03 00:00:00', '61740.00', '61740.00', '88200.00', NULL, 2, 58, NULL);

set foreign_key_checks = 1;
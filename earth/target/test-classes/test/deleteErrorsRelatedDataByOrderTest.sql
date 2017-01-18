SET FOREIGN_KEY_CHECKS=0;

delete from import_result;
delete from contract;
delete from rent_order;
delete from customer;
delete from house;
delete from app;

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `payee_account_name`, `payee_account_no`, `payee_bank_name`) VALUES
(1, 'xiaoyu', '70991db75ebb24fa0993f4fa25775022', b'0', 'http://beta.demo2do.com/jupiter/', '寓见', 5, NULL, NULL, NULL);

INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`) VALUES
(1, '2015-04-01', NULL, NULL, 'KXHY1#1803-20150402-1', 3, '16500.00', '2015-04-03 16:45:24', b'0', NULL, '16500.00', 1, 1, 1, NULL, NULL, 0, NULL, NULL),
(2, '2015-05-01', NULL, NULL, 'KXHY1#1803-20150402-2', 3, '16500.00', '2015-05-13 15:17:34', b'0', NULL, '16500.00', 2, 2, 0, NULL, NULL, 0, NULL, NULL),
(3, '2015-05-01', NULL, NULL, 'KXHY1#1803-20150402-3', 3, '16500.00', '2015-05-13 15:17:34', b'0', NULL, '16500.00', 2, 2, 0, NULL, NULL, 0, NULL, NULL),
(4, '2015-05-01', NULL, NULL, 'KXHY1#1803-20150402-4', 3, '16500.00', '2015-05-13 15:17:34', b'0', NULL, '16500.00', 2, 2, 0, NULL, NULL, 0, NULL, NULL),
(5, '2015-05-01', NULL, NULL, 'KXHY1#1803-20150402-5', 3, '16500.00', '2015-05-13 15:17:34', b'0', NULL, '16500.00', 2, 2, 0, NULL, NULL, 0, NULL, NULL),
(6, '2015-05-01', NULL, NULL, 'KXHY1#1803-20150402-6', 3, '16500.00', '2015-05-13 15:17:34', b'0', NULL, '16500.00', 2, 2, 0, NULL, NULL, 0, NULL, NULL),
(7, '2015-06-01', NULL, NULL, 'KXHY1#1803-20150402-7', 3, '16500.00', '2015-05-27 16:45:49', b'0', NULL, '16500.00', 3, 3, 0, NULL, NULL, 0, NULL, NULL);

INSERT INTO `house` (`id`, `address`, `area`, `bulid_area`, `city`, `community`, `community_detail`, `hall`, `house_status`, `house_type`, `province`, `room`, `app_id`) VALUES
(2, '汇川路88号1#1803', NULL, 0, NULL, '凯欣豪苑1#1803', NULL, 0, 1, 1, NULL, 0, 1),
(3, '徐汇区淮海中路183弄3号602室', NULL, 0, NULL, '汇宁花园3#602', NULL, 0, 1, 1, NULL, 0, 1),
(4, '紫云路118弄1号33B室', NULL, 0, NULL, '虹桥豪苑1#33B', NULL, 0, 1, 1, NULL, 0, 1),
(5, '紫云路118弄1号33B室', NULL, 0, NULL, '虹桥豪苑1#33B', NULL, 0, 1, 1, NULL, 0, 1);

INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`) VALUES
(1, NULL, NULL, NULL, '小寓预付款承租方', NULL, '寓见', 1),
(2, NULL, NULL, NULL, '上海化耀国际贸易有限公司', NULL, '优帕克', 1),
(3, NULL, NULL, NULL, '吴林飞', NULL, '优帕克', 1),
(4, NULL, NULL, NULL, '吴林飞', NULL, '优帕克', 1);
INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`, `transfer_status`) VALUES
(1, '2015-04-01', 'KXHY1#1803', 0, '33000.00', '2016-03-31', NULL, '', '16500.00', b'0', 0, NULL, NULL, 1, 1, 2, 0),
(2, '2014-11-01', 'HNHY3#602', 0, '25200.00', '2015-10-31', NULL, '', '12600.00', b'0', 0, NULL, NULL, 1, 2, 3, 0),
(3, '2014-06-01', 'HQHY1#33B', 0, '34000.00', '2015-11-30', NULL, '', '17000.00', b'0', 0, NULL, NULL, 1, 3, 4, 0),
(4, '2014-06-01', 'HQHY1#33B', 0, '34000.00', '2015-11-30', NULL, '', '17000.00', b'0', 0, NULL, NULL, 1, 4, 5, 0);

SET FOREIGN_KEY_CHECKS=1;
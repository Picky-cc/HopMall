set foreign_key_checks = 0;

delete from rent_order;
delete from contract ;
delete from app;
delete from asset_package;
delete from customer;

INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`) VALUES
(971, NULL, NULL, NULL, '小寓预付款承租方', NULL, '寓见', 1),
(106, NULL, NULL, NULL, '上海化耀国际贸易有限公司', NULL, '优帕克', 2),
(107, NULL, NULL, NULL, '吴林飞', NULL, '优帕克', 2),
(108, NULL, NULL, NULL, 'R.B', NULL, '优帕克', 2),
(109, NULL, NULL, NULL, 'OWENBOYDALEXANDER', NULL, '优帕克', 2),
(110, NULL, NULL, NULL, 'QM', NULL, '优帕克', 2);

INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`, `contract_payment_amount`, `contract_payment_type`, `doubt_record`, `virtual_account_unique_id`,`asset_package_id`) VALUES
(1, '2015-04-13', NULL, NULL, 'KXHY1#1803-20150402-1', 0, '16500.00', '2015-04-03 16:45:24', b'0', NULL, '16500.00', 13, 106, 1, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, NULL, NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a',13),
(2, '2015-04-14', NULL, NULL, 'KXHY1#1803-20150402-2', 1, '16500.00', '2015-05-13 15:17:34', b'0', NULL, '16500.00', 13, 106, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, NULL, NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a',13),
(3, '2015-04-15', NULL, NULL, 'KXHY1#1803-20150402-3', 1, '16500.00', '2015-05-27 16:45:49', b'0', NULL, '16500.00', 13, 106, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 0, NULL, NULL, NULL, 0, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a',13),
(4, '2015-04-16', NULL, NULL, 'KXHY1#1803-20150402-4', 0, '16500.00', '2015-06-23 17:04:30', b'0', NULL, '16500.00', 13, 106, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, NULL, NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a',13);

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`, `transfer_status`) VALUES
(13, '2015-09-01', 'HQWB8#701-2015-09-08', 0, '10000.00', '2016-08-31', NULL, '', '10000.00', b'0', 3, NULL, NULL, 1, 971, 913, 0);

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `payee_account_name`, `payee_account_no`, `payee_bank_name`) VALUES
(1, 'xiaoyu', '70991db75ebb24fa0993f4fa25775022', b'0', 'http://beta.demo2do.com/jupiter/', '寓见', 5, NULL, NULL, NULL),
(2, 'youpark', '123456', b'0', '', '优帕克', 4, NULL, NULL, NULL);

INSERT INTO `asset_package` (`id`, `adva_start_date`, `is_available`, `bank_account`, `bank_name`, `create_time`, `latest_settle_date`, `otsd_amt`, `otsd_amt_aval`, `rec_transfee_total_amt`, `remove_time`, `contract_id`, `factoring_contract_id`) VALUES
(13, '2015-04-03 00:00:00', b'1', NULL, NULL, '2015-04-02 00:00:00', '2015-04-03 00:00:00', '138600.00', '138600.00', '198000.00', NULL, 13, 58);

set foreign_key_checks = 1;
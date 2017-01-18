SET FOREIGN_KEY_CHECKS=0;

delete from `rent_order`;
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`, `contract_payment_amount`, `contract_payment_type`, `doubt_record`) VALUES
(1377, '2015-06-26', NULL, NULL, 'CC-60794-3', 0, '0.00', NULL, b'0', NULL, '2000.00', 181, 242, 1, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL),
(1378, '2015-07-26', NULL, NULL, 'CC-60794-4', 0, '0.00', NULL, b'0', NULL, '2000.00', 181, 242, 0, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL),
(1379, '2015-08-26', NULL, NULL, 'CC-60794-5', 2, '2000.00', NULL, b'0', NULL, '2000.00', 181, 242, 0, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL),
(1380, '2015-09-26', NULL, NULL, 'CC-60794-6', 0, '0.00', NULL, b'0', NULL, '2000.00', 181, 242, 0, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL),
(1381, '2015-10-26', NULL, NULL, 'CC-60794-7', 0, '0.00', NULL, b'0', NULL, '2000.00', 181, 242, 0, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL),
(1382, '2015-11-26', NULL, NULL, 'CC-60794-8', 0, '0.00', NULL, b'0', NULL, '2000.00', 181, 242, 0, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL),
(1383, '2015-12-26', NULL, NULL, 'CC-60794-9', 0, '0.00', NULL, b'0', NULL, '2000.00', 181, 242, 0, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL),
(1384, '2016-01-26', NULL, NULL, 'CC-60794-10', 0, '0.00', NULL, b'0', NULL, '2000.00', 181, 242, 0, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL),
(1385, '2016-02-26', NULL, NULL, 'CC-60794-11', 0, '0.00', NULL, b'0', NULL, '2000.00', 181, 242, 0, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL),
(1386, '2016-03-26', NULL, NULL, 'CC-60794-12', 0, '0.00', NULL, b'0', NULL, '2000.00', 181, 242, 0, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL);


delete from `contract`;
INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`, `transfer_status`) VALUES
(181, '2015-04-26', 'CC-60794', 0, '4000.00', '2016-04-25', NULL, '', '2000.00', b'0', 1, NULL, NULL, 1, 242, 184, 0);

delete from `asset_package`;
INSERT INTO `asset_package` (`id`, `adva_start_date`, `is_available`, `bank_account`, `bank_name`, `create_time`, `latest_settle_date`, `otsd_amt`, `otsd_amt_aval`, `rec_transfee_total_amt`, `remove_time`, `contract_id`, `factoring_contract_id`) VALUES
(146, '2015-06-02 00:00:00', b'0', NULL, NULL, '2015-07-01 20:06:29', '2015-06-02 00:00:00', '14000.00', '14000.00', '20000.00', NULL, 181, 61);



SET FOREIGN_KEY_CHECKS=1;

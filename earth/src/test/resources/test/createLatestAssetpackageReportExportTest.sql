SET FOREIGN_KEY_CHECKS=0;

delete from factoring_contract;
delete from asset_package;
delete from contract;
delete from rent_order;
delete from house;
delete from customer;
delete from payment_refund;

INSERT INTO `payment_refund` (`id`, `asset_package_id`, `payment_amount`, `repayment_time`) VALUES
(3, 59, '7803.13', '2015-10-19 13:34:35'),
(4, 26, '17255.78', '2015-10-19 13:34:00'),
(5, 23, '11474.13', '2015-10-13 15:35:00');

INSERT INTO `factoring_contract` (`id`, `adva_matuterm`, `adva_pct`, `adva_start_date`, `adva_term`, `ar_amt`, `ar_cost_rate`, `ar_deposit_amt`, `ar_deposit_amt_aval`, `ar_deposit_amt_input_days`, `ar_deposit_amt_input_type`, `ar_ldgamt`, `borrower_name`, `contract_no`, `cost_deduct_type`, `deposit_deduct_type`, `int_accuretype`, `int_rate`, `latest_settle_date`, `lender_ac_bk_name`, `lender_ac_no`, `lender_name`, `otsd_amt`, `otsd_amt_aval`, `settle_pct`, `app_id`, `company_id`, `nominal_rate`, `payment_agreement_id`) VALUES
(58, 7, '0.70', '2015-04-02 00:00:00', 0, '11211970.00', '0.00', '0.00', '0.00', 15, 1, '11211970.00', '优帕克', 'DCF-YPK-LR903A', 2, 1, 2, '0.1971432000', NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', '7848379.00', '7848379.00', '1.00', 2, 2, '0.0850000000', 1),
(61, 7, '0.70', '2015-06-02 21:47:05', 0, '0.00', '0.00', '0.00', '0.00', 0, 1, '0.00', '寓见', 'DCF-XY-FZR905A', 2, 2, 2, '0.1966003200', NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', '0.00', '0.00', '1.00', 1, 2, '0.0850000000', 2),
(62, 10, '0.70', '2015-06-11 17:32:16', 0, '7092633.60', '0.00', '0.00', '0.00', 0, 1, '7092633.60', '柯罗芭', 'DCF-KLB-JMR904A', 2, 1, 2, '0.1853200000', NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', '4793040.00', '4793040.00', '1.00', 6, 2, '0.0850000000', 1),
(63, 7, '0.70', '2015-08-27 12:32:16', 0, '0.00', '0.00', '0.00', '0.00', 15, 1, '0.00', '安心公寓', 'DCF-RX-FR906A', 2, 1, 2, '0.1966003200', NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', '0.00', '0.00', '1.00', 10, 2, '0.0850000000', 1),
(66, 7, '0.70', '2015-09-07 14:30:16', 0, '0.00', '0.00', '0.00', '0.00', 15, 1, '0.00', '源涞国际', 'DCF-YL-FR907A', 2, 1, 2, '0.1966003200', NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', '0.00', '0.00', '1.00', 11, 2, '0.0850000000', 1),
(67, 10, 0.70, '2015-10-30 14:40:20', 0, 0.00, 0.00, 0.00, 0.00, 0, 1, 0.00, '汉维仓储', 'DCF-HW-FR908A', 2, 1, 2, 0.1966003200, NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', 0.00, 0.00, 1.00, 12, 2, 0.0850000000, 6);


INSERT INTO `asset_package` (`id`, `adva_start_date`, `is_available`, `bank_account`, `bank_name`, `create_time`, `latest_settle_date`, `otsd_amt`, `otsd_amt_aval`, `rec_transfee_total_amt`, `remove_time`, `contract_id`, `factoring_contract_id`) VALUES
(59, '2015-07-31 00:00:00', b'1', NULL, NULL, '2015-07-28 21:48:50', '2015-07-31 00:00:00', '6300.00', '6300.00', '9000.00', NULL, 911, 61);

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`, `transfer_status`) VALUES
(911, '2015-09-01', 'HQWB8#701-2015-09-08', 0, '10000.00', '2016-08-31', NULL, '', '10000.00', b'0', 3, NULL, NULL, 2, 971, 913, 0);

INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`, `contract_payment_amount`, `contract_payment_type`, `doubt_record`, `virtual_account_unique_id`) VALUES
(6003, '2016-05-28', '2016-09-03', NULL, 'ct-0253-5', 0, '0.00', NULL, b'0', '2016-06-04', '3210.00', 877, 937, NULL, NULL, '953fb78f2d6f6ea9cb10a82d399e4464', 1, NULL, 'meijia_ct-0253-5_6003', NULL, NULL, NULL, '36f1c341-ab36-4ec0-bea8-26fd385b9114'),
(6004, '2015-09-06', '2015-12-04', NULL, '0000283-1', 0, '0.00', NULL, b'0', '2015-09-05', '4110.00', 878, 938, NULL, NULL, '953fb78f2d6f6ea9cb10a82d399e4464', 1, NULL, 'meijia_0000283-1_6004', NULL, NULL, NULL, '36f1c341-ab36-4ec0-bea8-26fd385b9114'),
(6005, '2015-12-01', '2016-03-04', NULL, '0000283-2', 0, '0.00', NULL, b'0', '2015-12-05', '2610.00', 878, 938, NULL, NULL, '953fb78f2d6f6ea9cb10a82d399e4464', 1, NULL, 'meijia_0000283-2_6005', NULL, NULL, NULL, '36f1c341-ab36-4ec0-bea8-26fd385b9114'),
(6006, '2016-03-01', '2016-06-04', NULL, '0000283-3', 0, '0.00', NULL, b'0', '2016-03-05', '2610.00', 878, 938, NULL, NULL, '953fb78f2d6f6ea9cb10a82d399e4464', 1, NULL, 'meijia_0000283-3_6006', NULL, NULL, NULL, '36f1c341-ab36-4ec0-bea8-26fd385b9114'),
(6007, '2016-06-01', '2016-09-04', NULL, '0000283-4', 0, '0.00', NULL, b'0', '2016-06-05', '2610.00', 878, 938, NULL, NULL, '953fb78f2d6f6ea9cb10a82d399e4464', 1, NULL, 'meijia_0000283-4_6007', NULL, NULL, NULL, '36f1c341-ab36-4ec0-bea8-26fd385b9114');

INSERT INTO `house` (`id`, `address`, `area`, `bulid_area`, `city`, `community`, `community_detail`, `hall`, `house_status`, `house_type`, `province`, `room`, `app_id`) VALUES
(13, '汇川路88号1#1803', NULL, 0, NULL, '凯欣豪苑1#1803', NULL, 0, 1, 1, NULL, 0, 2),
(14, '徐汇区淮海中路183弄3号602室', NULL, 0, NULL, '汇宁花园3#602', NULL, 0, 1, 1, NULL, 0, 2),
(15, '紫云路118弄1号33B室', NULL, 0, NULL, '虹桥豪苑1#33B', NULL, 0, 1, 1, NULL, 0, 2),
(16, '新闸路1528弄1号1804室', NULL, 0, NULL, '静安国际1804', NULL, 0, 1, 1, NULL, 0, 2),
(17, '常德路500弄4号26A室', NULL, 0, NULL, '静安枫景4#26A', NULL, 0, 1, 1, NULL, 0, 2),
(18, '西康路501号4号1203室', NULL, 0, NULL, '静安豪景4#1203', NULL, 0, 1, 1, NULL, 0, 2);

INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`) VALUES
(-1, NULL, NULL, NULL, '小寓预付款承租方', NULL, '寓见', 1),
(106, NULL, NULL, NULL, '上海化耀国际贸易有限公司', NULL, '优帕克', 2),
(107, NULL, NULL, NULL, '吴林飞', NULL, '优帕克', 2),
(108, NULL, NULL, NULL, 'R.B', NULL, '优帕克', 2),
(109, NULL, NULL, NULL, 'OWENBOYDALEXANDER', NULL, '优帕克', 2),
(110, NULL, NULL, NULL, 'QM', NULL, '优帕克', 2),
(111, NULL, NULL, NULL, 'eileen', NULL, '优帕克', 2),
(118, NULL, NULL, NULL, '目黑克彦', NULL, '优帕克', 2);

SET FOREIGN_KEY_CHECKS=1;
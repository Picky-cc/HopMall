SET FOREIGN_KEY_CHECKS=0;
delete from factoring_contract;
delete from contract;
delete from rent_order;
delete from asset_package;
delete from customer;
delete from house;

INSERT INTO `factoring_contract` (`id`, `adva_matuterm`, `adva_pct`, `adva_start_date`, `adva_term`, `ar_amt`, `ar_cost_rate`, `ar_deposit_amt`, `ar_deposit_amt_aval`, `ar_deposit_amt_input_days`, `ar_deposit_amt_input_type`, `ar_ldgamt`, `borrower_name`, `contract_no`, `cost_deduct_type`, `deposit_deduct_type`, `int_accuretype`, `int_rate`, `latest_settle_date`, `lender_ac_bk_name`, `lender_ac_no`, `lender_name`, `otsd_amt`, `otsd_amt_aval`, `settle_pct`, `app_id`, `company_id`, `nominal_rate`, `payment_agreement_id`, `deposit_rate`) 
VALUES 
         ('61', '7', '0.70', '2015-06-02 21:47:05', '0', '0.00', '0.00', '0.00', '0.00', '0', '1', '0.00', '寓见', 'DCF-XY-FZR905A', '2', '2', '2', '0.1966003200', NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', '0.00', '0.00', '1.00', '1', '2', '0.0850000000', '2', NULL),
         ('58', '7', '0.70', '2015-04-02 00:00:00', '0', '11211970.00', '0.00', '0.00', '0.00', '15', '1', '11211970.00', '优帕克', 'DCF-YPK-LR903A', '2', '1', '2', '0.1971432000', NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', '7848379.00', '7848379.00', '1.00', '2', '2', '0.0850000000', '1', NULL),
         ('62', '10', '0.70', '2015-06-11 17:32:16', '0', '10289433.60', '0.00', '0.00', '0.00', '0', '1', '10289433.60', '柯罗芭', 'DCF-KLB-JMR904A', '2', '1', '2', '0.1853200000', NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', '7313040.00', '7313040.00', '1.00', '6', '2', '0.0850000000', '5', NULL),
         ('63', '7', '0.70', '2015-08-27 12:32:16', '0', '6.00', '0.00', '0.00', '0.00', '15', '1', '6.00', '安心公寓', 'DCF-RX-FR906A', '2', '1', '2', '0.1966003200', NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', '20.00', '20.00', '1.00', '10', '2', '0.0850000000', '3', NULL),
         ('66', '7', '0.70', '2015-09-07 14:30:16', '0', '0.00', '0.00', '0.00', '0.00', '15', '1', '0.00', '源涞国际', 'DCF-YL-FR907A', '2', '1', '2', '0.1966003200', NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', '0.00', '0.00', '1.00', '11', '2', '0.0850000000', '4', '0.1000000000'),
          ('67', '10', '0.70', '2015-10-30 14:40:20', '0', '2640000.00', '0.00', '0.00', '0.00', '0', '1', '2640000.00', '汉维仓储', 'DCF-HW-FR908A', '2', '1', '2', '0.1966003200', NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', '1866000.00', '1866000.00', '1.00', '12', '2', '0.0850000000', '6', NULL);


     
INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`, `shroff_account_id`, `transfer_status`, `contract_life_cycle`, `contract_type`)
VALUES
         ('1274', '2015-10-15', 'CC-63848', '0', '0.00', '2016-10-14', NULL, '', '700.00', '\0', '2', NULL, NULL, '1', '1335', '1264', NULL, '0', NULL, '0'),
         ('79', '2015-03-06', 'CC-60334', '0', '3200.00', '2016-03-05', NULL, NULL, '1600.00', '\0', '1', NULL, NULL, '1', '140', '82', NULL, '0', '1', '0'),
         ('1007', '2015-05-02', 'CC-60943', '0', '0.00', '2017-05-01', NULL, '', '4000.00', '\0', '2', NULL, NULL, '1', '1067', '1003', NULL, '0', '1', '0'),
         ('140', '2015-04-08', 'CC-60537', '0', '4800.00', '2016-04-07', NULL, '', '2400.00', '\0', '1', NULL, NULL, '1', '201', '143', NULL, '0', '3', '0'),
         ('86', '2014-05-20', 'JFDS5A', '0', '29000.00', '2015-11-19', NULL, NULL, '14500.00', '\0', '1', NULL, NULL, '2', '147', '89', NULL, '0', '6', '0');

INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`, `contract_payment_amount`, `contract_payment_type`, `doubt_record`, `virtual_account_unique_id`, `bill_life_cycle`, `collection_bill_life_cycle`, `collection_bill_capital_status`, `asset_package_id`, `collection_bill_service_notice_status`, `collection_bill_frozen_status`, `factoring_contract_id`, `audit_bill_id`)
VALUES 
         ('373', '2015-04-05', '2015-05-04', NULL, 'CC-60334-2', '0', '0.00', NULL, '\0', '2015-04-05', '1600.00', '79', '140', '0', NULL, '9dfca839-5d8e-419d-8e9c-50fad3683b9e', '1', NULL, 'xiaoyu_CC-60334-2_373', NULL, NULL, NULL, 'ed8cb46d-525b-47e4-abae-419bae28dc61', '1', '1', '4', '-1', '1', '0', '-1', NULL),
         ('374', '2015-05-05', '2015-06-04', NULL, 'CC-60334-3', '0', '0.00', NULL, '\0', '2015-05-05', '1600.00', '79', '140', '0', NULL, '9dfca839-5d8e-419d-8e9c-50fad3683b9e', '1', NULL, 'xiaoyu_CC-60334-3_374', NULL, NULL, NULL, 'ed8cb46d-525b-47e4-abae-419bae28dc61', '1', '1', '4', '-1', '1', '0', '-1', NULL),
         ('375', '2015-06-05', '2015-07-04', NULL, 'CC-60334-4', '0', '0.00', NULL, '\0', '2015-06-05', '1600.00', '79', '140', '0', NULL, '9dfca839-5d8e-419d-8e9c-50fad3683b9e', '1', NULL, 'xiaoyu_CC-60334-4_375', NULL, NULL, NULL, 'ed8cb46d-525b-47e4-abae-419bae28dc61', '1', '1', '4', '-1', '1', '0', '-1', NULL),

         ('8420', '2015-12-15', NULL, NULL, 'CC-63848-2', '2', '1400.00', '2015-12-08 17:11:00', '\0', NULL, '1400.00', '1274', '1335', '0', NULL, NULL, '0', NULL, 'xiaoyu_CC-63848-2_8420', '1400.00', '1', NULL, NULL, '2', '3', '3', '829', '1', '0', '61', '9068d6d6-46ab-4771-9ee8-decb1e4a47c5'),
         ('8421', '2016-02-15', NULL, NULL, 'CC-63848-3', '0', NULL, NULL, '\0', NULL, '1400.00', '1274', '1335', '0', NULL, NULL, '0', NULL, 'xiaoyu_CC-63848-3_8421', '1400.00', '1', NULL, NULL, '1', '1', '4', '829', '1', '0', '61', '148b1b8f-88bf-408e-9121-a716e51fade2'),
         ('8422', '2016-04-15', NULL, NULL, 'CC-63848-4', '0', NULL, NULL, '\0', NULL, '1400.00', '1274', '1335', '0', NULL, NULL, '0', NULL, 'xiaoyu_CC-63848-4_8422', '1400.00', '1', NULL, NULL, '1', '1', '4', '829', '1', '0', '61', 'fb683545-a5f0-48ec-a8f8-8f573a6151d1'),
         ('8423', '2016-06-15', NULL, NULL, 'CC-63848-5', '0', NULL, NULL, '\0', NULL, '1400.00', '1274', '1335', '0', NULL, NULL, '0', NULL, 'xiaoyu_CC-63848-5_8423', '1400.00', '1', NULL, NULL, '1', '1', '4', '829', '1', '0', '61', 'd5bece34-309e-4f41-9fcf-6649bfa595c8'),
         ('8424', '2016-08-15', NULL, NULL, 'CC-63848-6', '0', NULL, NULL, '\0', NULL, '1400.00', '1274', '1335', '0', NULL, NULL, '0', NULL, 'xiaoyu_CC-63848-6_8424', '1400.00', '1', NULL, NULL, '1', '1', '4', '829', '1', '0', '61', '509d95be-ec37-4739-82f1-1f74e0a052a5'),
('967', '2015-06-08', NULL, NULL, 'CC-60537-3', '2', '2400.00', '2015-06-24 15:30:49', '\0', NULL, '2400.00', '140', '201', '1', NULL, '9dfca839-5d8e-419d-8e9c-50fad3683b9e', '0', '2015-06-24 15:30:49', 'xiaoyu_CC-60537-3_967', NULL, NULL, NULL, 'ed8cb46d-525b-47e4-abae-419bae28dc61', '3', '1', '3', '105', '0', '0', '61', NULL),
('968', '2015-07-08', NULL, NULL, 'CC-60537-4', '2', '2400.00', '2015-07-07 17:46:44', '\0', NULL, '2400.00', '140', '201', '0', NULL, '9dfca839-5d8e-419d-8e9c-50fad3683b9e', '0', NULL, 'xiaoyu_CC-60537-4_968', NULL, NULL, NULL, 'ed8cb46d-525b-47e4-abae-419bae28dc61', '3', '1', '3', '105', '0', '0', '61', NULL),
 ('969', '2015-08-08', NULL, NULL, 'CC-60537-5', '2', '2400.00', '2015-08-05 18:13:04', '\0', NULL, '2400.00', '140', '201', '0', NULL, '9dfca839-5d8e-419d-8e9c-50fad3683b9e', '0', NULL, 'xiaoyu_CC-60537-5_969', NULL, NULL, NULL, 'ed8cb46d-525b-47e4-abae-419bae28dc61', '3', '1', '3', '105', '0', '0', '61', NULL),
('970', '2015-09-08', NULL, NULL, 'CC-60537-6', '2', '2400.00', '2015-09-06 16:59:13', '\0', NULL, '2400.00', '140', '201', '0', NULL, '9dfca839-5d8e-419d-8e9c-50fad3683b9e', '0', NULL, 'xiaoyu_CC-60537-6_970', NULL, NULL, NULL, 'ed8cb46d-525b-47e4-abae-419bae28dc61', '3', '1', '3', '105', '0', '0', '61', NULL),
('971', '2015-10-08', NULL, NULL, 'CC-60537-7', '2', '2400.00', '2015-10-08 17:33:52', '\0', NULL, '2400.00', '140', '201', '0', NULL, '9dfca839-5d8e-419d-8e9c-50fad3683b9e', '0', NULL, 'xiaoyu_CC-60537-7_971', NULL, NULL, NULL, 'ed8cb46d-525b-47e4-abae-419bae28dc61', '3', '1', '3', '105', '0', '0', '61', NULL),
('972', '2015-11-08', NULL, NULL, 'CC-60537-8', '2', '2400.00', '2015-11-10 16:55:00', '\0', NULL, '2400.00', '140', '201', '0', NULL, '9dfca839-5d8e-419d-8e9c-50fad3683b9e', '1', NULL, 'xiaoyu_CC-60537-8_972', NULL, NULL, NULL, 'ed8cb46d-525b-47e4-abae-419bae28dc61', '2', '3', '3', '105', '1', '0', '61', NULL),
 ('973', '2015-12-08', NULL, NULL, 'CC-60537-9', '0', '0.00', NULL, '\0', NULL, '2400.00', '140', '201', '0', NULL, '9dfca839-5d8e-419d-8e9c-50fad3683b9e', '1', NULL, 'xiaoyu_CC-60537-9_973', NULL, NULL, NULL, 'ed8cb46d-525b-47e4-abae-419bae28dc61', '1', '1', '4', '105', '1', '0', '61', NULL),

 ('7079', '2015-11-02', NULL, NULL, 'CC-60943-4', '2', '8000.00', '2015-11-02 16:52:00', '\0', NULL, '8000.00', '1007', '1067', '0', NULL, NULL, '0', NULL, 'xiaoyu_CC-60943-4_7079', '8000.00', '1', NULL, NULL, '2', '3', '3', '584', '1', '0', '61', NULL),
 ('7080', '2016-01-02', NULL, NULL, 'CC-60943-5', '0', '0.00', NULL, '\0', NULL, '8000.00', '1007', '1067', '0', NULL, NULL, '0', NULL, 'xiaoyu_CC-60943-5_7080', '8000.00', '1', NULL, NULL, '1', '1', '4', '584', '1', '0', '61', NULL),
('7081', '2016-03-02', NULL, NULL, 'CC-60943-6', '0', NULL, NULL, '\0', NULL, '8000.00', '1007', '1067', '0', NULL, NULL, '0', NULL, 'xiaoyu_CC-60943-6_7081', '8000.00', '1', NULL, NULL, '1', '1', '4', '584', '1', '0', '61', NULL),
('7082', '2016-05-02', NULL, NULL, 'CC-60943-7', '0', NULL, NULL, '\0', NULL, '8000.00', '1007', '1067', '0', NULL, NULL, '0', NULL, 'xiaoyu_CC-60943-7_7082', '8000.00', '1', NULL, NULL, '1', '1', '4', '584', '1', '0', '61', NULL),
('7083', '2016-07-02', NULL, NULL, 'CC-60943-8', '0', NULL, NULL, '\0', NULL, '8000.00', '1007', '1067', '0', NULL, NULL, '0', NULL, 'xiaoyu_CC-60943-8_7083', '8000.00', '1', NULL, NULL, '1', '1', '4', '584', '1', '0', '61', NULL),
('7084', '2016-09-02', NULL, NULL, 'CC-60943-9', '0', NULL, NULL, '\0', NULL, '8000.00', '1007', '1067', '0', NULL, NULL, '0', NULL, 'xiaoyu_CC-60943-9_7084', '8000.00', '1', NULL, NULL, '1', '1', '4', '584', '1', '0', '61', NULL);



INSERT INTO `asset_package` (`id`, `adva_start_date`, `is_available`, `bank_account`, `bank_name`, `create_time`, `latest_settle_date`, `otsd_amt`, `otsd_amt_aval`, `rec_transfee_total_amt`, `remove_time`, `contract_id`, `factoring_contract_id`, `thru_date`) 
VALUES 
('829', '2015-11-18 00:00:00', '', NULL, NULL, '2015-11-18 22:33:06', '2015-12-08 17:11:00', '4900.00', '4900.00', '7000.00', NULL, '1274', '61', NULL),
('105', '2015-06-24 00:00:00', '', NULL, NULL, '2015-07-01 20:06:28', '2015-06-24 00:00:00', '16800.00', '16800.00', '24000.00', NULL, '140', '61', NULL),
('584', '2015-10-20 00:00:00', '', NULL, NULL, '2015-10-20 20:16:34', '2015-10-20 00:00:00', '33600.00', '33600.00', '48000.00', NULL, '1007', '61', NULL);
  


INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`)
VALUES 
         ('1335', NULL, NULL, NULL, '杨士胜', NULL, '寓见', '1'),
          ('201', NULL, NULL, NULL, '孙久', NULL, '寓见', '1'),
  ('1067', NULL, NULL, NULL, '张意旻', NULL, '寓见', '1');

INSERT INTO `house` (`id`, `address`, `area`, `bulid_area`, `city`, `community`, `community_detail`, `hall`, `house_status`, `house_type`, `province`, `room`, `app_id`)
VALUES 
         ('1264', '宝山区罗店上海市宝山区陆翔路3489弄13号1001室A房', NULL, '0', NULL, 'SH-600908-A', NULL, '0', '1', '2', NULL, '0', '1'),
          ('143', '闵行区浦江浦雪南路366弄48号402室A房', NULL, '0', NULL, 'SH-600361-A', NULL, '0', '1', '2', NULL, '0', '1'),
('1003', '宝山区顾村苏家浜路435弄18号601室', NULL, '0', NULL, 'SH-600184', NULL, '0', '1', '2', NULL, '0', '1');

INSERT INTO `finance_payment_record` (`id`, `creator_id`, `pay_money`, `payment_no`, `payment_time`, `order_id`, `batch_pay_record_id`, `bank_name`, `card_no`, `payee_name`, `uuid`, `interest_time`, `interest_adjust_note`) 
VALUES 
         ('2520', '9', '1400.00', '1519120843441110015', '2015-12-08 17:11:00', '8420', '489', NULL, NULL, NULL, NULL, '2015-12-08 17:11:00', NULL),
 ('171', '1', '2400.00', '20150624110070101500000002423900', '2015-06-24 15:30:49', '967', '1', NULL, NULL, NULL, NULL, '2015-06-24 15:30:49', NULL),
 ('240', '11', '2400.00', '1584070792984570010', '2015-07-07 17:46:44', '968', '4', NULL, NULL, NULL, NULL, '2015-07-07 17:46:44', NULL),
('504', '9', '2400.00', '1580080584886650006', '2015-08-05 18:13:04', '969', '42', NULL, NULL, NULL, NULL, '2015-08-05 18:13:04', NULL),
 ('899', '12', '2400.00', '1500090668829100016  ', '2015-09-06 16:59:13', '970', '108', NULL, NULL, NULL, NULL, '2015-09-06 16:59:13', NULL),
('1387', '9', '2400.00', '1531100851320650009', '2015-10-08 17:33:52', '971', '214', NULL, NULL, NULL, NULL, '2015-10-08 17:33:52', NULL),
('1961', '9', '2400.00', '1593111023250950011', '2015-11-10 16:55:00', '972', '372', NULL, NULL, NULL, NULL, '2015-11-10 16:55:00', NULL);








SET FOREIGN_KEY_CHECKS=1;
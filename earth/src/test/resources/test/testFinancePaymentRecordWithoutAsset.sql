SET FOREIGN_KEY_CHECKS=0;
delete from finance_payment_record;
delete from app;
delete from rent_order;
delete from contract;
delete from customer;
delete from receive_order_map;
delete from house;
delete from principal;
delete from app_arrive_record;
delete from transaction_record;
delete from transaction_record_log;
delete from `asset_package`;

INSERT INTO `finance_payment_record` (`id`, `creator_id`, `pay_money`, `payment_no`, `payment_time`, `order_id`, `batch_pay_record_id`, `bank_name`, `card_no`, `payee_name`, `uuid`, `interest_time`, `interest_adjust_note`)
VALUES
    ('58', '1', '20800.00', '1', '2015-12-07 18:45:57', '257', NULL, NULL, NULL, NULL, NULL, '2015-12-07 18:45:57', NULL),
    ('77', '1', '13000.00', '1', '2015-12-15 11:48:57', '828', NULL, NULL, NULL, NULL, NULL, '2015-12-15 11:48:57', NULL),
    ('97', '1', '13000.00', '1', '2015-12-15 12:48:57', '373', NULL, NULL, NULL, NULL, NULL, '2015-12-15 12:48:57', NULL),
    ('2442', '9', '8032.67', NULL, '2015-12-04 15:25:00', '8925', '479', NULL, NULL, NULL, NULL, '2015-12-04 15:25:00', NULL);
    
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`, `contract_payment_amount`, `contract_payment_type`, `doubt_record`, `virtual_account_unique_id`, `bill_life_cycle`, `collection_bill_life_cycle`, `collection_bill_capital_status`, `asset_package_id`, `collection_bill_service_notice_status`, `collection_bill_frozen_status`, `factoring_contract_id`, `audit_bill_id`) 
VALUES ('8760', '2015-11-16', '2015-11-16', NULL, 'CC-62649-error', '2', '1550.00', NULL, '\0', '2015-11-16', '1550.00', '220', '1163', '0', NULL, NULL, '0', NULL, 'xiaoyu_CC-62649-error_8760', '1550.00', '1', NULL, NULL, '2', '3', '1', '-1', '1', '0', '-1', NULL),
     ('7471', '2015-11-01', NULL, NULL, 'CC-62749-2', '2', '1500.00', NULL, '\0', NULL, '1500.00', '220', '1169', '0', NULL, NULL, '0', NULL, 'xiaoyu_CC-62749-2_7471', '3000.00', '1', NULL, NULL, '2', '2', '1', '-1', '1', '0', '-1', NULL),
     ('257', '2015-05-13', '2015-06-12', NULL, 'CC-60445-4', '2', '0.10', NULL, '\0', '2015-05-13', '0.10', '220', '125', '0', NULL, '9dfca839-5d8e-419d-8e9c-50fad3683b9e', '1', NULL, 'testForXiaoyu_CC-60445-4_257', NULL, NULL, NULL, '3f16b3e8-98ac-47ae-89a7-61de75f61967', '2', '1', '1', '-1', '1', '0', '-1', NULL),
     ('828', '2015-07-06', NULL, NULL, 'CC-60501-4', '0', '0.00', NULL, '\0', NULL, '800.00', '220', '187', '0', NULL, '9dfca839-5d8e-419d-8e9c-50fad3683b9e', '1', NULL, 'xiaoyu_CC-60501-4_828', NULL, NULL, NULL, 'ed8cb46d-525b-47e4-abae-419bae28dc61', '1', '1', '4', '91', '1', '0', '61', NULL),
     ('373', '2015-04-05', '2015-05-04', NULL, 'CC-60334-2', '0', '0.00', NULL, '\0', '2015-04-05', '1600.00', '220', '140', '0', NULL, '9dfca839-5d8e-419d-8e9c-50fad3683b9e', '1', NULL, 'xiaoyu_CC-60334-2_373', NULL, NULL, NULL, 'ed8cb46d-525b-47e4-abae-419bae28dc61', '1', '1', '4', NULL, '1', '0', '-1', NULL),
     ('8925', '2015-12-04', '2015-12-04', NULL, 'CC-62150-3-repo', '2', '8032.67', '2015-12-04 15:25:00', '\0', '2015-12-04', '8032.67', '1080', '1140', '0', NULL, NULL, '0', NULL, 'xiaoyu_CC-62150-3_7340', '8032.67', '1', NULL, NULL, '3', '3', '3', '657', '1', '0', '61', NULL);

     
INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`, `transfer_status`, `contract_life_cycle`, `contract_type`) 
VALUES 
      ('220', '2015-06-01', 'pre_payment_contract_no', '0', '0.00', '2016-06-01', NULL, NULL, '100000000.00', '\0', '1', NULL, NULL, '1', '-1', '220', '0', '3', '1'),
      ('1080', '2015-07-26', 'CC-62150', '0', '0.00', '2016-07-25', NULL, '', '1400.00', '\0', '2', NULL, NULL, '1', '1140', '1076', '0', '1', '0');

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `addressee`, `payee_account_name`, `payee_account_no`, `payee_bank_name`) VALUES ('1', 'xiaoyu', '70991db75ebb24fa0993f4fa25775022', '\0', 'http://beta.demo2do.com/jupiter/', '寓见', '5', NULL, NULL, NULL, NULL);

INSERT INTO `principal` (`id`, `authority`, `name`, `password`, `start_date`, `thru_date`) 
VALUES
      ('37', 'ROLE_SUPER_USER', NULL, 'zhenghangbo', 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL);

INSERT INTO `asset_package` (`id`, `adva_start_date`, `is_available`, `bank_account`, `bank_name`, `create_time`, `latest_settle_date`, `otsd_amt`, `otsd_amt_aval`, `rec_transfee_total_amt`, `remove_time`, `contract_id`, `factoring_contract_id`, `thru_date`) 
VALUES 
       ('91', '2015-06-02 00:00:00', '\0', NULL, NULL, '2015-07-01 20:06:27', '2015-06-02 00:00:00', '5600.00', '5600.00', '8000.00', NULL, '126', '61', NULL),
       ('657', '2015-10-20 00:00:00', '', NULL, NULL, '2015-10-20 20:16:40', '2015-10-20 00:00:00', '7840.00', '7840.00', '11200.00', NULL, '1080', '61', '2015-12-04 16:46:45');

INSERT INTO `transaction_record` (`id`, `card_no`, `channel`, `last_modified_time`, `merchant_payment_no`, `order_no`, `pay_money`, `request_no`, `trade_no`, `transaction_record_status`, `app_id`, `payment_institution_id`, `transaction_clearing_status`, `unique_transaction_id`) VALUES ('3164', NULL, '1', '2015-12-04 15:25:00', NULL, 'CC-62150-3-repo', '8032.67', NULL, NULL, '2', '1', '1', '1', NULL);

INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`) 
VALUES 
      ('1140', NULL, NULL, NULL, '章德生', NULL, '寓见', '1');
INSERT INTO  `house` (`id`, `address`, `area`, `bulid_area`, `city`, `community`, `community_detail`, `hall`, `house_status`, `house_type`, `province`, `room`, `app_id`) 
VALUES 
      ('1076', '宝山区顾村顾北东路155弄195号202室', NULL, '0', NULL, 'SH-600643-A', NULL, '0', '1', '2', NULL, '0', '1');


SET FOREIGN_KEY_CHECKS=1;
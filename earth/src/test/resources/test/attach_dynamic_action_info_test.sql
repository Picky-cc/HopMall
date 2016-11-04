SET FOREIGN_KEY_CHECKS=0;

delete from factoring_contract;
delete from contract;
delete from finance_payment_record;
delete from asset_package;
delete from charge;
delete from rent_order;

INSERT INTO `factoring_contract` (`id`, `adva_matuterm`, `adva_pct`, `adva_start_date`, `adva_term`, `ar_amt`, `ar_cost_rate`, `ar_deposit_amt`, `ar_deposit_amt_aval`, `ar_deposit_amt_input_days`, `ar_deposit_amt_input_type`, `ar_ldgamt`, `borrower_name`, `contract_no`, `cost_deduct_type`, `deposit_deduct_type`, `int_accuretype`, `int_rate`, `latest_settle_date`, `lender_ac_bk_name`, `lender_ac_no`, `lender_name`, `otsd_amt`, `otsd_amt_aval`, `settle_pct`, `app_id`, `company_id`, `nominal_rate`, `payment_agreement_id`) VALUES
(58, 7, '0.70', '2015-04-02 00:00:00', 0, '11211970.00', '0.00', '0.00', '0.00', 15, 1, '11211970.00', '优帕克', 'DCF-YPK-LR903A', 2, 1, 2, '0.1971432000', NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', '7848379.00', '7848379.00', '1.00', 2, 2, '0.0850000000', 1);

INSERT INTO `asset_package` (`id`, `adva_start_date`, `is_available`, `bank_account`, `bank_name`, `create_time`, `latest_settle_date`, `otsd_amt`, `otsd_amt_aval`, `rec_transfee_total_amt`, `remove_time`, `contract_id`, `factoring_contract_id`) VALUES
(27, '2015-04-10 00:00:00', b'1', NULL, NULL, '2015-04-10 16:04:49', '2015-04-10 00:00:00', '145530.00', '145530.00', '207900.00', NULL, 61, 58);

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`, `transfer_status`) VALUES
(61, '2015-03-23', 'SHIMAO1#45H', 0, '0.00', '2016-03-22', NULL, NULL, '18900.00', b'0', 1, NULL, NULL, 2, 122, 64, 0);

INSERT INTO `finance_payment_record` (`id`, `creator_id`, `pay_money`, `payment_no`, `payment_time`, `order_id`, `batch_pay_record_id`, `bank_name`, `card_no`, `payee_name`,`interest_time`,`interest_adjust_note`) VALUES
(17, -1, '18900.00', '0', '2015-04-10 16:55:00', 222, NULL, NULL, NULL, NULL,'2015-04-10 16:55:00',null),
(38, 1, '18900.00', '1', '2015-04-30 12:18:55', 223, NULL, NULL, NULL, NULL,'2015-04-30 12:18:55',null),
(91, 1, '18900.00', '1', '2015-05-25 16:38:01', 224, NULL, NULL, NULL, NULL,'2015-05-25 16:38:01',null);

INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`, `contract_payment_amount`, `contract_payment_type`, `doubt_record`, `virtual_account_unique_id`) VALUES
(222, '2015-04-23', '2015-05-22', NULL, 'SHIMAO1#45H-20150408-2', 1, '18900.00', '2015-04-10 16:55:00', b'0', '2015-04-23', '18900.00', 61, 122, 1, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, NULL, NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a'),
(223, '2015-05-23', '2015-06-22', NULL, 'SHIMAO1#45H-20150408-3', 1, '18900.00', '2015-04-30 12:18:55', b'0', '2015-05-23', '18900.00', 61, 122, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, NULL, NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a'),
(224, '2015-06-23', '2015-07-22', NULL, 'SHIMAO1#45H-20150408-4', 1, '18900.00', '2015-05-25 16:38:01', b'0', '2015-06-23', '18900.00', 61, 122, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, NULL, NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a'),
(232, '2016-02-23', '2016-03-22', NULL, 'SHIMAO1#45H-20150408-12', 0, '0.00', NULL, b'0', '2016-02-23', '18900.00', 61, 122, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, NULL, NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a');

INSERT INTO `charge` (`id`, `factoring_contract_id`, `finance_payment_record_id`, `pay_money`, `payment_time`, `remark`, `batch_no`, `charge_status`, `modify_time`) VALUES
(11, 58, 17, '18900.00', '2015-04-10 16:55:00', '', NULL, 2, '2015-04-10 16:55:00');

SET FOREIGN_KEY_CHECKS=1;
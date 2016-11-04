SET FOREIGN_KEY_CHECKS=0;

delete   from  `app`;
delete   from  `asset_package`;
delete   from  `factoring_contract`; 


INSERT INTO `asset_package` (`id`, `adva_start_date`, `is_available`, `bank_account`, `bank_name`, `create_time`, `latest_settle_date`, `otsd_amt`, `otsd_amt_aval`, `rec_transfee_total_amt`, `remove_time`, `contract_id`, `factoring_contract_id`, `thru_date`) VALUES
(13, '2015-04-03 00:00:00', b'1', NULL, NULL, '2015-04-02 00:00:00', '2015-04-03 00:00:00', '138600.00', '138600.00', '198000.00', NULL, 13, 58,null),
(842, '2015-11-18 00:00:00', b'1', NULL, NULL, '2015-11-18 22:33:07', '2015-11-18 00:00:00', '18480.00', '18480.00', '26400.00', NULL, 1287, 61,null),
(206, '2015-07-31 10:00:01', b'1', NULL, NULL, '2015-07-03 19:18:54', '2015-07-31 00:00:00', '8960.00', '8960.00', '12800.00', NULL, 244, 61, null),
(204, '2015-07-01 10:00:00', b'1', NULL, NULL, '2015-07-03 19:18:54', '2015-07-21 00:00:00', '8960.00', '8960.00', '12800.00', NULL, 242, 61,null),
(205, '2015-08_01 00:00:00', b'1', NULL, NULL, '2015-07-03 19:18:54', '2015-07-31 00:00:00', '8960.00', '8960.00', '12800.00', NULL, 244, 61, null);

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `payee_account_name`, `payee_account_no`, `payee_bank_name`) VALUES
(2, 'youpark', '123456', b'0', '', '优帕克', 4, NULL, NULL, NULL),
(1, 'xiaoyu', '70991db75ebb24fa0993f4fa25775022', b'0', 'http://beta.demo2do.com/jupiter/', '寓见', 5, NULL, NULL, NULL);



INSERT INTO `factoring_contract` (`id`, `adva_matuterm`, `adva_pct`, `adva_start_date`, `adva_term`, `ar_amt`, `ar_cost_rate`, `ar_deposit_amt`, `ar_deposit_amt_aval`, `ar_deposit_amt_input_days`, `ar_deposit_amt_input_type`, `ar_ldgamt`, `borrower_name`, `contract_no`, `cost_deduct_type`, `deposit_deduct_type`, `int_accuretype`, `int_rate`, `latest_settle_date`, `lender_ac_bk_name`, `lender_ac_no`, `lender_name`, `otsd_amt`, `otsd_amt_aval`, `settle_pct`, `app_id`, `company_id`, `nominal_rate`, `payment_agreement_id`) VALUES
(58, 7, '0.70', '2015-04-02 00:00:00', 0, '11211970.00', '0.00', '0.00', '0.00', 15, 1, '11211970.00', '优帕克', 'DCF-YPK-LR903A', 2, 1, 2, '0.1971432000', NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', '7848379.00', '7848379.00', '1.00', 2, 2, '0.0850000000', 1),
(61, 7, '0.70', '2015-06-02 21:47:05', 0, '0.00', '0.00', '0.00', '0.00', 0, 1, '0.00', '寓见', 'DCF-XY-FZR905A', 2, 2, 2, '0.1966003200', NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', '0.00', '0.00', '1.00', 1, 2, '0.0850000000', 2);




SET FOREIGN_KEY_CHECKS=1;

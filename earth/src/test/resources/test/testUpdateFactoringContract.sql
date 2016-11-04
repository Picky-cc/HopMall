SET FOREIGN_KEY_CHECKS=0;
delete from `factoring_contract`;


INSERT INTO `factoring_contract` (`id`, `adva_matuterm`, `adva_pct`, `adva_start_date`, `adva_term`, `ar_amt`, `ar_cost_rate`, `ar_deposit_amt`, `ar_deposit_amt_aval`, `ar_deposit_amt_input_days`, `ar_deposit_amt_input_type`, `ar_ldgamt`, `borrower_name`, `contract_no`, `cost_deduct_type`, `deposit_deduct_type`, `int_accuretype`, `int_rate`, `latest_settle_date`, `lender_ac_bk_name`, `lender_ac_no`, `lender_name`, `otsd_amt`, `otsd_amt_aval`, `settle_pct`, `app_id`, `company_id`, `nominal_rate`, `payment_agreement_id`, `deposit_rate`, `financing_verification_ways`, `adva_repo_term`, `is_buffer_repayment`, `thru_date`) VALUES ('58', '6', '0.75', '2015-04-02 00:00:00', '0', '11211970.00', '0.00', '0.00', '0.00', '15', '0', '11211970.00', '优帕克', 'DCF-YPK-LR903A', '2', '1', '2', '0.1971432000', NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', '7848379.00', '7848379.00', '0.10', '2', '2', '0.0850000000', '1', '0.3000000000', '0', '5', '0', '2016-01-15 14:55:20');
INSERT INTO `factoring_contract` (`id`, `adva_matuterm`, `adva_pct`, `adva_start_date`, `adva_term`, `ar_amt`, `ar_cost_rate`, `ar_deposit_amt`, `ar_deposit_amt_aval`, `ar_deposit_amt_input_days`, `ar_deposit_amt_input_type`, `ar_ldgamt`, `borrower_name`, `contract_no`, `cost_deduct_type`, `deposit_deduct_type`, `int_accuretype`, `int_rate`, `latest_settle_date`, `lender_ac_bk_name`, `lender_ac_no`, `lender_name`, `otsd_amt`, `otsd_amt_aval`, `settle_pct`, `app_id`, `company_id`, `nominal_rate`, `payment_agreement_id`, `deposit_rate`, `financing_verification_ways`, `adva_repo_term`, `is_buffer_repayment`, `thru_date`) VALUES ('61', '10', '0.70', '2015-06-02 21:47:05', '0', '0.00', '0.00', '0.00', '0.00', '0', '0', '0.00', '寓见', 'DCF-XY-FZR905A', '2', '2', '2', '0.1966003200', NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', '0.00', '0.00', '1.00', '1', '2', '0.0850000000', '2', '0.1000000000', '0', '1', '0', '2016-01-28 13:55:35');




SET FOREIGN_KEY_CHECKS=1;
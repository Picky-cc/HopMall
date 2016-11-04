SET FOREIGN_KEY_CHECKS=0;

delete from contract;
delete from contract_account;
delete from update_repayment_information_log;


INSERT INTO `contract` (`id`, `unique_id`, `begin_date`, `contract_no`, `end_date`, `asset_type`, `month_fee`, `app_id`, `customer_id`, `house_id`, `actual_end_date`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`) 
VALUES 
('323', '12345678', '2016-04-17', '云信信2016-78-DK(ZQ2016042522479)', NULL, '1', '0.00', '1', '337', '337', NULL, '2016-06-15 22:04:59', '0.1560000000', '0', '0', '3', '2', '1200.00', '0.0005000000');

INSERT INTO `contract_account` (`id`, `pay_ac_no`, `bankcard_type`, `contract_id`, `payer_name`, `bank`, `bind_id`, `id_card_num`, `bank_code`, `province`, `city`, `from_date`, `thru_date`) 
VALUES 
('323', '6217000000000003006', NULL, '323', '测试用户1', '中国邮政储蓄银行', NULL, NULL, '403', '安徽省', '亳州', '2016-07-13 10:03:25', '2016-07-13 10:21:26'),
('348', 'bankAccount', NULL, '323', '测试用户1', '中国邮政储蓄银行', NULL, NULL, '604', '安徽省', '亳州', '2016-07-13 10:21:26', '2900-01-01 00:00:00');


SET FOREIGN_KEY_CHECKS=1;
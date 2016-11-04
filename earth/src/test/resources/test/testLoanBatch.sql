SET FOREIGN_KEY_CHECKS=0;

delete from contract;
delete from asset_package;
delete from loan_batch;
delete from asset_set;
delete from principal;

INSERT INTO `loan_batch` (`id`, `is_available`, `code`, `create_time`, `financial_contract_id`, `last_modified_time`, `loan_date`) 
VALUES 
('5', '', 'DCF-NFQ-LR903A 20160511 18:09:322', '2016-05-11 18:09:39', '2', NULL, NULL);

INSERT INTO `asset_package` (`id`, `is_available`, `create_time`, `contract_id`, `asset_package_no`, `financial_contract_id`, `loan_batch_id`)
VALUES 
('1285', 0, '2016-05-11 18:09:40','1750', 'asset_package_no', '2', '5');


INSERT INTO `contract` (`id`, `asset_type`, `begin_date`, `contract_no`, `month_fee`, `app_id`, `customer_id`, `house_id`, `create_time`, `interest_rate`, `payment_day_in_month`, `payment_frequency`, `periods`, `repayment_way`, `total_amount`, `penalty_interest`) 
VALUES 
('1750', '1', '2016-04-27', 'ZQ2016042422406', '15976.15', '1', '1514', '1440', '2016-03-28 11:20:28', '0.1500000000', '0', '1', '20', '0', '100000.00', '0.0005000000');

INSERT INTO `principal` (`id`, `authority`, `name`, `password`, `start_date`, `thru_date`) VALUES 
('1', 'ROLE_SUPER_USER', 'zhushiyun', 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL);


SET FOREIGN_KEY_CHECKS=1;
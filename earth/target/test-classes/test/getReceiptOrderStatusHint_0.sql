SET FOREIGN_KEY_CHECKS=0;
delete from contract;
delete from rent_order;
delete from transaction_record;

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `end_date`, `month_fee`, `payment_instrument`, `url`, `customer_id`, `house_id`, `app_id`,`non_deposit_guarantee_way`) VALUES
	(3, '2015-02-02', '10120212', 0, '2015-09-02', 3000.00, 1, NULL, 4, 3, 1,b'0');


INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`) VALUES
	(3, '2015-03-31', '2015-03-31', 0.00, '0003', 1, 0.00, NULL, b'0', '2015-01-01', 9000.00, 3, 4);

INSERT INTO `transaction_record` (`id`, `card_no`, `channel`, `last_modified_time`, `merchant_payment_no`, `order_no`, `pay_money`, `request_no`, `trade_no`, `transaction_record_status`, `app_id`, `payment_institution_id`) VALUES
	(1, NULL, 1, '2015-02-03 14:16:16', 'a123', '0003', 8999.96, '201502030213150003', '1502031413659412', 2, 1, 1),
	(2, NULL, 1, '2015-02-03 14:43:09', 'b123', '0003', 0.01, '201502030241430003', '1502031441743942', 2, 1, 1),
	(3, NULL, 1, '2015-02-03 14:58:36', 'c123', '0003', 0.01, '201502030258260003', '1502031458793482', 2, 1, 1),
	(4, NULL, 1, '2015-02-03 15:16:10', 'd123', '0003', 0.01, '201502030314430003', '1502031514836352', 2, 1, 1),
	(5, NULL, 1, '2015-02-03 15:59:14', 'e123', '0003', 0.01, '201502030357370003', '1502031557965672', 1, 1, 1);
SET FOREIGN_KEY_CHECKS=1;

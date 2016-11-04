SET FOREIGN_KEY_CHECKS=0;

delete from `rent_order`;
delete from `contract`;

INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`, `contract_payment_amount`, `contract_payment_type`,`collection_bill_capital_status`)
VALUES
	(3135,'2015-07-06',NULL,NULL,'CTHY77#2502-20150724-3',2,7300.00,'2015-07-24 13:40:33',00000000,NULL,7300.00,431,491,1,NULL,NULL,0,NULL,NULL,0.00,0,'3'),
	(3136,'2015-08-06',NULL,NULL,'CTHY77#2502-20150724-4',2,7300.00,'2015-08-06 17:43:45',00000000,NULL,7300.00,431,491,0,NULL,NULL,0,NULL,NULL,21900.00,1,'3'),
	(3137,'2015-09-06',NULL,NULL,'CTHY77#2502-20150724-5',0,NULL,NULL,00000000,NULL,7300.00,431,491,0,NULL,NULL,0,NULL,NULL,0.00,0,'0'),
	(3138,'2015-10-06',NULL,NULL,'CTHY77#2502-20150724-6',0,NULL,NULL,00000000,NULL,7300.00,431,491,0,NULL,NULL,0,NULL,NULL,0.00,0,'0'),
	(3139,'2015-11-06',NULL,NULL,'CTHY77#2502-20150724-7',0,NULL,NULL,00000000,NULL,7300.00,431,491,0,NULL,NULL,0,NULL,NULL,21900.00,1,'0'),
	(3140,'2015-12-06',NULL,NULL,'CTHY77#2502-20150724-8',0,NULL,NULL,00000000,NULL,7300.00,431,491,0,NULL,NULL,0,NULL,NULL,0.00,0,'0'),
	(3141,'2016-01-06',NULL,NULL,'CTHY77#2502-20150724-9',0,NULL,NULL,00000000,NULL,7300.00,431,491,0,NULL,NULL,0,NULL,NULL,0.00,0,'0'),
	(3142,'2016-02-06',NULL,NULL,'CTHY77#2502-20150724-10',0,NULL,NULL,00000000,NULL,7300.00,431,491,0,NULL,NULL,0,NULL,NULL,21900.00,1,'0'),
	(3143,'2016-03-06',NULL,NULL,'CTHY77#2502-20150724-11',0,NULL,NULL,00000000,NULL,7300.00,431,491,0,NULL,NULL,0,NULL,NULL,0.00,0,'0'),
	(3144,'2016-04-06',NULL,NULL,'CTHY77#2502-20150724-12',0,NULL,NULL,00000000,NULL,7300.00,431,491,0,NULL,NULL,0,NULL,NULL,0.00,0,'0');

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`, `transfer_status`) VALUES
	(431, '2015-05-06', 'CTHY77#2502-2015-07-24', 0, '7300.00', '2016-05-05', NULL, '', '7300.00', b'0', 3, NULL, NULL, 2, 491, 433, 0);
	
SET FOREIGN_KEY_CHECKS=1;

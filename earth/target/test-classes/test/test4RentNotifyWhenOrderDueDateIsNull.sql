set foreign_key_checks = 0;

delete from `rent_order`;
delete from `customer`;
delete from `contract`;
delete from `app`;
delete from `app_sms_config`;


INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`)
VALUES
	(3696, NULL, NULL, NULL, 'test20150728-11', 0, 0.00, NULL, 00000000, NULL, 100.00, 490, 550, NULL, NULL, NULL, 0, NULL, 'test4Zufangbao_test20150728-11_3696');

INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`)
VALUES
	(550, NULL, NULL, '', 'test20150728', NULL, '租房宝测试账号', 3);

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`, `transfer_status`)
VALUES
	(490, NULL, 'test20150728', 0, 100.00, NULL, NULL, NULL, 100.00, 00000000, 0, NULL, NULL, 3, 550, 492, 0);

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `payee_account_name`, `payee_account_no`, `payee_bank_name`)
VALUES
	(3, 'test4Zufangbao', '2e85ae999845f25faf8ea7b514ee0aca', 00000000, 'http://e.zufangbao.cn', '租房宝测试账号', 9, NULL, NULL, NULL);

INSERT INTO `app_sms_config` (`id`, `is_deleted`, `sms_rule_suit`, `app_id`, `is_need_link`, `default_sms_rule`)
VALUES
	(8, 00000000, '[{\"contentTemplate\":\"(##appName##)您当期房租##amount##元已到支付日期，请及时完成支付！请点击链接[##link##]【随地付】\",\"ruleScript\":\"function execute(order){return (order.getDistanceFromTodayToDueDay() == 0);}\",\"ruleUniqueId\":\"c99c94d7-74d9-4f68-a845-d1f7e7ea48f6\"}]', 3, 00000001, '{\"contentTemplate\":\"(##appName##)您当期房租##amount##元已到支付日期，请及时完成支付！请点击链接[##link##]【随地付】\",\"ruleScript\":\"function execute(order){return true;}\",\"ruleUniqueId\":\"936c9600-d26f-45fa-b42e-cab4fd492497\"}');

	
set foreign_key_checks = 1;

SET FOREIGN_KEY_CHECKS=0;

delete from transaction_record;
delete from app;
delete from rent_order;
delete from payment_institution;
delete from transaction_record_log;
INSERT INTO `transaction_record` (`id`, `card_no`, `channel`, `last_modified_time`, `merchant_payment_no`, `order_no`, `pay_money`, `request_no`, `trade_no`, `transaction_record_status`, `app_id`, `payment_institution_id`) VALUES
(498, NULL, 1, '2015-07-06 19:22:05', '618017030329009152', 'CC-61229-3', '1650.00', '20150706072157618017030329009152','20150706000010003400000000', 1, 1, 2),
(499, NULL, 1, '2015-07-06 21:57:19', 'testPayNo4XiaoYu1122', 'XXXX_For_XiaoYu', '0.01', '20150706064710testPayNo4XiaoYu1122', '2015070600001000920070707297', 1, 7, 2),
(500, NULL, 1, '2015-07-06 21:57:29', 'testPayNo4XiaoYu1122', 'XXXX_For_XiaoYu', '0.01', '20150706064930testPayNo4XiaoYu1122', '2015070600001000920070707297', 1, 7, 2),
(501, NULL, 1, '2015-07-06 21:57:30', '618014523175405568', 'CC-61679-3', '0.01', '20150706071159618014523175405568', '2015070600001000340058702828', 2, 1, 2),
(502, NULL, 1, '2015-07-06 21:57:40', '618017030329009152', 'CC-61229-3', '1650.00', '20150706072157618017030329009152','2015070600001000340058702827', 1, 1, 2),
(503, NULL, 1, '2015-07-06 21:57:50', '618014523175405568', 'CC-61679-3', '0.01', '20150706071159618014523175405568', null, 1, 1, 2),
(504, NULL, 1, '2015-07-06 21:57:51', '618014523172222568', 'CC-61679-3', '0.01', '201507060711596180145231711111', null, 1, 1, 2);

INSERT INTO `transaction_record_log` (`id`, `modified_time`, `operator_id`, `transaction_record_operate_status`, `transaction_record_id`) VALUES
(5, '2015-04-13 19:27:02', 130, 0, 498),
(6, '2015-04-13 20:16:04', 126, 0, 499),
(7, '2015-04-13 20:17:11', 6, 1, 500),
(8, '2015-04-13 20:19:06', 126, 0, 501),
(9, '2015-04-13 20:19:45', 8, 1, 502),
(10, '2015-04-13 20:24:07', 126, 0, 503),
(11, '2015-04-13 20:24:44', 10, 1, 504);

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`)
VALUES
	(1,'xiaoyu','70991db75ebb24fa0993f4fa25775022',00000000,'http://beta.demo2do.com/jupiter/','寓见',5),
	(2,'youpark','123456',00000000,'','优帕克',4),
	(3,'test4Zufangbao','2e85ae999845f25faf8ea7b514ee0aca',00000000,'http://e.zufangbao.cn','租房宝测试账号',9);


SET FOREIGN_KEY_CHECKS = 1;
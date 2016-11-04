SET FOREIGN_KEY_CHECKS = 0;

delete from `journal_voucher`;
delete from `app_arrive_record`;
delete from `app`;

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`)
VALUES
	(1,'xiaoyu','70991db75ebb24fa0993f4fa25775022',00000000,'http://beta.demo2do.com/jupiter/','寓见',5),
	(2,'youpark','123456',00000000,'','优帕克',4),
	(3,'test4Zufangbao','2e85ae999845f25faf8ea7b514ee0aca',00000000,'http://e.zufangbao.cn','租房宝测试账号',9),
	(4,'zhuke','cb742d55634a532060ac7387caa8d242',00000000,'http://zkroom.com/','杭州驻客网络技术有限公司',6),
	(5,'laoA','744a38b1672b728dc35a68f7a10df082',00000000,'http://www.13980.com','上海元轼信息咨询有限公司',7),
	(6,'keluoba','30f4d225438a7fd1541fe1a055420dfd',00000000,'http://keluoba.com','柯罗芭',8);

INSERT INTO `journal_voucher` (`id`, `booking_amount`, `account_side`, `journal_voucher_uuid`, `completeness`, `status`, `checking_level`, `cash_flow_uuid`, `trade_time`, `cash_flow_serial_no`, `notification_memo`, `notification_identity`, `bank_identity`, `cash_flow_amount`, `source_document_breif`, `notification_record_uuid`, `notified_date`, `source_document_identity`, `source_document_cash_flow_serial_no`, `source_document_amount`, `company_id`, `cash_flow_breif`, `virtual_account_uuid`, `business_voucher_uuid`, `billing_plan_uuid`)
VALUES
	(145, NULL, NULL, '213747a3-ef44-4ed9-89da-c36a0c1625b6', NULL, 0, 1, NULL, NULL, NULL, NULL, NULL, NULL, 100.00, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(146, NULL, NULL, '98792a84-6a27-479d-8c3d-97a08ba1b1df', NULL, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL, 100.32, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(147, NULL, NULL, 'b335c3cd-b77a-46fe-b374-5b17e8886022', NULL, 2, 1, NULL, NULL, NULL, NULL, NULL, NULL, 100.32, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(148, NULL, NULL, 'a7b89c0b-9ee2-455c-9887-7bc2d8764a69', NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, 100.02, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(149, NULL, NULL, 'aee3a38e-d8eb-40f7-8ced-ddfcbc51815b', NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 100.30, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

	
SET FOREIGN_KEY_CHECKS = 1;

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
--	(10, NULL, 1, '941f841c-e68b-464a-b998-60467069d087', 1, NULL, NULL, '9d425d23-8f69-4c72-923e-e48f712bbef4', '2015-10-12 07:25:30', '2015101200001000940062595431', '', '20151012072517653350666284500992', '2088911214323004', 1650.00, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
--	(11, NULL, 1, '6643bd95-6138-441e-99b6-2103d68dddc2', 1, NULL, NULL, '7e5da8a3-29da-4a06-8552-a6f7a2e5c974', '2015-10-12 07:58:01', '2015101200001000860059999879', '服务费[2015101200001000860059999879]', '20151012075715653358723781560320', '2088911214323004', 19.38, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
	(12, NULL, 0, '87264edd-4d05-48cb-829f-974b87e8fbdd', 1, NULL, NULL, '91da0d3f-8598-40f0-922f-e0adb8548373', '2015-10-12 09:39:29', '', '服务费[20151012110070101500000000176283]', '20151012110070101500000000176283', '', 25.00, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

	

SET FOREIGN_KEY_CHECKS = 1;
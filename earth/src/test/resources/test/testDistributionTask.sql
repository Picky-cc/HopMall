SET FOREIGN_KEY_CHECKS=0;

delete from `factoring_contract`;
delete from `payment_agreement`;
delete from `app`;
delete from `company`;
delete from `account`;
delete from `bank`;
delete from `contract`;
delete from `house`;
delete from `customer`;
delete from `asset_package`;
delete from `rent_order`;
delete from `finance_payment_record`;

INSERT INTO `factoring_contract` (`id`, `adva_matuterm`, `adva_pct`, `adva_start_date`, `adva_term`, `ar_amt`, `ar_cost_rate`, `ar_deposit_amt`, `ar_deposit_amt_aval`, `ar_deposit_amt_input_days`, `ar_deposit_amt_input_type`, `ar_ldgamt`, `borrower_name`, `contract_no`, `cost_deduct_type`, `deposit_deduct_type`, `int_accuretype`, `int_rate`, `latest_settle_date`, `lender_ac_bk_name`, `lender_ac_no`, `lender_name`, `otsd_amt`, `otsd_amt_aval`, `settle_pct`, `app_id`, `company_id`, `nominal_rate`, `payment_agreement_id`)
VALUES
	(58, 7, 0.70, '2015-04-02 00:00:00', 0, 11211970.00, 0.00, 0.00, 0.00, 15, 1, 11211970.00, '优帕克', 'DCF-YPK-LR903A', 2, 1, 2, 0.1971432000, NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', 7848379.00, 7848379.00, 1.00, 2, 2, 0.0850000000, 1),
	(61, 7, 0.70, '2015-06-02 21:47:05', 0, 0.00, 0.00, 0.00, 0.00, 0, 1, 0.00, '寓见', 'DCF-XY-FZR905A', 2, 2, 2, 0.1966003200, NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', 0.00, 0.00, 1.00, 1, 2, 0.0850000000, 2),
	(62, 10, 0.70, '2015-06-11 17:32:16', 0, 7092633.60, 0.00, 0.00, 0.00, 0, 1, 7092633.60, '柯罗芭', 'DCF-KLB-JMR904A', 2, 1, 2, 0.1853200000, NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', 4793040.00, 4793040.00, 1.00, 6, 2, 0.0850000000, 5),
	(63, 7, 0.70, '2015-08-27 12:32:16', 0, 6.00, 0.00, 0.00, 0.00, 15, 1, 6.00, '安心公寓', 'DCF-RX-FR906A', 2, 1, 2, 0.1966003200, NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', 20.00, 20.00, 1.00, 10, 2, 0.0850000000, 3),
	(66, 7, 0.70, '2015-09-07 14:30:16', 0, 0.00, 0.00, 0.00, 0.00, 15, 1, 0.00, '源涞国际', 'DCF-YL-FR907A', 2, 1, 2, 0.1966003200, NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', 0.00, 0.00, 1.00, 11, 2, 0.0850000000, 4),
	(67, 10, 0.70, '2015-10-30 14:40:20', 0, 0.00, 0.00, 0.00, 0.00, 0, 1, 0.00, '汉维仓储', 'DCF-HW-FR908A', 2, 1, 2, 0.1966003200, NULL, NULL, NULL, '鼎程（上海）商业保理有限公司', 0.00, 0.00, 1.00, 12, 2, 0.0850000000, 6);


INSERT INTO `payment_agreement` (`id`, `collection_accounts`, `repayment_account_id`, `risk_reserve_pay_account_id`, `risk_reserve_query_account_id`, `bank_corporate_account_id`, `usb_key_id`)
VALUES
	(1, '[{\"accountName\":\"上海优帕克投资管理有限公司\",\"accountNo\":\"1001300219000027827\",\"alias\":\"优帕克回款户\",\"bank\":{\"bankType\":\"TraditionalBank\",\"code\":\"ICBC\",\"deleted\":false,\"fullName\":\"中国工商银行\",\"id\":1,\"shortName\":\"工商银行\"},\"bankAccount\":true,\"id\":3}]', 1, 2, 2, 5, '0baa43b3-6f77-46ac-81ab-2eb051cd7e76');

	
INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `payee_account_name`, `payee_account_no`, `payee_bank_name`)
VALUES
	(2, 'youpark', '123456', 00000000, '', '优帕克', 4, NULL, NULL, NULL);


INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`, `bank_name`, `card_no`, `name`)
VALUES
	(4, '上海', '上海优帕克投资管理有限公司', '优帕克', NULL, NULL, NULL);

	
INSERT INTO `account` (`id`, `account_name`, `account_no`, `bank_id`, `alias`, `attr`)
VALUES
	(1, '鼎程（上海）商业保理有限公司', '11014671112002', 2, '鼎程回款户', NULL),
	(2, '鼎程（上海）商业保理有限公司', '1001262119204647489', 1, '鼎程－优帕克风险准备金户', NULL),
	(3, '上海优帕克投资管理有限公司', '1001300219000027827', 1, '优帕克回款户', NULL),
	(5, '杭州随地付网络技术有限公司', '1001262119204646188', 1, '随地付', NULL);

	
INSERT INTO `bank` (`id`, `code`, `full_name`, `is_deleted`, `short_name`, `branch_no`, `area_code`, `city_code`, `bank_type`)
VALUES
	(1, 'ICBC', '中国工商银行', 00000000, '工商银行', NULL, NULL, NULL, 0),
	(2, '', '平安银行上海分行营业部', 00000000, '平安银行', NULL, NULL, NULL, 0);

	
INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`, `transfer_status`, `contract_life_cycle`, `contract_type`)
VALUES
	(60, '2014-04-17', 'SHIMAO1#35D', 0, 0.00, '2015-10-16', NULL, NULL, 28500.00, 00000000, 1, NULL, NULL, 2, 121, 63, 0, 6, 0),
	(57, '2014-04-13', 'KXHY9#2601', 0, 38000.00, '2015-10-12', NULL, '', 19500.00, 00000000, 1, NULL, NULL, 2, 118, 60, 0, 6, 0),
	(93, '2015-04-15', 'TSYJY17#903', 0, 20000.00, '2016-04-14', NULL, NULL, 10000.00, 00000000, 1, NULL, NULL, 2, 154, 96, 0, 1, 0);


	
INSERT INTO `house` (`id`, `address`, `area`, `bulid_area`, `city`, `community`, `community_detail`, `hall`, `house_status`, `house_type`, `province`, `room`, `app_id`)
VALUES
	(63, '潍坊西路1弄7号3502室', NULL, 0, NULL, '世茂1#35D', NULL, 0, 1, 2, NULL, 0, 2),
	(60, '汇川路88号9#2601室', NULL, 0, NULL, '凯欣豪苑9#2601', NULL, 0, 1, 1, NULL, 0, 2),
	(96, '威宁路511弄17号903室', NULL, 0, NULL, '天山怡景苑17#903', NULL, 0, 1, 2, NULL, 0, 2);

	
INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`)
VALUES
	(121, NULL, NULL, NULL, 'Michael Charles Madely', NULL, '优帕克', 2),
	(118, NULL, NULL, NULL, '目黑克彦', NULL, '优帕克', 2),
	(154, NULL, NULL, NULL, '上海菱河化工科技有限公司', NULL, '优帕克', 2);

	
INSERT INTO `asset_package` (`id`, `adva_start_date`, `is_available`, `bank_account`, `bank_name`, `create_time`, `latest_settle_date`, `otsd_amt`, `otsd_amt_aval`, `rec_transfee_total_amt`, `remove_time`, `contract_id`, `factoring_contract_id`, `thru_date`)
VALUES
	(58, '2015-04-21 00:00:00', 00000001, NULL, NULL, '2015-04-21 13:49:04', '2015-04-21 00:00:00', 84000.00, 84000.00, 120000.00, NULL, 93, 58, NULL),
	(26, '2015-04-10 00:00:00', 00000001, NULL, NULL, '2015-04-10 15:58:02', '2015-04-10 00:00:00', 119700.00, 119700.00, 171000.00, NULL, 60, 58, NULL),
	(23, '2015-04-03 00:00:00', 00000001, NULL, NULL, NULL, '2015-04-03 00:00:00', 81900.00, 81900.00, 117000.00, NULL, 57, 58, NULL);

	
	
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`, `contract_payment_amount`, `contract_payment_type`, `doubt_record`, `virtual_account_unique_id`, `bill_life_cycle`, `collection_bill_life_cycle`, `collection_bill_capital_status`, `asset_package_id`, `collection_bill_service_notice_status`, `collection_bill_frozen_status`, `factoring_contract_id`, `audit_bill_id`)
VALUES
	(216, '2015-04-17', '2015-05-16', NULL, 'SHIMAO1#35D-20150408-13', 2, 28500.00, '2015-04-10 16:55:00', 00000000, '2015-04-17', 28500.00, 60, 121, 1, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 0, NULL, 'youpark_SHIMAO1#35D-20150408-13_216', NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 3, 1, 3, 26, 0, 0, 58, NULL),
	(217, '2015-05-17', '2015-06-16', NULL, 'SHIMAO1#35D-20150408-14', 2, 28500.00, '2015-05-14 17:04:55', 00000000, '2015-05-17', 28500.00, 60, 121, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 0, NULL, 'youpark_SHIMAO1#35D-20150408-14_217', NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 3, 1, 3, 26, 0, 0, 58, NULL),
	(218, '2015-06-17', '2015-07-16', NULL, 'SHIMAO1#35D-20150408-15', 2, 28500.00, '2015-06-08 17:16:19', 00000000, '2015-06-17', 28500.00, 60, 121, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 0, NULL, 'youpark_SHIMAO1#35D-20150408-15_218', NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 3, 1, 3, 26, 0, 0, 58, NULL),
	(219, '2015-07-17', '2015-08-16', NULL, 'SHIMAO1#35D-20150408-16', 2, 28500.00, '2015-07-13 17:36:18', 00000000, '2015-07-17', 28500.00, 60, 121, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 0, NULL, 'youpark_SHIMAO1#35D-20150408-16_219', NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 3, 1, 3, 26, 0, 0, 58, NULL),
	(220, '2015-08-17', '2015-09-16', NULL, 'SHIMAO1#35D-20150408-17', 2, 28500.00, '2015-08-10 17:02:59', 00000000, '2015-08-17', 28500.00, 60, 121, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 0, NULL, 'youpark_SHIMAO1#35D-20150408-17_220', NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 3, 1, 3, 26, 0, 0, 58, NULL),
	(221, '2015-09-17', '2015-10-16', NULL, 'SHIMAO1#35D-20150408-18', 2, 28500.00, '2015-09-07 16:56:13', 00000000, '2015-09-17', 28500.00, 60, 121, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 0, NULL, 'youpark_SHIMAO1#35D-20150408-18_221', NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 3, 1, 3, 26, 0, 0, 58, NULL),
	(190, '2015-04-13', NULL, NULL, 'KXHY9#2601-20150402-13', 2, 19500.00, '2015-04-03 16:45:24', 00000000, NULL, 19500.00, 57, 118, 1, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 0, NULL, 'youpark_KXHY9#2601-20150402-13_190', NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 3, 1, 3, 23, 0, 0, 58, NULL),
	(191, '2015-05-13', NULL, NULL, 'KXHY9#2601-20150402-14', 2, 19500.00, '2015-05-14 17:34:06', 00000000, NULL, 19500.00, 57, 118, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 0, NULL, 'youpark_KXHY9#2601-20150402-14_191', NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 3, 1, 3, 23, 0, 0, 58, NULL),
	(192, '2015-06-13', NULL, NULL, 'KXHY9#2601-20150402-15', 2, 19500.00, '2015-06-09 17:28:58', 00000000, NULL, 19500.00, 57, 118, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 0, NULL, 'youpark_KXHY9#2601-20150402-15_192', NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 3, 1, 3, 23, 0, 0, 58, NULL),
	(193, '2015-07-13', NULL, NULL, 'KXHY9#2601-20150402-16', 2, 19500.00, '2015-07-10 17:08:13', 00000000, NULL, 19500.00, 57, 118, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 0, NULL, 'youpark_KXHY9#2601-20150402-16_193', NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 3, 1, 3, 23, 0, 0, 58, NULL),
	(194, '2015-08-13', NULL, NULL, 'KXHY9#2601-20150402-17', 2, 19500.00, '2015-08-11 16:34:48', 00000000, NULL, 19500.00, 57, 118, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 0, NULL, 'youpark_KXHY9#2601-20150402-17_194', NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 3, 1, 3, 23, 0, 0, 58, NULL),
	(195, '2015-09-13', NULL, NULL, 'KXHY9#2601-20150402-18', 2, 19500.00, '2015-09-09 17:18:37', 00000000, NULL, 19500.00, 57, 118, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 0, NULL, 'youpark_KXHY9#2601-20150402-18_195', NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 3, 1, 3, 23, 0, 0, 58, NULL),
	(506, '2015-04-15', '2015-05-14', NULL, 'TSYJY17#903-20150421-1', 2, 10000.00, '2015-04-21 14:39:36', 00000000, '2015-04-15', 10000.00, 93, 154, 1, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 0, NULL, 'youpark_TSYJY17#903-20150421-1_506', NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 3, 1, 3, 58, 0, 0, 58, NULL),
	(507, '2015-05-15', '2015-06-14', NULL, 'TSYJY17#903-20150421-2', 2, 10000.00, '2015-05-20 16:46:21', 00000000, '2015-05-15', 10000.00, 93, 154, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 0, NULL, 'youpark_TSYJY17#903-20150421-2_507', NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 3, 1, 3, 58, 0, 0, 58, NULL),
	(508, '2015-06-15', '2015-07-14', NULL, 'TSYJY17#903-20150421-3', 2, 10000.00, '2015-06-16 16:39:13', 00000000, '2015-06-15', 10000.00, 93, 154, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 0, NULL, 'youpark_TSYJY17#903-20150421-3_508', NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 3, 1, 3, 58, 0, 0, 58, NULL),
	(509, '2015-07-15', '2015-08-14', NULL, 'TSYJY17#903-20150421-4', 2, 10000.00, '2015-07-14 17:04:09', 00000000, '2015-07-15', 10000.00, 93, 154, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 0, NULL, 'youpark_TSYJY17#903-20150421-4_509', NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 3, 1, 3, 58, 0, 0, 58, NULL),
	(510, '2015-08-15', '2015-09-14', NULL, 'TSYJY17#903-20150421-5', 2, 10000.00, '2015-08-17 16:50:08', 00000000, '2015-08-15', 10000.00, 93, 154, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 0, NULL, 'youpark_TSYJY17#903-20150421-5_510', NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 3, 1, 3, 58, 0, 0, 58, NULL),
	(511, '2015-09-15', '2015-10-14', NULL, 'TSYJY17#903-20150421-6', 2, 10000.00, '2015-09-28 16:25:57', 00000000, '2015-09-15', 10000.00, 93, 154, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 0, NULL, 'youpark_TSYJY17#903-20150421-6_511', NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 3, 1, 3, 58, 0, 0, 58, NULL),
	(512, '2015-10-15', '2015-11-14', NULL, 'TSYJY17#903-20150421-7', 2, 0.00, NULL, 00000000, '2015-10-15', 10000.00, 93, 154, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, 'youpark_TSYJY17#903-20150421-7_512', NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 1, 1, 4, -1, 1, 0, -1, NULL),
	(513, '2015-11-15', '2015-12-14', NULL, 'TSYJY17#903-20150421-8', 2, 0.00, NULL, 00000000, '2015-11-15', 10000.00, 93, 154, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, 'youpark_TSYJY17#903-20150421-8_513', NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 1, 1, 4, -1, 1, 0, -1, NULL),
	(514, '2015-12-15', '2016-01-14', NULL, 'TSYJY17#903-20150421-9', 2, 0.00, NULL, 00000000, '2015-12-15', 10000.00, 93, 154, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, 'youpark_TSYJY17#903-20150421-9_514', NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 1, 1, 4, -1, 1, 0, -1, NULL),
	(515, '2016-01-15', '2016-02-14', NULL, 'TSYJY17#903-20150421-10', 2, 0.00, NULL, 00000000, '2016-01-15', 10000.00, 93, 154, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, 'youpark_TSYJY17#903-20150421-10_515', NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 1, 1, 4, -1, 1, 0, -1, NULL),
	(516, '2016-02-15', '2016-03-14', NULL, 'TSYJY17#903-20150421-11', 2, 0.00, NULL, 00000000, '2016-02-15', 10000.00, 93, 154, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, 'youpark_TSYJY17#903-20150421-11_516', NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 1, 1, 4, -1, 1, 0, -1, NULL),
	(517, '2016-03-15', '2016-04-14', NULL, 'TSYJY17#903-20150421-12', 2, 0.00, NULL, 00000000, '2016-03-15', 10000.00, 93, 154, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, 'youpark_TSYJY17#903-20150421-12_517', NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 1, 1, 4, -1, 1, 0, -1, NULL),
	(8118, '2015-11-02', '2015-11-02', NULL, 'TSYJY17#903-20150421-7-repo', 2, 40413.09, '2015-11-02 10:56:00', 00000000, '2015-11-02', 40413.09, 93, 154, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, 'youpark_TSYJY17#903-20150421-7_512', 40413.09, 1, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 3, 3, 3, 58, 1, 0, 58, NULL);

	
INSERT INTO `finance_payment_record` (`id`, `creator_id`, `pay_money`, `payment_no`, `payment_time`, `order_id`, `batch_pay_record_id`, `bank_name`, `card_no`, `payee_name`, `uuid`,`interest_time`)
VALUES
	(14, -1, 19500.00, '0', '2015-04-03 16:45:24', 190, NULL, NULL, NULL, NULL, NULL,'2015-04-03 16:45:24'),
	(70, 1, 19500.00, '1', '2015-05-14 17:34:06', 191, NULL, NULL, NULL, NULL, NULL,'2015-05-14 17:34:06'),
	(116, 1, 19500.00, '1', '2015-06-09 17:28:58', 192, NULL, NULL, NULL, NULL, NULL,'2015-06-09 17:28:58'),
	(273, 10, 19500.00, '1', '2015-07-10 17:08:13', 193, NULL, NULL, NULL, NULL, NULL,'2015-07-10 17:08:13'),
	(557, 9, 19500.00, '1', '2015-08-11 16:34:48', 194, NULL, NULL, NULL, NULL, NULL,'2015-08-11 16:34:48'),
	(1006, 9, 19500.00, '1', '2015-09-09 17:18:37', 195, NULL, NULL, NULL, NULL, NULL,'2015-09-09 17:18:37'),
	(16, -1, 28500.00, '0', '2015-04-10 16:55:00', 216, NULL, NULL, NULL, NULL, NULL,'2015-04-10 16:55:00'),
	(69, 1, 28500.00, '1', '2015-05-14 17:04:55', 217, NULL, NULL, NULL, NULL, NULL,'2015-05-14 17:04:55'),
	(115, 1, 28500.00, '1', '2015-06-08 17:16:19', 218, NULL, NULL, NULL, NULL, NULL,'2015-06-08 17:16:19'),
	(276, 10, 28500.00, '1', '2015-07-13 17:36:18', 219, NULL, NULL, NULL, NULL, NULL,'2015-07-13 17:36:18'),
	(551, 9, 28500.00, '1', '2015-08-10 17:02:59', 220, NULL, NULL, NULL, NULL, NULL,'2015-08-10 17:02:59'),
	(921, 9, 28500.00, '1', '2015-09-07 16:56:13', 221, NULL, NULL, NULL, NULL, NULL,'2015-09-07 16:56:13'),
	(31, 1, 10000.00, '1', '2015-04-21 14:39:36', 506, NULL, NULL, NULL, NULL, NULL,'2015-04-21 14:39:36'),
	(83, 1, 10000.00, '1', '2015-05-20 16:46:21', 507, NULL, NULL, NULL, NULL, NULL,'2015-05-20 16:46:21'),
	(134, 1, 10000.00, '1', '2015-06-16 16:39:13', 508, NULL, NULL, NULL, NULL, NULL,'2015-06-16 16:39:13'),
	(289, 10, 10000.00, '1', '2015-07-14 17:04:09', 509, NULL, NULL, NULL, NULL, NULL,'2015-07-14 17:04:09'),
	(618, 9, 10000.00, '1', '2015-08-17 16:50:08', 510, NULL, NULL, NULL, NULL, NULL,'2015-08-17 16:50:08'),
	(1264, 9, 10000.00, NULL, '2015-09-28 16:25:57', 511, 178, NULL, NULL, NULL, NULL,'2015-09-28 16:25:57'),
	(1841, 9, 40413.09, NULL, '2015-11-02 10:56:00', 8118, 340, NULL, NULL, NULL, NULL,'2015-11-02 10:56:00');

	
SET FOREIGN_KEY_CHECKS=1;

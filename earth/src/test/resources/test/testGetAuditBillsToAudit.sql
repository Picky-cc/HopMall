SET FOREIGN_KEY_CHECKS=0;

delete from `app`;
delete from `company`;
delete from `contract`;
delete from `rent_order`;
delete from `app_arrive_record`;
delete from `cash_flow_bill_match`;

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `addressee`, `payee_account_name`, `payee_account_no`, `payee_bank_name`)
VALUES
	(2, 'youpark', '123456', 00000000, '', '优帕克', 4, 'guanzhishi@zufangbao.com;zhangjianming@zufangbao.com;jinyin@zufangbao.com;lixu@zufangbao.com;lucy__ni@163.com;zhaolin@dcfco.cn;xuxiaodong@dcfco.cn;zhouming@dcfco.cn;3115855910@qq.com;jackli@dcfco.cn;zhukai@zufangbao.com', NULL, NULL, NULL),
	(11, 'yuanlai', 'eaaf6bfe5c98e042822b60cae955a276', 00000000, 'http://yuanlai.com', '源涞国际', 14, 'zhaolin@dcfco.cn;xuxiaodong@dcfco.cn;zhouming@dcfco.cn;liangzhiping@dcfco.cn;jackli<jackli@dcfco.cn>;guanzhishi@zufangbao.com;zhangjianming@zufangbao.com;zhukai@zufangbao.com', NULL, NULL, NULL);

INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`, `bank_name`, `card_no`, `name`)
VALUES
	(4, '上海', '上海优帕克投资管理有限公司', '优帕克', NULL, NULL, NULL),
	(14, NULL, '源涞国际', '源涞国际', NULL, NULL, NULL);

	
INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`, `contract_payment_amount`, `contract_payment_type`, `doubt_record`, `virtual_account_unique_id`, `bill_life_cycle`, `collection_bill_life_cycle`, `collection_bill_capital_status`, `asset_package_id`, `collection_bill_service_notice_status`, `collection_bill_frozen_status`, `factoring_contract_id`, `audit_bill_id`, `repayment_audit_status`, `repayment_status`, `repayment_bill_id`)
VALUES
	(1822, '2016-02-04', '2016-03-03', NULL, 'ZTBY16#402-20150703-10', 0, 0.00, NULL, 00000000, '2016-02-04', 10000.00, 225, 285, 0, NULL, '59ee73b1-f54d-416c-b23b-12cdaa660e05', 1, NULL, 'youpark_ZTBY16#402-20150703-10_1822', NULL, NULL, NULL, '47c76809-2f69-4fdb-9a02-04824b9daa1a', 1, 1, 4, 187, 1, 0, 58, 'd026afd5-0005-452a-9d3e-c9f9a0b199ca', 0, 0, '2d9b66a1-15d2-47bc-8056-28fe3445d8cb'),
	(6891, '2016-02-08', NULL, NULL, 'JSBLW19#802-20151009-7', 0, NULL, NULL, 00000000, NULL, 17800.00, 985, 1045, 0, NULL, NULL, 0, NULL, 'youpark_JSBLW19#802-20151009-7_6891', 17800.00, 1, NULL, NULL, 1, 1, 4, 565, 1, 0, 58, 'a62f37ca-1dce-4032-81b8-bcb40be1e0a7', 0, 0, 'c7c10062-ebf5-4452-bae4-4d731d835cba'),
	(8096, '2016-03-07', NULL, NULL, 'C23-20151102-7', 0, NULL, NULL, 00000000, NULL, 23500.00, 1226, 1287, 0, NULL, NULL, 0, NULL, 'yuanlai_C23-20151102-7_8096', 23500.00, 1, NULL, NULL, 1, 1, 4, 784, 1, 0, 66, '13157cd7-55be-473c-8d6a-b813b64b68e2', 0, 0, 'c4f5ee57-7279-497a-8243-03e1b5e0c5d5'),
	(8991, '2016-01-15', NULL, NULL, 'C239-20151207-3', 0, NULL, NULL, 00000000, NULL, 19500.00, 1333, 1394, 0, NULL, NULL, 0, NULL, 'yuanlai_C239-20151207-3_8991', 19500.00, 1, NULL, NULL, 1, 1, 4, 887, 1, 0, 66, '61830e11-0da3-4a9e-8ed9-f2f3f97ebdca', 0, 0, '3c847002-915a-4e2b-bdcf-f79e0c18e072');

	
INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`, `shroff_account_id`, `transfer_status`, `contract_life_cycle`, `contract_type`)
VALUES
	(225, '2015-05-04', 'ZTBY16#402', 0, 20000.00, '2016-05-03', NULL, NULL, 10000.00, 00000000, 1, NULL, NULL, 2, 285, 227, NULL, 0, 3, 0),
	(985, '2015-08-08', 'JSBLW19#802-2015-10-09', 0, 35600.00, '2016-08-07', NULL, '', 17800.00, 00000000, 1, NULL, NULL, 2, 1045, 981, NULL, 0, 3, 0),
	(1226, '2015-09-07', 'C23', 0, 0.00, '2016-09-06', NULL, '', 23500.00, 00000000, 1, NULL, NULL, 11, 1287, 1216, NULL, 0, NULL, 0),
	(1333, '2015-11-15', 'C239', 0, 39000.00, '2016-11-14', NULL, '', 19500.00, 00000000, 1, NULL, NULL, 11, 1394, 1317, NULL, 0, NULL, 0);

INSERT INTO `app_arrive_record` (`id`, `amount`, `arrive_record_status`, `pay_ac_no`, `receive_ac_no`, `remark`, `serial_no`, `time`, `app_id`, `drcrf`, `pay_name`, `summary`, `vouh_no`, `operate_remark`, `cash_flow_uid`, `detail_data`, `cash_flow_channel_type`, `transaction_type`, `partner_id`, `audit_status`, `issued_amount`, `repayment_audit_status`, `repayment_issued_amount`)
VALUES
	(11804, 10000.00, 0, '6212261001029074629', '1001300219000027827', '中天碧云苑16号402室2月份房租', '2016-01-28-09.21.27.475959', '2016-01-28 09:21:27', 2, '2', 'LIM SANG BAE', '网转', '0', NULL, '667c1ff3-540b-4588-bd4e-69208d232b60', '', 0, NULL, '', 0, NULL, 0, NULL),
	(11819, 17800.00, 0, '1210378-2', '1001300219000027827', '', '2016-01-28-12.31.52.707581', '2016-01-28 12:31:52', 2, '2', '伯东企业（上海）有限公司', '.', '0', NULL, '767a3081-519c-4507-a088-409835bddb17', '', 0, NULL, '', 0, NULL, 0, NULL),
	(11820, 19500.00, 0, '310069024018010013447', '121913751710201', NULL, 'G1400200047669C', '2016-01-28 12:44:13', 11, '2', '佩特化工（上海）有限公司', '1月房租', '', NULL, '2a12c03e-53e6-4a31-ae3b-e174b1b7b465', '', 0, NULL, '', 0, NULL, 0, NULL),
	(11826, 23500.00, 0, '00000450768505580', '121913751710201', NULL, 'G1400500051258C', '2016-01-28 13:13:22', 11, '2', '苏威（上海）有限公司', '采购款', '', NULL, 'd373f30b-22f7-43dd-977f-ca5e8d20c459', '', 0, NULL, '', 0, NULL, 0, NULL);

	
INSERT INTO `cash_flow_bill_match` (`id`, `cash_flow_uuid`, `audit_bill_id`, `create_time`, `match_status`)
VALUES
	(1, '667c1ff3-540b-4588-bd4e-69208d232b60', 'd026afd5-0005-452a-9d3e-c9f9a0b199ca', '2016-01-28 16:00:50', 0),
	(2, '767a3081-519c-4507-a088-409835bddb17', 'a62f37ca-1dce-4032-81b8-bcb40be1e0a7', '2016-01-28 16:00:50', 0),
	(3, '2a12c03e-53e6-4a31-ae3b-e174b1b7b465', '13157cd7-55be-473c-8d6a-b813b64b68e2', '2016-01-28 16:00:50', 0),
	(4, 'd373f30b-22f7-43dd-977f-ca5e8d20c459', '61830e11-0da3-4a9e-8ed9-f2f3f97ebdca', '2016-01-28 16:00:50', 0);

	

SET FOREIGN_KEY_CHECKS=1;

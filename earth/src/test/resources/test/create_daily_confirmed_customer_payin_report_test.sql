SET FOREIGN_KEY_CHECKS=0;

delete from transaction_record;
delete from app;
delete from payment_institution;
delete from rent_order;
delete from contract;

delete from customer;

INSERT INTO `customer` (`id`, `account`, `city`, `mobile`, `name`, `province`, `source`, `app_id`) VALUES
(-1, NULL, NULL, NULL, '小寓预付款承租方', NULL, '寓见', 1),
(971, NULL, NULL, NULL, '上海化耀国际贸易有限公司', NULL, '优帕克', 2);

INSERT INTO `contract` (`id`, `begin_date`, `contract_no`, `contract_status`, `deposit`, `end_date`, `interrupt_date`, `interrupt_reason`, `month_fee`, `non_deposit_guarantee_way`, `payment_instrument`, `renewal`, `url`, `app_id`, `customer_id`, `house_id`, `transfer_status`) VALUES
(943, '2015-09-01', 'HQWB8#701-2015-09-08', 0, '10000.00', '2016-08-31', NULL, '', '10000.00', b'0', 3, NULL, NULL, 2, 971, 913, 0);

INSERT INTO `rent_order` (`id`, `due_date`, `end_date`, `late_fee`, `order_no`, `order_status`, `paid_rent`, `payout_time`, `is_settled`, `start_date`, `total_rent`, `contract_id`, `customer_id`, `repayment_type`, `user_upload_param`, `unique_particle_id`, `transfer_status`, `modify_time`, `unique_bill_id`, `contract_payment_amount`, `contract_payment_type`, `doubt_record`, `virtual_account_unique_id`,`collection_bill_capital_status`) VALUES
(6521, '2015-11-19', NULL, NULL, 'CC-61389-1', 0, NULL, NULL, b'0', NULL, '15400.00', 943, 1003, 0, NULL, NULL, 0, NULL, 'anxin_RJ00136-20150915-4_6521', '46200.00', 1, NULL, NULL,4),
(6522, '2015-12-19', NULL, NULL, 'CC-61389-2', 1, NULL, NULL, b'0', NULL, '15400.00', 943, 1003, 0, NULL, NULL, 0, NULL, 'anxin_RJ00136-20150915-5_6522', '0.00', 0, NULL, NULL,3),
(6523, '2016-01-19', NULL, NULL, 'CC-61389-3', 2, NULL, NULL, b'0', NULL, '15400.00', 943, 1003, 0, NULL, NULL, 0, NULL, 'anxin_RJ00136-20150915-6_6523', '0.00', 0, NULL, NULL,3),
(6524, '2016-02-19', NULL, NULL, 'CC-61389-4', 0, NULL, NULL, b'0', NULL, '15400.00', 943, 1003, 0, NULL, NULL, 0, NULL, 'anxin_RJ00136-20150915-7_6524', '46200.00', 1, NULL, NULL,3),
(6525, '2016-03-19', NULL, NULL, 'CC-61389-5', 1, NULL, NULL, b'0', NULL, '15400.00', 943, 1003, 0, NULL, NULL, 0, NULL, 'anxin_RJ00136-20150915-8_6525', '0.00', 0, NULL, NULL,4),
(6526, '2016-04-19', NULL, NULL, 'CC-61389-6', 1, NULL, NULL, b'0', NULL, '15400.00', 943, 1003, 0, NULL, NULL, 0, NULL, 'anxin_RJ00136-20150915-9_6526', '0.00', 0, NULL, NULL,4);

INSERT INTO `transaction_record` (`id`, `card_no`, `channel`, `last_modified_time`, `merchant_payment_no`, `order_no`, `pay_money`, `request_no`, `trade_no`, `transaction_record_status`, `app_id`, `payment_institution_id`) VALUES
(1084, NULL, 1, '2015-08-16 21:43:18', '632910458296665088', 'CC-61389-1', '2700.00', '20150816094307632910458296665088', NULL, 0, 1, 2),
(1085, NULL, 1, '2015-08-17 16:13:15', '632963447715726336', 'CC-61389-2', '1600.00', '20150817011340632963447715726336', '2015081700001000220060490246', 2, 1, 2),
(1086, NULL, 1, '2015-08-17 16:13:17', '632964074848060416', 'CC-61389-3', '1650.00', '20150817011610632964074848060416', '2015081700001000930003053996', 2, 1, 2),
(1087, NULL, 1, '2015-08-17 16:13:20', '633050981661607936', 'CC-61389-4', '1610.00', '20150817070130633050981661607936', '2015081700001000040061148642', 2, 1, 2),
(1088, NULL, 1, '2015-08-17 12:07:55', '633128058356171776', 'CC-61389-5', '1650.00', '20150817120747633128058356171776', NULL, 0, 1, 2),
(1089, NULL, 1, '2015-08-17 16:25:49', '633192852337656832', 'CC-61389-6', '1700.00', '20150817042529633192852337656832', NULL, 0, 1, 2);

INSERT INTO `payment_institution` (`id`, `alias`, `code`, `day_upper_limit`, `name`, `once_upper_limit`) VALUES
(1, '', 'record', NULL, '记录支付', NULL),
(2, 'alipay', 'alipay', NULL, '支付宝', NULL),
(3, 'directbank', 'ICBC', NULL, '工行银企互联', NULL),
(4, 'unionpay', 'unionpay', NULL, '银联', NULL),
(5, 'directbank', 'CMB', NULL, '招行银企直联', NULL);

INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`, `payee_account_name`, `payee_account_no`, `payee_bank_name`) VALUES
(1, 'xiaoyu', '70991db75ebb24fa0993f4fa25775022', b'0', 'http://beta.demo2do.com/jupiter/', '寓见', 5, NULL, NULL, NULL),
(2, 'youpark', '123456', b'0', '', '优帕克', 4, NULL, NULL, NULL),
(3, 'test4Zufangbao', '2e85ae999845f25faf8ea7b514ee0aca', b'0', 'http://e.zufangbao.cn', '租房宝测试账号', 3, NULL, NULL, NULL),
(4, 'zhuke', 'cb742d55634a532060ac7387caa8d242', b'0', 'http://zkroom.com/', '杭州驻客网络技术有限公司', 6, NULL, NULL, NULL),
(5, 'laoA', '744a38b1672b728dc35a68f7a10df082', b'0', 'http://www.13980.com', '上海元轼信息咨询有限公司', 7, NULL, NULL, NULL),
(6, 'keluoba', '30f4d225438a7fd1541fe1a055420dfd', b'0', 'http://keluoba.com', '柯罗芭', 8, NULL, NULL, NULL),
(7, 'testForXiaoyu', '2138ed4b66cebbded5753f3c59a064ae', b'0', 'http://xxx.com', '小寓测试帐号', 10, NULL, NULL, NULL),
(8, 'woju', '495d49ae55fd794036576aa8f71dbb43', b'0', 'http://www.woju.com', '杭州蜗居', 11, NULL, NULL, NULL),
(9, 'meijia', '1bf40057e15fd462773c13e0a85e9676', b'0', 'http://99196.hotel.cthy.com/', '美家公寓', 12, NULL, NULL, NULL),
(10, 'anxin', 'd1cd2618432723c478fab102bdfa2e11', b'0', 'http://anxin.com', '安心公寓', 13, NULL, NULL, NULL),
(11, 'yuanlai', 'eaaf6bfe5c98e042822b60cae955a276', b'0', 'http://yuanlai.com', '源涞国际', 14, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS=1;
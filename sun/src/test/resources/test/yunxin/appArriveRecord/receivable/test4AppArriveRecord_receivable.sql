SET FOREIGN_KEY_CHECKS=0;

delete from `app_arrive_record`;
delete from `app`;
delete from `company`;


INSERT INTO `app` (`id`, `app_id`, `app_secret`, `is_disabled`, `host`, `name`, `company_id`)
VALUES
	(1,'xiaoyu','70991db75ebb24fa0993f4fa25775022',00000000,'http://beta.demo2do.com/jupiter/','寓见',5),
	('11', 'yuanlai', 'eaaf6bfe5c98e042822b60cae955a276', '\0', 'http://yuanlai.com', '源涞国际', '14');


INSERT INTO `company` (`id`, `address`, `full_name`, `short_name`) VALUES 
('5', '上海', '小寓科技', '小寓'),
('14', NULL, '源涞国际', '源涞国际');

	
	
INSERT INTO `app_arrive_record` (`id`, `amount`, `arrive_record_status`, `pay_ac_no`, `receive_ac_no`, `remark`, `serial_no`, `time`, `app_id`, `drcrf`, `pay_name`, `summary`, `vouh_no`, `operate_remark`, `cash_flow_uid`, `detail_data`, `cash_flow_channel_type`, `transaction_type`, `partner_id`,`issued_amount`,`audit_status`)
VALUES
-- id(1,2,3,5,6)的`drcrf`为1（借，进账），id为4的drcrf为2（出账）；
-- id为7的账单appId为'xiaoyu';
(1, '10.00', 0, 'pay_ac_no_1', 'account_no_1', 'remark', 'serial_no_1', '2015-10-01 07:25:30', 11, '1', 'pay_name_1', 'summary_1', 'alipay_cash_flow_1', NULL, 'cash_flow_uuid_1', 'detail_data_1', 1, 5, 'partner_id',NULL,0),
(2, '20.00', 0, 'pay_ac_no_2', 'account_no_1', '服务费_2', 'serial_no_2', '2015-10-02 07:58:01', 11, '1', 'pay_name_2', 'summary_2', 'alipay_cash_flow_2', NULL, 'cash_flow_uuid_2', 'detail_data_1', 1, 5, 'partner_id','1.00',1),
(3, '30.00', 0, 'pay_ac_no_1', 'account_no_1',	'服务费_3', 'serial_no_3', '2015-10-03 09:39:29', 11, '1', 'pay_name_3', 'summary_3', 'alipay_cash_flow_3', NULL, 'cash_flow_uuid_3', 'detail_data_1', 1, 5, 'partner_id','30.00',2),
(4, '40.00', 0, '', '', 						'服务费_4', 'serial_no_4', '2015-10-04 09:39:30', 11, '2', 'pay_name_4', 'summary_4', 'alipay_cash_flow_4', NULL, 'cash_flow_uuid_4', 'detail_data_1', 1, 5, 'partner_id',NULL,NULL),
(5, '50.00', 0, 'pay_ac_no_5', 'account_no_2',	  '服务费_5', 'serial_no_5', '2015-10-05 09:39:31', 11, '1', 'pay_name_5', 'summary_5', 'alipay_cash_flow_5', NULL, 'cash_flow_uuid_5', 'detail_data_1', 1, 5, 'partner_id','1.00',3),
(6, '60.00', 0, 'pay_ac_no_6', 'account_no_1',	  '服务费_3', 'serial_no_6', '2015-10-03 09:40:33', 11, '1', 'pay_name_6', 'summary_6', 'alipay_cash_flow_6', NULL, 'cash_flow_uuid_6', 'detail_data_1', 1, 5, 'partner_id','60.00',2),
(7, '70.00', 0, 'pay_ac_no_7', 'account_no_3',	  '服务费_3', 'serial_no_6', '2015-10-07 09:39:35', 1, '1', 'pay_name_7', 'summary_7', 'alipay_cash_flow_7', NULL, 'cash_flow_uuid_7', 'detail_data_1', 1, 5, 'partner_id','70.00',2);


SET FOREIGN_KEY_CHECKS=1;
